package com.authmicroservice.demo.service;

import com.authmicroservice.demo.model.User;
import com.authmicroservice.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Método para autenticar o usuário e gerar o token
    public String authenticateUser(String username, String password) {
        // Implementar autenticação (verificação de credenciais)
        return null;
    }

    // Método para registrar um novo usuário
    public boolean registerUser(User user) {
        // Verificar se o nome de usuário já existe
        if (userRepository.existsByUsername(user.getUsername())) {
            return false; // Retorna falso se o nome de usuário já existir
        }

        // Criptografar a senha antes de salvar
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // Salvar o novo usuário no banco de dados
        userRepository.save(user);
        return true;
    }
}
