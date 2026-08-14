# Lista de Exercícios: Módulo 2 - POO (50 Questões)

Estes exercícios são de **implementação**: você escreve o código do zero. O objetivo é sair do `main` gigante do Módulo 1 e passar a modelar sistemas com classes.

Crie seus arquivos na pasta `modulo2/respostas/` com o nome `ExercicioXX.java` (onde XX é o número da questão).

> [!TIP]
> Em quase todo exercício, o enunciado pede uma classe **e** um `main` para testá-la. Coloque as duas no mesmo arquivo: só a classe que dá nome ao arquivo precisa ser `public`.

---

## Grupo A: Classes, Objetos e Atributos (1 a 10)

1. **Classe Pessoa:** Crie a classe `Pessoa` com os atributos `nome` (String) e `idade` (int). No `main`, crie dois objetos, preencha os atributos e exiba os dados de cada um.
2. **Classe Livro:** Crie a classe `Livro` com `titulo`, `autor` e `paginas`. Instancie três livros e exiba todos.
3. **Método de Comportamento:** Adicione à classe `Pessoa` um método `void apresentar()` que imprime `"Olá, meu nome é [nome] e tenho [idade] anos."`. Chame-o para cada objeto.
4. **Retorno de Método:** Crie a classe `Retangulo` com `largura` e `altura`, e os métodos `double calcularArea()` e `double calcularPerimetro()`.
5. **Objetos Independentes:** Crie dois objetos `Retangulo` com medidas diferentes e comprove, exibindo as áreas, que alterar um não afeta o outro.
6. **Classe Circulo:** Crie `Circulo` com o atributo `raio` e os métodos `calcularArea()` (use `Math.PI`) e `calcularCircunferencia()`.
7. **Classe Aluno:** Crie `Aluno` com `nome` e três notas (`double`). Adicione `double calcularMedia()` e `boolean estaAprovado()` (média maior ou igual a 7).
8. **Array de Objetos:** Crie um array `Aluno[]` com 5 alunos, percorra com `for-each` e exiba nome, média e situação de cada um.
9. **Classe Termômetro:** Crie `Termometro` com o atributo `celsius` e os métodos `paraFahrenheit()` e `paraKelvin()`.
10. **Contador de Cliques:** Crie a classe `Contador` com um atributo `valor`, e os métodos `incrementar()`, `decrementar()` e `zerar()`. Teste a sequência de chamadas no `main`.

---

## Grupo B: Construtores e `this` (11 a 20)

11. **Construtor Simples:** Refaça a classe `Pessoa` para que nome e idade sejam obrigatórios no construtor. Não deve existir mais a possibilidade de criar uma pessoa sem nome.
12. **Uso do `this`:** Escreva um construtor cujos parâmetros tenham exatamente o mesmo nome dos atributos, e use `this` para diferenciá-los.
13. **Construtor Sobrecarregado:** Na classe `Livro`, crie dois construtores: um com `(titulo, autor, paginas)` e outro apenas com `(titulo)`, que preenche autor como `"Desconhecido"` e páginas como `0`.
14. **Encadeamento com `this(...)`:** Refaça o exercício 13 fazendo o construtor menor chamar o maior com `this(titulo, "Desconhecido", 0)`.
15. **Validação no Construtor:** Crie a classe `Produto` cujo construtor recebe o preço, mas armazena `0.01` se o valor recebido for menor ou igual a zero.
16. **Construtor Padrão Sumido:** Crie uma classe com apenas um construtor com parâmetros. Tente instanciar com `new Classe()` sem argumentos, observe o erro de compilação e explique num comentário o motivo.
17. **Classe Data:** Crie `Data` com `dia`, `mes` e `ano`, cujo construtor valide se o mês está entre 1 e 12 (caso contrário, define 1). Adicione `String formatar()` devolvendo `dd/mm/aaaa`.
18. **Classe Endereco:** Crie `Endereco` com rua, número, cidade e estado, todos definidos via construtor, e um método `exibir()`.
19. **Objeto dentro de Objeto:** Crie a classe `Cliente` que tem, como atributo, um objeto do tipo `Endereco`. Instancie um cliente completo e exiba tudo.
20. **Classe Retangulo com Construtor:** Refaça `Retangulo` recebendo largura e altura no construtor, e impeça valores negativos (substitua por 1).

