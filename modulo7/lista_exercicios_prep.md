# Lista de Exercícios Prep: Módulo 7 - Simulado da Prova de POO (30 Questões)

Simulado no formato da prova do prof. Maurício: verdadeiro ou falso, "o que será impresso", "compila ou não" e conceitos. São 30 questões, e não 50 como nos outros módulos: o objetivo é caber numa sessão de estudo na véspera. Todas as saídas foram conferidas rodando no JDK 21.

**Como usar:** responda tudo no papel, **sem rodar nada**, e só depois confira o gabarito comentado no final. Anote as que errou e releia a seção correspondente da [teoria](../modulo7/teoria.md).

---

## Grupo A: Verdadeiro ou falso (1 a 8)

Para cada afirmação, responda **V** ou **F**.

### 1. Uma classe em Java pode possuir múltiplos construtores.

### 2. Em Java, uma classe pode herdar diretamente de múltiplas classes.

### 3. Na linguagem Java, não é possível construir uma interface que herde de outra interface.

### 4. Métodos sobrecarregados devem obrigatoriamente ter tipos de retorno diferentes.

### 5. A anotação `@Deprecated` indica que um elemento não deve mais ser utilizado.

### 6. Anotações em Java são usadas apenas para documentação e não influenciam o comportamento do código.

### 7. Construtores são herdados pela subclasse, assim como os métodos públicos.

### 8. Um método `private` da superclasse pode ser sobrescrito pela subclasse.

---

## Grupo B: O que será impresso? (9 a 16)

### 9. A questão 04 da prova, com os dois métodos `protected`:
```java
class Alfa {
    protected void showA() { System.out.print("Alfa-A "); }
    protected void showB() { System.out.print("Alfa-B "); }
    protected void execute() { showA(); showB(); }
}
class Beta extends Alfa {
    protected void showA() { System.out.print("Beta-A "); }
    protected void showB() { System.out.print("Beta-B "); }
}
// no main:
new Beta().execute();
```
*   a) `Alfa-A Alfa-B`
*   b) `Alfa-A Beta-B`
*   c) `Beta-A Beta-B`
*   d) Erro de compilação: faltou `@Override`.

### 10. E se os métodos forem `static`?
```java
class A {
    static void s() { System.out.print("A.s "); }
    void run() { s(); }
}
class B extends A {
    static void s() { System.out.print("B.s "); }
}
// no main:
new B().run();
```
*   a) `A.s`
*   b) `B.s`
*   c) Erro de compilação: método `static` não pode ter o mesmo nome na filha.
*   d) Erro de execução.

### 11. Ordem dos construtores:
```java
class Q { Q() { System.out.print("Q "); } }
class R extends Q { R() { System.out.print("R "); } }
// no main:
new R();
```
*   a) `R`
*   b) `R Q`
*   c) `Q R`
*   d) Erro de compilação: R não chamou `super()`.

### 12. Encadeamento com `this(...)`:
```java
class P {
    P() { this(5); System.out.print("1 "); }
    P(int x) { System.out.print("2 "); }
}
// no main:
new P();
```
*   a) `1`
*   b) `1 2`
*   c) `2 1`
*   d) Erro de compilação: `this(...)` só pode chamar o construtor da mãe.

### 13. Qual versão roda?
```java
Animal a = new Cachorro();   // Cachorro sobrescreve interagir() com "Au au"
a.interagir();
```
*   a) A versão de `Animal`, porque a variável é do tipo `Animal`.
*   b) A versão de `Cachorro`, porque o objeto real é um `Cachorro`.
*   c) As duas, primeiro a de `Animal` e depois a de `Cachorro`.
*   d) Erro de compilação: é preciso fazer cast antes.

### 14. Atributo `static` contra atributo de instância:
```java
class Cont {
    static int total = 0;
    int meu = 0;
    Cont() { total++; meu++; }
}
// no main:
Cont c1 = new Cont();
Cont c2 = new Cont();
System.out.println(c1.total + " " + c2.total + " " + c1.meu + " " + c2.meu);
```
*   a) `1 2 1 1`
*   b) `2 2 1 1`
*   c) `2 2 2 2`
*   d) `1 1 1 1`

### 15. Resolução de sobrecarga:
```java
static void m(int x)    { System.out.print("int "); }
static void m(double x) { System.out.print("double "); }
// no main:
m(5); m(5L); m('a');
```
*   a) `int double int`
*   b) `int int int`
*   c) `int double double`
*   d) Erro de compilação em `m(5L)`: não existe `m(long)`.

