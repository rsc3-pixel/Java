# Módulo 2: Programação Orientada a Objetos (POO)

No Módulo 1 você escreveu tudo dentro do `main`, usando métodos `static` soltos. Isso funciona para exercícios pequenos, mas quebra quando o sistema cresce: variáveis espalhadas, nenhuma proteção sobre os dados e código impossível de reaproveitar.

A Orientação a Objetos resolve isso com uma ideia central: **juntar os dados e os comportamentos que agem sobre eles no mesmo lugar**.

---

## 1. Classe e Objeto: a planta e a casa

A distinção mais importante do módulo:

*   **Classe:** o molde, a planta arquitetônica. Descreve o que uma coisa **tem** (atributos) e o que ela **faz** (métodos). A classe não ocupa memória de dados, ela apenas descreve.
*   **Objeto:** a casa construída a partir da planta. É a instância concreta, que existe na memória e tem valores próprios.

Uma planta gera quantas casas você quiser, e cada casa pode ter a parede pintada de uma cor diferente. Mesma estrutura, estados independentes.

```java
// A CLASSE: o molde
public class Carro {
    // Atributos: o que todo carro TEM
    String modelo;
    String cor;
    int velocidadeAtual;

    // Método: o que todo carro FAZ
    void acelerar(int incremento) {
        velocidadeAtual = velocidadeAtual + incremento;
        System.out.println(modelo + " agora está a " + velocidadeAtual + " km/h");
    }
}
```

```java
// OS OBJETOS: instâncias concretas do molde
Carro carroDoRenato = new Carro();
carroDoRenato.modelo = "Civic";
carroDoRenato.cor = "Preto";

Carro carroDaVizinha = new Carro();
carroDaVizinha.modelo = "Onix";
carroDaVizinha.cor = "Branco";

carroDoRenato.acelerar(80);   // Civic agora está a 80 km/h
carroDaVizinha.acelerar(40);  // Onix agora está a 40 km/h
```

Repare: os dois objetos compartilham a mesma classe, mas `velocidadeAtual` de um não interfere no do outro. Cada `new` reserva um espaço novo de memória.

> [!NOTE]
> **O papel do `new`:** o operador `new` faz três coisas em sequência: aloca memória no *heap* para o objeto, executa o construtor e devolve a **referência** (o endereço) desse espaço. A variável `carroDoRenato` não guarda o carro, ela guarda o endereço de onde o carro está.

---

## 2. Referências: por que `==` mente em objetos

No Módulo 1 você aprendeu que Strings devem ser comparadas com `.equals()`. Agora dá para entender o motivo real, e ele vale para **todo objeto**, não só String.

Variáveis de tipos primitivos (`int`, `double`, `char`, `boolean`) guardam **o valor em si**. Variáveis de objeto guardam **o endereço** onde o objeto mora.

```java
int a = 10;
int b = 10;
System.out.println(a == b); // true — compara os valores 10 e 10

Carro c1 = new Carro();
Carro c2 = new Carro();
System.out.println(c1 == c2); // false — endereços diferentes, mesmo que o conteúdo seja idêntico

Carro c3 = c1;
System.out.println(c1 == c3); // true — c3 aponta para o MESMO objeto que c1
```

A consequência prática de `c3 = c1`: se você alterar `c3.cor`, a cor de `c1` muda junto. São dois controles remotos apontando para a mesma televisão.

---

## 3. Construtores: nascer já pronto

No exemplo do carro, o objeto nasceu vazio e você preencheu campo a campo. Isso é frágil: nada impede esquecer de definir o modelo e ficar com um carro sem nome.

O **construtor** é um bloco especial que roda automaticamente no `new` e garante que o objeto nasça em estado válido.

Regras do construtor:
*   Tem exatamente o **mesmo nome da classe**.
*   **Não declara tipo de retorno** (nem `void`).
*   Pode ser sobrecarregado, igual a métodos comuns.

