package com.renatochong.cursoapi.repository;

import com.renatochong.cursoapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * O REPOSITORY: a camada de acesso ao banco.
 *
 * Repare que isto é uma INTERFACE e não tem NENHUMA implementação.
 * O Spring Data gera a classe concreta em tempo de execução.
 *
 * Compare mentalmente com o JDBC da teoria: abrir Connection, montar
 * PreparedStatement, percorrer ResultSet, fechar tudo, tratar SQLException.
 * Tudo isso desaparece.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /*
     * Ao estender JpaRepository<Produto, Long> você JÁ GANHA, sem escrever nada:
     *
     *   save(produto)        -> INSERT se o id for null, UPDATE se não for
     *   findById(id)         -> devolve Optional<Produto> (o Optional do Módulo 5)
     *   findAll()            -> List<Produto>
     *   deleteById(id)
     *   count()
     *   existsById(id)
     *
     * Os métodos abaixo são os QUERY METHODS: o Spring LÊ o nome do método,
     * interpreta as palavras-chave e gera o SQL correspondente.
     */

    // SELECT * FROM produtos WHERE categoria = ?
    List<Produto> findByCategoria(String categoria);

    // SELECT * FROM produtos WHERE preco > ?
    List<Produto> findByPrecoGreaterThan(Double preco);

    // SELECT * FROM produtos WHERE UPPER(nome) LIKE UPPER('%?%')
    List<Produto> findByNomeContainingIgnoreCase(String trecho);

    // Combinando condições com And, e ordenando com OrderBy
    List<Produto> findByCategoriaOrderByPrecoDesc(String categoria);

    // Top/First limitam a quantidade de resultados
    List<Produto> findTop3ByOrderByPrecoDesc();

    // Devolver Optional é o correto quando o resultado pode não existir
    Optional<Produto> findFirstByNomeIgnoreCase(String nome);

    // Produtos sem estoque
    List<Produto> findByQuantidadeEstoqueLessThanEqual(Integer quantidade);

    /*
     * Quando o nome do método ficaria absurdamente longo, escreva a consulta.
     * Atenção: isto é JPQL, não SQL. Ele opera sobre a ENTIDADE (Produto) e
     * seus ATRIBUTOS (p.preco), não sobre a tabela e as colunas.
     */
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :min AND :max ORDER BY p.preco")
    List<Produto> buscarNaFaixaDePreco(@Param("min") Double min, @Param("max") Double max);

    @Query("SELECT COALESCE(SUM(p.preco * p.quantidadeEstoque), 0) FROM Produto p")
    Double calcularValorTotalDoEstoque();

    // Para SQL puro, quando o JPQL não basta:
    // @Query(value = "SELECT * FROM produtos WHERE ...", nativeQuery = true)
}
