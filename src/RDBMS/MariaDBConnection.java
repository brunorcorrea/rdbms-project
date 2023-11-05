package RDBMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MariaDBConnection {
    public static String USERNAME;
    public static String PASSWORD;

    private static final String JDBC_URL = "jdbc:mariadb://143.106.243.64:3306/SI400";

    private Connection connection = null;

    public Connection getConnection() {
        return (connection);
    }

    public MariaDBConnection() {
        super();

        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } else {
                System.out.println("Conexão com o banco de dados falhou!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao se conectar com o banco: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão com o banco: " + e.getMessage());
        }
    }
}
