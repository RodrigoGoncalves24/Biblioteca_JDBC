package com.rodrigo.biblioteca.DAO;

import com.rodrigo.biblioteca.DTO.EmprestimoResumo;
import com.rodrigo.biblioteca.Domain.Emprestimo;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class EmprestimoDAO {

    private Connection connection;
    private String sql;
    private PreparedStatement ps;

    public EmprestimoDAO(Connection conn) {
        this.connection = conn;
    }

    //Listar emprestimos
    public Set<EmprestimoResumo> listarEmprestimo() {
        Set<EmprestimoResumo> emprestimos = new HashSet<EmprestimoResumo>();
        int idEmprestimo = 0;
        String cliente;
        String livro;
        Date dataEmprestimo;
        Date dataDevolucao;
        boolean devolvido;

        sql = "Select e.id_emprestimo, c.nome_cliente, l.titulo_livro, e.data_emprestimo, e.data_devolucao_emprestimo, e.devolvido_emprestimo " +
                "from emprestimo e " +
                "join cliente c on e.id_usu_emprestimo = c.id_cliente " +
                "join livro l on e.id_livro_emprestimo = l.id_livro";

        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idEmprestimo = rs.getInt(1);
                cliente = rs.getString(2);
                livro = rs.getString(3);
                dataEmprestimo = rs.getDate(4);
                dataDevolucao = rs.getDate(5);
                devolvido = rs.getBoolean(6);


                emprestimos.add(new EmprestimoResumo(idEmprestimo, cliente, livro, dataEmprestimo, dataDevolucao, simToNao(devolvido)));
            }
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        return emprestimos;
    }

    //Adicionar um emprestimo
    public void realizarEmprestimo(int idCliente, int idLivro, Date emprestimo, Date dataDevolucao) {
        sql = "insert into emprestimo(id_usu_emprestimo, id_livro_emprestimo,data_emprestimo, data_devolucao_emprestimo, devolvido_emprestimo)"
                + "values(?,?,?,?,?)";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.setInt(2, idLivro);
            ps.setDate(3, emprestimo);
            ps.setDate(4, dataDevolucao);
            ps.setBoolean(5, false);

            ps.execute();

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Atualizar emprestimo - visando tornar mais rapido, pesquisar pelo id antes de autualizar/excluir
    public void atualizaEmprestimo(int idEmprestimo, Date dataDevolucao) {
        sql = "Update emprestimo set devolvido_emprestimo = ?, data_devolucao_emprestimo = ? " +
                "where id_emprestimo = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setDate(2, dataDevolucao);
            ps.setInt(3, idEmprestimo);

            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Buscar emprestimo
    public int idEmprestimo(int idLivro, int idCliente) {
        int idEmprestimo = 0;
        sql = "Select id_emprestimo from emprestimo e " +
                "join cliente c on e.id_usu_emprestimo =  c.id_cliente " +
                "join livro l on e.id_livro_emprestimo = l.id_livro " +
                "where c.id_cliente = ? and e.id_livro_emprestimo = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.setInt(2, idLivro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) idEmprestimo = rs.getInt(1);

            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return idEmprestimo;
    }


    //Para evitar repetição de código, métodos que passam a condição para a classe de consultaEmprestimo, visando consultar por cliente ou por livro
    public Set<Emprestimo> consultaEmprestimoLivro(int idLivro){
        return consultaEmprestimo("livro", idLivro);
    }

    public Set<Emprestimo> consultaEmprestimoCliente(int idCliente){
        return consultaEmprestimo("cliente", idCliente);
    }

    private Set<Emprestimo> consultaEmprestimo(String tipo, int id){
        Set<Emprestimo> emprestimos = new HashSet<>();

        sql = "Select c.nome_cliente, l.titulo_livro, e.data_emprestimo, e.data_devolucao_emprestimo, e.devolvido_emprestimo" +
                " from emprestimo e " +
                "join Livro l on l.id_livro = e.id_livro_emprestimo " +
                "join Cliente c on c.id_cliente = e.id_usu_emprestimo ";
        // Evitando duplicidade do códigos
        if(tipo.equals("cliente")){
             sql = sql + "where e.id_cliente = ?";
        }else if(tipo.equals("livro")){
            sql = sql + "where e.id_livro_emprestimo = ?";
        }


        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cliente = rs.getString(1);
                String livro = rs.getString(2);
                Date dataEmprestimo = rs.getDate(3);
                Date dataDevolucao = rs.getDate(4);
                boolean devolvido = rs.getBoolean(5);

                emprestimos.add(new Emprestimo(cliente, livro, dataEmprestimo, dataDevolucao, devolvido));
            }

            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return emprestimos;


    }

    private String simToNao(boolean dev){
        return dev ? "Devolvido" : "Não devolvido";
    }


}
