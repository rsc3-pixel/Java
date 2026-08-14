# Lista de Exercícios: Módulo 3 - Coleções (50 Questões)

Exercícios de **implementação**. Crie seus arquivos em `modulo3/respostas/` com o nome `ExercicioXX.java`.

> [!TIP]
> Lembre dos imports: `java.util.ArrayList`, `java.util.List`, `java.util.HashMap`, `java.util.Map`, `java.util.HashSet`, `java.util.Set`, `java.util.Collections`, `java.util.Arrays`. No VS Code, `Ctrl + .` sobre o nome sublinhado importa automaticamente.

---

## Grupo A: Arrays e Matrizes (1 a 12)

1. **Soma de Array:** Crie um array com 10 inteiros e calcule a soma de todos.
2. **Média de Array:** Usando o mesmo array, calcule e exiba a média com duas casas decimais.
3. **Maior e Menor:** Encontre o maior e o menor valor de um array de inteiros percorrendo-o manualmente (sem usar `Arrays.sort`).
4. **Inverter Array:** Crie um array e exiba seus elementos na ordem inversa.
5. **Busca Linear:** Peça um número ao usuário e informe se ele existe no array e em qual posição (ou -1 se não existir).
6. **Contar Pares e Ímpares:** Percorra um array de 20 números e conte quantos são pares e quantos são ímpares.
7. **Copiar sem Duplicatas:** Dado um array com valores repetidos, gere um segundo array apenas com os valores distintos.
8. **Arrays Utilitário:** Use `Arrays.sort()`, `Arrays.toString()` e `Arrays.copyOf()` num array de notas e exiba o resultado de cada operação.
9. **Matriz Identidade:** Crie uma matriz 5x5 preenchida com 1 na diagonal principal e 0 no restante. Exiba formatada.
10. **Soma de Matrizes:** Crie duas matrizes 3x3 e gere uma terceira com a soma dos elementos correspondentes.
11. **Média por Linha:** Crie uma matriz 4x3 de notas (4 alunos, 3 provas) e exiba a média de cada aluno.
12. **Transposta:** Dada uma matriz 3x4, gere e exiba a matriz transposta (4x3), trocando linhas por colunas.

---

## Grupo B: ArrayList (13 a 24)

13. **Lista de Nomes:** Crie um `ArrayList<String>`, adicione 5 nomes e exiba todos com `for-each`.
14. **Inserir na Posição:** Insira um nome na posição 0 e outro no meio da lista. Exiba antes e depois.
15. **Remover por Valor e Índice:** Demonstre a diferença entre `remove(0)` e `remove("Ana")` numa mesma lista.
16. **Pegadinha do Integer:** Crie um `List<Integer>` com os valores 10, 20, 30. Mostre a diferença entre `remove(1)` e `remove(Integer.valueOf(20))`.
17. **Buscar Elemento:** Peça um nome ao usuário e informe se ele está na lista, usando `contains()` e `indexOf()`.
18. **Atualizar Elemento:** Peça uma posição e um novo valor, e atualize a lista com `set()`, validando o índice antes.
19. **Lista de Objetos:** Crie a classe `Contato` (nome, telefone) e uma `List<Contato>` com 5 contatos. Exiba todos.
20. **Filtrar Lista:** A partir de uma `List<Integer>` com 20 números, crie uma segunda lista apenas com os múltiplos de 3.
21. **Somar Lista de Objetos:** Crie `List<Produto>` e some o preço de todos os produtos.
22. **Média de Objetos:** Usando `List<Aluno>`, calcule a média geral da turma e liste quem está acima da média.
23. **removeIf:** Numa lista de nomes, remova todos os que começam com a letra "A" usando `removeIf`.
24. **ConcurrentModificationException:** Escreva o código errado (removendo dentro de um `for-each`), rode, observe a exceção e depois corrija usando `Iterator`. Comente o motivo no arquivo.

---

## Grupo C: Set (25 a 32)

25. **Removendo Duplicatas:** Dada uma `List<String>` com repetições, crie um `HashSet` a partir dela e exiba a diferença de tamanho.
26. **Retorno do add:** Demonstre que `add()` retorna `false` ao tentar inserir um elemento repetido.
27. **Comparando Sets:** Insira os mesmos 5 elementos (com repetição) em `HashSet`, `LinkedHashSet` e `TreeSet`, e compare as ordens de saída.
28. **Set de Objetos sem equals:** Crie a classe `Livro` **sem** sobrescrever `equals`/`hashCode`, adicione dois livros idênticos num `HashSet` e observe que ambos entram.
29. **Set de Objetos com equals:** Sobrescreva `equals` e `hashCode` no `Livro` do exercício anterior e comprove que a duplicata passa a ser recusada.
30. **União de Conjuntos:** Dados dois `Set<String>`, gere a união deles usando `addAll()`.
31. **Interseção:** Dados dois conjuntos, gere a interseção usando `retainAll()` numa cópia.
32. **Diferença:** Dados dois conjuntos, gere a diferença (elementos do primeiro que não estão no segundo) usando `removeAll()` numa cópia.

---

## Grupo D: Map (33 a 42)

