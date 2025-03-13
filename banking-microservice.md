# Banking Microservice Structure

## Core Module
```
banking-microservice/
├── core/
│   ├── domain/
│   │   ├── model/
│   │   │   ├── Account.java
│   │   │   ├── Transaction.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── AccountRepository.java
│   │   │   ├── TransactionRepository.java
│   │   │   └── UserRepository.java
│   │   └── service/
│   │       ├── AccountService.java
│   │       ├── TransactionService.java
│   │       └── AuthService.java
│   └── exception/
│       ├── BusinessException.java
│       ├── NotFoundException.java
│       └── ValidationException.java
```

### BusinessException.java
```java
package com.example.banking.core.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
```

### NotFoundException.java
```java
package com.example.banking.core.exception;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message);
    }
}
```

### ValidationException.java
```java
package com.example.banking.core.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }
}
```

### Account.java
```java
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
```

### Transaction.java
```java
package com.example.banking.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;
    
    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;
    
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String type;
    private String status;
}
```

### User.java
```java
package com.example.banking.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    private String email;
    private boolean active = true;
}
```

## API Module
```
banking-microservice/
├── api/
│   ├── controller/
│   │   ├── AccountController.java
│   │   ├── TransactionController.java
│   │   └── AuthController.java
│   ├── dto/
│   │   ├── request/
│   │   │   ├── AccountRequest.java
│   │   │   ├── TransactionRequest.java
│   │   │   └── UserRequest.java
│   │   └── response/
│   │       ├── AccountResponse.java
│   │       ├── TransactionResponse.java
│   │       └── UserResponse.java
│   └── mapper/
│       ├── AccountMapper.java
│       ├── TransactionMapper.java
│       └── UserMapper.java
```

### AccountRequest.java
```java
package com.example.banking.api.dto.request;

import lombok.Data;

@Data
public class AccountRequest {
    private String accountNumber;
    private Long userId;
}
```

### AccountResponse.java
```java
package com.example.banking.api.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long userId;
}
```

### AccountMapper.java
```java
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
```

### AccountController.java
```java
package com.example.banking.api.controller;

import com.example.banking.api.dto.request.AccountRequest;
import com.example.banking.api.dto.response.AccountResponse;
import com.example.banking.api.mapper.AccountMapper;
import com.example.banking.core.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountMapper accountMapper;
    
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
```

## Infrastructure Module
```
banking-microservice/
├── infrastructure/
│   ├── config/
│   │   ├── DatabaseConfig.java
│   │   ├── SecurityConfig.java
│   │   └── WebConfig.java
│   ├── security/
│   │   ├── JwtTokenProvider.java
│   │   └── UserDetailsServiceImpl.java
│   └── persistence/
│       └── repository/
│           ├── AccountRepositoryImpl.java
│           ├── TransactionRepositoryImpl.java
│           └── UserRepositoryImpl.java
```

### DatabaseConfig.java
```java
package com.example.banking.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.banking.core.domain.repository")
@EnableTransactionManagement
public class DatabaseConfig {
}
```

### SecurityConfig.java
```java
package com.example.banking.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated();
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

## Application Configuration

### application.properties
```properties
server.port=8081

# Database Configuration (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/auth-teste
spring.datasource.username=postgres
spring.datasource.password=adm
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=yourSecretKey
jwt.expiration=86400000
```

### BankingApplication.java
```java
package com.example.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }
}
```

## Docker Configuration

### Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8081:8081"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/auth-teste
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=adm
    depends_on:
      - db
  
  db:
    image: postgres:13
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_DB=auth-teste
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=adm
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

## Project Structure
```
banking-microservice/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── banking/
│       │               ├── api/
│       │               │   ├── controller/
│       │               │   ├── dto/
│       │               │   └── mapper/
│       │               ├── core/
│       │               │   ├── domain/
│       │               │   └── exception/
│       │               └── infrastructure/
│       │                   ├── config/
│       │                   ├── security/
│       │                   └── persistence/
│       └── resources/
│           └── application.properties
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```