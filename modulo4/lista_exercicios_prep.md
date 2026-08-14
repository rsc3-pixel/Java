# Lista de Exercícios Prep: Módulo 4 - Exceções e Arquivos (50 Questões)

Simulado preparatório sobre a hierarquia de exceções, checked vs unchecked, `try/catch/finally`, `try-with-resources`, `throw`/`throws`, exceções customizadas e manipulação de arquivos. Gabarito comentado ao final.

---

## Grupo A: Hierarquia e Conceitos (1 a 10)

### 1. Qual é a superclasse de todas as exceções e erros em Java?
*   a) `Exception`
*   b) `Throwable`
*   c) `Error`
*   d) `Object`

### 2. Qual a diferença entre `Error` e `Exception`?
*   a) Nenhuma, são sinônimos.
*   b) `Error` representa falhas graves da JVM que não devem ser tratadas; `Exception` representa problemas da aplicação que podem e devem ser tratados.
*   c) `Error` é checked e `Exception` é unchecked.
*   d) `Error` ocorre em compilação e `Exception` em execução.

### 3. Qual destas é uma exceção **checked**?
*   a) `NullPointerException`
*   b) `ArithmeticException`
*   c) `IOException`
*   d) `ArrayIndexOutOfBoundsException`

### 4. Qual destas é uma exceção **unchecked**?
*   a) `IOException`
*   b) `FileNotFoundException`
*   c) `SQLException`
*   d) `NumberFormatException`

### 5. O que define uma exceção como unchecked?
*   a) Ser lançada apenas em tempo de execução.
*   b) Herdar de `RuntimeException` (ou de `Error`), o que dispensa o compilador de exigir tratamento.
*   c) Não possuir mensagem.
*   d) Ser criada pelo programador.

### 6. O que o compilador exige ao chamar um método que declara `throws IOException`?
*   a) Nada, o tratamento é opcional.
*   b) Que o chamador trate com `try/catch` ou propague declarando `throws` também.
*   c) Que o método seja `static`.
*   d) Que a `IOException` seja convertida em `RuntimeException`.

### 7. `StackOverflowError` deve ser tratado com `try/catch`?
*   a) Sim, sempre.
*   b) Não. É um `Error` que sinaliza falha estrutural (normalmente recursão infinita); a correção é no código, não no `catch`.
*   c) Sim, mas apenas em métodos recursivos.
*   d) Sim, é uma exceção checked.

### 8. Qual exceção ocorre em `Integer.parseInt("abc")`?
*   a) `ArithmeticException`
*   b) `NumberFormatException`
*   c) `ClassCastException`
*   d) `IllegalStateException`

### 9. Qual exceção ocorre em `int x = 10 / 0;`?
*   a) `ArithmeticException`
*   b) `NumberFormatException`
*   c) Nenhuma, o resultado é `Infinity`.
*   d) `NullPointerException`

### 10. E qual o resultado de `double x = 10.0 / 0;`?
*   a) Lança `ArithmeticException`.
*   b) Não lança exceção: o resultado é `Infinity`.
*   c) O resultado é `0.0`.
*   d) Erro de compilação.

---

## Grupo B: try, catch e finally (11 a 24)

### 11. Qual será a saída?
```java
try {
    System.out.print("A");
    int x = 1 / 0;
    System.out.print("B");
} catch (ArithmeticException e) {
    System.out.print("C");
}
System.out.print("D");
```
*   a) `ABCD`
*   b) `ACD`
*   c) `ABD`
*   d) `AD`

### 12. Um bloco `try` pode existir sem `catch`?
*   a) Não, nunca.
*   b) Sim, desde que tenha `finally` ou seja um `try-with-resources`.
*   c) Sim, sempre.
*   d) Apenas dentro de métodos `static`.

### 13. Por que este código não compila?
```java
try { ... }
catch (Exception e) { ... }
catch (ArithmeticException e) { ... }
```
*   a) Falta o `finally`.
*   b) Porque `ArithmeticException` já foi capturada pelo `catch (Exception)` anterior, tornando o segundo bloco inalcançável.
*   c) Porque não se pode ter dois `catch`.
*   d) Porque `Exception` não pode ser capturada.

### 14. Quando o bloco `finally` **não** executa?
*   a) Quando ocorre exceção.
*   b) Quando há `return` dentro do `try`.
*   c) Praticamente nunca; apenas se a JVM for encerrada com `System.exit()` ou travar.
*   d) Quando o `catch` captura a exceção.

