package com.example.banking.core.domain.service;

import com.example.banking.core.domain.model.User;

public interface AuthService {
    User registerUser(User user);
    User authenticateUser(String username, String password);
    void validateUserCredentials(String username, String password);
}