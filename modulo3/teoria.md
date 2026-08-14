# Módulo 3: Coleções de Dados e Estruturas

No Módulo 2 você criou objetos. Agora o problema é outro: **como guardar muitos deles**. Um sistema real não tem três produtos, tem milhares, e a quantidade muda o tempo todo.

Este módulo é sobre escolher a estrutura certa para cada situação, e entender o custo de cada escolha.

---

## 1. Arrays: o básico e seus limites

Um **array** é um bloco contíguo de memória com tamanho fixo, definido no momento da criação.

```java
// Forma 1: declarar com tamanho e preencher depois
int[] numeros = new int[5]; // 5 posições, todas com valor 0
numeros[0] = 10;
numeros[4] = 50;

// Forma 2: declarar já com os valores
String[] linguagens = {"Java", "C#", "TypeScript"};

System.out.println(linguagens.length);  // 3 — repare: 'length' é ATRIBUTO, sem parênteses
System.out.println(linguagens[0]);      // Java — índices começam em 0
```

> [!WARNING]
> **`ArrayIndexOutOfBoundsException`:** um array de tamanho 3 tem índices válidos de 0 a 2. Acessar `linguagens[3]` compila normalmente, mas explode em tempo de execução. O compilador não protege você aqui.

### A limitação fatal

```java
String[] nomes = new String[3];
// e se aparecer um quarto nome? Não existe nomes.adicionar(...)
```

O array não cresce. Para "adicionar" um elemento você precisaria criar um array maior e copiar tudo manualmente. É exatamente esse trabalho repetitivo que a API de Coleções automatiza.

### Matrizes (arrays multidimensionais)

Um array cujos elementos são outros arrays. Útil para tabelas, tabuleiros e planilhas.

```java
// 3 linhas x 4 colunas
int[][] matriz = new int[3][4];

matriz[0][0] = 1; // linha 0, coluna 0
matriz[2][3] = 99;

// Ou já preenchida:
int[][] tabuleiro = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Percorrendo com laços aninhados
for (int i = 0; i < tabuleiro.length; i++) {          // linhas
    for (int j = 0; j < tabuleiro[i].length; j++) {   // colunas daquela linha
        System.out.print(tabuleiro[i][j] + " ");
    }
    System.out.println(); // quebra a linha ao terminar
}
```

---

## 2. A hierarquia das Coleções

A partir daqui tudo vem do pacote `java.util` e obedece a uma hierarquia de interfaces:

```
                Iterable
                    |
                Collection
        ____________|____________
       |            |            |
     List          Set         Queue

    Map (fora da hierarquia Collection, mas parte da API)
```

*   **`List`:** admite duplicatas e mantém a **ordem de inserção**. Acesso por índice.
*   **`Set`:** **não** admite duplicatas. Geralmente sem ordem garantida.
*   **`Queue`:** fila, otimizada para inserir numa ponta e remover na outra.
*   **`Map`:** pares chave/valor. Não é uma `Collection`, mas anda junto com elas.

O padrão profissional é **declarar pela interface e instanciar pela implementação**:

```java
List<String> nomes = new ArrayList<>(); // ✔ flexível
// ArrayList<String> nomes = new ArrayList<>(); // funciona, mas te prende à implementação
```

Assim, trocar `ArrayList` por `LinkedList` depois exige mudar uma única linha, porque o resto do código só depende dos métodos de `List`.

---

## 3. Generics: `<>` e segurança de tipo

Aquele `<String>` é um **generic**. Ele diz ao compilador que tipo de objeto a coleção guarda.

```java
List<String> nomes = new ArrayList<>();
nomes.add("Renato");
nomes.add(42); // ERRO DE COMPILAÇÃO — e isso é ótimo

String primeiro = nomes.get(0); // sem cast: o compilador já sabe que é String
```

Sem generics (código antigo, anterior ao Java 5), tudo virava `Object` e exigia cast manual, com risco de `ClassCastException` em execução. Generics transformam esse erro de execução em erro de compilação.

