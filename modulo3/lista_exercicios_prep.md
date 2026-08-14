# Lista de Exercícios Prep: Módulo 3 - Coleções (50 Questões)

Simulado preparatório sobre arrays, matrizes, `List`, `Set`, `Map`, generics, wrappers, `equals`/`hashCode` e ordenação. Gabarito comentado ao final.

---

## Grupo A: Arrays e Matrizes (1 a 10)

### 1. Qual a saída do código abaixo?
```java
int[] v = new int[3];
System.out.println(v[0] + " " + v.length);
```
*   a) `null 3`
*   b) `0 3`
*   c) Erro: o array não foi inicializado.
*   d) `0 2`

### 2. Qual a diferença entre `array.length` e `lista.size()`?
*   a) Nenhuma, são sinônimos.
*   b) `length` é um atributo de array (sem parênteses); `size()` é um método de coleções (com parênteses).
*   c) `length` é método e `size()` é atributo.
*   d) `length` conta apenas posições preenchidas.

### 3. O que acontece ao executar `int[] v = {1,2,3}; System.out.println(v[3]);`?
*   a) Erro de compilação.
*   b) Imprime `0`.
*   c) Lança `ArrayIndexOutOfBoundsException` em tempo de execução.
*   d) Imprime `null`.

### 4. Como declarar corretamente uma matriz de 3 linhas por 5 colunas?
*   a) `int matriz[3][5];`
*   b) `int[][] matriz = new int[3][5];`
*   c) `int[] matriz = new int[3,5];`
*   d) `Array matriz = new Array(3,5);`

### 5. Em `int[][] m = new int[4][6];`, qual o valor de `m.length` e `m[0].length`?
*   a) `6` e `4`
*   b) `4` e `6`
*   c) `24` e `24`
*   d) `4` e `4`

### 6. O que `Arrays.toString(v)` faz?
*   a) Converte cada elemento em String individualmente.
*   b) Devolve uma representação legível do array, como `[1, 2, 3]`.
*   c) Ordena e depois imprime.
*   d) Retorna o endereço de memória do array.

### 7. Qual será a saída?
```java
int[] a = {3, 1, 2};
int[] b = a;
Arrays.sort(b);
System.out.println(Arrays.toString(a));
```
*   a) `[3, 1, 2]`
*   b) `[1, 2, 3]`
*   c) Erro de compilação.
*   d) `[2, 1, 3]`

### 8. Qual a principal limitação de um array em relação a um `ArrayList`?
*   a) Arrays não aceitam objetos.
*   b) Arrays têm tamanho fixo definido na criação e não crescem.
*   c) Arrays não podem ser percorridos com `for-each`.
*   d) Arrays não aceitam tipos primitivos.

### 9. Qual estrutura é mais eficiente em memória para 1 milhão de inteiros?
*   a) `ArrayList<Integer>`
*   b) `int[]`, pois evita o overhead de objetos wrapper.
*   c) `HashSet<Integer>`
*   d) `LinkedList<Integer>`

### 10. Como percorrer corretamente uma matriz `int[][] m`?
*   a) Um único `for` até `m.length * m[0].length`.
*   b) Dois laços aninhados: o externo sobre `m.length`, o interno sobre `m[i].length`.
*   c) Apenas `for-each` simples.
*   d) Matrizes não podem ser percorridas com laços.

---

## Grupo B: List, Generics e Wrappers (11 a 22)

### 11. Qual é a forma recomendada de declarar uma lista?
*   a) `ArrayList<String> l = new ArrayList<>();`
*   b) `List<String> l = new ArrayList<>();` — declarar pela interface facilita trocar a implementação.
*   c) `List l = new List();`
*   d) `Collection<String> l = new List<>();`

### 12. Para que serve o `<String>` em `List<String>`?
*   a) Apenas documentação; é ignorado pelo compilador.
*   b) É um generic: garante em tempo de compilação que só Strings entram na lista e dispensa cast na leitura.
*   c) Define o tamanho máximo da lista.
*   d) Converte automaticamente qualquer tipo para String.

### 13. Por que `List<int>` não compila?
*   a) Porque `int` é palavra reservada.
*   b) Porque coleções armazenam apenas objetos; primitivos precisam de wrappers, como `List<Integer>`.
*   c) Porque falta o import.
*   d) Porque `int` não tem `equals()`.

