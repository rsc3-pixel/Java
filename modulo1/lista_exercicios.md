# Lista de Exercícios: Módulo 1 (50 Questões)

Para dominar a sintaxe básica de Java, tipos de dados, operadores e entrada/saída (`Scanner`), resolva os desafios abaixo.
Crie seus arquivos na pasta `curso-java/modulo1/respostas/` com o nome `ExercicioXX.java` (onde XX é o número da questão).

---

## Grupo A: Saída de Dados e Aritmética Simples (1 a 10)

1. **Olá Mundo:** Escreva um programa que exiba a mensagem "Olá, Java!" na tela.
2. **Nome e Idade:** Crie variáveis para armazenar seu nome e sua idade. Exiba uma frase formatada: `"Olá, meu nome é [nome] e tenho [idade] anos."`
3. **Soma Simples:** Defina dois números inteiros em variáveis e exiba a soma deles.
4. **Operações Básicas:** Defina dois números reais (`double`) e exiba a soma, subtração, multiplicação e divisão entre eles.
5. **Média Aritmética:** Crie três variáveis `double` com notas escolares (ex: 7.5, 8.0, 6.0) e calcule a média aritmética delas.
6. **Quadrado de um Número:** Defina um número inteiro e exiba o seu valor ao quadrado.
7. **Área do Retângulo:** Crie variáveis para a largura e a altura de um retângulo. Calcule e exiba a área (`largura * altura`).
8. **Perímetro do Retângulo:** Usando as mesmas variáveis do exercício anterior, calcule e exiba o perímetro (`2 * (largura + altura)`).
9. **Cálculo de Desconto:** Crie uma variável com o preço de um produto e outra com um desconto (ex: 15 para 15%). Calcule e exiba o novo preço com desconto.
10. **Conversão de Dólar:** Crie uma variável com um valor em reais e outra com a cotação atual do dólar. Calcule e exiba o valor equivalente em dólares.

---

## Grupo B: Leitura de Dados com Scanner (11 a 20)

11. **Leitura de Nome:** Peça para o usuário digitar seu nome usando `Scanner` e exiba uma mensagem de boas-vindas personalizada.
12. **Leitura de Números:** Peça dois números inteiros ao usuário, some-os e exiba o resultado.
13. **Calculadora de Dobro:** Peça um número decimal (`double`) e exiba o dobro dele.
14. **Calculadora de Idade:** Peça o ano de nascimento do usuário e o ano atual. Calcule e exiba a idade aproximada dele.
15. **Área do Círculo:** Solicite o raio de um círculo, calcule sua área usando a fórmula $\text{Área} = \pi \times \text{raio}^2$. (Use `Math.PI`).
16. **Temperatura Celsius para Fahrenheit:** Peça uma temperatura em graus Celsius e converta para Fahrenheit usando a fórmula: $F = (C \times 9/5) + 32$.
17. **Temperatura Fahrenheit para Celsius:** Peça uma temperatura em Fahrenheit e converta para Celsius: $C = (F - 32) \times 5/9$.
18. **Consumo de Combustível:** Peça a distância total percorrida (em km) e o total de combustível gasto (em litros). Calcule e exiba o consumo médio do veículo (km/l).
19. **Conversor de Metros para Centímetros:** Peça um valor em metros e exiba o valor convertido em centímetros.
20. **Salário Mensal:** Pergunte quanto o usuário ganha por hora e o número de horas trabalhadas no mês. Calcule e exiba o salário bruto total.

---

## Grupo C: Cálculos e Formatação (21 a 30)

21. **IMC (Índice de Massa Corporal):** Peça o peso (kg) e a altura (metros) do usuário. Calcule e exiba o IMC usando a fórmula $\text{IMC} = \frac{\text{peso}}{\text{altura}^2}$. Formate o resultado com duas casas decimais.
22. **Cálculo de Gorjeta:** Solicite o valor total da conta de um restaurante e a porcentagem de gorjeta desejada (ex: 10, 12, 15). Exiba o valor da gorjeta e o valor total final da conta.
23. **Tempo de Viagem:** Peça a distância de uma viagem (em km) e a velocidade média esperada (em km/h). Calcule e exiba o tempo estimado de viagem em horas.
24. **Preço por Litro:** Pergunte o valor pago para abastecer e a quantidade de litros colocados. Exiba o preço cobrado por litro de combustível.
25. **Conversor de Dias para Horas/Minutos/Segundos:** Peça uma quantidade de dias e converta esse tempo para horas, minutos e segundos totais.
26. **Juros Simples:** Solicite o capital inicial ($C$), a taxa de juros mensal em porcentagem ($i$) e o tempo em meses ($t$). Calcule o montante final usando Juros Simples ($J = C \times i/100 \times t$ e $\text{Montante} = C + J$).
27. **Comissão de Imobiliária:** Um corretor recebe 6% de comissão sobre a venda de qualquer imóvel. Solicite o valor do imóvel vendido e exiba o valor da comissão dele.
28. **Desconto de INSS:** Peça o salário bruto de um funcionário. Calcule um desconto fixo de 11% de INSS e exiba o salário líquido.
29. **Calculadora de Azulejos:** Peça a altura e largura de uma parede (em metros), e a área de cobertura de um azulejo (em m²). Calcule quantos azulejos são necessários para cobrir a parede (considere arredondar para cima usando `Math.ceil`).
30. **Divisão de Conta:** Peça o valor total de uma conta e a quantidade de amigos. Exiba o valor que cada amigo deve pagar.

