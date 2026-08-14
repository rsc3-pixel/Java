# Módulo 5: Java Moderno (Java 8+)

O Java 8, lançado em 2014, foi a maior mudança da linguagem desde a sua criação. Ele trouxe programação funcional para uma linguagem que até então era estritamente orientada a objetos.

Na prática do dia a dia, isso significa escrever **o que** você quer, em vez de **como** obter. Um laço que ocupava 8 linhas vira uma linha declarativa. Este módulo é o que separa código Java de 2005 de código Java de hoje, e é justamente o que uma entrevista técnica costuma cobrar.

---

## 1. Generics: escrever uma vez, servir para qualquer tipo

No Módulo 3 você **usou** generics (`List<String>`). Agora vamos **criar** os seus.

O problema que eles resolvem:

```java
// Sem generics: preciso de uma classe por tipo
class CaixaDeString { private String conteudo; /* ... */ }
class CaixaDeInteger { private Integer conteudo; /* ... */ }
// e assim por diante, para sempre
```

Com generics, uma classe serve para todos:

```java
// <T> é um "parâmetro de tipo": um espaço em branco preenchido no uso
public class Caixa<T> {
    private T conteudo;

    public void guardar(T item) {
        this.conteudo = item;
    }

    public T retirar() {
        return conteudo;
    }
}
```

```java
Caixa<String> caixaTexto = new Caixa<>();
caixaTexto.guardar("Java");
String texto = caixaTexto.retirar(); // sem cast: o compilador já sabe o tipo

Caixa<Integer> caixaNumero = new Caixa<>();
caixaNumero.guardar(42);
// caixaNumero.guardar("erro"); // não compila, e é exatamente esse o ganho
```

### Convenção dos nomes

Não é regra da linguagem, mas todo mundo segue: `T` (Type), `E` (Element), `K` (Key), `V` (Value), `N` (Number).

### Métodos genéricos

O `<T>` também pode ficar em um método isolado, antes do tipo de retorno:

```java
public static <T> void imprimirArray(T[] array) {
    for (T item : array) {
        System.out.println(item);
    }
}
```

### Limitando o tipo com `extends`

Às vezes você precisa garantir que o tipo tenha certas capacidades:

```java
// <T extends Number> aceita apenas Integer, Double, Long, etc.
public static <T extends Number> double somar(List<T> numeros) {
    double total = 0;
    for (T n : numeros) {
        total += n.doubleValue(); // só posso chamar isso porque garanti que é Number
    }
    return total;
}
```

Sem o `extends Number`, o compilador trataria `T` como `Object` e `doubleValue()` não existiria.

> [!NOTE]
> **Type erasure:** os generics existem apenas em tempo de compilação. Depois de compilado, `List<String>` e `List<Integer>` viram a mesma coisa no bytecode. Por isso não é possível fazer `new T[10]` nem `if (x instanceof List<String>)`. É uma decisão de compatibilidade com o Java anterior a 2004, e uma pergunta frequente em entrevista.

---

## 2. Interfaces funcionais

Uma **interface funcional** é qualquer interface com **exatamente um método abstrato**. É esse detalhe que permite substituí-la por uma lambda.

```java
@FunctionalInterface // anotação opcional: faz o compilador verificar a regra
interface Calculadora {
    double calcular(double a, double b); // um único método abstrato
}
```

Antes do Java 8, para usar uma dessas era preciso uma classe anônima:

```java
Calculadora soma = new Calculadora() {
    @Override
    public double calcular(double a, double b) {
        return a + b;
    }
};
```

Seis linhas para expressar `a + b`. O restante é cerimônia.

---

## 3. Expressões Lambda

Uma lambda é uma função escrita de forma compacta, sem nome e sem classe.

```java
Calculadora soma = (a, b) -> a + b;
System.out.println(soma.calcular(10, 5)); // 15
```

Toda a informação que sumiu (nome do método, tipos dos parâmetros, `return`) foi **inferida** pelo compilador a partir da interface.

### Formas sintáticas

```java
// Sem parâmetros
Runnable r = () -> System.out.println("executando");

// Um parâmetro: os parênteses são opcionais
Consumer<String> imprime = nome -> System.out.println(nome);

// Vários parâmetros
Calculadora divide = (a, b) -> a / b;

// Corpo com várias linhas: exige chaves e return explícito
Calculadora segura = (a, b) -> {
    if (b == 0) {
        return 0;
    }
    return a / b;
};
```

