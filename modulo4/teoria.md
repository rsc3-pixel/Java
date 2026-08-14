# Módulo 4: Tratamento de Erros e Manipulação de Arquivos

Até aqui, quando algo dava errado o programa simplesmente morria. Um `Scanner` recebendo letra onde esperava número, uma divisão por zero, um índice fora do array: tela vermelha e fim.

Software de produção não pode fazer isso. Este módulo é sobre **antecipar a falha, tratá-la e manter o sistema de pé** e, na segunda metade, sobre fazer os dados sobreviverem ao fechamento do programa.

---

## 1. A árvore de exceções

Tudo que pode ser "lançado" em Java descende de `Throwable`:

```
                    Throwable
                   /         \
              Error           Exception
           (não trate)        /        \
                    RuntimeException   (demais Exceptions)
                    (unchecked)         (checked)
```

*   **`Error`:** falhas graves da JVM, como `OutOfMemoryError` e `StackOverflowError`. **Não se trata**: se a memória acabou, não há o que o seu `catch` possa fazer.
*   **`Exception`:** problemas da aplicação, que você pode e deve tratar. Divide-se em dois grupos.

### Checked vs Unchecked: a distinção central do módulo

| | **Unchecked** (`RuntimeException`) | **Checked** (demais `Exception`) |
| :--- | :--- | :--- |
| O compilador obriga a tratar? | Não | **Sim** |
| Origem típica | erro de lógica do programador | condição externa fora do seu controle |
| Exemplos | `NullPointerException`, `ArithmeticException`, `ArrayIndexOutOfBoundsException`, `NumberFormatException` | `IOException`, `FileNotFoundException`, `SQLException` |
| Como evitar | corrigindo o código | tratando com `try/catch` ou propagando com `throws` |

A lógica por trás dessa divisão: se o arquivo pode não existir (condição do mundo real, fora do seu alcance), o compilador exige que você diga o que fazer. Se você acessou um índice inválido, isso é bug seu, e nenhum `catch` conserta a causa.

> [!NOTE]
> Um `NullPointerException` não deve ser "tratado" com `try/catch` como regra. Ele é sintoma de código que não validou algo. A correção certa quase sempre é verificar o `null` antes, não capturar a exceção depois.

---

## 2. `try`, `catch` e `finally`

```java
try {
    // código que PODE falhar
    int resultado = 10 / divisor;
    System.out.println(resultado);

} catch (ArithmeticException e) {
    // executa APENAS se aquela exceção específica ocorrer
    System.out.println("Não é possível dividir por zero.");
    System.out.println("Detalhe técnico: " + e.getMessage());

} finally {
    // executa SEMPRE: com erro, sem erro, e até com return dentro do try
    System.out.println("Operação encerrada.");
}
```

### Como o fluxo se comporta

Quando uma exceção é lançada dentro do `try`, o restante do bloco **é abandonado imediatamente**. O Java procura um `catch` compatível, executa-o e segue depois do `finally`.

```java
try {
    System.out.println("A");
    int x = 10 / 0;        // explode aqui
    System.out.println("B"); // NUNCA executa
} catch (ArithmeticException e) {
    System.out.println("C");
}
System.out.println("D");

// Saída: A C D
```

### Múltiplos `catch`

```java
try {
    int[] v = new int[3];
    v[5] = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Erro de cálculo.");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Índice inválido.");
} catch (Exception e) {
    // Genérico: precisa vir POR ÚLTIMO
    System.out.println("Erro inesperado: " + e.getMessage());
}
```

> [!WARNING]
> **Ordem importa.** O `catch (Exception e)` captura tudo. Se ele vier primeiro, os `catch` seguintes viram código inalcançável e o programa **não compila**. Sempre do mais específico para o mais genérico.

### `multi-catch` (Java 7+)

Quando o tratamento é o mesmo, use `|`:

```java
try {
    processar();
} catch (ArithmeticException | NumberFormatException e) {
    System.out.println("Erro nos dados numéricos: " + e.getMessage());
}
```

