
package com.authmicroservice.demo.service;


import com.authmicroservice.demo.dto.AuthRequest;
import com.authmicroservice.demo.dto.AuthResponse;
import com.authmicroservice.demo.dto.UserDto;

public interface AuthService {
    AuthResponse login(AuthRequest loginRequest);
    AuthResponse register(UserDto registerRequest);
}