### As interfaces funcionais prontas (`java.util.function`)

Você raramente precisa criar as suas. O Java já traz as principais:

| Interface | Método | Recebe | Devolve | Uso típico |
| :--- | :--- | :--- | :--- | :--- |
| `Predicate<T>` | `test(T)` | um T | `boolean` | filtrar |
| `Function<T,R>` | `apply(T)` | um T | um R | transformar |
| `Consumer<T>` | `accept(T)` | um T | nada | executar ação |
| `Supplier<T>` | `get()` | nada | um T | fornecer valor |
| `BiFunction<T,U,R>` | `apply(T,U)` | dois | um R | combinar dois |
| `UnaryOperator<T>` | `apply(T)` | um T | um T | transformar mantendo o tipo |

```java
Predicate<String> ehLongo = s -> s.length() > 5;
System.out.println(ehLongo.test("Java"));        // false
System.out.println(ehLongo.test("JavaScript"));  // true

Function<String, Integer> tamanho = s -> s.length();
System.out.println(tamanho.apply("Renato"));     // 6

Consumer<String> exibir = s -> System.out.println(">> " + s);
exibir.accept("teste");                          // >> teste

Supplier<Double> aleatorio = () -> Math.random();
System.out.println(aleatorio.get());
```

### Method Reference: a lambda encurtada

Quando a lambda apenas chama um método existente, dá para apontar direto para ele com `::`:

```java
// Estas duas linhas fazem exatamente a mesma coisa
lista.forEach(nome -> System.out.println(nome));
lista.forEach(System.out::println);

// Método estático
Function<String, Integer> converter = Integer::parseInt;

// Método de instância de um objeto qualquer do tipo
Function<String, Integer> tam = String::length;

// Construtor
Supplier<ArrayList<String>> criar = ArrayList::new;
```

> [!IMPORTANT]
> **Variáveis capturadas devem ser efetivamente finais.** Uma lambda pode ler variáveis locais do método onde foi escrita, mas não pode alterá-las, e elas não podem mudar depois. Isso existe porque a lambda pode executar muito depois (ou em outra thread), quando aquela variável já não existiria mais na pilha.

---

## 4. Streams: o coração do módulo

Um **Stream** é um fluxo de dados sobre o qual você encadeia operações. Ele **não é** uma coleção: não guarda os dados, apenas os processa.

### O contraste que explica tudo

```java
// IMPERATIVO (como fazer): passo a passo, controlando o laço
List<String> resultado = new ArrayList<>();
for (Produto p : produtos) {
    if (p.getPreco() > 100) {
        String nome = p.getNome().toUpperCase();
        resultado.add(nome);
    }
}
Collections.sort(resultado);
```

```java
// DECLARATIVO (o que quero): descrevendo a intenção
List<String> resultado = produtos.stream()
        .filter(p -> p.getPreco() > 100)
        .map(p -> p.getNome().toUpperCase())
        .sorted()
        .toList();
```

### A anatomia de um Stream

Todo pipeline tem três partes:

1.  **Origem:** `lista.stream()`, `Arrays.stream(array)`, `Stream.of(a, b, c)`
2.  **Operações intermediárias:** devolvem outro Stream e podem ser encadeadas (`filter`, `map`, `sorted`, `distinct`, `limit`)
3.  **Operação terminal:** encerra o fluxo e produz o resultado (`collect`, `forEach`, `count`, `reduce`)

> [!WARNING]
> **Streams são preguiçosos (lazy).** As operações intermediárias **não executam nada** até que uma terminal seja chamada. O código abaixo não imprime absolutamente nada:
> ```java
> produtos.stream().filter(p -> { System.out.println("testando"); return true; });
> ```
> Sem operação terminal, o pipeline nunca roda.

> [!WARNING]
> **Um Stream é de uso único.** Depois da operação terminal ele está consumido; reutilizá-lo lança `IllegalStateException`. Para percorrer duas vezes, crie dois streams a partir da coleção.

---

## 5. Operações intermediárias