### Os métodos úteis do objeto de exceção

```java
catch (Exception e) {
    e.getMessage();       // a mensagem descritiva
    e.printStackTrace();  // a pilha completa: útil para depurar, ruim para o usuário final
    e.getClass().getSimpleName(); // o nome do tipo, ex: "ArithmeticException"
}
```

---

## 3. `finally` e a ordem de execução

O `finally` roda mesmo quando há `return` dentro do `try`:

```java
public static int teste() {
    try {
        return 1;
    } finally {
        System.out.println("finally executou"); // roda ANTES do retorno acontecer
    }
}
```

A finalidade histórica do `finally` é **liberar recursos**: fechar arquivo, conexão de banco, `Scanner`. Se o `close()` ficasse só no `try`, uma exceção o puliria e o recurso vazaria.

### `try-with-resources` (Java 7+): a forma moderna

```java
// Forma antiga, verbosa e sujeita a erro
Scanner leitor = null;
try {
    leitor = new Scanner(new File("dados.txt"));
    // ...
} catch (FileNotFoundException e) {
    // ...
} finally {
    if (leitor != null) leitor.close(); // fácil de esquecer
}

// Forma moderna: o recurso é fechado automaticamente
try (Scanner leitor = new Scanner(new File("dados.txt"))) {
    while (leitor.hasNextLine()) {
        System.out.println(leitor.nextLine());
    }
} catch (FileNotFoundException e) {
    System.out.println("Arquivo não encontrado.");
}
// leitor.close() foi chamado sozinho, com erro ou sem erro
```

Qualquer classe que implemente `AutoCloseable` funciona nessa sintaxe. **Prefira sempre esta forma** ao trabalhar com arquivos e conexões.

---

## 4. `throw` e `throws`: dois nomes parecidos, papéis opostos

| | `throw` | `throws` |
| :--- | :--- | :--- |
| Onde aparece | dentro do corpo do método | na assinatura do método |
| O que faz | **lança** uma exceção agora | **declara** que o método pode lançar |
| Sintaxe | `throw new IllegalArgumentException("...");` | `public void ler() throws IOException {` |

```java
// 'throws' AVISA quem chama que este método pode falhar e não trata internamente
public static void validarIdade(int idade) throws IllegalArgumentException {
    if (idade < 0) {
        // 'throw' LANÇA a exceção de fato
        throw new IllegalArgumentException("Idade não pode ser negativa: " + idade);
    }
    System.out.println("Idade válida.");
}
```

Quando um método declara `throws` de uma exceção **checked**, quem o chama é obrigado a tratar ou propagar:

```java
public static void main(String[] args) {
    try {
        lerArquivo(); // obrigatório tratar, pois lerArquivo declara throws IOException
    } catch (IOException e) {
        System.out.println("Falha na leitura.");
    }
}

public static void lerArquivo() throws IOException {
    // não trata: empurra o problema para quem chamou
}
```

> [!IMPORTANT]
> **Quando propagar e quando tratar?** Trate onde você tem informação suficiente para **decidir algo útil**. Uma classe de acesso a dados geralmente não sabe se deve mostrar mensagem na tela ou tentar de novo; ela propaga. A camada que fala com o usuário sabe, e trata. Capturar cedo demais e engolir a exceção é o antipadrão mais comum do módulo.

---

## 5. Exceções customizadas

Criar sua própria exceção deixa o erro semântico e permite tratamento específico.

```java
// Unchecked: estende RuntimeException
public class SaldoInsuficienteException extends RuntimeException {
    private final double saldoDisponivel;

    public SaldoInsuficienteException(String mensagem, double saldoDisponivel) {
        super(mensagem); // repassa a mensagem para a classe pai
        this.saldoDisponivel = saldoDisponivel;
    }

    public double getSaldoDisponivel() {
        return saldoDisponivel;
    }
}
```

