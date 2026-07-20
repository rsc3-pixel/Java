package modulo1;

import java.util.Scanner;

public class Exemplo1 {
    public static void main(String[] args) {
        // Criando o objeto leitor para entrada de dados
        Scanner leitor = new Scanner(System.in);

        System.out.println("=== Bem-vindo ao Exemplo do Módulo 1! ===");

        // 1. Variáveis e Tipos de Dados
        String produto = "Mouse Gamer";
        double precoUnitario = 150.00;
        boolean temEstoque = true;

        System.out.println("Produto em promoção: " + produto);
        System.out.println("Preço original: R$" + precoUnitario);
        System.out.println("Disponível em estoque? " + temEstoque);

        System.out.println("\n--- Simulador de Venda ---");

        // 2. Entrada de dados
        System.out.print("Quantos " + produto + "(s) você deseja comprar? ");
        int quantidade = leitor.nextInt();

        // 3. Cálculos e Operadores
        double total = precoUnitario * quantidade;

        // Se a quantidade for maior ou igual a 3, damos 10% de desconto
        double desconto = 0.0;
        if (quantidade >= 3) {
            desconto = total * 0.10;
            total = total - desconto;
            System.out.println("Parabéns! Você ganhou 10% de desconto!");
        }

        // 4. Exibição dos resultados (Saída)
        System.out.printf("Quantidade: %d\n", quantidade);
        System.out.printf("Valor do desconto: R$ %.2f\n", desconto);
        System.out.printf("Valor total a pagar: R$ %.2f\n", total);

        // Fechando o Scanner para evitar vazamento de memória (resource leak)
        leitor.close();
        System.out.println("\nObrigado por rodar o exemplo!");
    }
}