### 16. `super.metodo()` dentro da sobrescrita:
```java
class Mae   { void f() { System.out.print("mae "); } }
class Filha extends Mae {
    @Override
    void f() { super.f(); System.out.print("filha "); }
}
// no main:
Mae m = new Filha();
m.f();
```
*   a) `mae`
*   b) `filha`
*   c) `mae filha`
*   d) `filha mae`

---

## Grupo C: Compila ou não compila? (17 a 25)

Considere a hierarquia `Dispositivo ← Smartphone ← SmartphoneAndroid` e `Dispositivo ← Computador ← Notebook`, com `fazerLigacao()` existindo só em `Smartphone`. Para cada trecho, responda: **(a) compila e roda**, **(b) não compila** ou **(c) compila, mas lança exceção ao rodar**.

### 17. `Smartphone s1 = new Object();`

### 18. `Computador c1 = new Notebook();`

### 19.
```java
Dispositivo d = new SmartphoneAndroid();
d.fazerLigacao();
```

### 20.
```java
Dispositivo d = new Computador();
Smartphone s = (Smartphone) d;
```

### 21.
```java
class Pai { Pai(String nome) { } }
class Filho extends Pai {
    Filho() { System.out.println("oi"); }
}
```

### 22.
```java
class Calc {
    int somar(int a, int b)    { return a + b; }
    double somar(int a, int b) { return a + b; }
}
```

### 23.
```java
class Func       { public double getSalario(double bonus) { return bonus; } }
class Comissao extends Func {
    @Override
    public double getSalario(float bonus) { return bonus; }
}
```

### 24.
```java
class X { public void f() { } }
class Y extends X { protected void f() { } }
```

### 25.
```java
abstract class Figura { abstract void desenhar(); }
// no main:
Figura f = new Figura();
```

---

## Grupo D: Conceitos (26 a 30)

### 26. O sistema MusicBox fica em `www.soundwave.com`. Pela regra da URL vista em aula, o pacote é:
*   a) `www.soundwave.com.musicbox`
*   b) `com.soundwave.musicbox`
*   c) `com.soundwave.MusicBox`
*   d) `musicbox.soundwave.com`

### 27. Uma classe `Filha` está em **outro pacote** e herda de `Mae`. Quais membros de `Mae` ela acessa diretamente?
*   a) `public` e `protected`
*   b) Apenas `public`
*   c) `public`, `protected` e default
*   d) Todos, inclusive `private`, porque a herança copia tudo.

### 28. Sem sobrescrever, o que o `equals` herdado de `Object` compara?
*   a) O valor de cada atributo, um por um.
*   b) O resultado do `toString()` dos dois objetos.
*   c) Se as duas referências apontam para o mesmo objeto na memória.
*   d) O `hashCode()` dos dois, apenas.

### 29. Para que o programa consiga perguntar, rodando, se uma classe tem a anotação `@EntidadePersistente`, a anotação precisa de:
*   a) `@Target(ElementType.TYPE)`
*   b) `@Retention(RetentionPolicy.RUNTIME)`
*   c) `@Retention(RetentionPolicy.SOURCE)`
*   d) Nada: toda anotação é visível em execução.

### 30. Qual declaração é válida?
*   a) `Caixa<int> c = new Caixa<>();`
*   b) `Caixa<Integer> c = new Caixa<>();`
*   c) `Caixa c<Integer> = new Caixa();`
*   d) `Caixa<> c = new Caixa<Integer>();`

---

# Gabarito comentado

## Grupo A

**1. V.** É a sobrecarga de construtores. O que muda entre eles é a lista de parâmetros.

**2. F.** Java não tem herança múltipla de classes: uma única mãe direta. O "múltiplo" vale para interfaces (`implements A, B`).

**3. F.** Interface herda de interface com `extends`, e pode herdar de várias: `interface X extends Y, Z`.

**4. F.** O que precisa mudar são os **parâmetros**. O retorno pode mudar ou não. Mudar **só** o retorno, aliás, é erro de compilação (questão 22).

**5. V.** É uma anotação marcadora. O compilador passa a emitir aviso para quem usar o elemento.

**6. F.** `@Override` faz o compilador recusar sobrescrita errada, e no Spring `@Entity`, `@GetMapping` e `@NotBlank` mudam o que o programa faz. O [Exemplo 2](../modulo7/Exemplo2.java) demonstra uma anotação alterando a saída.

