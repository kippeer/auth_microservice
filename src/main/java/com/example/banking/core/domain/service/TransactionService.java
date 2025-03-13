package com.example.banking.core.domain.service;

import com.example.banking.core.domain.model.Transaction;
import java.math.BigDecimal;

public interface TransactionService {
    Transaction createTransaction(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount);
    Transaction getTransaction(Long transactionId);
    void validateTransaction(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount);
}