# Módulo 6: Ecossistema Profissional e Spring Boot

Este é o módulo que transforma "sei Java" em "sou desenvolvedor Java". Tudo até aqui rodava no terminal e morria ao fechar o programa. Agora o objetivo é outro: **uma aplicação que fica no ar, conversa com um banco de dados e responde a requisições HTTP**.

> [!NOTE]
> **Este módulo é diferente dos anteriores.** Os arquivos `.java` soltos não bastam: Spring Boot exige um projeto Maven com dependências baixadas da internet. A pasta [projeto-spring/](./projeto-spring/) contém a estrutura completa e funcional, com instruções de execução no [README](./projeto-spring/README.md).

---

## 1. Maven: o gerenciador de dependências

Até agora você compilava com `javac Arquivo.java`. Isso funciona para um arquivo. Um projeto real tem centenas de classes e depende de dezenas de bibliotecas externas, cada uma com suas próprias dependências.

**Maven** resolve três problemas de uma vez:

1.  **Dependências:** você declara o que precisa; ele baixa a biblioteca e tudo de que ela depende.
2.  **Build padronizado:** `mvn package` compila, roda os testes e gera o `.jar`, em qualquer máquina.
3.  **Estrutura convencionada:** todo projeto Maven tem a mesma organização de pastas, então qualquer desenvolvedor se localiza imediatamente.

### A estrutura padrão

```
meu-projeto/
├── pom.xml                          <- o coração do projeto
└── src/
    ├── main/
    │   ├── java/                    <- o código-fonte
    │   │   └── com/exemplo/app/
    │   └── resources/               <- configurações, SQL, arquivos estáticos
    │       └── application.properties
    └── test/
        └── java/                    <- os testes automatizados
```

### O `pom.xml`

*POM* significa *Project Object Model*. É um XML que descreve o projeto:

```xml
<project>
    <!-- Identificação única do seu projeto no mundo -->
    <groupId>com.renatochong</groupId>       <!-- organização, domínio invertido -->
    <artifactId>curso-api</artifactId>       <!-- nome do projeto -->
    <version>1.0.0</version>                 <!-- versão -->

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

Cada dependência é identificada por três coordenadas: `groupId`, `artifactId` e `version`. Com isso, o Maven busca a biblioteca no repositório central e a guarda em `~/.m2/repository` na sua máquina.

### Comandos essenciais

| Comando | O que faz |
| :--- | :--- |
| `mvn clean` | apaga a pasta `target/` (o resultado do build anterior) |
| `mvn compile` | compila o código-fonte |
| `mvn test` | roda os testes automatizados |
| `mvn package` | gera o `.jar` executável em `target/` |
| `mvn spring-boot:run` | sobe a aplicação Spring diretamente |

> [!TIP]
> **Gradle** é a alternativa ao Maven, com sintaxe mais enxuta (Groovy ou Kotlin em vez de XML) e builds geralmente mais rápidos. Domina o mundo Android. No back-end corporativo Java, Maven ainda é maioria, e é por isso que começamos por ele.

---

## 2. JDBC: falando com o banco na unha

**JDBC** (*Java Database Connectivity*) é a API padrão do Java para bancos relacionais. Você raramente vai escrever JDBC puro no dia a dia, mas precisa entendê-lo, porque é o que roda por baixo do Spring Data.

```java
import java.sql.*;

String url = "jdbc:postgresql://localhost:5432/meubanco";
String usuario = "postgres";
String senha = "senha";

