package modulo2;

/*
 * EXEMPLO 1 DO MÓDULO 2: Classes, Objetos, Construtores e Encapsulamento
 *
 * Como rodar:
 * 1. Para compilar: javac modulo2/Exemplo1.java
 * 2. Para rodar:    java modulo2.Exemplo1
 */

// ============================================================
// CLASSE DE APOIO: a "planta" de uma conta bancária
// Repare que ela fica FORA da classe principal, no mesmo arquivo.
// Só a classe pública pode ter o nome do arquivo.
// ============================================================
class ContaBancaria {

    // Atributo static: existe UMA vez para a classe inteira,
    // compartilhado por todos os objetos criados.
    private static int totalDeContas = 0;

    // Atributos de instância: cada objeto tem a sua própria cópia.
    private final int numero;
    private String titular;
    private double saldo;

    // CONSTRUTOR: roda automaticamente no 'new' e garante estado válido.
    public ContaBancaria(String titular, double depositoInicial) {
        totalDeContas++;
        this.numero = totalDeContas;
        this.titular = titular;
        // Uma conta nunca nasce com saldo negativo.
        this.saldo = depositoInicial > 0 ? depositoInicial : 0.0;
    }

    // CONSTRUTOR SOBRECARREGADO: quando não há depósito inicial.
    public ContaBancaria(String titular) {
        this(titular, 0.0); // reaproveita o construtor de cima
    }

    // GETTERS: leitura controlada dos dados privados.
    public int getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    // SETTER COM VALIDAÇÃO: aqui está o ganho real do encapsulamento.
    public void setTitular(String novoTitular) {
        if (novoTitular == null || novoTitular.isBlank()) {
            System.out.println("[ERRO] Titular inválido. Alteração recusada.");
            return;
        }
        this.titular = novoTitular;
    }

    // COMPORTAMENTO DE NEGÓCIO: não existe setSaldo().
    // O saldo só muda através das regras reais do banco.
    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("[ERRO] Depósito precisa ser positivo.");
            return;
        }
        saldo += valor;
        System.out.printf("[OK] Depósito de R$ %.2f na conta %d.%n", valor, numero);
    }

    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("[ERRO] Saque precisa ser positivo.");
            return false;
        }
        if (valor > saldo) {
            System.out.printf("[ERRO] Saldo insuficiente (disponível: R$ %.2f).%n", saldo);
            return false;
        }
        saldo -= valor;
        System.out.printf("[OK] Saque de R$ %.2f na conta %d.%n", valor, numero);
        return true;
    }

    // Método static: pertence à CLASSE, chamado sem precisar de objeto.
    public static int getTotalDeContas() {
        return totalDeContas;
    }

    // toString(): o Java chama sozinho dentro de println e concatenações.
    @Override
    public String toString() {
        return String.format("Conta #%d | %s | Saldo: R$ %.2f", numero, titular, saldo);
    }
}

// ============================================================
// CLASSE PRINCIPAL
// ============================================================
public class Exemplo1 {
    public static void main(String[] args) {
        System.out.println("=== BANCO DIGITAL - MÓDULO 2 ===\n");

        // 1. Instanciando objetos com 'new'
        ContaBancaria contaRenato = new ContaBancaria("Renato Chong", 1000.00);
        ContaBancaria contaAna = new ContaBancaria("Ana Souza"); // usa o construtor sobrecarregado

        System.out.println(contaRenato); // toString() é chamado automaticamente
        System.out.println(contaAna);

        // 2. Cada objeto tem estado independente
        System.out.println("\n--- Movimentações ---");
        contaRenato.depositar(500.00);
        contaAna.depositar(200.00);
        contaRenato.sacar(300.00);

        // 3. As validações do encapsulamento em ação
        System.out.println("\n--- Tentativas inválidas ---");
        contaAna.sacar(9999.00);   // saldo insuficiente
        contaRenato.depositar(-50); // valor negativo
        contaAna.setTitular("");    // nome em branco

        // Sem encapsulamento, a linha abaixo seria possível e destruiria a regra:
        // contaAna.saldo = 1000000; // não compila: 'saldo' é private

        // 4. Estado final
        System.out.println("\n--- Situação final ---");
        System.out.println(contaRenato);
        System.out.println(contaAna);

        // 5. Membro static: acessado pela CLASSE, não pelo objeto
        System.out.println("\nTotal de contas abertas: " + ContaBancaria.getTotalDeContas());

        // 6. Referências: variável de objeto guarda o ENDEREÇO, não o objeto
        System.out.println("\n--- Entendendo referências ---");
        ContaBancaria apelido = contaRenato; // aponta para o MESMO objeto
        apelido.depositar(100.00);
        System.out.println("Saldo via contaRenato: R$ " + contaRenato.getSaldo());
        System.out.println("São o mesmo objeto? " + (apelido == contaRenato));

        ContaBancaria clone = new ContaBancaria("Renato Chong", contaRenato.getSaldo());
        System.out.println("Conteúdo parecido, mas mesmo objeto? " + (clone == contaRenato));
    }
}
