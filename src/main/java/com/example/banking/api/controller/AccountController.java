package com.example.banking.api.controller;

import com.example.banking.api.dto.request.AccountRequest;
import com.example.banking.api.dto.response.AccountResponse;
import com.example.banking.api.mapper.AccountMapper;
import com.example.banking.core.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        var account = accountMapper.toEntity(request);
        var createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(accountMapper.toResponse(createdAccount));
    }
    
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) {
        var account = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(accountMapper.toResponse(account));
    }
}