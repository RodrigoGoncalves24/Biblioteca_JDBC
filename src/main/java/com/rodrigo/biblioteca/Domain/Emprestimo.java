package com.rodrigo.biblioteca.Domain;

import javax.xml.crypto.Data;
import java.util.Date;

public class Emprestimo {
    private int id;
    private String usarioEmprestimo;
    private String livroEmprestimo;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private boolean devolvido;

    //Construtor para front
    public Emprestimo(String usuarioEmprestimo, String livroEmprestimo, Date dataEmprestimo) {
        this.usarioEmprestimo = usuarioEmprestimo;
        this.livroEmprestimo = livroEmprestimo;
        this.dataDevolucao = dataEmprestimo;
        this.devolvido = false;
    }

    //Construtor banco de dados
    public Emprestimo(String usuarioEmprestimo, String livroEmprestimo, Date dataEmprestimo, Date dataDevolucao, boolean devolvido) {
        this.usarioEmprestimo = usuarioEmprestimo;
        this.livroEmprestimo = livroEmprestimo;
        this.dataDevolucao = dataEmprestimo;
        this.dataEmprestimo = dataDevolucao;
        this.devolvido = devolvido;
    }

    public int getId() {
        return id;
    }
    public String getUsarioEmprestimo() {
        return usarioEmprestimo;
    }
    public String getLivroEmprestimo() {
        return livroEmprestimo;
    }
    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
       this.dataDevolucao = dataDevolucao;
    }

    public boolean getDevolvido() {
        return devolvido;
    }

    @Override
    public String toString() {
        return "\nUsuário: "+getUsarioEmprestimo()+"\nLivro: "+getLivroEmprestimo()+"\nData empréstimo: "+getDataEmprestimo() + "\n Data de devolução: "+getDataDevolucao();
    }
}
