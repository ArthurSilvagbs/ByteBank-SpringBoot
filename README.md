# ByteBank - Spring Boot API

> ⚠️ **Status do Projeto**: Em desenvolvimento ativo. Funcionalidades principais implementadas; melhorias sendo adicionadas gradualmente.

## Descrição

O **ByteBank** é uma API RESTful desenvolvida com **Spring Boot 4** para gerenciar operações bancárias essenciais. O sistema permite o cadastro de clientes (Pessoa Física e Jurídica), gerenciamento de contas bancárias e a realização de movimentações financeiras, com validações rigorosas, tratamento de exceções centralizado e documentação interativa via Swagger.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Security**: Autenticação HTTP Basic configurada
- **Spring Data JPA**: Persistência de dados
- **PostgreSQL**: Banco de dados relacional
- **springdoc-openapi 2.8.6**: Documentação interativa (Swagger UI)
- **MapStruct**: Mapeamento entre entidades e DTOs
- **Lombok**: Redução de boilerplate
- **Spring Validation**: Validação de dados de entrada
- **Maven**: Gerenciamento de dependências e build

## Arquitetura

O projeto segue arquitetura em camadas:

| Camada | Responsabilidade |
|---|---|
| **Controller** | Recebe requisições HTTP e delega para a camada de serviço |
| **Service** | Lógica de negócio e regras do domínio |
| **Repository** | Comunicação com o banco via JPA |
| **Model/Entity** | Representação das tabelas do banco |
| **DTO** | Transferência de dados entre camadas (sem expor entidades) |
| **Mapper** | Conversão entre entidades e DTOs via MapStruct |
| **GlobalExceptionHandler** | Tratamento centralizado de exceções com respostas padronizadas |

## Endpoints

### Clientes (`/clientes`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/clientes/pf` | Cadastrar Pessoa Física |
| `POST` | `/clientes/pj` | Cadastrar Pessoa Jurídica |
| `GET` | `/clientes/{id}` | Buscar cliente por ID |
| `PUT` | `/clientes/{id}` | Atualizar dados do cliente |
| `DELETE` | `/clientes/{id}` | Remover cliente |

### Contas (`/contas`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/contas` | Criar conta bancária |
| `GET` | `/contas/{id}` | Buscar conta por ID |
| `PUT` | `/contas/{id}` | Atualizar saldo da conta |
| `DELETE` | `/contas/{id}` | Encerrar conta |

### Transações (`/transacoes`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/transacoes/deposito` | Efetuar depósito |
| `POST` | `/transacoes/saque` | Efetuar saque |
| `POST` | `/transacoes/transferencia` | Efetuar transferência entre contas |

## Exceções Tratadas

O `GlobalExceptionHandler` captura e formata os seguintes erros:

| Exceção | HTTP |
|---|---|
| `RecursoNaoEncontradoException` | 404 Not Found |
| `RegistroDuplicadoException` | 409 Conflict |
| `SaldoInsuficienteException` | 422 Unprocessable Entity |
| `OperacaoNaoPermitidaException` | 422 Unprocessable Entity |
| `ValorInvalidoException` | 400 Bad Request |
| `MethodArgumentNotValidException` | 400 Bad Request |

## Documentação (Swagger UI)

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

## Testes

Testes unitários implementados para as camadas de serviço:

- `ClienteServiceTest`
- `ContaServiceTest`
- `TransacaoServiceTest`

## Como Executar

### Pré-requisitos

- JDK 21
- Maven
- PostgreSQL em execução

### Passos

1. **Clone o repositório:**

    ```bash
    git clone https://github.com/ArthurSilvagbs/bytebank-springboot.git
    cd ByteBank-SpringBoot
    ```

2. **Configure o banco de dados** em `src/main/resources/application.yml`:

    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/seu_banco
        username: seu_usuario
        password: sua_senha
    ```

3. **Execute a aplicação:**

    ```bash
    ./mvnw spring-boot:run
    ```

## Próximos Passos

- [ ] Implementação de JWT para substituir HTTP Basic
- [ ] Testes de integração
- [ ] Paginação nos endpoints de listagem
