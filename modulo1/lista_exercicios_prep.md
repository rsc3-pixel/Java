# Lista de Exercícios Prep: Módulo 1 - Controle de Fluxo e Métodos (50 Questões)

Este é um simulado de estilo preparatório com 50 questões focadas na sintaxe do Java para **Condições, Laços e Métodos**. As questões cobrem de pegadinhas clássicas de compilação a lógica de execução. O gabarito detalhado está disponível ao final do arquivo.

---

## Grupo A: Estruturas Condicionais (1 a 17)

### 1. O que acontece ao tentar compilar o código abaixo?
```java
int x = 5;
if (x) {
    System.out.println("Verdadeiro");
}
```
*   a) Imprime "Verdadeiro".
*   b) Erro de compilação: `int` não pode ser convertido para `boolean`.
*   c) Não compila porque falta o `else`.
*   d) Lança uma exceção em tempo de execução.

### 2. Considere as Strings `s1 = new String("Java");` e `s2 = new String("Java");`. Qual será o resultado das comparações `s1 == s2` e `s1.equals(s2)` respectivamente?
*   a) `true` e `true`
*   b) `false` e `false`
*   c) `true` e `false`
*   d) `false` e `true`

### 3. Qual o resultado da execução do seguinte trecho de código?
```java
String status = "ativo";
if (status.equalsIgnoreCase("ATIVO")) {
    System.out.print("Sim ");
} else {
    System.out.print("Não ");
}
```
*   a) Sim
*   b) Não
*   c) Erro de compilação pois Strings devem ser comparadas apenas com `.equals()`.
*   d) Sim Não

### 4. Qual tipo de dato NÃO é permitido na expressão de controle de um comando `switch` tradicional em Java?
*   a) `int`
*   b) `char`
*   c) `double`
*   d) `String`

### 5. O que será impresso pelo código abaixo?
```java
int opc = 2;
switch (opc) {
    case 1: System.out.print("1 ");
    case 2: System.out.print("2 ");
    case 3: System.out.print("3 ");
    default: System.out.print("D ");
}
```
*   a) 2 
*   b) 2 3 D
*   c) 2 3
*   d) Erro de compilação devido à falta de comandos `break`.

### 6. O que será exibido pelo código a seguir?
```java
boolean a = false;
if (a = true) {
    System.out.println("Entrou");
} else {
    System.out.println("Não entrou");
}
```
*   a) Entrou
*   b) Não entrou
*   c) Erro de compilação: a atribuição `=` não pode ser colocada dentro do `if`.
*   d) O programa trava em loop infinito.

### 7. Qual a diferença entre os operadores lógicos `&&` (E curto-circuito) e `&` (E lógico completo)?
*   a) Não há diferença; ambos funcionam de forma idêntica.
*   b) O `&&` avalia o segundo operando mesmo se o primeiro for falso.
*   c) O `&&` interrompe a avaliação se o primeiro operando for falso (curto-circuito), enquanto `&` avalia ambos sempre.
*   d) `&` é usado apenas para comparação de strings.

### 8. O que acontecerá ao compilar e rodar o trecho abaixo?
```java
int valor = 10;
String res = (valor > 10) ? "Maior" : (valor < 10) ? "Menor" : "Igual";
System.out.println(res);
```
*   a) Imprime "Maior"
*   b) Imprime "Menor"
*   c) Imprime "Igual"
*   d) Erro de compilação: o operador ternário não pode ser aninhado.

### 9. Qual o comportamento do código abaixo?
```java
int x = 3;
if (x > 2)
    System.out.print("A ");
    System.out.print("B ");
```
*   a) Imprime apenas "A "
*   b) Imprime apenas "B "
*   c) Imprime "A B "
*   d) Erro de compilação: falta de chaves `{}` no bloco `if`.

### 10. Se a variável `nota` vale `6.0`, o que será impresso?
```java
double nota = 6.0;
if (nota >= 7.0) {
    System.out.println("Aprovado");
} else if (nota >= 5.0) {
    System.out.println("Recuperação");
} else if (nota >= 3.0) {
    System.out.println("Exame especial");
} else {
    System.out.println("Reprovado");
}
```
*   a) Recuperação
*   b) Recuperação Exame especial
*   c) Aprovado
*   d) Reprovado

