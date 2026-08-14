# Lista de Exercícios Prep: Módulo 5 - Java Moderno (50 Questões)

Simulado preparatório sobre generics, interfaces funcionais, lambdas, Streams, Collectors, `Optional` e `java.time`. Gabarito comentado ao final.

---

## Grupo A: Generics (1 a 8)

### 1. O que significa o `<T>` em `class Caixa<T>`?
*   a) Um tipo chamado literalmente "T".
*   b) Um parâmetro de tipo: um espaço em branco preenchido no momento do uso.
*   c) Uma anotação de compilação.
*   d) Um atributo estático.

### 2. Qual a principal vantagem dos generics?
*   a) Reduzir o consumo de memória.
*   b) Garantir segurança de tipo em tempo de compilação e dispensar casts.
*   c) Acelerar a execução do programa.
*   d) Permitir herança múltipla.

### 3. O que é *type erasure*?
*   a) A remoção de objetos pelo Garbage Collector.
*   b) O apagamento das informações de tipo genérico após a compilação: `List<String>` e `List<Integer>` viram a mesma coisa no bytecode.
*   c) A conversão automática de tipos primitivos.
*   d) A exclusão de variáveis não utilizadas.

### 4. Por que `new T[10]` não compila dentro de uma classe genérica?
*   a) Sintaxe incorreta.
*   b) Por causa do type erasure: em tempo de execução o tipo `T` não existe mais, então a JVM não sabe que array criar.
*   c) Porque arrays não aceitam generics.
*   d) Porque `T` precisa ser `final`.

### 5. O que `<T extends Number>` garante?
*   a) Que `T` herde de qualquer classe.
*   b) Que `T` seja `Number` ou uma subclasse dela, permitindo chamar os métodos de `Number`.
*   c) Que `T` seja obrigatoriamente `Integer`.
*   d) Que `T` implemente `Comparable`.

### 6. Qual a sintaxe correta de um método genérico?
*   a) `public static void metodo<T>(T item)`
*   b) `public static <T> void metodo(T item)`
*   c) `public static void <T> metodo(T item)`
*   d) `public <T> static void metodo(T item)`

### 7. O que é o *diamond operator*?
*   a) O `<>` vazio do lado direito, que permite ao compilador inferir o tipo declarado à esquerda.
*   b) Um operador de comparação.
*   c) Uma forma de herança múltipla.
*   d) Um tipo de lambda.

### 8. `List<Object> lista = new ArrayList<String>();` compila?
*   a) Sim, pois `String` é subtipo de `Object`.
*   b) Não. Generics não são covariantes: `List<String>` não é subtipo de `List<Object>`.
*   c) Sim, com um aviso.
*   d) Sim, apenas em Java 8+.

---

## Grupo B: Interfaces Funcionais e Lambdas (9 a 20)

### 9. O que define uma interface funcional?
*   a) Ter a anotação `@FunctionalInterface` obrigatoriamente.
*   b) Ter exatamente um método abstrato.
*   c) Ter apenas métodos `static`.
*   d) Não ter métodos.

### 10. A anotação `@FunctionalInterface` é obrigatória?
*   a) Sim, sem ela a lambda não funciona.
*   b) Não. Ela é opcional e serve para o compilador validar a regra de um único método abstrato.
*   c) Sim, em Java 11+.
*   d) Não, e não tem nenhum efeito.

### 11. Uma interface funcional pode ter métodos `default`?
*   a) Não, apenas o método abstrato.
*   b) Sim. Métodos `default` e `static` têm corpo e não contam para o limite de um método abstrato.
*   c) Sim, mas perde a capacidade de virar lambda.
*   d) Apenas se forem `private`.

### 12. Qual lambda equivale a `(String s) -> { return s.length(); }`?
*   a) `s -> s.length()`
*   b) `s -> { s.length(); }`
*   c) `(s) => s.length()`
*   d) `s -> return s.length();`

### 13. Qual interface funcional recebe um valor e devolve `boolean`?
*   a) `Function<T,R>`
*   b) `Consumer<T>`
*   c) `Predicate<T>`
*   d) `Supplier<T>`

