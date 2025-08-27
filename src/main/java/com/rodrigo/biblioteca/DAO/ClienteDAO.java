package com.rodrigo.biblioteca.DAO;

import com.rodrigo.biblioteca.Domain.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ClienteDAO {
    private Connection connection;
    private PreparedStatement ps;
    private String sql;

    public ClienteDAO(Connection conn) {
        this.connection = conn;
    }

    //Listar clientes
    public Set<Cliente> listarCliente() {
        Set<Cliente> clientes = new HashSet<>();
        sql = "Select * from cliente";

        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Percorre todas as linhas que houver no banco
            while (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String email = rs.getString(3);
                String cpf = rs.getString(4);

                clientes.add(new Cliente(nome, email, cpf, id));
            }
            ps.close();
            rs.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return clientes;
    }

    //Cadastrar novo cliente
    public void cadastraCliente(String nome, String email, String cpf){
        sql = "insert into cliente (nome_cliente, cpf_cliente, email_cliente)"+"values(?,?,?)";

        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1,nome);
            ps.setString(2, cpf);
            ps.setString(3, email);

            ps.execute();
            ps.close();
            connection.close();
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    //Excluir cliente
    public void excluirCliente(String c){
        sql = "Delete from cliente where cpf = ?";

        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, c);
            ps.executeUpdate();
            ps.close();
            connection.close();
        }catch(SQLException e){
            System.out.println(e);
        }

    }

    // Inner com emprestimo para retornar se cliente esta com livro, se sim, pegar as duas datas, se não, apenas deixar vazio
        public Cliente buscarClinete(String cpfCliente){
        Cliente cliente = null;
        sql = "Select * from cliente where cpf_cliente = ?";

        // Chamar met da tabela de emprestimo para saber a situação do cliente

        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, cpfCliente);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String cpf =  rs.getString(4);
                String nome = rs.getString(2);
                String email = rs.getString(3);

                cliente = new Cliente(nome, email, cpf);
            }
            ps.close();
            rs.close();

        }catch(SQLException e){
            System.out.println(e);
        }
        return cliente;


    }

    //Retorna id do cliente para pesquisa no BANCO DE DADOS
    public int idCliente(String c){
        sql = "Select id from cliente where cpf = ? ";
        int idCliente = 0;
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, c);
            ResultSet rs = ps.executeQuery();
            idCliente = rs.getInt(1);

            ps.close();
            rs.close();
            connection.close();
        }catch(SQLException e){
            System.out.println(e);
        }

        return idCliente;
    }

}