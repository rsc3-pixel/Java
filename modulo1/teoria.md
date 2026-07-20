# Módulo 1: Introdução ao Java e Sintaxe Básica

Bem-vindo ao seu ponto de partida em Java! Este módulo foi desenvolvido para introduzir os fundamentos da linguagem de forma direta e estruturada, cobrindo desde a configuração inicial da sintaxe até a criação de lógica de controle e métodos.

---

## 1. Como o Java Funciona? (Compilador vs Interpretador)

O Java utiliza um modelo híbrido de execução que envolve duas etapas principais:

1. **Compilação (`javac`):** O compilador traduz o código-fonte escrito nos arquivos `.java` para um formato intermediário e otimizado chamado **Bytecode** (gerando arquivos `.class`).
2. **Execução (`java`):** A Máquina Virtual Java (**JVM**) lê o Bytecode e o traduz para instruções nativas da máquina física em tempo de execução. Isso permite a portabilidade do Java: *"Write once, run anywhere"* (Escreva uma vez, rode em qualquer lugar), pois o mesmo Bytecode pode rodar em Windows, Linux ou macOS, desde que haja uma JVM compatível instalada.

---

## 2. A Estrutura de um Programa Java

Em **Java**, todo código deve obrigatoriamente residir dentro de uma **Classe**, e a execução do programa sempre se inicia em um ponto de entrada padrão: o método especial `main`.

Veja a estrutura fundamental:

```java
// O nome da classe deve ser exatamente igual ao nome do arquivo físico (ex: OlaMundo.java)
public class OlaMundo {

    // Ponto de entrada (entrypoint) que a JVM executa ao iniciar o programa
    public static void main(String[] args) {
        
        // Exibe o texto no console e pula para a próxima linha
        System.out.println("Olá, Mundo Java!");
        
    }
}
```

### Detalhes da Assinatura do Método `main`:
*   `public`: O método é acessível por qualquer outra classe (necessário para que a JVM possa invocá-lo).
*   `static`: O método pertence à classe OlaMundo em si, e não a uma instância/objeto criado a partir dela.
*   `void`: Indica que o método realiza sua tarefa mas não retorna nenhum valor.
*   `String[] args`: Um array de argumentos do tipo texto que podem ser fornecidos via linha de comando ao iniciar o programa.

---

## 3. Variáveis e Tipos de Dados

Java é uma linguagem de **tipagem estática e forte**. Isso significa que toda variável deve ter seu tipo explicitamente declarado no momento de sua criação, e esse tipo não pode ser alterado durante a execução do programa.

Os principais tipos primitivos e de referência para iniciar são:

| Tipo em Java | Descrição | Exemplo de Declaração |
| :--- | :--- | :--- |
| `int` | Número inteiro (sem casas decimais) | `int idade = 25;` |
| `double` | Número decimal de dupla precisão | `double preco = 99.90;` |
| `boolean`| Valor lógico: verdadeiro (`true`) ou falso (`false`) | `boolean ativo = true;` |
| `char` | Um único caractere (usa aspas simples `'`) | `char genero = 'M';` |
| `String` | Classe que representa textos completos (usa aspas duplas `"`) | `String nome = "Renato";` |

> [!WARNING]
> **Aspas Simples vs Aspas Duplas:**
> Em Java, caracteres individuais (`char`) devem ser delimitados por aspas simples (ex: `'A'`). Sequências de caracteres (`String`) devem ser delimitadas por aspas duplas (ex: `"Texto"`). Misturar essas regras gera erro de compilação.

---

## 4. Entrada e Saída de Dados (Console)

Para interagir com o usuário via terminal, o Java fornece classes e fluxos integrados:

*   **Saída (`System.out`):**
    *   `System.out.println(texto)`: Imprime o conteúdo e avança o cursor para a próxima linha.
    *   `System.out.print(texto)`: Imprime o conteúdo e mantém o cursor na mesma linha.