### 11. O que ocorre se colocarmos o bloco `default` no início do `switch`?
*   a) Erro de compilação; o `default` precisa estar obrigatoriamente no final.
*   b) O `default` é executado primeiro, ignorando todos os outros cases.
*   c) Funciona normalmente, mas a lógica de fluxo pode continuar para os cases abaixo se não houver um `break`.
*   d) O compilador ignora o `default`.

### 12. Qual das seguintes alternativas representa uma expressão booleana válida para um `if` em Java?
*   a) `if (status = 1)`
*   b) `if (nome.equals("Renato"))`
*   c) `if (x + y)`
*   d) `if (null)`

### 13. O que acontece se omitirmos o bloco `else` em um programa?
*   a) Erro de compilação.
*   b) O compilador assume um `else` vazio implicitamente, e o código funciona.
*   c) O programa fecha abruptamente se a condição do `if` for falsa.
*   d) O Java gera um aviso de advertência em tempo de execução.

### 14. Avalie o trecho a seguir e aponte a saída correta:
```java
int a = 10, b = 20;
if (a > 5 || b++ > 30) {
    System.out.println("Condição aceita. b = " + b);
}
```
*   a) Condição aceita. b = 20
*   b) Condição aceita. b = 21
*   c) Não imprime nada.
*   d) Erro de compilação.

### 15. O que acontecerá ao testar uma variável `String` contendo o valor `null` usando `.equals()`?
```java
String texto = null;
if (texto.equals("Java")) {
    System.out.println("Igual");
}
```
*   a) Imprime "Igual"
*   b) Erro de compilação.
*   c) Lança um erro de execução `NullPointerException`.
*   d) Não faz nada e passa para a próxima linha sem erros.

### 16. Como fazer uma comparação segura de igualdade quando uma das variáveis pode ser `null`?
*   a) `"Java".equals(texto)`
*   b) `texto.equals("Java")`
*   c) `texto == "Java"`
*   d) Não é possível fazer isso em Java.

### 17. O que será impresso?
```java
int idade = 20;
boolean amigoDoDono = true;
if (idade >= 18 && amigoDoDono == true) {
    System.out.println("Pode entrar");
}
```
*   a) Pode entrar
*   b) Não imprime nada
*   c) Erro de compilação por comparar booleanos com `==`
*   d) Erro de sintaxe nos conectivos lógicos

---

## Grupo B: Laços de Repetição (18 a 34)

### 18. Quantas vezes o loop abaixo será executado?
```java
int x = 10;
while (x > 0) {
    x--;
}
```
*   a) 9 vezes
*   b) 10 vezes
*   c) 11 vezes
*   d) Infinitas vezes

### 19. Qual a principal característica de um loop `do-while`?
*   a) Ele sempre executa pelo menos uma vez antes de avaliar a condição.
*   b) Ele é executado apenas se a condição for falsa inicialmente.
*   c) Ele nunca resulta em loop infinito.
*   d) Ele não aceita o comando `break`.

### 20. O que será impresso pelo código abaixo?
```java
for (int i = 0; i < 3; i++) {
    System.out.print(i + " ");
}
// System.out.print(i); // Linha Comentada
```
*   a) 0 1 2 3
*   b) 0 1 2
*   c) 1 2 3
*   d) Erro de compilação.

### 21. Descomentando a linha `System.out.print(i);` do exercício anterior (Questão 20), o que acontece?
*   a) Imprime 0 1 2 3
*   b) Imprime 0 1 2 2
*   c) Erro de compilação: a variável `i` não está no escopo fora do bloco `for`.
*   d) Lança uma exceção em tempo de execução.

### 22. O que faz o comando `continue` dentro de um laço de repetição?
*   a) Encerra o laço imediatamente.
*   b) Reinicia o programa desde a primeira linha da classe.
*   c) Pula o restante do bloco de código na iteração corrente e passa para a próxima verificação do laço.
*   d) Pausa a execução por 1 segundo.

