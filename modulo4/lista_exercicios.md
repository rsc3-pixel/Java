# Lista de Exercícios: Módulo 4 - Exceções e Arquivos (50 Questões)

Exercícios de **implementação**. Crie seus arquivos em `modulo4/respostas/` com o nome `ExercicioXX.java`.

> [!WARNING]
> Os exercícios de arquivo criam e apagam dados no disco. Trabalhe sempre dentro de uma pasta de teste (`modulo4/respostas/dados/`), nunca apontando para pastas do sistema. E lembre: `new FileWriter("x.txt")` **sem** o segundo parâmetro apaga todo o conteúdo do arquivo.

---

## Grupo A: try, catch e finally (1 a 12)

1. **Divisão Segura:** Peça dois inteiros ao usuário e exiba a divisão. Trate `ArithmeticException` para o caso de o divisor ser zero.
2. **Índice Inválido:** Crie um array de 5 posições e tente acessar o índice 10 dentro de um `try`. Trate `ArrayIndexOutOfBoundsException`.
3. **Conversão de Texto:** Peça um texto ao usuário e converta para `int` com `Integer.parseInt()`. Trate `NumberFormatException`.
4. **Null Protegido:** Declare uma `String` como `null` e chame `.length()` dentro de um `try`. Trate `NullPointerException` e explique num comentário por que validar antes seria melhor que capturar depois.
5. **Ordem dos catch:** Escreva um `try` com `catch (Exception e)` **antes** de um `catch (ArithmeticException e)`. Tente compilar, leia o erro e corrija a ordem.
6. **Fluxo Interrompido:** Imprima "A", cause um erro, imprima "B" (após o erro), e imprima "C" no `catch`. Rode e comprove que "B" nunca aparece.
7. **finally Sempre:** Escreva um `try/catch/finally` e rode duas vezes: uma sem erro e outra com erro. Comprove que o `finally` executa nos dois casos.
8. **finally com return:** Crie um método que dê `return` dentro do `try` e imprima algo no `finally`. Observe a ordem real de execução.
9. **multi-catch:** Trate `NumberFormatException` e `ArithmeticException` num único `catch` usando o operador `|`.
10. **Métodos da Exceção:** Num `catch`, imprima `getMessage()`, `getClass().getSimpleName()` e chame `printStackTrace()`. Compare as três saídas.
11. **Loop Resiliente:** Percorra o array `{"10", "abc", "5", "0", null}`, convertendo cada item para `int` e dividindo 100 pelo resultado. O laço deve terminar por completo, tratando todos os erros.
12. **Scanner com Buffer:** Peça um número com `nextInt()`. Se o usuário digitar letra, trate `InputMismatchException` **e limpe o buffer** com `nextLine()`. Sem a limpeza, o programa entra em loop infinito. Teste os dois casos.

---

## Grupo B: throw, throws e Exceções Customizadas (13 a 24)

13. **Primeiro throw:** Crie `validarIdade(int idade)` que lance `IllegalArgumentException` quando a idade for negativa ou maior que 130.
14. **Validador de Senha:** Crie `validarSenha(String senha)` que lance exceção se a senha tiver menos de 8 caracteres.
15. **throws na Assinatura:** Crie um método que declare `throws Exception` e não trate nada. Trate no `main` de quem o chama.
16. **Checked Obrigatória:** Crie uma exceção `SaldoInsuficienteException extends Exception` e comprove que o compilador **obriga** o `try/catch` de quem chama.
17. **Unchecked Opcional:** Refaça o exercício anterior com `extends RuntimeException` e comprove que o `try/catch` deixa de ser obrigatório.
18. **Exceção com Dados:** Crie uma exceção customizada que carregue um campo extra (ex: `saldoDisponivel`) e um getter para ele. Use esse dado na mensagem do `catch`.
19. **Estoque Insuficiente:** Crie `ProdutoSemEstoqueException` e uma classe `Estoque` cujo método `retirar(int qtd)` a lance quando não houver quantidade suficiente.
20. **Idade Mínima:** Crie `MenorDeIdadeException` e um método `comprarBebida(int idade)` que a lance para idades abaixo de 18.
21. **CPF Inválido:** Crie `CpfInvalidoException` e um validador que a lance quando o CPF não tiver exatamente 11 dígitos numéricos.
22. **Propagação em Cadeia:** Crie três métodos onde A chama B, que chama C. Lance a exceção em C e trate apenas em A, sem `try/catch` em B.
23. **Relançar com Contexto:** Capture uma `NumberFormatException` e relance-a como uma exceção customizada sua, passando a original como causa: `throw new MinhaException("msg", e);`.
24. **Hierarquia Própria:** Crie `ErroDeNegocioException` e duas filhas dela. No `catch`, capture apenas a classe pai e comprove que ela captura as duas filhas.

