# Lista de Exercícios: Módulo 7 - As Listas do Professor (22 Questões)

Aqui estão as **três listas oficiais** de POO do prof. Maurício, reorganizadas, mais algumas questões no formato da prova. Elas são a melhor previsão do que cai: a discursiva da prova (a classe `Musica`) é praticamente o exercício 1 da lista 3 com outro nome.

Crie seus arquivos na pasta `modulo7/respostas/` com o nome `ExercicioXX.java`.

> [!IMPORTANT]
> Na prova você escreve **no papel**, sem IDE para completar `import`, fechar chave ou apontar erro. Faça pelo menos as questões marcadas com ⭐ à mão primeiro, e só depois digite para conferir se compila.

> [!TIP]
> As questões 12 a 18 já estão guiadas nos desafios [Exercicio1.java](../modulo7/Exercicio1.java) (12 a 14) e [Exercicio2.java](../modulo7/Exercicio2.java) (15 a 18). Se já fez lá, pule.

---

## Grupo A: Conceitos da Lista 1 (1 a 3)

Responda por escrito, em três ou quatro linhas cada. A prova pode ter pergunta aberta assim.

1. Explique a diferença entre classes e objetos.
2. Explique o que são construtores, qual o seu papel na orientação a objetos e quais são as regras para sua criação e utilização em Java.
3. Explique o princípio da abstração.

---

## Grupo B: Encapsulamento (4 a 11, Listas 1 e 2)

4. **Biblioteca Matemática:** Escreva uma classe que funcione como biblioteca de cálculos, que **não possa ser instanciada** e que tenha apenas métodos `static`: `adicionar` (soma dois reais), `media` (recebe um vetor de reais) e `ehPrimo` (recebe um inteiro, devolve `boolean`).
    *   *Dica:* "não pode ser instanciada" se resolve com um construtor `private`. É o que a classe `Math` do Java faz.
5. ⭐ **Pedido:** Classe encapsulada com número do pedido, nome do cliente e valor total, e um construtor.
6. **Veículo:** Classe encapsulada com placa, nome do dono, modelo, fabricante e valor de mercado, e um construtor.
7. **Candidato:** Classe encapsulada com número, nome e quantidade de votos, um construtor e um método que incrementa os votos.
    *   *Pense:* faz sentido existir `setVotos(int)`? Ou só `receberVoto()`? Qual protege melhor a regra?
8. **Animal do Pet Shop:** Classe encapsulada com espécie, idade, vacinado (`boolean`), preço e descrição, e um construtor. Numa classe `Programa`, crie objetos e imprima os atributos.
    *   *Lembre:* o getter de `boolean` costuma ser `isVacinado()`, não `getVacinado()`.
9. **Retângulo:** Classe encapsulada com métodos de área e perímetro, e um `toString()` que mostre os atributos, a área e o perímetro.
10. **Pessoa e IMC:** Classe com nome, idade, altura, peso e sexo, um método que calcula o IMC (peso dividido pela altura ao quadrado) e um `toString()` com o nome e a categoria:
    *   até 18,5: abaixo do peso normal
    *   acima de 18,5 até 25: peso normal
    *   acima de 25 até 30: acima do peso normal
    *   acima de 30: obesidade
11. **Pilha, Vetor e Complexo (Lista 2, questões 4 a 6):** Escolha **uma** das três para a véspera:
    *   `Pilha` com `boolean adicionar(Object o)`, `Object retirar()` e `boolean estaVazia()` (quem entra por último sai primeiro).
    *   `Vetor` tridimensional com `Vetor adicionar(Vetor v)`, `void multiplicacaoEscalar(double num)` e `double produtoEscalar(Vetor v)`.
    *   `NumeroComplexo` com `adicionar`, `subtrair` e `multiplicar`, cada um devolvendo um **novo** `NumeroComplexo`. Multiplicação: `(a+bi)(c+di) = (ac-bd) + (ad+bc)i`.

---

## Grupo C: Lista 3 (12 a 18)