### 14. Qual interface funcional não recebe nada e devolve um valor?
*   a) `Consumer<T>`
*   b) `Supplier<T>`
*   c) `Runnable`
*   d) `Predicate<T>`

### 15. Qual o método de `Function<T,R>`?
*   a) `test()`
*   b) `accept()`
*   c) `apply()`
*   d) `get()`

### 16. Qual a saída?
```java
Predicate<String> p = s -> s.length() > 3;
System.out.println(p.negate().test("Java"));
```
*   a) `true`
*   b) `false`
*   c) Erro de compilação.
*   d) `null`

### 17. Por que uma variável local usada dentro de uma lambda precisa ser efetivamente final?
*   a) Por uma limitação arbitrária do compilador.
*   b) Porque a lambda pode executar depois que o método terminou, quando aquela variável já não existiria na pilha; o valor é copiado.
*   c) Porque lambdas não aceitam variáveis.
*   d) Porque `final` melhora o desempenho.

### 18. O que `System.out::println` representa?
*   a) Uma chamada imediata ao método.
*   b) Um method reference: uma referência ao método, equivalente à lambda `x -> System.out.println(x)`.
*   c) Um operador de concatenação.
*   d) Uma classe anônima.

### 19. Qual method reference equivale a `s -> Integer.parseInt(s)`?
*   a) `Integer::parseInt`
*   b) `Integer.parseInt()`
*   c) `s::parseInt`
*   d) `::parseInt`

### 20. `ArrayList::new` é um method reference para quê?
*   a) Para o método `new()`.
*   b) Para o construtor da classe.
*   c) Para o método `clone()`.
*   d) Não é válido.

---

## Grupo C: Streams (21 a 36)

### 21. O que é um Stream em Java?
*   a) Uma coleção que armazena dados.
*   b) Um fluxo de processamento sobre uma fonte de dados; ele não armazena nada.
*   c) Um tipo de array dinâmico.
*   d) Uma thread de execução.

### 22. Quais são as três partes de um pipeline de Stream?
*   a) Início, meio e fim.
*   b) Origem, operações intermediárias e uma operação terminal.
*   c) Filtro, mapa e redução.
*   d) Entrada, processo e saída.

### 23. O que caracteriza uma operação intermediária?
*   a) Encerra o pipeline e produz o resultado.
*   b) Devolve outro Stream, permitindo o encadeamento, e é preguiçosa.
*   c) Só pode aparecer uma vez.
*   d) Executa imediatamente.

### 24. Qual é a saída deste código?
```java
List<String> l = List.of("a", "b");
l.stream().filter(s -> { System.out.println("teste"); return true; });
System.out.println("fim");
```
*   a) `teste teste fim`
*   b) Apenas `fim`, pois sem operação terminal o pipeline não executa.
*   c) `fim teste teste`
*   d) Erro de compilação.

### 25. O que acontece ao reutilizar um Stream após uma operação terminal?
*   a) Funciona normalmente.
*   b) Lança `IllegalStateException`: streams são de uso único.
*   c) Devolve uma lista vazia.
*   d) Reinicia o pipeline do começo.

### 26. Qual a diferença entre `map` e `filter`?
*   a) São sinônimos.
*   b) `map` transforma cada elemento em outra coisa; `filter` seleciona quais elementos continuam.
*   c) `map` remove duplicatas e `filter` ordena.
*   d) `map` é terminal e `filter` é intermediária.

### 27. Por que usar `mapToDouble` em vez de `map` ao somar preços?
*   a) É apenas uma questão de estilo.
*   b) `mapToDouble` produz um `DoubleStream` primitivo, que possui `sum()`, `average()` e `summaryStatistics()`; `Stream<Double>` não tem esses métodos.
*   c) `map` não aceita `double`.
*   d) `mapToDouble` ordena automaticamente.

### 28. Qual destas é uma operação **terminal**?
*   a) `filter`
*   b) `map`
*   c) `collect`
*   d) `sorted`

