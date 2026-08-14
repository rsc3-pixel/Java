# Lista de Exercícios Prep: Módulo 2 - POO (50 Questões)

Simulado de estilo preparatório focado nas pegadinhas clássicas de POO em Java: construtores, referências, encapsulamento, herança, polimorfismo, classes abstratas e interfaces. O gabarito comentado está ao final do arquivo.

---

## Grupo A: Classes, Objetos e Referências (1 a 10)

### 1. Qual é a diferença fundamental entre uma classe e um objeto?
*   a) Classe roda em tempo de compilação; objeto roda em tempo de execução.
*   b) Classe é o molde que descreve estrutura e comportamento; objeto é a instância concreta alocada em memória.
*   c) Não há diferença prática, são sinônimos em Java.
*   d) Classe armazena dados; objeto armazena apenas métodos.

### 2. O que o operador `new` faz, em ordem?
*   a) Executa o construtor, aloca memória e destrói a referência antiga.
*   b) Aloca memória no heap, executa o construtor e retorna a referência do objeto.
*   c) Apenas reserva memória; o construtor precisa ser chamado manualmente depois.
*   d) Copia um objeto existente para um novo endereço.

### 3. Qual será o resultado?
```java
Ponto p1 = new Ponto(5, 5);
Ponto p2 = new Ponto(5, 5);
System.out.println(p1 == p2);
```
*   a) `true`, pois os atributos são idênticos.
*   b) `false`, pois `==` compara referências de memória e cada `new` gera um endereço distinto.
*   c) Erro de compilação: objetos não podem usar `==`.
*   d) `true`, porque o Java otimiza objetos iguais reutilizando o mesmo endereço.

### 4. Considerando o código abaixo, o que é impresso?
```java
Carro a = new Carro("Civic");
Carro b = a;
b.modelo = "Onix";
System.out.println(a.modelo);
```
*   a) `Civic`
*   b) `Onix`
*   c) `null`
*   d) Erro de compilação.

### 5. O que diferencia um tipo primitivo de um tipo de referência quanto ao armazenamento?
*   a) Primitivo guarda o valor diretamente; referência guarda o endereço do objeto.
*   b) Primitivo vai para o heap; referência vai para a stack.
*   c) Primitivo pode ser `null`; referência não pode.
*   d) Não existe diferença após a compilação.

### 6. Quantas classes `public` podem existir em um único arquivo `.java`?
*   a) Quantas forem necessárias.
*   b) No máximo duas.
*   c) Exatamente uma, e seu nome deve ser igual ao do arquivo.
*   d) Nenhuma; classes públicas exigem arquivos separados obrigatoriamente.

### 7. O que é impresso ao executar `System.out.println(obj);` numa classe que **não** sobrescreve `toString()`?
*   a) Todos os atributos separados por vírgula.
*   b) `null`
*   c) O nome da classe seguido de `@` e o hashcode em hexadecimal.
*   d) Erro de compilação.

### 8. Onde o Java aloca objetos criados com `new`?
*   a) Na stack.
*   b) No heap.
*   c) Na área de constantes.
*   d) Depende do modificador de acesso da classe.

### 9. O que acontece com um objeto que perde todas as suas referências?
*   a) Gera erro em tempo de execução.
*   b) Permanece em memória até o fim do programa.
*   c) Torna-se elegível para coleta pelo Garbage Collector.
*   d) É removido imediatamente pelo compilador.

### 10. Analise:
```java
public class Teste {
    int contador;
    void incrementar() { contador++; }
}
// no main:
Teste t1 = new Teste();
Teste t2 = new Teste();
t1.incrementar();
t1.incrementar();
t2.incrementar();
System.out.println(t1.contador + " " + t2.contador);
```
*   a) `3 3`
*   b) `2 1`
*   c) `2 2`
*   d) `3 1`

---

## Grupo B: Construtores e `this` (11 a 20)

### 11. Qual característica identifica um construtor?
*   a) Ter o mesmo nome da classe e nenhum tipo de retorno declarado.
*   b) Ser sempre `static` e retornar `void`.
*   c) Chamar-se obrigatoriamente `construtor()`.
*   d) Ter o mesmo nome da classe e retornar `void`.