### 23. Qual será a saída deste laço `for`?
```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        break;
    }
    System.out.print(i + " ");
}
```
*   a) 1 2 4 5
*   b) 1 2 3
*   c) 1 2
*   d) 1 2 3 4 5

### 24. Qual será a saída deste laço contendo `continue`?
```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue;
    }
    System.out.print(i + " ");
}
```
*   a) 1 2 4 5
*   b) 1 2 3
*   c) 1 2
*   d) 1 2 3 4 5

### 25. O que acontece ao tentar compilar e executar o seguinte loop?
```java
for (;;) {
    System.out.println("Olá");
    break;
}
```
*   a) Erro de compilação: falta as expressões de controle no `for`.
*   b) Imprime "Olá" uma vez e sai.
*   c) Imprime "Olá" infinitamente.
*   d) Lança um erro de estouro de pilha.

### 26. Qual é a sintaxe correta do laço `for-each` para percorrer o array `double[] notas = {7.5, 8.0, 9.5};`?
*   a) `for (double n : notas) { ... }`
*   b) `for (double n in notas) { ... }`
*   c) `for (notas : double n) { ... }`
*   d) `for (let n of notas) { ... }`

### 27. O que acontece com o código abaixo?
```java
int x = 5;
while (x == 5) {
    System.out.print("A ");
    x = 10;
}
```
*   a) Erro de compilação.
*   b) Imprime "A " uma vez.
*   c) Imprime "A " infinitamente.
*   d) Não imprime nada.

### 28. O que acontece se declararmos a mesma variável dentro e fora de um laço desta forma?
```java
int n = 10;
for (int i = 0; i < 2; i++) {
    int n = 20; // Linha X
    System.out.print(n + " ");
}
```
*   a) Compila e imprime: 20 20
*   b) Erro de compilação na Linha X: a variável `n` já está definida no método.
*   c) Compila e imprime: 10 10
*   d) Lança erro de tempo de execução.

### 29. Qual a saída deste código?
```java
int k = 0;
do {
    System.out.print(k + " ");
    k++;
} while (k < 0);
```
*   a) Não imprime nada.
*   b) 0
*   c) 0 1
*   d) Loop infinito.

### 30. Quantos elementos o loop a seguir imprimirá?
```java
String[] nomes = {"Ana", "Beto"};
for (String nome : nomes) {
    if (nome.equals("Ana")) {
        nome = "Carlos";
    }
}
System.out.println(nomes[0]);
```
*   a) Carlos
*   b) Ana
*   c) Beto
*   d) Erro de compilação: não podemos alterar variáveis locais do for-each.

### 31. O que acontece no código abaixo?
```java
int i = 0;
for (; i < 3; ) {
    System.out.print(i + " ");
    i++;
}
```
*   a) Erro de compilação.
*   b) Imprime 0 1 2.
*   c) Loop infinito imprimindo 0.
*   d) Imprime 1 2 3.

### 32. Como evitar um loop infinito em um laço `while`?
*   a) Usando obrigatoriamente um comando `if` no seu interior.
*   b) Garantindo que a expressão condicional eventualmente passe a ser avaliada como `false` por meio de modificações de variáveis dentro do loop.
*   c) Usando a palavra chave `void`.
*   d) O Java detecta e evita loops infinitos automaticamente na compilação.

### 33. O que será impresso?
```java
for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
        System.out.print(i + "" + j + " ");
    }
}
```
*   a) 00 01 10 11
*   b) 0 1 2 3
*   c) 00 11
*   d) Erro de compilação por laço aninhado.

### 34. Qual a saída do código a seguir?
```java
int valor = 5;
while (valor > 0) {
    valor--;
    if (valor == 3) continue;
    System.out.print(valor + " ");
}
```
*   a) 4 3 2 1 0
*   b) 4 2 1 0
*   c) 5 4 2 1 0
*   d) 4 2 1

---

## Grupo C: Métodos (Funções) (35 a 50)

### 35. Qual é a finalidade da palavra-chave `void` na assinatura de um método?
*   a) Indica que o método aceita qualquer tipo de parâmetro.
*   b) Indica que o método pertence a um pacote vazio.
*   c) Indica que o método não retorna nenhum valor.
*   d) Torna o método privado.

