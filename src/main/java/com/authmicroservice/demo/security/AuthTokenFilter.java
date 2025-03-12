package com.authmicroservice.demo.security;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class AuthTokenFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Lógica opcional de inicialização
        logger.info("AuthTokenFilter inicializado.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast para HttpServletRequest e HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        logger.info("Requisição recebida para URI: {}", requestURI);

        // Permite o acesso aos endpoints /api/auth/login e /api/auth/register sem token
        if (requestURI.equals("/api/auth/login") || requestURI.equals("/api/auth/register")) {
            logger.info("Permissão concedida para o endpoint {} sem autenticação", requestURI);
            chain.doFilter(request, response);  // Passa sem validar o token
            return;
        }

        // Extração e validação do token JWT
        String token = httpRequest.getHeader("Authorization");

        if (token == null) {
            logger.warn("Token de autorização ausente para a requisição {}", requestURI);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Authorization token is missing");
            return;
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove o prefixo "Bearer "
            logger.info("Token recebido, validando...");
            if (isTokenValid(token)) {
                logger.info("Token válido para a requisição {}", requestURI);
                chain.doFilter(request, response);  // Token válido, permite a requisição
            } else {
                logger.warn("Token inválido para a requisição {}", requestURI);
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Invalid or expired token");
            }
        } else {
            logger.warn("Token mal formatado para a requisição {}", requestURI);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Authorization token is invalid");
        }
    }

    @Override
    public void destroy() {
        // Lógica opcional de limpeza
        logger.info("AuthTokenFilter destruído.");
    }

    // Método de validação de token (substitua com a lógica real de validação do JWT)
    private boolean isTokenValid(String token) {
        // Exemplo de validação simples (substitua com sua lógica real)
        return token.equals("valid-token");  // Aqui, substitua isso com a validação real do JWT
    }
}
