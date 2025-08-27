package com.rodrigo.biblioteca.Domain;

public class Livro {
    private String titulo;
    private String autor;
    private boolean disponivel;
    private int idLivro;
    private String categoria;

    //Listar apenas o status do livro, se disponível ou não
    public Livro(String titulo, String categoria, boolean disponivel) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.disponivel = disponivel;
    }

    //Listar todos os livros do sistema
    public Livro(String titulo, String categoria, String autor, boolean disponivel, int idLivro) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = disponivel;
        this.idLivro = idLivro;
        this.categoria = categoria;
    }
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }
    public boolean getDisponivel() {
        return disponivel;
    }
    public int getIdLivro() {
        return idLivro;
    }
    public String getCategoria() {return categoria;}

    @Override
    public String toString() {
        return "Id: "+getIdLivro()+ "\nTitulo: "+getTitulo()+"\nCategoria: "+getCategoria()+"\nAutor: "+getAutor()+"\nDisponibilidade: "+getDisponivel();
    }
}