### 36. O que acontece se tentarmos usar a instrução `return 10;` dentro de um método declarado como `public static void teste()`?
*   a) O compilador faz o cast automático para `void`.
*   b) Erro de compilação: incompatibilidade de tipos de retorno.
*   c) Retorna o valor 10 normalmente para quem o chamou.
*   d) O método compila, mas lança erro em tempo de execução.

### 37. Para que serve a palavra-chave `static` na declaração de um método em uma classe?
*   a) Indica que o método nunca pode ser alterado ou redefinido.
*   b) Permite chamar o método diretamente a partir do nome da classe, sem a necessidade de criar um objeto daquela classe com o `new`.
*   c) Faz com que o método execute mais rápido.
*   d) Oculta o método de outras classes do mesmo projeto.

### 38. O que caracteriza a Sobrecarga de Métodos (Method Overloading) em Java?
*   a) Métodos em classes diferentes com assinaturas idênticas.
*   b) Métodos na mesma classe com o mesmo nome, porém com parâmetros diferentes em tipo, quantidade ou ordem.
*   c) Métodos que causam estouro de memória ao serem chamados.
*   d) Métodos com o mesmo nome que alteram o tipo de retorno de forma dinâmica.

### 39. O que acontece ao tentar compilar e executar a classe abaixo?
```java
public class TesteMetodo {
    public static void exibir(int x) {
        System.out.print("int ");
    }
    public static void exibir(double x) {
        System.out.print("double ");
    }
    public static void main(String[] args) {
        exibir(5);
        exibir(5.0);
    }
}
```
*   a) Imprime `int int `
*   b) Imprime `double double `
*   c) Imprime `int double `
*   d) Erro de compilação por ambiguidade de métodos.

### 40. O que será exibido pela execução do código abaixo?
```java
public class Escopo {
    public static void dobrar(int valor) {
        valor = valor * 2;
    }
    public static void main(String[] args) {
        int x = 10;
        dobrar(x);
        System.out.println(x);
    }
}
```
*   a) 10
*   b) 20
*   c) 0
*   d) Erro de compilação: a variável `x` não foi atualizada.

### 41. Qual a assinatura correta de um método que recebe uma String e retorna um boolean?
*   a) `public boolean verificar(String texto)`
*   b) `public String verificar(boolean status)`
*   c) `public void verificar(String texto, boolean status)`
*   d) `public static boolean String verificar()`

### 42. Um método com tipo de retorno `int` pode terminar sem uma instrução `return`?
*   a) Sim, o compilador retorna 0 por padrão.
*   b) Não, a menos que o método seja abstrato ou interface, métodos concretos com retorno tipado exigem o comando `return` retornando o tipo adequado.
*   c) Sim, desde que tenhamos um laço de repetição infinito no corpo do método.
*   d) Sim, em Java todas as saídas de métodos numéricos retornam null se omitido.

### 43. O que acontece no código abaixo?
```java
public class RetornoPrecoce {
    public static void testar(int x) {
        if (x < 0) {
            return;
        }
        System.out.print("Positivo ");
    }
    public static void main(String[] args) {
        testar(-5);
        testar(5);
    }
}
```
*   a) Imprime `Positivo Positivo `
*   b) Imprime apenas `Positivo `
*   c) Erro de compilação: métodos `void` não podem ter a palavra `return`.
*   d) Lança uma exceção de argumento ilegal.

### 44. Qual a melhor descrição para parâmetros de um método?
*   a) São constantes globais compartilhadas entre todos os arquivos do projeto.
*   b) São variáveis locais ao método que são inicializadas com os valores enviados pelo invocador na chamada do método.
*   c) São palavras reservadas do compilador Java.
*   d) São referências dinâmicas de banco de dados.

### 45. O que ocorre se tentarmos declarar dois métodos na mesma classe com as seguintes assinaturas?
```java
public static int somar(int a, int b) { return a + b; }
public static double somar(int a, int b) { return a + b; }
```
*   a) O compilador aceita porque os tipos de retorno são diferentes.
*   b) Erro de compilação: apenas o tipo de retorno diferente não é suficiente para diferenciar métodos sobrecarregados; a lista de parâmetros deve ser distinta.
*   c) O método que retorna double faz um cast implícito do int.
*   d) Ambos retornam 0.

