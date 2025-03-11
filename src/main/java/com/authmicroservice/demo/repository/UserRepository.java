package com.authmicroservice.demo.repository;

import com.authmicroservice.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Aqui podemos adicionar métodos customizados, se necessário.
}
