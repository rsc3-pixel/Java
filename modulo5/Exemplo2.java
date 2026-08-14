package modulo5;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * EXEMPLO 2 DO MÓDULO 5: Streams, Optional e a API de Datas
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo5/Exemplo2.java
 * 2. Para rodar:    java modulo5.Exemplo2
 */

class Funcionario {
    private final String nome;
    private final String departamento;
    private final double salario;
    private final LocalDate admissao;

    public Funcionario(String nome, String departamento, double salario, LocalDate admissao) {
        this.nome = nome;
        this.departamento = departamento;
        this.salario = salario;
        this.admissao = admissao;
    }

    public String getNome() {
        return nome;
    }

    public String getDepartamento() {
        return departamento;
    }

    public double getSalario() {
        return salario;
    }

    public LocalDate getAdmissao() {
        return admissao;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, R$ %.2f)", nome, departamento, salario);
    }
}

public class Exemplo2 {

    public static void main(String[] args) {

        List<Funcionario> equipe = List.of(
                new Funcionario("Renato", "TI", 7500.00, LocalDate.of(2021, 3, 15)),
                new Funcionario("Ana", "TI", 9200.00, LocalDate.of(2019, 7, 1)),
                new Funcionario("Bruno", "Vendas", 4300.00, LocalDate.of(2022, 1, 10)),
                new Funcionario("Carla", "Vendas", 5100.00, LocalDate.of(2020, 11, 5)),
                new Funcionario("Diego", "RH", 3800.00, LocalDate.of(2023, 6, 20)),
                new Funcionario("Elisa", "TI", 11000.00, LocalDate.of(2018, 2, 28))
        );

        // ============================================================
        // PARTE 1: imperativo vs declarativo, lado a lado
        // ============================================================
        System.out.println("=== PARTE 1: O contraste ===\n");

        // JEITO ANTIGO: descrevendo COMO fazer, passo a passo
        List<String> antigoJeito = new ArrayList<>();
        for (Funcionario f : equipe) {
            if (f.getSalario() > 5000) {
                antigoJeito.add(f.getNome().toUpperCase());
            }
        }
        antigoJeito.sort(Comparator.naturalOrder());
        System.out.println("Imperativo (8 linhas): " + antigoJeito);

        // JEITO MODERNO: descrevendo O QUE quero
        List<String> novoJeito = equipe.stream()
                .filter(f -> f.getSalario() > 5000)
                .map(f -> f.getNome().toUpperCase())
                .sorted()
                .toList();
        System.out.println("Declarativo (5 linhas): " + novoJeito);

        // ============================================================
        // PARTE 2: operações intermediárias
        // ============================================================
        System.out.println("\n=== PARTE 2: Operações intermediárias ===\n");

        System.out.println("filter (salário > 5000):");
        equipe.stream()
                .filter(f -> f.getSalario() > 5000)
                .forEach(f -> System.out.println("  " + f));

        System.out.println("\nmap (só os nomes): "
                + equipe.stream().map(Funcionario::getNome).toList());

        System.out.println("\nsorted por salário decrescente:");
        equipe.stream()
                .sorted(Comparator.comparingDouble(Funcionario::getSalario).reversed())
                .forEach(f -> System.out.printf("  %-8s R$ %9.2f%n", f.getNome(), f.getSalario()));

        System.out.println("\nsorted por departamento e depois por nome (desempate):");
        equipe.stream()
                .sorted(Comparator.comparing(Funcionario::getDepartamento)
                        .thenComparing(Funcionario::getNome))
                .forEach(f -> System.out.println("  " + f.getDepartamento() + " - " + f.getNome()));

        System.out.println("\nlimit + skip (paginação: 2 itens a partir do 3º):");
        equipe.stream().skip(2).limit(2).forEach(f -> System.out.println("  " + f.getNome()));

        System.out.println("\ndistinct nos departamentos: "
                + equipe.stream().map(Funcionario::getDepartamento).distinct().toList());

        // ============================================================
        // PARTE 3: preguiça (lazy evaluation)
        // ============================================================
        System.out.println("\n=== PARTE 3: Streams são preguiçosos ===\n");

        System.out.println("Montando um pipeline SEM operação terminal:");
        equipe.stream().filter(f -> {
            System.out.println("  ...testando " + f.getNome());
            return true;
        });
        System.out.println("Nada foi impresso acima: sem terminal, o pipeline NÃO roda.");

        System.out.println("\nAgora COM operação terminal (.count()):");
        long quantos = equipe.stream().filter(f -> {
            System.out.println("  ...testando " + f.getNome());
            return f.getSalario() > 5000;
        }).count();
        System.out.println("Resultado: " + quantos + " funcionários.");

        // ============================================================
        // PARTE 4: operações terminais
        // ============================================================
        System.out.println("\n=== PARTE 4: Operações terminais ===\n");

        System.out.println("count:      " + equipe.stream().filter(f -> f.getSalario() > 5000).count());
        System.out.println("anyMatch:   existe alguém acima de 10 mil? "
                + equipe.stream().anyMatch(f -> f.getSalario() > 10000));
        System.out.println("allMatch:   todos ganham acima de 3 mil? "
                + equipe.stream().allMatch(f -> f.getSalario() > 3000));
        System.out.println("noneMatch:  ninguém ganha abaixo de mil? "
                + equipe.stream().noneMatch(f -> f.getSalario() < 1000));

        System.out.println("joining:    "
                + equipe.stream().map(Funcionario::getNome).collect(Collectors.joining(", ")));

        double folha = equipe.stream().mapToDouble(Funcionario::getSalario).sum();
        System.out.printf("sum:        folha total R$ %.2f%n", folha);

        double totalReduce = equipe.stream()
                .map(Funcionario::getSalario)
                .reduce(0.0, Double::sum); // 0.0 é o valor inicial
        System.out.printf("reduce:     mesma soma R$ %.2f%n", totalReduce);

        // Estatísticas prontas numa tacada só
        DoubleSummaryStatistics stats = equipe.stream()
                .mapToDouble(Funcionario::getSalario)
                .summaryStatistics();
        System.out.println("\nEstatísticas da folha:");
        System.out.printf("  Quantidade: %d%n", stats.getCount());
        System.out.printf("  Soma:       R$ %.2f%n", stats.getSum());
        System.out.printf("  Média:      R$ %.2f%n", stats.getAverage());
        System.out.printf("  Mínimo:     R$ %.2f%n", stats.getMin());
        System.out.printf("  Máximo:     R$ %.2f%n", stats.getMax());

        // ============================================================
        // PARTE 5: Collectors.groupingBy
        // ============================================================
        System.out.println("\n=== PARTE 5: Agrupamentos ===\n");

        // No Módulo 3 isso levou 5 linhas com putIfAbsent. Agora é uma.
        Map<String, List<Funcionario>> porDepto = equipe.stream()
                .collect(Collectors.groupingBy(Funcionario::getDepartamento));

        System.out.println("groupingBy (agrupado por departamento):");
        porDepto.forEach((depto, lista) -> {
            System.out.println("  " + depto + ":");
            lista.forEach(f -> System.out.println("     - " + f.getNome()));
        });

        Map<String, Long> contagem = equipe.stream()
                .collect(Collectors.groupingBy(Funcionario::getDepartamento, Collectors.counting()));
        System.out.println("\ngroupingBy + counting: " + contagem);

        Map<String, Double> somaPorDepto = equipe.stream()
                .collect(Collectors.groupingBy(Funcionario::getDepartamento,
                        Collectors.summingDouble(Funcionario::getSalario)));
        System.out.println("groupingBy + summing:  " + somaPorDepto);

        Map<Boolean, List<String>> particao = equipe.stream()
                .collect(Collectors.partitioningBy(
                        f -> f.getSalario() > 5000,
                        Collectors.mapping(Funcionario::getNome, Collectors.toList())));
        System.out.println("\npartitioningBy (acima de 5 mil):");
        System.out.println("  true  -> " + particao.get(true));
        System.out.println("  false -> " + particao.get(false));

        // ============================================================
        // PARTE 6: Optional
        // ============================================================
        System.out.println("\n=== PARTE 6: Optional ===\n");

        Optional<Funcionario> maisCaro = equipe.stream()
                .max(Comparator.comparingDouble(Funcionario::getSalario));
        maisCaro.ifPresent(f -> System.out.println("Maior salário: " + f));

        // Busca que ENCONTRA
        Optional<Funcionario> achado = buscarPorNome(equipe, "Ana");
        System.out.println("\nBuscando 'Ana':");
        System.out.println("  isPresent: " + achado.isPresent());
        System.out.println("  valor:     " + achado.orElse(null));

        // Busca que NÃO encontra: sem NullPointerException
        Optional<Funcionario> naoAchado = buscarPorNome(equipe, "Fulano");
        System.out.println("\nBuscando 'Fulano':");
        System.out.println("  isPresent: " + naoAchado.isPresent());
        System.out.println("  orElse:    " + naoAchado.map(Funcionario::getNome).orElse("não encontrado"));

        // ifPresent só executa se houver valor: nenhum if necessário
        naoAchado.ifPresent(f -> System.out.println("  esta linha nunca aparece"));

        // orElseGet calcula a alternativa só quando precisa (lazy)
        String nome = naoAchado.map(Funcionario::getNome).orElseGet(() -> "calculado sob demanda");
        System.out.println("  orElseGet: " + nome);

        try {
            naoAchado.orElseThrow(() -> new IllegalStateException("Funcionário inexistente"));
        } catch (IllegalStateException e) {
            System.out.println("  orElseThrow lançou: " + e.getMessage());
        }

        // ============================================================
        // PARTE 7: API de Datas
        // ============================================================
        System.out.println("\n=== PARTE 7: java.time ===\n");

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Hoje:           " + hoje.format(br));
        System.out.println("Daqui 30 dias:  " + hoje.plusDays(30).format(br));
        System.out.println("Ano passado:    " + hoje.minusYears(1).format(br));
        System.out.println("Hoje continua:  " + hoje.format(br) + "  (as classes são IMUTÁVEIS)");

        LocalDate natal = LocalDate.of(hoje.getYear(), Month.DECEMBER, 25);
        long diasAteNatal = ChronoUnit.DAYS.between(hoje, natal);
        System.out.println("\nDias até o Natal: " + diasAteNatal);

        DateTimeFormatter completo = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("Agora: " + LocalDateTime.now().format(completo));

        // Datas combinadas com streams
        System.out.println("\nTempo de casa de cada funcionário:");
        equipe.stream()
                .sorted(Comparator.comparing(Funcionario::getAdmissao))
                .forEach(f -> {
                    Period p = Period.between(f.getAdmissao(), hoje);
                    System.out.printf("  %-8s admitido em %s -> %d anos e %d meses%n",
                            f.getNome(), f.getAdmissao().format(br), p.getYears(), p.getMonths());
                });

        System.out.println("\nVeteranos (mais de 5 anos de casa): "
                + equipe.stream()
                        .filter(f -> ChronoUnit.YEARS.between(f.getAdmissao(), hoje) >= 5)
                        .map(Funcionario::getNome)
                        .toList());

        // ============================================================
        // PARTE 8: stream de uso único
        // ============================================================
        System.out.println("\n=== PARTE 8: Stream é de uso único ===\n");

        var fluxo = equipe.stream().filter(f -> f.getSalario() > 5000);
        System.out.println("Primeira operação terminal: " + fluxo.count());
        try {
            fluxo.count(); // reutilizar o mesmo stream não é permitido
        } catch (IllegalStateException e) {
            System.out.println("Segunda tentativa falhou: " + e.getMessage());
            System.out.println("Para percorrer duas vezes, crie DOIS streams da coleção.");
        }
    }

    // Devolver Optional AVISA na assinatura que o resultado pode não existir.
    // Isso é muito melhor que devolver null e torcer para quem chamou verificar.
    private static Optional<Funcionario> buscarPorNome(List<Funcionario> lista, String nome) {
        return lista.stream()
                .filter(f -> f.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }
}
