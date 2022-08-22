package mintic.utils;

import mintic.model.ProductDAO;
import mintic.view.MainWindow;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils {
    // private static final ProductDAO modelProduct = new ProductDAO();;

    // Listar los productos de la base de datos
    public static void printTable(JTable tblProducts, MainWindow view, ProductDAO modelProduct) {
        DefaultTableModel tblModel = (DefaultTableModel) tblProducts.getModel();
        tblModel.setRowCount(0); // Vuelve a iniciar la tabla en cero, por si tiene resutados anteriores.
        Object[] listProducts = new Object[4];

        try {
            ResultSet result = modelProduct.listAllProducts();

            while (result.next()) {
                listProducts[0] = result.getLong("codigo");
                listProducts[1] = result.getString("nombre");
                listProducts[2] = result.getFloat("precio");
                listProducts[3] = result.getInt("inventario");
                tblModel.addRow(listProducts);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
        view.setTblModel(tblModel);
    }

    // Valida campos de texto
    public static boolean validateInputs(String nameProduct, String priceProduct, String stockProduct) {
        String msg = "";
        if (nameProduct.equals("") || priceProduct.equals("") || stockProduct.equals("")) {
            msg = "Todos los campos deben de llenarse correctamente";
            JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!priceProduct.matches("^[0-9]+(\\.?[0-9])*$")) {
            msg = "Asegurate que el precio sea correo, solo numeros y punto con decimales.";
            JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!stockProduct.matches("^[\\d]+$")) {
            msg = "Asegurate que la cantidad solo sean números.";
            JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Validar que en la tabla se de click a un valor existente
    public static Long checkExist(JTable table) throws ClassCastException {

        // ClassCastException
        Long idProduct = (Long) table.getModel().getValueAt(table.getSelectedRow(), 0);
        //Exception
        if (idProduct == null) {
            throw new ClassCastException();
        }

        return idProduct;
    }
}
