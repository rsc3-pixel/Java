package modulo4;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * DESAFIO 1 DO MÓDULO 4: Calculadora à Prova de Erros
 *
 * Objetivo:
 * Praticar try/catch, múltiplos catch, finally, validação de entrada do usuário
 * e criação de exceção customizada.
 *
 * Cenário:
 * Uma calculadora de terminal que NUNCA pode quebrar, não importa o que o
 * usuário digite: letra onde espera número, divisão por zero, raiz de negativo.
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:".
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo4/Exercicio1.java
 * 2. Para rodar:    java modulo4.Exercicio1
 */

/*
 * TODO: 1. Crie a exceção customizada 'OperacaoInvalidaException' logo abaixo,
 *          estendendo RuntimeException (unchecked).
 *
 *          class OperacaoInvalidaException extends RuntimeException {
 *              public OperacaoInvalidaException(String mensagem) {
 *                  super(mensagem);
 *              }
 *          }
 *
 *          PENSE: por que unchecked e não checked?
 *          Porque um operador inválido indica que quem chamou usou a API errado,
 *          e não uma condição externa fora do controle do programa (como um
 *          arquivo que não existe). Essa é a regra prática para escolher entre os dois.
 */

public class Exercicio1 {

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("=== CALCULADORA À PROVA DE ERROS ===");

        while (continuar) {
            try {
                System.out.print("\nDigite o primeiro número: ");
                double a = leitor.nextDouble();

                System.out.print("Digite o operador (+, -, *, /, %, ^): ");
                String operador = leitor.next();

                System.out.print("Digite o segundo número: ");
                double b = leitor.nextDouble();

                double resultado = calcular(a, b, operador);
                System.out.printf("Resultado: %.4f%n", resultado);

            } catch (InputMismatchException e) {
                /*
                 * TODO: 2. Trate a entrada inválida (usuário digitou letra onde
                 *          esperava número).
                 *          a) Imprima "[ERRO] Digite apenas números."
                 *          b) IMPORTANTE: chame leitor.nextLine() para LIMPAR o buffer.
                 *
                 *          PENSE: por que limpar o buffer?
                 *          Quando nextDouble() falha, o texto inválido CONTINUA no
                 *          buffer do Scanner. Sem limpar, o próximo nextDouble() lê
                 *          o mesmo lixo de novo e o programa entra em loop infinito
                 *          de erros. É a pegadinha clássica de Scanner com exceção.
                 */

            } catch (ArithmeticException e) {
                // TODO: 3. Imprima "[ERRO] " concatenado com e.getMessage()

            // TODO: 4. Descomente o catch abaixo assim que criar a classe no TODO 1,
            //          e dentro dele imprima "[ERRO] " concatenado com e.getMessage()
            // } catch (OperacaoInvalidaException e) {
            //
            } finally {
                /*
                 * TODO: 5. Imprima "--- operação finalizada ---"
                 *          Observe rodando: esta linha aparece TANTO no sucesso
                 *          QUANTO em cada um dos erros acima. É essa garantia que
                 *          torna o finally o lugar certo para liberar recursos.
                 */
            }

            System.out.print("\nDeseja continuar? (s/n): ");
            String resposta = leitor.next();
            continuar = resposta.equalsIgnoreCase("s");
        }

        System.out.println("Calculadora encerrada.");
        leitor.close();
    }

    /*
     * TODO: 6. Complete o método calcular.
     *
     *          Use um switch sobre o operador e retorne o resultado de cada caso:
     *            "+" -> a + b
     *            "-" -> a - b
     *            "*" -> a * b
     *            "^" -> Math.pow(a, b)
     *
     *            "/" -> ANTES de dividir, verifique se b == 0.
     *                   Se for, lance: throw new ArithmeticException("Divisão por zero.");
     *                   PENSE: com double, 10.0/0 NÃO lança exceção sozinho, ele
     *                   devolve Infinity. Só a divisão INTEIRA lança ArithmeticException.
     *                   Por isso a validação manual aqui é obrigatória.
     *
     *            "%" -> mesma verificação de zero antes de fazer a % b
     *
     *            default -> lance a sua exceção customizada:
     *                       throw new OperacaoInvalidaException("Operador inválido: " + operador);
     */
    private static double calcular(double a, double b, String operador) {
        return 0; // TODO: substituir pela implementação real
    }

    /*
     * TODO: 7. (DESAFIO EXTRA) Crie o método abaixo e chame-o no main:
     *
     *          private static double calcularRaiz(double numero)
     *
     *          Se 'numero' for negativo, lance:
     *              throw new OperacaoInvalidaException("Raiz de número negativo.");
     *          Senão, retorne Math.sqrt(numero).
     *
     *          PENSE: Math.sqrt(-4) não lança exceção, ele retorna NaN
     *          (Not a Number). NaN se propaga silenciosamente por todos os
     *          cálculos seguintes e contamina o resultado final sem nenhum aviso.
     *          Validar antes é o que transforma um bug invisível em erro visível.
     */
}
