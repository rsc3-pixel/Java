package modulo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * EXEMPLO 2 DO MÓDULO 3: Set, Map, equals/hashCode e Ordenação
 *
 * Como rodar:
 * 1. Para compilar: javac modulo3/Exemplo2.java
 * 2. Para rodar:    java modulo3.Exemplo2
 */

// ============================================================
// CLASSE DE APOIO: implementa Comparable para ter ordem natural,
// e sobrescreve equals/hashCode para funcionar dentro de Set e Map.
// ============================================================
class Produto implements Comparable<Produto> {

    private final String nome;
    private final double preco;
    private final String categoria;

    public Produto(String nome, double preco, String categoria) {
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

    // ORDEM NATURAL: usada por Collections.sort() e TreeSet.
    // Retorno negativo = vem antes | zero = empate | positivo = vem depois.
    @Override
    public int compareTo(Produto outro) {
        // Double.compare evita o estouro que (int)(a - b) pode causar
        return Double.compare(this.preco, outro.preco);
    }

    // equals + hashCode: SEMPRE os dois juntos, usando os MESMOS campos.
    // Sem isso, o HashSet trata dois produtos idênticos como diferentes.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto outro = (Produto) obj;
        return Double.compare(preco, outro.preco) == 0
                && Objects.equals(nome, outro.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, preco);
    }

    @Override
    public String toString() {
        return String.format("%s (R$ %.2f)", nome, preco);
    }
}

