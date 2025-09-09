package com.rodrigo.biblioteca.Domain;

// Não utilizada, passando a utlizar apenas ClienteResumo para fazer as operações necessárias - pode ser util futuramente para atualização de cadastros
public class Cliente {
    private String nome;
    private String email;
    private String cpf;

    // Criação de um novo cliente
    public Cliente(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

}