*   **Entrada (`Scanner`):** Classe utilitária do pacote `java.util` usada para ler dados digitados no teclado.

Exemplo de uso:

```java
import java.util.Scanner;

public class EntradaDados {
    public static void main(String[] args) {
        // Inicializa o Scanner associado à entrada padrão do sistema (teclado)
        Scanner leitor = new Scanner(System.in);
        
        System.out.print("Digite seu nome: ");
        String nome = leitor.nextLine(); // Lê uma linha inteira de texto
        
        System.out.print("Digite sua idade: ");
        int idade = leitor.nextInt(); // Lê o próximo valor inteiro
        
        System.out.println("Olá " + nome + ", você tem " + idade + " anos!");
        
        // Libera os recursos do Scanner
        leitor.close();
    }
}
```

---

## 5. Operadores

O Java fornece operadores nativos para manipulação de variáveis:

*   **Aritméticos:** `+` (soma), `-` (subtração), `*` (multiplicação), `/` (divisão), `%` (resto da divisão).
*   **Comparação:** `>` (maior), `<` (menor), `>=` (maior ou igual), `<=` (menor ou igual), `==` (igualdade de primitivos), `!=` (diferente).
*   **Lógicos:** `&&` (E lógico), `||` (OU lógico), `!` (NÃO lógico).

> [!NOTE]
> Como Java é fortemente tipado, o compilador impede comparações entre variáveis de tipos incompatíveis diretamente. Por exemplo, tentar comparar um número inteiro com uma String (`idade == "25"`) resultará em erro de compilação imediato.

---

## 6. Estruturas Condicionais (Tomada de Decisão)

As condicionais direcionam o fluxo do programa com base em testes lógicos.

### A. Condicional `if` / `else if` / `else`
Em Java, a expressão testada dentro do `if` deve obrigatoriamente resultar em um valor booleano (`true` ou `false`).

```java
int idade = 18;

if (idade >= 18) {
    System.out.println("Acesso permitido: Maior de idade.");
} else {
    System.out.println("Acesso negado: Menor de idade.");
}
```

> [!IMPORTANT]
> **Avaliação Estrita:**
> Diferente de linguagens dinâmicas, o Java não possui o conceito de valores implicitamente "verdadeiros" ou "falsos" (como converter `1` ou uma String populada para `true`). O compilador rejeita expressões que não sejam puramente booleanas no `if` (ex: `if (1)` ou `if (nome)` são inválidos).

### B. Comparação de Strings (`==` vs `.equals()`)
Esta é uma regra fundamental da linguagem Java:

*   O operador `==` compara a **referência de memória** de objetos.
*   Strings são objetos em Java. Portanto, usar `==` compara se dois objetos apontam para o mesmo local de memória, e não o conteúdo textual.
*   **Para comparar o conteúdo de duas Strings, use o método `.equals()`**.

```java
String loginDigitado = "admin";
String loginCorreto = "admin";

// ❌ INCORRETO (Pode falhar em tempo de execução dependendo de como as Strings foram alocadas)
if (loginDigitado == loginCorreto) { ... }

// ✔️ CORRETO (Compara o conteúdo de texto do objeto)
if (loginDigitado.equals(loginCorreto)) {
    System.out.println("Acesso liberado.");
}

// ✔️ CORRETO (Compara o conteúdo ignorando diferenças de caixa alta/baixa)
if (loginDigitado.equalsIgnoreCase("ADMIN")) {
    System.out.println("Acesso liberado (case-insensitive).");
}
```

### C. Estrutura `switch-case`
É usada para testar o valor de uma única variável contra múltiplos cenários de valores constantes.

```java
int opcao = 2;

switch (opcao) {
    case 1:
        System.out.println("Carregando Perfil...");
        break; // Impede que o código execute as instruções dos cases subsequentes
    case 2:
        System.out.println("Abrindo Configurações...");
        break;
    default:
        System.out.println("Opção inválida.");
}
```

---

## 7. Estruturas de Repetição (Laços / Loops)

