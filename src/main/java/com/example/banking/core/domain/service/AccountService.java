package com.example.banking.core.domain.service;

import com.example.banking.core.domain.model.Account;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    Account getAccount(String accountNumber);
    List<Account> getUserAccounts(Long userId);
    void updateBalance(String accountNumber, BigDecimal newBalance);
    void validateAccount(String accountNumber);
}