```java
public class Carro {
    String modelo;
    String cor;
    int velocidadeAtual;

    // Construtor completo
    public Carro(String modelo, String cor) {
        this.modelo = modelo;
        this.cor = cor;
        this.velocidadeAtual = 0; // todo carro nasce parado
    }

    // Construtor sobrecarregado: cor padrão quando não informada
    public Carro(String modelo) {
        this(modelo, "Prata"); // chama o construtor de cima, evitando código duplicado
    }
}
```

Uso:

```java
Carro c1 = new Carro("Civic", "Preto");
Carro c2 = new Carro("Onix"); // vira prata automaticamente
```

### A palavra-chave `this`

`this` é a referência ao **objeto atual**, aquele em que o método está sendo executado agora.

No construtor acima, o parâmetro se chama `modelo` e o atributo também. Sem `this`, a linha `modelo = modelo` atribuiria o parâmetro a ele mesmo, e o atributo continuaria nulo. `this.modelo` diz explicitamente "o atributo deste objeto".

> [!IMPORTANT]
> **Construtor padrão invisível:** se você não escrever nenhum construtor, o Java cria um vazio automaticamente (`public Carro() {}`). Mas no instante em que você declara **qualquer** construtor, esse padrão desaparece. Se depois disso você tentar `new Carro()` sem argumentos, o código não compila. É uma das pegadinhas mais comuns em prova.

---

## 4. Encapsulamento: proteger os dados

Deixar atributos acessíveis de fora permite escrever isto:

```java
carroDoRenato.velocidadeAtual = -500; // um carro a menos 500 km/h?
```

O objeto não tem defesa contra estados absurdos. **Encapsulamento** é fechar o acesso direto e obrigar todo mundo a passar por métodos que validam.

### Modificadores de acesso

| Modificador | Quem enxerga |
| :--- | :--- |
| `public` | Qualquer classe, de qualquer pacote |
| `protected` | O próprio pacote e as classes filhas (herança) |
| *(nenhum)* — default/package | Apenas classes do mesmo pacote |
| `private` | Somente dentro da própria classe |

A regra prática do mercado: **atributos `private`, métodos de comportamento `public`**.

### Getters e Setters

```java
public class ContaBancaria {
    private String titular;
    private double saldo; // ninguém mexe direto

    public ContaBancaria(String titular) {
        this.titular = titular;
        this.saldo = 0.0;
    }

    // GETTER: apenas lê
    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    // SETTER com validação: aqui está o ganho real
    public void setTitular(String titular) {
        if (titular == null || titular.isBlank()) {
            System.out.println("Titular inválido. Alteração recusada.");
            return;
        }
        this.titular = titular;
    }

    // Comportamento de negócio: melhor que um setSaldo() cru
    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Depósito deve ser positivo.");
            return;
        }
        saldo += valor;
    }

    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            System.out.println("Saque recusado: valor inválido ou saldo insuficiente.");
            return false;
        }
        saldo -= valor;
        return true;
    }
}
```

Repare que **não existe** `setSaldo()`. O saldo só muda por depósito ou saque, que são as regras reais do negócio. Encapsulamento bem feito não é criar getter e setter para tudo mecanicamente, é decidir o que o mundo externo tem direito de fazer.

---

## 5. Membros `static`: pertencem à classe, não ao objeto

Um atributo normal existe uma vez **por objeto**. Um atributo `static` existe uma vez **por classe**, compartilhado por todas as instâncias.

```java
public class ContaBancaria {
    private static int totalDeContas = 0; // uma cópia só, para todas as contas
    private int numeroConta;

    public ContaBancaria(String titular) {
        totalDeContas++;              // incrementa o contador global
        this.numeroConta = totalDeContas; // cada conta guarda o seu número
    }

    public static int getTotalDeContas() {
        return totalDeContas; // método static: chamado sem precisar de objeto
    }
}
```

```java
new ContaBancaria("Renato");
new ContaBancaria("Ana");
System.out.println(ContaBancaria.getTotalDeContas()); // 2 — chamado NA CLASSE
```

> [!WARNING]
> **Um método `static` não enxerga atributos de instância.** Faz sentido: se o método pertence à classe e não a um objeto específico, de qual objeto ele leria o atributo? É exatamente por isso que, no Módulo 1, todo método chamado a partir do `main` precisava ser `static` também.