### 14. O que é autoboxing?
*   a) A conversão automática entre primitivo e seu wrapper correspondente.
*   b) A criação automática de objetos pelo Garbage Collector.
*   c) A ordenação automática de coleções.
*   d) O redimensionamento automático do `ArrayList`.

### 15. Qual a saída?
```java
List<Integer> l = new ArrayList<>(List.of(10, 20, 30));
l.remove(1);
System.out.println(l);
```
*   a) `[10, 30]`
*   b) `[20, 30]`
*   c) `[10, 20, 30]`
*   d) `[10, 20]`

### 16. E qual a saída deste?
```java
List<Integer> l = new ArrayList<>(List.of(10, 20, 30));
l.remove(Integer.valueOf(10));
System.out.println(l);
```
*   a) `[20, 30]`
*   b) `[10, 30]`
*   c) `[10, 20]`
*   d) Erro de compilação.

### 17. Qual a saída deste trecho?
```java
Integer a = 1000, b = 1000;
System.out.println(a == b);
System.out.println(a.equals(b));
```
*   a) `true true`
*   b) `false true`
*   c) `true false`
*   d) `false false`

### 18. O que acontece em `Integer x = null; int y = x;`?
*   a) `y` recebe `0`.
*   b) Erro de compilação.
*   c) `NullPointerException` em tempo de execução, durante o unboxing.
*   d) `y` recebe `null`.

### 19. Qual método adiciona um elemento numa posição específica da lista?
*   a) `add(elemento)`
*   b) `add(indice, elemento)`
*   c) `set(indice, elemento)`
*   d) `insert(indice, elemento)`

### 20. Qual a diferença entre `set(1, "X")` e `add(1, "X")`?
*   a) Nenhuma.
*   b) `set` substitui o elemento do índice 1; `add` insere na posição 1 e desloca o resto.
*   c) `set` insere e `add` substitui.
*   d) `set` só funciona em `LinkedList`.

### 21. Quando `LinkedList` é preferível a `ArrayList`?
*   a) Sempre, pois é mais moderna.
*   b) Quando há muitas inserções e remoções nas extremidades e pouco acesso por índice.
*   c) Quando o acesso aleatório por índice é frequente.
*   d) Quando é preciso evitar duplicatas.

### 22. O que `indexOf()` retorna quando o elemento não existe na lista?
*   a) `0`
*   b) `null`
*   c) `-1`
*   d) Lança exceção.

---

## Grupo C: Set, equals e hashCode (23 a 34)

### 23. Qual a característica definidora de um `Set`?
*   a) Manter os elementos ordenados.
*   b) Não permitir elementos duplicados.
*   c) Permitir acesso por índice.
*   d) Armazenar pares chave-valor.

### 24. O que `add()` retorna ao tentar inserir um elemento já existente num `Set`?
*   a) `true`
*   b) `false`
*   c) `null`
*   d) Lança `DuplicateElementException`.

### 25. Qual implementação de `Set` mantém a ordem de inserção?
*   a) `HashSet`
*   b) `TreeSet`
*   c) `LinkedHashSet`
*   d) Nenhuma mantém ordem.

### 26. Qual implementação de `Set` mantém os elementos ordenados?
*   a) `HashSet`
*   b) `LinkedHashSet`
*   c) `TreeSet`
*   d) `ArraySet`

### 27. Como o `HashSet` identifica que um elemento é duplicado?
*   a) Comparando com `==`.
*   b) Comparando primeiro `hashCode()` e, quando há coincidência, confirmando com `equals()`.
*   c) Percorrendo todos os elementos com `equals()`.
*   d) Usando `compareTo()`.

### 28. Qual a saída?
```java
class P { String n; P(String n){ this.n = n; } }
// no main:
Set<P> s = new HashSet<>();
s.add(new P("a"));
s.add(new P("a"));
System.out.println(s.size());
```
*   a) `1`
*   b) `2`
*   c) `0`
*   d) Erro de compilação.

### 29. Qual é o contrato entre `equals()` e `hashCode()`?
*   a) São independentes.
*   b) Se dois objetos são iguais por `equals()`, seus `hashCode()` devem ser iguais.
*   c) Se os `hashCode()` são iguais, os objetos são obrigatoriamente iguais.
*   d) `hashCode()` deve retornar sempre um valor diferente para cada objeto.