### 12. O código abaixo compila?
```java
public class Pessoa {
    String nome;
    public void Pessoa(String nome) { this.nome = nome; }
}
// no main:
Pessoa p = new Pessoa("Renato");
```
*   a) Sim, funciona normalmente.
*   b) Não. `public void Pessoa(...)` é um método comum, não um construtor; logo `new Pessoa("Renato")` não existe.
*   c) Sim, mas o nome fica `null`.
*   d) Não, porque falta a palavra `static`.

### 13. Qual o problema deste construtor?
```java
public class Produto {
    private String nome;
    public Produto(String nome) { nome = nome; }
}
```
*   a) Nenhum, funciona corretamente.
*   b) O parâmetro é atribuído a si mesmo; o atributo continua `null`. Falta `this.nome = nome;`.
*   c) Não compila por ambiguidade de nomes.
*   d) O atributo precisa ser `public` para receber o valor.

### 14. O que acontece se uma classe declara apenas `public Item(String nome)` e alguém escreve `new Item()`?
*   a) Compila e usa o construtor padrão automático.
*   b) Erro de compilação: o construtor padrão deixa de ser gerado quando qualquer construtor é declarado.
*   c) Compila, mas lança `NullPointerException`.
*   d) O Java escolhe o construtor mais próximo e preenche com `null`.

### 15. Sobre a chamada `this(...)` dentro de um construtor:
*   a) Chama o construtor da superclasse.
*   b) Chama outro construtor da mesma classe e deve ser a primeira instrução.
*   c) Pode aparecer em qualquer linha do construtor.
*   d) É sinônimo de `super(...)`.

### 16. Quantos construtores uma classe pode ter?
*   a) Apenas um.
*   b) No máximo dois: um vazio e um completo.
*   c) Vários, desde que as listas de parâmetros sejam diferentes (sobrecarga).
*   d) Vários, desde que retornem tipos diferentes.

### 17. O que a palavra `this` referencia?
*   a) A classe pai.
*   b) O objeto atual, aquele sobre o qual o método está sendo executado.
*   c) A última instância criada da classe.
*   d) Uma cópia estática dos atributos.

### 18. Qual o resultado?
```java
public class Caixa {
    int volume;
    public Caixa() { this(10); }
    public Caixa(int volume) { this.volume = volume; }
}
// no main:
System.out.println(new Caixa().volume);
```
*   a) `0`
*   b) `10`
*   c) Erro de compilação.
*   d) `null`

### 19. Um construtor pode ser `private`?
*   a) Não, construtores são sempre `public`.
*   b) Sim, e isso impede a instanciação a partir de fora da classe (usado em Singleton e classes utilitárias).
*   c) Sim, mas apenas em classes abstratas.
*   d) Não, o compilador remove o modificador.

### 20. Se um atributo `int` de instância não é inicializado no construtor, qual seu valor?
*   a) `null`
*   b) Lixo de memória.
*   c) `0`, pois atributos de instância recebem valor padrão automaticamente.
*   d) Erro de compilação por falta de inicialização.

---

## Grupo C: Encapsulamento e `static` (21 a 30)

### 21. Qual a ordem correta dos modificadores de acesso, do mais restritivo ao menos restritivo?
*   a) `private` → default → `protected` → `public`
*   b) `private` → `protected` → default → `public`
*   c) default → `private` → `protected` → `public`
*   d) `public` → `protected` → default → `private`

### 22. Um atributo `private` da classe `Pai` pode ser acessado diretamente pela classe `Filha extends Pai`?
*   a) Sim, herança dá acesso total.
*   b) Não. `private` limita o acesso à própria classe; a filha precisa de um getter.
*   c) Sim, desde que estejam no mesmo pacote.
*   d) Apenas se a filha declarar o mesmo atributo.

### 23. Qual é o principal ganho de usar setter em vez de atributo público?
*   a) Desempenho superior em tempo de execução.
*   b) A possibilidade de validar o valor antes de atribuí-lo, protegendo o estado do objeto.
*   c) Reduzir o uso de memória.
*   d) Permitir que o atributo seja `static`.

