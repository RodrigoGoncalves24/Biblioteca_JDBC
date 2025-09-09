package com.rodrigo.biblioteca.DAO;

import com.rodrigo.biblioteca.DTO.LivroResumo;
import com.rodrigo.biblioteca.Domain.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class LivroDAO {
    private Connection connection;
    private PreparedStatement ps;
    private String sql;

    public LivroDAO(Connection con) {
        this.connection = con;
    }

    //Listar todos os livros
    public Set<LivroResumo> listaLivros() {
        Set<LivroResumo> livros = new HashSet<LivroResumo>();
        sql = "select * from livro";
        String titulo;
        String autor;
        String categoria;
        boolean disponivel;

        try {
            ps = connection.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                titulo = resultado.getString(2);
                categoria = resultado.getString(3);
                autor = resultado.getString(4);
                disponivel = resultado.getBoolean(5);

                livros.add(new LivroResumo(titulo, autor, categoria, toSimNao(disponivel)));
            }
            ps.close();
            resultado.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return livros;
    }

    //Adiciona um novo livro
    public void adicionaLivro(String titulo, String autor, String categoria) {

        // Id é auto increment, não precisa passar valor
        sql = "INSERT INTO Livro (titulo_livro, categoria_livro, autor_livro, disp_livro) " + "values (?,?,?,?)";

        try {
            ps = connection.prepareStatement(sql);

            ps.setString(1,titulo.toUpperCase());
            ps.setString(2,categoria);
            ps.setString(3,autor);
            ps.setBoolean(4, true);

            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Verificar status do livro
    public LivroResumo verificaLivro(String tituloLivro){
        sql = "Select  * from livro where upper(titulo_livro) = upper(?)";
        LivroResumo l = null;
        String titulo = "";
        String categoria = "";
        String autor = " ";
        boolean disponivel = false;

        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, tituloLivro);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                 titulo = rs.getString(2);
                 categoria = rs.getString(3);
                 autor = rs.getString(4);
                 disponivel = rs.getBoolean(5);
            }

               l = new LivroResumo(titulo, categoria, autor, toSimNao(disponivel));
           ps.close();
           rs.close();
           connection.close();


        }catch(SQLException e){
            System.out.println(e);
        }
        return l;
    }

    public void excluirLivro(String titulo) {
        sql = "Delete from livro where titulo_livro = ?";

        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.execute();
            ps.close();
            connection.close();
        }catch(SQLException e){
            System.out.println(e);
        }

    }

    public int idLivro(String tituloLivro){
        sql = "select id_livro from livro where upper(titulo_livro) = upper(?) ";
        int idLivro = 0;

        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, tituloLivro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idLivro = rs.getInt(1);
            }
            connection.close();
            ps.close();
        }catch(SQLException e){
            System.out.println(e);
        }

        return idLivro;
    }

    public Livro buscaLivro(String titulo){
        Livro livro = null;
        sql = "Select * from livro where upper(titulo_livro) = upper(?)";
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String tituloLivro = rs.getString(2);
                String categoria = rs.getString(3);
                String autor = rs.getString(4);
                boolean disponivel = rs.getBoolean(5);
                livro = new Livro(tituloLivro, categoria, autor, disponivel, id);
            }
            connection.close();
            ps.close();
        }catch(SQLException e){
            System.out.println(e);
        }
        return livro;
    }

    public void atualizaLivro(String titulo, boolean disponibilidade){
        sql = "update livro set disp_livro = ? where upper(titulo_livro) = upper(?)";

        try{
            ps = connection.prepareStatement(sql);
            ps.setBoolean(1, disponibilidade);
            ps.setString(2, titulo);
            ps.execute();

            ps.close();
            connection.close();
        }catch(SQLException e){
            System.out.println(e);
        }


    }

    // Usado para retornar se o livro esta disponivel ou não
    private String toSimNao(boolean disp){
        return disp? "Sim": "Não";
    }



}
