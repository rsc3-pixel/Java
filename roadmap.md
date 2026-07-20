# 🗺️ Java Learning Roadmap: Do Básico ao Avançado

Este roteiro de estudos serve para guiar seu aprendizado de Java estruturando o conhecimento de forma incremental. Cada módulo se baseia no anterior, elevando gradativamente o nível de complexidade.

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
*   **Status:** 📅 *Próxima etapa a ser criada.*

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
*   **Status:** 📅 *Aguardando conclusão do Módulo 2.*

---

## ⚠️ Módulo 4: Tratamento de Erros e Manipulação de Arquivos
**Objetivo:** Construir aplicações resilientes a falhas e ler/salvar dados persistidos no disco local.
*   **Tópicos:**
    *   A árvore de exceções do Java: `Throwable`, `Error`, `Exception` (Checked vs Unchecked).
    *   Capturando e tratando erros com `try`, `catch` e `finally`.
    *   Propagando exceções usando as cláusulas `throw` e `throws`.
    *   Criando suas próprias regras com Exceções Customizadas.
    *   Manipulação de Arquivos de texto: `File`, `FileWriter`, `FileReader`, `BufferedReader`.
*   **Status:** 📅 *Aguardando módulos anteriores.*

---

## ⚡ Módulo 5: Java Moderno (Java 8+) e Recursos Avançados
**Objetivo:** Adotar programação funcional e técnicas modernas do ecossistema Java.
*   **Tópicos:**
    *   Generics: Escrevendo classes e métodos que aceitam qualquer tipo de objeto com segurança de tipo.
    *   Interfaces funcionais e Expressões Lambda (`() -> { }`).
    *   **API de Streams:** Processamento declarativo de dados com `.filter()`, `.map()`, `.forEach()`, `.reduce()` e `.collect()`.
    *   A classe `Optional`: Adeus às verificações excessivas de `null` e prevenção do `NullPointerException`.
    *   Nova API de Datas (`LocalDate`, `LocalDateTime`, `DateTimeFormatter`).
*   **Status:** 📅 *Aguardando módulos anteriores.*

---

## 🔌 Módulo 6: Ecossistema Profissional e Spring Boot
**Objetivo:** Conectar sistemas a bancos de dados SQL e criar sua primeira API Web corporativa.
*   **Tópicos:**
    *   Integração com bancos de dados relacionais usando JDBC.
    *   Gerenciadores de Dependências e Build de projetos: Maven e Gradle.
    *   O ecossistema do Framework Spring Boot.
    *   Arquitetura MVC (Model-View-Controller) aplicada na Web.
    *   Criando rotas e endpoints HTTP REST (`@RestController`, `@GetMapping`, `@PostMapping`).
*   **Status:** 📅 *Etapa Final.*
