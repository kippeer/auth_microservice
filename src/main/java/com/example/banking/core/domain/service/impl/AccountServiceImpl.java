package com.example.banking.core.domain.service.impl;

import com.example.banking.core.domain.model.Account;
import com.example.banking.core.domain.repository.AccountRepository;
import com.example.banking.core.domain.service.AccountService;
import com.example.banking.core.exception.NotFoundException;
import com.example.banking.core.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Account createAccount(Account account) {
        validateNewAccount(account);
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found: " + accountNumber));
    }

    @Override
    public List<Account> getUserAccounts(Long userId) {
        // Implementation pending based on user repository integration
        return List.of();
    }

    @Override
    @Transactional
    public void updateBalance(String accountNumber, BigDecimal newBalance) {
        Account account = getAccount(accountNumber);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    public void validateAccount(String accountNumber) {
        if (!accountRepository.findByAccountNumber(accountNumber).isPresent()) {
            throw new ValidationException("Invalid account number: " + accountNumber);
        }
    }

    private void validateNewAccount(Account account) {
        if (account.getAccountNumber() == null || account.getAccountNumber().trim().isEmpty()) {
            throw new ValidationException("Account number cannot be empty");
        }
        if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new ValidationException("Account number already exists");
        }
    }
}