### 24. Qual afirmação sobre `static` está correta?
*   a) Um atributo `static` tem uma cópia por objeto.
*   b) Um atributo `static` tem uma única cópia compartilhada por toda a classe.
*   c) `static` impede que o atributo seja alterado.
*   d) Métodos `static` só podem ser chamados por objetos.

### 25. Por que um método `static` não consegue acessar atributos de instância diretamente?
*   a) Por uma limitação arbitrária do compilador.
*   b) Porque ele pertence à classe e pode ser chamado sem que exista objeto algum; não haveria de qual instância ler o atributo.
*   c) Porque atributos de instância são sempre `private`.
*   d) Porque `static` roda antes do construtor.

### 26. Qual será a saída?
```java
public class Item {
    static int total = 0;
    int id;
    public Item() { total++; id = total; }
}
// no main:
new Item(); new Item(); Item x = new Item();
System.out.println(Item.total + " " + x.id);
```
*   a) `1 1`
*   b) `3 3`
*   c) `3 1`
*   d) `1 3`

### 27. O modificador `final` aplicado a um **atributo** significa:
*   a) Que ele não pode ser herdado.
*   b) Que seu valor não pode ser alterado após a inicialização.
*   c) Que ele é automaticamente `static`.
*   d) Que ele deve ser `private`.

### 28. Qual combinação declara corretamente uma constante em Java?
*   a) `const double PI = 3.14;`
*   b) `public static final double PI = 3.14;`
*   c) `final static const double PI = 3.14;`
*   d) `static double final PI = 3.14;`

### 29. Numa classe bem encapsulada de conta bancária, por que geralmente **não** existe `setSaldo()`?
*   a) Porque `double` não aceita setter.
*   b) Porque o saldo deve mudar apenas por operações de negócio validadas (depósito e saque), e um setter livre contornaria essas regras.
*   c) Porque atributos numéricos são sempre somente leitura.
*   d) Porque o Java gera o setter automaticamente.

### 30. O que acontece ao tentar compilar?
```java
public class Util {
    private int valor = 10;
    public static void mostrar() { System.out.println(valor); }
}
```
*   a) Compila e imprime `10`.
*   b) Erro de compilação: não é possível referenciar um atributo de instância a partir de um contexto `static`.
*   c) Compila e imprime `0`.
*   d) Erro apenas em tempo de execução.

---

## Grupo D: Herança e Sobrescrita (31 a 40)

### 31. Qual palavra-chave estabelece herança entre classes em Java?
*   a) `implements`
*   b) `extends`
*   c) `inherits`
*   d) `super`

### 32. Java suporta herança múltipla de classes?
*   a) Sim, separando as classes por vírgula após `extends`.
*   b) Não. Uma classe estende apenas uma outra, mas pode implementar várias interfaces.
*   c) Sim, desde que as classes estejam no mesmo pacote.
*   d) Sim, apenas para classes abstratas.

### 33. Sobre a chamada `super(...)` no construtor da filha:
*   a) É opcional e pode aparecer em qualquer linha.
*   b) Deve ser a primeira instrução e é obrigatória quando o pai não possui construtor sem argumentos.
*   c) Só pode ser usada em classes abstratas.
*   d) Substitui a necessidade de construtor na filha.

### 34. O que ocorre se a filha não chama `super(...)` explicitamente e o pai só tem construtor com parâmetros?
*   a) O Java escolhe o primeiro construtor do pai automaticamente.
*   b) Erro de compilação: o compilador tenta inserir `super()` sem argumentos, que não existe.
*   c) Compila e os atributos do pai ficam `null`.
*   d) Compila com aviso de depreciação.

### 35. Qual a diferença entre sobrescrita (override) e sobrecarga (overload)?
*   a) Sobrescrita ocorre na mesma classe com assinaturas diferentes; sobrecarga ocorre entre pai e filha.
*   b) Sobrescrita ocorre entre pai e filha com assinatura idêntica e é resolvida em execução; sobrecarga ocorre na mesma classe com assinaturas diferentes e é resolvida na compilação.
*   c) São sinônimos.
*   d) Sobrescrita exige tipos de retorno diferentes; sobrecarga exige o mesmo tipo.