### 46. O que acontece se chamarmos um método não estático dentro do método estático `main` sem instanciar a classe?
```java
public class MainMetodo {
    public void mensagem() {
        System.out.println("Olá");
    }
    public static void main(String[] args) {
        mensagem();
    }
}
```
*   a) Compila e imprime "Olá".
*   b) Erro de compilação: método não estático `mensagem()` não pode ser referenciado a partir de um contexto estático.
*   c) Roda normalmente mas exibe um warning no terminal.
*   d) Compila, mas gera erro em tempo de execução.

### 47. Qual alternativa descreve o fluxo correto da chamada de métodos recursivos?
*   a) Métodos recursivos são métodos que chamam a si mesmos e devem possuir uma condição de parada para evitar estouro de pilha.
*   b) São métodos que são executados em paralelo na nuvem.
*   c) São métodos proibidos na linguagem Java desde a versão 8.
*   d) Métodos recursivos só podem retornar `void`.

### 48. O que acontecerá ao compilar o código abaixo?
```java
public class MetodoInalcancavel {
    public static int obterNumero() {
        return 10;
        System.out.println("Fim do método"); // Linha X
    }
}
```
*   a) Compila com sucesso.
*   b) Erro de compilação na Linha X: comando inalcançável (unreachable statement).
*   c) Roda normalmente e imprime "Fim do método" após retornar 10.
*   d) Lança uma exceção em tempo de execução.

### 49. Qual o comportamento do método a seguir?
```java
public static boolean eMaior(int a, int b) {
    return a > b;
}
```
*   a) Retorna o maior número entre `a` e `b`.
*   b) Retorna `true` se `a` for maior que `b` e `false` caso contrário.
*   c) Não compila porque não usa o bloco `if`.
*   d) Sempre retorna `false`.

### 50. Como passar múltiplos parâmetros de tipos variados em Java?
*   a) Separando-os por vírgula e declarando o tipo de cada um individualmente.
*   b) Agrupando todos em colchetes como `[int a, String b]`.
*   c) Declarando apenas o tipo do primeiro parâmetro; os outros herdam o tipo dele.
*   d) Java permite apenas um parâmetro por método.

---

## Gabarito Comentado

### 1. Resposta: B
**Explicação:** Em Java, o bloco condicional `if` exige uma expressão estritamente booleana. Ao passar a variável `x` (que é do tipo `int`), o código não compila. Não há conversão implícita de números inteiros para booleanos (como `5` virar `true`).

### 2. Resposta: D
**Explicação:** O operador `==` compara as referências de memória. Como usamos `new String(...)`, dois objetos diferentes foram alocados na memória (endereços distintos), logo `s1 == s2` dá `false`. O método `.equals()` compara o conteúdo dos objetos, logo `s1.equals(s2)` dá `true` porque ambos guardam o texto "Java".

### 3. Resposta: A
**Explicação:** O método `.equalsIgnoreCase()` compara duas strings ignorando diferenças entre letras maiúsculas e minúsculas. Portanto, "ativo" é igual a "ATIVO", caindo no bloco `if` e imprimindo "Sim ".

### 4. Resposta: C
**Explicação:** O comando `switch` tradicional em Java não aceita valores do tipo ponto flutuante (`double` ou `float`) porque esses tipos possuem problemas de precisão na representação binária e não são exatos. Tipos aceitos incluem `byte`, `short`, `char`, `int`, `String` e enums.

### 5. Resposta: B
**Explicação:** Como a variável `opc` vale `2`, o código entra no `case 2:`. Como não há instruções `break` nos blocos seguintes, ocorre o comportamento de "fall-through" (queda livre), executando sucessivamente o `case 3` e o `default`, imprimindo "2 3 D ".

### 6. Resposta: A
**Explicação:** A expressão no `if` é `a = true` (um único sinal de igual representa atribuição). Isso atribui `true` à variável `a` e o resultado da expressão inteira passa a ser `true`. Assim, o bloco do `if` é executado, exibindo "Entrou". Dica: Sempre use `==` para comparações!

