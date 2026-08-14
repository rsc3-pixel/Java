package modulo3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * DESAFIO 1 DO MÓDULO 3: Gerenciador de Lista de Tarefas
 *
 * Objetivo:
 * Praticar ArrayList com um menu interativo: adicionar, listar, marcar como
 * concluída, remover e buscar. É o CRUD clássico em memória.
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:".
 * O menu e a estrutura do laço já estão prontos para você focar nas coleções.
 *
 * Como rodar:
 * 1. Para compilar: javac modulo3/Exercicio1.java
 * 2. Para rodar:    java modulo3.Exercicio1
 */

class Tarefa {
    private final String descricao;
    private boolean concluida;

    public Tarefa(String descricao) {
        this.descricao = descricao;
        this.concluida = false; // toda tarefa nasce pendente
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void concluir() {
        this.concluida = true;
    }

    @Override
    public String toString() {
        return (concluida ? "[X] " : "[ ] ") + descricao;
    }
}

public class Exercicio1 {

    // TODO: 1. Declare aqui um atributo static do tipo List<Tarefa> chamado 'tarefas',
    //          já instanciado com new ArrayList<>().
    //          PENSE: por que static? Porque os métodos auxiliares abaixo também são
    //          static (chamados a partir do main sem criar objeto desta classe).

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int opcao;

        System.out.println("=== GERENCIADOR DE TAREFAS ===");

        do {
            exibirMenu();
            System.out.print("Escolha: ");
            opcao = leitor.nextInt();
            leitor.nextLine(); // consome a quebra de linha deixada pelo nextInt()

            switch (opcao) {
                case 1:
                    System.out.print("Descrição da tarefa: ");
                    String descricao = leitor.nextLine();
                    adicionarTarefa(descricao);
                    break;
                case 2:
                    listarTarefas();
                    break;
                case 3:
                    System.out.print("Número da tarefa a concluir: ");
                    int indiceConcluir = leitor.nextInt();
                    concluirTarefa(indiceConcluir - 1); // usuário digita 1, lista usa 0
                    break;
                case 4:
                    System.out.print("Número da tarefa a remover: ");
                    int indiceRemover = leitor.nextInt();
                    removerTarefa(indiceRemover - 1);
                    break;
                case 5:
                    System.out.print("Buscar por: ");
                    String termo = leitor.nextLine();
                    buscarTarefas(termo);
                    break;
                case 6:
                    exibirEstatisticas();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        leitor.close();
    }

    private static void exibirMenu() {
        System.out.println("\n---------------------------");
        System.out.println("1 - Adicionar tarefa");
        System.out.println("2 - Listar tarefas");
        System.out.println("3 - Concluir tarefa");
        System.out.println("4 - Remover tarefa");
        System.out.println("5 - Buscar tarefa");
        System.out.println("6 - Estatísticas");
        System.out.println("0 - Sair");
        System.out.println("---------------------------");
    }

    /*
     * TODO: 2. Complete o método adicionarTarefa:
     *          a) Se a descrição for null ou estiver em branco (use .isBlank()),
     *             imprima "[ERRO] Descrição vazia." e retorne.
     *          b) Caso contrário, crie um new Tarefa(descricao) e adicione na lista
     *             com tarefas.add(...).
     *          c) Imprima uma confirmação.
     */
    private static void adicionarTarefa(String descricao) {
        // TODO: implementar
    }

    /*
     * TODO: 3. Complete o método listarTarefas:
     *          a) Se a lista estiver vazia (use tarefas.isEmpty()), imprima
     *             "Nenhuma tarefa cadastrada." e retorne.
     *          b) Caso contrário, percorra com um for TRADICIONAL (precisamos do índice)
     *             e imprima algo como: "1. [ ] Estudar Coleções"
     *             Dica: System.out.println((i + 1) + ". " + tarefas.get(i));
     */
    private static void listarTarefas() {
        // TODO: implementar
    }

    /*
     * TODO: 4. Complete o método concluirTarefa:
     *          a) Valide o índice: se for menor que 0 OU maior ou igual a tarefas.size(),
     *             imprima "[ERRO] Tarefa inexistente." e retorne.
     *             ATENÇÃO: essa validação é o que evita IndexOutOfBoundsException.
     *          b) Pegue a tarefa com tarefas.get(indice), chame .concluir() nela
     *             e imprima uma confirmação.
     */
    private static void concluirTarefa(int indice) {
        // TODO: implementar
    }

    /*
     * TODO: 5. Complete o método removerTarefa:
     *          a) Faça a mesma validação de índice do método anterior.
     *          b) Use tarefas.remove(indice), que RETORNA a tarefa removida.
     *             Guarde esse retorno numa variável e use na mensagem de confirmação.
     */
    private static void removerTarefa(int indice) {
        // TODO: implementar
    }

    /*
     * TODO: 6. Complete o método buscarTarefas:
     *          a) Crie uma List<Tarefa> local chamada 'encontradas' (new ArrayList<>()).
     *          b) Percorra 'tarefas' com for-each. Para cada uma, verifique se a descrição
     *             contém o termo, ignorando maiúsculas:
     *             t.getDescricao().toLowerCase().contains(termo.toLowerCase())
     *          c) Se contiver, adicione em 'encontradas'.
     *          d) No fim, se 'encontradas' estiver vazia imprima "Nada encontrado.",
     *             senão imprima todas as encontradas.
     */
    private static void buscarTarefas(String termo) {
        // TODO: implementar
    }

    /*
     * TODO: 7. Complete o método exibirEstatisticas:
     *          a) Conte quantas tarefas estão concluídas (percorra e use isConcluida()).
     *          b) Calcule as pendentes: total menos concluídas.
     *          c) Calcule o percentual concluído.
     *             CUIDADO COM DIVISÃO POR ZERO: se a lista estiver vazia, não divida.
     *          d) Imprima total, concluídas, pendentes e o percentual com 1 casa decimal.
     */
    private static void exibirEstatisticas() {
        // TODO: implementar
    }
}