### 15. Qual a saída?
```java
public static int teste() {
    try {
        return 1;
    } finally {
        System.out.print("F");
    }
}
// no main: System.out.print(teste());
```
*   a) `1F`
*   b) `F1`
*   c) `1`
*   d) `F`

### 16. Qual a sintaxe correta do multi-catch?
*   a) `catch (ArithmeticException, NumberFormatException e)`
*   b) `catch (ArithmeticException || NumberFormatException e)`
*   c) `catch (ArithmeticException | NumberFormatException e)`
*   d) `catch (ArithmeticException e1, NumberFormatException e2)`

### 17. O que `e.getMessage()` retorna?
*   a) A pilha de chamadas completa.
*   b) A mensagem descritiva associada à exceção.
*   c) O nome da classe da exceção.
*   d) A linha onde o erro ocorreu.

### 18. Qual a diferença entre `getMessage()` e `printStackTrace()`?
*   a) São idênticos.
*   b) `getMessage()` devolve apenas a mensagem; `printStackTrace()` imprime toda a pilha de chamadas até a origem do erro.
*   c) `printStackTrace()` devolve uma String.
*   d) `getMessage()` encerra o programa.

### 19. Qual a principal vantagem do `try-with-resources`?
*   a) Deixa o código mais rápido em execução.
*   b) Fecha automaticamente os recursos declarados, com erro ou sem erro, dispensando o `finally`.
*   c) Captura todas as exceções automaticamente.
*   d) Permite abrir apenas um recurso por vez.

### 20. Que requisito uma classe precisa cumprir para ser usada no `try-with-resources`?
*   a) Ser `final`.
*   b) Implementar `AutoCloseable` (ou `Closeable`).
*   c) Ser `static`.
*   d) Estender `Exception`.

### 21. Qual a saída?
```java
try {
    throw new RuntimeException("erro");
} catch (RuntimeException e) {
    System.out.print("1");
} finally {
    System.out.print("2");
}
System.out.print("3");
```
*   a) `123`
*   b) `12`
*   c) `13`
*   d) `23`

### 22. O que ocorre quando uma exceção é lançada e **nenhum** `catch` a captura?
*   a) O programa continua normalmente.
*   b) Ela sobe pela pilha de chamadas e, se ninguém tratar, encerra a thread exibindo o stack trace.
*   c) O compilador impede a execução.
*   d) A exceção é ignorada silenciosamente.

### 23. Após `nextInt()` lançar `InputMismatchException`, o que é indispensável fazer?
*   a) Fechar o `Scanner`.
*   b) Limpar o buffer com `nextLine()`, pois o texto inválido permanece nele e causaria loop infinito.
*   c) Criar um novo objeto `Scanner`.
*   d) Nada, o `Scanner` se recupera sozinho.

### 24. Qual é o pior antipadrão de tratamento de exceções?
*   a) Usar `finally`.
*   b) Um `catch` vazio, que engole a exceção e transforma um erro visível num bug invisível.
*   c) Usar múltiplos `catch`.
*   d) Declarar `throws` na assinatura.

---

## Grupo C: throw, throws e Customizadas (25 a 36)

### 25. Qual a diferença entre `throw` e `throws`?
*   a) São sinônimos.
*   b) `throw` lança a exceção dentro do corpo do método; `throws` declara na assinatura que o método pode lançá-la.
*   c) `throw` fica na assinatura e `throws` no corpo.
*   d) `throws` só funciona com exceções unchecked.

### 26. Qual a sintaxe correta para lançar uma exceção?
*   a) `throw IllegalArgumentException("erro");`
*   b) `throw new IllegalArgumentException("erro");`
*   c) `throws new IllegalArgumentException("erro");`
*   d) `raise IllegalArgumentException("erro");`

### 27. Quantas exceções podem ser declaradas num `throws`?
*   a) Apenas uma.
*   b) Várias, separadas por vírgula.
*   c) Várias, separadas por `|`.
*   d) Nenhuma, `throws` não aceita parâmetros.

### 28. Para criar uma exceção **checked** customizada, de qual classe herdar?
*   a) `RuntimeException`
*   b) `Exception`
*   c) `Error`
*   d) `Throwable`

### 29. E para criar uma **unchecked** customizada?
*   a) `Exception`
*   b) `RuntimeException`
*   c) `IOException`
*   d) `Error`

