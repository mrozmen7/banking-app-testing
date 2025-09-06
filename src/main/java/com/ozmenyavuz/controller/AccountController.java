package com.ozmenyavuz.controller;


import com.ozmenyavuz.dto.AccountDto;
import com.ozmenyavuz.dto.AccountRequestDto;
import com.ozmenyavuz.model.Account;
import com.ozmenyavuz.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 1) Create Account
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountRequestDto requestDto) {

        Account account = new Account
                (null, requestDto.getAccountNumber(),
                requestDto.getHolderName(), requestDto.getBalance());

        Account saved = accountService.createAccount(account);

        AccountDto response = toDto(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2) Get Account by Id
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(toDto(account));
    }

    // 3) Get All Accounts
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    // 4) Deposit
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account account = accountService.deposit(id, amount);
        return ResponseEntity.ok(toDto(account));
    }

    // 5) Withdraw
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account account = accountService.withdraw(id, amount);
        return ResponseEntity.ok(toDto(account));
    }

    // 6) Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    // --- Mapping Method (Entity → DTO) ---
    private AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setHolderName(account.getHolderName());
        dto.setBalance(account.getBalance());
        return dto;
    }
}