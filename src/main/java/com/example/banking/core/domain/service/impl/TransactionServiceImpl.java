package com.example.banking.core.domain.service.impl;

import com.example.banking.core.domain.model.Account;
import com.example.banking.core.domain.model.Transaction;
import com.example.banking.core.domain.repository.TransactionRepository;
import com.example.banking.core.domain.service.AccountService;
import com.example.banking.core.domain.service.TransactionService;
import com.example.banking.core.exception.BusinessException;
import com.example.banking.core.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public Transaction createTransaction(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {
        validateTransaction(sourceAccountNumber, targetAccountNumber, amount);
        
        Account sourceAccount = accountService.getAccount(sourceAccountNumber);
        Account targetAccount = accountService.getAccount(targetAccountNumber);
        
        // Validate balance
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient funds");
        }
        
        // Update balances
        accountService.updateBalance(sourceAccountNumber, sourceAccount.getBalance().subtract(amount));
        accountService.updateBalance(targetAccountNumber, targetAccount.getBalance().add(amount));
        
        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType("TRANSFER");
        transaction.setStatus("COMPLETED");
        
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new BusinessException("Transaction not found"));
    }

    @Override
    public void validateTransaction(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {
        if (sourceAccountNumber.equals(targetAccountNumber)) {
            throw new ValidationException("Source and target accounts cannot be the same");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Amount must be greater than zero");
        }
        
        accountService.validateAccount(sourceAccountNumber);
        accountService.validateAccount(targetAccountNumber);
    }
}