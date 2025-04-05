package kg.attractor.payment.service;

import jakarta.validation.Valid;
import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.dto.CurrencyDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<AccountDto> getUserAccounts();

    Long createAccount(@Valid CurrencyDto dto);

    BigDecimal getAccountBalance(Long accountId);

    BigDecimal topUpAccount(Long accountId, BigDecimal amount);
}