**7. F.** Construtor **não** é herdado (slide 6 de Herança). A filha herda a **obrigação** de chamar um construtor da mãe.

**8. F.** A filha nem enxerga o `private` da mãe. Um método com o mesmo nome na filha é um método novo, e por isso a questão 04 imprime o `showA` de Alfa.

## Grupo B

**9. c)** `Beta-A Beta-B`. Com os dois `protected`, as duas chamadas passam pela ligação dinâmica e rodam a versão do objeto real (Beta). O `@Override` é recomendado, mas não é obrigatório para compilar, por isso a d) está errada.

**10. a)** `A.s`. Método `static` não participa do polimorfismo. A filha pode declarar um com a mesma assinatura (isso se chama *hiding*, esconder), mas a chamada dentro de `run()`, compilada na classe A, fica amarrada a `A.s()`. Mesmo raciocínio do `private`.

**11. c)** `Q R`. O compilador insere `super()` na primeira linha do construtor de R. A mãe termina de construir primeiro, como a fundação antes das paredes.

**12. c)** `2 1`. `new P()` entra no construtor vazio, cuja primeira linha delega para `P(int)`, que imprime `2`. Só na volta o vazio imprime `1`.

**13. b)** O tipo da variável decide o que **pode** ser chamado; o tipo do objeto decide **qual versão** roda.

**14. b)** `2 2 1 1`. `total` é um só para a classe: os dois `new` incrementaram a mesma variável. `meu` é de cada objeto: cada um tem o seu, com valor 1.

**15. a)** `int double int`. O compilador escolhe o parâmetro mais "próximo" que aceite o valor sem perder informação: `5` é `int` exato; `5L` (long) não cabe em `int` mas cabe em `double`; `'a'` (char) alarga para `int`, que é mais próximo que `double`.

**16. c)** `mae filha`. A versão de Filha roda (polimorfismo), e ela mesma chama a da mãe primeiro com `super.f()`.

## Grupo C

**17. (b) Não compila.** `Object` não é um `Smartphone`. A caixa do tipo específico não aceita o tipo genérico.

**18. (a) Compila e roda.** Notebook **é um** Computador.

**19. (b) Não compila.** O objeto até é um smartphone, mas a **variável** é `Dispositivo`, e `Dispositivo` não tem `fazerLigacao()`. O compilador só conhece o tipo da variável.

**20. (c) Compila, mas lança `ClassCastException`.** O cast é uma promessa que o compilador aceita (um Dispositivo *poderia* ser um Smartphone). Na execução a JVM confere o objeto real, vê um Computador, e recusa.

**21. (b) Não compila.** O construtor de Filho recebe um `super()` invisível, mas Pai só tem `Pai(String)`. Conserto: `Filho() { super("algum nome"); ... }`.

**22. (b) Não compila.** Mesmo nome e mesmos parâmetros: para o compilador é o mesmo método declarado duas vezes. Retorno diferente não basta para sobrecarregar.

**23. (b) Não compila.** É o exemplo do slide 25: `float` no lugar de `double` torna o método uma sobrecarga, não uma sobrescrita, e o `@Override` denuncia isso. Sem o `@Override`, compilaria em silêncio e o bug ficaria escondido.

**24. (b) Não compila.** A sobrescrita não pode **diminuir** a visibilidade. Se a mãe prometeu `public`, a filha não pode esconder para `protected` (quebraria o princípio da substituição).

**25. (b) Não compila.** Classe abstrata não pode ser instanciada. O certo é `Figura f = new Circulo();`, com uma subclasse concreta.

## Grupo D

**26. b)** `com.soundwave.musicbox`. Domínio ao contrário, sem o `www`, tudo minúsculo.

**27. a)** `public` e `protected`. O default exige o **mesmo pacote**, e o `private` só a própria classe.

**28. c)** Compara referências, igual a `==`. Por isso quem quer igualdade por conteúdo sobrescreve `equals` (e junto o `hashCode`).

**29. b)** `RUNTIME` faz a anotação sobreviver até a execução. O `@Target` diz **onde** colar, não **até quando** ela dura.

**30. b)** Generics só aceita tipos de referência: `Integer`, não `int`. A d) inverte o lugar do diamante (`<>`), que só pode aparecer do lado do `new`.
