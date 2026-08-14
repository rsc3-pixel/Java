package com.renatochong.cursoapi.controller;

import com.renatochong.cursoapi.model.Produto;
import com.renatochong.cursoapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * O CONTROLLER: a porta de entrada HTTP.
 *
 * A regra de ouro desta camada: ela TRADUZ, não DECIDE.
 * Recebe a requisição, chama o Service e devolve a resposta.
 * Se você encontrar um 'if' de regra de negócio aqui, ele está no lugar errado.
 *
 * @RestController = @Controller + @ResponseBody
 * O @ResponseBody é o que faz o Spring converter o objeto devolvido em JSON
 * automaticamente, usando a biblioteca Jackson.
 */
@RestController
@RequestMapping("/api/produtos")   // prefixo de TODAS as rotas desta classe
public class ProdutoController {

    private final ProdutoService service;

    // Injeção por construtor, igual ao Service
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    /**
     * GET /api/produtos
     * Teste: curl http://localhost:8080/api/produtos
     */
    @GetMapping
    public List<Produto> listar() {
        return service.listarTodos(); // a List vira um array JSON sozinha
    }

    /**
     * GET /api/produtos/1
     * @PathVariable captura o valor que vem NO CAMINHO da URL.
     */
    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        // Se não existir, o Service lança ProdutoNaoEncontradoException,
        // que o TratadorDeErros converte em 404. Nenhum if aqui.
        return service.buscarPorId(id);
    }

    /**
     * GET /api/produtos/buscar?categoria=Periferico
     * @RequestParam captura o valor da QUERY STRING (depois do '?').
     */
    @GetMapping("/buscar")
    public List<Produto> buscarPorCategoria(@RequestParam String categoria) {
        return service.buscarPorCategoria(categoria);
    }

    /**
     * GET /api/produtos/pesquisar?nome=mou
     * 'required = false' torna o parâmetro opcional.
     */
    @GetMapping("/pesquisar")
    public List<Produto> pesquisar(@RequestParam(required = false, defaultValue = "") String nome) {
        return service.buscarPorNome(nome);
    }

    /**
     * GET /api/produtos/faixa?min=100&max=500
     */
    @GetMapping("/faixa")
    public List<Produto> buscarNaFaixa(@RequestParam Double min, @RequestParam Double max) {
        return service.buscarNaFaixa(min, max);
    }

    /**
     * GET /api/produtos/mais-caros
     */
    @GetMapping("/mais-caros")
    public List<Produto> maisCaros() {
        return service.maisCaros();
    }

    /**
     * GET /api/produtos/relatorio
     */
    @GetMapping("/relatorio")
    public Map<String, Object> relatorio() {
        return service.gerarRelatorio();
    }

    /**
     * POST /api/produtos
     *
     * @RequestBody converte o JSON recebido em um objeto Produto.
     * @Valid dispara as validações declaradas na Entity (@NotBlank, @Positive).
     *        Se falharem, o Spring lança MethodArgumentNotValidException,
     *        tratada globalmente pelo TratadorDeErros como 400.
     * @ResponseStatus(CREATED) devolve 201 em vez do 200 padrão.
     *
     * Teste:
     *   curl -X POST http://localhost:8080/api/produtos ^
     *        -H "Content-Type: application/json" ^
     *        -d "{\"nome\":\"Webcam\",\"preco\":180.0,\"categoria\":\"Video\",\"quantidadeEstoque\":7}"
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criar(@Valid @RequestBody Produto produto) {
        return service.criar(produto);
    }

    /**
     * PUT /api/produtos/1
     * PUT substitui o recurso INTEIRO. Para alteração parcial, existe o PATCH.
     */
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        return service.atualizar(id, produto);
    }

    /**
     * PATCH /api/produtos/1/baixa?quantidade=2
     * Operação de negócio específica, que não é um CRUD puro.
     */
    @PatchMapping("/{id}/baixa")
    public Produto darBaixa(@PathVariable Long id, @RequestParam int quantidade) {
        return service.darBaixaNoEstoque(id, quantidade);
    }

    /**
     * DELETE /api/produtos/1
     * 204 No Content: sucesso, sem corpo de resposta.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