---

## Grupo C: Escrita em Arquivos (25 a 34)

25. **Primeiro Arquivo:** Crie um arquivo `teste.txt` e escreva a frase "Olá, arquivos!" nele usando `FileWriter`.
26. **Múltiplas Linhas:** Escreva 5 linhas num arquivo usando `BufferedWriter` e `newLine()`.
27. **Sobrescrever vs Append:** Escreva num arquivo com `FileWriter(arquivo, false)` e depois com `true`. Abra o arquivo e comprove a diferença.
28. **try-with-resources:** Reescreva o exercício 26 usando `try-with-resources` e compare o tamanho do código com a versão que usa `finally`.
29. **PrintWriter com printf:** Gere um arquivo de relatório com valores monetários formatados usando `PrintWriter` e `printf("%.2f")`.
30. **Criar Pasta:** Crie a pasta `dados/relatorios/` com `mkdirs()` e grave um arquivo dentro dela.
31. **Log com Timestamp:** Crie um método `registrarLog(String mensagem)` que acrescente ao arquivo `log.txt` uma linha no formato `[data hora] mensagem`. Use `LocalDateTime.now()`.
32. **Salvar Lista:** Crie uma `List<String>` com 10 nomes e grave cada um numa linha do arquivo.
33. **Salvar Objetos em CSV:** Crie a classe `Aluno` (nome, nota) com um método `paraCsv()` e grave uma lista de 5 alunos no formato `nome;nota`.
34. **Files.writeString:** Grave o conteúdo de uma String inteira num arquivo usando `Files.writeString()` e compare com a abordagem do `BufferedWriter`.

---

## Grupo D: Leitura de Arquivos (35 a 44)

35. **Ler Linha a Linha:** Leia e exiba o conteúdo de um arquivo com `BufferedReader` e `readLine()`.
36. **Contar Linhas:** Conte quantas linhas existem num arquivo de texto.
37. **Contar Palavras:** Conte o total de palavras de um arquivo usando `split(" ")` em cada linha.
38. **Ler com Scanner:** Leia o mesmo arquivo usando `Scanner(new File(...))` e `hasNextLine()`. Compare com o `BufferedReader`.
39. **Arquivo Inexistente:** Tente ler um arquivo que não existe e trate `FileNotFoundException` com uma mensagem amigável.
40. **Numerar Linhas:** Leia um arquivo e exiba cada linha precedida do seu número (`1: texto`).
41. **Buscar Palavra:** Peça uma palavra ao usuário e informe em quais linhas do arquivo ela aparece.
42. **Files.readAllLines:** Leia um arquivo para uma `List<String>` de uma vez e explique num comentário quando essa abordagem é ruim.
43. **Carregar Objetos do CSV:** Leia o arquivo do exercício 33 de volta e reconstrua a `List<Aluno>`, calculando a média da turma.
44. **Leitura Tolerante:** Adicione uma linha corrompida no CSV do exercício anterior e faça a leitura ignorá-la com um `try/catch` **dentro** do laço, sem abortar o resto.

---

## Grupo E: Integração (45 a 50)

45. **Copiador de Arquivo:** Leia um arquivo linha a linha e grave o conteúdo idêntico num segundo arquivo.
46. **Filtro de Arquivo:** Leia um arquivo e grave num segundo apenas as linhas que contenham determinada palavra.
47. **Estatísticas de Arquivo:** Gere um relatório com total de linhas, total de palavras, total de caracteres e a linha mais longa de um arquivo de texto.
48. **Listar Diretório:** Percorra uma pasta com `listFiles()` e exiba nome, tamanho em bytes e se cada item é arquivo ou diretório.
49. **Agenda Persistente:** Faça um CRUD de contatos em menu de terminal (adicionar, listar, buscar, remover) que salve em CSV e recarregue ao iniciar. Trate todos os erros possíveis.
50. **Sistema Bancário Completo:** Integre tudo o que viu:
    *   classe `Conta` com exceções customizadas `SaldoInsuficienteException` e `ValorInvalidoException`;
    *   persistência das contas em CSV, carregada ao iniciar e salva ao sair;
    *   arquivo `extrato.txt` em modo append registrando cada operação com data e hora;
    *   menu de terminal que nunca quebra, qualquer que seja a entrada do usuário.