12. ⭐ **Pedido com sobrecarga:** Classe `Pedido` encapsulada com código (`int`), nome do cliente (`String`) e valor (`double`). Dois construtores: um com tudo e outro só com código e nome, que deixa o valor em zero. Sobrescreva `toString()`. **Obrigatório** usar `@Override` em todo método sobrescrito.
13. **Interface Autenticavel:** Interface com `boolean autenticar(String senha)` e uma classe `Usuario` (login e senha privados) que a implementa. **Obrigatório** `@Override`.
14. **Anotação `@EntidadePersistente`:** Aplicável só a classes e disponível em execução. Anote com ela uma classe `Cliente` (id e nome).
15. **`Caixa<T>`:** atributo privado `conteudo` do tipo `T` e os métodos `guardar(T)`, `T retirar()` e `boolean estaVazia()`.
16. **Testando a Caixa:** Crie uma caixa de `String`, guarde `"usando Generics"`, chame `estaVazia`, retire, chame `estaVazia` de novo, imprimindo cada resposta. Crie também uma caixa de `Integer`.
17. **`Par<K, V>`:** atributos privados `chave` (`K`) e `valor` (`V`), construtor, getters e `@Override toString()` no formato `"(chave, valor)"`.
18. **Testando o Par:** Crie `notaAluno` (aluno e nota) e `codigoProduto` (código e descrição), imprima os pares e depois só as chaves.

---

## Grupo D: No formato da prova (19 a 22)

19. ⭐ **A discursiva da prova:** Sem olhar o [Exemplo 2](../modulo7/Exemplo2.java), escreva a classe `Musica` que herda de `class Midia { }`, no pacote do sistema MusicBox (`www.soundwave.com`), com nome e duração em segundos, encapsulamento, dois construtores (só o nome, com duração zero; e nome mais duração), `@Override` e `toString()`. Depois confira com o [checklist da teoria](../modulo7/teoria.md).
20. ⭐ **Variação da discursiva:** Mesmo sistema, agora a classe `Podcast`, que também herda de `Midia`, com título, apresentador e número do episódio. Três construtores: só o título (apresentador `"Desconhecido"`, episódio 1); título e apresentador (episódio 1); e completo. Os dois menores devem delegar para o completo com `this(...)`.
21. **Prever a saída:** Escreva num papel a saída do [Exemplo1.java](../modulo7/Exemplo1.java) **antes** de rodá-lo. Depois rode e marque onde errou.
22. **Montando a hierarquia da questão 02:** Crie as classes `Dispositivo`, `Smartphone`, `SmartphoneAndroid`, `Computador` e `Notebook`. No `main`, escreva as sete atribuições abaixo, preveja quais compilam e confirme com o compilador:
    ```java
    Smartphone s1 = new Object();
    Object o1 = new Computador();
    Dispositivo d3 = new SmartphoneAndroid();
    Smartphone s2 = new SmartphoneAndroid();
    Computador c1 = new Notebook();
    Computador c2 = new Smartphone();
    Notebook n1 = new Computador();
    ```

---

## Respostas comentadas das questões que costumam travar

**Q4 (classe que não pode ser instanciada):** um construtor `private` vazio basta. Com ele declarado, o Java não cria o construtor padrão público, e ninguém de fora consegue dar `new`. Os métodos são `static` porque não dependem de estado nenhum: `Matematica.adicionar(2, 3)`.

**Q7 (`setVotos` ou `receberVoto`):** `receberVoto()`. Um `setVotos(int)` permite que qualquer um escreva `setVotos(1000000)`. O método de comportamento deixa a única operação válida (somar um) protegida dentro do objeto. É isso que o slide 20 chama de "garantir que sejam usados de forma correta".

**Q11 (Pilha com `Object`):** é o problema que o slide 32 de Polimorfismo mostra. Ao retirar, você recebe `Object` e precisa de cast para usar. A versão moderna seria `Pilha<T>`, que é exatamente o que a lista 3 ensina com `Caixa<T>`.

**Q22:** compilam `o1`, `d3`, `s2` e `c1`. Não compilam `s1` (mãe na caixa da filha), `c2` (primos, nenhum herda do outro) e `n1` (mãe na caixa da filha).