O `<>` vazio do lado direito chama-se **diamond operator**: o compilador infere o tipo a partir da declaração da esquerda.

---

## 4. Wrappers e Autoboxing

Coleções só armazenam **objetos**, nunca primitivos. Para cada primitivo existe uma classe equivalente:

| Primitivo | Wrapper |
| :--- | :--- |
| `int` | `Integer` |
| `double` | `Double` |
| `boolean` | `Boolean` |
| `char` | `Character` |
| `long` | `Long` |

```java
List<Integer> idades = new ArrayList<>(); // não existe List<int>
idades.add(25);           // AUTOBOXING: o int 25 vira Integer automaticamente
int primeira = idades.get(0); // UNBOXING: o Integer vira int automaticamente
```

> [!WARNING]
> **A pegadinha do `==` com wrappers:** o Java mantém um cache de `Integer` de -128 a 127. Dentro dessa faixa, `Integer a = 100; Integer b = 100; a == b` dá `true`. Fora dela, `Integer a = 1000; Integer b = 1000; a == b` dá **`false`**, porque são objetos distintos. Wrapper é objeto: compare sempre com `.equals()`.

> [!WARNING]
> **`NullPointerException` no unboxing:** `Integer x = null; int y = x;` compila, mas explode em execução, porque `null` não tem valor primitivo correspondente.

---

## 5. `ArrayList`: a lista do dia a dia

É a implementação de `List` que você vai usar em 90% dos casos. Por dentro, é um array que se redimensiona sozinho quando enche.

```java
import java.util.ArrayList;
import java.util.List;

List<String> tarefas = new ArrayList<>();

// Inserir
tarefas.add("Estudar POO");           // adiciona no fim
tarefas.add("Fazer exercícios");
tarefas.add(0, "Tomar café");         // insere na posição 0, empurrando o resto

// Ler
String primeira = tarefas.get(0);     // "Tomar café"
int quantas = tarefas.size();         // 3 — repare: size() é MÉTODO, com parênteses

// Atualizar
tarefas.set(1, "Estudar Coleções");   // substitui o elemento do índice 1

// Remover
tarefas.remove(0);                    // remove pelo ÍNDICE
tarefas.remove("Fazer exercícios");   // remove pelo OBJETO

// Consultar
boolean existe = tarefas.contains("Estudar Coleções"); // true
int posicao = tarefas.indexOf("Estudar Coleções");     // 0 (ou -1 se não existir)
boolean vazia = tarefas.isEmpty();

// Percorrer
for (String t : tarefas) {
    System.out.println("- " + t);
}
```

> [!IMPORTANT]
> **`remove(int)` vs `remove(Object)`:** numa `List<Integer>`, `lista.remove(2)` remove o elemento da **posição 2**, enquanto `lista.remove(Integer.valueOf(2))` remove o **número 2**. É sobrecarga de método, e uma das pegadinhas mais cobradas em prova.

---

## 6. `LinkedList`: quando a ponta importa

`LinkedList` é uma lista duplamente encadeada: cada elemento guarda o endereço do anterior e do próximo.

```java
LinkedList<String> fila = new LinkedList<>();
fila.addFirst("Urgente");   // insere no começo, custo baixo
fila.addLast("Normal");
String primeiro = fila.removeFirst();
```

### ArrayList ou LinkedList?

| Operação | `ArrayList` | `LinkedList` |
| :--- | :--- | :--- |
| Acesso por índice `get(i)` | **rápido** (cálculo direto) | lento (percorre nó a nó) |
| Inserir/remover no **fim** | rápido | rápido |
| Inserir/remover no **começo ou meio** | lento (desloca elementos) | **rápido** (religa ponteiros) |
| Consumo de memória | menor | maior (guarda ponteiros) |

Na prática: **use `ArrayList` por padrão**. Só troque para `LinkedList` quando o gargalo real for inserção e remoção nas pontas, com pouco acesso por índice. Em código de produção, `ArrayList` vence na maioria das medições reais por causa da localidade de memória.