### 29. O que `.limit(5).skip(2)` produz numa lista de 10 elementos?
*   a) Os elementos 3 a 7.
*   b) Os elementos 3 a 5 (limita a 5 e depois pula 2).
*   c) Os elementos 1 a 5.
*   d) Uma lista vazia.

### 30. `distinct()` usa qual mecanismo para identificar duplicatas?
*   a) `==`
*   b) `equals()` e `hashCode()`, exatamente como o `HashSet`.
*   c) `compareTo()`
*   d) O endereço de memória.

### 31. O que `count()` devolve?
*   a) `int`
*   b) `long`
*   c) `Integer`
*   d) `Optional<Integer>`

### 32. Qual a diferença entre `anyMatch` e `allMatch`?
*   a) Nenhuma.
*   b) `anyMatch` devolve true se ao menos um elemento passar; `allMatch` exige que todos passem.
*   c) `anyMatch` devolve uma lista e `allMatch` um boolean.
*   d) `allMatch` é intermediária.

### 33. O que `findFirst()` retorna?
*   a) O elemento diretamente.
*   b) Um `Optional`, porque o filtro pode não encontrar nada.
*   c) `null` quando não encontra.
*   d) Uma lista com um elemento.

### 34. No `reduce(identidade, acumulador)`, o que é a identidade?
*   a) O primeiro elemento da lista.
*   b) O valor inicial, que deve ser o elemento neutro da operação (0 para soma, 1 para multiplicação).
*   c) Um identificador único.
*   d) O tamanho do stream.

### 35. Qual a saída?
```java
int r = Stream.of(1, 2, 3, 4).reduce(0, (a, b) -> a * b);
System.out.println(r);
```
*   a) `24`
*   b) `0`
*   c) `10`
*   d) `1`

### 36. Quando **não** usar Streams?
*   a) Nunca, Streams são sempre melhores.
*   b) Quando é preciso `break`, quando o índice importa, quando há muita mutação de estado externo ou em laços triviais.
*   c) Apenas em listas grandes.
*   d) Quando a coleção é um `Set`.

---

## Grupo D: Collectors (37 a 42)

### 37. O que `Collectors.joining(", ")` faz?
*   a) Junta os elementos de um `Stream<String>` numa única String com o separador indicado.
*   b) Une duas listas.
*   c) Agrupa por chave.
*   d) Ordena e concatena.

### 38. O que `Collectors.groupingBy(Produto::getCategoria)` devolve?
*   a) `List<Produto>`
*   b) `Map<String, List<Produto>>`
*   c) `Map<String, Produto>`
*   d) `Set<String>`

### 39. E `Collectors.groupingBy(X::getCat, Collectors.counting())`?
*   a) `Map<String, Integer>`
*   b) `Map<String, Long>`
*   c) `List<Long>`
*   d) `Map<Long, String>`

### 40. Qual a diferença entre `groupingBy` e `partitioningBy`?
*   a) Nenhuma.
*   b) `groupingBy` cria uma chave por valor distinto; `partitioningBy` sempre cria exatamente duas chaves, `true` e `false`.
*   c) `partitioningBy` só funciona com números.
*   d) `groupingBy` é terminal e `partitioningBy` é intermediária.

### 41. O que acontece em `Collectors.toMap(chave, valor)` com chaves duplicadas?
*   a) A última sobrescreve a anterior silenciosamente.
*   b) Lança `IllegalStateException: Duplicate key`.
*   c) A duplicata é ignorada.
*   d) Cria uma lista como valor.

### 42. Como resolver o problema da questão anterior?
*   a) Usando `distinct()` antes.
*   b) Passando um terceiro parâmetro de merge: `toMap(k, v, (a, b) -> a)`.
*   c) Trocando para `groupingBy`.
*   d) Não é possível resolver.

---

## Grupo E: Optional e Datas (43 a 50)

### 43. Qual o propósito de `Optional<T>`?
*   a) Tornar atributos opcionais numa classe.
*   b) Representar explicitamente na assinatura do método que o resultado pode não existir, evitando o retorno de `null`.
*   c) Melhorar o desempenho.
*   d) Substituir todos os `if`.

