package kg.attractor.payment.controller;

import jakarta.validation.Valid;
import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;
import kg.attractor.payment.service.TransactionService;
import kg.attractor.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("{accountId}/history")
    public ResponseEntity<List<TransactionDto>> getAccountTransactions(@PathVariable Long accountId){
        return ResponseEntity.ofNullable(transactionService.getAccountTransactions(accountId, userService.getAuthId()));
    }

    @PostMapping
    public ResponseEntity<Long> processTransaction(@RequestBody @Valid TransactionRequestDto transactionDto){
        return ResponseEntity.ofNullable(transactionService.processTransaction(transactionDto));
    }
}
