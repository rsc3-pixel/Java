package com.renatochong.cursoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PONTO DE ENTRADA DA APLICAÇÃO.
 *
 * A anotação @SpringBootApplication combina três outras:
 *
 *   @Configuration        -> esta classe pode definir Beans
 *   @EnableAutoConfiguration -> o Spring configura sozinho o que encontrar no classpath
 *                               (achou o H2 e o JPA? cria a conexão e o EntityManager)
 *   @ComponentScan        -> varre ESTE pacote e todos os subpacotes procurando
 *                            classes anotadas com @RestController, @Service, @Repository
 *
 * É por causa do @ComponentScan que esta classe precisa ficar no pacote RAIZ.
 * Se ela estivesse em um subpacote, o Spring não enxergaria as classes irmãs
 * e a aplicação subiria sem nenhuma rota, sem erro aparente. Esse é um dos
 * enganos mais frustrantes de quem está começando com Spring.
 */
@SpringBootApplication
public class CursoApiApplication {

    public static void main(String[] args) {
        // Este único comando: sobe o Tomcat embutido na porta 8080,
        // cria o contêiner de Beans, conecta no banco e registra as rotas.
        SpringApplication.run(CursoApiApplication.class, args);

        System.out.println("""

                =====================================================
                  API NO AR: http://localhost:8080/api/produtos
                  Console do banco H2: http://localhost:8080/h2-console
                  Encerre com Ctrl + C
                =====================================================
                """);
    }
}
