package com.rodrigo.biblioteca.Service;

import com.rodrigo.biblioteca.ConnectionFactory;
import com.rodrigo.biblioteca.DAO.LivroDAO;
import com.rodrigo.biblioteca.DTO.LivroResumo;
import com.rodrigo.biblioteca.Domain.Emprestimo;
import com.rodrigo.biblioteca.Domain.Livro;
import com.rodrigo.biblioteca.RegraDeNegocioException;

import java.sql.Connection;
import java.util.Set;

public class LivroService {
    private ConnectionFactory connection;
    private EmprestimoService serviceEmprestimo = new EmprestimoService();

    public LivroService() {
        this.connection = new ConnectionFactory();
    }

    // Lista os livros
    public Set<LivroResumo> listarLivros(){
        Connection conn = connection.recuperarConecxao();
        return new LivroDAO(conn).listaLivros();
    }

    //Adiciona um novo livro
    public void adicionaLivro(String titulo, String autor, String categoria){
        Connection conn = connection.recuperarConecxao();

        if(verificaLivroExiste(titulo)){
            throw new RegraDeNegocioException("O livro fornecido já existe!");
        }
        new LivroDAO(conn).adicionaLivro(titulo, autor, categoria);
    }

    //Verifica se o status de um livro em especifico
    public LivroResumo verificaLivro(String titulo){
        Connection conn = connection.recuperarConecxao();
        LivroResumo livro =  new LivroDAO(conn).verificaLivro(titulo);
        if(livro ==  null){
            throw new RegraDeNegocioException("Livro não existe");
        }
        return livro;
    }

    //Excluir livro e verificar na tabela de emprestimo se o livro esta emprestado ou não
    public void excluirLivro(String t){
        Connection conn = connection.recuperarConecxao();
       Set<Emprestimo> devolvido = serviceEmprestimo.consultarEmprestimo(t);

       // Se um for true, não permite excluir
        if(devolvido.stream().noneMatch(Emprestimo::getDevolvido)){
            throw new RegraDeNegocioException("O livro esta em empréstimo, não pode ser excluído!");
        }

        new LivroDAO(conn).excluirLivro(t);
    }

    //Muda disponibilidade
    public void atualizaLivro(String titulo, boolean disponivel){
        Connection conn = connection.recuperarConecxao();
        new LivroDAO(conn).atualizaLivro(titulo, disponivel);
    }

    public int idLivro(String titulo){
        Connection conn = connection.recuperarConecxao();
        return new LivroDAO(conn).idLivro(titulo);
    }

    private boolean verificaLivroExiste(String t){
        Connection conn = connection.recuperarConecxao();
        Livro l = new LivroDAO(conn).buscaLivro(t);

        return l != null;

    }

}
