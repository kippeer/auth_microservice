package com.example.banking.api.dto.request;

import lombok.Data;

@Data
public class AccountRequest {
    private String accountNumber;
    private Long userId;
}