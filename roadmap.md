# 🗺️ Java Learning Roadmap: Do Básico ao Avançado

Este roteiro de estudos serve para guiar seu aprendizado de Java estruturando o conhecimento de forma incremental. Cada módulo se baseia no anterior, elevando gradativamente o nível de complexidade.

## 📊 Mapa do curso

| Módulo | Tema | Pré-requisito | O que você consegue fazer ao final |
| :--- | :--- | :--- | :--- |
| [1](#-módulo-1-fundamentos-e-sintaxe-básica-o-básico) | Fundamentos e Sintaxe | nenhum | escrever programas de terminal com lógica e métodos |
| [2](#-módulo-2-programação-orientada-a-objetos-poo) | POO | Módulo 1 | modelar sistemas com classes, herança e interfaces |
| [3](#-módulo-3-coleções-de-dados-e-estruturas) | Coleções | Módulo 2 | armazenar e manipular grandes volumes de objetos |
| [4](#️-módulo-4-tratamento-de-erros-e-manipulação-de-arquivos) | Exceções e Arquivos | Módulo 2 | criar sistemas resilientes com dados que persistem |
| [5](#-módulo-5-java-moderno-java-8-e-recursos-avançados) | Java Moderno | Módulos 3 e 4 | escrever código declarativo com Streams e lambdas |
| [6](#-módulo-6-ecossistema-profissional-e-spring-boot) | Spring Boot | todos | construir APIs REST conectadas a banco de dados |
| [7](#-módulo-7-revisão-para-a-prova-de-poo) | Revisão: Prova de POO | Módulo 2 | resolver as questões da prova de POO da CESAR School |

**Estrutura de cada módulo:** teoria completa, dois exemplos comentados, dois desafios guiados para completar, uma lista de 50 exercícios de implementação e um simulado de 50 questões com gabarito comentado.

> [!TIP]
> **Como estudar:** leia a teoria, rode os exemplos e leia o código deles, complete os desafios guiados (os `// TODO:`) e só então ataque as listas. Os simulados servem para checar o que ficou de fato, e o gabarito comentado explica o porquê de cada resposta, não apenas qual é a letra certa.

**Pré-requisito para rodar tudo:** JDK 17 ou superior. Confira com `java -version` e `javac -version`.

---

## 🚀 Módulo 1: Fundamentos e Sintaxe Básica (O Básico)
**Objetivo:** Dominar as estruturas de lógica, tipos, operadores e controle de fluxo do Java.
*   **Tópicos:**
    *   Como a JVM funciona (Bytecode, compilação com `javac`, execução com `java`).
    *   Estrutura de uma classe Java e o método de entrada `public static void main`.
    *   Variáveis fortemente tipadas (`int`, `double`, `boolean`, `char`, `String`).
    *   Operadores aritméticos, lógicos e relacionais.
    *   Entrada e Saída no terminal com `Scanner` e `System.out.println`.
    *   **Controle de Fluxo (Condições):** `if`, `else if`, `else`, `switch` e comparação segura de Strings com `.equals()`.
    *   **Estruturas de Repetição (Laços):** `for` tradicional, `while`, `do-while` e `for-each` para percorrer arrays.
    *   **Métodos (Funções):** Anatomia de assinaturas, parâmetros, tipos de retorno (`void` vs tipos primitivos/objetos) e Sobrecarga de Métodos.
*   **Recursos do Módulo:**
    *   📖 [Teoria Completa do Módulo 1](./modulo1/teoria.md)
    *   💻 [Exemplo de Código 1 (Variáveis e E/S)](./modulo1/Exemplo1.java) | [Exemplo de Código 2 (Laços e Métodos)](./modulo1/Exemplo2.java)
    *   📝 [Desafio Guiado 1 (Comissão)](./modulo1/Exercicio1.java) | [Desafio Guiado 2 (Boletim Escolar)](./modulo1/Exercicio2.java)
    *   ✏️ [Lista de Exercícios Aritmética e E/S (50 Questões)](./modulo1/lista_exercicios.md)
    *   ✏️ [Simulado Preparatório sobre Condições, Laços e Métodos (50 Questões)](./modulo1/lista_exercicios_prep.md)

---

## 📦 Módulo 2: Programação Orientada a Objetos (POO)
**Objetivo:** Compreender a filosofia de design de software do Java e modelar sistemas do mundo real.
*   **Tópicos:**
    *   O que são Classes, Objetos, Atributos e Métodos.
    *   Instanciação de objetos e o papel do construtor (`new`).
    *   **Encapsulamento:** Modificadores de acesso (`public`, `private`, `protected`, default) e métodos assessores (`getters` e `setters`).
    *   **Herança:** Reaproveitamento de atributos e métodos com `extends` e a palavra-chave `super`.
    *   **Polimorfismo:** Sobrescrita de métodos (`@Override`) e referências genéricas de classes filhas.
    *   **Abstração:** Classes Abstratas e Interfaces como contratos de design de software.
*   **Recursos do Módulo:**
    *   📖 [Teoria Completa do Módulo 2](./modulo2/teoria.md)
    *   💻 [Exemplo de Código 1 (Classes, Construtores e Encapsulamento)](./modulo2/Exemplo1.java) | [Exemplo de Código 2 (Herança, Polimorfismo e Interfaces)](./modulo2/Exemplo2.java)
    *   📝 [Desafio Guiado 1 (Cadastro de Produtos)](./modulo2/Exercicio1.java) | [Desafio Guiado 2 (Locadora de Veículos)](./modulo2/Exercicio2.java)
    *   ✏️ [Lista de Exercícios de Implementação (50 Questões)](./modulo2/lista_exercicios.md)
    *   ✏️ [Simulado Preparatório sobre POO (50 Questões)](./modulo2/lista_exercicios_prep.md)

---

## 📚 Módulo 3: Coleções de Dados e Estruturas
**Objetivo:** Manipular listas dinâmicas e mapas de dados com eficiência.
*   **Tópicos:**
    *   Arrays tradicionais unidimensionais e Matrizes (Arrays Multidimensionais).
    *   A API de Coleções do Java (`java.util.Collection`).
    *   Trabalhando com listas sequenciais: `ArrayList` e `LinkedList`.
    *   Garantindo unicidade de elementos com conjuntos: `HashSet` e `TreeSet`.
    *   Estruturas de mapeamento Chave-Valor: `HashMap`.
    *   Wrappers (`Integer`, `Double`, `Boolean`) e o comportamento de Autoboxing/Unboxing.
    *   Contrato `equals()` / `hashCode()` e ordenação com `Comparable` e `Comparator`.
*   **Recursos do Módulo:**
    *   📖 [Teoria Completa do Módulo 3](./modulo3/teoria.md)
    *   💻 [Exemplo de Código 1 (Arrays, Matrizes e Listas)](./modulo3/Exemplo1.java) | [Exemplo de Código 2 (Set, Map e Ordenação)](./modulo3/Exemplo2.java)
    *   📝 [Desafio Guiado 1 (Gerenciador de Tarefas)](./modulo3/Exercicio1.java) | [Desafio Guiado 2 (Sistema de Matrículas)](./modulo3/Exercicio2.java)
    *   ✏️ [Lista de Exercícios de Implementação (50 Questões)](./modulo3/lista_exercicios.md)
    *   ✏️ [Simulado Preparatório sobre Coleções (50 Questões)](./modulo3/lista_exercicios_prep.md)

---

## ⚠️ Módulo 4: Tratamento de Erros e Manipulação de Arquivos
**Objetivo:** Construir aplicações resilientes a falhas e ler/salvar dados persistidos no disco local.
*   **Tópicos:**
    *   A árvore de exceções do Java: `Throwable`, `Error`, `Exception` (Checked vs Unchecked).
    *   Capturando e tratando erros com `try`, `catch` e `finally`.
    *   Propagando exceções usando as cláusulas `throw` e `throws`.
    *   Criando suas próprias regras com Exceções Customizadas.
    *   Manipulação de Arquivos de texto: `File`, `FileWriter`, `FileReader`, `BufferedReader`.
    *   `try-with-resources` e a API moderna `Files` / `Path`.
*   **Recursos do Módulo:**
    *   📖 [Teoria Completa do Módulo 4](./modulo4/teoria.md)
    *   💻 [Exemplo de Código 1 (Tratamento de Exceções)](./modulo4/Exemplo1.java) | [Exemplo de Código 2 (Manipulação de Arquivos)](./modulo4/Exemplo2.java)
    *   📝 [Desafio Guiado 1 (Calculadora à Prova de Erros)](./modulo4/Exercicio1.java) | [Desafio Guiado 2 (Agenda com Persistência)](./modulo4/Exercicio2.java)
    *   ✏️ [Lista de Exercícios de Implementação (50 Questões)](./modulo4/lista_exercicios.md)
    *   ✏️ [Simulado Preparatório sobre Exceções e Arquivos (50 Questões)](./modulo4/lista_exercicios_prep.md)

---

## ⚡ Módulo 5: Java Moderno (Java 8+) e Recursos Avançados
**Objetivo:** Adotar programação funcional e técnicas modernas do ecossistema Java.
*   **Tópicos:**
    *   Generics: Escrevendo classes e métodos que aceitam qualquer tipo de objeto com segurança de tipo.
    *   Interfaces funcionais e Expressões Lambda (`() -> { }`).
    *   **API de Streams:** Processamento declarativo de dados com `.filter()`, `.map()`, `.forEach()`, `.reduce()` e `.collect()`.
    *   A classe `Optional`: Adeus às verificações excessivas de `null` e prevenção do `NullPointerException`.
    *   Nova API de Datas (`LocalDate`, `LocalDateTime`, `DateTimeFormatter`).
    *   Agrupamentos com `Collectors.groupingBy()` e `partitioningBy()`.
*   **Recursos do Módulo:**
    *   📖 [Teoria Completa do Módulo 5](./modulo5/teoria.md)
    *   💻 [Exemplo de Código 1 (Generics e Lambdas)](./modulo5/Exemplo1.java) | [Exemplo de Código 2 (Streams, Optional e Datas)](./modulo5/Exemplo2.java)
    *   📝 [Desafio Guiado 1 (Generics e Lambdas)](./modulo5/Exercicio1.java) | [Desafio Guiado 2 (Analisador de Vendas com Streams)](./modulo5/Exercicio2.java)
    *   ✏️ [Lista de Exercícios de Implementação (50 Questões)](./modulo5/lista_exercicios.md)
    *   ✏️ [Simulado Preparatório sobre Java Moderno (50 Questões)](./modulo5/lista_exercicios_prep.md)

---

## 🔌 Módulo 6: Ecossistema Profissional e Spring Boot
**Objetivo:** Conectar sistemas a bancos de dados SQL e criar sua primeira API Web corporativa.
*   **Tópicos:**
    *   Integração com bancos de dados relacionais usando JDBC.
    *   Gerenciadores de Dependências e Build de projetos: Maven e Gradle.
    *   O ecossistema do Framework Spring Boot.
    *   Arquitetura MVC (Model-View-Controller) aplicada na Web.
    *   Criando rotas e endpoints HTTP REST (`@RestController`, `@GetMapping`, `@PostMapping`).
    *   JPA/Hibernate: `@Entity`, `JpaRepository` e Query Methods.
    *   Injeção de Dependência e tratamento global de erros com `@RestControllerAdvice`.
*   **Recursos do Módulo:**
    *   📖 [Teoria Completa do Módulo 6](./modulo6/teoria.md)
    *   🚀 [**Projeto Spring Boot completo e funcional**](./modulo6/projeto-spring/) | [Como rodar](./modulo6/projeto-spring/README.md)
    *   ✏️ [Lista de Exercícios de Projeto (50 Questões)](./modulo6/lista_exercicios.md)
    *   ✏️ [Simulado Preparatório para Entrevista (50 Questões)](./modulo6/lista_exercicios_prep.md)

> [!NOTE]
> Este módulo não tem arquivos `.java` soltos: uma aplicação Spring exige um projeto Maven com dependências. A pasta `projeto-spring/` traz uma API REST completa que roda com um único comando, sem instalar Maven nem banco de dados.

---

## 🎯 Módulo 7: Revisão para a Prova de POO
**Objetivo:** Revisar para a primeira prova de POO da CESAR School (prof. Maurício Braga). Não traz matéria nova: usa a prova do período passado para entender o **estilo** do professor e aprofunda os temas que os outros módulos só tocam de passagem.
*   **Tópicos:**
    *   Mapa de cada slide e lista do professor para a seção do curso onde o assunto está.
    *   Pacotes e a regra da URL invertida.
    *   Construtores na herança e o `super()` invisível.
    *   Sobrecarga contra sobrescrita, e por que `private`, `static` e `final` ficam fora do polimorfismo.
    *   Conversões: o que compila, o que não compila e o que lança `ClassCastException`.
    *   Anotações próprias com `@Retention` e `@Target`, e generics das listas (`Caixa<T>`, `Par<K, V>`).
    *   Classes abstratas contra interfaces, `final`, `equals` e as peças do Spring nos exemplos do professor.
*   **Recursos do Módulo:**
    *   📖 [Teoria e mapa de estudo do Módulo 7](./modulo7/teoria.md)
    *   💻 [Exemplo de Código 1 (O que será impresso?)](./modulo7/Exemplo1.java) | [Exemplo de Código 2 (A discursiva resolvida e anotação própria)](./modulo7/Exemplo2.java)
    *   📝 [Desafio Guiado 1 (Lista 3: Pedido, Autenticavel e anotação)](./modulo7/Exercicio1.java) | [Desafio Guiado 2 (Lista 3: Generics)](./modulo7/Exercicio2.java)
    *   ✏️ [As Listas do Professor (25 Questões)](./modulo7/lista_exercicios.md)
    *   ✏️ [Simulado no Estilo da Prova (40 Questões)](./modulo7/lista_exercicios_prep.md)

> [!NOTE]
> Diferente dos outros módulos, as listas deste têm 25 e 40 questões, não 50: o módulo foi feito para caber na véspera da prova.
