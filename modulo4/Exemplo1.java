package modulo4;

import java.util.ArrayList;
import java.util.List;

/*
 * EXEMPLO 1 DO MÓDULO 4: Tratamento de Exceções
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo4/Exemplo1.java
 * 2. Para rodar:    java modulo4.Exemplo1
 */

// ============================================================
// EXCEÇÃO CUSTOMIZADA UNCHECKED (estende RuntimeException)
// Usada para violação de REGRA DE NEGÓCIO.
// Quem chama não é obrigado a tratar.
// ============================================================
class SaldoInsuficienteException extends RuntimeException {

    private final double saldoDisponivel;
    private final double valorSolicitado;

    public SaldoInsuficienteException(double saldoDisponivel, double valorSolicitado) {
        // super() repassa a mensagem para a classe pai (Throwable)
        super(String.format("Saque de R$ %.2f recusado. Disponível: R$ %.2f",
                valorSolicitado, saldoDisponivel));
        this.saldoDisponivel = saldoDisponivel;
        this.valorSolicitado = valorSolicitado;
    }

    // A exceção pode carregar DADOS EXTRAS além da mensagem
    public double getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public double getValorFaltante() {
        return valorSolicitado - saldoDisponivel;
    }
}

// ============================================================
// EXCEÇÃO CUSTOMIZADA CHECKED (estende Exception)
// O compilador OBRIGA quem chama a tratar ou propagar.
// ============================================================
class ContaNaoEncontradaException extends Exception {
    public ContaNaoEncontradaException(String numero) {
        super("Conta não encontrada: " + numero);
    }
}

class Conta {
    private final String numero;
    private double saldo;

    public Conta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    // Lança uma exceção UNCHECKED: não precisa declarar throws
    public void sacar(double valor) {
        if (valor <= 0) {
            // IllegalArgumentException é uma unchecked padrão do Java,
            // perfeita para argumento inválido.
            throw new IllegalArgumentException("Valor deve ser positivo: " + valor);
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException(saldo, valor);
        }
        saldo -= valor;
    }
}

public class Exemplo1 {

    private static final List<Conta> CONTAS = new ArrayList<>();

    public static void main(String[] args) {

        CONTAS.add(new Conta("001", 1000.00));
        CONTAS.add(new Conta("002", 50.00));

        // ============================================================
        // PARTE 1: try / catch básico e o fluxo de execução
        // ============================================================
        System.out.println("=== PARTE 1: Fluxo do try/catch ===\n");

        try {
            System.out.println("A - antes do erro");
            int resultado = 10 / 0;              // ArithmeticException aqui
            System.out.println("B - " + resultado); // NUNCA executa
        } catch (ArithmeticException e) {
            System.out.println("C - catch: " + e.getMessage());
        }
        System.out.println("D - após o bloco\n");
        System.out.println("Saída A, C, D: o resto do try foi ABANDONADO no erro.");

        // ============================================================
        // PARTE 2: múltiplos catch, do específico ao genérico
        // ============================================================
        System.out.println("\n=== PARTE 2: Múltiplos catch ===\n");

        String[] entradas = {"10", "abc", "0", null};

        for (String entrada : entradas) {
            try {
                int numero = Integer.parseInt(entrada); // pode lançar NumberFormatException
                int divisao = 100 / numero;             // pode lançar ArithmeticException
                System.out.printf("Entrada '%s' -> 100/%d = %d%n", entrada, numero, divisao);

            } catch (NumberFormatException e) {
                // Cobre tanto "abc" quanto null (parseInt(null) também lança esta)
                System.out.printf("Entrada '%s' -> não é um número válido.%n", entrada);

            } catch (ArithmeticException e) {
                System.out.printf("Entrada '%s' -> divisão por zero.%n", entrada);

            } catch (Exception e) {
                // O genérico SEMPRE por último. Se viesse antes, os outros catch
                // seriam código inalcançável e o programa não compilaria.
                System.out.printf("Entrada '%s' -> erro inesperado: %s%n",
                        entrada, e.getClass().getSimpleName());
            }
        }

        // ============================================================
        // PARTE 3: multi-catch com | (Java 7+)
        // ============================================================
        System.out.println("\n=== PARTE 3: multi-catch ===\n");

        try {
            int[] v = new int[3];
            v[10] = Integer.parseInt("xyz");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            // Mesmo tratamento para exceções diferentes: evita duplicar código
            System.out.println("Erro capturado: " + e.getClass().getSimpleName());
            System.out.println("Mensagem: " + e.getMessage());
        }

        // ============================================================
        // PARTE 4: finally sempre executa
        // ============================================================
        System.out.println("\n=== PARTE 4: finally ===\n");

        System.out.println("Chamando método com return dentro do try...");
        System.out.println("Retorno recebido: " + metodoComFinally());

        // ============================================================
        // PARTE 5: exceções customizadas UNCHECKED
        // ============================================================
        System.out.println("\n=== PARTE 5: Exceção customizada (unchecked) ===\n");

        Conta conta = CONTAS.get(1); // saldo 50,00

        double[] tentativas = {30.00, 500.00, -10.00};
        for (double valor : tentativas) {
            try {
                conta.sacar(valor);
                System.out.printf("[OK] Saque de R$ %.2f | novo saldo: R$ %.2f%n",
                        valor, conta.getSaldo());

            } catch (SaldoInsuficienteException e) {
                // A exceção customizada carrega DADOS além da mensagem
                System.out.println("[ERRO] " + e.getMessage());
                System.out.printf("       Faltam R$ %.2f para completar.%n", e.getValorFaltante());

            } catch (IllegalArgumentException e) {
                System.out.println("[ERRO] " + e.getMessage());
            }
        }

        // ============================================================
        // PARTE 6: exceções CHECKED e a obrigação de tratar
        // ============================================================
        System.out.println("\n=== PARTE 6: Exceção checked ===\n");

        String[] numerosBusca = {"001", "999"};
        for (String num : numerosBusca) {
            try {
                Conta encontrada = buscarConta(num);
                System.out.printf("Conta %s encontrada | saldo: R$ %.2f%n",
                        encontrada.getNumero(), encontrada.getSaldo());

            } catch (ContaNaoEncontradaException e) {
                // O compilador OBRIGOU este catch, porque buscarConta declara throws
                System.out.println("[ERRO] " + e.getMessage());
            }
        }

        // ============================================================
        // PARTE 7: o antipadrão que você não deve repetir
        // ============================================================
        System.out.println("\n=== PARTE 7: O antipadrão ===\n");

        try {
            System.out.println(10 / 0);
        } catch (Exception e) {
            // ENGOLIR A EXCEÇÃO: o erro aconteceu e ninguém ficou sabendo.
            // Isso transforma um erro visível num bug invisível.
        }
        System.out.println("O bloco acima escondeu um erro real. Nunca faça isso:");
        System.out.println("um catch vazio é sempre pior que o programa quebrando.");
    }

    // O finally executa mesmo com return dentro do try
    private static String metodoComFinally() {
        try {
            System.out.println("  1. dentro do try");
            return "valor do try";
        } finally {
            System.out.println("  2. finally executou ANTES do retorno chegar ao main");
        }
    }

    // 'throws' na assinatura AVISA que este método pode falhar e não trata internamente.
    // Como ContaNaoEncontradaException é checked, quem chamar é OBRIGADO a tratar.
    private static Conta buscarConta(String numero) throws ContaNaoEncontradaException {
        for (Conta c : CONTAS) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        // 'throw' LANÇA a exceção de fato
        throw new ContaNaoEncontradaException(numero);
    }
}
