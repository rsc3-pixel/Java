package modulo2;

/*
 * DESAFIO 2 DO MÓDULO 2: Sistema de Veículos de uma Locadora
 *
 * Objetivo:
 * Praticar classe abstrata, herança com super(), sobrescrita com @Override,
 * interface e polimorfismo percorrendo um array do tipo do pai.
 *
 * Cenário:
 * Uma locadora aluga Carros, Motos e Caminhões. Cada tipo cobra a diária de um jeito:
 *   - Carro:    diariaBase + R$ 20,00 por assento acima de 2
 *   - Moto:     diariaBase (sem acréscimo), mas com 10% de desconto
 *   - Caminhao: diariaBase + R$ 50,00 por tonelada de capacidade
 *
 * Além disso, Carro e Caminhao possuem rastreador (interface Rastreavel); Moto não.
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:".
 *
 * Como rodar:
 * 1. Para compilar: javac modulo2/Exercicio2.java
 * 2. Para rodar:    java modulo2.Exercicio2
 */

// ============================================================
// PARTE 1: A INTERFACE (contrato de capacidade)
// ============================================================
interface Rastreavel {
    // TODO: 1. Declare o método 'String obterLocalizacao();'
    //          Em interface não se escreve corpo nem a palavra 'public' (é implícita).
}

// ============================================================
// PARTE 2: A CLASSE ABSTRATA (o molde comum)
// ============================================================
abstract class Veiculo {

    // TODO: 2. Declare os atributos PROTECTED:
    //          - placa (String)
    //          - modelo (String)
    //          - diariaBase (double)
    //          PENSE: por que protected e não private? Porque as filhas precisam
    //          acessar diariaBase para fazer seus cálculos.

    /*
     * TODO: 3. Crie o construtor recebendo (String placa, String modelo, double diariaBase)
     *          e atribua os três atributos usando this.
     */

    /*
     * TODO: 4. Declare o MÉTODO ABSTRATO:
     *          public abstract double calcularDiaria();
     *          Ele não tem corpo. Toda classe filha é obrigada a implementá-lo.
     */

    /*
     * TODO: 5. Declare o método abstrato:
     *          public abstract String getTipo();
     *          Cada filha devolve seu nome ("Carro", "Moto", "Caminhão").
     */

    /*
     * TODO: 6. Crie o método CONCRETO (com corpo, herdado por todas):
     *          public double calcularAluguel(int dias)
     *          Retorne calcularDiaria() * dias.
     *
     *          Repare no detalhe importante: este método chama calcularDiaria() sem
     *          saber qual implementação vai rodar. Quem decide é o objeto real,
     *          em tempo de execução. Isso é polimorfismo funcionando por dentro.
     */

    // Getters prontos: descomente assim que declarar os atributos no TODO 2.
    // public String getPlaca() {
    //     return placa;
    // }
    //
    // public String getModelo() {
    //     return modelo;
    // }

    /*
     * TODO: 7. Sobrescreva toString() com @Override retornando algo como:
     *          "Carro | ABC-1234 | Civic | Diária: R$ 170,00"
     *          Dica: String.format("%-8s | %s | %s | Diária: R$ %.2f", getTipo(), placa, modelo, calcularDiaria())
     */
}

// ============================================================
// PARTE 3: AS CLASSES FILHAS
// ============================================================

/*
 * TODO: 8. Crie a classe 'Carro' que ESTENDE Veiculo e IMPLEMENTA Rastreavel.
 *          Sintaxe: class Carro extends Veiculo implements Rastreavel { ... }
 *
 *          a) Atributo private int assentos.
 *          b) Construtor (String placa, String modelo, double diariaBase, int assentos):
 *             chame super(placa, modelo, diariaBase) na PRIMEIRA linha, depois this.assentos.
 *          c) @Override calcularDiaria(): retorne diariaBase + ((assentos - 2) * 20.0)
 *             Cuidado: se assentos for menor que 2, o cálculo fica negativo. Trate isso.
 *          d) @Override getTipo(): retorne "Carro".
 *          e) @Override obterLocalizacao(): retorne "GPS ativo - Recife/PE".
 */

/*
 * TODO: 9. Crie a classe 'Moto' que ESTENDE Veiculo (NÃO implementa Rastreavel).
 *
 *          a) Atributo private int cilindradas.
 *          b) Construtor (String placa, String modelo, double diariaBase, int cilindradas).
 *          c) @Override calcularDiaria(): retorne diariaBase * 0.90 (10% de desconto).
 *          d) @Override getTipo(): retorne "Moto".
 */

/*
 * TODO: 10. Crie a classe 'Caminhao' que ESTENDE Veiculo e IMPLEMENTA Rastreavel.
 *
 *           a) Atributo private double capacidadeToneladas.
 *           b) Construtor (String placa, String modelo, double diariaBase, double capacidadeToneladas).
 *           c) @Override calcularDiaria(): retorne diariaBase + (capacidadeToneladas * 50.0)
 *           d) @Override getTipo(): retorne "Caminhão".
 *           e) @Override obterLocalizacao(): retorne "GPS ativo - BR-101, km 42".
 */

// ============================================================
// PARTE 4: O PROGRAMA PRINCIPAL
// ============================================================
public class Exercicio2 {
    public static void main(String[] args) {
        System.out.println("=== LOCADORA DE VEÍCULOS - MÓDULO 2 ===\n");

        /*
         * Descomente os blocos abaixo conforme for completando os TODOs acima.
         */

        // ---------- Bloco 1: polimorfismo em array do tipo do pai ----------
        // Veiculo[] frota = {
        //         new Carro("ABC-1234", "Civic", 150.00, 5),
        //         new Moto("XYZ-9876", "CG 160", 80.00, 160),
        //         new Caminhao("TRK-0001", "Volvo FH", 400.00, 12.5),
        //         new Carro("DEF-5678", "Fiat Uno", 100.00, 4)
        // };
        //
        // System.out.println("--- Frota disponível ---");
        // for (Veiculo v : frota) {
        //     System.out.println(v); // toString() de cada filha
        // }

        // ---------- Bloco 2: cálculo de aluguel (método concreto do pai) ----------
        // int diasAluguel = 7;
        // System.out.println("\n--- Simulação de aluguel por " + diasAluguel + " dias ---");
        //
        // double receitaTotal = 0;
        // for (Veiculo v : frota) {
        //     double valor = v.calcularAluguel(diasAluguel);
        //     System.out.printf("%-10s (%s): R$ %.2f%n", v.getModelo(), v.getPlaca(), valor);
        //     receitaTotal += valor;
        // }
        // System.out.printf("RECEITA TOTAL: R$ %.2f%n", receitaTotal);

        // ---------- Bloco 3: instanceof e interface ----------
        // System.out.println("\n--- Rastreamento da frota ---");
        // for (Veiculo v : frota) {
        //     if (v instanceof Rastreavel) {
        //         Rastreavel r = (Rastreavel) v;
        //         System.out.println(v.getModelo() + ": " + r.obterLocalizacao());
        //     } else {
        //         System.out.println(v.getModelo() + ": sem rastreador instalado.");
        //     }
        // }

        // ---------- Bloco 4: prova de que a abstrata não instancia ----------
        // Descomente a linha abaixo e tente compilar. Leia a mensagem de erro:
        // ela é a prova de que uma classe abstract não pode virar objeto.
        // Veiculo generico = new Veiculo("AAA-0000", "Genérico", 100.00);

        System.out.println("Complete os TODOs e descomente os blocos do main.");
    }
}
