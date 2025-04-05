package kg.attractor.payment.service.impl;

import kg.attractor.payment.component.AuthAdapter;
import kg.attractor.payment.dao.AccountDao;
import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.dto.CurrencyDto;
import kg.attractor.payment.exception.AccountAlreadyExistsException;
import kg.attractor.payment.exception.AccountLimitExceededException;
import kg.attractor.payment.exception.AccountNotFoundException;
import kg.attractor.payment.mapper.AccountMapper;
import kg.attractor.payment.model.Account;
import kg.attractor.payment.service.AccountService;
import kg.attractor.payment.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao dao;
    private final AccountMapper accountMapper;
    private final CurrencyService currencyService;
    private final AuthAdapter adapter;

    @Override
    public List<AccountDto> getUserAccounts() {
        Long userId = adapter.getAuthId();
        List<AccountDto> accounts = dao.getUserAccounts(userId)
                .stream()
                .map(accountMapper::toDto)
                .toList();
        validateAccountList(accounts, "User has no accounts");
        return accounts;
    }

    @Override
    public Long createAccount(CurrencyDto dto) {
        log.info("Creating account {}", dto);

        Long currencyId = currencyService.getCurrecyIdByName(dto.getCurrency());
        Long userId = adapter.getAuthId();
        validateAccount(userId, currencyId);

        Long accountId =  dao.create(userId, currencyId);
        log.info("Account {} created with id {}", accountId, accountId);
        return accountId;
    }

    @Override
    public BigDecimal getAccountBalance(Long accountId) {
        Long userId = adapter.getAuthId();

        AccountDto accountDto = getAccountByUserAndId(userId, accountId);
        BigDecimal balance = accountDto.getBalance();

        log.info("Account {} balance {}", accountId, balance);
        return balance;
    }

    @Override
    public BigDecimal topUpAccount(Long accountId, BigDecimal amount) {
        Long userId = adapter.getAuthId();
        AccountDto before = getAccountByUserAndId(userId, accountId);
        log.info("Balance before {}", before.getBalance());

        dao.topUpAccount(accountId, amount);

        BigDecimal balance = getAccountBalance(accountId);
        log.info("Balance after {}", balance);
        return balance;
    }

    private void validateAccount(Long userId, Long currencyId) {
        if (dao.countUserAccounts(userId) > 3) {
            throw new AccountLimitExceededException("You cannot create more than 3 accounts!");
        }

        if (dao.getUserAccount(userId, currencyId).isPresent()) {
            throw new AccountAlreadyExistsException("You already have an account with that currency!");
        }
    }

    private void validateAccountList(List<?> accounts, String errorMessage) {
        if (accounts.isEmpty()) {
            log.warn(errorMessage);
            throw new AccountNotFoundException(errorMessage);
        }
        log.info("Retrieved {} accounts", accounts.size());
    }

    public AccountDto getAccountByUserAndId(Long userId, Long accountId){
        Account account = dao.getAccountByUserAndId(userId, accountId)
                .orElseThrow(() -> new AccountNotFoundException("Ypu have no account with id " +  accountId));
        return accountMapper.toDto(account);
    }
}
