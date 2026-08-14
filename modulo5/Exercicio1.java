package modulo5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * DESAFIO 1 DO MÓDULO 5: Generics e Lambdas
 *
 * Objetivo:
 * Praticar a criação de classes genéricas, interfaces funcionais próprias,
 * expressões lambda e method references.
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:".
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo5/Exercicio1.java
 * 2. Para rodar:    java modulo5.Exercicio1
 */

/*
 * TODO: 1. Crie a classe genérica 'Repositorio<T>' abaixo.
 *
 *          class Repositorio<T> {
 *              private final List<T> itens = new ArrayList<>();
 *
 *              public void adicionar(T item) { ... }
 *              public List<T> listarTodos() { return itens; }
 *              public int total() { return itens.size(); }
 *
 *              // Este é o método interessante: recebe COMPORTAMENTO como argumento
 *              public List<T> buscar(Predicate<T> criterio) {
 *                  List<T> resultado = new ArrayList<>();
 *                  for (T item : itens) {
 *                      if (criterio.test(item)) resultado.add(item);
 *                  }
 *                  return resultado;
 *              }
 *          }
 *
 *          PENSE: por que <T> em vez de criar RepositorioDeProduto,
 *          RepositorioDeCliente, RepositorioDePedido? Porque a lógica de guardar
 *          e buscar é idêntica; só o TIPO muda. Generics eliminam essa duplicação
 *          sem abrir mão da checagem de tipo em tempo de compilação.
 */

/*
 * TODO: 2. Crie a interface funcional 'Transformador<T, R>':
 *
 *          @FunctionalInterface
 *          interface Transformador<T, R> {
 *              R transformar(T entrada);
 *          }
 *
 *          Ela precisa ter EXATAMENTE um método abstrato. É isso que permite
 *          que uma lambda a substitua. Adicionar um segundo método abstrato faz
 *          a anotação @FunctionalInterface causar erro de compilação.
 */

class ProdutoM5 {
    private final String nome;
    private final double preco;
    private final String categoria;

    public ProdutoM5(String nome, double preco, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format("%s (R$ %.2f)", nome, preco);
    }
}

public class Exercicio1 {

