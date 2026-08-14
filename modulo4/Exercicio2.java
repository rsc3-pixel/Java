package modulo4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * DESAFIO 2 DO MÓDULO 4: Agenda de Contatos com Persistência
 *
 * Objetivo:
 * Praticar try-with-resources, leitura e escrita de arquivos, serialização em
 * CSV e tratamento tolerante a falhas (uma linha ruim não derruba a leitura toda).
 *
 * Cenário:
 * Uma agenda que SOBREVIVE ao fechamento do programa. Os contatos são gravados
 * em 'agenda/contatos.csv' e carregados de volta na próxima execução.
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:".
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo4/Exercicio2.java
 * 2. Para rodar:    java modulo4.Exercicio2
 *
 * Rode DUAS vezes: na segunda, os contatos da primeira devem reaparecer.
 * É essa a prova de que a persistência funcionou.
 */

class Contato {
    private final String nome;
    private final String telefone;
    private final String email;

    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    /*
     * TODO: 1. Implemente o método paraCsv().
     *          Ele deve devolver os três campos separados por ";", exemplo:
     *          "Renato;81999998888;renato@email.com"
     *
     *          Dica: return nome + ";" + telefone + ";" + email;
     */
    public String paraCsv() {
        return ""; // TODO: substituir
    }

    /*
     * TODO: 2. Implemente o método estático deCsv(String linha).
     *          Ele faz o caminho inverso: recebe a linha e devolve um Contato.
     *
     *          a) Quebre a linha com: String[] p = linha.split(";");
     *          b) VALIDE antes de usar: se p.length != 3, lance
     *             throw new IllegalArgumentException("Linha malformada: " + linha);
     *          c) Retorne new Contato(p[0], p[1], p[2]);
     *
     *          PENSE: sem a validação da letra (b), uma linha com campo faltando
     *          causaria ArrayIndexOutOfBoundsException, que é um erro genérico e
     *          confuso. Lançar sua própria exceção com a linha problemática na
     *          mensagem transforma "deu erro" em "deu erro AQUI, por ISSO".
     */
    public static Contato deCsv(String linha) {
        return null; // TODO: substituir
    }

    @Override
    public String toString() {
        return String.format("%-15s | %-13s | %s", nome, telefone, email);
    }
}

public class Exercicio2 {

    private static final String PASTA = "agenda";
    private static final String ARQUIVO = PASTA + "/contatos.csv";

    public static void main(String[] args) {
        System.out.println("=== AGENDA COM PERSISTÊNCIA ===\n");

        // ---------- Etapa 1: carregar o que já existe ----------
        List<Contato> contatos = carregar();
        System.out.println("Contatos carregados do disco: " + contatos.size());
        for (Contato c : contatos) {
            System.out.println("  " + c);
        }

        // ---------- Etapa 2: adicionar novos ----------
        System.out.println("\nAdicionando novos contatos...");
        contatos.add(new Contato("Renato Chong", "81999998888", "renato@email.com"));
        contatos.add(new Contato("Ana Souza", "81988887777", "ana@email.com"));

        // ---------- Etapa 3: salvar tudo ----------
        salvar(contatos);

        // ---------- Etapa 4: provar que persistiu ----------
        System.out.println("\nRelendo do disco para conferir:");
        List<Contato> reconferidos = carregar();
        for (Contato c : reconferidos) {
            System.out.println("  " + c);
        }
        System.out.println("Total no arquivo: " + reconferidos.size());

        // ---------- Etapa 5: testar a tolerância a linha corrompida ----------
        System.out.println("\n--- Teste de robustez ---");
        corromperArquivo();
        List<Contato> aposCorrupcao = carregar();
        System.out.println("Contatos válidos após a corrupção: " + aposCorrupcao.size());
        System.out.println("Se o número acima for maior que zero, seu tratamento");
        System.out.println("funcionou: a linha ruim foi ignorada sem derrubar o resto.");

        System.out.println("\nRode o programa DE NOVO: os contatos devem reaparecer.");
        System.out.println("Para zerar, apague a pasta '" + PASTA + "'.");
    }

