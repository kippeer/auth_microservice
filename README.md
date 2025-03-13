# Auth Microservice API

## 📌 Sobre o Projeto

Este microserviço de autenticação fornece funcionalidades de registro, login e gerenciamento de usuários utilizando **Spring Boot** e **Spring Security**. Ele foi desenvolvido com **JWT** para autenticação segura e inclui controle de acesso baseado em funções (**RBAC** - Role-Based Access Control).

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Lombok**
- **Docker**

---

## ⚙️ Configuração do Projeto

### 🔧 Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

- **Java 17** ou superior
- **Maven**
- **Docker** (opcional, para rodar o banco de dados PostgreSQL)

### 📦 Configuração do Banco de Dados

Caso utilize o PostgreSQL localmente, crie um banco de dados chamado `auth_db` e configure o arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Se quiser rodar o banco via **Docker**, utilize o comando:

```sh
docker run --name auth-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=auth_db -p 5432:5432 -d postgres
```

---

## ▶️ Como Rodar o Projeto

### 📥 Clonar o repositório

```sh
git clone https://github.com/seu-usuario/auth-microservice.git
cd auth-microservice
```

### 🚀 Rodar a aplicação

```sh
./mvnw spring-boot:run
```

Ou se estiver usando **Maven** instalado:

```sh
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8083`

---

## 📌 Endpoints Disponíveis

### 🔐 Autenticação

| Método | Endpoint | Descrição |
|--------|---------|------------|
| `POST` | `/api/auth/login` | Autentica um usuário e retorna um token JWT |
| `POST` | `/api/auth/register` | Registra um novo usuário |

### 👤 Gerenciamento de Usuários

| Método | Endpoint | Descrição |
|--------|---------|------------|
| `GET` | `/api/users/me` | Obtém os dados do usuário autenticado |
| `GET` | `/api/users/{id}` | Obtém um usuário por ID (Apenas ADMIN ou dono do perfil) |
| `GET` | `/api/users` | Obtém todos os usuários (Apenas ADMIN) |
| `PUT` | `/api/users/{id}` | Atualiza um usuário (Apenas ADMIN ou dono do perfil) |
| `DELETE` | `/api/users/{id}` | Remove um usuário (Apenas ADMIN ou dono do perfil) |
| `POST` | `/api/users/{id}/change-password` | Altera a senha do usuário autenticado |

---

## 🔑 Autenticação e Segurança

- A autenticação é feita via **JWT**.
- Para acessar endpoints protegidos, adicione o token no cabeçalho:

```sh
Authorization: Bearer SEU_TOKEN_JWT
```

---

## 🛠️ Testando a API

Você pode testar os endpoints utilizando:
- **Postman**
- **Insomnia**
- `curl` via terminal:

Exemplo de login:

```sh
curl -X POST "http://localhost:8083/api/auth/login" \
-H "Content-Type: application/json" \
-d '{"username": "admin", "password": "123456"}'
```

---

## 🐳 Rodando com Docker

Se quiser rodar a aplicação com Docker, utilize:

```sh
docker build -t auth-microservice .
docker run -p 8083:8083 auth-microservice
```

---

## 📜 Licença

Este projeto está sob a licença MIT. Sinta-se livre para usá-lo e contribuir! 🚀

---

## 💡 Contribuição

Se quiser contribuir:
1. Fork este repositório 🍴
2. Crie uma branch com sua feature (`git checkout -b minha-feature`)
3. Commit suas alterações (`git commit -m 'Adicionando nova funcionalidade'`)
4. Envie um push para a branch (`git push origin minha-feature`)
5. Abra um **Pull Request** 🚀

---

👨‍💻 Desenvolvido por [Seu Nome](https://github.com/seu-usuario)