```java
List<Produto> produtos = /* ... */;

// filter: mantém apenas o que passa no teste (Predicate)
produtos.stream().filter(p -> p.getPreco() > 100)

// map: TRANSFORMA cada elemento em outra coisa (Function)
produtos.stream().map(Produto::getNome)          // Stream<Produto> vira Stream<String>

// mapToDouble/mapToInt: viram streams numéricos, que ganham sum(), average(), max()
produtos.stream().mapToDouble(Produto::getPreco).sum()

// sorted: ordena (natural ou por Comparator)
produtos.stream().sorted()
produtos.stream().sorted(Comparator.comparing(Produto::getNome))
produtos.stream().sorted(Comparator.comparingDouble(Produto::getPreco).reversed())

// distinct: remove duplicatas (usa equals/hashCode, exatamente como o Set)
produtos.stream().distinct()

// limit e skip: paginação
produtos.stream().skip(10).limit(5)              // do 11º ao 15º

// peek: espia sem alterar, útil só para depuração
produtos.stream().peek(System.out::println).filter(...)

// flatMap: achata listas dentro de listas
listaDeListas.stream().flatMap(List::stream)
```

### `Comparator` moderno

O Módulo 3 exigia uma classe anônima de 5 linhas para ordenar por nome. Agora:

```java
// Por um campo
.sorted(Comparator.comparing(Produto::getNome))

// Decrescente
.sorted(Comparator.comparingDouble(Produto::getPreco).reversed())

// Critério de desempate encadeado
.sorted(Comparator.comparing(Produto::getCategoria)
                  .thenComparing(Produto::getNome))
```

---

## 6. Operações terminais

```java
// collect: a mais usada, monta uma coleção
List<String> nomes = stream.collect(Collectors.toList());
List<String> nomes = stream.toList();            // Java 16+, mais curto (lista imutável)
Set<String> unicos = stream.collect(Collectors.toSet());
String juntos = stream.collect(Collectors.joining(", "));

// forEach: executa uma ação em cada elemento
stream.forEach(System.out::println);

// count: quantos elementos sobraram
long quantos = produtos.stream().filter(p -> p.getPreco() > 100).count();

// anyMatch / allMatch / noneMatch: devolvem boolean
boolean temCaro = produtos.stream().anyMatch(p -> p.getPreco() > 1000);
boolean todosBaratos = produtos.stream().allMatch(p -> p.getPreco() < 5000);

// findFirst / findAny: devolvem Optional
Optional<Produto> primeiro = produtos.stream().filter(...).findFirst();

// min / max: também devolvem Optional
Optional<Produto> maisCaro = produtos.stream().max(Comparator.comparingDouble(Produto::getPreco));

// reduce: combina todos os elementos em um só
double total = produtos.stream()
        .map(Produto::getPreco)
        .reduce(0.0, (a, b) -> a + b);           // 0.0 é o valor inicial
```

### Estatísticas prontas

```java
DoubleSummaryStatistics stats = produtos.stream()
        .mapToDouble(Produto::getPreco)
        .summaryStatistics();

stats.getCount();
stats.getSum();
stats.getAverage();
stats.getMin();
stats.getMax();
```

### `Collectors.groupingBy`: o agrupamento em uma linha

Lembra do Exemplo2 do Módulo 3, onde agrupar produtos por categoria levou 5 linhas com `putIfAbsent`? Agora:

```java
Map<String, List<Produto>> porCategoria = produtos.stream()
        .collect(Collectors.groupingBy(Produto::getCategoria));

// Agrupar e já contar
Map<String, Long> contagem = produtos.stream()
        .collect(Collectors.groupingBy(Produto::getCategoria, Collectors.counting()));

// Agrupar e somar
Map<String, Double> somaPorCategoria = produtos.stream()
        .collect(Collectors.groupingBy(Produto::getCategoria,
                 Collectors.summingDouble(Produto::getPreco)));

// Particionar em dois grupos (true/false)
Map<Boolean, List<Produto>> caroBarato = produtos.stream()
        .collect(Collectors.partitioningBy(p -> p.getPreco() > 500));
```

---

## 7. `Optional`: o fim do `NullPointerException`

O problema: um método que pode não encontrar nada devolve `null`, e quem chama esquece de verificar.

```java
Produto p = buscarPorNome("inexistente"); // devolve null
System.out.println(p.getPreco());          // NullPointerException
```

`Optional<T>` é uma caixa que **pode ou não** conter um valor, e cuja assinatura avisa isso explicitamente:

```java
public Optional<Produto> buscarPorNome(String nome) {
    return produtos.stream()
            .filter(p -> p.getNome().equals(nome))
            .findFirst(); // já devolve Optional
}
```

### Como consumir um `Optional`

