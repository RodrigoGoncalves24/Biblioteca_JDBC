package com.rodrigo.biblioteca.DTO;

//Classe para front/terminal
public class LivroResumo {
    private String titulo;
    private String autor;
    private String categoria;
    private String disponivel;

    public LivroResumo(String titulo, String autor, String categoria, String disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.disponivel = disponivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public String isDisponivel() { return disponivel; }

    public String toString() {
        return "Titulo: "+getTitulo()+"\nAutor: "+getAutor()+"\nCategoria: "+getCategoria()+"\nDisponivel: "+isDisponivel()+"\n";
    }
}