### 36. Para que serve a anotação `@Override`?
*   a) É obrigatória para que a sobrescrita funcione.
*   b) É opcional, mas faz o compilador verificar se o método realmente sobrescreve algo do pai, transformando erros silenciosos em erros de compilação.
*   c) Torna o método `final`.
*   d) Permite herança múltipla.

### 37. Qual será a saída?
```java
class A { void falar() { System.out.println("A"); } }
class B extends A { void falar() { System.out.println("B"); } }
// no main:
A obj = new B();
obj.falar();
```
*   a) `A`
*   b) `B`
*   c) `A B`
*   d) Erro de compilação.

### 38. Considerando `A obj = new B();` onde `B` tem um método exclusivo `pular()` que não existe em `A`, o que ocorre em `obj.pular();`?
*   a) Executa normalmente, pois o objeto real é `B`.
*   b) Erro de compilação: a referência declarada como `A` só enxerga os membros de `A`. É preciso fazer cast.
*   c) Erro em tempo de execução.
*   d) Imprime `null`.

### 39. Um método marcado como `final` na classe pai:
*   a) Não pode ser sobrescrito pelas filhas.
*   b) Não pode ser chamado pelas filhas.
*   c) Torna a classe inteira final.
*   d) Só pode ser chamado uma vez.

### 40. O que `super.calcularSalario()` faz dentro de um método sobrescrito na filha?
*   a) Chama recursivamente a versão da própria filha, gerando loop infinito.
*   b) Executa a implementação original da classe pai, permitindo estender o comportamento em vez de substituí-lo.
*   c) Cria um novo objeto do tipo pai.
*   d) Não compila dentro de um método com `@Override`.

---

## Grupo E: Polimorfismo, Abstração e Interfaces (41 a 50)

### 41. O que caracteriza uma classe `abstract`?
*   a) Ela não pode ter atributos nem construtores.
*   b) Ela não pode ser instanciada diretamente e pode conter métodos sem implementação.
*   c) Todos os seus métodos são obrigatoriamente abstratos.
*   d) Ela não pode ser estendida.

### 42. Uma classe abstrata pode ter construtor?
*   a) Não, pois nunca é instanciada.
*   b) Sim. Ele é executado através de `super(...)` quando uma filha concreta é instanciada.
*   c) Sim, mas apenas construtores `private`.
*   d) Apenas se não tiver métodos abstratos.

### 43. O que acontece se uma classe concreta estende uma abstrata e **não** implementa todos os métodos abstratos?
*   a) Compila normalmente; os métodos ficam vazios.
*   b) Erro de compilação, a menos que a própria filha também seja declarada `abstract`.
*   c) Erro somente em tempo de execução.
*   d) O Java gera implementações padrão automaticamente.

### 44. Qual a diferença central entre classe abstrata e interface?
*   a) Interfaces são mais rápidas em tempo de execução.
*   b) A classe abstrata modela uma relação "é um" e permite estado compartilhado (uma única por classe); a interface modela "é capaz de" e pode ser implementada em qualquer quantidade.
*   c) Classes abstratas não podem ter métodos concretos.
*   d) Interfaces podem ter atributos de instância comuns.

### 45. Atributos declarados dentro de uma interface são implicitamente:
*   a) `private`
*   b) `protected static`
*   c) `public static final` (constantes)
*   d) atributos de instância comuns

### 46. Qual declaração está sintaticamente correta?
*   a) `class Carro implements Veiculo extends Rastreavel { }`
*   b) `class Carro extends Veiculo implements Rastreavel, Segurado { }`
*   c) `class Carro extends Veiculo, Motor { }`
*   d) `class Carro implements Veiculo, Motor extends Rastreavel { }`

