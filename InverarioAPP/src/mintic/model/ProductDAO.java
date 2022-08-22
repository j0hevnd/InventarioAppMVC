package mintic.model;

import mintic.config.CrudProductMySql;
import mintic.config.Connect;

import java.sql.*;

public class ProductDAO implements CrudProductMySql {

    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    private static ResultSet result;
    private static int affectedRecords = 0;
    private static final String SQL_QUERY_LIST_ONE = "SELECT codigo, nombre, precio, inventario FROM productos WHERE codigo = ?";
    private static final String SQL_QUERY_LIST = "SELECT * FROM productos";
    private static final String SQL_QUERY_ADD  = "INSERT INTO productos(nombre, precio, inventario) VALUES (?,?,?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE productos SET nombre = ?, precio = ?, inventario = ? WHERE codigo = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM productos WHERE codigo = ?";

    public static void closeConnections() throws SQLException {
        if (pstmt != null) {
            Connect.close(pstmt);
        }
        Connect.close(result);
        Connect.close(stmt);
        Connect.close(conn);
    }

    public ResultSet getProduct(Long codeId) throws SQLException, ClassNotFoundException {
        conn = Connect.getConnection();
        pstmt = conn.prepareStatement(SQL_QUERY_LIST_ONE);
        pstmt.setLong(1, codeId);
        result = pstmt.executeQuery();
        return result;
    }

    @Override
    public ResultSet listAllProducts() throws SQLException, ClassNotFoundException {
        conn = Connect.getConnection();
        stmt = conn.createStatement();
        result = stmt.executeQuery(SQL_QUERY_LIST);
        return result;
    }

    @Override
    public int createProduct(Product product) throws SQLException, ClassNotFoundException {
        conn = Connect.getConnection();
        pstmt = conn.prepareStatement(SQL_QUERY_ADD);
        pstmt.setString(1, product.getName());
        pstmt.setFloat(2, product.getPrice());
        pstmt.setInt(3, product.getStock());

        affectedRecords = pstmt.executeUpdate();
        return affectedRecords;
    }

    @Override
    public int updateProduct(Product product) throws SQLException, ClassNotFoundException {
        // conn = Connect.getConnection();
        pstmt = conn.prepareStatement(SQL_QUERY_UPDATE);
        pstmt.setString(1, product.getName());
        pstmt.setFloat(2, product.getPrice());
        pstmt.setInt(3, product.getStock());
        pstmt.setLong(4, product.getCode());

        affectedRecords = pstmt.executeUpdate();
        return affectedRecords;
    }

    @Override
    public int deleteProduct(Long code) throws SQLException, ClassNotFoundException {
        conn = Connect.getConnection();
        pstmt = conn.prepareStatement(SQL_QUERY_DELETE);
        pstmt.setLong(1, code);

        affectedRecords = pstmt.executeUpdate();

        return affectedRecords;
    }
}
