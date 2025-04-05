package kg.attractor.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Long> createAccount(@RequestBody @Valid  CurrencyDto dto){
        return ResponseEntity.ofNullable(accountService.createAccount(dto));
    }

    @GetMapping("balance")
    public ResponseEntity<Double> getBalance(@RequestParam Long accountId){
        return ResponseEntity.ofNullable(accountService.getAccountBalance(accountId));
    }

    @PostMapping("balance")
    public ResponseEntity<AccountDto> topUp(@RequestBody @Valid AccountDto dto){
        return ResponseEntity.ofNullable(accountService.topUpAccount(dto));
    }
}
