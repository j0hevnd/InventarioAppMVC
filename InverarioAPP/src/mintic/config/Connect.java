package mintic.config;

import java.sql.*;

public class Connect {
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/reto5?serverTimezone=GMT-5";
    private final static String JDBC_USER = "root";
    private final static String JDBC_PASS = "admin12345"; // contraseña para MySql

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    public static void close(ResultSet result) throws SQLException {
        result.close();
    }

    public static void close(Statement stmt) throws SQLException {
        stmt.close();
    }

    public static void close(PreparedStatement stmt) throws SQLException {
        stmt.close();
    }

    public static void close(Connection conn) throws SQLException {
        conn.close();
    }
}
