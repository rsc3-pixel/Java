# Lista de Exercícios Prep: Módulo 6 - Spring Boot (50 Questões)

Simulado preparatório sobre Maven, JDBC, injeção de dependência, Spring Boot, JPA/Hibernate, arquitetura em camadas e REST. Este é o conteúdo mais cobrado em entrevista técnica para vaga Java. Gabarito comentado ao final.

---

## Grupo A: Maven e Build (1 a 6)

### 1. Qual a função principal do Maven?
*   a) Compilar Java mais rápido que o javac.
*   b) Gerenciar dependências, padronizar o build e a estrutura do projeto.
*   c) Substituir a JVM.
*   d) Servir como banco de dados.

### 2. Quais são as três coordenadas que identificam uma dependência?
*   a) `name`, `version`, `type`
*   b) `groupId`, `artifactId`, `version`
*   c) `package`, `class`, `method`
*   d) `id`, `nome`, `escopo`

### 3. Onde fica o código-fonte num projeto Maven padrão?
*   a) `/código`
*   b) `src/main/java`
*   c) `/java`
*   d) `target/classes`

### 4. O que `mvn package` produz?
*   a) Apenas os `.class`.
*   b) O `.jar` (ou `.war`) na pasta `target/`, após compilar e rodar os testes.
*   c) Um instalador do Windows.
*   d) A documentação do projeto.

### 5. Para que serve o `<parent>` do `spring-boot-starter-parent`?
*   a) Para herdar métodos Java.
*   b) Para definir as versões compatíveis de todas as dependências, dispensando `<version>` em cada uma.
*   c) Para criar a classe principal.
*   d) É apenas decorativo.

### 6. O que é o Maven Wrapper (`mvnw`)?
*   a) Uma IDE.
*   b) Um script que baixa a versão correta do Maven automaticamente, dispensando a instalação na máquina.
*   c) Um plugin de testes.
*   d) Um servidor web.

---

## Grupo B: JDBC (7 a 12)

### 7. O que é JDBC?
*   a) Um banco de dados.
*   b) A API padrão do Java para comunicação com bancos relacionais.
*   c) Um framework web.
*   d) Um formato de arquivo.

### 8. Por que usar `PreparedStatement` em vez de `Statement`?
*   a) Apenas por questão de estilo.
*   b) Porque ele separa o comando SQL dos dados, prevenindo SQL Injection, e é pré-compilado.
*   c) Porque `Statement` foi removido do Java.
*   d) Porque é mais curto de escrever.

### 9. O que acontece com `"SELECT * FROM users WHERE login = '" + entrada + "'"` se a entrada for `' OR '1'='1`?
*   a) Nada, a consulta falha.
*   b) A condição vira sempre verdadeira e a consulta devolve todos os usuários da tabela.
*   c) O banco recusa a conexão.
*   d) Lança `SQLException`.

### 10. Como percorrer um `ResultSet`?
*   a) `for (Row r : rs)`
*   b) `while (rs.next()) { ... }`
*   c) `rs.forEach(...)`
*   d) `while (rs.hasNext())`

### 11. Qual método executa um `INSERT`?
*   a) `executeQuery()`
*   b) `executeUpdate()`
*   c) `run()`
*   d) `insert()`

### 12. Por que usar `try-with-resources` com `Connection`?
*   a) Para tratar exceções automaticamente.
*   b) Porque conexões são recursos limitados e caros; deixá-las abertas esgota o pool e derruba a aplicação.
*   c) Para acelerar a consulta.
*   d) Não é necessário.

---

## Grupo C: Spring e Injeção de Dependência (13 a 22)

### 13. Qual a diferença entre biblioteca e framework?
*   a) Nenhuma.
*   b) Você chama a biblioteca; o framework chama o seu código (Inversão de Controle).
*   c) Framework é sempre maior.
*   d) Biblioteca é paga.

