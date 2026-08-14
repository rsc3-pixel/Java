# Lista de Exercícios: Módulo 6 - Spring Boot (50 Questões)

Estes exercícios são **de projeto**, não de arquivo solto. Trabalhe dentro de [projeto-spring/](./projeto-spring/) ou crie projetos novos pelo [Spring Initializr](https://start.spring.io).

> [!TIP]
> Deixe `spring.jpa.show-sql=true` ligado enquanto estuda. Ver o SQL que o Hibernate gera para cada método do repositório é a forma mais rápida de entender o que está acontecendo por baixo.

---

## Grupo A: Maven e Estrutura (1 a 8)

1. **Primeiro Projeto:** Gere um projeto no [Spring Initializr](https://start.spring.io) com as dependências Web, JPA e H2. Rode e confirme que sobe na porta 8080.
2. **Anatomia do pom.xml:** Abra o `pom.xml` do projeto-spring e escreva, num comentário, o que cada uma das cinco dependências faz.
3. **Mudando a Porta:** Altere `server.port` para 9090 e comprove que a API responde no novo endereço.
4. **Comandos Maven:** Execute `clean`, `compile`, `test` e `package` em sequência. Descreva o que cada um produziu na pasta `target/`.
5. **Jar Executável:** Gere o `.jar` com `package` e rode com `java -jar`. Comprove que funciona sem o Maven.
6. **Adicionando Dependência:** Adicione `spring-boot-starter-actuator` ao `pom.xml` e acesse `/actuator/health`.
7. **Application Properties:** Crie três propriedades customizadas e leia-as numa classe usando `@Value("${sua.propriedade}")`.
8. **Perfis:** Crie `application-dev.properties` e `application-prod.properties` com portas diferentes e alterne entre eles com `--spring.profiles.active=dev`.

---

## Grupo B: JDBC Puro (9 a 14)

> Estes exercícios usam JDBC sem Spring, para você sentir a diferença. Use o H2 em arquivo ou instale o Postgres.

9. **Primeira Conexão:** Abra uma `Connection` com `DriverManager`, imprima uma confirmação e feche com try-with-resources.
10. **Criando Tabela:** Execute um `CREATE TABLE` via `Statement`.
11. **INSERT com PreparedStatement:** Insira três registros usando `?` e `setString`/`setDouble`.
12. **SELECT e ResultSet:** Consulte a tabela e percorra o `ResultSet` com `while (rs.next())`.
13. **Demonstrando SQL Injection:** Monte uma consulta concatenando a entrada do usuário e teste com `' OR '1'='1`. Depois corrija com `PreparedStatement` e explique num comentário por que a segunda versão é imune.
14. **Comparação Final:** Conte quantas linhas você precisou para um CRUD completo em JDBC puro e compare com as linhas do `ProdutoRepository` do projeto Spring.

---

## Grupo C: Entity e Repository (15 a 26)

15. **Primeira Entity:** Crie a entidade `Cliente` com id, nome, email e telefone.
16. **Explorando o Schema:** Suba a aplicação, entre no console H2 e observe a tabela que o Hibernate criou a partir da sua Entity.
17. **Nomes Customizados:** Use `@Table(name = "tb_clientes")` e `@Column(name = "nome_completo")` e confirme a mudança no banco.
18. **Restrições:** Adicione `nullable = false`, `unique = true` e `length` e teste inserir dados que violem cada regra.
19. **Repository Básico:** Crie `ClienteRepository extends JpaRepository<Cliente, Long>` e use os cinco métodos herdados.
20. **Query Method Simples:** Crie `findByNome(String nome)` e confira o SQL gerado no console.
21. **Query Methods Compostos:** Crie métodos com `And`, `Or`, `GreaterThan`, `Containing` e `OrderBy`.
22. **Top e First:** Crie `findTop5ByOrderByNomeAsc()`.
23. **Retornando Optional:** Crie `findByEmail` devolvendo `Optional<Cliente>` e trate o caso vazio.
24. **@Query com JPQL:** Escreva uma consulta explícita com `@Query` usando parâmetros nomeados.
25. **@Query Nativa:** Refaça a anterior com `nativeQuery = true` e compare as duas sintaxes.
26. **Relacionamento:** Crie a entidade `Pedido` com `@ManyToOne` para `Cliente` e observe a chave estrangeira criada.

---

## Grupo D: Service e Controller (27 a 40)

27. **Primeiro Service:** Crie `ClienteService` com injeção por construtor e o método `listarTodos()`.
28. **Buscar com Exceção:** Implemente `buscarPorId` usando `orElseThrow` com exceção customizada.
29. **Regra de Negócio:** No `criar()`, impeça o cadastro de dois clientes com o mesmo e-mail.
30. **Primeiro Controller:** Crie `ClienteController` com `@RestController` e um `@GetMapping` que liste todos.
31. **PathVariable:** Adicione `GET /clientes/{id}`.
32. **RequestParam:** Adicione `GET /clientes/buscar?nome=X`.
33. **POST com RequestBody:** Implemente a criação e teste com `curl` ou Postman.
34. **Status Correto:** Faça o POST devolver 201 e o DELETE devolver 204.
35. **PUT:** Implemente a atualização completa do cliente.
36. **DELETE:** Implemente a remoção, com 404 quando o id não existir.
37. **Bean Validation:** Adicione `@NotBlank`, `@Email` e `@Size` na Entity e `@Valid` no Controller. Teste enviando dados inválidos.
38. **Tratador Global:** Crie um `@RestControllerAdvice` que devolva 404 e 400 nos casos apropriados.
39. **ResponseEntity:** Reescreva um endpoint devolvendo `ResponseEntity<Cliente>` em vez do objeto puro e compare as duas abordagens.
40. **Endpoint de Relatório:** Crie `GET /clientes/relatorio` que devolva um `Map` com estatísticas calculadas via Streams.

---

## Grupo E: Integração e Projeto Final (41 a 50)

41. **Carga Inicial:** Crie um `CommandLineRunner` que popule o banco com 10 registros ao subir.
42. **@Transactional:** Crie um método que faça duas operações de escrita e lance uma exceção no meio. Comprove que nada foi gravado.
43. **DTO de Entrada:** Crie `ClienteRequest` (sem id) e converta para Entity no Service. Explique por que expor a Entity direto no Controller é arriscado.
44. **DTO de Saída:** Crie `ClienteResponse` que omita campos sensíveis e devolva-o no lugar da Entity.
45. **Paginação:** Use `Pageable` e devolva `Page<Cliente>`, testando com `?page=0&size=5`.
46. **Ordenação por Parâmetro:** Aceite `?sort=nome,desc` na listagem.
47. **Teste Unitário do Service:** Escreva um teste com JUnit 5 e Mockito, simulando o Repository com `@Mock`.
48. **Teste do Controller:** Use `@WebMvcTest` e `MockMvc` para testar um endpoint sem subir o servidor.
49. **Migrando para PostgreSQL:** Siga as instruções do [README](./projeto-spring/README.md) e faça o projeto rodar em Postgres. Comprove que nenhuma classe Java mudou.
50. **PROJETO FINAL para o portfólio:** Construa uma **API de Biblioteca** completa:
    *   entidades `Livro`, `Autor` e `Emprestimo`, com os relacionamentos corretos;
    *   CRUD completo dos três recursos;
    *   regras de negócio: não emprestar livro indisponível, limite de 3 empréstimos simultâneos por usuário, cálculo de multa por atraso;
    *   tratamento global de erros com status HTTP adequados;
    *   DTOs de entrada e saída;
    *   paginação na listagem;
    *   ao menos cinco testes automatizados;
    *   `README` explicando como rodar e listando os endpoints.

---

## Gabarito Comentado

**Q2 (dependências):** `starter-web` traz Tomcat, Spring MVC e Jackson; `starter-data-jpa` traz Hibernate e Spring Data; `starter-validation` habilita as anotações de validação; `h2` é o banco em memória; `starter-test` traz JUnit 5, Mockito e AssertJ.

**Q5 (jar executável):** o `.jar` do Spring Boot é um *fat jar*: contém sua aplicação, todas as dependências e o Tomcat embutido. É por isso que o deploy moderno é copiar um arquivo, e não instalar um servidor de aplicação.

**Q13 (SQL Injection):** com concatenação, a entrada `' OR '1'='1` altera a **estrutura** do comando SQL. Com `PreparedStatement`, o comando é compilado antes e o valor viaja separado, como dado puro. O banco nunca interpreta esse dado como instrução, e é por isso que a proteção é estrutural e não uma questão de "filtrar caracteres".

**Q14 (comparação):** um CRUD em JDBC puro costuma passar de 100 linhas com tratamento de exceção e fechamento de recursos. O equivalente em Spring Data é uma interface vazia. Entender o JDBC continua importando porque é ele que roda por baixo, e quando algo dá errado o erro aparece nessa camada.

**Q18 (restrições):** `nullable = false` gera `NOT NULL` na coluna e a violação lança `DataIntegrityViolationException`. Compare com `@NotBlank`, que age antes, na camada de validação: a primeira é a defesa do banco, a segunda é a defesa da aplicação. Ter as duas é redundância intencional.

**Q20 (query method):** se você errar o nome de um atributo, a aplicação **falha ao subir**, não em produção. Esse é o benefício escondido dos query methods: o erro aparece no momento mais barato possível.

**Q23 (`Optional` no repositório):** devolver `Optional` na assinatura obriga quem chama a lidar com a ausência. Devolver a Entity direto convida ao `null` e ao `NullPointerException` três camadas adiante.

**Q25 (JPQL vs nativa):** JPQL opera sobre **entidades e atributos** (`SELECT p FROM Produto p WHERE p.preco`), é portável entre bancos e é validada ao subir. SQL nativa opera sobre **tabelas e colunas**, amarra o código ao banco específico, mas libera recursos que o JPQL não cobre.

**Q29 (regra de negócio):** a regra fica no Service, nunca no Controller. Se ela estivesse no Controller, um segundo ponto de entrada (um job, um consumidor de fila) a ignoraria completamente.

**Q34 (status correto):** 201 comunica "criei um recurso novo"; 204 comunica "fiz o que pediu e não tenho nada para devolver". Devolver 200 para tudo funciona, mas obriga o cliente a inspecionar o corpo para descobrir o que aconteceu.

**Q39 (`ResponseEntity`):** devolver o objeto puro é mais limpo e o Spring assume 200. `ResponseEntity` é necessário quando o status varia em tempo de execução ou quando é preciso definir cabeçalhos. Use o objeto puro por padrão e `ResponseEntity` quando houver motivo.

**Q42 (`@Transactional`):** sem ele, a primeira escrita persiste e a segunda falha, deixando o banco em estado inconsistente. Com ele, o Spring desfaz tudo. Detalhe importante: por padrão o rollback ocorre apenas em exceções **unchecked**; para checked é preciso `@Transactional(rollbackFor = Exception.class)`.

**Q43 e Q44 (DTOs):** expor a Entity acopla o contrato da sua API à estrutura do banco. Renomear uma coluna passa a quebrar todos os clientes. Pior: um `@RequestBody Cliente` permite que alguém envie campos que você nunca pretendeu aceitar, como `id` ou `admin: true`. O DTO existe para você decidir explicitamente o que entra e o que sai.

**Q47 (teste com Mockito):** o Repository é substituído por um mock, então o teste roda em milissegundos e sem banco. Isso só é possível porque o Service recebe a dependência pelo construtor. Se ele criasse o Repository internamente com `new`, o teste exigiria um banco real. É a justificativa prática da injeção de dependência.

**Q50 (projeto final):** este é o exercício que vale para o portfólio. Uma API com regras de negócio reais, testes e README demonstra muito mais competência do que qualquer CRUD genérico. Se for publicar no GitHub, capriche no README: em processo seletivo, é a primeira coisa que abrem.