### 7. Resposta: C
**Explicação:** O operador `&&` faz avaliação de curto-circuito. Se o operando da esquerda for `false`, ele nem avalia o da direita (pois o resultado final já é obrigatoriamente `false`). O operador `&` avalia ambos os lados independente do resultado do primeiro.

### 8. Resposta: C
**Explicação:** O código usa operadores ternários aninhados. Como `valor` é igual a 10, a primeira expressão `(valor > 10)` dá `false`, avançando para o segundo ternário `(valor < 10) ? ... : ...`. Como 10 não é menor que 10, cai na última cláusula, resultando em "Igual".

### 9. Resposta: C
**Explicação:** Sem o uso de chaves `{}`, apenas a primeira instrução após a condição faz parte do bloco `if`. A linha `System.out.print("B ");` está fora da estrutura condicional e executará sempre. Como `x > 2` é `true`, imprime "A " e na sequência imprime "B ", resultando em "A B ".

### 10. Resposta: A
**Explicação:** O código usa uma estrutura `if / else if` encadeada. Como a primeira condição que se provou verdadeira foi `nota >= 5.0` (pois 6.0 >= 5.0), o bloco associado ("Recuperação") é executado e toda a estrutura condicional é encerrada, ignorando as condições seguintes.

### 11. Resposta: C
**Explicação:** O bloco `default` pode ser posicionado em qualquer lugar da estrutura `switch`. Se colocado no início, funciona normalmente. Contudo, se não houver um `break` nele, o fluxo continuará executando os cases de baixo caso o default seja acionado.

### 12. Resposta: B
**Explicação:** A alternativa B contém a chamada ao método `.equals()`, que retorna um booleano puro (`true` ou `false`). A alternativa A tenta fazer uma atribuição numérica (`= 1`), a C passa uma operação aritmética (`x + y`) e a D passa o valor `null`, o que não é permitido para loops/condições em Java.

### 13. Resposta: B
**Explicação:** O bloco `else` é inteiramente opcional. Se omitido e a condição do `if` for falsa, o programa simplesmente ignora o bloco `if` e segue a execução das linhas inferiores normalmente.

### 14. Resposta: A
**Explicação:** Graças à avaliação de curto-circuito do operador `||` (OU), como `a > 5` (10 > 5) é verdadeiro (`true`), a expressão inteira é considerada verdadeira de imediato. A segunda parte da comparação (`b++ > 30`) nunca é executada. Por isso, a variável `b` mantém seu valor original (20).

### 15. Resposta: C
**Explicação:** Tentar invocar qualquer método (incluindo `.equals()`) em uma variável que aponta para `null` resulta no erro clássico `java.lang.NullPointerException` em tempo de execução.

### 16. Resposta: A
**Explicação:** Colocar a string literal à esquerda ("Java") garante que nenhum método seja chamado sobre uma referência nula, pois a string literal nunca é `null`. Assim, se `texto` for `null`, o resultado será apenas `false`, sem estourar erros.

### 17. Resposta: A
**Explicação:** Ambas as condições são verdadeiras: 20 >= 18 (`true`) E `amigoDoDono` é `true`. O operador `&&` une ambas, resultando em `true`, executando o bloco e imprimindo "Pode entrar".

### 18. Resposta: B
**Explicação:** A variável de controle começa em 10. A cada iteração ela é decrementada (`x--`). O loop continuará rodando enquanto `x > 0`. Ele imprimirá e decrementará quando `x` for 10, 9, 8, 7, 6, 5, 4, 3, 2 e 1, totalizando 10 execuções.

### 19. Resposta: A
**Explicação:** A marca registrada do laço `do-while` é que o teste condicional ocorre na cauda (final) do laço, garantindo que o corpo do bloco de código execute ao menos uma vez, mesmo se a condição inicial for falsa.

### 20. Resposta: B
**Explicação:** O laço imprime os números `0`, `1` e `2` (enquanto `i < 3`). Ao chegar em 3, o laço termina. A linha que imprimiria `i` fora do laço está comentada, portanto não há erros.

