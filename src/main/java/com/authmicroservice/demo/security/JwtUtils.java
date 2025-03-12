package com.authmicroservice.demo.security;

import com.authmicroservice.demo.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Geração do token JWT
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Construção do token com base nas informações do usuário
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())  // Subjetivo (usuário)
                .setIssuedAt(new Date())  // Data de emissão
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))  // Data de expiração
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  // Assinatura com o segredo
                .compact();
    }

    // Extração do nome do usuário do token JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()  // Método para versões mais antigas
                .setSigningKey(jwtSecret)  // Definição da chave para validar o token
                .parseClaimsJws(token)  // Parse do token
                .getBody()
                .getSubject();  // Retorno do nome do usuário
    }

    // Validação do token JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()  // Método para versões mais antigas
                    .setSigningKey(jwtSecret)  // Definição da chave para validar o token
                    .parseClaimsJws(authToken);  // Validação e parsing do token
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());  // Erro relacionado à assinatura do JWT
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());  // Erro de formatação do JWT
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());  // Token expirado
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());  // Token não suportado
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());  // Erro de argumento inválido
        }
        return false;  // Se qualquer exceção for lançada, o token é considerado inválido
    }
}