### 14. O que é Inversão de Controle (IoC)?
*   a) Inverter a ordem dos métodos.
*   b) Transferir ao framework o controle sobre a criação e o ciclo de vida dos objetos.
*   c) Um padrão de ordenação.
*   d) Executar de trás para frente.

### 15. O que é um Bean no Spring?
*   a) Uma classe qualquer.
*   b) Um objeto criado e gerenciado pelo contêiner do Spring.
*   c) Um tipo primitivo.
*   d) Um arquivo de configuração.

### 16. Qual a vantagem da injeção por construtor sobre `@Autowired` no atributo?
*   a) É mais curta.
*   b) Permite campos `final`, deixa as dependências explícitas e facilita o teste sem framework.
*   c) É a única forma que funciona.
*   d) É mais rápida em execução.

### 17. Por que depender de uma interface em vez da implementação concreta?
*   a) Por convenção de nomenclatura.
*   b) Porque permite trocar a implementação (banco, mock de teste) sem alterar a classe que a usa.
*   c) Porque interfaces são mais rápidas.
*   d) Porque o Java exige.

### 18. O que `@SpringBootApplication` combina?
*   a) `@Controller`, `@Service` e `@Repository`
*   b) `@Configuration`, `@EnableAutoConfiguration` e `@ComponentScan`
*   c) `@Entity`, `@Id` e `@Table`
*   d) Apenas `@Configuration`

### 19. Por que a classe com `@SpringBootApplication` deve ficar no pacote raiz?
*   a) Por convenção estética.
*   b) Porque o `@ComponentScan` varre a partir do pacote dela; num subpacote, ela não enxergaria as classes irmãs.
*   c) Porque o Java exige.
*   d) Não faz diferença.

### 20. Qual a diferença entre `@Component`, `@Service` e `@Repository`?
*   a) Funcionalmente são quase idênticas; `@Service` e `@Repository` são especializações que comunicam a intenção da camada (e `@Repository` traduz exceções de acesso a dados).
*   b) Só `@Component` cria Beans.
*   c) `@Service` é mais rápida.
*   d) `@Repository` só funciona com JDBC.

### 21. O que a autoconfiguração do Spring Boot faz?
*   a) Escreve o código por você.
*   b) Detecta o que está no classpath e configura automaticamente (achou o driver e a URL do banco, cria a conexão).
*   c) Gera testes automaticamente.
*   d) Compila o projeto.

### 22. O que é um "starter"?
*   a) A classe principal.
*   b) Um agrupamento de dependências relacionadas numa única declaração.
*   c) Um script de inicialização.
*   d) Um tipo de Bean.

---

## Grupo D: JPA, Entity e Repository (23 a 34)

### 23. Qual a relação entre JPA e Hibernate?
*   a) São a mesma coisa.
*   b) JPA é a especificação (o contrato); Hibernate é a implementação mais usada dela.
*   c) Hibernate é a especificação e JPA a implementação.
*   d) São concorrentes incompatíveis.

### 24. O que `@Entity` indica?
*   a) Que a classe é um Bean do Spring.
*   b) Que a classe é mapeada para uma tabela do banco.
*   c) Que a classe é abstrata.
*   d) Que a classe é um Controller.

### 25. Por que uma Entity precisa de construtor sem argumentos?
*   a) Por convenção.
*   b) Porque o JPA a instancia por reflexão ao ler o banco, e para isso precisa de um construtor vazio.
*   c) Porque o Java exige em toda classe.
*   d) Não precisa.

### 26. O que `@GeneratedValue(strategy = GenerationType.IDENTITY)` faz?
*   a) Gera um UUID.
*   b) Delega a geração do id ao banco (coluna auto increment).
*   c) Usa a data como id.
*   d) Exige que o id venha preenchido.

### 27. O que você ganha ao estender `JpaRepository<Produto, Long>`?
*   a) Nada, é preciso implementar tudo.
*   b) `save`, `findById`, `findAll`, `deleteById`, `count` e `existsById` já implementados pelo Spring em tempo de execução.
*   c) Apenas o `save`.
*   d) Apenas os métodos que você declarar.

