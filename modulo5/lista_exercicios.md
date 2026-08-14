# Lista de Exercícios: Módulo 5 - Java Moderno (50 Questões)

Exercícios de **implementação**. Crie seus arquivos em `modulo5/respostas/` com o nome `ExercicioXX.java`.

> [!IMPORTANT]
> **Regra do módulo:** nos grupos C, D e E, resolva tudo com Streams, sem escrever nenhum `for`. O objetivo é treinar o pensamento declarativo. Depois de resolver, reescreva alguns com `for` e compare: você vai descobrir sozinho quais casos ficam melhores em cada estilo.

---

## Grupo A: Generics (1 a 8)

1. **Caixa Genérica:** Crie `Caixa<T>` com os métodos `guardar(T)` e `retirar()`. Teste com `String`, `Integer` e uma classe sua.
2. **Par Genérico:** Crie `Par<K, V>` com dois atributos e getters. Instancie um `Par<String, Double>` e um `Par<Integer, String>`.
3. **Trio Genérico:** Estenda a ideia para `Trio<A, B, C>` e sobrescreva `toString()`.
4. **Método Genérico:** Crie `public static <T> void imprimirArray(T[] array)` que exiba qualquer array.
5. **Primeiro Elemento:** Crie `public static <T> T primeiro(List<T> lista)` que devolva o primeiro item ou `null` se a lista estiver vazia.
6. **Limite com extends:** Crie `public static <T extends Number> double media(List<T> numeros)`. Teste com `List<Integer>` e `List<Double>`.
7. **Pilha Genérica:** Implemente `Pilha<T>` com `empilhar(T)`, `desempilhar()` e `estaVazia()`, usando uma `List` por dentro.
8. **Repositório Genérico:** Crie `Repositorio<T>` com `adicionar(T)`, `listarTodos()` e `buscar(Predicate<T>)`. Use com dois tipos diferentes.

---

## Grupo B: Interfaces Funcionais e Lambdas (9 a 18)

9. **Primeira Interface Funcional:** Crie `@FunctionalInterface interface Operacao { double aplicar(double a, double b); }` e implemente as quatro operações básicas com lambdas.
10. **Classe Anônima vs Lambda:** Implemente a mesma interface do exercício 9 das duas formas e compare o número de linhas.
11. **Predicate:** Crie três `Predicate<String>` (é vazio, tem mais de 10 caracteres, começa com vogal) e teste cada um.
12. **Combinando Predicates:** Use `.and()`, `.or()` e `.negate()` para combinar os predicados anteriores.
13. **Function:** Crie `Function<String, Integer>` que devolva o tamanho da String, e encadeie com `.andThen()` para dobrar o resultado.
14. **Consumer:** Crie um `Consumer<Produto>` que imprima uma etiqueta formatada e aplique-o com `forEach` numa lista.
15. **Supplier:** Crie um `Supplier<LocalDateTime>` que devolva o momento atual e chame-o três vezes com pausas, comprovando que ele executa a cada `get()`.
16. **BiFunction:** Crie uma `BiFunction<Double, Double, Double>` que calcule o preço com desconto a partir do valor e do percentual.
17. **Method Reference:** Reescreva cinco lambdas suas usando `::` (estático, de instância e de construtor).
18. **Lambda como Parâmetro:** Crie `filtrar(List<T>, Predicate<T>)` genérico e chame-o com três critérios diferentes, sem alterar o método.

---

## Grupo C: Streams Básicos (19 a 32)

19. **Primeiro Stream:** A partir de `List<Integer>`, filtre os pares e exiba com `forEach`.
20. **map Simples:** Transforme uma `List<String>` em uma lista com todos os nomes em maiúsculas.
21. **filter + map:** De uma `List<Produto>`, obtenha os nomes dos produtos com preço acima de 500.
22. **count:** Conte quantos produtos custam menos de 100.
23. **sorted:** Ordene uma lista de nomes alfabeticamente usando Streams.
24. **sorted com Comparator:** Ordene produtos por preço decrescente.
25. **Ordenação com Desempate:** Ordene produtos por categoria e, dentro dela, por nome.
26. **distinct:** A partir de uma lista com repetições, obtenha os elementos únicos.
27. **limit e skip:** Implemente paginação: exiba os itens 6 a 10 de uma lista de 20.
28. **anyMatch:** Verifique se existe algum produto acima de R$ 1.000.
29. **allMatch e noneMatch:** Verifique se todos os produtos têm preço positivo e se nenhum está zerado.
30. **findFirst:** Encontre o primeiro produto de determinada categoria e trate o `Optional` retornado.
31. **sum e average:** Calcule o total e a média dos preços com `mapToDouble`.
32. **summaryStatistics:** Obtenha count, sum, average, min e max de uma vez só.

---

## Grupo D: Collectors e Agrupamentos (33 a 42)

33. **toList e toSet:** Colete o resultado de um pipeline em uma `List` e depois em um `Set`, comparando os tamanhos.
34. **joining:** Junte os nomes de todos os produtos numa única String separada por vírgula.
35. **joining com Prefixo:** Use `Collectors.joining(", ", "[", "]")` e observe o resultado.
36. **groupingBy:** Agrupe produtos por categoria em um `Map<String, List<Produto>>`.
37. **groupingBy + counting:** Conte quantos produtos existem em cada categoria.
38. **groupingBy + summing:** Some o valor total de cada categoria.
39. **groupingBy + averaging:** Calcule o preço médio de cada categoria.
40. **partitioningBy:** Divida os produtos em caros e baratos usando R$ 500 como corte.
41. **toMap:** Crie um `Map<String, Double>` de nome para preço. Teste o que acontece com nomes duplicados e resolva com a função de merge.
42. **reduce:** Calcule o produto de todos os elementos de uma `List<Integer>` usando `reduce`.