Permitem a execução repetitiva de um bloco de instruções sob determinadas condições.

### A. Laço `while`
Avalia a condição antes de cada iteração. Se a condição for falsa logo no início, o bloco não roda nenhuma vez.

```java
int contador = 1;
while (contador <= 5) {
    System.out.println("Contagem: " + contador);
    contador++;
}
```

### B. Laço `do-while`
Executa o bloco de instruções primeiro e avalia a condição lógica depois. Garante que o bloco execute **pelo menos uma vez**.

```java
int numero;
Scanner leitor = new Scanner(System.in);
do {
    System.out.print("Digite um número maior que zero para sair: ");
    numero = leitor.nextInt();
} while (numero <= 0);
```

### C. Laço `for` (Tradicional)
Indicado quando o número exato de repetições é conhecido antes de iniciar a execução.

```java
// (Inicialização; Condição de parada; Incremento/Decremento)
for (int i = 0; i < 5; i++) {
    System.out.println("Índice: " + i);
}
```

### D. Laço `for-each` (Enhanced For)
Estrutura otimizada para percorrer arrays ou coleções do início ao fim de forma linear.

```java
String[] tecnologias = {"Java", "SQL", "Spring"};

// Para cada String 'tech' contida no array 'tecnologias'
for (String tech : tecnologias) {
    System.out.println("Tecnologia: " + tech);
}
```

### E. Comandos `break` e `continue`
*   `break`: Interrompe a execução do laço de repetição imediatamente, pulando para a linha após o laço.
*   `continue`: Aborta a iteração atual do laço e prossegue diretamente para o teste de condição da próxima iteração.

---

## 8. Funções em Java: Métodos

No Java, toda funcionalidade isolada (função) deve ser declarada no escopo de uma Classe e recebe o nome de **Método**.

### A. Anatomia de um Método

```
[Modificador de Acesso] [Modificador Especial] [Tipo de Retorno] [NomeDoMetodo]([Parâmetros]) {
    // Escopo do método
    return valor;
}
```

*   **Modificador de Acesso (`public`, `private`):** Regula a visibilidade do método fora da classe onde foi definido.
*   **Modificador Especial (ex: `static`):** Indica que o método pertence à classe (pode ser chamado sem precisar criar instâncias da classe com `new`).
*   **Tipo de Retorno:** O tipo de dados que o método devolve. Se nenhuma resposta for gerada, deve ser marcado como **`void`**.
*   **Parâmetros:** Dados de entrada opcionais que o método precisa para trabalhar, sempre acompanhados de seus tipos explícitos.

### Exemplo Prático:

```java
public class Calculadora {

    // Método estático que recebe dois inteiros e retorna a soma deles
    public static int somar(int a, int b) {
        return a + b; // O tipo do retorno deve ser int
    }

    // Método estático do tipo void (não retorna valor)
    public static void imprimirMensagem(String mensagem) {
        System.out.println("Mensagem: " + mensagem);
    }

    public static void main(String[] args) {
        imprimirMensagem("Iniciando cálculo...");
        
        int resultado = somar(15, 30);
        System.out.println("Soma: " + resultado);
    }
}
```

---

## 9. Sobrecarga de Métodos (Method Overloading)

Java suporta declarar múltiplos métodos com o mesmo nome na mesma classe, contanto que as assinaturas de seus parâmetros (tipos, quantidade ou ordem) sejam diferentes. O compilador decide qual método executar de acordo com os argumentos passados na chamada.

```java
public class Impressora {

    // Método para imprimir inteiros
    public static void exibir(int numero) {
        System.out.println("Valor inteiro: " + numero);
    }

    // Método para imprimir texto
    public static void exibir(String texto) {
        System.out.println("Valor texto: " + texto);
    }

    public static void main(String[] args) {
        exibir(50);         // Invoca o método com parâmetro int
        exibir("Sucesso");  // Invoca o método com parâmetro String
    }
}
```
