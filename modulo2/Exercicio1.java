package modulo2;

/*
 * DESAFIO 1 DO MÓDULO 2: Sistema de Cadastro de Produtos
 *
 * Objetivo:
 * Praticar criação de classes, construtores, encapsulamento (private + getters/setters
 * com validação), atributos static e sobrescrita de toString().
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:" para que o programa funcione.
 * A classe Produto está incompleta de propósito.
 *
 * Como rodar:
 * 1. Para compilar: javac modulo2/Exercicio1.java
 * 2. Para rodar:    java modulo2.Exercicio1
 *
 * Saída esperada quando estiver correto:
 * - Cada produto exibido com código sequencial (1, 2, 3...)
 * - Estoque nunca fica negativo
 * - Preço nunca aceita valor menor ou igual a zero
 */

class Produto {

    // TODO: 1. Declare um atributo static privado 'contadorCodigos' do tipo int, iniciando em 0.
    //          Ele serve para gerar códigos sequenciais automaticamente.

    // TODO: 2. Declare os atributos de instância PRIVADOS:
    //          - codigo (int)
    //          - nome (String)
    //          - preco (double)
    //          - quantidadeEstoque (int)

    /*
     * TODO: 3. Crie o CONSTRUTOR principal recebendo (String nome, double preco, int quantidadeEstoque).
     *          Dentro dele:
     *          a) Incremente o contadorCodigos.
     *          b) Atribua o valor do contador ao 'codigo' deste objeto (use this.codigo).
     *          c) Atribua nome ao atributo (use this. porque o parâmetro tem o mesmo nome).
     *          d) Valide o preço: se for maior que zero, atribua; senão, atribua 0.01.
     *          e) Valide o estoque: se for maior ou igual a zero, atribua; senão, atribua 0.
     */

    /*
     * TODO: 4. Crie um CONSTRUTOR SOBRECARREGADO recebendo apenas (String nome, double preco).
     *          Ele deve chamar o construtor principal usando this(nome, preco, 0);
     *          Lembre: essa chamada precisa ser a PRIMEIRA linha do construtor.
     */

    // TODO: 5. Crie os GETTERS para codigo, nome, preco e quantidadeEstoque.
    //          Getter é um método public que apenas retorna o atributo.

    /*
     * TODO: 6. Crie o SETTER 'setPreco(double novoPreco)' COM VALIDAÇÃO:
     *          - Se novoPreco <= 0, imprima "[ERRO] Preço deve ser positivo." e retorne sem alterar.
     *          - Caso contrário, atribua o novo valor.
     */

    /*
     * TODO: 7. Crie o método public void adicionarEstoque(int quantidade):
     *          - Se quantidade <= 0, imprima "[ERRO] Quantidade inválida." e retorne.
     *          - Caso contrário, some ao quantidadeEstoque.
     */

    /*
     * TODO: 8. Crie o método public boolean venderUnidades(int quantidade):
     *          - Se quantidade <= 0, imprima "[ERRO] Quantidade inválida." e retorne false.
     *          - Se quantidade > quantidadeEstoque, imprima
     *            "[ERRO] Estoque insuficiente de " + nome  e retorne false.
     *          - Caso contrário, subtraia do estoque e retorne true.
     *
     *          PENSE: por que criar venderUnidades() em vez de um setQuantidadeEstoque() simples?
     *          Resposta: porque a REGRA (não vender mais do que existe) fica protegida dentro
     *          do objeto, e não espalhada por quem usa a classe.
     */

    /*
     * TODO: 9. Crie o método public double calcularValorTotalEstoque():
     *          Retorne preco * quantidadeEstoque.
     */

    /*
     * TODO: 10. Crie o método STATIC public static int getTotalProdutosCadastrados():
     *           Retorne o contadorCodigos.
     *           Repare: é static porque a informação pertence à CLASSE, não a um produto específico.
     */

    /*
     * TODO: 11. Sobrescreva o toString() com @Override, retornando algo como:
     *           "[1] Mouse Gamer | R$ 150,00 | Estoque: 10 un"
     *           Dica: use String.format("[%d] %s | R$ %.2f | Estoque: %d un", ...)
     */
}

public class Exercicio1 {
    public static void main(String[] args) {
        System.out.println("=== CONTROLE DE ESTOQUE - MÓDULO 2 ===\n");

        /*
         * ATENÇÃO: as linhas abaixo estão comentadas porque a classe Produto ainda
         * está vazia e o arquivo não compilaria. Conforme você completar os TODOs,
         * vá DESCOMENTANDO os blocos e recompilando para testar aos poucos.
         */

        // ---------- Bloco 1: criação e toString ----------
        // Produto p1 = new Produto("Mouse Gamer", 150.00, 10);
        // Produto p2 = new Produto("Teclado Mecânico", 320.00, 5);
        // Produto p3 = new Produto("Mousepad", 45.00); // usa o construtor sobrecarregado
        //
        // System.out.println(p1);
        // System.out.println(p2);
        // System.out.println(p3);

        // ---------- Bloco 2: validações do encapsulamento ----------
        // System.out.println("\n--- Testando as validações ---");
        // p1.setPreco(-99);        // deve recusar
        // p1.venderUnidades(50);   // deve recusar: estoque insuficiente
        // p3.adicionarEstoque(-5); // deve recusar
        //
        // Produto invalido = new Produto("Item Bugado", -10.0, -3);
        // System.out.println("Produto corrigido pelo construtor: " + invalido);

        // ---------- Bloco 3: operações válidas ----------
        // System.out.println("\n--- Operações válidas ---");
        // p3.adicionarEstoque(20);
        // p1.venderUnidades(3);
        // p2.setPreco(299.90);
        //
        // System.out.println(p1);
        // System.out.println(p2);
        // System.out.println(p3);

        // ---------- Bloco 4: relatório final ----------
        // System.out.println("\n--- Relatório ---");
        // System.out.printf("Valor em estoque (%s): R$ %.2f%n", p1.getNome(), p1.calcularValorTotalEstoque());
        // System.out.printf("Valor em estoque (%s): R$ %.2f%n", p2.getNome(), p2.calcularValorTotalEstoque());
        // System.out.printf("Valor em estoque (%s): R$ %.2f%n", p3.getNome(), p3.calcularValorTotalEstoque());
        //
        // System.out.println("Total de produtos cadastrados: " + Produto.getTotalProdutosCadastrados());

        System.out.println("Complete os TODOs da classe Produto e descomente os blocos do main.");
    }
}
