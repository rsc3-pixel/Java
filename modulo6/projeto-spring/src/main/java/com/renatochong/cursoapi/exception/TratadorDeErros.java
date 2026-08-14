package com.renatochong.cursoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * TRATAMENTO GLOBAL DE ERROS.
 *
 * Sem esta classe, qualquer exceção viraria um 500 com stack trace exposto
 * ao cliente, o que além de feio é um risco de segurança (revela a estrutura
 * interna do sistema).
 *
 * Aqui está o elo entre o Módulo 4 e este: o Service lança exceções de NEGÓCIO,
 * sem saber o que é HTTP, e esta classe TRADUZ cada tipo para o status correto.
 * As camadas continuam separadas.
 *
 * @RestControllerAdvice intercepta as exceções de TODOS os controllers.
 */
@RestControllerAdvice
public class TratadorDeErros {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * 404 NOT FOUND: o recurso pedido não existe.
     */
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarNaoEncontrado(ProdutoNaoEncontradoException e) {
        return montarResposta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * 400 BAD REQUEST: o cliente enviou dados que violam uma regra de negócio.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> tratarDadosInvalidos(IllegalArgumentException e) {
        return montarResposta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 400 BAD REQUEST vindo das anotações @NotBlank, @Positive da Entity.
     * Aqui devolvemos campo a campo, para o front saber exatamente onde corrigir.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException e) {
        Map<String, String> camposComErro = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(erro ->
                camposComErro.put(erro.getField(), erro.getDefaultMessage()));

        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", LocalDateTime.now().format(FORMATO));
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("erro", "Dados inválidos");
        corpo.put("campos", camposComErro);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    /**
     * 500 INTERNAL SERVER ERROR: a rede de segurança para o que não foi previsto.
     *
     * Repare: a mensagem devolvida ao cliente é genérica de propósito.
     * O detalhe técnico vai para o log do servidor, não para a resposta HTTP.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErroInesperado(Exception e) {
        // Em produção, use um logger de verdade (SLF4J) em vez de println
        System.err.println("[ERRO NÃO TRATADO] " + e.getClass().getSimpleName() + ": " + e.getMessage());

        return montarResposta(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno. Contate o suporte.");
    }

    private ResponseEntity<Map<String, Object>> montarResposta(HttpStatus status, String mensagem) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", LocalDateTime.now().format(FORMATO));
        corpo.put("status", status.value());
        corpo.put("erro", mensagem);
        return ResponseEntity.status(status).body(corpo);
    }
}
