package com.example.banking.api.controller;

import com.example.banking.api.dto.request.TransactionRequest;
import com.example.banking.api.dto.response.TransactionResponse;
import com.example.banking.core.domain.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transaction management APIs")
@SecurityRequirement(name = "bearer-jwt")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Transfer money between accounts")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest request) {
        var transaction = transactionService.createTransaction(
            request.getSourceAccountNumber(),
            request.getTargetAccountNumber(),
            request.getAmount()
        );
        
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction details", description = "Retrieve details of a specific transaction")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
        var transaction = transactionService.getTransaction(id);
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }
}