---

## Grupo E: Optional e Datas (43 a 50)

43. **Retornando Optional:** Crie `buscarPorId(int id)` que devolva `Optional<Produto>` em vez de `null`.
44. **ifPresent e orElse:** Consuma o `Optional` do exercício anterior das duas formas, com e sem resultado.
45. **orElseThrow:** Faça a busca lançar uma exceção customizada quando não encontrar nada.
46. **map em Optional:** A partir de `Optional<Produto>`, obtenha `Optional<String>` com o nome, tratando o caso vazio.
47. **Calculadora de Idade:** Peça a data de nascimento e calcule a idade exata em anos, meses e dias com `Period`.
48. **Dias até uma Data:** Calcule quantos dias faltam para uma data futura usando `ChronoUnit.DAYS`.
49. **Formatação de Datas:** Formate a data atual em três padrões diferentes com `DateTimeFormatter` e leia uma data a partir de uma String.
50. **Sistema Completo de Análise:** Modele um sistema de pedidos de e-commerce, com `Pedido` (cliente, valor, data, status) e gere, **usando apenas Streams**:
    *   faturamento total e ticket médio;
    *   os 5 maiores pedidos;
    *   faturamento por cliente, ordenado do maior para o menor;
    *   contagem de pedidos por status;
    *   pedidos dos últimos 30 dias;
    *   o cliente que mais comprou, dentro de um `Optional`;
    *   um relatório mensal agrupado por mês.

---

## Gabarito Comentado

**Q5 (primeiro elemento):** devolver `null` aqui é justamente o problema que o `Optional` resolve. Refaça este exercício depois do Grupo E, mudando a assinatura para `Optional<T>`, e compare qual das duas versões é mais difícil de usar errado.

**Q6 (`extends Number`):** sem o limite, `T` seria tratado como `Object` e `doubleValue()` não existiria. O `extends` em generics significa "é este tipo ou um subtipo dele", e vale tanto para classes quanto para interfaces.

**Q10 (anônima vs lambda):** a classe anônima gasta 5 linhas de cerimônia (`new`, `@Override`, assinatura completa) para expressar uma linha de lógica. A lambda mantém só a lógica, porque o compilador infere o resto da interface funcional.

**Q12 (combinando Predicates):** `.and()`, `.or()` e `.negate()` devolvem **novos** Predicates; os originais permanecem intactos. Esse padrão de composição é o mesmo espírito imutável de `java.time`.

**Q15 (Supplier):** cada `.get()` executa a lambda de novo, por isso os horários saem diferentes. É essa característica que torna o `Supplier` útil em `orElseGet()`: a alternativa só é calculada quando realmente falta o valor.

**Q19 a Q21 (primeiros pipelines):** o erro mais comum aqui é esquecer a operação terminal. Sem `forEach`, `toList()` ou `collect()`, o pipeline não executa nada e o programa segue em silêncio.

**Q27 (paginação):** a ordem importa. `.skip(5).limit(5)` pega os itens 6 a 10; `.limit(5).skip(5)` corta para 5 e depois pula todos, devolvendo lista vazia.

**Q30 (`findFirst`):** ele devolve `Optional` porque o filtro pode não encontrar nada. Chamar `.get()` direto funciona no teste feliz e explode em produção; use `orElse` ou `ifPresent`.

**Q31 (`sum` e `average`):** `mapToDouble` é obrigatório, porque `Stream<Double>` não possui `sum()`. E `average()` devolve `OptionalDouble`, já que a média de uma lista vazia não existe.

**Q41 (`toMap` com duplicatas):** sem tratamento, chaves repetidas lançam `IllegalStateException: Duplicate key`. A solução é o terceiro parâmetro: `Collectors.toMap(chave, valor, (a, b) -> a)` mantém o primeiro, ou `(a, b) -> b` mantém o último. Esse detalhe cai em prova com frequência.

**Q42 (`reduce`):** para multiplicação, o valor inicial precisa ser `1`, não `0`. Com `0`, todo produto resulta em zero. O valor inicial é o **elemento neutro** da operação, e errá-lo é a falha clássica com `reduce`.

**Q43 a Q45 (Optional):** a diferença entre `orElse` e `orElseGet` é sutil e importante. `orElse(criarObjetoCaro())` executa o método **sempre**, mesmo quando o valor existe; `orElseGet(() -> criarObjetoCaro())` só executa quando falta o valor. Para valores literais, `orElse` é mais legível; para cálculos caros, `orElseGet` é o correto.

**Q47 (`Period` vs `ChronoUnit`):** `Period` devolve a diferença decomposta em anos, meses e dias, ideal para exibir idade. `ChronoUnit.YEARS.between()` devolve o total em uma única unidade, ideal para comparações. Usar o errado é a origem de bugs de idade calculada com um ano a mais.

**Q50 (sistema completo):** o resultado esperado é que o relatório inteiro, que no Módulo 3 exigiria dezenas de linhas de laços e mapas manuais, caiba em cerca de uma dezena de pipelines curtos. Se o seu código ficou longo, provavelmente você está misturando estilos: procure os `for` que sobraram e converta-os.