### 30. O que acontece ao sobrescrever `equals()` sem sobrescrever `hashCode()`?
*   a) Erro de compilação.
*   b) O objeto passa a funcionar corretamente em `HashSet` e `HashMap`.
*   c) Coleções baseadas em hash quebram silenciosamente: duplicatas são aceitas e buscas falham.
*   d) Nada muda.

### 31. Qual método gera um hash consistente a partir de vários campos?
*   a) `Objects.hash(campo1, campo2)`
*   b) `String.valueOf(campo1)`
*   c) `Math.random()`
*   d) `System.identityHashCode()`

### 32. Qual operação de conjunto `retainAll()` realiza?
*   a) União
*   b) Interseção (mantém apenas os elementos presentes em ambos)
*   c) Diferença
*   d) Cópia

### 33. `TreeSet` consegue armazenar objetos de uma classe que não implementa `Comparable`?
*   a) Sim, sem problema algum.
*   b) Não, salvo se um `Comparator` for passado no construtor; caso contrário lança `ClassCastException`.
*   c) Sim, ele usa `hashCode()` para ordenar.
*   d) Sim, mas a ordem fica aleatória.

### 34. Qual estrutura escolher para armazenar CPFs únicos com busca rápida?
*   a) `ArrayList<String>`
*   b) `HashSet<String>`
*   c) `LinkedList<String>`
*   d) `int[]`

---

## Grupo D: Map (35 a 44)

### 35. `Map` faz parte da interface `Collection`?
*   a) Sim, é uma subinterface direta.
*   b) Não. Faz parte da API de Coleções, mas tem hierarquia própria por trabalhar com pares.
*   c) Sim, herda de `List`.
*   d) Não, `Map` pertence a outro pacote.

### 36. O que acontece ao chamar `put()` com uma chave já existente?
*   a) Lança exceção.
*   b) Cria uma segunda entrada com a mesma chave.
*   c) Substitui o valor antigo e retorna o valor anterior.
*   d) Ignora a operação.

### 37. O que `get()` retorna para uma chave inexistente?
*   a) `0`
*   b) `null`
*   c) String vazia
*   d) Lança `NoSuchElementException`.

### 38. Qual a vantagem de `getOrDefault(chave, padrao)`?
*   a) É mais rápido que `get()`.
*   b) Devolve um valor padrão quando a chave não existe, evitando tratar `null`.
*   c) Insere a chave automaticamente.
*   d) Ordena o mapa.

### 39. Qual a forma mais adequada de percorrer chaves e valores juntos?
*   a) `for (String k : map.keySet())` e depois `map.get(k)`
*   b) `for (Map.Entry<K,V> e : map.entrySet())`
*   c) `for (V v : map.values())`
*   d) Não é possível percorrer os dois juntos.

### 40. Qual será a saída?
```java
Map<String,Integer> m = new HashMap<>();
m.put("a", 1);
m.put("b", 2);
m.put("a", 3);
System.out.println(m.size() + " " + m.get("a"));
```
*   a) `3 1`
*   b) `2 3`
*   c) `3 3`
*   d) `2 1`

### 41. Qual implementação mantém as chaves ordenadas?
*   a) `HashMap`
*   b) `LinkedHashMap`
*   c) `TreeMap`
*   d) `Hashtable`

### 42. Qual o padrão correto para contar ocorrências num `Map<String,Integer>`?
*   a) `map.put(k, map.get(k) + 1)`
*   b) `map.put(k, map.getOrDefault(k, 0) + 1)`
*   c) `map.add(k, 1)`
*   d) `map.merge(k)`

### 43. Um `Map` pode ter valores duplicados?
*   a) Não, nem chaves nem valores podem repetir.
*   b) Sim. Apenas as chaves precisam ser únicas; os valores podem repetir livremente.
*   c) Apenas se for `TreeMap`.
*   d) Apenas se os valores forem primitivos.

### 44. Ao usar um objeto seu como **chave** de `HashMap`, o que é indispensável?
*   a) Implementar `Comparable`.
*   b) Sobrescrever `equals()` e `hashCode()`.
*   c) Marcar a classe como `final`.
*   d) Implementar `Serializable`.