---

## Grupo C: Encapsulamento (21 a 30)

21. **Tornando Privado:** Pegue a classe `Pessoa` do exercício 11, torne os atributos `private` e crie os getters correspondentes.
22. **Setter com Validação:** Adicione `setIdade(int idade)` que só aceita valores entre 0 e 130, imprimindo um aviso quando recusar.
23. **Atributo Somente Leitura:** Crie a classe `Cpf` com o atributo `numero` que tem getter, mas **não tem setter**. O valor só é definido no construtor.
24. **Conta Corrente:** Crie `ContaCorrente` com `saldo` privado e os métodos `depositar(double)` e `sacar(double)`. O saque deve recusar valores maiores que o saldo.
25. **Sem setSaldo:** No exercício anterior, explique num comentário por que criar um `setSaldo()` público destruiria a regra de negócio.
26. **Cofrinho:** Crie a classe `Cofrinho` que só aceita depósitos de moedas válidas (0.05, 0.10, 0.25, 0.50, 1.00). Qualquer outro valor é recusado.
27. **Senha Protegida:** Crie `Usuario` com `login` público via getter e `senha` privada sem getter. Adicione `boolean verificarSenha(String tentativa)`.
28. **Estoque Controlado:** Crie `ItemEstoque` com `quantidade` privada, e os métodos `entrada(int)` e `saida(int)`. A saída nunca pode deixar a quantidade negativa.
29. **Temperatura com Limite:** Crie `Forno` com `temperatura` privada, aceitando apenas valores entre 0 e 300 através do setter.
30. **Getter Calculado:** Crie `Funcionario` com `salarioBase` e `descontoInss` privados, e um getter `getSalarioLiquido()` que **calcula** o valor em vez de armazená-lo.

---

## Grupo D: Herança e `super` (31 a 40)

31. **Herança Básica:** Crie a classe `Animal` com `nome` e o método `emitirSom()`. Crie `Cachorro` e `Gato` estendendo `Animal`, cada uma sobrescrevendo `emitirSom()`.
32. **Chamando `super(...)`:** Crie `Veiculo` com construtor `(String marca)` e `Carro extends Veiculo` cujo construtor recebe marca e portas, repassando a marca via `super`.
33. **Estendendo o Pai com `super.metodo()`:** Crie `Funcionario` com `calcularSalario()` retornando o salário base, e `Gerente` que sobrescreve o método retornando `super.calcularSalario() + bonus`.
34. **Três Níveis:** Crie a hierarquia `Ser` → `Animal` → `Mamifero` → `Cachorro`, cada nível acrescentando um atributo. Instancie um cachorro e exiba todos os atributos herdados.
35. **`protected` na Prática:** Crie um pai com um atributo `protected` e outro `private`. Tente acessar os dois de dentro da classe filha e explique num comentário por que apenas um funciona.
36. **Hierarquia de Contas:** Crie `Conta` (com saldo e saque simples), `ContaPoupanca` (saque livre) e `ContaCorrente` (saque cobra taxa de R$ 0,50).
37. **Hierarquia de Formas:** Crie `Forma` com `calcularArea()` retornando 0, e as filhas `Quadrado`, `Triangulo` e `Circulo` sobrescrevendo o cálculo.
38. **Sobrescrita de `toString()`:** Adicione `toString()` na classe pai e nas filhas do exercício 37, e imprima os objetos diretamente com `System.out.println`.
39. **Método Final:** Crie um método marcado como `final` na classe pai, tente sobrescrevê-lo na filha e explique o erro de compilação num comentário.
40. **Hierarquia de Mídia:** Crie `Midia` (com título e duração) e as filhas `Musica` (com artista) e `Filme` (com diretor), cada uma com sua própria versão de `exibirFicha()`.

---

## Grupo E: Polimorfismo, Abstração e Interfaces (41 a 50)

