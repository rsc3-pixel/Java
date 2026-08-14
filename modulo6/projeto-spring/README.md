# API REST de Produtos: projeto do Módulo 6

Projeto Spring Boot completo e funcional, criado para o Módulo 6 do curso. Ao contrário dos outros módulos, aqui não há arquivos `.java` soltos: uma aplicação Spring exige projeto Maven com dependências baixadas.

---

## Como rodar

Você **não precisa instalar o Maven**. O projeto traz o *Maven Wrapper* (`mvnw`), que baixa a versão correta sozinho na primeira execução.

**Pré-requisito único:** JDK 17 ou superior.

```bash
# Entre na pasta do projeto
cd modulo6/projeto-spring

# Windows (PowerShell ou CMD)
.\mvnw.cmd spring-boot:run

# Linux / macOS / Git Bash
./mvnw spring-boot:run
```

A primeira execução demora alguns minutos, porque baixa o Maven e todas as dependências do Spring. As seguintes sobem em segundos.

Quando aparecer `Started CursoApiApplication`, a API está no ar em `http://localhost:8080`.

Para encerrar: `Ctrl + C`.

> [!TIP]
> Se a porta 8080 estiver ocupada, mude `server.port` em [application.properties](src/main/resources/application.properties).

---

## Testando os endpoints

### Pelo navegador (só funciona com GET)

*   http://localhost:8080/api/produtos
*   http://localhost:8080/api/produtos/1
*   http://localhost:8080/api/produtos/relatorio
*   http://localhost:8080/api/produtos/mais-caros
*   http://localhost:8080/api/produtos/buscar?categoria=Video

### Pelo console do banco H2

Acesse http://localhost:8080/h2-console e preencha:

| Campo | Valor |
| :--- | :--- |
| JDBC URL | `jdbc:h2:mem:cursodb` |
| User Name | `sa` |
| Password | *(deixe vazio)* |

Lá dá para rodar `SELECT * FROM PRODUTOS;` e ver a tabela que o Hibernate criou a partir da classe `Produto`. Vale a pena olhar: é a prova visual de que a Entity virou tabela sem você escrever SQL.

### Por linha de comando

```bash
# Listar todos
curl http://localhost:8080/api/produtos

# Buscar por id
curl http://localhost:8080/api/produtos/1

# Criar (POST)
curl -X POST http://localhost:8080/api/produtos \
     -H "Content-Type: application/json" \
     -d '{"nome":"Impressora","preco":650.00,"categoria":"Periferico","quantidadeEstoque":4}'

# Atualizar (PUT)
curl -X PUT http://localhost:8080/api/produtos/1 \
     -H "Content-Type: application/json" \
     -d '{"nome":"Mouse Gamer Pro","preco":199.90,"categoria":"Periferico","quantidadeEstoque":20}'

# Dar baixa no estoque (PATCH)
curl -X PATCH "http://localhost:8080/api/produtos/1/baixa?quantidade=5"

# Remover (DELETE)
curl -X DELETE http://localhost:8080/api/produtos/1
```

No PowerShell, use `curl.exe` (com o `.exe`) para não colidir com o alias `Invoke-WebRequest`.

---

## Testes que valem a pena fazer

Estes são os experimentos que mais ensinam. Faça cada um e observe a resposta:

| Teste | O que esperar | Por quê |
| :--- | :--- | :--- |
| `GET /api/produtos/999` | 404 com JSON de erro | o Service lança a exceção, o `TratadorDeErros` traduz |
| POST com `"preco": -10` | 400 com o campo apontado | validação da `@Positive` na Entity |
| POST com `"nome": ""` | 400 com o campo apontado | validação da `@NotBlank` |
| POST com nome já existente | 400 com mensagem de negócio | regra escrita no Service |
| PATCH pedindo baixa maior que o estoque | 400 informando o disponível | regra de negócio composta |
| `GET /faixa?min=500&max=100` | 400 | validação de coerência dos parâmetros |

Repare que **nenhum** desses erros devolve stack trace ao cliente. É exatamente o que o `@RestControllerAdvice` garante.

---

## Endpoints disponíveis