---

## Grupo E: Ordenação e Iteração (45 a 50)

### 45. O que o retorno de `compareTo()` significa?
*   a) `true` para maior, `false` para menor.
*   b) Negativo se este objeto vem antes, zero se empata, positivo se vem depois.
*   c) Sempre -1, 0 ou 1, obrigatoriamente.
*   d) A diferença numérica exata entre os objetos.

### 46. Qual a diferença entre `Comparable` e `Comparator`?
*   a) São idênticos.
*   b) `Comparable` fica dentro da classe e define a ordem natural única; `Comparator` fica fora e permite várias ordens alternativas.
*   c) `Comparable` só funciona com números.
*   d) `Comparator` é obsoleto desde o Java 8.

### 47. Por que `return (int)(this.preco - outro.preco);` é uma implementação arriscada de `compareTo`?
*   a) Porque não compila.
*   b) Porque a subtração pode estourar o limite do tipo e inverter o resultado, além de truncar diferenças decimais menores que 1.
*   c) Porque `compareTo` precisa retornar `boolean`.
*   d) Porque `preco` precisa ser `int`.

### 48. O que causa `ConcurrentModificationException`?
*   a) Usar coleções em múltiplas threads apenas.
*   b) Modificar a coleção (add/remove) enquanto ela é percorrida por um `for-each`.
*   c) Ordenar uma lista já ordenada.
*   d) Chamar `size()` dentro do laço.

### 49. Qual a forma correta de remover elementos durante a iteração?
*   a) `lista.remove(x)` dentro do `for-each`.
*   b) Usar `Iterator` explícito com `it.remove()`, ou o método `removeIf()`.
*   c) Não é possível remover durante iteração.
*   d) Usar `break` após cada remoção.

### 50. Um sistema precisa buscar produtos por código com máxima velocidade, garantir que não haja códigos repetidos e ainda exibir um catálogo ordenado por nome. Qual combinação faz sentido?
*   a) Três `ArrayList` separadas.
*   b) `Map` indexado pelo código (busca e unicidade da chave) e uma `List` ordenada por nome para exibição.
*   c) Apenas um `HashSet`.
*   d) Uma matriz bidimensional.

---

# GABARITO COMENTADO

## Grupo A

**1. b)** `0 3`. Arrays de tipos numéricos nascem com 0, de `boolean` com `false` e de referências com `null`.

**2. b)** `length` é atributo de array; `size()` é método de coleção. Detalhe extra: `String` usa `length()`, com parênteses. São três formas diferentes, e a confusão entre elas é clássica.

**3. c)** `ArrayIndexOutOfBoundsException`. Compila normalmente: o compilador não avalia índices em tempo de compilação.

**4. b)** `int[][] matriz = new int[3][5];`. A sintaxe `int matriz[3][5]` é de C, não de Java.

**5. b)** `4` e `6`. `m.length` conta as linhas; `m[0].length` conta as colunas da primeira linha. Em matrizes irregulares, cada linha pode ter comprimento diferente.

**6. b)** Devolve `[1, 2, 3]`. Sem ele, `System.out.println(v)` imprime o hash do array.

**7. b)** `[1, 2, 3]`. `b = a` copia a **referência**: existe um único array. Ordenar por `b` ordena o mesmo objeto que `a` aponta. Para copiar de verdade, use `Arrays.copyOf()`.

**8. b)** Tamanho fixo. É a razão de existir da API de Coleções.

**9. b)** `int[]`. Cada `Integer` é um objeto com cabeçalho próprio, consumindo bem mais que os 4 bytes de um `int`, além de fragmentar a memória.

**10. b)** Dois laços aninhados, usando `m[i].length` no interno (e não `m[0].length`), o que torna o código correto mesmo em matrizes irregulares.

## Grupo B

**11. b)** Declarar pela interface. Trocar a implementação depois exige mudar uma linha só.

**12. b)** Segurança de tipo em tempo de compilação e leitura sem cast. Generics transformam um erro de execução em erro de compilação.

**13. b)** Coleções guardam objetos. Daí a necessidade dos wrappers.

**14. a)** Conversão automática primitivo ↔ wrapper, introduzida no Java 5.

