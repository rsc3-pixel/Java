package modulo7;

// Estes imports já servem ao TODO 8. Até você fazê-lo, a IDE avisa que não
// estão sendo usados; é só um aviso, o arquivo compila normalmente.
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * DESAFIO 1 DO MÓDULO 7: Lista 3 do professor, exercícios 1, 2 e 3
 *
 * Objetivo:
 * Praticar o trio que mais cai na discursiva: sobrecarga de construtores,
 * @Override obrigatório e interface como contrato. De bônus, criar a sua
 * própria anotação.
 *
 * Instruções:
 * Complete os locais marcados com "// TODO:". Faça SEM olhar o Exemplo 2
 * primeiro; só consulte se travar. Depois descomente os blocos do main.
 *
 * Como rodar (a partir da raiz do repositório):
 * 1. Para compilar: javac -encoding UTF-8 modulo7/Exercicio1.java
 * 2. Para rodar:    java modulo7.Exercicio1
 *
 * Saída esperada quando estiver correto:
 * - Dois pedidos impressos pelo toString, o segundo com valor 0.0
 * - "senha123" autentica (true) e "errada" não (false)
 * - "Cliente é persistente? true"
 */

// ============================================================
// EXERCÍCIO 1: Pedido
// ============================================================
class Pedido {

    // TODO: 1. Declare os atributos PRIVADOS: codigo (int), nomeCliente (String), valor (double).
    //          (A lista escreve nome_cliente, mas a convenção Java é camelCase: nomeCliente.)

    /*
     * TODO: 2. Construtor COMPLETO recebendo (int codigo, String nomeCliente, double valor).
     *          Use this. para diferenciar atributo de parâmetro.
     */

    /*
     * TODO: 3. Construtor SOBRECARREGADO recebendo só (int codigo, String nomeCliente).
     *          Ele deve DELEGAR para o completo, passando 0 como valor.
     *          Lembre: this(...) tem que ser a primeira linha.
     */

    // TODO: 4. Getters para os três atributos.

    /*
     * TODO: 5. Sobrescreva toString() COM @Override, retornando algo como:
     *          "Pedido [codigo=1, cliente=Ana, valor=150.0]"
     *
     *          PENSE: por que toString() é uma SOBRESCRITA e não uma sobrecarga?
     *          De qual classe ele vem, se Pedido não tem extends?
     */
}

// ============================================================
// EXERCÍCIO 2: interface Autenticavel e classe Usuario
// ============================================================

// TODO: 6. Crie a interface Autenticavel com o método:  boolean autenticar(String senha);
//          (Não precisa escrever public nem abstract: em interface, já são implícitos.)

/*
 * TODO: 7. Crie a classe Usuario que IMPLEMENTA Autenticavel.
 *          a) Atributos privados login e senha (String).
 *          b) Um construtor recebendo os dois.
 *          c) Implemente autenticar COM @Override, devolvendo se a senha recebida
 *             é igual à guardada.
 *
 *          CUIDADO: compare Strings com .equals(), nunca com ==.
 *          CUIDADO 2: o método precisa ser public na classe. Na interface ele é
 *          public implícito, e a sobrescrita NÃO pode diminuir a visibilidade.
 */

// ============================================================
// EXERCÍCIO 3: anotação @EntidadePersistente
// ============================================================

/*
 * TODO: 8. Crie a anotação EntidadePersistente com:
 *          - @Retention(RetentionPolicy.RUNTIME)  (os imports já estão no topo)
 *          - @Target(ElementType.TYPE)
 *          Lembre da sintaxe: @interface, não interface.
 */

/*
 * TODO: 9. Crie a classe Cliente com os atributos privados id (int) e nome (String),
 *          e cole a anotação @EntidadePersistente em cima dela.
 */

public class Exercicio1 {
    public static void main(String[] args) {
        System.out.println("=== LISTA 3: EXERCÍCIOS 1, 2 E 3 ===\n");

        /*
         * ATENÇÃO: os blocos estão comentados porque as classes ainda estão vazias
         * e o arquivo não compilaria. Descomente um bloco por vez conforme terminar.
         */

        // ---------- Bloco 1: Pedido ----------
        // Pedido p1 = new Pedido(1, "Ana", 150.0);
        // Pedido p2 = new Pedido(2, "Bruno");   // usa o construtor sobrecarregado
        // System.out.println(p1);
        // System.out.println(p2);

        // ---------- Bloco 2: Autenticavel ----------
        // Autenticavel u = new Usuario("renato", "senha123");  // variável do tipo da INTERFACE
        // System.out.println("\nsenha123 -> " + u.autenticar("senha123"));
        // System.out.println("errada   -> " + u.autenticar("errada"));

        // ---------- Bloco 3: anotação lida em execução ----------
        // boolean persistente = Cliente.class.isAnnotationPresent(EntidadePersistente.class);
        // System.out.println("\nCliente é persistente? " + persistente);
        // PENSE: se você trocar RUNTIME por CLASS, o que essa linha passa a imprimir? Por quê?

        System.out.println("Complete os TODOs e descomente os blocos do main.");
    }
}
