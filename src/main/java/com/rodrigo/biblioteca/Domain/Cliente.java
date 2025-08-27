package com.rodrigo.biblioteca.Domain;

public class Cliente {
    private String nome;
    private String email;
    private int id;
    private String cpf;

    // Criação de um novo cliente
    public Cliente(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    // Retorno do banco de dados
    public Cliente(String nome, String email, String cpf, int id) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }
    public String getCpf() {return cpf;}


    public String toString() {
        return "Id: "+getId()+"\nNome: " + getNome() + "\nCPF: "+getCpf()+"\nEmail: " + getEmail();
    }

}
