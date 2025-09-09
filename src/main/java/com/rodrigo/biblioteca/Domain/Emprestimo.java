package com.rodrigo.biblioteca.Domain;

import java.util.Date;

public class Emprestimo {
    private String usarioEmprestimo;
    private String livroEmprestimo;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private boolean devolvido;

    //Construtor banco de dados
    public Emprestimo(String usuarioEmprestimo, String livroEmprestimo, Date dataEmprestimo, Date dataDevolucao, boolean devolvido) {
        this.usarioEmprestimo = usuarioEmprestimo;
        this.livroEmprestimo = livroEmprestimo;
        this.dataDevolucao = dataEmprestimo;
        this.dataEmprestimo = dataDevolucao;
        this.devolvido = devolvido;
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

    public boolean getDevolvido() {
        return devolvido;
    }

    @Override
    public String toString() {
        return "\nUsuário: "+getUsarioEmprestimo()+"\nLivro: "+getLivroEmprestimo()+"\nData empréstimo: "+getDataEmprestimo() + "\nData de devolução: "+getDataDevolucao()+"\nDevolvido: "+getDevolvido();
    }
}
