package kg.attractor.payment.service;

import jakarta.validation.Valid;
import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAccountTransactions(Long accountId, Long userId);

    List<TransactionDto> getAllTransactions();

    List<TransactionDto> getTransactionsRequiringApproval();

    Long processTransaction(@Valid TransactionRequestDto transactionDto);

    TransactionDto approveTransaction(Long transactionId);

    TransactionDto rollbackTransaction(Long transactionId);

    TransactionDto deleteTransaction(Long transactionId);
}