### 30. O que `super(mensagem)` faz no construtor de uma exceção customizada?
*   a) Encerra o programa.
*   b) Repassa a mensagem à classe pai, tornando-a acessível via `getMessage()`.
*   c) Cria uma nova exceção.
*   d) É opcional e não tem efeito.

### 31. Uma exceção customizada pode ter atributos e métodos próprios?
*   a) Não, apenas a mensagem.
*   b) Sim. É uma classe comum, e carregar dados extras (como o saldo disponível) é uma prática recomendada.
*   c) Apenas se for checked.
*   d) Apenas atributos `static`.

### 32. Qual o critério prático para escolher entre checked e unchecked?
*   a) Checked para erros graves, unchecked para leves.
*   b) Checked quando quem chama pode reagir de forma útil à condição; unchecked para violações de regra que indicam uso incorreto da API.
*   c) Unchecked sempre, checked nunca.
*   d) Depende apenas do tamanho do projeto.

### 33. Ao relançar uma exceção, por que passar a original como causa (`new MinhaEx("msg", e)`)?
*   a) Para o programa rodar mais rápido.
*   b) Para preservar o rastro original no stack trace, que passa a mostrar "Caused by" apontando a origem real.
*   c) Porque o compilador exige.
*   d) Para converter checked em unchecked.

### 34. Ao capturar `ErroDeNegocioException`, exceções que herdam dela também são capturadas?
*   a) Não, é preciso um `catch` para cada filha.
*   b) Sim. Um `catch` captura o tipo declarado e todos os seus subtipos.
*   c) Apenas se forem checked.
*   d) Apenas com multi-catch.

### 35. Uma exceção lançada em C, com A chamando B que chama C, pode ser tratada em A?
*   a) Não, precisa ser tratada em B.
*   b) Sim. Ela sobe pela pilha até encontrar um `catch` compatível; B só precisa declarar `throws` se ela for checked.
*   c) Não, exceções não atravessam métodos.
*   d) Apenas se todos os métodos forem `static`.

### 36. Por que usar exceções como controle de fluxo normal é ruim?
*   a) Porque não compila.
*   b) Porque construir o stack trace tem custo real de desempenho e o código fica menos legível; para casos esperados, um `if` é mais claro e barato.
*   c) Porque exceções só funcionam uma vez.
*   d) Porque exige `finally`.

---

## Grupo D: Arquivos (37 a 50)

### 37. O que a classe `File` representa?
*   a) O conteúdo do arquivo carregado em memória.
*   b) Um caminho no sistema de arquivos, que pode ou não existir fisicamente.
*   c) Um fluxo de leitura aberto.
*   d) Apenas diretórios.

### 38. O que acontece em `new File("x.txt")` quando o arquivo não existe?
*   a) Lança `FileNotFoundException`.
*   b) O objeto é criado normalmente; `exists()` retorna `false` e nenhum arquivo é criado no disco.
*   c) O arquivo é criado automaticamente.
*   d) Retorna `null`.

### 39. Qual a diferença entre `mkdir()` e `mkdirs()`?
*   a) Nenhuma.
*   b) `mkdir()` cria apenas o último diretório; `mkdirs()` cria também todos os diretórios pais que faltarem.
*   c) `mkdir()` cria arquivos e `mkdirs()` cria pastas.
*   d) `mkdirs()` só funciona no Linux.

### 40. O que faz `new FileWriter("dados.txt")` sem o segundo parâmetro?
*   a) Acrescenta ao final do arquivo.
*   b) Sobrescreve o arquivo, apagando todo o conteúdo existente.
*   c) Lança exceção se o arquivo já existir.
*   d) Abre o arquivo apenas para leitura.

### 41. Como acrescentar conteúdo sem apagar o que já existe?
*   a) `new FileWriter("dados.txt", true)`
*   b) `new FileWriter("dados.txt", false)`
*   c) `new FileWriter("dados.txt", "append")`
*   d) Não é possível com `FileWriter`.

### 42. Por que envolver um `FileWriter` num `BufferedWriter`?
*   a) Para permitir leitura.
*   b) Para acumular dados em memória e gravar em blocos, reduzindo acessos ao disco, além de ganhar o método `newLine()`.
*   c) Porque `FileWriter` não compila sozinho.
*   d) Para tratar exceções automaticamente.