public class Exemplo2 {
    public static void main(String[] args) {

        // ============================================================
        // PARTE 1: Set garante unicidade
        // ============================================================
        System.out.println("=== PARTE 1: Set ===\n");

        Set<String> emails = new HashSet<>();
        System.out.println("Adicionou 1ª vez?  " + emails.add("renato@email.com")); // true
        System.out.println("Adicionou de novo? " + emails.add("renato@email.com")); // false
        emails.add("ana@email.com");
        System.out.println("Total de e-mails: " + emails.size() + " -> " + emails);

        // As três implementações e suas ordens
        System.out.println("\n--- Comparando implementações de Set ---");
        String[] entrada = {"Zeca", "Ana", "Bruno", "Ana"};

        Set<String> hash = new HashSet<>();
        Set<String> linked = new LinkedHashSet<>();
        Set<String> tree = new TreeSet<>();
        for (String s : entrada) {
            hash.add(s);
            linked.add(s);
            tree.add(s);
        }

        System.out.println("HashSet       (sem ordem):        " + hash);
        System.out.println("LinkedHashSet (ordem de inserção): " + linked);
        System.out.println("TreeSet       (ordenado):          " + tree);

        // ============================================================
        // PARTE 2: por que equals/hashCode importam
        // ============================================================
        System.out.println("\n=== PARTE 2: equals e hashCode ===\n");

        Set<Produto> catalogo = new HashSet<>();
        catalogo.add(new Produto("Mouse", 150.00, "Periférico"));
        catalogo.add(new Produto("Mouse", 150.00, "Periférico")); // objeto diferente, conteúdo igual
        catalogo.add(new Produto("Teclado", 320.00, "Periférico"));

        System.out.println("Itens no catálogo: " + catalogo.size());
        System.out.println("Como Produto sobrescreve equals/hashCode, o Mouse duplicado foi recusado.");
        System.out.println("Sem essa sobrescrita, o resultado seria 3 em vez de 2.");

        // ============================================================
        // PARTE 3: Map (chave -> valor)
        // ============================================================
        System.out.println("\n=== PARTE 3: Map ===\n");

        Map<String, Integer> estoque = new HashMap<>();
        estoque.put("Mouse", 10);
        estoque.put("Teclado", 5);
        estoque.put("Monitor", 3);
        estoque.put("Mouse", 15); // chave repetida SUBSTITUI o valor, não duplica

        System.out.println("Estoque completo: " + estoque);
        System.out.println("Unidades de Mouse: " + estoque.get("Mouse"));
        System.out.println("Unidades de Webcam: " + estoque.get("Webcam")); // null
        System.out.println("Webcam com padrão: " + estoque.getOrDefault("Webcam", 0)); // 0, sem null

        System.out.println("Tem a chave 'Monitor'? " + estoque.containsKey("Monitor"));

        // Percorrendo o Map: entrySet() dá chave e valor de uma vez
        System.out.println("\n--- Inventário ---");
        for (Map.Entry<String, Integer> item : estoque.entrySet()) {
            System.out.printf("%-10s: %2d unidades%n", item.getKey(), item.getValue());
        }

        // TreeMap mantém as chaves ordenadas
        Map<String, Integer> ordenado = new TreeMap<>(estoque);
        System.out.println("\nTreeMap (chaves ordenadas): " + ordenado);

        // Caso de uso clássico: contar ocorrências
        System.out.println("\n--- Contando palavras ---");
        String frase = "java é bom java é forte java é verboso";
        Map<String, Integer> contagem = new HashMap<>();
        for (String palavra : frase.split(" ")) {
            // se não existe, começa em 0; depois soma 1
            contagem.put(palavra, contagem.getOrDefault(palavra, 0) + 1);
        }
        System.out.println(contagem);

        // ============================================================
        // PARTE 4: Ordenação
        // ============================================================
        System.out.println("\n=== PARTE 4: Ordenação ===\n");

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Monitor", 899.90, "Vídeo"));
        produtos.add(new Produto("Mouse", 150.00, "Periférico"));
        produtos.add(new Produto("Teclado", 320.00, "Periférico"));
        produtos.add(new Produto("Headset", 250.00, "Áudio"));

        System.out.println("Ordem de inserção: " + produtos);

        // Ordem NATURAL, definida pelo compareTo (por preço)
        Collections.sort(produtos);
        System.out.println("Por preço (natural): " + produtos);

        // Ordem ALTERNATIVA via Comparator, sem tocar na classe Produto
        produtos.sort(new Comparator<Produto>() {
            @Override
            public int compare(Produto a, Produto b) {
                return a.getNome().compareTo(b.getNome());
            }
        });
        System.out.println("Por nome (Comparator): " + produtos);

        // Ordem decrescente reaproveitando a natural
        produtos.sort(Collections.reverseOrder());
        System.out.println("Por preço decrescente: " + produtos);

        System.out.println("\nMais caro: " + Collections.max(produtos));
        System.out.println("Mais barato: " + Collections.min(produtos));

        // ============================================================
        // PARTE 5: Map + List combinados (agrupar por categoria)
        // ============================================================
        System.out.println("\n=== PARTE 5: Agrupando por categoria ===\n");

        // TreeMap ordena as chaves, mas repare na saída: "Áudio" aparece DEPOIS de
        // "Vídeo". Isso não é bug: String.compareTo compara código Unicode, e letras
        // acentuadas ficam acima de A-Z na tabela. Para ordem alfabética de português
        // de verdade, é preciso passar um Collator como Comparator:
        //   new TreeMap<>(java.text.Collator.getInstance(new java.util.Locale("pt", "BR")))
        Map<String, List<Produto>> porCategoria = new TreeMap<>();
        for (Produto p : produtos) {
            // Se a categoria ainda não tem lista, cria uma; depois adiciona.
            porCategoria.putIfAbsent(p.getCategoria(), new ArrayList<>());
            porCategoria.get(p.getCategoria()).add(p);
        }

        for (Map.Entry<String, List<Produto>> grupo : porCategoria.entrySet()) {
            System.out.println(grupo.getKey() + ":");
            for (Produto p : grupo.getValue()) {
                System.out.println("   - " + p);
            }
        }

        System.out.println("\nNo Módulo 5 este agrupamento inteiro vira uma linha só,");
        System.out.println("usando Streams com Collectors.groupingBy().");
    }
}
