package kg.attractor.payment.service.impl;

import jakarta.validation.Valid;
import kg.attractor.payment.dao.TransactionDao;
import kg.attractor.payment.dao.TransactionRollbackDao;
import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;
import kg.attractor.payment.exception.InsufficientFundsException;
import kg.attractor.payment.exception.TransactionNotFoundException;
import kg.attractor.payment.mapper.TransactionMapper;
import kg.attractor.payment.model.Transaction;
import kg.attractor.payment.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao dao;
    private final TransactionRollbackDao rollbackDao;
    private final TransactionMapper transactionMapper;
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionStatusService statusService;
    private final CurrencyConverterService currencyConverterService;

    @Override
    public List<TransactionDto> getAccountTransactions(Long accountId, Long userId) {
        accountService.getAccountByUserAndId(userId, accountId);

        List<TransactionDto> transactions = dao.getTransactionsByAccountId(accountId)
                .stream()
                .map(transactionMapper::toDto)
                .toList();

        validateTransactionList(transactions);
        return transactions;
    }

    @Override
    public List<TransactionDto> getAllTransactions(){
        List<TransactionDto> transactions = dao.getAllTransactions()
                .stream()
                .map(transactionMapper::toDto)
                .toList();
        validateTransactionList(transactions);
        return transactions;
    }

    @Override
    public List<TransactionDto> getTransactionsRequiringApproval() {
        List<TransactionDto> transactions = dao.getTransactionsRequiringApproval()
                .stream()
                .map(transactionMapper::toDto)
                .toList();
        validateTransactionList(transactions);
        return transactions;
    }

    @Override
    public Long processTransaction(@Valid TransactionRequestDto transactionDto) {
        Long fromAccount = transactionDto.getFromAccountId();
        Long toAccount = transactionDto.getToAccountId();

        if(fromAccount.equals(toAccount)){
            throw new AccessDeniedException("You can't transfer money to the same account!");
        }

        Long userId = userService.getAuthId();
        accountService.getAccountByUserAndId(userId, fromAccount);
        accountService.getAccountById(toAccount);

        BigDecimal amount = transactionDto.getAmount();

        validateSufficientFunds(fromAccount, amount);

        log.info("Processing transaction from {} to {}", fromAccount, toAccount);

        if (amount.compareTo(BigDecimal.TEN) > 0) {
            return createTransactionWithPending(fromAccount, toAccount, amount);
        } else {
            Long transactionStatus = statusService.getStatusCompleted();
            return processTransaction(fromAccount, toAccount, amount, transactionStatus);
        }
    }

    public Long processTransaction(Long fromAccountId, Long toAccountId, BigDecimal amount, Long transactionStatus) {
        BigDecimal fromAccountBalance = accountService.getAccountBalance(fromAccountId).subtract(amount);
        BigDecimal toAccountBalance = convertCurrencyIfNecessary(fromAccountId, toAccountId, amount, false);

        accountService.updateBalance(fromAccountId, fromAccountBalance);
        accountService.updateBalance(toAccountId, toAccountBalance);

        Long transactionId = dao.addTransaction(
                Transaction.builder()
                        .fromAccountId(fromAccountId)
                        .toAccountId(toAccountId)
                        .statusId(transactionStatus)
                        .amount(amount)
                        .build()
        );

        log.info("Transaction {} created with status 'completed'.", transactionId);
        return transactionId;
    }

    @Override
    public TransactionDto approveTransaction(Long transactionId) {
        TransactionDto dto = getRequiringApprovalTransaction(transactionId);
        dto.setStatusId(statusService.getStatusCompleted());

        validateSufficientFunds(dto.getFromAccountId(), dto.getAmount());

        BigDecimal fromAccountBalance = accountService.getAccountBalance(dto.getFromAccountId()).subtract(dto.getAmount());
        BigDecimal toAccountBalance = convertCurrencyIfNecessary(dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount(), false);

        accountService.updateBalance(dto.getFromAccountId(), fromAccountBalance);
        accountService.updateBalance(dto.getToAccountId(), toAccountBalance);

        dao.updateTransaction(transactionMapper.toEntity(dto));
        return dto;
    }

    @Override
    public TransactionDto rollbackTransaction(Long transactionId) {
        TransactionDto dto = getTransactionByIdForRollback(transactionId);

        if(dto.getStatusId().equals(statusService.getStatusCompleted())) {
            BigDecimal fromAccountBalance = accountService.getAccountBalance(dto.getFromAccountId()).add(dto.getAmount());
            BigDecimal toAccountBalance = convertCurrencyIfNecessary(dto.getToAccountId(), dto.getFromAccountId(), dto.getAmount(), true);

            accountService.updateBalance(dto.getFromAccountId(), fromAccountBalance);
            accountService.updateBalance(dto.getToAccountId(), toAccountBalance);
        }

        dto.setStatusId(statusService.getStatusRolledBack());
        dao.updateTransaction(transactionMapper.toEntity(dto));
        log.info("Rolled back transaction {}", transactionId);
        rollbackDao.addTransaction(dto.getId());
        return dto;
    }

    @Override
    public TransactionDto deleteTransaction(Long transactionId) {
        TransactionDto dto = getTransactionForDeleting(transactionId);
        dto.setStatusId(statusService.getStatusDeleted());
        rollbackDao.addTransaction(transactionId);
        dao.updateTransaction(transactionMapper.toEntity(dto));
        log.info("Deleted transaction {}", transactionId);
        return dto;
    }

    public TransactionDto getTransactionForDeleting(Long transactionId) {
        Transaction transaction = rollbackDao.getTransactionForDeleting(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + transactionId + " cannot be deleted."));
        log.info("Retrieved transaction for deleting {}", transactionId);
        return transactionMapper.toDto(transaction);
    }

    public TransactionDto getTransactionByIdForRollback(Long transactionId) {
        Transaction transaction = dao.getTransactionForCancellation(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + transactionId + " cannot be rolled back"));
        log.info("Retrieved transaction with id {}", transactionId);
        return transactionMapper.toDto(transaction);
    }

    public TransactionDto getRequiringApprovalTransaction(Long transactionId) {
        Transaction transaction = dao.getRequiringApprovalTransaction(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + transactionId + " doesn't needs approval"));
        log.info("Transaction {} approved with id {}", transactionId, transactionId);
        return transactionMapper.toDto(transaction);
    }

    private Long createTransactionWithPending(Long fromAccount, Long toAccount, BigDecimal amount) {
        Long transactionStatus = statusService.getStatusPending();
        Long transactionId = dao.addTransaction(
                Transaction.builder()
                        .fromAccountId(fromAccount)
                        .toAccountId(toAccount)
                        .statusId(transactionStatus)
                        .amount(amount)
                        .build()
        );
        log.info("Transaction {} created with status 'pending'.", transactionId);
        return transactionId;
    }

    private BigDecimal convertCurrencyIfNecessary(Long fromAccountId, Long toAccountId, BigDecimal amount, boolean isRolledBack) {
        Long fromCurrencyId = accountService.getAccountCurrencyId(fromAccountId);
        Long toCurrencyId = accountService.getAccountCurrencyId(toAccountId);

        BigDecimal toAccountAmount = amount;

        if (!fromCurrencyId.equals(toCurrencyId)) {
            log.info("Converting currencies from {} to {}", fromAccountId, toAccountId);
            toAccountAmount = currencyConverterService.convert(fromCurrencyId, toCurrencyId, amount);
        }

        if(isRolledBack){
            BigDecimal toAccountBalance = (accountService.getAccountBalance(fromAccountId)).subtract(toAccountAmount);
            validateSufficientFunds(fromAccountId, toAccountAmount);
            return toAccountBalance;
        }
        return toAccountAmount.add(accountService.getAccountBalance(toAccountId));
    }

    private void validateSufficientFunds(Long fromAccountId, BigDecimal amount) {
        BigDecimal fromAccountBalance = accountService.getAccountBalance(fromAccountId).subtract(amount);

        log.info("Check sufficient funds from {} to {}", fromAccountId, fromAccountBalance);
        if (fromAccountBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("You have insufficient funds to process this transaction");
        }
    }

    private void validateTransactionList(List<?> transactions) {
        if (transactions.isEmpty()) {
            log.warn("User has no transactions");
            throw new TransactionNotFoundException("There are no transactions");
        }
        log.info("Retrieved {} transactions", transactions.size());
    }
}