### 43. O que `BufferedReader.readLine()` retorna ao chegar ao fim do arquivo?
*   a) String vazia `""`
*   b) `null`
*   c) `-1`
*   d) Lança `EOFException`.

### 44. Qual o idioma padrão de leitura linha a linha em Java?
*   a) `while (leitor.readLine() != null) { ... }`
*   b) `while ((linha = leitor.readLine()) != null) { ... }`
*   c) `for (String linha : leitor) { ... }`
*   d) `do { ... } while (leitor.hasNext());`

### 45. Qual o risco de `Files.readAllLines()` em arquivos grandes?
*   a) Nenhum, é sempre a melhor opção.
*   b) Carrega o arquivo inteiro na memória, podendo causar `OutOfMemoryError`.
*   c) Corrompe o arquivo.
*   d) Só funciona com arquivos `.txt`.

### 46. Qual a forma correta de escrever um caminho do Windows em Java?
*   a) `"C:\dados\arquivo.txt"`
*   b) `"C:\\dados\\arquivo.txt"` ou `"C:/dados/arquivo.txt"`
*   c) `"C:/dados\arquivo.txt"`
*   d) `'C:\dados\arquivo.txt'`

### 47. `FileNotFoundException` é subclasse de qual exceção?
*   a) `RuntimeException`
*   b) `IOException`
*   c) `Error`
*   d) `SQLException`

### 48. Ao carregar objetos de um CSV, onde colocar o `try/catch` para que uma linha corrompida não aborte a leitura?
*   a) Envolvendo o laço inteiro.
*   b) Dentro do laço, ao redor da conversão de cada linha.
*   c) No método `main` apenas.
*   d) Não é possível tratar isso.

### 49. Ao ler um CSV, qual o risco de `linha.split(";")` sem validação prévia?
*   a) Nenhum.
*   b) Se a linha tiver menos campos que o esperado, acessar `partes[2]` lança `ArrayIndexOutOfBoundsException`.
*   c) O método `split` não existe em String.
*   d) O programa trava indefinidamente.

### 50. Num sistema que persiste dados em arquivo, qual arquitetura é mais adequada?
*   a) Tudo dentro do `main`, num único método.
*   b) Classes de domínio com as regras e exceções, uma classe separada de persistência (`salvar`/`carregar`), e o `main` cuidando apenas do menu e das mensagens ao usuário.
*   c) Uma classe por arquivo de texto.
*   d) Apenas métodos `static` sem classes.

---

# GABARITO COMENTADO

## Grupo A

**1. b)** `Throwable`. Dela descendem `Error` e `Exception`.

**2. b)** `Error` é falha estrutural da JVM (memória, pilha); `Exception` é problema da aplicação. Capturar `OutOfMemoryError` raramente resolve, porque não há memória nem para tratar o erro.

**3. c)** `IOException`. Regra prática: quase tudo de E/S e banco é checked.

**4. d)** `NumberFormatException`, subclasse de `IllegalArgumentException`, que é `RuntimeException`.

**5. b)** Herdar de `RuntimeException` ou `Error`. Não é sobre "quando ocorre": toda exceção ocorre em tempo de execução; a diferença é o compilador exigir ou não o tratamento.

**6. b)** Tratar ou propagar. É a definição operacional de checked.

**7. b)** Não trate. `StackOverflowError` quase sempre significa recursão sem caso base; o conserto é na lógica.

**8. b)** `NumberFormatException`, com a mensagem `For input string: "abc"`.

**9. a)** `ArithmeticException` com mensagem `/ by zero`. Vale apenas para divisão **inteira**.

**10. b)** `Infinity`. Ponto flutuante segue o padrão IEEE 754 e não lança exceção: `0.0/0.0` gera `NaN`. Por isso validar o divisor manualmente é obrigatório com `double`.

## Grupo B

**11. b)** `ACD`. O restante do `try` é abandonado imediatamente após a exceção.

**12. b)** Sim, com `finally` ou como `try-with-resources`. O que não existe é `try` sozinho.

**13. b)** Bloco inalcançável. O compilador acusa "exception has already been caught".

**14. c)** Praticamente nunca. As exceções são `System.exit()`, travamento da JVM ou desligamento da máquina.

**15. b)** `F1`. O `finally` executa e imprime `F` **antes** de o valor voltar para quem chamou; só depois o `main` imprime o `1`. Quem responde `1F` está imaginando que o `return` sai na hora, o que não acontece: o valor fica em espera enquanto o `finally` roda.