// try-with-resources: fecha conexão, statement e resultset automaticamente
try (Connection conn = DriverManager.getConnection(url, usuario, senha);
     PreparedStatement stmt = conn.prepareStatement("SELECT id, nome, preco FROM produtos WHERE preco > ?")) {

    stmt.setDouble(1, 100.0); // preenche o primeiro '?'

    try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) { // avança linha a linha, devolve false ao terminar
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            double preco = rs.getDouble("preco");
            System.out.printf("%d - %s: R$ %.2f%n", id, nome, preco);
        }
    }

} catch (SQLException e) {
    System.out.println("Erro no banco: " + e.getMessage());
}
```

### As peças do JDBC

| Classe | Papel |
| :--- | :--- |
| `Connection` | a conexão aberta com o banco |
| `Statement` | executa SQL fixo (evite: veja o aviso abaixo) |
| `PreparedStatement` | executa SQL com parâmetros `?`, pré-compilado |
| `ResultSet` | o resultado da consulta, percorrido com `next()` |

> [!WARNING]
> **SQL Injection: sempre use `PreparedStatement`.** Concatenar entrada do usuário direto no SQL é a vulnerabilidade mais explorada da web:
> ```java
> // NUNCA faça isso
> String sql = "SELECT * FROM usuarios WHERE login = '" + login + "'";
> // Se o usuário digitar:  ' OR '1'='1
> // O SQL vira:  SELECT * FROM usuarios WHERE login = '' OR '1'='1'
> // e devolve TODOS os usuários do sistema.
> ```
> Com `PreparedStatement` e `?`, o valor é enviado separado do comando e nunca é interpretado como SQL.

### Para escrita, use `executeUpdate`

```java
try (PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO produtos (nome, preco) VALUES (?, ?)")) {
    stmt.setString(1, "Mouse");
    stmt.setDouble(2, 150.00);
    int linhasAfetadas = stmt.executeUpdate(); // devolve quantas linhas mudaram
}
```

---

## 3. O que é o Spring, afinal

Spring é um **framework**, e a diferença em relação a uma biblioteca é a direção do controle:

*   **Biblioteca:** o seu código chama o código dela.
*   **Framework:** o código dele chama o seu. Isso se chama **Inversão de Controle** (IoC).

O Spring cuida de tudo que é repetitivo em uma aplicação corporativa (servidor web, conexões, transações, segurança) para você escrever apenas a regra de negócio.

### Injeção de Dependência: a ideia central

Sem injeção de dependência, cada classe cria o que precisa:

```java
public class ProdutoService {
    // O Service está AMARRADO a esta implementação específica
    private ProdutoRepository repository = new ProdutoRepositoryPostgres();
}
```

Trocar Postgres por MySQL exige mexer no Service. Testar exige um banco real rodando.

Com injeção de dependência, a classe **declara** o que precisa e recebe pronto:

```java
@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    // O Spring vê o construtor e ENTREGA a implementação registrada
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }
}
```

Agora o Service depende da **interface**, não da implementação. Trocar o banco não o afeta, e testar é passar um repositório falso pelo construtor.

O objeto que o Spring cria e gerencia chama-se **Bean**, e vive dentro do **contêiner IoC** (o *ApplicationContext*).

> [!TIP]
> **Injeção por construtor é a forma recomendada.** Existe também `@Autowired` em cima do atributo, comum em código antigo, mas ela impede o atributo de ser `final`, esconde as dependências e dificulta o teste. Se o construtor tem oito parâmetros, isso é um sinal legítimo de que a classe faz coisas demais.

---

## 4. Spring Boot e o fim da configuração

Spring puro exigia arquivos XML enormes. **Spring Boot** trouxe três ideias:

1.  **Autoconfiguração:** ele detecta o que está no classpath e configura sozinho. Achou o driver do Postgres e a URL do banco? Cria a conexão.
2.  **Starters:** dependências agrupadas. `spring-boot-starter-web` traz servidor embutido, JSON e MVC de uma vez.
3.  **Servidor embutido:** o Tomcat vem dentro do `.jar`. Não existe mais "instalar servidor de aplicação".

### A classe principal

```java
@SpringBootApplication // combina três anotações: configuração, autoconfiguração e varredura de componentes
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
```

Esse `main` sobe a aplicação inteira, com servidor web na porta 8080.

### `application.properties`

```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/meubanco
spring.datasource.username=postgres
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 5. A arquitetura em camadas

Este é o conceito mais importante do módulo, e você já o praticou no Módulo 4 sem saber, ao separar domínio, persistência e menu.

```
   Requisição HTTP
         |
   [ Controller ]   <- recebe a requisição, devolve a resposta. NÃO tem regra de negócio.
         |
   [  Service   ]   <- as REGRAS DE NEGÓCIO. Não sabe o que é HTTP nem SQL.
         |
   [ Repository ]   <- acesso ao banco. Não tem regra alguma.
         |
   [  Entity    ]   <- o objeto que representa a tabela
         |
      Banco de dados
```

