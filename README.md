# ByteBank - Spring Boot API

> ⚠️ **Status do Projeto**: Este projeto está em fase de desenvolvimento e ainda não foi finalizado. Novas funcionalidades e melhorias estão sendo implementadas gradualmente.

## Descrição do Projeto

O **ByteBank** é uma API RESTful robusta desenvolvida com **Spring Boot 3** para gerenciar operações bancárias essenciais. O sistema permite o gerenciamento de clientes (Pessoas Físicas e Jurídicas), contas bancárias e a realização de movimentações financeiras, garantindo a integridade dos dados através de validações rigorosas e tratamento de exceções personalizado.

## Tecnologias Utilizadas

*   **Java 21**: Versão mais recente do Java com suporte a recursos modernos.
*   **Spring Boot 4.0.2**: Framework principal para criação da API.
*   **Spring Data JPA**: Para persistência de dados e integração com o banco de dados.
*   **PostgreSQL**: Banco de dados relacional utilizado para armazenamento persistente.
*   **Lombok**: Biblioteca para reduzir o código boilerplate (getters, setters, construtores).
*   **MapStruct**: Para mapeamento eficiente entre entidades e DTOs.
*   **Spring Validation**: Para validação de dados de entrada nos endpoints.
*   **Maven**: Gerenciador de dependências e automação de build.

## Arquitetura e Organização

O projeto segue os padrões de arquitetura em camadas, promovendo a separação de responsabilidades:

*   **Controller**: Endpoints da API que recebem as requisições HTTP.
*   **Service**: Camada de lógica de negócios e regras do sistema.
*   **Repository**: Interface de comunicação com o banco de dados via JPA.
*   **Model/Entity**: Representação das tabelas do banco de dados.
*   **DTO (Data Transfer Object)**: Objetos para transferência de dados entre as camadas, evitando a exposição direta das entidades.
*   **Mapper**: Conversão entre Entidades e DTOs utilizando MapStruct.
*   **Validator**: Lógica de validação customizada para garantir a consistência das operações.

## Funcionalidades (Em Desenvolvimento)

*   **Gestão de Clientes**:
    *   Suporte para Pessoa Física (CPF) e Pessoa Jurídica (CNPJ).
    *   Cadastro, atualização e consulta de clientes.
*   **Gestão de Contas**:
    *   Criação de contas vinculadas a clientes.
    *   Controle de saldo e validação de status da conta.
*   **Movimentações Financeiras**:
    *   Registro de depósitos, saques e transferências.
    *   Validação de saldo insuficiente e operações não permitidas.
*   **Tratamento de Erros**:
    *   Handler global para capturar e formatar exceções como `SaldoInsuficienteException` e `RegistroDuplicadoException`.

## Como Executar (Ambiente de Desenvolvimento)

### Pré-requisitos

*   JDK 21 instalado.
*   Maven instalado.
*   PostgreSQL configurado e em execução.

### Passos

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/ArthurSilvagbs/bytebank-springboot.git
    cd ByteBank-SpringBoot
    ```

2.  **Configure o banco de dados:**

    Edite o arquivo `src/main/resources/application.yml` com as credenciais do seu PostgreSQL:

    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/seu_banco
        username: seu_usuario
        password: sua_senha
    ```

3.  **Execute a aplicação:**

    ```bash
    ./mvnw spring-boot:run
    ```

## Próximos Passos

*   [ ] Implementação de testes unitários e de integração.
*   [ ] Adição de documentação Swagger/OpenAPI.
*   [ ] Implementação de autenticação e autorização com Spring Security.
*   [ ] Finalização dos mappers para todos os tipos de clientes.

---

*Gerado por Manus AI em 27 de fevereiro de 2026.*