---

## Gabarito Comentado

**Q4 (NullPointerException):** capturar NPE é quase sempre o remédio errado. Ele sinaliza que faltou validação (`if (texto != null)`) ou que um método devolveu `null` onde não deveria. Tratar o sintoma esconde a causa e o bug reaparece em outro lugar.

**Q5 (ordem dos catch):** o compilador rejeita com "exception has already been caught". Como `ArithmeticException` é subclasse de `Exception`, o `catch` genérico já a captura, tornando o específico inalcançável. Regra: do mais específico para o mais genérico, sempre.

**Q8 (finally com return):** o `finally` executa **antes** do valor chegar a quem chamou. Detalhe avançado: se o `finally` também tiver `return`, ele sobrescreve o do `try` e engole exceções pendentes. Por isso nunca se coloca `return` dentro de `finally`.

**Q12 (buffer do Scanner):** quando `nextInt()` falha, o texto inválido **permanece** no buffer. Sem `nextLine()` para descartá-lo, a próxima leitura encontra o mesmo lixo e o programa entra em loop infinito de erros. É a pegadinha mais comum ao combinar `Scanner` com exceções.

**Q16 e Q17 (checked x unchecked):** com `extends Exception`, o compilador exige `try/catch` ou `throws`. Com `extends RuntimeException`, não exige nada. A escolha é de design: use checked quando quem chama **pode reagir** de forma útil; use unchecked para violação de regra que indica uso incorreto da API.

**Q22 (propagação):** B não precisa de `try/catch` nenhum, apenas declarar `throws` se a exceção for checked. Isso ilustra o princípio central do módulo: trate onde há informação para decidir algo útil, não no primeiro lugar onde é possível.

**Q23 (relançar com causa):** use o construtor `super(mensagem, causa)`. Preservar a exceção original é essencial, porque o *stack trace* passa a mostrar "causado por", apontando a origem real. Relançar sem a causa apaga o rastro e dificulta a depuração.

**Q27 (sobrescrever x append):** `new FileWriter(arquivo)` e `new FileWriter(arquivo, false)` são equivalentes e **apagam o conteúdo**. Só `true` preserva. Esquecer isso é a forma mais comum de perder dados nos exercícios.

**Q28 (try-with-resources):** além de mais curto, ele é mais **correto**. Na versão com `finally`, é preciso testar `if (writer != null)` antes de fechar, porque uma falha no construtor deixaria a variável nula. O `try-with-resources` trata isso automaticamente e ainda fecha na ordem inversa da abertura quando há vários recursos.

**Q39 (arquivo inexistente):** `FileNotFoundException` é subclasse de `IOException`, então um `catch (IOException e)` já a cobre. Capture a específica apenas quando o tratamento for diferente do de outros erros de E/S.

**Q42 (readAllLines):** carrega o arquivo **inteiro** na memória. Num arquivo de alguns GB, isso estoura o heap com `OutOfMemoryError`. Para arquivos grandes, leia linha a linha com `BufferedReader`, que mantém apenas uma linha por vez na memória.

**Q44 (leitura tolerante):** a posição do `try` é a decisão de design do exercício. Dentro do laço, uma linha ruim é ignorada e as demais são lidas. Envolvendo o laço, a primeira linha ruim aborta tudo. Nenhuma das duas é universalmente certa: depende de o dado ser crítico ou descartável.

**Q49 e Q50 (sistemas completos):** a arquitetura esperada separa três responsabilidades: as classes de domínio (`Conta`, `Contato`) com as regras e as exceções; uma classe de persistência com `salvar()` e `carregar()`; e o `main` com o menu, que é o único lugar que fala com o usuário e, portanto, o único que exibe mensagens de erro. Essa separação é exatamente a que o Módulo 6 vai formalizar como camadas (Model, Repository, Controller).
