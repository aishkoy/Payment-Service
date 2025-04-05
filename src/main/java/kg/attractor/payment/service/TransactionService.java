package kg.attractor.payment.service;

import jakarta.validation.Valid;
import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAccountTransactions(Long accountId);
    Long processTransaction(@Valid TransactionRequestDto transactionDto);

    Long processTransaction(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
