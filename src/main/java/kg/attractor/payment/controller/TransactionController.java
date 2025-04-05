package kg.attractor.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor

public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("{accountId}/history")
    public ResponseEntity<List<TransactionDto>> getAccountTransactions(@PathVariable String accountId){
        return ResponseEntity.ofNullable(transactionService.getAccountTransactions(accountId));
    }

    @PostMapping
    public ResponseEntity<Long> processTransaction(@RequestBody @Valid TransactionDto  transactionDto){
        return ResponseEntity.ofNullable(transactionDto.processTransaction(transactionDto));
    }
}
