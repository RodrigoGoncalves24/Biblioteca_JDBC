package com.rodrigo.biblioteca.Service;

import com.rodrigo.biblioteca.ConnectionFactory;
import com.rodrigo.biblioteca.DAO.ClienteDAO;
import com.rodrigo.biblioteca.Domain.Cliente;
import com.rodrigo.biblioteca.Domain.Emprestimo;
import com.rodrigo.biblioteca.RegraDeNegocioException;

import java.sql.Connection;
import java.util.Set;


public class ClienteService {
    // Regras de negócio do usuário
    private ConnectionFactory connection;
    private EmprestimoService serviceEmprestimo;

    public ClienteService() {
        this.connection = new ConnectionFactory();
    }

    public Set<Cliente> listarCliente() {
        Connection conn = connection.recuperarConecxao();
        return new ClienteDAO(conn).listarCliente();
    }

    public void cadastrarCliente(String nome, String email, String cpf) {
        Connection conn = connection.recuperarConecxao();

        if (nome.contains("delete") || email.contains("delete")) {
            throw new RegraDeNegocioException("Aqui não tico tico");
        }
        //Verifica se cpf é valido
//        if(cpf.length() != 11){
//            throw new RegraDeNegocioException("CPF inválido! Por favor, verifique e tente novamente!");
//        }

        Cliente c = new ClienteDAO(conn).buscarClinete(cpf);

        //Verifica se já não existe esse cliente na base
        if (c != null) {
            throw new RegraDeNegocioException("Cliente já cadastrado");
        }

        conn = connection.recuperarConecxao(); // Péssimo prática, ajustar posteriormente
        new ClienteDAO(conn).cadastraCliente(nome, email, cpf);


    }

    // Antes de exluir, verificar se há livros com o cliente ou se ele está ok para ser exlcuido
    public void excluirCliente(String cpf) {
        Connection conn = connection.recuperarConecxao();
        Set<Emprestimo> emprestimoPorCliente = serviceEmprestimo.emprestimoPorCliente(cpf);

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

        if(cpf.contains("delete") || cpf.contains("null")){
            throw new RegraDeNegocioException("Aqui não tico tico");
        }

        return new ClienteDAO(conn).idCliente(cpf);
    }

}
