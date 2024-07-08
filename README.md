# springboot-estacionamento
Projeto para o gerenciamento de vagas em um estacionamento, desenvolvido baseado no curso "API REST e Spring Boot: Aprenda do Zero e na Prática".

## Como baixar
Primeiro voce precisa clonar o repositorio:
```bash
# Clone o repositório
$ git clone https://github.com/biancabalabenute/springboot-estacionamento.git
```
## Executando o projeto:
- Estou usando o PostgreSQL na porta padrão 5432.
- Crie um banco de dados com o nome demo_park.
- Atualize as credenciais de acesso ao banco de dados conforme necessário:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/demo_park
spring.datasource.username=postgres
spring.datasource.password=********
```
2 Criação das Tabelas:
- Execute o projeto para que as tabelas sejam criadas automaticamente.


3 - Testando as Requisições:
- Você pode começar a testar as requisições via Postman ou Swagger em http://localhost:8080/swagger-ui/index.html.

## IMPORTANTE
- Você deve criar primeiro um usuário, por padrão todos os usuários são criado como 'CLIENTE', para ter acesso a algumas funções é necessário o perfil 'ADMIN'.
- Isso pode ser resolvido com a alteração no banco de dados:
```bash
UPDATE usuarios
SET role = 'ADMIN'
WHERE id = 1;
```
- Você deve se autenticar com o seu perfil de usuário, seja ele 'CLIENTE' ou 'ADMIN' para ter acesso a todas as demais funcionalidades, nem todas as funcionalidades de 'CLIENTE' são possíveis de realizar como 'ADMIN' e vice-versa.

## Tecnologias para desenvolver este projeto:
- Java
- Spring Boot
- PostgreSQL (Banco de dados oficial)
- H2 (Banco de dados para testes)
- Swagger (Documentação)
- Spring Security (Segurança da aplicação)
- Postman (Teste de requisições)
- JasperReports (Geração de relatórios em PDF)
- Teste de integração realizados

## Pré-requisitos
- Java JDK 17

## Ambiente de desenvolvimento usado
Este projeto foi desenvolvido utilizando as seguintes ferramentas:

- **IntelliJ IDEA**: Uma poderosa IDE para desenvolvimento Java, utilizada para escrever, depurar e executar o código do projeto.
- **Maven**: Ferramenta de automação de compilação utilizada para gerenciar as dependências do projeto e realizar builds.
- **PostgreSQL**: Banco de dados relacional utilizado para persistência de dados. Recomenda-se a utilização do pgAdmin ou de outras ferramentas de administração para gerenciar o banco de dados.
- **Postman**: Uma plataforma de colaboração para desenvolvimento de APIs. Utilizado para testar endpoints e realizar requisições HTTP durante o desenvolvimento da aplicação.
- **Jaspersoft Studio**: Ferramenta de design visual utilizada para criar templates e layouts de relatórios. Integra-se com o JasperReports para gerar relatórios a partir dos dados do banco de dados. Suporta a criação de relatórios em diversos formatos, como PDF, HTML e XLS.
