-- Inserir roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Inserir o usuário admin com a senha codificada em BCrypt
INSERT INTO users (username, email, name, password, enabled)
VALUES ('admin', 'admin@example.com', 'admin', '$2a$10$EqPFNQ5DYsfNR3EUXxaHp.Ys94a6ZLB5mwlmJBrDiOLJN5JCGkbZG', true);

-- Associar o usuário admin à role 'ROLE_ADMIN' (role_id = 2)
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2);  -- Aqui, '1' é o ID do usuário admin e '2' é o ID da role 'ROLE_ADMIN'