### 28. Como o Spring implementa `findByCategoria(String categoria)`?
*   a) Você precisa escrever a implementação.
*   b) Ele interpreta o nome do método e gera a consulta automaticamente.
*   c) Ele procura um arquivo SQL com esse nome.
*   d) Ele usa reflexão sobre um XML.

### 29. O que acontece se você escrever `findByPrecoo` (com erro de digitação)?
*   a) Compila e devolve lista vazia.
*   b) A aplicação falha ao subir, porque o Spring não encontra o atributo na entidade.
*   c) Lança exceção só quando o método é chamado.
*   d) O Spring ignora o método.

### 30. O que `findById` devolve?
*   a) A entidade ou `null`.
*   b) `Optional<Entidade>`.
*   c) Uma `List`.
*   d) `boolean`.

### 31. Qual a diferença entre JPQL e SQL nativa no `@Query`?
*   a) Nenhuma.
*   b) JPQL opera sobre entidades e atributos e é portável entre bancos; SQL nativa opera sobre tabelas e colunas e amarra o código a um banco específico.
*   c) JPQL é mais lenta sempre.
*   d) SQL nativa não é suportada.

### 32. O que `spring.jpa.hibernate.ddl-auto=update` faz?
*   a) Atualiza a versão do Hibernate.
*   b) Ajusta o schema do banco conforme as entidades mudam.
*   c) Atualiza todos os registros.
*   d) Não faz nada.

### 33. Por que `ddl-auto=update` é inadequado em produção?
*   a) Porque é lento.
*   b) Porque deixa alterações de schema a cargo de um algoritmo automático, sem revisão, sem histórico e sem reversão; produção exige migrações versionadas (Flyway/Liquibase).
*   c) Porque não funciona com Postgres.
*   d) Porque exige reiniciar o banco.

### 34. O que `@ManyToOne` representa?
*   a) Um relacionamento onde muitos registros desta entidade apontam para um registro da outra.
*   b) Uma tabela com muitas colunas.
*   c) Uma consulta que devolve muitos resultados.
*   d) Herança entre entidades.

---

## Grupo E: Arquitetura e REST (35 a 50)

### 35. Qual a ordem correta das camadas numa requisição?
*   a) Repository → Service → Controller
*   b) Controller → Service → Repository
*   c) Service → Controller → Repository
*   d) Controller → Repository → Service

### 36. Por que o Service não deve conhecer HTTP?
*   a) Por questão de desempenho.
*   b) Porque assim a mesma regra de negócio serve para API REST, app mobile, job agendado e teste, sem duplicação.
*   c) Porque o Spring proíbe.
*   d) Porque HTTP é obsoleto.

### 37. Onde deve ficar a validação "não vender mais itens do que há em estoque"?
*   a) No Controller.
*   b) No Service, porque é regra de negócio.
*   c) No Repository.
*   d) Na Entity.

### 38. O que `@RestController` faz?
*   a) Apenas mapeia rotas.
*   b) Combina `@Controller` e `@ResponseBody`, fazendo os métodos devolverem JSON em vez de nome de view.
*   c) Cria um cliente REST.
*   d) Habilita o CORS.

### 39. Qual anotação captura um valor vindo do caminho da URL, como em `/produtos/5`?
*   a) `@RequestParam`
*   b) `@PathVariable`
*   c) `@RequestBody`
*   d) `@RequestHeader`

### 40. E um valor da query string, como em `/produtos?categoria=TI`?
*   a) `@PathVariable`
*   b) `@RequestParam`
*   c) `@RequestBody`
*   d) `@ModelAttribute`

### 41. Qual anotação converte o JSON do corpo da requisição em objeto Java?
*   a) `@RequestBody`
*   b) `@ResponseBody`
*   c) `@JsonParse`
*   d) `@PathVariable`

### 42. Qual o status HTTP adequado para uma criação bem-sucedida?
*   a) 200 OK
*   b) 201 Created
*   c) 204 No Content
*   d) 302 Found