A regra de ouro: **cada camada só conhece a de baixo**. O Controller chama o Service, que chama o Repository. Nunca o contrário, e nunca pulando etapas.

Por que isso importa: quando a regra de negócio não sabe o que é HTTP, a mesma regra serve para uma API REST, um app mobile e um job agendado, sem duplicação. É a mesma ideia da separação que você fez no Módulo 4, agora formalizada.

---

## 6. A Entity: mapeando a tabela

**JPA** (*Java Persistence API*) é a especificação de mapeamento objeto-relacional; **Hibernate** é a implementação usada por padrão. Na prática: você escreve classes Java, e ele gera o SQL.

```java
import jakarta.persistence.*;

@Entity                          // esta classe representa uma tabela
@Table(name = "produtos")        // opcional: o nome da tabela
public class Produto {

    @Id                                                   // chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // o banco gera o valor
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    private String categoria;

    // JPA EXIGE um construtor vazio para conseguir instanciar via reflexão
    public Produto() {
    }

    public Produto(String nome, Double preco, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    // getters e setters...
}
```

### Relacionamentos

```java
@Entity
public class Pedido {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne                          // muitos pedidos para um cliente
    @JoinColumn(name = "cliente_id")    // a coluna de chave estrangeira
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();
}
```

---

## 7. O Repository: persistência sem SQL

Aqui está a mágica que mais impressiona quem vem do JDBC:

```java
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // é só isso. Nenhuma implementação necessária.
}
```

Ao estender `JpaRepository<Entidade, TipoDoId>`, você ganha prontos:

```java
repository.save(produto);           // insere ou atualiza
repository.findById(1L);            // devolve Optional<Produto>
repository.findAll();               // devolve List<Produto>
repository.deleteById(1L);
repository.count();
repository.existsById(1L);
```

O Spring gera a implementação em tempo de execução.

### Query Methods: consultas pelo nome do método

```java
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // O Spring LÊ o nome do método e gera o SQL sozinho
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByPrecoGreaterThan(Double preco);
    List<Produto> findByNomeContainingIgnoreCase(String trecho);
    List<Produto> findByCategoriaAndPrecoLessThan(String cat, Double preco);
    List<Produto> findByCategoriaOrderByPrecoDesc(String categoria);
    Optional<Produto> findFirstByOrderByPrecoDesc();

    // Quando o nome ficaria absurdo, escreva a consulta explicitamente
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :min AND :max")
    List<Produto> buscarNaFaixa(@Param("min") Double min, @Param("max") Double max);
}
```

Palavras reconhecidas: `findBy`, `And`, `Or`, `Between`, `LessThan`, `GreaterThan`, `Like`, `Containing`, `IgnoreCase`, `OrderBy`, `Top`, `First`.

> [!NOTE]
> Se você errar o nome de um campo no método (`findByPreco` numa entidade cujo atributo é `valor`), a aplicação **falha ao subir**, não em produção. Erro cedo é erro barato.

---

## 8. O Service: onde vive a regra de negócio

```java
@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id)); // Optional do Módulo 5
    }

    public Produto criar(Produto produto) {
        // A REGRA DE NEGÓCIO mora aqui, não no Controller
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo.");
        }
        return repository.save(produto);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }
}
```

Repare que este código não menciona HTTP, status 404 nem JSON. Ele é puro Java com regra de negócio, e é isso que o torna testável e reutilizável.

---

## 9. O Controller: a porta de entrada HTTP

```java
@RestController                       // @Controller + @ResponseBody: devolve JSON
@RequestMapping("/api/produtos")      // prefixo de todas as rotas desta classe
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    // GET /api/produtos
    @GetMapping
    public List<Produto> listar() {
        return service.listarTodos(); // o Spring converte a lista em JSON sozinho
    }

    // GET /api/produtos/1
    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // GET /api/produtos/buscar?categoria=Periferico
    @GetMapping("/buscar")
    public List<Produto> porCategoria(@RequestParam String categoria) {
        return service.buscarPorCategoria(categoria);
    }

    // POST /api/produtos  (dados no corpo da requisição)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)   // devolve 201 em vez de 200
    public Produto criar(@RequestBody Produto produto) {
        return service.criar(produto);
    }

    // PUT /api/produtos/1
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        return service.atualizar(id, produto);
    }

    // DELETE /api/produtos/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204: sucesso sem corpo
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
```

