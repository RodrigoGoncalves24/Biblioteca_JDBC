package com.rodrigo.biblioteca.Service;

import com.rodrigo.biblioteca.ConnectionFactory;
import com.rodrigo.biblioteca.DAO.EmprestimoDAO;
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

    //Listar emprestimos
    public Set<Emprestimo> listarEmprestimos(){
        Connection conn = connection.recuperarConecxao();
        return new EmprestimoDAO(conn).listarEmprestimo();
    }

    //Realiza um novo emprestimo
    public void realizarEmprestimo(String cpf, String titulo, java.sql.Date emprestimo){
        Connection conn = connection.recuperarConecxao();

        Livro l = serviceLivro.verificaLivro(titulo);
        if(!l.getDisponivel()){
            throw new RegraDeNegocioException("O livro já esta emprestado no momento!");
        }

        int idLivro = serviceLivro.idLivro(titulo);
        int idCliente = serviceCliente.idCliente(cpf);

        serviceLivro.atualizaLivro(titulo, l.getDisponivel()); // Atualizando livro como indisponivel

        java.sql.Date dataDevolucao = java.sql.Date.valueOf(emprestimo.toLocalDate().plusMonths(1));
        new EmprestimoDAO(conn).realizarEmprestimo(idCliente, idLivro,  emprestimo, dataDevolucao);
        System.out.println("Empréstimo realizado com sucesso");

    }

    //Atualiza com a devolutiva do emprestimo
    public void atualizarEmprestimo(String cpf, String t){
        Connection conn = connection.recuperarConecxao();
        int idLivro = serviceLivro.idLivro(t);
        int idCliente = serviceCliente.idCliente(cpf);
        int idEmprestimo = idEmprestimo(idLivro, idCliente);

        serviceLivro.atualizaLivro(t,false); //Atualiza status do livro

        // Passar a data que a devolucao foi feita foi feito
        java.sql.Date dataDevolucao = java.sql.Date.valueOf(LocalDate.now());

        new EmprestimoDAO(conn).atualizaEmprestimo(idEmprestimo, dataDevolucao);
        System.out.println("Obrigado pela preferência!");

    }

    //Pesquisa se um emprestimo foi -- FAZ SENTIDO?
    public boolean consultarEmprestimo(String tituloLivro){
        Connection conn = connection.recuperarConecxao();
        int idLivro = serviceLivro.idLivro(tituloLivro);

        if(idLivro == 0){
            throw new RegraDeNegocioException("Livro não encontrado!");
        }
        return new EmprestimoDAO(conn).consultarEmprestimo(idLivro);

    }

    private int idEmprestimo(int idLivro, int idCliente){
        Connection conn = connection.recuperarConecxao();
        return new EmprestimoDAO(conn).idEmprestimo(idLivro, idCliente);
    }

    //Lista todos os emprestimos de um cliente
    public Set<Emprestimo> emprestimoPorCliente(String cpf){
        Connection conn = connection.recuperarConecxao();
        int idCliente = serviceCliente.idCliente(cpf);

        if(idCliente == 0){
            throw new RegraDeNegocioException("Cliente não encontrado!");
        }

        return new EmprestimoDAO(conn).emprestimoPorCliente(idCliente);

    }

}
