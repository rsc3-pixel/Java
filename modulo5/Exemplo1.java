package modulo5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/*
 * EXEMPLO 1 DO MÓDULO 5: Generics, Interfaces Funcionais e Lambdas
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo5/Exemplo1.java
 * 2. Para rodar:    java modulo5.Exemplo1
 */

// ============================================================
// CLASSE GENÉRICA: <T> é um espaço em branco preenchido no uso.
// Uma classe só, servindo para qualquer tipo, com segurança.
// ============================================================
class Caixa<T> {
    private T conteudo;

    public void guardar(T item) {
        this.conteudo = item;
    }

    public T retirar() {
        return conteudo;
    }

    public boolean estaVazia() {
        return conteudo == null;
    }
}

// ============================================================
// GENERIC COM DOIS PARÂMETROS DE TIPO
// Convenção: K de Key, V de Value.
// ============================================================
class Par<K, V> {
    private final K chave;
    private final V valor;

    public Par(K chave, V valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public K getChave() {
        return chave;
    }

    public V getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return chave + " = " + valor;
    }
}

// ============================================================
// INTERFACE FUNCIONAL: exatamente UM método abstrato.
// É esse detalhe que permite substituí-la por uma lambda.
// ============================================================
@FunctionalInterface // opcional, mas faz o compilador VERIFICAR a regra
interface Calculadora {
    double calcular(double a, double b);
}

public class Exemplo1 {