    public static void main(String[] args) {
        System.out.println("=== GENERICS E LAMBDAS ===\n");

        List<ProdutoM5> catalogo = List.of(
                new ProdutoM5("Mouse", 150.00, "Periférico"),
                new ProdutoM5("Teclado", 320.00, "Periférico"),
                new ProdutoM5("Monitor", 899.90, "Vídeo"),
                new ProdutoM5("Headset", 250.00, "Áudio"),
                new ProdutoM5("Webcam", 180.00, "Vídeo")
        );

        // ---------- Bloco 1: a classe genérica ----------
        // Descomente conforme completar o TODO 1.
        //
        // Repositorio<ProdutoM5> repo = new Repositorio<>();
        // for (ProdutoM5 p : catalogo) {
        //     repo.adicionar(p);
        // }
        // System.out.println("Total no repositório: " + repo.total());
        //
        // // O MESMO método buscar, comportamentos diferentes via lambda:
        // System.out.println("Acima de R$ 200: " + repo.buscar(p -> p.getPreco() > 200));
        // System.out.println("Categoria Vídeo: " + repo.buscar(p -> p.getCategoria().equals("Vídeo")));
        // System.out.println("Nome com 'e':    " + repo.buscar(p -> p.getNome().contains("e")));
        //
        // // A mesma classe servindo para OUTRO tipo, sem nenhuma alteração:
        // Repositorio<String> repoTexto = new Repositorio<>();
        // repoTexto.adicionar("Java");
        // repoTexto.adicionar("Python");
        // repoTexto.adicionar("TypeScript");
        // System.out.println("Linguagens longas: " + repoTexto.buscar(s -> s.length() > 5));

        // ---------- Bloco 2: as interfaces funcionais prontas ----------
        System.out.println("\n--- Interfaces funcionais ---");

        /*
         * TODO: 3. Crie um Predicate<ProdutoM5> chamado 'ehCaro' que devolva true
         *          quando o preço for maior que 300.
         *          Sintaxe: Predicate<ProdutoM5> ehCaro = p -> ...;
         *          Depois teste com: System.out.println(ehCaro.test(catalogo.get(0)));
         */

        /*
         * TODO: 4. Crie um Function<ProdutoM5, String> chamado 'extrairNome' que
         *          devolva apenas o nome do produto.
         *          Depois reescreva a MESMA coisa usando method reference:
         *          Function<ProdutoM5, String> extrairNome2 = ProdutoM5::getNome;
         *          Comprove que as duas produzem o mesmo resultado.
         */

        /*
         * TODO: 5. Crie um Consumer<ProdutoM5> chamado 'exibirEtiqueta' que imprima
         *          o produto no formato:  ">> Mouse | R$ 150,00"
         *          Dica: use System.out.printf dentro da lambda, com chaves:
         *          Consumer<ProdutoM5> exibirEtiqueta = p -> {
         *              System.out.printf(">> %s | R$ %.2f%n", p.getNome(), p.getPreco());
         *          };
         *          Depois aplique em todo o catálogo com: catalogo.forEach(exibirEtiqueta);
         */

        /*
         * TODO: 6. Crie um Supplier<ProdutoM5> chamado 'produtoPadrao' que devolva
         *          um novo ProdutoM5("Sem nome", 0.0, "Indefinido").
         *          PENSE: por que Supplier e não simplesmente criar o objeto direto?
         *          Porque o Supplier ADIA a criação: o objeto só nasce quando .get()
         *          é chamado. Isso importa quando criar é caro (consulta a banco,
         *          leitura de arquivo) e talvez nem seja necessário.
         */

        /*
         * TODO: 7. Crie um BiFunction<Double, Double, Double> chamado 'aplicarDesconto'
         *          que receba (preco, percentual) e devolva o preço com desconto:
         *          preco - (preco * percentual / 100)
         *          Teste com 150.00 e 10.0. O resultado esperado é 135.0.
         */

        // ---------- Bloco 3: combinando Predicates ----------
        System.out.println("\n--- Combinando Predicates ---");

        /*
         * TODO: 8. Crie dois Predicate<ProdutoM5>:
         *            'ehPeriferico'  -> categoria igual a "Periférico"
         *            'ehBarato'      -> preço menor que 200
         *
         *          Depois combine-os e teste cada combinação com o catálogo:
         *            ehPeriferico.and(ehBarato)   -> periférico E barato
         *            ehPeriferico.or(ehBarato)    -> periférico OU barato
         *            ehBarato.negate()            -> NÃO barato
         *
         *          Use o método filtrar() que já está pronto no final desta classe.
         */

        // ---------- Bloco 4: sua interface funcional ----------
        System.out.println("\n--- Transformador customizado ---");

        /*
         * TODO: 9. Usando a interface Transformador do TODO 2, crie:
         *
         *          Transformador<ProdutoM5, String> paraEtiqueta =
         *              p -> p.getNome().toUpperCase() + " - R$ " + p.getPreco();
         *
         *          Transformador<ProdutoM5, Double> paraPrecoComImposto =
         *              p -> p.getPreco() * 1.18;
         *
         *          E aplique nos produtos do catálogo, imprimindo os resultados.
         */

        /*
         * TODO: 10. (DESAFIO EXTRA) Crie o método genérico abaixo e use-o:
         *
         *           private static <T, R> List<R> transformarLista(
         *                   List<T> origem, Transformador<T, R> t)
         *
         *           Ele percorre a lista de origem, aplica o transformador em cada
         *           item e devolve a lista de resultados.
         *
         *           Repare que este método é, na essência, o .map() dos Streams
         *           que você viu na teoria. Implementá-lo à mão é a melhor forma
         *           de entender o que o map faz por baixo dos panos.
         */
    }

    // Método pronto: recebe o critério como parâmetro
    private static List<ProdutoM5> filtrar(List<ProdutoM5> lista, Predicate<ProdutoM5> criterio) {
        List<ProdutoM5> resultado = new ArrayList<>();
        for (ProdutoM5 p : lista) {
            if (criterio.test(p)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
