package com.authmicroservice.demo.service;

import com.authmicroservice.demo.model.User;
import com.authmicroservice.demo.repository.UserRepository;
import com.authmicroservice.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Método para autenticar e gerar o token
    public String authenticateUser(String username, String password) {
        // Aqui, você pode fazer a validação de senha, por exemplo
        User user = userRepository.findById(username).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            return jwtTokenProvider.generateToken(username);  // Gerar o token se as credenciais forem válidas
        } else {
            return null;  // Ou lançar uma exceção, caso as credenciais sejam inválidas
        }
    }
}