---

## 6. Herança: reaproveitar sem copiar

Imagine um sistema com `Funcionario`, `Gerente` e `Vendedor`. Os três têm nome, CPF e salário. Copiar esses atributos em três classes significa corrigir bugs em três lugares.

**Herança** cria uma relação "é um": um Gerente **é um** Funcionário, com algo a mais.

```java
public class Funcionario {
    protected String nome;   // protected: as filhas acessam, o mundo externo não
    protected double salarioBase;

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    public double calcularSalario() {
        return salarioBase;
    }

    public String getNome() {
        return nome;
    }
}
```

```java
public class Gerente extends Funcionario {
    private double bonus;

    public Gerente(String nome, double salarioBase, double bonus) {
        super(nome, salarioBase); // chama o construtor do pai PRIMEIRO
        this.bonus = bonus;
    }

    @Override
    public double calcularSalario() {
        return super.calcularSalario() + bonus; // reaproveita o cálculo do pai e soma
    }
}
```

### A palavra-chave `super`

*   `super(...)` como primeira linha do construtor: invoca o construtor da superclasse. É **obrigatório** quando o pai não tem construtor sem argumentos, e sempre precisa ser a primeira instrução.
*   `super.metodo()`: chama a versão do pai de um método que a filha sobrescreveu. Útil para estender o comportamento em vez de substituí-lo por completo.

> [!NOTE]
> **Java não tem herança múltipla de classes.** Uma classe só pode ter um `extends`. Isso evita o problema clássico do diamante (dois pais com o mesmo método, qual vale?). Quando você precisa de múltiplos contratos, usa interfaces, que veremos adiante.

---

## 7. Polimorfismo: mesma chamada, comportamentos diferentes

Polimorfismo significa "muitas formas". Na prática: uma variável do tipo do pai pode apontar para qualquer objeto filho, e o Java decide **em tempo de execução** qual versão do método rodar.

```java
Funcionario f1 = new Funcionario("Carlos", 3000);
Funcionario f2 = new Gerente("Renato", 5000, 2000); // referência do PAI, objeto FILHO

System.out.println(f1.calcularSalario()); // 3000.0
System.out.println(f2.calcularSalario()); // 7000.0 — roda a versão do Gerente
```

Isso é o que dá poder de verdade ao código:

```java
Funcionario[] equipe = {
    new Funcionario("Carlos", 3000),
    new Gerente("Renato", 5000, 2000),
    new Vendedor("Ana", 2000, 15000)
};

double folhaTotal = 0;
for (Funcionario f : equipe) {
    folhaTotal += f.calcularSalario(); // cada um calcula do seu jeito, sem if algum
}
```

Sem polimorfismo, esse laço viraria uma cadeia de `if (f instanceof Gerente) ... else if (f instanceof Vendedor) ...`, e cada novo cargo exigiria mexer nesse `if`. Com polimorfismo, criar um cargo novo não toca nesse código.

### Sobrescrita (`@Override`) vs Sobrecarga (Overload)

Confusão clássica em prova:

| | Sobrescrita (Override) | Sobrecarga (Overload) |
| :--- | :--- | :--- |
| Onde | Entre classe pai e filha | Na mesma classe |
| Assinatura | **Idêntica** ao método do pai | **Diferente** (tipos, quantidade ou ordem dos parâmetros) |
| Decisão | Em tempo de **execução** | Em tempo de **compilação** |
| Anotação | `@Override` | não se aplica |

A anotação `@Override` é opcional, mas use sempre: ela faz o compilador verificar se você realmente está sobrescrevendo algo. Se errar o nome do método ou os parâmetros, o erro aparece na compilação em vez de virar um bug silencioso.

---

## 8. Abstração: classes abstratas

Volte ao exemplo: faz sentido instanciar um `Funcionario` genérico? Talvez, mas imagine `FormaGeometrica`. Você não desenha uma "forma genérica", desenha um círculo ou um quadrado. A classe existe só para agrupar o que é comum.

