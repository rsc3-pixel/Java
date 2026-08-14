package com.renatochong.cursoapi.config;

import com.renatochong.cursoapi.model.Produto;
import com.renatochong.cursoapi.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CARGA INICIAL DE DADOS.
 *
 * CommandLineRunner é uma interface do Spring Boot cujo método run() é
 * executado UMA VEZ, logo depois que a aplicação termina de subir.
 *
 * Como o banco H2 é em memória e some a cada parada, sem isto a API subiria
 * vazia e você precisaria criar tudo no braço via POST para poder testar.
 *
 * Repare que CommandLineRunner é uma interface funcional (um único método),
 * então esta classe inteira poderia ser uma lambda dentro de um @Bean.
 * Preferi a classe para deixar explícito o que está acontecendo.
 */
@Component
public class CargaInicial implements CommandLineRunner {

    private final ProdutoRepository repository;

    public CargaInicial(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        // Guarda de segurança: não duplica se já houver dados
        if (repository.count() > 0) {
            return;
        }

        List<Produto> iniciais = List.of(
                new Produto("Mouse Gamer", 150.00, "Periferico", 25),
                new Produto("Teclado Mecanico", 320.00, "Periferico", 12),
                new Produto("Monitor 24 polegadas", 899.90, "Video", 8),
                new Produto("Headset 7.1", 250.00, "Audio", 15),
                new Produto("Webcam Full HD", 180.00, "Video", 0),
                new Produto("Notebook i7", 4500.00, "Computador", 3),
                new Produto("SSD 1TB", 420.00, "Armazenamento", 30),
                new Produto("Cadeira Ergonomica", 1200.00, "Mobiliario", 5)
        );

        repository.saveAll(iniciais);

        System.out.println("\n[CARGA INICIAL] " + iniciais.size() + " produtos cadastrados.\n");
    }
}
