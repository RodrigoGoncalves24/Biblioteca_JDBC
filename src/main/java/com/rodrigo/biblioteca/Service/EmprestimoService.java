package com.rodrigo.biblioteca.Service;

import com.rodrigo.biblioteca.ConnectionFactory;
import com.rodrigo.biblioteca.DAO.ClienteDAO;
import com.rodrigo.biblioteca.DAO.EmprestimoDAO;
import com.rodrigo.biblioteca.DAO.LivroDAO;
import com.rodrigo.biblioteca.Domain.Emprestimo;
import com.rodrigo.biblioteca.Domain.Livro;
import com.rodrigo.biblioteca.RegraDeNegocioException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Set;

public class EmprestimoService {

    private final ConnectionFactory connection;
    private static final ClienteService serviceCliente = new ClienteService();
    private static final LivroService serviceLivro = new LivroService();


    public EmprestimoService() {
        this.connection = new ConnectionFactory();
    }

    public Set<Emprestimo> listarEmprestimos(){
        Connection conn = connection.recuperarConecxao();
        return new EmprestimoDAO(conn).listarEmprestimo();
    }

    //Verifica se livro esta disponivel antes de realizar o emprestimo
    public void realizarEmprestimo(String cpf, String titulo, java.sql.Date emprestimo){
        Connection conn = connection.recuperarConecxao();

        Livro l = serviceLivro.verificaLivro(titulo);
        if(!l.getDisponivel()){
            throw new RegraDeNegocioException("O livro já esta emprestado no momento!");
        }

        int idLivro = serviceLivro.idLivro(titulo);
        int idCliente = serviceCliente.idCliente(cpf);

        serviceLivro.atualizaLivro(titulo, l.getDisponivel()); // Atualizando livro como indiponivel

        java.sql.Date dataDevolucao = java.sql.Date.valueOf(emprestimo.toLocalDate().plusMonths(1));
        new EmprestimoDAO(conn).realizarEmprestimo(idCliente, idLivro,  emprestimo, dataDevolucao);


    }

    public void atualizarEmprestimo(String cpf, String t){
        Connection conn = connection.recuperarConecxao();
        int idLivro = serviceLivro.idLivro(t);
        int idCliente = serviceCliente.idCliente(cpf);
        int idEmprestimo = new EmprestimoDAO(conn).idEmprestimo(idLivro, idCliente);

        serviceLivro.atualizaLivro(t,false);

        // Passar a data que o emprestimo foi feito
        java.sql.Date dataDevolucao = java.sql.Date.valueOf(LocalDate.now());

        new EmprestimoDAO(conn).atualizaEmprestimo(idEmprestimo, dataDevolucao);

    }

    public boolean consultarEmprestimo(String t){
        Connection conn = connection.recuperarConecxao();
        int idLivro = serviceLivro.idLivro(t);

        if(idLivro == 0){
            throw new RegraDeNegocioException("Livro não encontrado!");
        }
        return new EmprestimoDAO(conn).consultarEmprestimo(idLivro);

    }

    public Set<Emprestimo> emprestimoPorCliente(String cpf){
        Connection conn = connection.recuperarConecxao();
        Set<Emprestimo> emprestimoCliente = null;

        int idCliente = new ClienteDAO(conn).idCliente(cpf);

        if(idCliente == 0){
            throw new RegraDeNegocioException("Cliente não encontrado!");
        }
        return new EmprestimoDAO(conn).emprestimoPorCliente(idCliente);

    }

}
