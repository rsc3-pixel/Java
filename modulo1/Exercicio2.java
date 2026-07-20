package modulo1;

/*
 * DESAFIO DO MÓDULO 1: Sistema de Gestão de Notas de Alunos
 * 
 * Objetivo:
 * Praticar a criação e uso de métodos estáticos com e sem retorno,
 * estruturas de condição (if/else), laços de repetição (while) e E/S (Scanner).
 * 
 * Instruções:
 * Complete os locais marcados com "// TODO:" para que o programa funcione conforme esperado.
 * 
 * Requisitos:
 * 1. O método calcularMedia deve retornar a média aritmética simples das 3 notas.
 * 2. O método obterStatus deve retornar:
 *    - "APROVADO(A)" para média maior ou igual a 7.0.
 *    - "EM RECUPERAÇÃO" para média maior ou igual a 5.0 e menor que 7.0.
 *    - "REPROVADO(A)" para média menor que 5.0.
 * 3. O laço principal no main deve permitir cadastrar vários alunos até que o usuário decida sair digitando "N" ou "n".
 */

import java.util.Scanner;

public class Exercicio2 {

    // TODO: Criar o método 'calcularMedia' que recebe três parâmetros double e retorna double
    public static double calcularMedia(double nota1, double nota2, double nota3) {
        // Escreva sua lógica aqui
        return 0.0; // altere o retorno
    }

    // TODO: Criar o método 'obterStatus' que recebe a média (double) e retorna uma String
    public static String obterStatus(double media) {
        // Escreva sua lógica de if/else aqui
        return ""; // altere o retorno
    }

    // TODO: Criar o método 'exibirBoletim' (void) que imprime o nome, média e status formatados
    public static void exibirBoletim(String nome, double media, String status) {
        System.out.println("\n--- BOLETIM ESCOLAR ---");
        // Exiba os dados de forma organizada usando System.out.printf ou System.out.println
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String continuar = "S";

        System.out.println("=== SISTEMA DE GESTÃO DE NOTAS ===");

        // TODO: Criar um loop 'while' ou 'do-while' que roda enquanto a variável 'continuar' for igual a "S" ou "s"
        while (continuar.equalsIgnoreCase("S")) {
            
            System.out.print("\nDigite o nome do aluno: ");
            String nome = leitor.nextLine();

            System.out.print("Digite a Nota 1: ");
            double n1 = leitor.nextDouble();

            System.out.print("Digite a Nota 2: ");
            double n2 = leitor.nextDouble();

            System.out.print("Digite a Nota 3: ");
            double n3 = leitor.nextDouble();
            leitor.nextLine(); // Limpa o buffer do teclado

            // TODO: Chamar o método 'calcularMedia' passando as notas lidas
            double media = 0.0;

            // TODO: Chamar o método 'obterStatus' passando a média calculada
            String status = "";

            // TODO: Chamar o método 'exibirBoletim' para imprimir os resultados
            

            System.out.print("\nDeseja cadastrar outro aluno? (S/N): ");
            continuar = leitor.nextLine();
        }

        System.out.println("\nSistema encerrado. Bons estudos!");
        leitor.close();
    }
}
