package com.rodrigo.biblioteca.DTO;

import java.util.Date;

//Classe para front/terminal
public class EmprestimoResumo {
    private int numEmprestimo;
    private String cliente;
    private String livro;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private String devolvido;

    public EmprestimoResumo(int numEmprestimo, String cliente, String livro, Date dataEmprestimo, Date dataDevolucao, String devolvido  ) {
        this.numEmprestimo = numEmprestimo;
        this.cliente = cliente;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = devolvido;
    }

    public int getNumEmprestimo() {
        return numEmprestimo;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public String getCliente() { return cliente; }
    public String getTitulo() { return livro; }
    public String getDevolvido() { return devolvido; }

    public String toString(){
        return "Número do empréstimo: "+getNumEmprestimo()+"\nTitutlo: "+getTitulo()+"\nCliente: "+getCliente()+"\nData do empéstimo: "+getDataEmprestimo()+"\nDevolução em: "+getDataDevolucao()+"\nStatus: "+getDevolvido()+"\n";
    }
}