33. **Agenda Telefônica:** Crie um `Map<String, String>` com nome e telefone, adicione 5 contatos e busque um pelo nome.
34. **Chave Duplicada:** Faça `put()` duas vezes com a mesma chave e valores diferentes. Explique num comentário o que aconteceu.
35. **getOrDefault:** Busque uma chave que não existe usando `get()` e depois `getOrDefault()`, comparando o resultado.
36. **Percorrendo com entrySet:** Exiba todas as entradas de um `Map` no formato `chave -> valor`.
37. **keySet e values:** Exiba primeiro apenas as chaves e depois apenas os valores do mesmo mapa.
38. **Contador de Caracteres:** Dada uma String, conte quantas vezes cada caractere aparece usando `Map<Character, Integer>`.
39. **Contador de Palavras:** Dada uma frase, conte a ocorrência de cada palavra usando `split(" ")` e um `HashMap`.
40. **Estoque:** Crie `Map<String, Integer>` de produtos e quantidades, e implemente os métodos `entrada(produto, qtd)` e `saida(produto, qtd)` validando o estoque.
41. **TreeMap Ordenado:** Refaça o exercício 39 usando `TreeMap` e mostre que as palavras saem em ordem alfabética.
42. **Map de Listas:** Crie um `Map<String, List<String>>` agrupando alunos por curso, e exiba o relatório completo.

---

## Grupo E: Ordenação e Integração (43 a 50)

43. **Ordenação Natural:** Ordene uma `List<String>` e uma `List<Integer>` com `Collections.sort()`.
44. **Reverse e Shuffle:** Aplique `Collections.reverse()` e `Collections.shuffle()` numa lista e exiba os resultados.
45. **max e min:** Use `Collections.max()` e `Collections.min()` numa lista de números e numa de Strings.
46. **Comparable:** Faça a classe `Produto` implementar `Comparable<Produto>` ordenando por preço, e ordene uma lista com `Collections.sort()`.
47. **Comparator:** Sem alterar a classe `Produto`, ordene a mesma lista por nome usando um `Comparator`.
48. **Ordem Decrescente:** Ordene a lista de produtos do mais caro para o mais barato usando `Collections.reverseOrder()`.
49. **Ranking de Alunos:** Crie `List<Aluno>` (nome e média), ordene por média decrescente e exiba um ranking numerado com os 3 primeiros destacados.
50. **Sistema Integrado:** Modele um **controle de biblioteca** usando as três estruturas ao mesmo tempo:
    *   `Map<String, Livro>` indexando livros por ISBN;
    *   `Set<String>` com os ISBNs atualmente emprestados;
    *   `List<Livro>` para gerar o catálogo ordenado por título.
    Implemente `emprestar(isbn)`, `devolver(isbn)` e `listarDisponiveis()`.

---

## Gabarito Comentado

**Q3 (maior e menor manualmente):** inicialize `maior` e `menor` com o **primeiro elemento** do array, não com 0. Começar com 0 quebra quando todos os valores são negativos.

**Q7 (copiar sem duplicatas):** dá para resolver com laços aninhados, mas repare que este é exatamente o problema que o `Set` resolve de graça. Faça das duas formas e compare o tamanho do código.

**Q12 (transposta):** o segredo é `transposta[j][i] = original[i][j]`. Se a original é 3x4, a transposta precisa ser declarada como `new int[4][3]`.

**Q15 e Q16 (a pegadinha do remove):** numa `List<Integer>`, `remove(1)` chama a sobrecarga `remove(int index)` e apaga a **posição** 1. Para remover o **valor** 1, é preciso `remove(Integer.valueOf(1))`. Com `List<String>` a ambiguidade não existe, porque não há sobrecarga aplicável.

**Q24 (`ConcurrentModificationException`):** o `for-each` usa um iterador interno que guarda um contador de modificações. Alterar a lista por fora invalida esse contador. As saídas são `it.remove()` pelo `Iterator` explícito, ou `removeIf()`.

**Q28 e Q29 (Set sem/com equals):** por padrão, `hashCode()` deriva do endereço de memória, então dois objetos "iguais" caem em posições diferentes da tabela hash e ambos entram. Ao sobrescrever os dois métodos usando os mesmos campos, o `Set` passa a reconhecer a duplicata.

**Q30 a Q32 (operações de conjunto):** trabalhe sempre numa **cópia** (`new HashSet<>(a)`), porque `retainAll` e `removeAll` alteram o conjunto original. Isso vale como regra geral: métodos que retornam `void` ou `boolean` costumam modificar no lugar.

**Q34 (chave duplicada):** `put()` com chave existente **substitui** o valor e retorna o valor antigo. Não existe chave repetida em um `Map`.

**Q38 e Q39 (contadores):** o padrão é `map.put(chave, map.getOrDefault(chave, 0) + 1)`. A partir do Java 8 há a forma mais curta: `map.merge(chave, 1, Integer::sum)`.

**Q46 (`Comparable`):** o retorno de `compareTo` deve ser negativo, zero ou positivo, e não obrigatoriamente -1, 0 e 1. Use `Double.compare(a, b)` em vez de `(int)(a - b)`: a subtração pode estourar o `int` e inverter o resultado.

**Q47 (`Comparable` x `Comparator`):** `Comparable` fica **dentro** da classe e define a ordem natural, única. `Comparator` fica **fora** e permite quantas ordens alternativas você quiser, sem tocar na classe original. É a diferença entre "como este objeto se ordena" e "como eu quero ordenar desta vez".

**Q49 (ranking decrescente):** ordene por média com `Comparable` e depois aplique `Collections.reverse()`, ou passe `Collections.reverseOrder()` direto ao `sort`. Evite escrever um `compareTo` invertido: isso torna a ordem natural contraintuitiva para quem ler a classe depois.

**Q50 (sistema integrado):** o ponto do exercício é perceber que cada estrutura resolve um problema distinto no mesmo sistema. O `Map` dá busca por ISBN em tempo constante; o `Set` responde "está emprestado?" sem varrer nada; a `List` permite ordenar para exibição. Escolher a estrutura errada não quebra o programa, mas o deixa lento e o código mais longo.
