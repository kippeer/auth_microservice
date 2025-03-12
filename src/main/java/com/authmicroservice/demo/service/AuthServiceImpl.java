package com.authmicroservice.demo.service;

import com.authmicroservice.demo.dto.AuthRequest;
import com.authmicroservice.demo.dto.AuthResponse;
import com.authmicroservice.demo.dto.UserDto;
import com.authmicroservice.demo.entity.Role;
import com.authmicroservice.demo.entity.User;
import com.authmicroservice.demo.exception.InvalidCredentialsException;
import com.authmicroservice.demo.exception.ResourceNotFoundException;
import com.authmicroservice.demo.repository.RoleRepository;
import com.authmicroservice.demo.repository.UserRepository;
import com.authmicroservice.demo.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public AuthResponse login(AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        return new AuthResponse(jwt, "Bearer", user.getId(), user.getUsername());
    }

    @Override
    public AuthResponse register(UserDto registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new InvalidCredentialsException("Username is already taken");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new InvalidCredentialsException("Email is already in use");
        }

        // Criar usuário
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName()); // ✅ Adicionando o campo 'name'
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Atribuir ROLE_USER por padrão
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role not found."));
        user.setRoles(Collections.singleton(userRole));

        User savedUser = userRepository.save(user);

        // Autenticar o novo usuário
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        return new AuthResponse(jwt, "Bearer", savedUser.getId(), savedUser.getUsername());
    }
}
