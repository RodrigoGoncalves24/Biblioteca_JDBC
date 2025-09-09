package com.rodrigo.biblioteca.Service;

import com.rodrigo.biblioteca.ConnectionFactory;
import com.rodrigo.biblioteca.DAO.EmprestimoDAO;
import com.rodrigo.biblioteca.DTO.EmprestimoResumo;
import com.rodrigo.biblioteca.DTO.LivroResumo;
import com.rodrigo.biblioteca.Domain.Emprestimo;
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
    public Set<EmprestimoResumo> listarEmprestimos(){
        Connection conn = connection.recuperarConecxao();
        return new EmprestimoDAO(conn).listarEmprestimo();
    }

    //Realiza um novo emprestimo
    public void realizarEmprestimo(String cpf, String titulo, java.sql.Date emprestimo){
        Connection conn = connection.recuperarConecxao();

        LivroResumo l = serviceLivro.verificaLivro(titulo);
        if(l.isDisponivel().equalsIgnoreCase("Não")){
            throw new RegraDeNegocioException("O livro já esta emprestado no momento!");
        }

        int idLivro = serviceLivro.idLivro(titulo);
        int idCliente = serviceCliente.idCliente(cpf);

        serviceLivro.atualizaLivro(titulo, false); // Atualizando status do livro

        java.sql.Date dataDevolucao = java.sql.Date.valueOf(emprestimo.toLocalDate().plusMonths(1));
        new EmprestimoDAO(conn).realizarEmprestimo(idCliente, idLivro,  emprestimo, dataDevolucao);
        System.out.println("Empréstimo realizado com sucesso");

    }

    //Atualiza com a devolutiva do emprestimo
    public void atualizarEmprestimo(String cpf, String t){
        Connection conn = connection.recuperarConecxao();
        // Verificar se a devolução já não foi realizado para não sob escrever dados
        int idLivro = serviceLivro.idLivro(t);
        Set<Emprestimo> devolvido =  new EmprestimoDAO(conn).consultaEmprestimoLivro(idLivro);

        if(devolvido.stream().anyMatch(Emprestimo::getDevolvido)){
            throw new RegraDeNegocioException("Livro já devolvido!");
        }else{
            int idCliente = serviceCliente.idCliente(cpf);
            int idEmprestimo = idEmprestimo(idLivro, idCliente);

            serviceLivro.atualizaLivro(t,true); //Atualiza status do livro

            // Passar a data que a devolucao foi feita
            java.sql.Date dataDevolucao = java.sql.Date.valueOf(LocalDate.now());

            new EmprestimoDAO(conn).atualizaEmprestimo(idEmprestimo, dataDevolucao);
            System.out.println("Obrigado pela preferência!");
        }

    }

    //usado para não devolver o mesmo livro
    public Set<Emprestimo> consultarEmprestimo(String tituloLivro){
        Connection conn = connection.recuperarConecxao();
        int idLivro = serviceLivro.idLivro(tituloLivro);

        if(idLivro == 0){
            throw new RegraDeNegocioException("Livro não encontrado!");
        }
        return new EmprestimoDAO(conn).consultaEmprestimoLivro(idLivro);

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

        return new EmprestimoDAO(conn).consultaEmprestimoCliente(idCliente);

    }

}
