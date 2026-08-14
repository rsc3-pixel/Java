package com.renatochong.cursoapi.service;

import com.renatochong.cursoapi.exception.ProdutoNaoEncontradoException;
import com.renatochong.cursoapi.model.Produto;
import com.renatochong.cursoapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * O SERVICE: onde vivem as REGRAS DE NEGÓCIO.
 *
 * Leia esta classe inteira e repare no que ela NÃO menciona:
 * não há HTTP, não há status 404, não há JSON, não há SQL.
 *
 * É Java puro com regra de negócio. Essa independência é o que permite
 * reaproveitar exatamente estas regras numa API REST, num app mobile,
 * num job agendado ou num teste automatizado, sem duplicar nada.
 */
@Service   // registra esta classe como um Bean gerenciado pelo Spring
public class ProdutoService {

    private final ProdutoRepository repository;

    /**
     * INJEÇÃO DE DEPENDÊNCIA POR CONSTRUTOR (a forma recomendada).
     *
     * O Spring vê que este construtor precisa de um ProdutoRepository e
     * ENTREGA a instância pronta. Você nunca escreve 'new ProdutoRepository()'.
     *
     * Vantagens sobre o @Autowired em cima do atributo:
     *   - o campo pode ser 'final' (imutável depois de construído)
     *   - as dependências ficam explícitas na assinatura
     *   - em teste, basta passar um mock pelo construtor, sem framework
     */
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    /**
     * Repare no uso do Optional (Módulo 5): o findById devolve Optional,
     * e o orElseThrow converte a ausência numa exceção de negócio.
     * Nada de retornar null e torcer para o Controller verificar.
     */
    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    /**
     * @Transactional garante que a operação inteira aconteça ou nada aconteça.
     * Se uma exceção for lançada no meio, o Spring desfaz (rollback) tudo
     * que já foi gravado neste método.
     */
    @Transactional
    public Produto criar(Produto produto) {
        // A REGRA DE NEGÓCIO mora aqui, nunca no Controller.
        validarRegrasDeNegocio(produto);

        // Regra: não permitir dois produtos com o mesmo nome
        repository.findFirstByNomeIgnoreCase(produto.getNome())
                .ifPresent(existente -> {
                    throw new IllegalArgumentException(
                            "Já existe um produto com o nome: " + produto.getNome());
                });

        produto.setId(null); // garante INSERT, mesmo que venha id no corpo da requisição
        return repository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long id, Produto dados) {
        Produto existente = buscarPorId(id); // já lança a exceção se não existir
        validarRegrasDeNegocio(dados);

        existente.setNome(dados.getNome());
        existente.setPreco(dados.getPreco());
        existente.setCategoria(dados.getCategoria());
        existente.setQuantidadeEstoque(dados.getQuantidadeEstoque());

        return repository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria);
    }

    public List<Produto> buscarPorNome(String trecho) {
        return repository.findByNomeContainingIgnoreCase(trecho);
    }

    public List<Produto> buscarNaFaixa(Double min, Double max) {
        if (min > max) {
            throw new IllegalArgumentException("O preço mínimo não pode ser maior que o máximo.");
        }
        return repository.buscarNaFaixaDePreco(min, max);
    }

    public List<Produto> maisCaros() {
        return repository.findTop3ByOrderByPrecoDesc();
    }

    /**
     * REGRA DE NEGÓCIO composta: baixa de estoque.
     * Este é o tipo de operação que jamais deveria estar no Controller,
     * porque envolve validação e alteração de estado.
     */
    @Transactional
    public Produto darBaixaNoEstoque(Long id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser positiva.");
        }

        Produto produto = buscarPorId(id);

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException(String.format(
                    "Estoque insuficiente de '%s'. Disponível: %d, solicitado: %d",
                    produto.getNome(), produto.getQuantidadeEstoque(), quantidade));
        }

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        return repository.save(produto);
    }

    /**
     * Relatório usando Streams (Módulo 5) sobre os dados vindos do banco.
     * Repare como os módulos se somam: o Stream aqui é exatamente o mesmo
     * que você usou sobre listas em memória.
     */
    public Map<String, Object> gerarRelatorio() {
        List<Produto> todos = repository.findAll();

        Map<String, Long> porCategoria = todos.stream()
                .filter(p -> p.getCategoria() != null)
                .collect(Collectors.groupingBy(Produto::getCategoria, Collectors.counting()));

        double precoMedio = todos.stream()
                .mapToDouble(Produto::getPreco)
                .average()
                .orElse(0.0);

        return Map.of(
                "totalDeProdutos", todos.size(),
                "valorTotalEmEstoque", repository.calcularValorTotalDoEstoque(),
                "precoMedio", Math.round(precoMedio * 100.0) / 100.0,
                "quantidadePorCategoria", porCategoria,
                "semEstoque", repository.findByQuantidadeEstoqueLessThanEqual(0).size()
        );
    }

    // Validação centralizada, evitando repetir as mesmas checagens em criar e atualizar
    private void validarRegrasDeNegocio(Produto produto) {
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (produto.getQuantidadeEstoque() != null && produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }
    }
}
