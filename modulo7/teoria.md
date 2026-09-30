# Módulo 7: Revisão para a Prova de POO (CESAR School)

Este módulo não ensina matéria nova. Ele existe por um motivo só: a primeira prova de POO do prof. Maurício Braga. Os módulos 2, 3, 5 e 6 já cobrem quase todo o conteúdo dos slides, mas alguns pontos ficaram de fora ou apareceram de passagem. Eles estão aqui, com foco no jeito que o professor cobra.

> [!IMPORTANT]
> **Sobre a "prova" citada neste módulo:** é a prova **do período passado**, repassada por outros alunos. Ela serve para mostrar o **estilo** do professor (verdadeiro ou falso, "o que será impresso", "qual atribuição compila", uma classe discursiva), **não** o conteúdo da prova deste período. As questões novas vão ser outras, e temas que não apareceram nela (classes abstratas, interfaces, `final`, `equals`, Spring) podem cair agora. Por isso eles também estão aqui, nas seções 11 a 13.

> [!TIP]
> Ordem sugerida para a véspera: leia a seção 1 (o mapa), estude as seções 4, 5 e 6 (o raciocínio que o estilo do professor mais exige), depois as seções 11 a 13, rode o [Exemplo 1](../modulo7/Exemplo1.java) **tentando prever a saída antes**, e termine com o [Simulado](../modulo7/lista_exercicios_prep.md).

---

## 1. O mapa: conteúdo do professor → onde estudar no curso

| Slide / lista do professor | Onde está no curso | Prioridade |
| :--- | :--- | :--- |
| Classes e objetos, construtores, `this` | [Módulo 2](../modulo2/teoria.md), seções 1 a 3 | Alta |
| Abstração, encapsulamento, modificadores, get/set | [Módulo 2](../modulo2/teoria.md), seção 4 | Alta |
| `static` e `final` | [Módulo 2](../modulo2/teoria.md), seção 5 + seções 7 e 12 deste módulo | Média |
| Pacotes e a regra da URL | **Seção 2 deste módulo** | Alta (a discursiva do período passado cobrou) |
| Herança, `super`, construtores na herança | [Módulo 2](../modulo2/teoria.md), seção 6 + seção 3 deste módulo | Alta |
| Sobrecarga de métodos | [Módulo 1](../modulo1/teoria.md), seção 9 + seção 4 deste módulo | Alta |
| Sobrescrita e polimorfismo | [Módulo 2](../modulo2/teoria.md), seção 7 + **seções 5 e 6 deste módulo** | **Máxima** |
| Classes abstratas e interfaces | [Módulo 2](../modulo2/teoria.md), seções 8 e 9 + **seção 11 deste módulo** | Alta |
| `toString`, `equals`, `hashCode` | [Módulo 3](../modulo3/teoria.md), seção 7 + seções 8 e 12 deste módulo | Média |
| Anotações (`@Override`, `@Deprecated`, criar a sua) | **Seção 9 deste módulo** | Média |
| Generics (`Repositorio<T>`, `Caixa<T>`, `Par<K,V>`) | [Módulo 5](../modulo5/teoria.md), seção 1 + [Módulo 3](../modulo3/teoria.md), seção 3 | Média |
| Spring Boot (conversor, cadastro com JPA) | **Seção 13 deste módulo** + [Módulo 6](../modulo6/teoria.md), seções 5 a 9 e 11 | Média |

As prioridades acima vêm do peso de cada tema nos slides e nas listas, e não só do que apareceu na prova do período passado.

**O que você pode pular para esta prova:** Módulo 4 inteiro (exceções e arquivos), e no Módulo 5 tudo depois de generics (lambdas, streams, `Optional`, datas). Nada disso apareceu nos slides.

---

## 2. Pacotes e a regra da URL

Pacote é uma pasta com sobrenome. A regra do professor (slide 15 de Abstração):

*   Tudo em **minúsculas**, sem espaço, hífen ou underscore.
*   É o domínio **ao contrário**, seguido do projeto: `país.tipo.organização.departamento.sistema`.

| Enunciado | Pacote |
| :--- | :--- |
| Sistema MusicBox, site `www.soundwave.com` | `com.soundwave.musicbox` |
| Cadastro da SEFAZ-PE | `br.gov.sefazpe.dti.spo.cadastro` |
| Exercício da CESAR School | `br.cesarschool.poo.exercicio1` |

O `www` some, porque é o nome do servidor e não da organização. A declaração `package` é a **primeira linha** do arquivo, antes dos `import`.

