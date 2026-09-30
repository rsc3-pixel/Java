package modulo7;

/*
 * DESAFIO 2 DO MÓDULO 7: Lista 3 do professor, exercícios 4 a 7 (Generics)
 *
 * Objetivo:
 * Escrever as duas classes genéricas da lista, Caixa<T> e Par<K, V>, e
 * testá-las com tipos diferentes.
 *
 * A ideia de generics em uma frase: T é um "tipo em branco" que quem USA a
 * classe preenche. Caixa<String> é uma caixa que só aceita String, e o
 * compilador garante isso. Sem generics, tudo seria Object e você precisaria
 * de cast (e correria o risco de ClassCastException) ao retirar.
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:" e descomente os blocos do main.
 *
 * Como rodar (a partir da raiz do repositório):
 * 1. Para compilar: javac -encoding UTF-8 modulo7/Exercicio2.java
 * 2. Para rodar:    java modulo7.Exercicio2
 *
 * Saída esperada quando estiver correto:
 *   Vazia antes? false
 *   Retirado: usando Generics
 *   Vazia depois? true
 *   Caixa de Integer: 42
 *   (Maria, 9.5)
 *   (101, Teclado mecânico)
 *   Chaves: Maria e 101
 */

// ============================================================
// EXERCÍCIO 4: Caixa<T>
// ============================================================
class Caixa<T> {

    // TODO: 1. Declare o atributo privado 'conteudo' do tipo T.

    // TODO: 2. public void guardar(T conteudo): guarda o objeto na caixa.

    /*
     * TODO: 3. public T retirar(): devolve o conteúdo E esvazia a caixa.
     *          "Retirar" não é "espiar": depois de retirar, a caixa fica vazia.
     *          Dica: guarde numa variável local, ponha null no atributo, devolva a local.
     *          PENSE: por que não dá para fazer 'conteudo = null' depois do return?
     */

    // TODO: 4. public boolean estaVazia(): true se não houver conteúdo (conteudo == null).
}

// ============================================================
// EXERCÍCIO 6: Par<K, V>
// ============================================================
class Par<K, V> {

    // TODO: 5. Atributos privados: chave do tipo K e valor do tipo V.

    // TODO: 6. Construtor recebendo (K chave, V valor).

    // TODO: 7. Getters getChave() e getValor(). Repare no tipo de retorno de cada um.

    // TODO: 8. @Override toString() devolvendo o formato "(chave, valor)".
}

public class Exercicio2 {
    public static void main(String[] args) {
        System.out.println("=== LISTA 3: GENERICS ===\n");

        // ---------- Bloco 1 (exercício 5): Caixa de String ----------
        // Caixa<String> caixaTexto = new Caixa<>();   // o <> vazio: o tipo é deduzido da esquerda
        // caixaTexto.guardar("usando Generics");
        // System.out.println("Vazia antes? " + caixaTexto.estaVazia());
        // System.out.println("Retirado: " + caixaTexto.retirar());
        // System.out.println("Vazia depois? " + caixaTexto.estaVazia());
        //
        // caixaTexto.guardar(10);   // PENSE: descomente só esta linha. Por que não compila?

        // ---------- Bloco 2 (exercício 5): Caixa de Integer ----------
        // Caixa<Integer> caixaNumero = new Caixa<>();  // Integer, não int: generics não aceita primitivo
        // caixaNumero.guardar(42);                     // autoboxing: o 42 vira Integer sozinho
        // System.out.println("Caixa de Integer: " + caixaNumero.retirar());

        // ---------- Bloco 3 (exercício 7): pares ----------
        // Par<String, Double> notaAluno = new Par<>("Maria", 9.5);
        // Par<Integer, String> codigoProduto = new Par<>(101, "Teclado mecânico");
        // System.out.println(notaAluno);
        // System.out.println(codigoProduto);
        // System.out.println("Chaves: " + notaAluno.getChave() + " e " + codigoProduto.getChave());

        System.out.println("Complete os TODOs e descomente os blocos do main.");
    }
}
