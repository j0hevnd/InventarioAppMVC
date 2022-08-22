package mintic.config;

import mintic.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CrudProductMySql {
    ResultSet listAllProducts() throws SQLException, ClassNotFoundException;
    int createProduct(Product product) throws SQLException, ClassNotFoundException;
    int updateProduct(Product product) throws SQLException, ClassNotFoundException;
    int deleteProduct(Long code) throws SQLException, ClassNotFoundException;
}
