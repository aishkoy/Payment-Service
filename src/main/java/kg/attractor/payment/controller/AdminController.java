package kg.attractor.payment.controller;

import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.UserDto;
import kg.attractor.payment.service.TransactionService;
import kg.attractor.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(){
        return ResponseEntity.ofNullable(transactionService.getAllTransactions());
    }

    @GetMapping("transactions/approval")
    public ResponseEntity<List<TransactionDto>> getTransactionsRequiringApproval(){
        return ResponseEntity.ofNullable(transactionService.getTransactionsRequiringApproval());
    }

    @PostMapping("transactions/approval")
    public ResponseEntity<TransactionDto> approveTransaction(@RequestParam Long transactionId){
        return ResponseEntity.ofNullable(transactionService.approveTransaction(transactionId));
    }

    @PostMapping("transactions/rollback")
    public ResponseEntity<TransactionDto> rollbackTransaction(@RequestBody Map<String, Long> transaction){
        Long transactionId  = transaction.get("transactionId");
        return ResponseEntity.ofNullable(transactionService.rollbackTransaction(transactionId));
    }

    @DeleteMapping("transactions/{transactionId}")
    public ResponseEntity<TransactionDto> deleteTransaction(@PathVariable("transactionId") Long transactionId){
        return ResponseEntity.ofNullable(transactionService.deleteTransaction(transactionId));
    }

    @PutMapping("users/{userId}")
    public ResponseEntity<UserDto> blockUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ofNullable(userService.blockUser(userId));
    }
}
