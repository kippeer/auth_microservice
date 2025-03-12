package com.authmicroservice.demo.payload;

import java.util.List;

public class JwtResponse {
    private String token;  // token que será retornado
    private String type = "Bearer";  // Tipo do token (Bearer é o padrão para JWT)
    private Long id;  // ID do usuário
    private String name;  // Nome do usuário
    private String email;  // Email do usuário
    private List<String> roles;  // Roles ou permissões associadas ao usuário

    // Construtor da classe para inicializar os valores
    public JwtResponse(String token, Long id, String name, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    // Método para obter o token de autenticação
    public String getToken() {
        return token;
    }

    // Método para definir o token de autenticação
    public void setToken(String token) {
        this.token = token;
    }

    // Método para obter o tipo do token (geralmente "Bearer")
    public String getTokenType() {
        return type;
    }

    // Método para definir o tipo do token
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    // Método para obter o ID do usuário
    public Long getId() {
        return id;
    }

    // Método para definir o ID do usuário
    public void setId(Long id) {
        this.id = id;
    }

    // Método para obter o nome do usuário
    public String getName() {
        return name;
    }

    // Método para definir o nome do usuário
    public void setName(String name) {
        this.name = name;
    }

    // Método para obter o email do usuário
    public String getEmail() {
        return email;
    }

    // Método para definir o email do usuário
    public void setEmail(String email) {
        this.email = email;
    }

    // Método para obter as roles do usuário
    public List<String> getRoles() {
        return roles;
    }

    // Método para definir as roles do usuário
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