    /*
     * TODO: 3. Complete o método salvar.
     *
     *          a) Garanta que a pasta existe:
     *             File pasta = new File(PASTA);
     *             if (!pasta.exists()) pasta.mkdirs();
     *             PENSE: sem isso, o FileWriter lança IOException, porque ele cria
     *             o ARQUIVO mas nunca cria a PASTA que o contém.
     *
     *          b) Abra o writer com TRY-WITH-RESOURCES (fecha sozinho):
     *             try (BufferedWriter w = new BufferedWriter(new FileWriter(ARQUIVO, false))) {
     *
     *             ATENÇÃO ao 'false': aqui ele é proposital, porque estamos gravando
     *             a lista INTEIRA de uma vez. Se fosse 'true' (append), cada execução
     *             duplicaria todos os contatos no arquivo.
     *
     *          c) Percorra a lista, e para cada contato:
     *             w.write(c.paraCsv());
     *             w.newLine();
     *
     *          d) No catch (IOException e), imprima "[ERRO] " + e.getMessage()
     *
     *          e) No sucesso, imprima quantos contatos foram salvos.
     */
    private static void salvar(List<Contato> contatos) {
        // TODO: implementar
    }

    /*
     * TODO: 4. Complete o método carregar.
     *
     *          a) Crie a lista de resultado: List<Contato> resultado = new ArrayList<>();
     *
     *          b) Se o arquivo NÃO existe, retorne a lista vazia SEM erro:
     *             File f = new File(ARQUIVO);
     *             if (!f.exists()) return resultado;
     *             PENSE: arquivo inexistente na PRIMEIRA execução é situação normal,
     *             não é falha. Tratar isso com exceção seria usar exceção para
     *             fluxo esperado, que é justamente o antipadrão da teoria.
     *
     *          c) Abra com try-with-resources:
     *             try (BufferedReader r = new BufferedReader(new FileReader(ARQUIVO))) {
     *
     *          d) Leia linha a linha com o idioma padrão:
     *             String linha;
     *             while ((linha = r.readLine()) != null) { ... }
     *
     *          e) Dentro do laço: pule linhas em branco com
     *             if (linha.isBlank()) continue;
     *
     *          f) AINDA dentro do laço, use um try/catch INTERNO:
     *             try {
     *                 resultado.add(Contato.deCsv(linha));
     *             } catch (IllegalArgumentException e) {
     *                 System.out.println("[AVISO] " + e.getMessage());
     *             }
     *             PENSE: por que o try fica DENTRO do while?
     *             Porque assim uma única linha corrompida é ignorada e a leitura
     *             continua. Se o try envolvesse o laço inteiro, a primeira linha
     *             ruim abortaria o carregamento de todas as seguintes.
     *
     *          g) Trate IOException do bloco externo e retorne o resultado.
     */
    private static List<Contato> carregar() {
        return new ArrayList<>(); // TODO: implementar
    }

    // Método pronto: injeta uma linha inválida para testar seu tratamento.
    private static void corromperArquivo() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            w.write("linha_sem_ponto_e_virgula");
            w.newLine();
            System.out.println("Linha corrompida injetada no arquivo de propósito.");
        } catch (IOException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    /*
     * TODO: 5. (DESAFIO EXTRA) Implemente e chame no main:
     *
     *          private static List<Contato> buscarPorNome(List<Contato> contatos, String termo)
     *
     *          Devolva apenas os contatos cujo nome contenha o termo, ignorando
     *          maiúsculas e minúsculas:
     *              c.getNome().toLowerCase().contains(termo.toLowerCase())
     *
     *          E também:
     *          private static void exportarRelatorio(List<Contato> contatos)
     *
     *          que grava um arquivo 'agenda/relatorio.txt' com cabeçalho, a lista
     *          formatada e o total ao final. Use PrintWriter, que aceita printf.
     */
}