> [!NOTE]
> Os arquivos deste curso usam `package modulo7;` porque o pacote precisa bater com a pasta onde o arquivo está. Na prova, escreva o pacote que o enunciado pede.

---

## 3. Construtores na herança: o `super()` invisível

A regra que mais gera erro de compilação em prova:

1.  Construtor **não é herdado**. Cada classe tem os seus.
2.  Todo construtor chama um construtor da mãe **na primeira linha**. Se você não escrever, o compilador insere `super();` sozinho.
3.  Se a mãe **não tem** construtor vazio, esse `super()` invisível não encontra ninguém e o código **não compila**.

```java
class Pai {
    Pai(int x) { }          // declarou um construtor: o vazio automático deixa de existir
}
class Filho extends Pai {
    Filho() { }             // ERRO: o compilador tenta super(), que não existe
}
class FilhoCerto extends Pai {
    FilhoCerto() { super(10); }   // OK: chamou explicitamente o que existe
}
```

`this(...)` e `super(...)` disputam a mesma primeira linha, por isso **nunca aparecem juntos** no mesmo construtor. Quando você usa `this(...)`, quem chama o `super` é o construtor para onde você delegou.

---

## 4. Sobrecarga × sobrescrita (a tabela que resolve a questão de V ou F)

| | Sobrecarga (overload) | Sobrescrita (override) |
| :--- | :--- | :--- |
| Onde | Na **mesma** classe (ou herdada) | Na **filha**, redefinindo o da mãe |
| Nome | Igual | Igual |
| Parâmetros | **Obrigatoriamente diferentes** | **Idênticos** |
| Retorno | **Pode** ser diferente (não é obrigatório) | Igual (ou um subtipo) |
| Visibilidade | Livre | **Não pode diminuir** (`public` na mãe exige `public` na filha) |
| Quem decide qual roda | O **compilador**, pelos tipos dos argumentos | A **JVM na execução**, pelo tipo real do objeto |
| Anotação | Nenhuma | `@Override` (recomendada; o professor exige) |

Dois erros clássicos:

*   Mudar **só o retorno** não é sobrecarga, é erro de compilação: `int calcular()` e `double calcular()` na mesma classe não compilam.
*   Mudar o tipo de um parâmetro achando que sobrescreveu (slide 25: `getSalario(float)` contra `getSalario(double)`). Sem `@Override`, isso vira uma **sobrecarga silenciosa** e o método da mãe continua rodando. Com `@Override`, o compilador acusa o erro. É exatamente para isso que a anotação existe.

---

## 5. Quem NÃO participa do polimorfismo: `private`, `static` e `final`

Esta é a seção da questão 04 da prova do período passado. Leia devagar: mesmo que a questão deste período seja outra, o raciocínio de "prever a saída" é o que o professor gosta de cobrar.

O polimorfismo funciona assim: quando você chama um método sobrescrevível, a JVM olha o **objeto real** na hora de executar e roda a versão dele. Isso se chama **ligação dinâmica** (*dynamic binding*).

Mas três tipos de método ficam fora desse mecanismo, e o compilador já amarra a chamada **em tempo de compilação** (*ligação estática*):

| Método | Por que não é sobrescrito |
| :--- | :--- |
| `private` | A filha nem enxerga. Se ela declarar um com o mesmo nome, é um **método novo**, sem relação nenhuma com o da mãe. |
| `static` | Pertence à classe, não ao objeto. A filha pode "esconder" (*hiding*), mas não sobrescrever. |
| `final` | Proibido sobrescrever. Tentar é erro de compilação. |

**A questão do período passado, passo a passo:**

```java
class Alfa {
    private void showA()   { System.out.print("Classe Alfa -> showA "); }
    protected void showB() { System.out.print("Classe Alfa -> showB "); }
    protected void execute() { showA(); showB(); }
}
public class Beta extends Alfa {
    private void showA()   { System.out.print("Classe Beta -> showA "); }
    protected void showB() { System.out.print("Classe Beta -> showB "); }
    public static void main(String[] args) {
        Beta obj = new Beta();
        obj.execute();
    }
}
```

1.  `obj.execute()`: Beta não tem `execute`, então roda o **herdado de Alfa**.
2.  Dentro do `execute` de Alfa, `showA()` é **private**. O compilador, ao compilar a classe Alfa, amarrou essa chamada ao `showA` **de Alfa**. O `showA` de Beta é outro método com o mesmo nome. Imprime `Classe Alfa -> showA`.
3.  `showB()` é **protected**: Beta sobrescreveu de verdade. Na execução, a JVM vê que o objeto é um Beta e roda `Classe Beta -> showB`.

