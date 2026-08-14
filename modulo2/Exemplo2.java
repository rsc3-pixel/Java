package modulo2;

/*
 * EXEMPLO 2 DO MÓDULO 2: Herança, Polimorfismo, Classe Abstrata e Interface
 *
 * Como rodar:
 * 1. Para compilar: javac modulo2/Exemplo2.java
 * 2. Para rodar:    java modulo2.Exemplo2
 */

// ============================================================
// INTERFACE: um contrato de CAPACIDADE ("é capaz de")
// Quem implementa é obrigado a saber se autenticar.
// ============================================================
interface Autenticavel {
    boolean autenticar(String senha);
}

// ============================================================
// CLASSE ABSTRATA: um molde incompleto ("é um")
// Não pode ser instanciada: não existe "funcionário genérico" na folha.
// ============================================================
abstract class Funcionario {

    // protected: as classes filhas acessam, o mundo externo não.
    protected String nome;
    protected double salarioBase;

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    // MÉTODO ABSTRATO: sem corpo. Toda filha É OBRIGADA a implementar.
    public abstract double calcularSalario();

    // MÉTODO CONCRETO: herdado por todas as filhas sem alteração.
    // Repare que ele chama calcularSalario() sem saber qual versão vai rodar.
    public void exibirHolerite() {
        System.out.printf("%-10s | %-12s | R$ %10.2f%n",
                nome, getCargo(), calcularSalario());
    }

    // Método que as filhas vão sobrescrever para se identificar.
    public String getCargo() {
        return "Funcionário";
    }

    public String getNome() {
        return nome;
    }
}

// ============================================================
// FILHA 1: herda de Funcionario e implementa uma interface
// ============================================================
class Gerente extends Funcionario implements Autenticavel {

    private double bonus;
    private String senha;

    public Gerente(String nome, double salarioBase, double bonus, String senha) {
        super(nome, salarioBase); // OBRIGATÓRIO e sempre na primeira linha
        this.bonus = bonus;
        this.senha = senha;
    }

    @Override
    public double calcularSalario() {
        return salarioBase + bonus;
    }

    @Override
    public String getCargo() {
        return "Gerente";
    }

    @Override
    public boolean autenticar(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }
}

// ============================================================
// FILHA 2: mesma hierarquia, cálculo totalmente diferente
// ============================================================
class Vendedor extends Funcionario {

    private double totalVendido;
    private static final double TAXA_COMISSAO = 0.05; // constante da classe

    public Vendedor(String nome, double salarioBase, double totalVendido) {
        super(nome, salarioBase);
        this.totalVendido = totalVendido;
    }

    @Override
    public double calcularSalario() {
        return salarioBase + (totalVendido * TAXA_COMISSAO);
    }

    @Override
    public String getCargo() {
        return "Vendedor";
    }
}

// ============================================================
// FILHA 3: reaproveita o cálculo do pai com super.metodo()
// ============================================================
class Estagiario extends Funcionario {

    public Estagiario(String nome, double bolsa) {
        super(nome, bolsa);
    }

    @Override
    public double calcularSalario() {
        return salarioBase; // sem bônus, sem comissão
    }

    @Override
    public String getCargo() {
        return "Estagiário";
    }
}

// ============================================================
// CLASSE PRINCIPAL
// ============================================================
public class Exemplo2 {
    public static void main(String[] args) {
        System.out.println("=== FOLHA DE PAGAMENTO - MÓDULO 2 ===\n");

        // POLIMORFISMO: referência do tipo PAI apontando para objetos FILHOS.
        // Isso só é possível porque todos "são um" Funcionario.
        Funcionario[] equipe = {
                new Gerente("Renato", 5000.00, 2000.00, "admin123"),
                new Vendedor("Ana", 2000.00, 30000.00),
                new Estagiario("Carlos", 1200.00),
                new Vendedor("Bruna", 2000.00, 12000.00)
        };

        System.out.printf("%-10s | %-12s | %12s%n", "NOME", "CARGO", "SALÁRIO");
        System.out.println("-".repeat(40));

        double folhaTotal = 0;
        for (Funcionario f : equipe) {
            // A MESMA chamada executa código diferente conforme o objeto real.
            // Nenhum 'if' verificando o tipo: isso é o poder do polimorfismo.
            f.exibirHolerite();
            folhaTotal += f.calcularSalario();
        }

        System.out.println("-".repeat(40));
        System.out.printf("TOTAL DA FOLHA: R$ %.2f%n", folhaTotal);

        // A classe abstrata não pode ser instanciada:
        // Funcionario f = new Funcionario("Teste", 1000); // ERRO DE COMPILAÇÃO

        // ============================================================
        // instanceof: verificando o tipo real por trás da referência
        // ============================================================
        System.out.println("\n--- Autenticação (apenas quem implementa Autenticavel) ---");
        for (Funcionario f : equipe) {
            if (f instanceof Autenticavel) {
                Autenticavel usuario = (Autenticavel) f; // cast para o contrato
                boolean ok = usuario.autenticar("admin123");
                System.out.println(f.getNome() + " tem login. Senha correta? " + ok);
            } else {
                System.out.println(f.getNome() + " não possui acesso ao sistema.");
            }
        }

        // ============================================================
        // Sobrescrita x Sobrecarga
        // ============================================================
        System.out.println("\n--- Sobrecarga (mesma classe, assinaturas diferentes) ---");
        System.out.println("Reajuste em R$:  " + reajustar(3000.00, 500.00));
        System.out.println("Reajuste em %:   " + reajustar(3000.00, 10));
    }

    // SOBRECARGA: mesmo nome, parâmetros diferentes.
    // O compilador escolhe qual chamar pelo tipo do argumento (em tempo de compilação).
    public static double reajustar(double salario, double valorFixo) {
        return salario + valorFixo;
    }

    public static double reajustar(double salario, int percentual) {
        return salario + (salario * percentual / 100.0);
    }
}
