package com.rodrigo.biblioteca.Service;

import com.rodrigo.biblioteca.ConnectionFactory;
import com.rodrigo.biblioteca.DAO.ClienteDAO;
import com.rodrigo.biblioteca.DAO.EmprestimoDAO;
import com.rodrigo.biblioteca.DAO.LivroDAO;
import com.rodrigo.biblioteca.Domain.Cliente;
import com.rodrigo.biblioteca.Domain.Emprestimo;
import com.rodrigo.biblioteca.Domain.Livro;
import com.rodrigo.biblioteca.RegraDeNegocioException;

import java.sql.Connection;
import java.util.Set;

public class LivroService {
    // Regras de negócio antes de mandar para o banco de dados
    private ConnectionFactory connection;

    public LivroService() {
        this.connection = new ConnectionFactory();
    }

    // Lista os livros
    public Set<Livro> listarLivros(){
        Connection conn = connection.recuperarConecxao();
        return new LivroDAO(conn).listaLivros();
    }

    //Adiciona um novo livro
    public void adicionaLivro(String titulo, String autor, String categoria){
        Connection conn = connection.recuperarConecxao();

        if(titulo.contains("delete") || autor.contains("delete") || categoria.contains("delete")){
            throw new RegraDeNegocioException("Aqui não tico tico!");
        }

        Livro l = new LivroDAO(conn).buscaLivro(titulo);

        if(l != null){
            throw new RegraDeNegocioException("O livro fornecido já existe!");
        }
        conn = connection.recuperarConecxao(); // Péssima prática, ajustar -- conexão é fechada  ao pesquisar se ela existe no banco de dados...
        new LivroDAO(conn).adicionaLivro(titulo, autor, categoria);
    }

    //Verifica se o status de um livro em especifico
    public Livro verificaLivro(String titulo){
        Connection conn = connection.recuperarConecxao();
        Livro livro =  new LivroDAO(conn).verificaLivro(titulo);
        if(livro ==  null){
            throw new RegraDeNegocioException("Livro não existe");
        }
        return livro;
    }

    //Excluir livro e verificar na tabela de emprestimo se o livro esta emprestado ou não
    public void excluirLivro(String t){
        Connection conn = connection.recuperarConecxao();
        int idLivro = new LivroDAO(conn).idLivro(t);

        boolean devolvido = new EmprestimoDAO(conn).consultarEmprestimo(idLivro);

        if(!devolvido){ // verifica se o emprestimo existe ou não para poder excluir
            throw new RegraDeNegocioException("O livro esta em empréstimo, não pode ser excluído!");
        }
        new LivroDAO(conn).excluirLivro(idLivro);
    }

    //Muda disponibilidade
    public void atualizaLivro(String titulo, boolean disponivel){
        Connection conn = connection.recuperarConecxao();
        new LivroDAO(conn).atualizaLivro(titulo, disponivel);
    }

    public int idLivro(String titulo){
        Connection conn = connection.recuperarConecxao();

        if(titulo.contains("delete")){
            throw new RegraDeNegocioException("Aqui não tico tico");
        }

        return new LivroDAO(conn).idLivro(titulo);
    }

}