**Saída:** `Classe Alfa -> showA Classe Beta -> showB` (alternativa C).

A analogia: o método `private` é uma anotação na gaveta trancada da mãe. A filha pode ter uma gaveta com a mesma etiqueta, mas quando a mãe abre a gaveta, abre **a dela**. Já o `protected` é um interruptor na parede da casa: quem mora lá (o objeto real) decide o que ele acende.

> [!IMPORTANT]
> Se `showA` fosse `protected` em Alfa **e** em Beta, a saída mudaria para `Classe Beta -> showA Classe Beta -> showB`. O [Exemplo 1](../modulo7/Exemplo1.java) roda as duas versões lado a lado.

---

## 6. Conversões: o que compila, o que não compila e o que explode

A regra de ouro, cobrada na prova do período passado como "qual atribuição é válida":

> **A variável de tipo mais genérico aceita um objeto de tipo mais específico. O contrário não.**

Pense em caixas com etiqueta: a caixa "Dispositivo" aceita qualquer smartphone, porque todo smartphone **é um** dispositivo. A caixa "Smartphone" não aceita um dispositivo qualquer, porque nem todo dispositivo é smartphone.

Com a hierarquia `Object ← Dispositivo ← Smartphone ← SmartphoneAndroid` e `Dispositivo ← Computador ← Notebook`:

| Linha | Resultado | Por quê |
| :--- | :--- | :--- |
| `Object o1 = new Computador();` | Compila | Tudo é um `Object` |
| `Dispositivo d3 = new SmartphoneAndroid();` | Compila | Neta cabe na caixa da avó |
| `Smartphone s2 = new SmartphoneAndroid();` | Compila | Filha cabe na caixa da mãe |
| `Computador c1 = new Notebook();` | Compila | Idem |
| `Smartphone s1 = new Object();` | **Não compila** | `Object` não é um `Smartphone` |
| `Computador c2 = new Smartphone();` | **Não compila** | São "primos": nenhum herda do outro |
| `Smartphone s3 = (Smartphone) new Dispositivo();` | Compila, mas **`ClassCastException`** na execução | O cast é uma promessa; o objeto real não a cumpre |

Duas consequências que o professor cobra:

*   **O tipo da variável decide o que você PODE chamar.** `Animal a = new Cachorro(); a.cocar();` não compila se `cocar()` só existe em Cachorro, mesmo o objeto sendo um cachorro.
*   **O tipo do objeto decide QUAL versão roda.** `a.interagir()` roda a versão de Cachorro.

---

## 7. `static`: uma cópia só (a pegadinha do slide 35)

```java
ex1.setRotulo("Abacate");
ex2.setRotulo("Laranja");
System.out.println(ex1.getRotulo());  // Laranja
System.out.println(ex2.getRotulo());  // Laranja
```

`rotulo` é `static`, então existe **uma** variável para a classe inteira. A segunda chamada sobrescreveu a primeira. É como o quadro de avisos da sala: qualquer aluno que escreve nele muda o que todos leem. Por isso o professor recomenda chamar pelo nome da classe (`ExemploStatic.setRotulo(...)`), que deixa claro que não é de um objeto específico.

Método `static` não acessa atributo de instância (não existe `this` dentro dele). É por isso que o `main` não enxerga os atributos da própria classe sem criar um objeto.

---

## 8. Os três métodos de `Object`

Toda classe herda de `Object`, direta ou indiretamente, e com isso ganha:

| Método | Comportamento padrão | Quando sobrescrever |
| :--- | :--- | :--- |
| `toString()` | `NomeDaClasse@1b6d3586` (classe + hash) | Sempre que for imprimir o objeto |
| `equals(Object o)` | Compara **endereço de memória** (igual a `==`) | Quando dois objetos com os mesmos dados devem ser "iguais" |
| `hashCode()` | Número derivado do endereço | **Sempre junto com `equals`**: objetos iguais precisam ter o mesmo hash, senão o `HashSet`/`HashMap` se perde |

Todos são sobrescritas, então levam `@Override`.

---

## 9. Anotações

Anotação é uma etiqueta colada no código, que alguém lê depois: o compilador, uma ferramenta ou o próprio programa em execução.

**As três categorias dos slides:**