### 47. Qual o resultado?
```java
abstract class Forma { abstract double area(); }
class Quadrado extends Forma {
    double lado;
    Quadrado(double lado) { this.lado = lado; }
    double area() { return lado * lado; }
}
// no main:
Forma f = new Forma();
System.out.println(f.area());
```
*   a) `0.0`
*   b) Erro de compilação: `Forma` é abstrata e não pode ser instanciada.
*   c) `NullPointerException`
*   d) Compila e imprime `null`.

### 48. Para que serve o operador `instanceof`?
*   a) Para criar um novo objeto de um tipo específico.
*   b) Para verificar, em tempo de execução, se um objeto é de determinado tipo, protegendo um cast subsequente.
*   c) Para comparar o conteúdo de dois objetos.
*   d) Para converter tipos primitivos.

### 49. O que ocorre em `Gato g = (Gato) animal;` quando `animal` na verdade referencia um `Cachorro`?
*   a) Erro de compilação.
*   b) Compila, mas lança `ClassCastException` em tempo de execução.
*   c) O objeto é convertido silenciosamente.
*   d) Retorna `null`.

### 50. Qual o principal benefício do polimorfismo em código como o abaixo?
```java
for (Funcionario f : equipe) {
    total += f.calcularSalario();
}
```
*   a) Reduzir o consumo de memória do array.
*   b) Permitir tratar tipos diferentes de forma uniforme, eliminando cadeias de `if`/`instanceof` e permitindo adicionar novos tipos sem alterar este laço.
*   c) Garantir que todos os funcionários tenham o mesmo salário.
*   d) Acelerar a compilação do projeto.

---

# GABARITO COMENTADO

## Grupo A

**1. b)** A classe descreve; o objeto existe. A classe é carregada uma vez pela JVM, os objetos são criados quantas vezes forem necessárias.

**2. b)** Aloca no heap, executa o construtor, retorna a referência. A variável recebe o endereço, não o objeto.

**3. b)** `==` em objetos compara endereços. Para comparar conteúdo, sobrescreva `equals()`.

**4. b)** `Onix`. `b = a` copia a **referência**, não o objeto. Ambas apontam para a mesma área de memória: dois controles remotos, uma televisão.

**5. a)** Primitivo guarda o valor; referência guarda o endereço. É a raiz de toda a confusão entre `==` e `.equals()`.

**6. c)** Exatamente uma classe `public`, com nome idêntico ao arquivo. Classes sem modificador podem acompanhar no mesmo arquivo, como nos exemplos deste módulo.

**7. c)** Algo como `Carro@6d06d69c`. É o `toString()` herdado de `Object`. Sobrescrever é hábito barato que economiza horas de depuração.

**8. b)** No heap. Referências e primitivos locais ficam na stack.

**9. c)** Vira elegível para o Garbage Collector. "Elegível" é a palavra exata: o GC decide quando efetivamente coletar.

**10. b)** `2 1`. `contador` é atributo de instância, então cada objeto tem o seu.

## Grupo B

**11. a)** Mesmo nome da classe e **nenhum** tipo de retorno declarado, nem `void`.

**12. b)** Pegadinha clássica. Ao escrever `void` antes do nome, aquilo virou um método comum chamado `Pessoa`. Como nenhum construtor foi declarado, só existe o padrão vazio, e `new Pessoa("Renato")` não compila.

**13. b)** `nome = nome` atribui o parâmetro a si mesmo por causa do sombreamento de escopo. O atributo continua `null`. Correto: `this.nome = nome;`.

**14. b)** Erro de compilação. O construtor padrão automático só existe enquanto nenhum outro for declarado.

**15. b)** `this(...)` chama outro construtor da mesma classe e precisa ser a primeira instrução. `super(...)` chama o do pai, e pela mesma regra os dois nunca aparecem juntos.

**16. c)** Vários, por sobrecarga. O critério é a lista de parâmetros; tipo de retorno não existe em construtor.

**17. b)** O objeto atual. Serve para desambiguar nomes e para passar o próprio objeto adiante.

**18. b)** `10`. O construtor vazio delega para o de um argumento via `this(10)`.

**19. b)** Sim. Construtor `private` bloqueia o `new` externo, padrão usado em Singleton e em classes utilitárias como `Math`.

