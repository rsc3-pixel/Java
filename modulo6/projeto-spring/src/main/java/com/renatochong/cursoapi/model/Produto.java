package com.renatochong.cursoapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * A ENTITY: a ponte entre o objeto Java e a linha da tabela.
 *
 * O Hibernate lê estas anotações e gera o SQL de criação da tabela e de todas
 * as operações. Você não escreve CREATE TABLE, INSERT nem SELECT.
 */
@Entity                        // esta classe representa uma tabela
@Table(name = "produtos")      // nome da tabela (sem isso, seria "produto")
public class Produto {

    @Id                                                  // chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // o BANCO gera o valor (auto increment)
    private Long id;

    // As anotações jakarta.validation agem ANTES de chegar ao banco,
    // quando o Controller recebe @Valid. É a primeira linha de defesa.
    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false, length = 100)   // já a coluna 'nullable' é a defesa no BANCO
    private String nome;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    @Column(nullable = false)
    private Double preco;

    @Column(length = 50)
    private String categoria;

    @Column(name = "quantidade_estoque")   // nome diferente no banco, em snake_case
    private Integer quantidadeEstoque = 0;

    /**
     * CONSTRUTOR VAZIO OBRIGATÓRIO.
     * O JPA instancia a entidade por reflexão ao ler o banco, e para isso
     * precisa de um construtor sem argumentos. Sem ele, a aplicação nem sobe.
     */
    public Produto() {
    }

    public Produto(String nome, Double preco, String categoria, Integer quantidadeEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return String.format("Produto{id=%d, nome='%s', preco=%.2f}", id, nome, preco);
    }
}