```java
Optional<Produto> resultado = buscarPorNome("Mouse");

// Verificar e usar
if (resultado.isPresent()) {
    System.out.println(resultado.get().getPreco());
}

// Melhor: executa só se houver valor
resultado.ifPresent(p -> System.out.println(p.getPreco()));

// Com alternativa se estiver vazio
Produto p = resultado.orElse(new Produto("Padrão", 0));

// Alternativa calculada só se necessário (lazy)
Produto p = resultado.orElseGet(() -> criarProdutoPadrao());

// Lançar exceção se vazio
Produto p = resultado.orElseThrow(() -> new ProdutoNaoEncontradoException("Mouse"));

// Encadear transformações com segurança
String nome = resultado.map(Produto::getNome).orElse("desconhecido");
```

### Criando Optionals

```java
Optional<String> comValor = Optional.of("Java");        // erro se for null
Optional<String> talvez = Optional.ofNullable(pode);    // aceita null com segurança
Optional<String> vazio = Optional.empty();
```

> [!IMPORTANT]
> **Não use `Optional` como parâmetro de método nem como atributo de classe.** Ele foi desenhado para **retorno** de método, comunicando "isto pode não existir". Usá-lo em toda parte troca uma verificação de `null` por uma de `isPresent()`, sem ganho real. E `optional.get()` sem checar antes é o mesmo erro do `null` com outro nome.

---

## 8. Nova API de Datas (`java.time`)

As classes antigas (`Date`, `Calendar`, `SimpleDateFormat`) eram mutáveis, confusas (mês começava em 0) e inseguras entre threads. A partir do Java 8, use `java.time`, cujas classes são **imutáveis**.

```java
import java.time.*;
import java.time.format.DateTimeFormatter;

LocalDate hoje = LocalDate.now();                        // só data: 2026-08-10
LocalTime agora = LocalTime.now();                       // só hora: 14:30:15
LocalDateTime completo = LocalDateTime.now();            // data e hora

LocalDate nascimento = LocalDate.of(1995, 3, 15);        // mês 3 é março, sem truques
LocalDate natal = LocalDate.of(2026, Month.DECEMBER, 25);
```

### Operações (sempre devolvem um objeto novo)

```java
LocalDate amanha = hoje.plusDays(1);
LocalDate mesQueVem = hoje.plusMonths(1);
LocalDate anoPassado = hoje.minusYears(1);

// 'hoje' continua intacto: as classes são IMUTÁVEIS
System.out.println(hoje);
```

### Comparação e cálculo

```java
boolean passou = nascimento.isBefore(hoje);
boolean futuro = natal.isAfter(hoje);

long anos = ChronoUnit.YEARS.between(nascimento, hoje);   // idade
long dias = ChronoUnit.DAYS.between(hoje, natal);         // dias até o Natal

Period p = Period.between(nascimento, hoje);
System.out.printf("%d anos, %d meses, %d dias%n",
        p.getYears(), p.getMonths(), p.getDays());
```

### Formatação

```java
DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
System.out.println(hoje.format(br));                     // 10/08/2026

DateTimeFormatter completoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
System.out.println(LocalDateTime.now().format(completoBr));

// Texto para data
LocalDate lida = LocalDate.parse("25/12/2026", br);
```

---

## 9. Quando **não** usar Streams

Contraponto necessário, porque a empolgação com streams gera código pior:

*   **Laço simples com efeito colateral:** um `for` que só imprime é mais legível como `for`.
*   **Precisa de `break`:** streams não têm `break`. Existe `takeWhile`, mas em lógica complexa o laço vence.
*   **Precisa do índice:** streams não expõem índice naturalmente.
*   **Muita mutação de estado externo:** vai contra o modelo funcional e costuma dar bug.
*   **Desempenho crítico em coleção pequena:** o pipeline tem custo de montagem. Para 10 elementos, o `for` é mais rápido.

Streams brilham em **transformação de dados**: filtrar, mapear, agrupar, reduzir. É para isso que existem.

---

## 10. Resumo da evolução

| Tarefa | Java 7 | Java 8+ |
| :--- | :--- | :--- |
| Ordenar por campo | `Comparator` anônimo, 5 linhas | `Comparator.comparing(X::getY)` |
| Filtrar lista | `for` + `if` + `add` | `.stream().filter(...).toList()` |
| Somar campo | `for` acumulando | `.mapToDouble(...).sum()` |
| Agrupar | `Map` + `putIfAbsent` + `get` | `Collectors.groupingBy(...)` |
| Evitar null | `if (x != null)` espalhado | `Optional<T>` no retorno |
| Data formatada | `SimpleDateFormat` (não thread-safe) | `DateTimeFormatter` (imutável) |
