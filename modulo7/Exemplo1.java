package modulo7;

/*
 * EXEMPLO 1 DO MÓDULO 7: "O que será impresso?"
 *
 * As questões de "prever a saída" no estilo da prova do período passado,
 * rodando de verdade. A prova deste período vai ser outra, mas o raciocínio
 * cobrado é o mesmo.
 * Antes de executar, PEGUE UM PAPEL e escreva o que você acha que cada bloco
 * imprime. Depois compare. Errar aqui é barato; errar amanhã custa ponto.
 *
 * Como rodar (a partir da raiz do repositório):
 * 1. Para compilar: javac -encoding UTF-8 modulo7/Exemplo1.java
 * 2. Para rodar:    java modulo7.Exemplo1
 */

// ============================================================
// BLOCO 1: a questão 04 da prova do período passado, como caiu
// ============================================================
class Alfa {
    // private: fica FORA do polimorfismo. A chamada dentro de execute()
    // é amarrada a ESTE método na compilação da classe Alfa.
    private void showA() { System.out.print("Classe Alfa -> showA "); }

    // protected: pode ser sobrescrito. Quem decide qual versão roda é o
    // objeto real, na hora da execução.
    protected void showB() { System.out.print("Classe Alfa -> showB "); }

    protected void execute() {
        showA();
        showB();
    }
}

class Beta extends Alfa {
    // NÃO é sobrescrita. É um método novo que só tem o mesmo nome.
    // Repare que não dá para pôr @Override aqui: o compilador recusaria,
    // e essa recusa é a prova de que não existe sobrescrita. A IDE ainda avisa
    // "never used locally": ninguém chama este método, nem o execute() de Alfa.
    private void showA() { System.out.print("Classe Beta -> showA "); }

    @Override
    protected void showB() { System.out.print("Classe Beta -> showB "); }
}

// ============================================================
// BLOCO 2: a MESMA questão, trocando só o private por protected
// ============================================================
class AlfaAberta {
    protected void showA() { System.out.print("Classe Alfa -> showA "); }
    protected void showB() { System.out.print("Classe Alfa -> showB "); }

    protected void execute() {
        showA();
        showB();
    }
}

class BetaAberta extends AlfaAberta {
    @Override   // agora o @Override é aceito: a sobrescrita existe
    protected void showA() { System.out.print("Classe Beta -> showA "); }

    @Override
    protected void showB() { System.out.print("Classe Beta -> showB "); }
}

// ============================================================
// BLOCO 3: a hierarquia da questão 02 (conversões)
// ============================================================
class Dispositivo {
    public String descrever() { return "um dispositivo"; }
}

class Smartphone extends Dispositivo {
    @Override
    public String descrever() { return "um smartphone"; }

    // Existe SÓ em Smartphone: uma variável do tipo Dispositivo não enxerga.
    public String fazerLigacao() { return "ligando..."; }
}

class SmartphoneAndroid extends Smartphone {
    @Override
    public String descrever() { return "um smartphone Android"; }
}

class Computador extends Dispositivo {
    @Override
    public String descrever() { return "um computador"; }
}

class Notebook extends Computador {
    @Override
    public String descrever() { return "um notebook"; }
}

// ============================================================
// BLOCO 4: o static compartilhado (slide 35)
// ============================================================
class ExemploStatic {
    private double valor;          // cada objeto tem o seu
    private static String rotulo;  // UM só para a classe inteira

    public void setValor(double valor) { this.valor = valor; }
    public double getValor() { return valor; }

    public static void setRotulo(String rot) { rotulo = rot; }
    public static String getRotulo() { return rotulo; }
}

public class Exemplo1 {
    public static void main(String[] args) {

        System.out.println("=== BLOCO 1: showA private (questão do período passado) ===");
        Beta obj = new Beta();
        obj.execute();
        System.out.println();
        System.out.println("-> showA ficou com Alfa (private não se sobrescreve);");
        System.out.println("   showB foi para Beta (protected se sobrescreve).\n");

        System.out.println("=== BLOCO 2: showA protected ===");
        BetaAberta obj2 = new BetaAberta();
        obj2.execute();
        System.out.println();
        System.out.println("-> agora as duas chamadas vão para Beta.\n");

        System.out.println("=== BLOCO 3: conversões ===");
        Object o1 = new Computador();              // compila: tudo é Object
        Dispositivo d3 = new SmartphoneAndroid();  // compila: neta na caixa da avó
        Smartphone s2 = new SmartphoneAndroid();   // compila: filha na caixa da mãe
        Computador c1 = new Notebook();            // compila

        // Smartphone s1 = new Object();           // NÃO compila: Object não é Smartphone
        // Computador c2 = new Smartphone();       // NÃO compila: são "primos"
        // d3.fazerLigacao();                      // NÃO compila: a variável é Dispositivo

        // O tipo do OBJETO decide qual descrever() roda, não o da variável:
        System.out.println("o1 é " + o1);          // toString de Object: Computador@hash
        System.out.println("d3 é " + d3.descrever());
        System.out.println("s2 é " + s2.descrever());
        System.out.println("c1 é " + c1.descrever());

        // Cast explícito com instanceof: a forma segura de "descer" na hierarquia.
        if (d3 instanceof Smartphone) {
            Smartphone s = (Smartphone) d3;
            System.out.println("d3 depois do cast: " + s.fazerLigacao());
        }

        // Cast que compila mas explode: o compilador aceita a promessa,
        // a JVM confere o objeto real e recusa.
        try {
            Dispositivo qualquer = new Computador();
            Smartphone errado = (Smartphone) qualquer;
            System.out.println(errado.descrever()); // nunca chega aqui
        } catch (ClassCastException e) {
            System.out.println("Cast inválido: ClassCastException em tempo de EXECUÇÃO");
        }

        System.out.println("\n=== BLOCO 4: static compartilhado ===");
        ExemploStatic ex1 = new ExemploStatic();
        ExemploStatic ex2 = new ExemploStatic();
        ex1.setValor(100.00);
        ex2.setValor(50.00);
        // O slide chama ex1.setRotulo(...) e ex2.setRotulo(...). Compila, mas engana:
        // parece que cada objeto tem o seu rótulo. Pelo nome da classe fica honesto.
        ExemploStatic.setRotulo("Abacate");
        ExemploStatic.setRotulo("Laranja");
        System.out.println("ex1: " + ex1.getValor() + " / " + ExemploStatic.getRotulo());
        System.out.println("ex2: " + ex2.getValor() + " / " + ExemploStatic.getRotulo());
        System.out.println("-> o valor é de cada objeto; o rótulo é um só, e ficou Laranja.");
    }
}