### As três formas de receber dados

| Anotação | Vem de onde | Exemplo |
| :--- | :--- | :--- |
| `@PathVariable` | do caminho da URL | `/produtos/**5**` |
| `@RequestParam` | da query string | `/produtos?categoria=**TI**` |
| `@RequestBody` | do corpo da requisição | JSON enviado no POST |

### Verbos HTTP e status

| Verbo | Uso | Status de sucesso |
| :--- | :--- | :--- |
| GET | consultar | 200 OK |
| POST | criar | 201 Created |
| PUT | atualizar por inteiro | 200 OK |
| PATCH | atualizar parcialmente | 200 OK |
| DELETE | remover | 204 No Content |

| Faixa | Significado |
| :--- | :--- |
| 2xx | sucesso |
| 4xx | erro do cliente (400 dados inválidos, 401 sem autenticação, 403 sem permissão, 404 não encontrado) |
| 5xx | erro do servidor (500 exceção não tratada) |

---

## 10. Tratamento global de erros

Sem tratamento, qualquer exceção vira um 500 com stack trace exposto ao cliente. A solução do Spring aproveita tudo o que você aprendeu no Módulo 4:

```java
@RestControllerAdvice   // intercepta exceções de TODOS os controllers
public class TratadorDeErros {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarNaoEncontrado(ProdutoNaoEncontradoException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)   // 404
                .body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarDadosInvalidos(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(Map.of("erro", e.getMessage()));
    }
}
```

Agora o Service lança a exceção de negócio, sem saber o que é HTTP, e o `RestControllerAdvice` traduz cada tipo para o status correto. As camadas permanecem separadas.

---

## 11. As anotações que você precisa reconhecer

| Anotação | Onde | Para quê |
| :--- | :--- | :--- |
| `@SpringBootApplication` | classe main | inicia tudo |
| `@RestController` | classe | controller que devolve JSON |
| `@RequestMapping` | classe/método | define a rota base |
| `@GetMapping` e afins | método | mapeia o verbo HTTP |
| `@PathVariable` | parâmetro | valor vindo da URL |
| `@RequestParam` | parâmetro | valor da query string |
| `@RequestBody` | parâmetro | JSON do corpo |
| `@Service` | classe | registra um Bean de regra de negócio |
| `@Repository` | interface | registra um Bean de acesso a dados |
| `@Component` | classe | Bean genérico |
| `@Entity` | classe | mapeia para tabela |
| `@Id` | atributo | chave primária |
| `@Transactional` | método | executa dentro de uma transação |
| `@RestControllerAdvice` | classe | tratamento global de exceções |

---

## 12. Próximos passos depois deste módulo

O curso termina aqui, mas a formação continua. Na ordem de prioridade para uma vaga de Pleno:

1.  **Testes automatizados:** JUnit 5 e Mockito. É o que mais diferencia candidatos em entrevista.
2.  **Spring Security e JWT:** autenticação em APIs REST.
3.  **DTOs e Bean Validation:** parar de expor a Entity direto no Controller e validar com `@Valid`.
4.  **Docker:** empacotar a aplicação e o banco em contêineres (você já usa isso no Estrategico e no Flux).
5.  **Flyway ou Liquibase:** versionamento de banco, porque `ddl-auto=update` não serve para produção.
6.  **Documentação com Swagger/OpenAPI.**

> [!IMPORTANT]
> **O conselho mais valioso do módulo:** não tente decorar anotações. Entenda as camadas e o fluxo da requisição. Numa entrevista, "por que o Service não conhece HTTP?" vale muito mais do que saber de cor a diferença entre `@Component` e `@Service`. Anotação se consulta na documentação; arquitetura, não.