### 43. E para um DELETE bem-sucedido sem corpo de resposta?
*   a) 200 OK
*   b) 204 No Content
*   c) 404 Not Found
*   d) 410 Gone

### 44. O que significa a faixa 4xx?
*   a) Sucesso.
*   b) Erro do cliente: a requisição está incorreta (dados inválidos, sem permissão, recurso inexistente).
*   c) Erro do servidor.
*   d) Redirecionamento.

### 45. Qual status devolver quando o id pedido não existe?
*   a) 400
*   b) 404
*   c) 500
*   d) 204

### 46. Qual a função de `@RestControllerAdvice`?
*   a) Documentar a API.
*   b) Interceptar exceções lançadas por todos os controllers e traduzi-las em respostas HTTP padronizadas.
*   c) Validar dados de entrada.
*   d) Criar rotas automaticamente.

### 47. O que `@Transactional` garante?
*   a) Que o método seja mais rápido.
*   b) Que todas as operações de escrita do método aconteçam por completo ou sejam desfeitas (rollback) em caso de exceção.
*   c) Que apenas um usuário acesse por vez.
*   d) Que os dados sejam criptografados.

### 48. Por qual tipo de exceção o `@Transactional` faz rollback por padrão?
*   a) Todas.
*   b) Apenas unchecked (`RuntimeException`); para checked é preciso `rollbackFor`.
*   c) Apenas checked.
*   d) Nenhuma.

### 49. Por que é arriscado usar a Entity direto como `@RequestBody`?
*   a) Não compila.
*   b) Acopla o contrato da API à estrutura do banco e permite que o cliente envie campos que você nunca pretendeu aceitar, como `id` ou flags de permissão.
*   c) É mais lento.
*   d) Não funciona com JSON.

### 50. Numa entrevista, qual resposta demonstra mais senioridade?
*   a) Listar de cor todas as anotações do Spring.
*   b) Explicar por que as camadas são separadas, o que cada uma pode e não pode conhecer, e o custo de violar essa separação.
*   c) Citar a versão mais recente do framework.
*   d) Descrever a estrutura interna do Tomcat.

---

# GABARITO COMENTADO

## Grupo A

**1. b)** Dependências, build padronizado e estrutura convencionada. O ganho maior é o build funcionar igual em qualquer máquina.

**2. b)** `groupId`, `artifactId` e `version`, o chamado GAV.

**3. b)** `src/main/java`. Recursos vão em `src/main/resources` e testes em `src/test/java`.

**4. b)** O `.jar` em `target/`, depois de compilar e testar.

**5. b)** Define as versões compatíveis entre si. É por isso que as dependências do Spring não declaram `<version>`: tentar escolher versões manualmente costuma gerar conflitos difíceis de diagnosticar.

**6. b)** Script que baixa o Maven sozinho. Garante que todo mundo no time use exatamente a mesma versão do build.

## Grupo B

**7. b)** A API padrão de acesso a bancos relacionais. Cada banco fornece seu driver, que implementa essa API.

**8. b)** Separação entre comando e dado, prevenindo SQL Injection, além do ganho de pré-compilação.

**9. b)** Devolve todos os usuários. A entrada deixou de ser dado e virou parte da **estrutura** do comando, que é a essência do SQL Injection.

**10. b)** `while (rs.next())`. O cursor começa **antes** da primeira linha, por isso `next()` precisa ser chamado antes da primeira leitura.

**11. b)** `executeUpdate()`, que devolve o número de linhas afetadas. `executeQuery()` é só para `SELECT`.

**12. b)** Conexões são caras e limitadas. Vazá-las esgota o pool e a aplicação trava esperando conexão, um sintoma que costuma aparecer só sob carga, já em produção.

## Grupo C

**13. b)** A direção do controle. É a definição prática de framework.

**14. b)** O framework controla a criação e o ciclo de vida dos objetos.

**15. b)** Objeto gerenciado pelo contêiner do Spring.