Uma classe **`abstract`** não pode ser instanciada e pode declarar métodos sem corpo, que as filhas são obrigadas a implementar.

```java
public abstract class FormaGeometrica {
    protected String nome;

    public FormaGeometrica(String nome) {
        this.nome = nome;
    }

    // Método abstrato: só a assinatura, sem corpo. A filha É OBRIGADA a implementar.
    public abstract double calcularArea();

    // Método concreto: herdado normalmente por todas as filhas
    public void exibirDados() {
        System.out.printf("%s | Área: %.2f%n", nome, calcularArea());
    }
}
```

```java
public class Circulo extends FormaGeometrica {
    private double raio;

    public Circulo(double raio) {
        super("Círculo");
        this.raio = raio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }
}
```

```java
// FormaGeometrica f = new FormaGeometrica("qualquer"); // ERRO DE COMPILAÇÃO
FormaGeometrica f = new Circulo(5.0); // válido: referência abstrata, objeto concreto
f.exibirDados();
```

Note que `exibirDados()` chama `calcularArea()` sem saber qual implementação vai rodar. A classe pai define o **esqueleto** e delega o detalhe às filhas.

---

## 9. Abstração: interfaces

Uma **interface** é um contrato puro: lista o que uma classe precisa saber fazer, sem dizer como.

```java
public interface Autenticavel {
    boolean autenticar(String senha); // implicitamente public abstract
}

public interface Exportavel {
    String exportarParaCsv();
}
```

```java
// Uma classe pode implementar VÁRIAS interfaces, mas estender só uma classe
public class Gerente extends Funcionario implements Autenticavel, Exportavel {
    private String senha;

    // ... construtor omitido para brevidade ...

    @Override
    public boolean autenticar(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    @Override
    public String exportarParaCsv() {
        return nome + ";" + calcularSalario();
    }
}
```

### Classe abstrata ou interface?

| Critério | Classe abstrata | Interface |
| :--- | :--- | :--- |
| Relação | "**é um**" (Gerente é um Funcionário) | "**é capaz de**" (Gerente é capaz de autenticar) |
| Quantidade | só uma (`extends`) | várias (`implements`) |
| Atributos | atributos de instância normais | só constantes (`public static final`) |
| Uso típico | compartilhar código e estado entre parentes | definir capacidade entre classes sem parentesco |

Um `Gerente` e um `Roteador` não têm nada em comum na hierarquia, mas ambos podem ser `Autenticavel`. É isso que a interface resolve e a herança não.

> [!NOTE]
> Desde o Java 8 interfaces podem ter métodos `default` (com corpo) e `static`. Isso existe para permitir evoluir interfaces antigas sem quebrar quem já as implementava. Você vai reencontrar esse detalhe no Módulo 5, com as interfaces funcionais.

---

## 10. `toString()`: o método que todo objeto já tem

Toda classe em Java herda automaticamente de `Object`, que traz alguns métodos prontos. O mais usado é o `toString()`.

Sem sobrescrever, imprimir um objeto mostra algo inútil:

```java
System.out.println(carroDoRenato); // Carro@6d06d69c — nome da classe e hash de memória
```

Sobrescrevendo:

```java
@Override
public String toString() {
    return "Carro{modelo='" + modelo + "', cor='" + cor + "'}";
}
```

```java
System.out.println(carroDoRenato); // Carro{modelo='Civic', cor='Preto'}
```

O Java chama `toString()` sozinho sempre que um objeto aparece em concatenação de String ou dentro de um `println`. Sobrescrever é um hábito barato que economiza muito tempo de depuração.

---

## Resumo dos quatro pilares

| Pilar | Pergunta que ele responde | Ferramenta em Java |
| :--- | :--- | :--- |
| **Abstração** | O que importa modelar deste conceito? | `abstract`, `interface` |
| **Encapsulamento** | Quem tem direito de mexer nestes dados? | `private`, getters, setters |
| **Herança** | O que já existe e pode ser reaproveitado? | `extends`, `super` |
| **Polimorfismo** | Como tratar coisas diferentes de forma uniforme? | `@Override`, referência do pai |