```java
// Checked: estende Exception, obrigando quem chama a tratar
public class ContaNaoEncontradaException extends Exception {
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
```

Uso:

```java
public void sacar(double valor) {
    if (valor > saldo) {
        throw new SaldoInsuficienteException(
            "Saque de R$ " + valor + " recusado.", saldo);
    }
    saldo -= valor;
}
```

```java
try {
    conta.sacar(5000);
} catch (SaldoInsuficienteException e) {
    System.out.println(e.getMessage());
    System.out.printf("Disponível: R$ %.2f%n", e.getSaldoDisponivel()); // dado extra
}
```

### Checked ou unchecked na sua exceção?

A regra prática do mercado: **se quem chamou pode fazer algo a respeito, use checked**. Se é violação de regra que indica uso incorreto da API, use unchecked. Na dúvida, a tendência moderna (e a do Spring) é preferir unchecked, para não poluir assinaturas com `throws` em cascata.

---

## 6. Manipulação de arquivos: a classe `File`

`File` representa um **caminho**, não o conteúdo. O objeto existe mesmo que o arquivo não exista no disco.

```java
import java.io.File;

File arquivo = new File("dados.txt");

System.out.println(arquivo.exists());        // o arquivo existe no disco?
System.out.println(arquivo.getName());       // dados.txt
System.out.println(arquivo.getAbsolutePath()); // caminho completo
System.out.println(arquivo.length());        // tamanho em bytes
System.out.println(arquivo.canRead());       // permissão de leitura

// Criar diretório
File pasta = new File("relatorios");
pasta.mkdir();   // cria uma pasta
// pasta.mkdirs(); // cria a pasta e todas as pastas pai que faltarem

// Listar conteúdo de um diretório
for (File f : pasta.listFiles()) {
    System.out.println(f.getName());
}
```

> [!TIP]
> **Caminhos no Windows:** a barra invertida é caractere de escape em Java, então `"C:\dados\arquivo.txt"` não compila. Use `"C:\\dados\\arquivo.txt"` ou, melhor, `"C:/dados/arquivo.txt"`, que o Java aceita e funciona em qualquer sistema operacional.

---

## 7. Escrevendo em arquivos

### `FileWriter` e `BufferedWriter`

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// O segundo parâmetro do FileWriter define o modo:
//   false (padrão) -> SOBRESCREVE o arquivo inteiro
//   true            -> ACRESCENTA ao final (append)
try (BufferedWriter escritor = new BufferedWriter(new FileWriter("log.txt", true))) {

    escritor.write("Primeira linha");
    escritor.newLine();               // quebra de linha do sistema operacional
    escritor.write("Segunda linha");
    escritor.newLine();

} catch (IOException e) {
    System.out.println("Erro ao escrever: " + e.getMessage());
}
```

Por que envolver o `FileWriter` num `BufferedWriter`? O `FileWriter` sozinho acessa o disco a cada chamada. O `BufferedWriter` acumula os dados em memória e grava em blocos, o que é significativamente mais rápido em volume. Além disso, ele oferece o `newLine()`.

> [!WARNING]
> **`FileWriter("arquivo.txt")` sem o `true` apaga todo o conteúdo existente.** Esquecer esse segundo parâmetro é a forma mais comum de destruir dados acidentalmente em exercícios de arquivo.

### `PrintWriter`: quando você quer `printf`

```java
try (PrintWriter pw = new PrintWriter(new FileWriter("relatorio.txt"))) {
    pw.println("=== RELATÓRIO ===");
    pw.printf("Total: R$ %.2f%n", 1234.5);
}
```

---

## 8. Lendo arquivos

### Com `BufferedReader` (leitura linha a linha)

```java
import java.io.BufferedReader;
import java.io.FileReader;

