package modulo5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * DESAFIO 2 DO MÓDULO 5: Analisador de Vendas com Streams
 *
 * Objetivo:
 * Praticar o pipeline completo de Streams (filter, map, sorted, collect),
 * agrupamentos com Collectors, Optional e a API de datas.
 *
 * Cenário:
 * Você recebeu a base de vendas do semestre e precisa gerar o relatório
 * gerencial. Cada método abaixo responde a uma pergunta do gestor.
 *
 * REGRA DO EXERCÍCIO:
 * Resolva TUDO com Streams, sem escrever nenhum laço 'for'. O objetivo é
 * treinar o pensamento declarativo, não apenas obter o resultado.
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo5/Exercicio2.java
 * 2. Para rodar:    java modulo5.Exercicio2
 */

class Venda {
    private final String vendedor;
    private final String regiao;
    private final String produto;
    private final double valor;
    private final LocalDate data;

    public Venda(String vendedor, String regiao, String produto, double valor, LocalDate data) {
        this.vendedor = vendedor;
        this.regiao = regiao;
        this.produto = produto;
        this.valor = valor;
        this.data = data;
    }

    public String getVendedor() {
        return vendedor;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getProduto() {
        return produto;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("%s | %-8s | %-10s | R$ %8.2f | %s",
                vendedor, regiao, produto, valor,
                data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}

public class Exercicio2 {

    private static final List<Venda> VENDAS = List.of(
            new Venda("Renato", "Nordeste", "Notebook", 4500.00, LocalDate.of(2026, 1, 15)),
            new Venda("Ana", "Sudeste", "Monitor", 1200.00, LocalDate.of(2026, 1, 22)),
            new Venda("Renato", "Nordeste", "Mouse", 150.00, LocalDate.of(2026, 2, 3)),
            new Venda("Bruno", "Sul", "Notebook", 4500.00, LocalDate.of(2026, 2, 14)),
            new Venda("Ana", "Sudeste", "Notebook", 5200.00, LocalDate.of(2026, 3, 8)),
            new Venda("Carla", "Nordeste", "Teclado", 320.00, LocalDate.of(2026, 3, 19)),
            new Venda("Bruno", "Sul", "Monitor", 1100.00, LocalDate.of(2026, 4, 2)),
            new Venda("Renato", "Nordeste", "Monitor", 1350.00, LocalDate.of(2026, 4, 25)),
            new Venda("Carla", "Nordeste", "Notebook", 3900.00, LocalDate.of(2026, 5, 11)),
            new Venda("Ana", "Sudeste", "Mouse", 180.00, LocalDate.of(2026, 5, 30)),
            new Venda("Diego", "Norte", "Teclado", 280.00, LocalDate.of(2026, 6, 7)),
            new Venda("Bruno", "Sul", "Notebook", 4800.00, LocalDate.of(2026, 6, 18))
    );

    public static void main(String[] args) {
        System.out.println("=== ANALISADOR DE VENDAS ===\n");
        System.out.println("Base de dados: " + VENDAS.size() + " vendas registradas.\n");

        // Descomente cada bloco conforme for implementando o método correspondente.

        // System.out.println("1. Vendas acima de R$ 1.000:");
        // vendasAcimaDe(1000).forEach(v -> System.out.println("   " + v));

        // System.out.println("\n2. Faturamento total: R$ " + faturamentoTotal());

        // System.out.println("\n3. Nomes dos vendedores (sem repetir): " + vendedoresUnicos());

        // System.out.println("\n4. Faturamento por vendedor: " + faturamentoPorVendedor());

        // System.out.println("\n5. Quantidade de vendas por região: " + vendasPorRegiao());

        // System.out.println("\n6. Top 3 maiores vendas:");
        // top3Vendas().forEach(v -> System.out.println("   " + v));

        // System.out.println("\n7. Maior venda: " + maiorVenda().map(Venda::toString).orElse("nenhuma"));

        // System.out.println("\n8. Ticket médio: R$ " + ticketMedio());

        // System.out.println("\n9. Produtos vendidos, em ordem alfabética: " + produtosOrdenados());

        // System.out.println("\n10. Vendas do 1º trimestre: " + vendasDoTrimestre(1).size());

        System.out.println("Implemente os métodos abaixo e descomente os blocos do main.");
    }

    /*
     * TODO: 1. Retorne apenas as vendas com valor acima do parâmetro.
     *          Pipeline: VENDAS.stream().filter(...).toList()
     */
    private static List<Venda> vendasAcimaDe(double minimo) {
        return List.of(); // TODO: substituir
    }

    /*
     * TODO: 2. Retorne a soma de TODOS os valores de venda.
     *          Dica: .mapToDouble(Venda::getValor).sum()
     *
     *          PENSE: por que mapToDouble e não map? Porque map devolveria um
     *          Stream<Double> (objetos wrapper), que não tem o método sum().
     *          mapToDouble devolve um DoubleStream primitivo, que ganha sum(),
     *          average(), max() e summaryStatistics() de graça.
     */
    private static double faturamentoTotal() {
        return 0; // TODO: substituir
    }

    /*
     * TODO: 3. Retorne a lista de nomes de vendedores, SEM repetições e ordenada.
     *          Pipeline: .map(...).distinct().sorted().toList()
     */
    private static List<String> vendedoresUnicos() {
        return List.of(); // TODO: substituir
    }

    /*
     * TODO: 4. Retorne um Map<String, Double> com o total vendido por cada vendedor.
     *          Dica: Collectors.groupingBy(Venda::getVendedor,
     *                    Collectors.summingDouble(Venda::getValor))
     *
     *          Compare mentalmente com o que isso exigiria no Módulo 3:
     *          criar o Map, percorrer com for, testar containsKey, somar o valor
     *          antigo com o novo e dar put. Cinco linhas viram uma.
     */
    private static Map<String, Double> faturamentoPorVendedor() {
        return Map.of(); // TODO: substituir
    }

    /*
     * TODO: 5. Retorne um Map<String, Long> com a QUANTIDADE de vendas por região.
     *          Dica: Collectors.groupingBy(Venda::getRegiao, Collectors.counting())
     *          Atenção: counting() devolve Long, não Integer.
     */
    private static Map<String, Long> vendasPorRegiao() {
        return Map.of(); // TODO: substituir
    }

    /*
     * TODO: 6. Retorne as 3 maiores vendas, da maior para a menor.
     *          Pipeline: .sorted(Comparator.comparingDouble(Venda::getValor).reversed())
     *                    .limit(3).toList()
     */
    private static List<Venda> top3Vendas() {
        return List.of(); // TODO: substituir
    }

    /*
     * TODO: 7. Retorne a maior venda dentro de um Optional.
     *          Dica: .max(Comparator.comparingDouble(Venda::getValor))
     *
     *          PENSE: por que max devolve Optional e não a Venda direto?
     *          Porque a lista pode estar vazia, e nesse caso não existe máximo.
     *          O Optional força quem chama a lidar com esse caso, em vez de
     *          devolver null e criar um NullPointerException lá na frente.
     */
    private static Optional<Venda> maiorVenda() {
        return Optional.empty(); // TODO: substituir
    }

    /*
     * TODO: 8. Retorne o valor médio das vendas.
     *          Dica: .mapToDouble(Venda::getValor).average() devolve um OptionalDouble.
     *          Use .orElse(0.0) para tratar a lista vazia.
     */
    private static double ticketMedio() {
        return 0; // TODO: substituir
    }

    /*
     * TODO: 9. Retorne os nomes dos produtos vendidos, sem repetir, em ordem
     *          alfabética, TUDO numa única String separada por vírgula.
     *          Exemplo de saída: "Monitor, Mouse, Notebook, Teclado"
     *          Dica: termine o pipeline com .collect(Collectors.joining(", "))
     */
    private static String produtosOrdenados() {
        return ""; // TODO: substituir
    }

    /*
     * TODO: 10. Retorne as vendas de um trimestre (1 a 4).
     *           Dica: o mês da venda é v.getData().getMonthValue() (1 a 12).
     *           O trimestre de um mês é: (mes - 1) / 3 + 1
     *           Filtre onde esse cálculo for igual ao parâmetro.
     */
    private static List<Venda> vendasDoTrimestre(int trimestre) {
        return List.of(); // TODO: substituir
    }

    /*
     * TODO: 11. (DESAFIO EXTRA) Implemente e chame no main:
     *
     *           a) melhorVendedor(): devolve Optional<String> com o nome de quem
     *              mais faturou. Dica: pegue o Map do TODO 4, chame .entrySet()
     *              .stream(), use .max(Map.Entry.comparingByValue()) e .map(Entry::getKey).
     *
     *           b) relatorioCompleto(): imprime, para cada região, o total faturado,
     *              a quantidade de vendas e o produto mais vendido dela.
     *
     *           c) vendasDosUltimosDias(int dias): filtra as vendas cuja data esteja
     *              a menos de N dias de LocalDate.now().
     *              Dica: ChronoUnit.DAYS.between(v.getData(), LocalDate.now()) <= dias
     *              (com a base fixa de 2026 acima, teste com valores grandes, como 365)
     */
}
