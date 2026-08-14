package modulo3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/*
 * DESAFIO 2 DO MÓDULO 3: Sistema de Matrículas
 *
 * Objetivo:
 * Praticar Set (unicidade), Map (chave -> valor), equals/hashCode e ordenação
 * com Comparable, tudo num cenário único.
 *
 * Cenário:
 * Uma faculdade precisa controlar quais alunos estão matriculados em quais
 * disciplinas. Regras:
 *   - Um aluno não pode ser cadastrado duas vezes (mesma matrícula) -> Set
 *   - Cada disciplina tem uma lista de alunos                       -> Map<String, Set<Aluno>>
 *   - O relatório sai ordenado por nome do aluno                    -> Comparable
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:".
 *
 * Como rodar:
 * 1. Para compilar: javac modulo3/Exercicio2.java
 * 2. Para rodar:    java modulo3.Exercicio2
 */

class Aluno implements Comparable<Aluno> {

    private final String matricula;
    private final String nome;

    public Aluno(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    /*
     * TODO: 1. Sobrescreva equals(Object obj) usando APENAS a matrícula como critério.
     *          (dois alunos com a mesma matrícula são a mesma pessoa, mesmo que o
     *           nome tenha sido digitado diferente)
     *
     *          Estrutura padrão:
     *          @Override
     *          public boolean equals(Object obj) {
     *              if (this == obj) return true;
     *              if (obj == null || getClass() != obj.getClass()) return false;
     *              Aluno outro = (Aluno) obj;
     *              return Objects.equals(matricula, outro.matricula);
     *          }
     */

    /*
     * TODO: 2. Sobrescreva hashCode() usando o MESMO campo do equals:
     *          return Objects.hash(matricula);
     *
     *          PENSE: por que os dois precisam usar o mesmo campo?
     *          Porque o HashSet primeiro compara o hash (rápido) e só chama equals
     *          quando os hashes batem. Se equals disser "igual" mas hashCode gerar
     *          números diferentes, o Set nunca chega a comparar os dois e aceita
     *          a duplicata. Pior: o objeto entra na coleção e "some" na busca.
     */

    /*
     * TODO: 3. Implemente compareTo(Aluno outro) ordenando por NOME em ordem alfabética.
     *          Dica: String já sabe se comparar, então:
     *          return this.nome.compareTo(outro.nome);
     */
    @Override
    public int compareTo(Aluno outro) {
        return 0; // TODO: trocar pela comparação por nome
    }

    @Override
    public String toString() {
        return nome + " (" + matricula + ")";
    }
}

public class Exercicio2 {

    // Chave: nome da disciplina | Valor: conjunto de alunos matriculados nela
    private static final Map<String, Set<Aluno>> MATRICULAS = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE MATRÍCULAS ===\n");

        Aluno renato = new Aluno("2024001", "Renato Chong");
        Aluno ana = new Aluno("2024002", "Ana Souza");
        Aluno bruno = new Aluno("2024003", "Bruno Lima");
        Aluno renatoDuplicado = new Aluno("2024001", "RENATO CHONG"); // mesma matrícula

        // ---------- Teste 1: unicidade via equals/hashCode ----------
        System.out.println("--- Teste de unicidade (Set) ---");
        Set<Aluno> todosAlunos = new HashSet<>();
        todosAlunos.add(renato);
        todosAlunos.add(ana);
        todosAlunos.add(bruno);
        boolean entrouDuplicado = todosAlunos.add(renatoDuplicado);

        System.out.println("Duplicado foi aceito? " + entrouDuplicado);
        System.out.println("Total de alunos: " + todosAlunos.size());
        System.out.println("Esperado: false e 3. Se der true e 4, os TODOs 1 e 2 faltam.");

        // ---------- Teste 2: matrículas ----------
        System.out.println("\n--- Matriculando ---");
        matricular("Algoritmos", renato);
        matricular("Algoritmos", ana);
        matricular("Banco de Dados", renato);
        matricular("Banco de Dados", bruno);
        matricular("Redes", ana);
        matricular("Algoritmos", renatoDuplicado); // deve ser recusado

        // ---------- Teste 3: relatórios ----------
        System.out.println("\n--- Relatório por disciplina ---");
        exibirRelatorio();

        System.out.println("\n--- Disciplinas do Renato ---");
        System.out.println(disciplinasDoAluno(renato));

        System.out.println("\n--- Contagem por disciplina ---");
        System.out.println(contarPorDisciplina());
    }

    /*
     * TODO: 4. Complete o método matricular:
     *          a) Use MATRICULAS.putIfAbsent(disciplina, new HashSet<>());
     *             Isso cria o conjunto vazio APENAS se a disciplina ainda não existir.
     *          b) Pegue o Set daquela disciplina com MATRICULAS.get(disciplina).
     *          c) Chame .add(aluno) e guarde o retorno booleano.
     *          d) Se retornou true, imprima "[OK] <aluno> matriculado em <disciplina>".
     *             Se retornou false, imprima "[AVISO] <aluno> já está em <disciplina>".
     */
    private static void matricular(String disciplina, Aluno aluno) {
        // TODO: implementar
    }

    /*
     * TODO: 5. Complete o método exibirRelatorio:
     *          a) Percorra MATRICULAS.entrySet() com for-each.
     *          b) Para cada disciplina, imprima o nome dela.
     *          c) Copie os alunos para uma List (List<Aluno> lista = new ArrayList<>(entrada.getValue()))
     *             PENSE: por que copiar para uma List? Porque Set não tem ordem e
     *             Collections.sort() só funciona em List.
     *          d) Ordene com Collections.sort(lista) — isso usa o compareTo do TODO 3.
     *          e) Imprima cada aluno indentado com "   - ".
     */
    private static void exibirRelatorio() {
        // TODO: implementar
    }

    /*
     * TODO: 6. Complete o método disciplinasDoAluno:
     *          Ele deve retornar uma List<String> com as disciplinas em que o aluno está.
     *          a) Crie a lista de resultado.
     *          b) Percorra MATRICULAS.entrySet().
     *          c) Se entrada.getValue().contains(aluno), adicione entrada.getKey() na lista.
     *             (o contains só funciona porque você implementou equals/hashCode)
     *          d) Retorne a lista.
     */
    private static List<String> disciplinasDoAluno(Aluno aluno) {
        return new ArrayList<>(); // TODO: implementar de verdade
    }

    /*
     * TODO: 7. Complete o método contarPorDisciplina:
     *          Deve retornar um Map<String, Integer> onde a chave é a disciplina
     *          e o valor é a quantidade de alunos nela.
     *          a) Crie o Map de resultado.
     *          b) Percorra MATRICULAS.entrySet() e faça
     *             resultado.put(entrada.getKey(), entrada.getValue().size());
     *          c) Retorne o resultado.
     */
    private static Map<String, Integer> contarPorDisciplina() {
        return new HashMap<>(); // TODO: implementar de verdade
    }
}