---

## Grupo D: Manipulação Básica de Tipos (31 a 40)

31. **ASCII de um Char:** Peça para o usuário digitar um único caractere e exiba o seu código numérico correspondente na tabela ASCII. (Dica: faça um cast de `char` para `int`).
32. **Inversão de Três Dígitos:** Peça um número inteiro de 3 dígitos (ex: 384) e exiba o número invertido (ex: 483). *Dica: Use divisões por 10 e operador de resto (`%`).*
33. **Divisão Inteira vs Decimal:** Peça dois números inteiros ao usuário. Exiba a divisão inteira deles e, logo em seguida, a divisão real (com casas decimais) fazendo um cast para `double`.
34. **Tabuada Simples:** Peça um número inteiro e exiba a multiplicação dele de 1 a 5 (sem usar loops, apenas fazendo 5 impressões consecutivas).
35. **Troca de Valores:** Peça dois números e guarde-os nas variáveis `A` e `B`. Faça com que `A` passe a ter o valor de `B`, e `B` passe a ter o valor de `A`. Exiba os valores trocados.
36. **Separador de Centavos:** Peça um número decimal que represente um valor em dinheiro (ex: 45.72). Exiba separadamente a parte inteira (reais) e a parte decimal (centavos).
37. **Consumo de Energia:** Peça o valor do salário mínimo atual e a quantidade de quilowatts consumida por uma residência. Sabendo que cada quilowatt custa 1/500 do salário mínimo, calcule: o valor de cada quilowatt e o valor total a ser pago pela residência.
38. **Volume de uma Esfera:** Solicite o raio de uma esfera e calcule seu volume pela fórmula: $V = \frac{4}{3} \times \pi \times \text{raio}^3$.
39. **Calculadora de Idade em Dias:** Pergunte a idade de uma pessoa em anos, meses e dias (ex: 20 anos, 3 meses e 15 dias). Exiba a idade total dessa pessoa expressa apenas em dias. (Considere o ano com 365 dias e o mês com 30 dias).
40. **Distância entre Dois Pontos:** Solicite as coordenadas $X$ e $Y$ de dois pontos em um plano cartesiano ($x_1, y_1$ e $x_2, y_2$). Calcule a distância entre eles usando a fórmula: $d = \sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}$. (Dica: use `Math.sqrt` e `Math.pow`).

---

## Grupo E: Operadores Lógicos e Desafios (41 a 50)

41. **Verificação de Maioridade:** Peça a idade do usuário e exiba `true` se ele for maior de idade (18 anos ou mais) e `false` caso contrário, sem usar comandos `if` (exiba apenas a comparação direta).
42. **Ano Bissexto (Lógica Básica):** Peça um ano ao usuário e exiba `true` se ele for bissexto e `false` caso contrário. *Regra: O ano deve ser divisível por 4 e não divisível por 100, ou ser divisível por 400.* (Escreva em uma única expressão lógica).
43. **Média Ponderada:** Peça 3 notas de um aluno e seus respectivos pesos. Calcule e exiba a média ponderada.
44. **Tempo em Segundos:** Peça uma quantidade de segundos totais e exiba no formato `horas : minutos : segundos`. (Ex: 3665 segundos = `1h 1min 5s`).
45. **Custo de Carro Novo:** O custo de um carro novo ao consumidor é a soma do custo de fábrica com a porcentagem do distribuidor (28%) e dos impostos (45%), ambos aplicados sobre o custo de fábrica. Peça o custo de fábrica e calcule o custo final.
46. **Comprimento da Circunferência:** Peça o raio de uma circunferência e exiba o comprimento dela: $C = 2 \times \pi \times \text{raio}$.
47. **Conversão de Velocidade:** Peça a velocidade em m/s (metros por segundo) e exiba a velocidade equivalente em km/h (quilômetros por hora). A conversão é feita multiplicando por 3.6.
48. **Reajuste Salarial:** Peça o salário mensal atual de um funcionário e o percentual de reajuste. Calcule e exiba o valor do novo salário.
49. **Cálculo de Desconto Simples:** Solicite o preço original de uma mercadoria e a porcentagem de desconto. Exiba o valor do desconto e o preço final a pagar.
50. **Resto da Divisão por 2:** Peça um número inteiro. Sem usar `if`, exiba `true` se o resto da divisão por 2 for igual a 0 (ou seja, se for par) e `false` caso seja ímpar.