| Categoria | Formato | Exemplo |
| :--- | :--- | :--- |
| Marcadora | Só o nome | `@Override`, `@Deprecated`, `@Entity` |
| Valor único | Um valor entre parênteses | `@SuppressWarnings("unchecked")` |
| Completa | Vários pares `nome = valor` | `@Size(min = 3, max = 50, message = "...")` |

**Anotações "só documentam"? Não.** Essa foi a alternativa G da prova do período passado, e é **falsa**:

*   `@Override` faz o compilador **recusar** o código se a sobrescrita estiver errada.
*   No Spring, `@Entity` faz o Hibernate **criar uma tabela**, `@GetMapping("/")` faz uma URL **responder**, `@NotBlank` faz uma requisição **ser rejeitada**. Tire a anotação e o programa se comporta diferente.

**Criando a sua (lista 3, exercício 3):**

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)   // QUANDO ela existe: até a execução
@Target(ElementType.TYPE)             // ONDE pode ser usada: só em classes (e interfaces)
@interface EntidadePersistente { }    // repare: @interface, não interface

@EntidadePersistente
class Cliente { private int id; private String nome; }
```

*   `@Retention` responde "até quando a etiqueta dura": `SOURCE` (some na compilação), `CLASS` (vai para o `.class` mas a JVM ignora, é o padrão) ou `RUNTIME` (o programa consegue ler rodando).
*   `@Target` responde "onde posso colar": `TYPE` (classe), `METHOD`, `FIELD` (atributo) etc. Colar em lugar errado é erro de compilação.
*   Com `RUNTIME`, o programa lê a etiqueta assim: `Cliente.class.isAnnotationPresent(EntidadePersistente.class)`. É exatamente o que o Spring faz com `@Entity`. O [Exemplo 2](../modulo7/Exemplo2.java) demonstra isso.

---

## 10. Interfaces: as regras de "múltiplo"

| Afirmação | Verdadeira? |
| :--- | :--- |
| Uma classe pode herdar (`extends`) de várias classes | **Não** |
| Uma classe pode implementar (`implements`) várias interfaces | Sim |
| Uma classe pode herdar de uma e implementar várias ao mesmo tempo | Sim: `class A extends B implements C, D` |
| Uma interface pode herdar de outra interface | **Sim**, com `extends` |
| Uma interface pode herdar de **várias** interfaces | Sim: `interface X extends Y, Z` |
| Interface pode ser instanciada com `new` | Não |
| Atributo em interface | Só constante: é `public static final` mesmo sem escrever |

Classe que implementa a interface e não implementa todos os métodos precisa ser declarada `abstract`.

---

## 11. Classes abstratas × interfaces

Não apareceram na prova do período passado, mas ocupam 12 slides de Polimorfismo. Boa candidata a cair agora.

| | Classe abstrata | Interface |
| :--- | :--- | :--- |
| Palavra-chave | `abstract class` | `interface` |
| Pode dar `new`? | Não | Não |
| Métodos | Abstratos **e** concretos | Só abstratos (é o que os slides ensinam) |
| Atributos | Quaisquer | Só constantes: `public static final` implícito |
| Construtor | **Tem** (roda via `super()` das filhas) | Não tem |
| Como a filha usa | `extends` (uma só) | `implements` (quantas quiser) |
| Quando usar | Parentes que **compartilham código** | Classes diferentes que **cumprem o mesmo contrato** |

```java
abstract class Figura {
    private String cor;                          // abstrata pode ter atributo
    public Figura(String cor) { this.cor = cor; } // e construtor
    public String getCor() { return cor; }        // e método concreto
    public abstract double area();                // sem corpo: termina em ;
}

class Circulo extends Figura {
    private double raio;
    public Circulo(String cor, double raio) { super(cor); this.raio = raio; }

    @Override
    public double area() { return Math.PI * raio * raio; }  // OBRIGATÓRIO implementar
}