### 44. Qual a diferença entre `Optional.of()` e `Optional.ofNullable()`?
*   a) Nenhuma.
*   b) `of()` lança `NullPointerException` se o valor for null; `ofNullable()` aceita null e devolve um Optional vazio.
*   c) `ofNullable()` é mais rápido.
*   d) `of()` só aceita String.

### 45. Qual a diferença entre `orElse()` e `orElseGet()`?
*   a) Nenhuma.
*   b) `orElse` avalia o argumento sempre, mesmo quando o valor existe; `orElseGet` só executa a lambda quando o Optional está vazio.
*   c) `orElseGet` lança exceção.
*   d) `orElse` só aceita String.

### 46. Por que `optional.get()` sem verificação prévia é problemático?
*   a) Não compila.
*   b) Lança `NoSuchElementException` quando vazio, ou seja, é o mesmo risco do `null` com outro nome.
*   c) Devolve `null`.
*   d) É lento.

### 47. Qual é o uso **inadequado** de `Optional`?
*   a) Como retorno de método de busca.
*   b) Como parâmetro de método ou atributo de classe.
*   c) Com `ifPresent`.
*   d) Com `map`.

### 48. Qual a principal vantagem de `java.time` sobre `Date` e `Calendar`?
*   a) Nomes mais curtos.
*   b) As classes são imutáveis e seguras entre threads, e a API é coerente (mês 1 é janeiro).
*   c) Ocupam menos memória.
*   d) Aceitam qualquer formato de String automaticamente.

### 49. Qual a saída?
```java
LocalDate d = LocalDate.of(2026, 1, 10);
d.plusDays(5);
System.out.println(d.getDayOfMonth());
```
*   a) `15`
*   b) `10`, porque `LocalDate` é imutável e `plusDays` devolve um novo objeto.
*   c) `5`
*   d) Erro de compilação.

### 50. Qual a diferença entre `Period` e `ChronoUnit.between()`?
*   a) São idênticos.
*   b) `Period` decompõe a diferença em anos, meses e dias; `ChronoUnit.between` devolve o total numa única unidade.
*   c) `Period` só funciona com horas.
*   d) `ChronoUnit` é obsoleto.

---

# GABARITO COMENTADO

## Grupo A

**1. b)** Parâmetro de tipo. A letra é convenção (`T`, `E`, `K`, `V`), não regra da linguagem.

**2. b)** Segurança de tipo em compilação e ausência de cast. Antes dos generics, tudo era `Object` e o erro só aparecia em execução, como `ClassCastException`.

**3. b)** As informações genéricas são apagadas após a compilação. Foi a solução escolhida em 2004 para manter compatibilidade com o código anterior.

**4. b)** Consequência direta do type erasure. A solução usual é `(T[]) new Object[10]` com `@SuppressWarnings`, ou usar `List<T>` em vez de array.

**5. b)** `T` é `Number` ou subtipo, o que libera `doubleValue()`, `intValue()` e afins.

**6. b)** O `<T>` vem **antes** do tipo de retorno.

**7. a)** O `<>` vazio, introduzido no Java 7, que elimina a repetição do tipo.

**8. b)** Não compila. Se compilasse, seria possível inserir um `Integer` numa lista que na verdade é de `String`, quebrando a segurança de tipo. Para aceitar subtipos em leitura, existe o wildcard `List<? extends Object>`.

## Grupo B

**9. b)** Exatamente um método abstrato.

**10. b)** Opcional, mas recomendada: ela transforma a quebra da regra em erro de compilação.

**11. b)** Sim. Métodos `default` e `static` têm corpo, então não contam como abstratos. `Comparator` é o exemplo clássico, cheio de `default`.

**12. a)** `s -> s.length()`. Com uma única expressão, chaves, `return` e ponto e vírgula são dispensados.

**13. c)** `Predicate<T>`, cujo método é `test()`.

**14. b)** `Supplier<T>`, cujo método é `get()`.

**15. c)** `apply()`.

**16. b)** `false`. "Java" tem 4 caracteres, então o predicado original devolve `true` e o `negate()` inverte para `false`.