### 21. Resposta: C
**Explicação:** A variável `i` foi declarada na inicialização do laço `for`. Em Java, o escopo de variáveis declaradas no `for` restringe-se exclusivamente ao bloco interno do laço. Fora dele, a variável não existe e o compilador acusa erro.

### 22. Resposta: C
**Explicação:** O comando `continue` força o laço a pular as linhas restantes da iteração atual e avançar imediatamente para a etapa de verificação da condição (no caso do for, ele executa o incremento e depois a verificação).

### 23. Resposta: C
**Explicação:** O laço imprime `1` e `2`. Quando `i` passa a valer `3`, o `break` é disparado, encerrando e saindo do laço imediatamente. Por isso, os números 3, 4 e 5 nunca chegam a ser exibidos.

### 24. Resposta: A
**Explicação:** O laço imprime `1` e `2`. Quando `i` passa a ser `3`, o `continue` é acionado. Isso pula a impressão do número 3 e segue para a próxima iteração. Assim, 4 e 5 são impressos na sequência, pulando apenas o 3.

### 25. Resposta: B
**Explicação:** Em Java, a sintaxe `for(;;)` é válida e define um loop infinito clássico. Contudo, como a primeira instrução dentro do laço é seguida por um comando `break`, ele executa o `System.out.println("Olá")` e é interrompido de imediato, rodando uma única vez.

### 26. Resposta: A
**Explicação:** A sintaxe do for-each em Java requer a declaração do tipo do elemento individual (neste caso `double`), um nome de variável temporária (`n`), o sinal de `:` e, por fim, o nome da coleção/array a ser varrido (`notas`).

### 27. Resposta: B
**Explicação:** O loop inicia porque `x == 5` é verdadeiro. Ele imprime "A " e atualiza `x` para 10. Na próxima verificação, `x == 5` torna-se falso, encerrando o laço. Logo, imprime apenas uma vez.

### 28. Resposta: B
**Explicação:** Java proíbe a redeclaração de variáveis locais com o mesmo identificador no mesmo escopo ou subescopo direto. Como a variável `n` já existe no método `main`, declará-la novamente dentro do `for` causa erro de compilação.

### 29. Resposta: B
**Explicação:** Por ser um laço `do-while`, o bloco é executado uma vez primeiro. Imprime `0` (valor inicial de `k`) e incrementa `k` para 1. Na sequência, avalia `k < 0` (1 < 0, que é falso), saindo do loop.

### 30. Resposta: B
**Explicação:** No laço for-each, a variável temporária (`nome`) recebe uma cópia do valor contido na posição do array. Alterar `nome = "Carlos"` muda apenas a variável temporária do laço, mantendo o array original `nomes` intocado. Logo, `nomes[0]` continua contendo "Ana".

### 31. Resposta: B
**Explicação:** A estrutura do `for` aceita ter partes vazias. A inicialização está fora e o incremento está no fim do bloco de código. O código roda validando `i < 3` e imprime `0 1 2 ` incrementando de forma correta.

### 32. Resposta: B
**Explicação:** Para evitar loops infinitos, deve-se alterar o valor de alguma variável de controle usada na condição lógica do `while` de modo que a condição torne-se `false` em algum momento de execução del código.

### 33. Resposta: A
**Explicação:** Loops aninhados rodam o bloco interno por completo para cada etapa do externo.
- Para `i = 0`: roda `j = 0` (imprime `00`) e `j = 1` (imprime `01`).
- Para `i = 1`: roda `j = 0` (imprime `10`) e `j = 1` (imprime `11`).

### 34. Resposta: B
**Explicação:** Rastreando a execução:
- `valor = 5`: entra, vira 4. 4 != 3, imprime "4 ".
- `valor = 4`: entra, vira 3. 3 == 3, aciona `continue` (pula o resto do loop).
- `valor = 3`: entra, vira 2. 2 != 3, imprime "2 ".
- `valor = 2`: entra, vira 1. 1 != 3, imprime "1 ".
- `valor = 1`: entra, vira 0. 0 != 3, imprime "0 ".
- `valor = 0`: não entra mais. Saída final: `4 2 1 0 `.

