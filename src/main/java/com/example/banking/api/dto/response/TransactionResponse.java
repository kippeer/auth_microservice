package com.example.banking.api.dto.response;

import com.example.banking.core.domain.model.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String status;
    private String type;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.sourceAccountNumber = transaction.getSourceAccount().getAccountNumber();
        this.targetAccountNumber = transaction.getTargetAccount().getAccountNumber();
        this.amount = transaction.getAmount();
        this.timestamp = transaction.getTimestamp();
        this.status = transaction.getStatus();
        this.type = transaction.getType();
    }
}