---

## 7. `Set`: garantindo unicidade

Um `Set` recusa duplicatas silenciosamente. O método `add()` devolve `false` quando o elemento já existe.

```java
Set<String> emails = new HashSet<>();
emails.add("renato@email.com");        // true
boolean adicionou = emails.add("renato@email.com"); // false — já existia
System.out.println(emails.size());     // 1
```

### As três implementações

```java
Set<String> hash = new HashSet<>();        // sem ordem garantida, o mais RÁPIDO
Set<String> linked = new LinkedHashSet<>(); // mantém a ORDEM DE INSERÇÃO
Set<String> tree = new TreeSet<>();         // mantém ORDENADO alfabeticamente/numericamente
```

```java
Set<String> nomes = new HashSet<>();
nomes.add("Zeca"); nomes.add("Ana"); nomes.add("Bruno");
System.out.println(nomes); // ordem imprevisível, ex: [Bruno, Ana, Zeca]

Set<String> ordenados = new TreeSet<>();
ordenados.add("Zeca"); ordenados.add("Ana"); ordenados.add("Bruno");
System.out.println(ordenados); // [Ana, Bruno, Zeca] — sempre ordenado
```

### Como o `HashSet` sabe que é duplicata?

Ele usa dois métodos herdados de `Object`: `hashCode()` e `equals()`. Primeiro compara o hash (rápido); só quando os hashes batem é que chama `equals()` para confirmar.

Consequência prática com objetos seus:

```java
Set<Produto> produtos = new HashSet<>();
produtos.add(new Produto("Mouse", 150.0));
produtos.add(new Produto("Mouse", 150.0)); // entra como DUPLICATA
System.out.println(produtos.size()); // 2, e não 1
```

Dois objetos diferentes na memória têm hashes diferentes por padrão. Para o `Set` reconhecê-los como iguais, **sobrescreva `equals()` e `hashCode()`**:

```java
public class Produto {
    private String nome;
    private double preco;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;                    // mesmo endereço, é igual
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto outro = (Produto) obj;
        return Double.compare(preco, outro.preco) == 0
                && Objects.equals(nome, outro.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, preco); // usa os MESMOS campos do equals
    }
}
```

> [!IMPORTANT]
> **O contrato:** se `a.equals(b)` é `true`, então `a.hashCode()` **deve** ser igual a `b.hashCode()`. Sobrescrever um sem o outro quebra o funcionamento de `HashSet` e `HashMap` de forma silenciosa: o objeto entra na coleção e some quando você tenta buscá-lo. Sempre sobrescreva os dois, usando os mesmos campos.

---

## 8. `Map`: pares chave e valor

`Map` associa uma **chave única** a um **valor**. Pense numa agenda telefônica: o nome é a chave, o telefone é o valor.

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Double> estoque = new HashMap<>();

// Inserir/atualizar
estoque.put("Mouse", 150.00);
estoque.put("Teclado", 320.00);
estoque.put("Mouse", 175.00); // SUBSTITUI o valor: chave duplicada não duplica entrada

// Ler
Double preco = estoque.get("Mouse");          // 175.0
Double inexistente = estoque.get("Monitor");  // null
Double comPadrao = estoque.getOrDefault("Monitor", 0.0); // 0.0 — evita null

// Consultar
boolean temChave = estoque.containsKey("Teclado");
boolean temValor = estoque.containsValue(320.00);
int tamanho = estoque.size();

// Remover
estoque.remove("Teclado");
```

### Percorrendo um Map

```java
// Só as chaves
for (String produto : estoque.keySet()) {
    System.out.println(produto);
}

// Só os valores
for (Double valor : estoque.values()) {
    System.out.println(valor);
}