Figura f = new Circulo("azul", 2);   // compila: variável abstrata, objeto concreto
Figura g = new Figura("azul");       // NÃO compila: classe abstrata
```

As regras que viram pegadinha:

*   Filha concreta que **não** implementa todos os métodos abstratos **não compila**. A saída é implementar ou declarar a filha também `abstract`.
*   Método abstrato não tem corpo, nem `{ }` vazio: termina em `;`.
*   `abstract` e `final` juntos não compilam: um exige ser sobrescrito, o outro proíbe.
*   `abstract` e `private` juntos também não: a filha não enxergaria o método para implementar.
*   Um único método `abstract` já torna a classe obrigatoriamente abstrata.

---

## 12. `final` e `equals` na prática

**`final` em atributo** precisa receber valor **uma única vez**: na declaração ou no construtor. Qualquer atribuição depois disso não compila.

```java
class Conta {
    private final int numero;                          // sem valor aqui...
    public Conta(int numero) { this.numero = numero; } // ...então o construtor é obrigado a dar
    public void mudar() { numero = 5; }                // NÃO compila: cannot assign a value to final variable
}
```

Constante de verdade é `static final`, com nome em maiúsculas: `public static final double TAXA = 0.05;`.

**Sobrescrevendo `equals`** (o esqueleto que costuma ser pedido):

```java
@Override
public boolean equals(Object o) {              // o parâmetro TEM que ser Object
    if (this == o) return true;                // mesmo objeto: igual, sem mais perguntas
    if (!(o instanceof Pedido)) return false;  // outro tipo (ou null): diferente
    Pedido outro = (Pedido) o;                 // cast seguro, o instanceof garantiu
    return this.codigo == outro.codigo;        // o critério de igualdade é seu
}

@Override
public int hashCode() {
    return Integer.hashCode(codigo);           // mesmo critério do equals
}
```

A pegadinha: escrever `equals(Pedido o)` em vez de `equals(Object o)` cria uma **sobrecarga**, não uma sobrescrita. Com `@Override` o compilador denuncia; sem ele, o `HashSet` continua usando o `equals` de `Object` e o bug fica escondido. É o mesmo mecanismo do `float` contra `double` da seção 4.

---

## 13. Spring Boot nos exemplos do professor

O professor passou dois exemplos neste período: o conversor de temperaturas e o cadastro de clientes com JPA. É pouco provável que a prova peça código Spring no papel, mas é bem possível que peça para **reconhecer** as peças, e o Spring é POO pura aplicada:

| Camada | No exemplo do professor | Anotação | O conceito de POO por trás |
| :--- | :--- | :--- | :--- |
| Model | `Cliente`, `TemperaturaDTO` | `@Entity`, `@Id`, `@GeneratedValue` | Encapsulamento: atributos `private` com get/set |
| Repository | `ClienteRepository` | `@Repository` | Uma **interface que herda de interface genérica**: `extends JpaRepository<Cliente, Long>` |
| Service | `ClienteService`, `TemperaturaService` | `@Service` | Onde fica a regra de negócio (a validação de temperatura mínima, por exemplo) |
| Controller | `ClienteController`, `TemperaturaController` | `@Controller`, `@GetMapping`, `@PostMapping` | Recebe a requisição, chama o Service, devolve o nome da página |

Detalhes que dão boa questão:

*   Em `JpaRepository<Cliente, Long>`, o primeiro tipo é a entidade e o segundo é o **tipo do `@Id`**. É generics com dois parâmetros, como o `Par<K, V>` da lista 3.
*   O Controller recebe o Service **pelo construtor** (`public ClienteController(ClienteService service)`). Ninguém dá `new ClienteService()`: o Spring cria e entrega. Isso é **injeção de dependência**.
*   `@Controller` com `return "clientes";` renderiza a página `templates/clientes.html` (Thymeleaf). Já o `@RestController` do Módulo 6 devolve os dados (JSON). É a diferença entre os exemplos do professor e o projeto do curso.
*   `@NotBlank(message = "...")`, `@Size(min = 3, max = 50, message = "...")`, `@Email`, `@Min`, `@Max`: são as anotações **completas** e **de valor único** da seção 9, e o `@Valid` no Controller é o que faz o Spring conferi-las.
*   `@PathVariable Long id` pega o valor de dentro da URL (`/editar/5`); `@RequestParam String valor` pega um campo do formulário.

---

## 14. Checklist da questão discursiva (tipo "Classe Musica")

Antes de entregar, confira linha por linha:

- [ ] `package` na primeira linha, seguindo a regra da URL
- [ ] `extends` na classe pedida
- [ ] Atributos **`private`**
- [ ] Todos os construtores pedidos; o menor delegando para o maior com `this(...)`
- [ ] Valores padrão pedidos no enunciado (ex.: duração igual a **zero**)
- [ ] Getters e setters (é o que o professor entende por "utilizar encapsulamento")
- [ ] `@Override` em **todo** método sobrescrito, inclusive `toString()`
- [ ] `toString()` mostrando **todos** os atributos
- [ ] Tipos coerentes: duração em segundos é `int`, preço é `double`

A resposta modelo está no [Exemplo 2](../modulo7/Exemplo2.java). Tente escrever a sua no papel **antes** de abrir.
