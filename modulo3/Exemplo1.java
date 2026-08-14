package modulo3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 * EXEMPLO 1 DO MÓDULO 3: Arrays, Matrizes e Listas
 *
 * Como rodar:
 * 1. Para compilar: javac modulo3/Exemplo1.java
 * 2. Para rodar:    java modulo3.Exemplo1
 */
public class Exemplo1 {
    public static void main(String[] args) {

        // ============================================================
        // PARTE 1: ARRAYS (tamanho FIXO, definido na criação)
        // ============================================================
        System.out.println("=== PARTE 1: ARRAYS ===\n");

        // Forma 1: declarar o tamanho e preencher depois
        int[] notas = new int[5]; // 5 posições, todas valendo 0
        notas[0] = 85;
        notas[1] = 92;
        notas[2] = 78;
        notas[3] = 90;
        notas[4] = 65;

        // Forma 2: declarar já com os valores
        String[] materias = {"Java", "Banco de Dados", "Redes", "Algoritmos", "Web"};

        // 'length' é ATRIBUTO de array: sem parênteses
        System.out.println("Total de matérias: " + materias.length);

        int soma = 0;
        for (int i = 0; i < notas.length; i++) {
            System.out.printf("%-16s: %d%n", materias[i], notas[i]);
            soma += notas[i];
        }
        System.out.printf("Média geral: %.2f%n", (double) soma / notas.length);

        // Utilitários da classe Arrays
        int[] copia = Arrays.copyOf(notas, notas.length);
        Arrays.sort(copia); // ordena a CÓPIA, preservando o array original
        System.out.println("\nNotas ordenadas: " + Arrays.toString(copia));
        System.out.println("Menor nota: " + copia[0]);
        System.out.println("Maior nota: " + copia[copia.length - 1]);

        // A limitação do array: não dá para crescer.
        // notas[5] = 100; // ArrayIndexOutOfBoundsException em tempo de execução

        // ============================================================
        // PARTE 2: MATRIZES (arrays de arrays)
        // ============================================================
        System.out.println("\n=== PARTE 2: MATRIZES ===\n");

        // Boletim: 3 alunos x 4 bimestres
        double[][] boletim = {
                {8.0, 7.5, 9.0, 6.5},
                {5.0, 6.0, 7.0, 8.0},
                {9.5, 9.0, 10.0, 8.5}
        };
        String[] alunos = {"Renato", "Ana", "Bruno"};

        System.out.printf("%-8s | %5s %5s %5s %5s | %6s%n", "ALUNO", "1ºB", "2ºB", "3ºB", "4ºB", "MÉDIA");
        System.out.println("-".repeat(48));

        for (int i = 0; i < boletim.length; i++) {           // percorre as LINHAS
            System.out.printf("%-8s |", alunos[i]);
            double somaAluno = 0;
            for (int j = 0; j < boletim[i].length; j++) {    // percorre as COLUNAS
                System.out.printf(" %5.1f", boletim[i][j]);
                somaAluno += boletim[i][j];
            }
            System.out.printf(" | %6.2f%n", somaAluno / boletim[i].length);
        }

        // ============================================================
        // PARTE 3: ArrayList (tamanho DINÂMICO)
        // ============================================================
        System.out.println("\n=== PARTE 3: ArrayList ===\n");

        // Boa prática: declarar pela INTERFACE (List), instanciar pela implementação
        List<String> tarefas = new ArrayList<>();

        // Inserir
        tarefas.add("Estudar Coleções");
        tarefas.add("Fazer os exercícios");
        tarefas.add("Revisar POO");
        tarefas.add(0, "Tomar café"); // insere na posição 0, empurrando o resto

        System.out.println("Lista inicial: " + tarefas);
        System.out.println("Quantidade: " + tarefas.size()); // size() é MÉTODO: com parênteses

        // Ler e atualizar
        System.out.println("Primeira tarefa: " + tarefas.get(0));
        tarefas.set(2, "Fazer os 50 exercícios"); // substitui o índice 2

        // Consultar
        System.out.println("Contém 'Revisar POO'? " + tarefas.contains("Revisar POO"));
        System.out.println("Posição de 'Revisar POO': " + tarefas.indexOf("Revisar POO"));
        System.out.println("Posição de 'Dormir': " + tarefas.indexOf("Dormir")); // -1: não existe

        // Remover
        tarefas.remove("Tomar café"); // pelo OBJETO
        tarefas.remove(0);            // pelo ÍNDICE

        System.out.println("Após remoções: " + tarefas);

        // Percorrer
        System.out.println("\nTarefas pendentes:");
        for (String t : tarefas) {
            System.out.println("  [ ] " + t);
        }

        // ============================================================
        // PARTE 4: A PEGADINHA DO remove() COM List<Integer>
        // ============================================================
        System.out.println("\n=== PARTE 4: A pegadinha do remove() ===\n");

        List<Integer> numeros = new ArrayList<>(List.of(10, 20, 30, 40));
        System.out.println("Lista original: " + numeros);

        numeros.remove(2); // remove o ÍNDICE 2 (o valor 30), não o número 2
        System.out.println("Após remove(2)  -> por índice: " + numeros);

        numeros.remove(Integer.valueOf(20)); // agora sim remove o VALOR 20
        System.out.println("Após remove(Integer.valueOf(20)): " + numeros);

        // ============================================================
        // PARTE 5: ArrayList x LinkedList
        // ============================================================
        System.out.println("\n=== PARTE 5: LinkedList ===\n");

        LinkedList<String> filaAtendimento = new LinkedList<>();
        filaAtendimento.add("Cliente A");
        filaAtendimento.add("Cliente B");
        filaAtendimento.addFirst("PRIORITÁRIO"); // barato na LinkedList
        filaAtendimento.addLast("Cliente C");

        System.out.println("Fila: " + filaAtendimento);
        System.out.println("Atendendo: " + filaAtendimento.removeFirst());
        System.out.println("Fila após atendimento: " + filaAtendimento);

        // ============================================================
        // PARTE 6: ConcurrentModificationException e as saídas corretas
        // ============================================================
        System.out.println("\n=== PARTE 6: Removendo durante a iteração ===\n");

        List<String> nomes = new ArrayList<>(List.of("Ana", "Bruno", "Beatriz", "Carla"));
        System.out.println("Antes: " + nomes);

        // ERRADO: quebraria com ConcurrentModificationException
        // for (String n : nomes) { if (n.startsWith("B")) nomes.remove(n); }

        // CORRETO (Java 8+): removeIf
        nomes.removeIf(n -> n.startsWith("B"));
        System.out.println("Depois de removeIf: " + nomes);
    }
}