### 35. Resposta: C
**Explicação:** O tipo de retorno `void` informa ao compilador e à JVM que o método em questão executará seu fluxo de comandos normalmente, mas não devolverá nenhuma resposta/valor de retorno ao final.

### 36. Resposta: B
**Explicação:** Um método assinado com o retorno `void` não pode retornar valores por meio do comando `return`. Tentar fazer isso gera um erro de compilação incompatível.

### 37. Resposta: B
**Explicação:** Métodos estáticos pertencem à classe em si, não aos objetos construídos a partir dela. Dessa forma, você pode invocá-los usando a estrutura `NomeClasse.metodo()` diretamente.

### 38. Resposta: B
**Explicação:** A sobrecarga de métodos (overloading) permite reutilizar o nome do método em uma mesma classe desde que as listas de parâmetros sejam distintas de alguma forma (seja pela quantidade, pela ordem ou pelo tipo dos parâmetros).

### 39. Resposta: C
**Explicação:** Ao chamar `exibir(5)`, como `5` é um literal do tipo `int`, o compilador escolhe o método que recebe `int`. Ao chamar `exibir(5.0)`, que é do tipo `double`, ele seleciona o método que recebe `double`, imprimindo "int double ".

### 40. Resposta: A
**Explicação:** Em Java, a passagem de parâmetros para tipos primitivos (como `int`) é feita estritamente **por valor** (cópia). O método `dobrar` opera sobre uma cópia de `x`. A variável `x` original definida no método `main` permanece intacta (10).

### 41. Resposta: A
**Explicação:** A sintaxe adequada para a declaração de assinatura segue a ordem: modificador de acesso (`public`), seguido do tipo de retorno desejado (`boolean`), o identificador/nome do método (`verificar`), e na sequência os tipos e nomes dos parâmetros envolvidos entre parênteses (`String texto`).

### 42. Resposta: B
**Explicação:** Se o método foi configurado para retornar qualquer dado (que não seja `void`), o compilador exige que todas as saídas lógicas do código terminem com um comando `return` fornecendo um valor condizente.

### 43. Resposta: B
**Explicação:** Em métodos `void`, usar a instrução `return;` vazia serve como um comando de saída precoce. No caso de `testar(-5)`, ele cai na condição e executa o `return`, saindo imediatamente e sem imprimir nada. Em `testar(5)`, ele pula o `if` e exibe "Positivo ".

### 44. Resposta: B
**Explicação:** Parâmetros de métodos funcionam de forma semelhante a variáveis locais do método, sendo que os valores delas são alimentados a partir do código que invocou o método no momento de chamada.

### 45. Resposta: B
**Explicação:** Em Java, mudar apenas o tipo de retorno de um método não é suficiente para caracterizar uma sobrecarga. O compilador precisa distinguir qual método chamar baseado nos argumentos enviados, por isso a assinatura de parâmetros precisa obrigatoriamente mudar.

### 46. Resposta: B
**Explicação:** Métodos não estáticos dependem de um objeto ativo na memória (instanciado) para funcionarem, enquanto métodos estáticos (`static`) rodam a nível de classe. O compilador impede o acesso direto a componentes de instância a partir de contextos estáticos.

### 47. Resposta: A
**Explicação:** Recursividade ocorre quando uma função invoca a si mesma de forma repetitiva. Para evitar loops intermináveis de chamada de função e um erro de estouro de memória (`StackOverflowError`), é obrigatório o uso de uma condição de saída (caso base).

### 48. Resposta: B
**Explicação:** A linha `System.out.println` está posicionada imediatamente após uma instrução `return` incondicional. O compilador Java analisa o fluxo e detecta que essa linha nunca será alcançada, gerando o erro de código inalcançável (unreachable code).

### 49. Resposta: B
**Explicação:** A expressão `a > b` já retorna por si só um valor booleano (`true` ou `false`). Logo, o método simplesmente avalia essa condição e devolve seu resultado diretamente.

### 50. Resposta: A
**Explicação:** Para declarar métodos com vários parâmetros em Java, deve-se separá-los individualmente usando vírgulas e escrevendo o tipo correspondente de cada um antes do nome (ex: `void exibir(int idade, String nome, double altura)`).