// Chave e valor juntos: a forma preferida
for (Map.Entry<String, Double> entrada : estoque.entrySet()) {
    System.out.println(entrada.getKey() + " custa R$ " + entrada.getValue());
}
```

### Implementações de Map

| Implementação | Comportamento |
| :--- | :--- |
| `HashMap` | sem ordem, o mais rápido, uso geral |
| `LinkedHashMap` | mantém a ordem de inserção |
| `TreeMap` | mantém as chaves ordenadas |

> [!NOTE]
> A chave de um `HashMap` segue a mesma regra do `HashSet`: se for um objeto seu, sobrescreva `equals()` e `hashCode()`. `String` e os wrappers já vêm com os dois implementados corretamente, por isso funcionam sem esforço.

---

## 9. Ordenação de coleções

### Ordenação natural

```java
List<String> nomes = new ArrayList<>(List.of("Zeca", "Ana", "Bruno"));
Collections.sort(nomes);          // [Ana, Bruno, Zeca]
Collections.reverse(nomes);       // inverte a ordem atual
Collections.shuffle(nomes);       // embaralha

List<Integer> nums = new ArrayList<>(List.of(5, 1, 9));
Collections.sort(nums);           // [1, 5, 9]
System.out.println(Collections.max(nums)); // 9
System.out.println(Collections.min(nums)); // 1
```

Isso funciona porque `String` e `Integer` implementam a interface `Comparable`.

### `Comparable`: a ordem natural do seu objeto

Para ordenar objetos seus, implemente `Comparable<T>` e defina o critério padrão:

```java
public class Produto implements Comparable<Produto> {
    private String nome;
    private double preco;

    @Override
    public int compareTo(Produto outro) {
        // Regra do retorno:
        //   negativo -> este vem ANTES
        //   zero     -> empate
        //   positivo -> este vem DEPOIS
        return Double.compare(this.preco, outro.preco); // ordem natural: por preço
    }
}
```

```java
Collections.sort(listaDeProdutos); // agora funciona, ordenando por preço
```

> [!TIP]
> Para comparar números, use `Double.compare(a, b)` e `Integer.compare(a, b)` em vez de `(int)(a - b)`. A subtração pode estourar o limite do `int` e inverter o resultado silenciosamente.

### `Comparator`: ordens alternativas

`Comparable` define **uma** ordem natural. Quando você precisa de outras, usa `Comparator`:

```java
// Ordenar por nome, ignorando a ordem natural (preço)
listaDeProdutos.sort(new Comparator<Produto>() {
    @Override
    public int compare(Produto a, Produto b) {
        return a.getNome().compareTo(b.getNome());
    }
});
```

No Módulo 5 isso vai encolher para uma linha com lambdas:

```java
listaDeProdutos.sort(Comparator.comparing(Produto::getNome));
```

---

## 10. `Iterator` e a armadilha da remoção

Este erro pega quase todo mundo uma vez:

```java
List<String> nomes = new ArrayList<>(List.of("Ana", "Bruno", "Carla"));

for (String n : nomes) {
    if (n.startsWith("B")) {
        nomes.remove(n); // ConcurrentModificationException
    }
}
```

Modificar uma coleção enquanto o `for-each` a percorre quebra o iterador interno. As saídas corretas:

```java
// Opção 1: Iterator explícito, com o remove() dele
Iterator<String> it = nomes.iterator();
while (it.hasNext()) {
    String n = it.next();
    if (n.startsWith("B")) {
        it.remove(); // seguro: o iterador sabe do que aconteceu
    }
}

// Opção 2 (Java 8+): removeIf, mais direto
nomes.removeIf(n -> n.startsWith("B"));
```

---

## 11. Tabela de decisão rápida

| Preciso de... | Use |
| :--- | :--- |
| Lista com ordem e repetição, acesso por índice | `ArrayList` |
| Muita inserção/remoção nas pontas | `LinkedList` |
| Garantir que não há repetidos | `HashSet` |
| Sem repetidos, mantendo ordem de inserção | `LinkedHashSet` |
| Sem repetidos e sempre ordenado | `TreeSet` |
| Buscar valor por uma chave | `HashMap` |
| Buscar por chave, mantendo chaves ordenadas | `TreeMap` |
| Tamanho fixo e conhecido, performance máxima | array `[]` |
