package modulo4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * EXEMPLO 2 DO MÓDULO 4: Manipulação de Arquivos
 *
 * Este exemplo CRIA arquivos numa pasta 'dados' ao lado de onde você rodar.
 * No fim, ele limpa tudo que criou.
 *
 * Como rodar:
 * 1. Para compilar: javac -encoding UTF-8 modulo4/Exemplo2.java
 * 2. Para rodar:    java modulo4.Exemplo2
 */

class Produto {
    private final int codigo;
    private final String nome;
    private final double preco;

    public Produto(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    // SERIALIZAR: transformar o objeto numa linha de texto
    public String paraCsv() {
        return codigo + ";" + nome + ";" + preco;
    }

    // DESSERIALIZAR: reconstruir o objeto a partir da linha
    public static Produto deCsv(String linha) {
        String[] partes = linha.split(";");
        return new Produto(
                Integer.parseInt(partes[0]),
                partes[1],
                Double.parseDouble(partes[2]));
    }

    @Override
    public String toString() {
        return String.format("[%d] %-12s R$ %8.2f", codigo, nome, preco);
    }
}

public class Exemplo2 {

    private static final String PASTA = "dados";
    private static final String ARQUIVO_LOG = PASTA + "/log.txt";
    private static final String ARQUIVO_CSV = PASTA + "/produtos.csv";

