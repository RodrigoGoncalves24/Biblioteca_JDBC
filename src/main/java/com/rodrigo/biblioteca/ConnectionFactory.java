package com.rodrigo.biblioteca;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    // Para evitar da "new" em objetos, utilizamos o padrão factory que cuida desse problema - reduz o acoplamento e facilita manutenção no código
    public Connection recuperarConecxao() {
        try {
            return createDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/biblioteca");
        config.setUsername("root");
        config.setPassword("*****");
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }

}
