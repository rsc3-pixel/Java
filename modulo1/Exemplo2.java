package modulo1;

import java.util.Scanner;

public class Exemplo2 {

    // 1. Definição de métodos estáticos com diferentes tipos de retorno
    public static double calcularDesconto(double valorTotal, int quantidade) {
        if (quantidade >= 10) {
            return valorTotal * 0.15; // 15% de desconto
        } else if (quantidade >= 5) {
            return valorTotal * 0.10; // 10% de desconto
        }
        return 0.0;
    }

    public static void exibirCabecalho() {
        System.out.println("========================================");
        System.out.println("      DEMONSTRAÇÃO DE CONTROLE E MÉTODOS ");
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        exibirCabecalho();

        // 2. Uso de laços e condições simples
        String[] produtos = {"Teclado Mecânico", "Fone Bluetooth", "Carregador Rápido"};
        double[] precos = {250.0, 180.0, 80.0};

        System.out.println("Produtos disponíveis hoje:");
        // Uso de for tradicional
        for (int i = 0; i < produtos.length; i++) {
            System.out.printf("%d. %s - R$ %.2f\n", (i + 1), produtos[i], precos[i]);
        }

        System.out.print("\nEscolha o número do produto que deseja comprar: ");
        int opcao = leitor.nextInt();

        // Validando a escolha com condições
        if (opcao < 1 || opcao > produtos.length) {
            System.out.println("Opção inválida! Compra cancelada.");
            leitor.close();
            return;
        }

        // Obtendo dados correspondentes
        String produtoEscolhido = produtos[opcao - 1];
        double precoEscolhido = precos[opcao - 1];

        System.out.printf("Você escolheu: %s (R$ %.2f)\n", produtoEscolhido, precoEscolhido);
        System.out.print("Quantas unidades deseja comprar? ");
        int quantidade = leitor.nextInt();

        if (quantidade <= 0) {
            System.out.println("Quantidade deve ser maior que zero!");
            leitor.close();
            return;
        }

        double valorSemDesconto = precoEscolhido * quantidade;
        // Chamada de método para calcular desconto
        double desconto = calcularDesconto(valorSemDesconto, quantidade);
        double valorFinal = valorSemDesconto - desconto;

        System.out.println("\n--- Resumo do Pedido ---");
        System.out.printf("Subtotal: R$ %.2f\n", valorSemDesconto);
        System.out.printf("Desconto aplicado: R$ %.2f\n", desconto);
        System.out.printf("Total a pagar: R$ %.2f\n", valorFinal);

        // Demonstração de while/do-while e break/continue
        System.out.println("\n--- Simulação de Contador com continue e break ---");
        int contador = 1;
        while (true) {
            if (contador == 3) {
                System.out.println("Pulando o número 3 usando 'continue'.");
                contador++;
                continue;
            }
            if (contador > 5) {
                System.out.println("Saindo do loop com 'break'.");
                break;
            }
            System.out.println("Contador: " + contador);
            contador++;
        }

        leitor.close();
        System.out.println("\nExemplo encerrado com sucesso!");
    }
}