    public static void main(String[] args) {

        // ============================================================
        // PARTE 1: Classes genéricas
        // ============================================================
        System.out.println("=== PARTE 1: Generics ===\n");

        Caixa<String> caixaTexto = new Caixa<>();
        caixaTexto.guardar("Java 21");
        System.out.println("Caixa de texto: " + caixaTexto.retirar());
        // caixaTexto.guardar(42); // NÃO COMPILA: é exatamente esse o ganho

        Caixa<Integer> caixaNumero = new Caixa<>();
        System.out.println("Caixa vazia? " + caixaNumero.estaVazia());
        caixaNumero.guardar(42);
        int valor = caixaNumero.retirar(); // sem cast: o compilador já sabe o tipo
        System.out.println("Caixa de número: " + valor);

        Par<String, Double> precoMouse = new Par<>("Mouse", 150.00);
        System.out.println("Par genérico: " + precoMouse);

        // Método genérico com limite: <T extends Number>
        List<Integer> inteiros = List.of(1, 2, 3, 4, 5);
        List<Double> decimais = List.of(1.5, 2.5, 3.0);
        System.out.println("Soma de inteiros: " + somar(inteiros));
        System.out.println("Soma de decimais: " + somar(decimais));

        // ============================================================
        // PARTE 2: da classe anônima à lambda
        // ============================================================
        System.out.println("\n=== PARTE 2: A evolução até a lambda ===\n");

        // ANTES do Java 8: classe anônima, 6 linhas para expressar "a + b"
        Calculadora somaAntiga = new Calculadora() {
            @Override
            public double calcular(double a, double b) {
                return a + b;
            }
        };
        System.out.println("Classe anônima:  " + somaAntiga.calcular(10, 5));

        // DEPOIS: a mesma coisa em uma linha.
        // O compilador INFERE os tipos a partir da interface.
        Calculadora soma = (a, b) -> a + b;
        Calculadora subtrai = (a, b) -> a - b;
        Calculadora multiplica = (a, b) -> a * b;

        System.out.println("Lambda soma:     " + soma.calcular(10, 5));
        System.out.println("Lambda subtrai:  " + subtrai.calcular(10, 5));
        System.out.println("Lambda multiplica: " + multiplica.calcular(10, 5));

        // Lambda com corpo de várias linhas: exige chaves e return explícito
        Calculadora divisaoSegura = (a, b) -> {
            if (b == 0) {
                System.out.println("  (divisor zero detectado, devolvendo 0)");
                return 0;
            }
            return a / b;
        };
        System.out.println("Divisão segura:  " + divisaoSegura.calcular(10, 0));

        // ============================================================
        // PARTE 3: as interfaces funcionais prontas do Java
        // ============================================================
        System.out.println("\n=== PARTE 3: java.util.function ===\n");

        // PREDICATE: recebe um T, devolve boolean. Serve para FILTRAR.
        Predicate<String> ehLongo = s -> s.length() > 5;
        System.out.println("'Java' é longo?       " + ehLongo.test("Java"));
        System.out.println("'JavaScript' é longo? " + ehLongo.test("JavaScript"));

        // Predicates podem ser combinados
        Predicate<String> comecaComJ = s -> s.startsWith("J");
        System.out.println("'JavaScript' longo E com J? " + ehLongo.and(comecaComJ).test("JavaScript"));
        System.out.println("'Java' longo OU com J?      " + ehLongo.or(comecaComJ).test("Java"));
        System.out.println("'Java' NÃO é longo?         " + ehLongo.negate().test("Java"));

        // FUNCTION: recebe um T, devolve um R. Serve para TRANSFORMAR.
        Function<String, Integer> tamanho = s -> s.length();
        System.out.println("\nTamanho de 'Renato': " + tamanho.apply("Renato"));

        // Functions podem ser encadeadas
        Function<Integer, Integer> dobro = n -> n * 2;
        System.out.println("Tamanho dobrado: " + tamanho.andThen(dobro).apply("Renato"));

        // CONSUMER: recebe um T, não devolve nada. Serve para EXECUTAR AÇÃO.
        Consumer<String> exibir = s -> System.out.println("  >> " + s);
        System.out.println("\nConsumer em ação:");
        exibir.accept("processando...");

        // SUPPLIER: não recebe nada, devolve um T. Serve para FORNECER valor.
        Supplier<String> saudacao = () -> "Olá do Supplier!";
        System.out.println("\n" + saudacao.get());

        // BIFUNCTION: recebe dois, devolve um.
        BiFunction<Double, Double, String> comparar =
                (a, b) -> a > b ? "primeiro é maior" : "segundo é maior ou igual";
        System.out.println("Comparando 10 e 5: " + comparar.apply(10.0, 5.0));

        // UNARYOPERATOR: transforma mantendo o mesmo tipo.
        UnaryOperator<String> maiuscula = String::toUpperCase;
        System.out.println("Maiúscula: " + maiuscula.apply("java moderno"));

        // ============================================================
        // PARTE 4: Method Reference (::)
        // ============================================================
        System.out.println("\n=== PARTE 4: Method Reference ===\n");

        List<String> linguagens = new ArrayList<>(Arrays.asList("Java", "C#", "TypeScript"));

        System.out.println("Com lambda:");
        linguagens.forEach(nome -> System.out.println("  " + nome));

        System.out.println("Com method reference (mesma coisa, mais curto):");
        linguagens.forEach(System.out::println);

        // Referência a método ESTÁTICO
        Function<String, Integer> converter = Integer::parseInt;
        System.out.println("\nTexto '42' convertido + 8 = " + (converter.apply("42") + 8));

        // Referência a método de INSTÂNCIA do tipo
        Function<String, Integer> tam = String::length;
        System.out.println("Tamanho via method reference: " + tam.apply("Recife"));

        // Referência a CONSTRUTOR
        Supplier<ArrayList<String>> criarLista = ArrayList::new;
        List<String> nova = criarLista.get();
        nova.add("criada por construtor referenciado");
        System.out.println(nova);

        // ============================================================
        // PARTE 5: lambdas como argumento de método
        // ============================================================
        System.out.println("\n=== PARTE 5: Passando comportamento ===\n");

        List<String> nomes = List.of("Renato", "Ana", "Bruno", "Beatriz", "Carlos");

        // O MESMO método, comportamentos diferentes: é isso que a lambda permite.
        System.out.println("Nomes com mais de 5 letras: " + filtrar(nomes, n -> n.length() > 5));
        System.out.println("Nomes que começam com B:    " + filtrar(nomes, n -> n.startsWith("B")));
        System.out.println("Nomes que contêm 'an':      " + filtrar(nomes, n -> n.toLowerCase().contains("an")));

        System.out.println("\nAntes das lambdas, cada um desses filtros exigiria");
        System.out.println("um método separado ou uma classe anônima inteira.");

        // ============================================================
        // PARTE 6: a regra da variável efetivamente final
        // ============================================================
        System.out.println("\n=== PARTE 6: Variáveis capturadas ===\n");

        int limite = 5; // efetivamente final: nunca é reatribuída
        Predicate<String> maiorQueLimite = s -> s.length() > limite;
        System.out.println("'Renato' passa no limite " + limite + "? " + maiorQueLimite.test("Renato"));

        // limite = 10; // se esta linha existisse, a lambda acima NÃO COMPILARIA
        System.out.println("A lambda só captura variáveis que nunca mudam,");
        System.out.println("porque ela pode rodar depois que o método já terminou.");
    }

    // MÉTODO GENÉRICO com limite: <T extends Number> garante que T tem doubleValue()
    private static <T extends Number> double somar(List<T> numeros) {
        double total = 0;
        for (T n : numeros) {
            total += n.doubleValue(); // só é possível porque limitamos T a Number
        }
        return total;
    }

    // Recebe COMPORTAMENTO como argumento, e não apenas dados
    private static List<String> filtrar(List<String> lista, Predicate<String> criterio) {
        List<String> resultado = new ArrayList<>();
        for (String item : lista) {
            if (criterio.test(item)) {
                resultado.add(item);
            }
        }
        return resultado;
    }
}