**20. c)** `0`. Atributos de instância recebem valor padrão (`0`, `0.0`, `false`, `null`). Cuidado: **variáveis locais não têm esse benefício** e exigem inicialização explícita.

## Grupo C

**21. a)** `private` → default (package) → `protected` → `public`. O `protected` é menos restritivo que o default porque alcança filhas de outros pacotes.

**22. b)** Não. `private` para na própria classe. A filha só chega ao valor por um getter herdado.

**23. b)** A validação. Sem ela, o objeto aceita qualquer estado, inclusive absurdos como saldo negativo ou idade 900.

**24. b)** Uma única cópia por classe, compartilhada. É a base de contadores de instâncias e de constantes.

**25. b)** Porque o método pode ser chamado sem existir objeto algum. Não há "qual instância" de onde ler.

**26. b)** `3 3`. `total` é compartilhado e vale 3 ao final; `x` foi o terceiro criado, então `x.id` é 3.

**27. b)** Valor imutável após a inicialização. Em atributo de referência, trava o **endereço**, não o conteúdo do objeto apontado.

**28. b)** `public static final double PI = 3.14;`. Java não tem `const`.

**29. b)** Porque o saldo é resultado de regras, não um campo livre. Expor um setter transferiria a responsabilidade da regra para quem usa a classe, que é exatamente o que o encapsulamento evita.

**30. b)** Erro de compilação: contexto `static` não enxerga atributo de instância.

## Grupo D

**31. b)** `extends` para classes; `implements` para interfaces.

**32. b)** Não há herança múltipla de classes. Isso evita o problema do diamante. Interfaces resolvem a necessidade de múltiplos contratos.

**33. b)** Primeira instrução, e obrigatória quando o pai não oferece construtor sem argumentos.

**34. b)** Erro de compilação. O compilador insere `super()` implicitamente, e esse construtor não existe.

**35. b)** Override: pai e filha, mesma assinatura, decidido em execução. Overload: mesma classe, assinaturas diferentes, decidido na compilação.

**36. b)** Opcional mas altamente recomendada. Se você errar o nome ou os parâmetros, o compilador acusa em vez de criar um método novo silenciosamente.

**37. b)** `B`. A decisão é feita pelo **objeto real**, não pelo tipo da referência. Isso é despacho dinâmico.

**38. b)** Erro de compilação. Aqui vale o inverso da questão anterior: o **tipo da referência** define o que pode ser chamado. Precisa de cast: `((B) obj).pular();`.

**39. a)** Não pode ser sobrescrito. Serve para travar comportamento crítico.

**40. b)** Executa a versão do pai. Sem o `super.`, a chamada seria recursiva na própria filha e causaria `StackOverflowError`.

## Grupo E

**41. b)** Não instanciável e pode ter métodos sem corpo. Pode ter atributos, construtor e métodos concretos normalmente.

**42. b)** Sim, e ele roda via `super(...)` quando uma filha concreta é criada. Serve para inicializar o estado comum.

**43. b)** Erro de compilação, salvo se a própria filha for declarada `abstract` e empurrar a obrigação para o próximo nível.

**44. b)** "É um" com estado compartilhado (uma só) versus "é capaz de" (quantas quiser). Um Gerente e um Roteador não têm parentesco, mas ambos podem ser `Autenticavel`.

**45. c)** `public static final`. Interfaces não guardam estado de instância.

**46. b)** `extends` vem antes de `implements`, e só pode haver um `extends`.

**47. b)** Erro de compilação. `Forma` é abstrata: um objeto dela poderia receber `area()` sem código para executar.

**48. b)** Verificação de tipo em execução, protegendo o cast. Desde o Java 16 há a forma compacta com *pattern matching*: `if (a instanceof Gato g) { ... }`.

**49. b)** Compila (o compilador aceita a promessa) mas lança `ClassCastException` na execução. Por isso o `instanceof` antes.

**50. b)** Uniformidade e extensibilidade. Adicionar um novo cargo não exige tocar neste laço, o que é justamente o princípio Aberto/Fechado: aberto para extensão, fechado para modificação.