**16. b)** Campos `final`, dependências explícitas e teste sem framework. Bônus: um construtor com oito parâmetros denuncia visualmente que a classe faz coisas demais, sinal que o `@Autowired` no atributo esconde.

**17. b)** Trocar a implementação sem tocar em quem usa. É o Princípio da Inversão de Dependência do SOLID.

**18. b)** `@Configuration`, `@EnableAutoConfiguration` e `@ComponentScan`.

**19. b)** Por causa do `@ComponentScan`. Colocada num subpacote, a aplicação sobe sem nenhuma rota registrada e **sem erro visível**, que é o pior tipo de falha.

**20. a)** Funcionalmente quase idênticas. A diferença prática está na intenção comunicada ao leitor e na tradução de exceções que o `@Repository` faz.

**21. b)** Configura conforme o que encontra no classpath.

**22. b)** Agrupamento de dependências relacionadas.

## Grupo D

**23. b)** JPA é a especificação, Hibernate é a implementação. Programar contra a JPA permite, em tese, trocar de implementação.

**24. b)** Mapeia a classe para uma tabela.

**25. b)** Instanciação por reflexão. Sem o construtor vazio, a aplicação nem sobe.

**26. b)** O banco gera o id (auto increment).

**27. b)** O CRUD completo pronto, implementado em tempo de execução por um proxy dinâmico.

**28. b)** Ele interpreta o nome do método. As palavras reconhecidas incluem `findBy`, `And`, `Or`, `Between`, `GreaterThan`, `Containing`, `IgnoreCase`, `OrderBy`, `Top` e `First`.

**29. b)** Falha ao subir. Erro cedo é erro barato: melhor a aplicação não iniciar do que descobrir o problema em produção.

**30. b)** `Optional<Entidade>`, exatamente o conceito do Módulo 5.

**31. b)** JPQL sobre entidades e portável; nativa sobre tabelas e específica do banco.

**32. b)** Ajusta o schema conforme as entidades.

**33. b)** Falta de revisão, histórico e reversão. Além disso, `update` nunca remove nem renomeia colunas: ele só acrescenta, e o schema real vai divergindo silenciosamente do esperado. Produção pede Flyway ou Liquibase.

**34. a)** Muitos registros desta entidade apontam para um da outra, gerando a chave estrangeira deste lado.

## Grupo E

**35. b)** Controller → Service → Repository. Cada camada só conhece a de baixo.

**36. b)** Reaproveitamento sem duplicação. Se a regra estivesse no Controller, um job agendado precisaria reimplementá-la, e as duas versões divergiriam com o tempo.

**37. b)** No Service. É regra de negócio, e regra de negócio tem um único lugar.

**38. b)** `@Controller` + `@ResponseBody`.

**39. b)** `@PathVariable`.

**40. b)** `@RequestParam`.

**41. a)** `@RequestBody`. O `@ResponseBody` faz o caminho inverso.

**42. b)** 201 Created.

**43. b)** 204 No Content.

**44. b)** Erro do cliente. 400 dados inválidos, 401 sem autenticação, 403 autenticado mas sem permissão, 404 inexistente.

**45. b)** 404 Not Found.

**46. b)** Tratamento global de exceções. É o que permite ao Service lançar exceções de negócio sem conhecer HTTP.

**47. b)** Atomicidade: tudo ou nada.

**48. b)** Apenas unchecked por padrão. Esse detalhe cai muito em entrevista e é causa real de bugs: um método que lança `IOException` **não** faz rollback automático.

**49. b)** Acoplamento e superfície de ataque. O cliente poderia enviar campos que você não pretendia expor, e renomear uma coluna passaria a quebrar todos os consumidores da API.

**50. b)** Explicar a arquitetura. Anotação se consulta na documentação em trinta segundos; entender por que a separação existe e o que quebra quando ela é violada é o que distingue quem só usou o framework de quem o compreende. Se você souber responder isso com clareza, e ainda relacionar com a separação em camadas que fez à mão no Módulo 4, a resposta fica muito acima da média.
