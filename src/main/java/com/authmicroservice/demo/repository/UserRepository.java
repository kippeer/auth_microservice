package com.authmicroservice.demo.repository;

import com.authmicroservice.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    // Método para verificar se o nome de usuário já existe
    boolean existsByUsername(String username);
}