**15. a)** `[10, 30]`. `remove(1)` casa com a sobrecarga `remove(int index)` e apaga a **posição** 1.

**16. a)** `[20, 30]`. `Integer.valueOf(10)` força a sobrecarga `remove(Object)`, que apaga pelo **valor**.

**17. b)** `false true`. O cache de `Integer` vai de -128 a 127; 1000 está fora, então são objetos distintos. Regra prática: wrapper é objeto, compare com `.equals()`.

**18. c)** `NullPointerException` durante o unboxing. `null` não tem primitivo correspondente.

**19. b)** `add(indice, elemento)`.

**20. b)** `set` substitui, `add` insere e desloca. `set` não muda o tamanho da lista; `add` aumenta em um.

**21. b)** Inserções e remoções nas pontas, com pouco acesso por índice. Na prática, `ArrayList` ganha na maioria das medições reais por causa da localidade de memória.

**22. c)** `-1`. Nunca `null`, porque o retorno é `int` primitivo.

## Grupo C

**23. b)** Não permitir duplicatas. Ordem é característica da implementação escolhida, não do `Set`.

**24. b)** `false`. O `add` de `Set` retorna se a coleção foi realmente modificada.

**25. c)** `LinkedHashSet`.

**26. c)** `TreeSet`, que usa `compareTo` ou um `Comparator`.

**27. b)** Hash primeiro (barato), `equals()` depois para confirmar. Essa ordem é o motivo de o contrato entre os dois métodos existir.

**28. b)** `2`. Sem `equals`/`hashCode` sobrescritos, cada objeto é único pelo endereço.

**29. b)** `equals` iguais implica `hashCode` iguais. A recíproca **não** vale: hashes iguais podem ocorrer em objetos diferentes, o que se chama colisão e é normal.

**30. c)** Quebra silenciosa: entra duplicata e a busca falha. Silencioso é o pior tipo de bug, porque não há exceção apontando o erro.

**31. a)** `Objects.hash(...)`, que aceita vários campos e trata `null`.

**32. b)** Interseção. Trabalhe numa cópia, porque ele modifica o conjunto original.

**33. b)** Precisa de `Comparable` ou de um `Comparator` no construtor; sem isso, `ClassCastException` na primeira inserção.

**34. b)** `HashSet<String>`: garante unicidade e busca em tempo praticamente constante. Numa `ArrayList`, o `contains` percorreria a lista inteira.

## Grupo D

**35. b)** `Map` não estende `Collection`. Faz parte da API, mas tem hierarquia própria.

**36. c)** Substitui e retorna o valor anterior (ou `null` se não havia).

**37. b)** `null`. Por isso `getOrDefault` existe.

**38. b)** Evita o tratamento de `null`, especialmente útil em contadores e acumuladores.

**39. b)** `entrySet()`. Usar `keySet()` e depois `get()` faz uma busca extra por elemento, desnecessária.

**40. b)** `2 3`. A chave `"a"` não duplicou, apenas teve o valor substituído.

**41. c)** `TreeMap`.

**42. b)** `map.put(k, map.getOrDefault(k, 0) + 1)`. A alternativa `a` lança `NullPointerException` na primeira ocorrência. Desde o Java 8 há também `map.merge(k, 1, Integer::sum)`.

**43. b)** Valores podem repetir; só as chaves são únicas.

**44. b)** `equals()` e `hashCode()`, pela mesma razão do `HashSet`. Um detalhe extra: chaves devem ser imutáveis, porque alterar um campo usado no hash depois de inserir faz o objeto "sumir" do mapa.

## Grupo E

**45. b)** Negativo, zero ou positivo. Não precisa ser exatamente -1, 0 e 1.

**46. b)** Ordem natural única, dentro da classe, versus ordens alternativas, fora dela.

**47. b)** Risco de estouro do `int` e truncamento de decimais. Use `Double.compare()` ou `Integer.compare()`.

**48. b)** Modificação estrutural durante a iteração. O iterador detecta pelo contador interno de modificações.

**49. b)** `Iterator.remove()` ou `removeIf()`.

**50. b)** `Map` para busca e unicidade da chave, `List` ordenada para exibição. Cada estrutura resolve um requisito diferente do mesmo sistema, e é exatamente esse raciocínio de escolha que o módulo inteiro treina.
