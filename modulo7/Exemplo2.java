package modulo7;

// Na prova, esta primeira linha seria:  package com.soundwave.musicbox;
// (domínio www.soundwave.com ao contrário, sem o www, + nome do projeto).
// Aqui fica modulo7 porque o pacote precisa bater com a pasta do arquivo.

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * EXEMPLO 2 DO MÓDULO 7: a questão discursiva resolvida + anotação própria
 *
 * Parte 1: a classe Musica da questão 01 da prova do período passado
 *          (resposta modelo; a discursiva deste período será outra).
 *          TENTE ESCREVER A SUA NO PAPEL ANTES DE LER ESTA.
 * Parte 2: uma anotação criada do zero e lida com o programa rodando,
 *          para provar que anotação NÃO serve só para documentar.
 *
 * Como rodar (a partir da raiz do repositório):
 * 1. Para compilar: javac -encoding UTF-8 modulo7/Exemplo2.java
 * 2. Para rodar:    java modulo7.Exemplo2
 */

// ============================================================
// PARTE 1: Midia (dada no enunciado) e Musica (a resposta)
// ============================================================
class Midia { }   // exatamente como o enunciado deu: vazia

class Musica extends Midia {             // "deverá herdar de Midia"

    // Encapsulamento: ninguém de fora escreve musica.duracaoSegundos = -5.
    private String nome;
    private int duracaoSegundos;         // "duração em segundos": inteiro basta

    // Construtor 1: só o nome, duração igual a ZERO.
    // Em vez de repetir as atribuições, delega para o construtor completo.
    // Se amanhã entrar uma validação, ela fica num lugar só.
    public Musica(String nome) {
        this(nome, 0);
    }

    // Construtor 2: nome e duração.
    // Não escrevi super(): o compilador põe super() sozinho, e funciona
    // porque Midia tem o construtor vazio automático.
    public Musica(String nome, int duracaoSegundos) {
        this.nome = nome;                        // this.nome = o atributo
        this.duracaoSegundos = duracaoSegundos;  // nome sozinho = o parâmetro
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getDuracaoSegundos() { return duracaoSegundos; }
    public void setDuracaoSegundos(int duracaoSegundos) { this.duracaoSegundos = duracaoSegundos; }

    // "Fazer uso de anotações sempre que sobrescrever um método":
    // toString vem de Object, então ESTE é um método sobrescrito.
    @Override
    public String toString() {
        return "Musica [nome=" + nome + ", duracaoSegundos=" + duracaoSegundos + "]";
    }
}

// ============================================================
// PARTE 2: uma anotação própria
// ============================================================
@Retention(RetentionPolicy.RUNTIME)   // dura até a execução: o programa consegue ler
@Target(ElementType.TYPE)             // só pode ser colada em classes
@interface Destaque {
    String value();                   // anotação de VALOR ÚNICO: @Destaque("...")
}

@Destaque("Mais tocada da semana")
class MusicaEmDestaque extends Musica {
    public MusicaEmDestaque(String nome, int duracaoSegundos) {
        super(nome, duracaoSegundos);  // Musica não tem construtor vazio: super é obrigatório
    }
}

public class Exemplo2 {

    // Recebe QUALQUER Musica e muda o comportamento conforme a etiqueta.
    // É, em miniatura, o que o Spring faz ao encontrar @Entity ou @Controller.
    static void tocar(Musica m) {
        Destaque d = m.getClass().getAnnotation(Destaque.class);
        if (d != null) {
            System.out.println("*** " + d.value() + " *** " + m);
        } else {
            System.out.println(m);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== PARTE 1: os dois construtores ===");
        Musica m1 = new Musica("Asa Branca");
        Musica m2 = new Musica("Construção", 385);
        System.out.println(m1);   // println chama o toString() sozinho
        System.out.println(m2);

        m1.setDuracaoSegundos(190);
        System.out.println("Depois do setter: " + m1.getNome() + " tem "
                + m1.getDuracaoSegundos() + "s");

        System.out.println("\n=== PARTE 2: anotação mudando o comportamento ===");
        tocar(m2);
        tocar(new MusicaEmDestaque("Cálice", 245));
        System.out.println("-> mesmo método tocar(); a etiqueta mudou a saída.");
        System.out.println("   Anotação não é só documentação (alternativa G do período passado: FALSA).");
    }
}
