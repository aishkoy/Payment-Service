package kg.attractor.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(){
        return ResponseEntity.ofNullable(adminService.getAllTransactions())
    }

    @GetMapping("transactions/approval")
    public ResponseEntity<List<TransactionDto>> getTransactionsRequiringApproval(){
        return ResponseEntity.ofNullable(adminService.getTransactionsRequiringApproval);
    }

    @PostMapping("transactions/approval")
    public ResponseEntity<Long> approveTransaction(@RequestBody Map<String, Long> transaction){
        Long transactionId  = transaction.get("transactionId");
        return ResponseEntity.ofNullable(adminService.approveTransaction(transactionId));
    }

    @PostMapping("transactions/rollback")
    public ResponseEntity<Long> rollbackTransaction(@RequestBody Map<String, Long> transaction){
        Long transactionId  = transaction.get("transactionId");
        return ResponseEntity.ofNullable(adminService.rollbackTransaction(transactionId));
    }

    @DeleteMapping("transactions/{transactionId}")
    public ResponseEntity<Long> deleteTransaction(@PathVariable("transactionId") Long transactionId){
        return ResponseEntity.ofNullable(adminService.deleteTransaction(transactionId));
    }

    @PutMapping("users/{userId}")
    public ResponseEntity<Long> blockUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ofNullable(adminService.blockUser(userId));
    }
}