try (BufferedReader leitor = new BufferedReader(new FileReader("dados.txt"))) {

    String linha;
    // readLine() devolve null quando o arquivo termina
    while ((linha = leitor.readLine()) != null) {
        System.out.println(linha);
    }

} catch (IOException e) {
    System.out.println("Erro ao ler: " + e.getMessage());
}
```

A expressão `(linha = leitor.readLine()) != null` faz duas coisas de uma vez: atribui a próxima linha à variável e compara o resultado com `null`. É o idioma padrão de leitura em Java.

### Com `Scanner` (mesma API que você já usa no teclado)

```java
try (Scanner leitor = new Scanner(new File("dados.txt"))) {
    while (leitor.hasNextLine()) {
        System.out.println(leitor.nextLine());
    }
} catch (FileNotFoundException e) {
    System.out.println("Arquivo não encontrado.");
}
```

`Scanner` é mais conveniente e sabe converter tipos (`nextInt`, `nextDouble`); `BufferedReader` é mais rápido em arquivos grandes.

### Com `Files` (a forma moderna, Java 7+)

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

Path caminho = Paths.get("dados.txt");

// Lê o arquivo inteiro para uma lista de linhas
List<String> linhas = Files.readAllLines(caminho);
for (String l : linhas) {
    System.out.println(l);
}

// Escreve uma lista de linhas de uma vez
Files.write(caminho, linhas);

// Escreve texto direto
Files.writeString(caminho, "conteúdo completo"); // Java 11+
String conteudo = Files.readString(caminho);     // Java 11+
```

Cuidado com `readAllLines` e `readString`: eles carregam o arquivo **inteiro** na memória. Para arquivos grandes, prefira o `BufferedReader` linha a linha.

---

## 9. Persistência simples com CSV

O padrão mais comum em exercícios: salvar objetos como linhas de texto separadas por um delimitador.

```java
// Serializando: objeto -> linha de texto
public String paraCsv() {
    return codigo + ";" + nome + ";" + preco;
}

// Desserializando: linha de texto -> objeto
public static Produto deCsv(String linha) {
    String[] partes = linha.split(";");
    int codigo = Integer.parseInt(partes[0]);
    String nome = partes[1];
    double preco = Double.parseDouble(partes[2]);
    return new Produto(codigo, nome, preco);
}
```

```java
// Salvando a lista inteira
try (BufferedWriter w = new BufferedWriter(new FileWriter("produtos.csv"))) {
    for (Produto p : produtos) {
        w.write(p.paraCsv());
        w.newLine();
    }
} catch (IOException e) { /* ... */ }

// Carregando de volta
List<Produto> carregados = new ArrayList<>();
try (BufferedReader r = new BufferedReader(new FileReader("produtos.csv"))) {
    String linha;
    while ((linha = r.readLine()) != null) {
        if (linha.isBlank()) continue;       // pula linhas vazias
        try {
            carregados.add(Produto.deCsv(linha));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Linha corrompida, ignorando: " + linha);
        }
    }
} catch (IOException e) { /* ... */ }
```

Repare no `try/catch` **dentro** do laço: uma linha corrompida não deve impedir a leitura de todas as outras. Essa decisão de granularidade é o tipo de julgamento que separa código robusto de código frágil.

---

## 10. Boas práticas do módulo

**Faça:**
*   Trate a exceção mais específica que couber.
*   Use `try-with-resources` para tudo que precise ser fechado.
*   Crie exceções customizadas quando a regra de negócio for própria do domínio.
*   Registre a exceção com mensagem útil antes de propagar.

**Não faça:**

```java
// O antipadrão número 1: engolir a exceção
try {
    operacaoCritica();
} catch (Exception e) {
    // silêncio total: o erro sumiu e ninguém nunca vai saber
}
```

```java
// Antipadrão 2: usar exceção como controle de fluxo normal
try {
    return lista.get(indice);
} catch (IndexOutOfBoundsException e) {
    return null; // era mais barato e claro validar o índice com um if
}
```

Exceções são caras: montar o *stack trace* consome tempo. Elas existem para o **excepcional**, não para o esperado.
