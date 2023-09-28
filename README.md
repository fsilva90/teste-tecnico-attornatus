# Descrição

Uma API para gerenciar pessoas e seus endereços.

# Tecnologias utilizadas

1. Java 17
2. Maven
3. Spring Boot
4. Banco de dados H2

# Funcionalidades

* Cadastrar uma nova pessoa.
* Editar uma pessoa.
* Listar pessoas.
* Consultar uma determinada pessoa.
* Cadastrar o endereço para uma pessoa.
* Editar o endereço principal de uma pessoa.
* Buscar todos os endereços de uma pessoa.
* Buscar o endereço principal de uma pessoa.


# Execução

Para executar a aplicação localmente siga os seguintes passos:

* Navegue até o diretório raiz do projeto (onde se encontra o arquivo pom).
* Abra um terminal e execute o comando `mvn clean package spring-boot:run`.

A partir deste ponto, todas as rotas da aplicação estarão disponíveis para serem utilizadas.

## Rotas

1. POST `http://localhost:8080/pessoas` -> Cadastrar uma nova pessoa.
2. PUT  `http://localhost:8080/pessoas` -> Editar uma pessoa.
3. GET  `http://localhost:8080/pessoas` -> Listar pessoas.
4. GET  `http://localhost:8080/pessoas/{idPessoa}` -> Consultar uma determinada pessoa.
5. POST `http://localhost:8080/enderecos` -> Cadastrar o endereço para uma pessoa.
6. PUT  `http://localhost:8080/enderecos` -> Editar o endereço principal de uma pessoa.
7. GET  `http://localhost:8080/enderecos/listar-por-pessoa/{idPessoa}` -> Buscar todos os endereços de uma pessoa.
8. GET  `http://localhost:8080/enderecos/principal-por-pessoa/{idPessoa}` -> Buscar o endereço principal de uma pessoa.

# Testes unitários

Para executar os testes unitários, siga os seguintes passos:

* Navegue até o diretório raiz do projeto (onde se encontra o arquivo pom).
* Abra um terminal e execute o comando `mvn test`.

# Acesso ao console do H2

Para acessar o console do banco H2 siga os seguintes passos:
* acesse a `http://localhost:8080/h2-console`.
* No campo <strong>JDBC URL</strong> insira o valor `jdbc:h2:mem:teste_tecnico_db?createDatabaseIfNotExist=true`.
* No campo <strong>password</strong> insira o valor `admin`.

# Documentação swagger

Para ler a documentação swagger da aplicação localmente acesse `http://localhost:8080/swagger-ui/index.html`.


# Autor

[<br><sub>Felipe Silva</sub>](https://github.com/fsilva90)

