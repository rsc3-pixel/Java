# ☕ Curso de Java: do básico ao Spring Boot

Repositório de estudos de Java estruturado em 6 módulos progressivos, do primeiro `System.out.println` até uma API REST funcional com Spring Boot e banco de dados, mais um módulo extra de revisão para a prova de POO da CESAR School.

**➡️ Comece pelo [roadmap.md](./roadmap.md), que é o índice completo do curso.**

---

## O que tem aqui

| Módulo | Tema | Conteúdo |
| :--- | :--- | :--- |
| [1](./modulo1/) | Fundamentos e Sintaxe | tipos, operadores, condições, laços e métodos |
| [2](./modulo2/) | Programação Orientada a Objetos | classes, encapsulamento, herança, polimorfismo, interfaces |
| [3](./modulo3/) | Coleções | arrays, `List`, `Set`, `Map`, `equals`/`hashCode`, ordenação |
| [4](./modulo4/) | Exceções e Arquivos | `try/catch`, exceções customizadas, leitura e escrita de arquivos |
| [5](./modulo5/) | Java Moderno | generics, lambdas, Streams, `Optional`, `java.time` |
| [6](./modulo6/) | Spring Boot | Maven, JDBC, JPA, arquitetura em camadas, API REST |
| [7](./modulo7/) | Revisão: Prova de POO | as listas e a prova de POO da CESAR School, com o mapa de onde estudar cada assunto |

Cada módulo contém teoria explicada, dois exemplos comentados linha a linha, dois desafios guiados para você completar, uma lista de 50 exercícios de implementação e um simulado de 50 questões com gabarito comentado.

---

## Ler o curso como aplicativo

Para estudar sem clonar nada, o curso também vira um aplicativo do Windows, com menu, busca e tema claro/escuro:

```powershell
cd empacotador
.\compilar.ps1
```

Saem dois arquivos em `dist/`: o programa avulso e um instalador para compartilhar. Os detalhes estão no [README do empacotador](./empacotador/README.md).

Quem recebe o aplicativo **não precisa de JDK** para ler o curso, só para rodar os exemplos.

---

## Como rodar os exemplos

**Pré-requisito:** JDK 17 ou superior.

Os módulos 1 a 5 são arquivos `.java` que rodam direto, a partir da **raiz do repositório**:

```bash
# Compilar
javac -encoding UTF-8 modulo2/Exemplo1.java

# Executar (repare no ponto, não na barra)
java modulo2.Exemplo1
```

O módulo 6 é um projeto Maven completo, com instruções próprias no [README do projeto](./modulo6/projeto-spring/README.md):

```bash
cd modulo6/projeto-spring
./mvnw spring-boot:run     # no Windows: .\mvnw.cmd spring-boot:run
```

---

## Convenções

*   Os arquivos `ExemploN.java` estão **prontos**: rode e leia o código junto com os comentários.
*   Os arquivos `ExercicioN.java` estão **incompletos de propósito**, marcados com `// TODO:`. É onde você escreve.
*   As listas `lista_exercicios.md` pedem implementação; as `lista_exercicios_prep.md` são de múltipla escolha, com gabarito comentado no final do arquivo.
