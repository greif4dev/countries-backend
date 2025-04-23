# Documentação Backend - Sistema de Países

##  Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security
- JWT (token)
- Spring Data JPA + H2 Database
- Lombok
- Swagger para documentação de API

---

##  Autenticação e Segurança

### Fluxo:

1. Front envia credenciais para `POST /usuario/autenticar`
2. Backend valida e gera token JWT
3. Retorna objeto `UsuarioAutenticado` com:
    - nome, perfil (admin/convidado), token, autenticado: true
4. Token é usado nas requisições subsequentes via `Authorization: Bearer <token>`

### Proteção de rotas:

- Endpoints são protegidos por:
    - Interceptor de token JWT
    - Ou por `@PreAuthorize` / `@RolesAllowed` / `@Secured`
- O token é validado e o usuário é associado ao contexto de segurança

---

## Endpoints da API REST

###  `/usuario`

- `POST /usuario/autenticar` - Realiza login e retorna token
- `POST /usuario/renovar` - (futuro) Renova token JWT

###  `/pais`

- `GET /pais/listar` - Lista todos os países
- `POST /pais/salvar` - Cria ou atualiza um país
- `DELETE /pais/{id}` - Exclui um país por ID

---

##  Estrutura das Entidades

### `Usuario`
```java
@Entity
public class Usuario {
    private Long id;
    private String login;
    private String senha; // criptografada com BCrypt
    private String nome;
    private boolean administrador;
}
```

### `Pais`
```java
@Entity
public class Pais {
    private Long id;
    private String nome;
    private String sigla;
    private String gentilico;
}
```

---

## ⚖ Segurança com Spring Security

- `SecurityConfig` configura o CORS, permite OPTIONS e desabilita CSRF
- Filtro JWT intercepta as requisições e autentica via token
- Se token expirado/inválido: retorna `401 Unauthorized`
---

##  Banco de Dados (H2)

- Em modo embutido, reinicia a cada execução
- Inicializado via `data.sql`
- Acesso via navegador:
    - URL: `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:testdb`

---

## Swagger (OpenAPI)

- Disponível em: `http://localhost:8080/swagger-ui.html`
- Documenta todos os endpoints e permite testar interativamente
- Inclui suporte a envio de JWT no header (Authorization)

---

##  Estrutura de Pacotes (resumo)

```
src/main/java/com/example/countries/
├── config/       -> seguranca, CORS, JWT
├── controller/   -> endpoints REST
├── entity/       -> entidades JPA
├── repository/   -> acesso a dados
├── service/      -> regras de negócio
└── CountriesApplication.java
```
