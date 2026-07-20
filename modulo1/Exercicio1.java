package modulo1;

/*
 * DESAFIO DO MÓDULO 1: Calculadora de Salário de Vendedor
 * 
 * Objetivo:
 * Praticar a declaração de variáveis, leitura de dados com Scanner,
 * operações matemáticas simples e exibição de resultados.
 * 
 * Instruções:
 * Complete os locais marcados com "// TODO:" para que o programa funcione.
 * 
 * Como rodar:
 * Abra o terminal, navegue até a pasta 'curso-java' e digite:
 * 1. Para compilar: javac modulo1/Exercicio1.java
 * 2. Para rodar: java modulo1.Exercicio1
 */

import java.util.Scanner;

public class Exercicio1 {
    public static void main(String[] args) {
        // Criar o Scanner
        Scanner input = new Scanner(System.in);

        System.out.println("=== SISTEMA DE COMISSÃO DE VENDEDORES ===");

        // 1. Peça e leia o nome do vendedor (String)
        System.out.print("Digite o nome do vendedor: ");
        // TODO: Declarar a variável 'nome' e ler usando o Scanner (input.nextLine())
        String nome = ""; 

        // 2. Peça e leia o salário fixo do vendedor (double)
        System.out.print("Digite o salário fixo: R$ ");
        // TODO: Declarar a variável 'salarioFixo' e ler usando o Scanner (input.nextDouble())
        double salarioFixo = 0.0;

        // 3. Peça e leia o total de vendas efetuadas por ele no mês em dinheiro (double)
        System.out.print("Digite o total de vendas realizadas no mês: R$ ");
        // TODO: Declarar a variável 'vendasMes' e ler usando o Scanner (input.nextDouble())
        double vendasMes = 0.0;

        // 4. Peça e leia a taxa de comissão em porcentagem (double)
        System.out.print("Digite a comissão (ex: 5 para 5%): ");
        // TODO: Declarar a variável 'comissaoPorcentagem' e ler usando o Scanner (input.nextDouble())
        double comissaoPorcentagem = 0.0;

        // 5. CÁLCULOS:
        // TODO: Calcular a comissão ganha (vendas do mês multiplicadas pela porcentagem dividida por 100)
        double valorComissao = 0.0;

        // TODO: Calcular o salário final (salário fixo + valor da comissão)
        double salarioFinal = 0.0;

        // 6. EXIBIÇÃO:
        System.out.println("\n=== RESUMO DO MÊS ===");
        // TODO: Exibir o nome do vendedor
        // TODO: Exibir o salário fixo formatado (ex: R$ 2000,00)
        // TODO: Exibir a comissão ganha formatada (ex: R$ 500,00)
        // TODO: Exibir o salário final a receber formatado (ex: R$ 2500,00)

        // Fechar o scanner
        input.close();
    }
}
