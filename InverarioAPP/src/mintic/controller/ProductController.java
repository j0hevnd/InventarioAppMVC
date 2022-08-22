package mintic.controller;

import mintic.model.ProductDAO;
import mintic.model.Product;
import mintic.view.MainWindow;
import mintic.utils.Utils;
import mintic.view.UpdatedWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductController implements ActionListener {
    private ProductDAO modelProduct;
    private final MainWindow view;
    String msg_success;
    String msg_error;
    int resultQuery;

    // constructor
    public ProductController(MainWindow view) {
        this.view = view;
        this.modelProduct = new ProductDAO();
        addEvents();
        Utils.printTable(view.getTblProducts(), view, modelProduct);
    }

    // eventos de los botones
    private void addEvents() {
        view.getBtnAdd().addActionListener(this);
        view.getBtnDelete().addActionListener(this);
        view.getBtnShowFrameUpdate().addActionListener(this);
        view.getBtnReport().addActionListener(this);
    }

    // Agregar un producto a la base de datos
    public boolean addProduct() {
        String nameProduct = view.getTxtName().getText().trim();
        String priceProduct = view.getTxtPrice().getText().trim();
        String stockProduct = view.getTxtStock().getText().trim();

        boolean result = Utils.validateInputs(nameProduct, priceProduct, stockProduct);
        if (!result) { return false; }

        Product product = Product.createProduct(
                view.getTxtName().getText(),
                Float.parseFloat(view.getTxtPrice().getText()),
                Integer.parseInt(view.getTxtStock().getText())
        );

        try {
            resultQuery = modelProduct.createProduct(product);
        } catch (SQLException | ClassNotFoundException e) {
            //e.printStackTrace(System.out);
            msg_error = "Error al agregar el producto, intente de nuevo mas tarde";
            JOptionPane.showMessageDialog(null, this.msg_error, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }

        // Ajustamos los valores de las casillas a nada
        view.getTxtName().setText("");
        view.getTxtPrice().setText("");
        view.getTxtStock().setText("");

        return resultQuery >= 1;
    }

    // Eliminar un producto de la base de datos
    public boolean deleteProduct(){
        try {
            Long idProduct = Utils.checkExist(view.getTblProducts());
            resultQuery = modelProduct.deleteProduct(idProduct);

        } catch (Exception e) {
            //e.printStackTrace(System.out);
            msg_error = "Error al eliminar, intente de nuevo mas tarde";
            JOptionPane.showMessageDialog(null, this.msg_error, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
        return resultQuery >= 1;
    }

    // actualizar un producto
    private boolean updateProduct() {
        Long idProduct = (Long) view.getTblProducts().getModel().getValueAt(view.getTblProducts().getSelectedRow(), 0);
        String nameProduct = String.valueOf(view.getTblProducts().getModel().getValueAt(view.getTblProducts().getSelectedRow(), 1));
        String priceProduct = String.valueOf(view.getTblProducts().getModel().getValueAt(view.getTblProducts().getSelectedRow(), 2));
        String stockProduct = String.valueOf(view.getTblProducts().getModel().getValueAt(view.getTblProducts().getSelectedRow(), 3));

        view.getTxtName().setText(nameProduct);
        view.getTxtPrice().setText(priceProduct);
        view.getTxtStock().setText(stockProduct);
        return false;
    }

    // Generar el informe
    // Valor total del inventario
    private float totalInventory() {
        Float totalValue = 0f;
        try {
            ResultSet result = modelProduct.listAllProducts();
            while (result.next()) {
                totalValue += result.getFloat("precio") * result.getInt("inventario");
            }
        } catch (SQLException | ClassNotFoundException e) {
            //e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Error con el producto", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
        return totalValue;
    }

    // obtener el promedio de los precios de los productos
    private Float getAvgPrice() {
        float priceTotal = 0f, precioMean = 0f;
        int countProducts = 0;
        try {
            ResultSet result = modelProduct.listAllProducts();
            while (result.next()) {
                priceTotal += result.getFloat("precio");
                countProducts++;
            }
            precioMean = priceTotal / countProducts;

        } catch (SQLException | ClassNotFoundException e) {
            // e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Error con el producto", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
        return precioMean;
    }

    // Informe de productos
    public String productsReport() {
        float minValor = Float.MAX_VALUE, maxValor = Float.MIN_VALUE;
        String nameGrtProduct = "", nameLstProduct = "";

        try {
            ResultSet result = modelProduct.listAllProducts();
            while (result.next()) {
                // obtenemos el mayor
                if (result.getFloat("precio") > maxValor) {
                    maxValor = result.getFloat("precio");
                    nameGrtProduct = String.valueOf(result.getString("nombre"));
                }
                // obtenemos el menor
                if (result.getFloat("precio") < minValor) {
                    minValor = result.getFloat("precio");
                    nameLstProduct = String.valueOf(result.getString("nombre"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            // e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Error con el producto", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }

        String stringReport = "Producto precio mayor: "  + nameGrtProduct
                + "\nProduto precio menor: " + nameLstProduct
                + "\nPromedio precios: "     + String.format("%.1f", getAvgPrice())
                + "\nValor del inventario: " + totalInventory();
        return stringReport;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Agregar
        if (e.getSource() == view.getBtnAdd()){
            if (addProduct()) {
                Utils.printTable(view.getTblProducts(), view, modelProduct);
                msg_success = "Producto agregado correctamente.";
                JOptionPane.showMessageDialog(null, msg_success, "Mensaje información", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Actualizar
        if (e.getSource() == view.getBtnShowFrameUpdate()) {
            ProductUpdatedController productUpdatedController = new ProductUpdatedController(view, new UpdatedWindow(), modelProduct);
        }

        // Eliminar
        if (e.getSource() == view.getBtnDelete()) {
            if (deleteProduct()) {
                Utils.printTable(view.getTblProducts(), view, modelProduct);
                msg_success = "Producto eliminado.";
                JOptionPane.showMessageDialog(null, msg_success, "Mensaje información", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Informe
        if (e.getSource() == view.getBtnReport()) {
            JOptionPane.showMessageDialog(null, productsReport(), "Informe de inventario", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