    public static void main(String[] args) {

        // ============================================================
        // PARTE 1: a classe File representa um CAMINHO, não o conteúdo
        // ============================================================
        System.out.println("=== PARTE 1: A classe File ===\n");

        File pasta = new File(PASTA);
        if (!pasta.exists()) {
            boolean criou = pasta.mkdirs(); // mkdirs cria também as pastas pai
            System.out.println("Pasta '" + PASTA + "' criada? " + criou);
        } else {
            System.out.println("Pasta '" + PASTA + "' já existia.");
        }

        File arquivo = new File(ARQUIVO_LOG);
        // O objeto File existe mesmo que o arquivo NÃO exista no disco
        System.out.println("Objeto File criado. Existe no disco? " + arquivo.exists());
        System.out.println("Caminho absoluto: " + arquivo.getAbsolutePath());

        // ============================================================
        // PARTE 2: escrevendo (modo sobrescrever vs modo append)
        // ============================================================
        System.out.println("\n=== PARTE 2: Escrevendo ===\n");

        // try-with-resources: o writer é fechado automaticamente,
        // com erro ou sem erro. Não precisa de finally.
        // O 'false' no FileWriter SOBRESCREVE o arquivo inteiro.
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO_LOG, false))) {
            escritor.write("=== LOG DO SISTEMA ===");
            escritor.newLine(); // quebra de linha do sistema operacional
            escritor.write("Linha 1: sistema iniciado");
            escritor.newLine();
            System.out.println("[OK] Arquivo criado no modo SOBRESCREVER.");

        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao escrever: " + e.getMessage());
        }

        // Agora com 'true': ACRESCENTA ao final, preservando o conteúdo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO_LOG, true))) {
            escritor.write("Linha 2: acrescentada em modo append");
            escritor.newLine();
            escritor.write("Linha 3: o conteúdo anterior foi preservado");
            escritor.newLine();
            System.out.println("[OK] Duas linhas acrescentadas no modo APPEND.");

        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao escrever: " + e.getMessage());
        }

        System.out.println("\nATENÇÃO: sem o 'true', a segunda escrita teria APAGADO");
        System.out.println("todo o conteúdo anterior. É o erro mais comum do módulo.");

        // ============================================================
        // PARTE 3: lendo com BufferedReader
        // ============================================================
        System.out.println("\n=== PARTE 3: Lendo com BufferedReader ===\n");

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_LOG))) {

            String linha;
            int numeroLinha = 1;
            // O idioma padrão: atribui e compara com null na mesma expressão.
            // readLine() devolve null quando o arquivo acaba.
            while ((linha = leitor.readLine()) != null) {
                System.out.printf("%d: %s%n", numeroLinha++, linha);
            }

        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao ler: " + e.getMessage());
        }

        // ============================================================
        // PARTE 4: persistindo objetos em CSV
        // ============================================================
        System.out.println("\n=== PARTE 4: Salvando objetos em CSV ===\n");

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1, "Mouse", 150.00));
        produtos.add(new Produto(2, "Teclado", 320.00));
        produtos.add(new Produto(3, "Monitor", 899.90));

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_CSV))) {
            // PrintWriter permite println e printf, igual ao System.out
            for (Produto p : produtos) {
                pw.println(p.paraCsv());
            }
            System.out.println("[OK] " + produtos.size() + " produtos salvos em " + ARQUIVO_CSV);

        } catch (IOException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }

        // ============================================================
        // PARTE 5: carregando de volta, com tolerância a linha corrompida
        // ============================================================
        System.out.println("\n=== PARTE 5: Carregando do CSV ===\n");

        // Injetando uma linha inválida de propósito, para ver o tratamento agir
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ARQUIVO_CSV, true))) {
            w.write("linha;corrompida;sem_numero");
            w.newLine();
            w.write(""); // linha em branco
            w.newLine();
        } catch (IOException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }

        List<Produto> carregados = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(ARQUIVO_CSV))) {

            String linha;
            while ((linha = r.readLine()) != null) {
                if (linha.isBlank()) {
                    continue; // pula linha vazia sem alarde
                }
                // O try DENTRO do laço é proposital: uma linha ruim não pode
                // impedir a leitura de todas as outras.
                try {
                    carregados.add(Produto.deCsv(linha));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("[AVISO] Linha ignorada (corrompida): " + linha);
                }
            }

        } catch (IOException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }

        System.out.println("\nProdutos carregados com sucesso:");
        double total = 0;
        for (Produto p : carregados) {
            System.out.println("  " + p);
            total += p.getPreco();
        }
        System.out.printf("Valor total: R$ %.2f%n", total);

        // ============================================================
        // PARTE 6: a API moderna (Files + Path, Java 7+)
        // ============================================================
        System.out.println("\n=== PARTE 6: API moderna com Files ===\n");

        Path caminho = Paths.get(ARQUIVO_LOG);
        try {
            // Lê o arquivo INTEIRO de uma vez para uma lista
            List<String> todasLinhas = Files.readAllLines(caminho);
            System.out.println("Total de linhas no log: " + todasLinhas.size());
            System.out.println("Primeira linha: " + todasLinhas.get(0));

            // Escrita direta em uma chamada (Java 11+)
            Path resumo = Paths.get(PASTA + "/resumo.txt");
            Files.writeString(resumo, "Produtos válidos: " + carregados.size());
            System.out.println("Resumo gravado: " + Files.readString(resumo));

            System.out.println("\nCUIDADO: readAllLines e readString carregam o arquivo");
            System.out.println("INTEIRO na memória. Para arquivos grandes, use BufferedReader.");

        } catch (IOException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }

        // ============================================================
        // PARTE 7: tratando arquivo inexistente
        // ============================================================
        System.out.println("\n=== PARTE 7: Arquivo inexistente ===\n");

        try (BufferedReader r = new BufferedReader(new FileReader(PASTA + "/nao_existe.txt"))) {
            System.out.println(r.readLine());
        } catch (IOException e) {
            // FileNotFoundException é subclasse de IOException, então este catch a cobre
            System.out.println("[ESPERADO] " + e.getClass().getSimpleName());
            System.out.println("Mensagem: " + e.getMessage());
        }

        // ============================================================
        // LIMPEZA: removendo o que este exemplo criou
        // ============================================================
        System.out.println("\n=== Limpando os arquivos de teste ===");
        File dir = new File(PASTA);
        File[] conteudo = dir.listFiles();
        if (conteudo != null) {
            for (File f : conteudo) {
                System.out.println("Removendo: " + f.getName() + " -> " + f.delete());
            }
        }
        System.out.println("Removendo a pasta: " + dir.delete());
    }
}