**17. b)** A lambda pode sobreviver ao método que a criou, então o valor é copiado. Permitir alteração criaria duas fontes de verdade divergentes.

**18. b)** Method reference, equivalente à lambda correspondente.

**19. a)** `Integer::parseInt`, referência a método estático.

**20. b)** Referência a construtor.

## Grupo C

**21. b)** Fluxo de processamento, não armazenamento. É a diferença conceitual entre `Stream` e `Collection`.

**22. b)** Origem, intermediárias e terminal.

**23. b)** Devolve outro Stream e é preguiçosa: nada executa até a terminal chegar.

**24. b)** Apenas `fim`. Este é o teste clássico da preguiça dos streams.

**25. b)** `IllegalStateException`. Para percorrer duas vezes, crie dois streams a partir da coleção.

**26. b)** `map` transforma, `filter` seleciona. Confundir os dois é o erro mais comum de quem está começando.

**27. b)** Os streams primitivos (`IntStream`, `DoubleStream`) ganham os métodos numéricos e ainda evitam o custo de boxing.

**28. c)** `collect`. As demais são intermediárias.

**29. b)** Os elementos 3 a 5. A ordem importa: primeiro limita a 5, depois pula 2, sobrando 3. Invertendo (`skip(2).limit(5)`), o resultado seria os elementos 3 a 7.

**30. b)** `equals()` e `hashCode()`. Se sua classe não os sobrescreve, `distinct()` não remove nada.

**31. b)** `long`, para suportar streams muito grandes.

**32. b)** "ao menos um" versus "todos". Detalhe de prova: `allMatch` em stream **vazio** devolve `true` (vacuosamente verdadeiro) e `anyMatch` devolve `false`.

**33. b)** `Optional`, porque o filtro pode não encontrar nada.

**34. b)** O elemento neutro da operação.

**35. b)** `0`. A identidade deveria ser `1` para multiplicação; com `0`, todo produto zera. É a pegadinha clássica do `reduce`.

**36. b)** Streams não são universalmente melhores. Em laço trivial, com `break` ou com índice, o `for` é mais claro e às vezes mais rápido.

## Grupo D

**37. a)** Concatena com o separador. Aceita também prefixo e sufixo.

**38. b)** `Map<String, List<Produto>>`. Sem downstream collector, o valor é sempre uma lista.

**39. b)** `Map<String, Long>`, porque `counting()` devolve `Long`. Atribuir a `Map<String, Integer>` não compila.

**40. b)** `groupingBy` cria N chaves; `partitioningBy` cria exatamente duas, e ambas existem mesmo que uma delas fique com lista vazia.

**41. b)** `IllegalStateException: Duplicate key`. Diferente do `put` de um `Map`, que sobrescreve em silêncio.

**42. b)** O terceiro parâmetro de merge decide qual valor mantém.

## Grupo E

**43. b)** Comunicar na assinatura que o resultado pode não existir. O ganho é o compilador e o leitor saberem disso, em vez de descobrirem com um `NullPointerException`.

**44. b)** `of` rejeita null; `ofNullable` aceita e devolve vazio.

**45. b)** `orElse` avalia sempre. Com `orElse(consultarBanco())`, a consulta acontece mesmo quando o valor já existe, o que é desperdício e às vezes bug.

**46. b)** `NoSuchElementException`. Trocar `null` por `Optional` e chamar `.get()` sem checar é trocar seis por meia dúzia.

**47. b)** Como parâmetro ou atributo. Ele foi desenhado para retorno; nos outros lugares só adiciona uma camada sem eliminar a verificação.

**48. b)** Imutabilidade e coerência. As classes antigas eram mutáveis, tinham mês começando em 0 e o `SimpleDateFormat` não era seguro entre threads, causando bugs raros e difíceis de reproduzir.

**49. b)** `10`. `plusDays` devolve um objeto novo e o original permanece intacto. Ignorar o retorno é o erro clássico com `java.time`, exatamente como acontece com os métodos de `String`.

**50. b)** `Period` decompõe; `ChronoUnit.between` totaliza numa unidade. Para exibir idade, use `Period`; para comparar, use `ChronoUnit`.