| Verbo | Rota | O que faz |
| :--- | :--- | :--- |
| GET | `/api/produtos` | lista todos |
| GET | `/api/produtos/{id}` | busca por id |
| GET | `/api/produtos/buscar?categoria=X` | filtra por categoria |
| GET | `/api/produtos/pesquisar?nome=X` | busca por trecho do nome |
| GET | `/api/produtos/faixa?min=X&max=Y` | filtra por faixa de preço |
| GET | `/api/produtos/mais-caros` | os 3 mais caros |
| GET | `/api/produtos/relatorio` | estatísticas gerais |
| POST | `/api/produtos` | cria |
| PUT | `/api/produtos/{id}` | atualiza |
| PATCH | `/api/produtos/{id}/baixa?quantidade=N` | baixa no estoque |
| DELETE | `/api/produtos/{id}` | remove |

---

## Estrutura do projeto

```
projeto-spring/
├── pom.xml                          <- dependências e configuração do build
├── mvnw / mvnw.cmd                  <- Maven Wrapper (dispensa instalar Maven)
└── src/main/
    ├── java/com/renatochong/cursoapi/
    │   ├── CursoApiApplication.java      <- ponto de entrada (@SpringBootApplication)
    │   ├── model/Produto.java            <- ENTITY: mapeia a tabela
    │   ├── repository/ProdutoRepository  <- REPOSITORY: acesso ao banco (só a interface)
    │   ├── service/ProdutoService.java   <- SERVICE: as regras de negócio
    │   ├── controller/ProdutoController  <- CONTROLLER: as rotas HTTP
    │   ├── exception/                    <- exceção customizada + tratador global
    │   └── config/CargaInicial.java      <- popula o banco ao subir
    └── resources/
        └── application.properties        <- configuração (banco, porta, JPA)
```

### O fluxo de uma requisição

```
GET /api/produtos/1
        |
        v
ProdutoController.buscarPorId(1)      <- traduz HTTP, não decide nada
        |
        v
ProdutoService.buscarPorId(1)         <- regra: se não existir, lança exceção
        |
        v
ProdutoRepository.findById(1)         <- Optional<Produto>, SQL gerado pelo Spring
        |
        v
      Banco H2
```

Se o produto não existir, a exceção sobe do Service até o `TratadorDeErros`, que a converte em 404. O Controller não precisa de nenhum `if` para isso.

---

## A ordem certa de ler o código

1.  [Produto.java](src/main/java/com/renatochong/cursoapi/model/Produto.java) — o que é uma Entity
2.  [ProdutoRepository.java](src/main/java/com/renatochong/cursoapi/repository/ProdutoRepository.java) — uma interface sem implementação que funciona
3.  [ProdutoService.java](src/main/java/com/renatochong/cursoapi/service/ProdutoService.java) — onde as regras vivem
4.  [ProdutoController.java](src/main/java/com/renatochong/cursoapi/controller/ProdutoController.java) — como o HTTP entra
5.  [TratadorDeErros.java](src/main/java/com/renatochong/cursoapi/exception/TratadorDeErros.java) — como o erro sai

---

## Migrando para PostgreSQL

O H2 foi escolhido para o curso porque roda sem instalar nada, mas os dados somem ao parar a aplicação. Para usar Postgres de verdade:

1.  Descomente a dependência do `postgresql` no [pom.xml](pom.xml).
2.  Em [application.properties](src/main/resources/application.properties), comente o bloco do H2 e descomente o do Postgres.
3.  Troque `spring.jpa.hibernate.ddl-auto` para `update`.

**Nenhuma linha de código Java muda.** Essa independência em relação ao banco é a principal vantagem prática do JPA, e é um bom argumento para usar numa entrevista.

---

## Gerando o `.jar` executável

```bash
./mvnw clean package
java -jar target/curso-api-1.0.0.jar
```

O `.jar` gerado contém a aplicação, as dependências e o servidor Tomcat embutido. É esse arquivo único que se coloca num contêiner Docker ou num servidor. Foi essa característica que acabou com a era de "instalar o servidor de aplicação antes de fazer o deploy".
