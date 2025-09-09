package com.rodrigo.biblioteca.Service;

import com.rodrigo.biblioteca.ConnectionFactory;
import com.rodrigo.biblioteca.DAO.ClienteDAO;
import com.rodrigo.biblioteca.DTO.ClienteResumo;
import com.rodrigo.biblioteca.Domain.Emprestimo;
import com.rodrigo.biblioteca.RegraDeNegocioException;

import java.sql.Connection;

import java.util.Set;


public class ClienteService {
    private ConnectionFactory connection;
    private EmprestimoService serviceEmprestimo = new EmprestimoService();

    public ClienteService() {
        this.connection = new ConnectionFactory();
    }

    public Set<ClienteResumo> listarCliente() {
        Connection conn = connection.recuperarConecxao();
        return new ClienteDAO(conn).listarCliente();
    }

    public void cadastrarCliente(String nome, String email, String cpf) {
        Connection conn = connection.recuperarConecxao();

        //Verifica se cliente já esta cadastrado
        if (clienteCadastrado(cpf)) {
            throw new RegraDeNegocioException("Cliente já cadastrado");
        }

        new ClienteDAO(conn).cadastraCliente(nome, email, cpf);
    }

    // Antes de exluir, verificar se há livros com o cliente ou se ele está ok para ser exlcuido
    public void excluirCliente(String cpf) {
        Connection conn = connection.recuperarConecxao();
        Set<Emprestimo> emprestimoPorCliente;
        emprestimoPorCliente = serviceEmprestimo.emprestimoPorCliente(cpf);

        if (!temEmprestimo(emprestimoPorCliente)) {
            throw new RegraDeNegocioException("O cliente informado possui livros em empréstimo");
        } else {
            new ClienteDAO(conn).excluirCliente(cpf);
        }

    }

    private boolean temEmprestimo(Set<Emprestimo> emprestimo) { // Verifica se na lista de emprestimo do cliente, há pelo menos um livro não devolvido
        boolean existeEmprestimo = emprestimo.stream().
                allMatch(Emprestimo::getDevolvido);

        return existeEmprestimo;
    }

    public int idCliente(String cpf){
        Connection conn = connection.recuperarConecxao();

        return new ClienteDAO(conn).idCliente(cpf);
    }

    private boolean clienteCadastrado(String cpf){
        Connection conn = connection.recuperarConecxao();

        ClienteResumo c = new ClienteDAO(conn).buscarCliente(cpf);

        return c != null;
    }

}
