package com.rodrigo.biblioteca.DTO;

//Classe para front/terminal
public class ClienteResumo {
    private String cpf;
    private String nome;
    private String email;
    private int id;

    public ClienteResumo(String cpf, String nome, String email, int id) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
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
        return "Id: "+getId()+"\nNome: " + getNome() + "\nCPF: "+getCpf()+"\nEmail: " + getEmail()+"\n";
    }
}