41. **Array Polimórfico:** Usando a hierarquia de `Forma` (exercício 37), crie um array `Forma[]` misturando os três tipos e some todas as áreas num único laço, sem usar `if`.
42. **Classe Abstrata:** Transforme `Forma` em `abstract` e `calcularArea()` em método abstrato. Comprove que `new Forma()` deixou de compilar.
43. **Método Concreto na Abstrata:** Adicione à `Forma` abstrata um método concreto `exibirRelatorio()` que chama `calcularArea()`. Observe que ele funciona sem saber qual filha vai rodar.
44. **Interface Simples:** Crie a interface `Imprimivel` com o método `void imprimir()` e faça duas classes de hierarquias diferentes implementarem-na.
45. **Múltiplas Interfaces:** Crie `Ligavel` e `Recarregavel` e uma classe `Celular` que implemente as duas.
46. **Interface como Tipo:** Crie um array `Imprimivel[]` com objetos de classes sem parentesco algum e chame `imprimir()` em todos num laço.
47. **`instanceof` e Cast:** Percorra um array `Animal[]` e, apenas para os objetos que forem `Cachorro`, chame um método exclusivo dessa classe (ex.: `abanarRabo()`).
48. **Sistema de Pagamento:** Crie a interface `FormaPagamento` com `double calcularTaxa(double valor)` e as implementações `Pix` (taxa 0), `Debito` (2%) e `Credito` (5%). Compare as três com o mesmo valor.
49. **Abstrata + Interface Juntas:** Crie a abstrata `Instrumento` (com `tocar()` abstrato) e a interface `Afinavel`. Faça `Violao` estender e implementar; faça `Bateria` apenas estender.
50. **Sistema Completo:** Modele uma **biblioteca**: a abstrata `ItemAcervo` (título, `calcularMulta(int diasAtraso)` abstrato), as filhas `LivroFisico` (R$ 1,00/dia), `Revista` (R$ 0,50/dia) e `Dvd` (R$ 2,00/dia), e a interface `Emprestavel` com `boolean estaDisponivel()`. Crie um array polimórfico e gere um relatório com o total de multas.

---

## Gabarito Comentado

> [!NOTE]
> Os exercícios acima são de implementação livre: existe mais de uma solução correta. Abaixo estão as **respostas conceituais** das questões que pedem explicação, além dos pontos onde a maioria erra.

**Q5 (objetos independentes):** cada `new` aloca um espaço próprio no heap. Os objetos compartilham a classe, nunca os valores dos atributos de instância.

**Q16 (construtor padrão sumido):** o Java só cria o construtor vazio automático quando a classe **não declara nenhum** construtor. Ao escrever qualquer um, o padrão deixa de existir e `new Classe()` não compila.

**Q25 (por que não criar `setSaldo`):** um setter público de saldo permitiria a qualquer ponto do sistema atribuir um valor arbitrário, contornando as validações de `sacar()`. A regra "não sacar mais do que se tem" deixaria de estar protegida dentro do objeto e passaria a depender da disciplina de quem usa a classe.

**Q35 (`protected` vs `private`):** a filha enxerga o atributo `protected` porque ele foi declarado justamente para descendentes e para o mesmo pacote. O `private` é visível **apenas** dentro da classe onde foi declarado, nem a filha alcança. Para acessá-lo, a filha precisa de um getter `public` ou `protected` no pai.

**Q39 (método `final`):** `final` em um método proíbe a sobrescrita. Serve para travar comportamentos que não podem variar nas filhas, como uma regra de segurança ou um cálculo fiscal.

**Q42 (classe abstrata):** o compilador recusa `new Forma()` porque a classe tem método sem implementação. Um objeto dela poderia receber uma chamada de `calcularArea()` sem nenhum código para executar.

**Q43 (método concreto chamando abstrato):** este é o padrão *Template Method*. O pai define o esqueleto do algoritmo e delega os passos variáveis às filhas. A chamada é resolvida em tempo de execução pelo tipo real do objeto.

**Q47 (`instanceof` e cast):** o `instanceof` protege o cast. Sem ele, converter um `Gato` para `Cachorro` compila mas lança `ClassCastException` na execução. Desde o Java 16 existe a forma compacta: `if (a instanceof Cachorro c) { c.abanarRabo(); }`.

**Erro mais comum do módulo inteiro:** esquecer que a referência declarada limita **quais métodos podem ser chamados**, enquanto o objeto real determina **qual implementação roda**. Em `Funcionario f = new Gerente(...)`, chamar `f.calcularSalario()` roda a versão do Gerente, mas chamar um método exclusivo de Gerente não compila sem cast.
