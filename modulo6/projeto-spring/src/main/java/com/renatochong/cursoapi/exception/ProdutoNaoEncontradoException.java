package com.renatochong.cursoapi.exception;

/**
 * Exceção customizada de NEGÓCIO, exatamente no estilo do Módulo 4.
 *
 * Repare no que ela NÃO faz: não menciona HTTP, não conhece status 404,
 * não sabe o que é JSON. Ela apenas diz "este produto não existe".
 *
 * Traduzir isso para uma resposta HTTP é trabalho do TratadorDeErros,
 * e essa separação é o que mantém o Service reutilizável fora da web.
 */
public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto não encontrado com o id: " + id);
    }

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
