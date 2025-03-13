package com.example.banking.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String accountNumber;
    
    private BigDecimal balance = BigDecimal.ZERO;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}