**16. c)** Barra vertical simples: `catch (A | B e)`. A variável é implicitamente `final`.

**17. b)** Apenas a mensagem descritiva.

**18. b)** `printStackTrace()` mostra a pilha completa. Útil para depurar, inadequado para o usuário final, que não deve ver detalhes internos.

**19. b)** Fechamento automático dos recursos. Com vários recursos declarados, ele os fecha na ordem inversa da abertura.

**20. b)** Implementar `AutoCloseable`. `Scanner`, `BufferedReader`, `BufferedWriter` e `Connection` já implementam.

**21. a)** `123`. A exceção foi capturada, então o fluxo segue normalmente após o `finally`.

**22. b)** Sobe pela pilha; sem tratamento, encerra a thread com stack trace. Se for a `main`, o programa termina.

**23. b)** Limpar o buffer. Sem isso, o texto inválido é relido indefinidamente.

**24. b)** O `catch` vazio. Um erro que ninguém vê é pior que o programa quebrando, porque some sem deixar rastro e reaparece como comportamento inexplicável depois.

## Grupo C

**25. b)** `throw` lança; `throws` declara. A letra "s" final é a única diferença visual e a fonte de metade dos erros de prova.

**26. b)** É preciso instanciar com `new`, porque se lança um **objeto**.

**27. b)** Vírgula: `throws IOException, SQLException`. A barra `|` é do multi-catch, não do `throws`.

**28. b)** `Exception`.

**29. b)** `RuntimeException`.

**30. b)** Repassa a mensagem ao pai, que a armazena e a devolve em `getMessage()`. Sem `super(mensagem)`, `getMessage()` retorna `null`.

**31. b)** Sim, e é recomendado. Carregar o saldo disponível junto do erro permite ao `catch` montar uma mensagem muito mais útil.

**32. b)** A capacidade de quem chama reagir de forma útil. A tendência moderna (Spring inclusive) pende para unchecked, evitando `throws` em cascata por todas as camadas.

**33. b)** Preservar a causa. Sem ela, o stack trace aponta apenas o ponto do relançamento, e a origem real do problema desaparece.

**34. b)** Sim, `catch` é polimórfico: captura o tipo e todos os subtipos. É por isso que `catch (Exception e)` captura quase tudo.

**35. b)** Sim. A exceção sobe pela pilha até encontrar um `catch` compatível.

**36. b)** Custo de desempenho na montagem do stack trace e perda de clareza. Exceção é para o excepcional; o esperado se resolve com `if`.

## Grupo D

**37. b)** Um caminho, não o conteúdo.

**38. b)** O objeto é criado e `exists()` devolve `false`. Para criar de fato no disco, use `createNewFile()`.

**39. b)** `mkdirs()` cria a cadeia inteira de diretórios. Use-o quando o caminho tiver subpastas que talvez não existam.

**40. b)** Sobrescreve, apagando tudo. Causa clássica de perda de dados em exercícios.

**41. a)** O segundo parâmetro `true` ativa o modo append.

**42. b)** Gravação em blocos e o método `newLine()`. A diferença de desempenho em volume é grande, porque cada acesso a disco é ordens de magnitude mais lento que a memória.

**43. b)** `null`. Daí o idioma `while ((linha = readLine()) != null)`.

**44. b)** A atribuição dentro da condição. A alternativa `a` descartaria a linha lida, e o laço nunca teria acesso ao conteúdo.

**45. b)** `OutOfMemoryError`. Para arquivos grandes, leia linha a linha.

**46. b)** Barra dupla ou barra normal. A barra invertida simples é caractere de escape em Java e nem compila.

**47. b)** `IOException`. Por isso um `catch (IOException e)` já a cobre.

**48. b)** Dentro do laço. Assim a linha ruim é ignorada e a leitura continua.

**49. b)** `ArrayIndexOutOfBoundsException`. Validar `partes.length` antes e lançar uma exceção própria com a linha na mensagem transforma um erro genérico em diagnóstico preciso.

**50. b)** Separação em camadas: domínio, persistência e interface. É exatamente essa divisão que o Módulo 6 formaliza como Model, Repository e Controller no Spring Boot. Quem já organizou assim em arquivo entende o Spring como continuação, não como mágica nova.
