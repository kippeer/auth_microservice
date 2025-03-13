package com.example.banking.api.mapper;

import com.example.banking.api.dto.request.AccountRequest;
import com.example.banking.api.dto.response.AccountResponse;
import com.example.banking.core.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toEntity(AccountRequest request) {
        Account account = new Account();
        account.setAccountNumber(request.getAccountNumber());
        return account;
    }
    
    public AccountResponse toResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());
        response.setUserId(account.getUser().getId());
        return response;
    }
}