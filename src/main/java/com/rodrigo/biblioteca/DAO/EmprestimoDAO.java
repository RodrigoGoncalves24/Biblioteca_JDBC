package com.rodrigo.biblioteca.DAO;

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
    public Set<Emprestimo> listarEmprestimo() {
        Set<Emprestimo> emprestimos = new HashSet<Emprestimo>();
        String cliente = "";
        String livro = "";
        Date dataEmprestimo = null;
        Date dataDevolucao = null;
        boolean devolvido = false;

        sql = "Select c.nome_cliente, l.titulo_livro, e.data_emprestimo, e.data_devolucao_emprestimo, e.devolvido_emprestimo " +
                "from emprestimo e " +
                "join cliente c on e.id_usu_emprestimo = c.id_cliente " +
                "join livro l on e.id_livro_emprestimo = l.id_livro";

        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente = rs.getString(1);
                livro = rs.getString(2);
                dataEmprestimo = rs.getDate(3);
                dataDevolucao = rs.getDate(4);
                devolvido = rs.getBoolean(5);


                emprestimos.add(new Emprestimo(cliente, livro, dataDevolucao, dataEmprestimo, devolvido));
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
    public void realizarEmprestimo(int idCliente, int idLivro, Date dataEmprestimo, Date dataDevolucao) {
        sql = "insert into emprestimo(id_usu_emprestimo, id_livro_emprestimo,data_emprestimo, data_devolucao_emprestimo, devolvido_emprestimo)"
                + "values(?,?,?,?,?)";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.setInt(2, idLivro);
            ps.setDate(3, dataEmprestimo);
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
            System.out.println("id emprestimo: "+idEmprestimo);
            System.out.println("Data: "+dataDevolucao);

            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Buscar emprestimo
    public int idEmprestimo(int idCliente, int idLivro) {
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

            if (rs.next()) {
                idEmprestimo = rs.getInt(1);
            }

            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return idEmprestimo;
    }

    //Consultar emprestimo por livro
    public boolean consultarEmprestimo(int idLivro) {
        boolean devolvido = false;
        sql = "Select e.devolvido_emprestimo from emprestimo where id_livro_emprestimo = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idLivro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                devolvido = rs.getBoolean(1);
            }

            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return devolvido;

    }

    //Retorna os emprestimos realizados por um cliente
    public Set<Emprestimo> emprestimoPorCliente(int idCliente) {
        Set<Emprestimo> emprestimoPorCliente = new HashSet<>();
        sql = "Select c.nome_cliente, l.nome_livro, e.data_emprestimo, e.data_devolucao_emprestimo, e.devolvido_emprestimo from emprestimo e " +
                "join Livro l l.id_livro = e.id_livro_emprestimo " +
                "join Cliente c c.id_cliente = e.id_cliente_emprestimo " +
                "where c.cliente_id = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String cliente = rs.getString(1);
                String livro = rs.getString(2);
                Date dataEmprestimo = rs.getDate(3);
                Date dataDevolucao = rs.getDate(4);
                boolean devolvido = rs.getBoolean(5);

                emprestimoPorCliente.add(new Emprestimo(cliente, livro, dataEmprestimo, dataDevolucao, devolvido));
            }

            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return emprestimoPorCliente;
    }
}
