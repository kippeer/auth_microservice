package com.authmicroservice.demo.controller;

import com.authmicroservice.demo.service.UserService;
import com.authmicroservice.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Endpoint para login e gerar o token
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password) {
        String token = userService.authenticateUser(username, password);

        if (token != null) {
            return token;  // Retorna o token gerado
        } else {
            return "Credenciais inválidas!";  // Retorna uma mensagem de erro se as credenciais forem inválidas
        }
    }

    // Endpoint para registro de usuário
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        boolean userCreated = userService.registerUser(user);

        if (userCreated) {
            return "Usuário registrado com sucesso!";
        } else {
            return "Erro ao registrar o usuário!";
        }
    }
}
