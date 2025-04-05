package kg.attractor.payment.controller;

import jakarta.validation.Valid;
import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.dto.CurrencyDto;
import kg.attractor.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return ResponseEntity.ofNullable(accountService.getUserAccounts());
    }

    @PostMapping
    public ResponseEntity<Long> createAccount(@RequestBody @Valid CurrencyDto dto){
        return ResponseEntity.ofNullable(accountService.createAccount(dto));
    }

    @GetMapping("balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestParam Long accountId){
        return ResponseEntity.ofNullable(accountService.getAccountBalance(accountId));
    }

    @PostMapping("balance")
    public ResponseEntity<BigDecimal> topUp(@RequestParam Long accountId, @RequestParam BigDecimal amount){
        return ResponseEntity.ofNullable(accountService.updateBalance(accountId, amount));
    }
}
