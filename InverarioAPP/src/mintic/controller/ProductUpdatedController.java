package mintic.controller;

import mintic.model.Product;
import mintic.model.ProductDAO;
import mintic.utils.Utils;
import mintic.view.MainWindow;
import mintic.view.UpdatedWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductUpdatedController implements ActionListener  {

    UpdatedWindow view;
    MainWindow mainWindow;
    private ProductDAO modelProduct;
    private Product product;
    private Long codeID;
    private int resultQuery;
    boolean error = false;

    public ProductUpdatedController(MainWindow mainView, UpdatedWindow view, ProductDAO modelProduct) {
        this.view = view;
        this.mainWindow = mainView;
        this.modelProduct = modelProduct;
        addEvents();
        try {
            Long idProduct = Utils.checkExist(mainWindow.getTblProducts());
        } catch (ArrayIndexOutOfBoundsException | ClassCastException e) {
            error = true;
            //e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Error con el producto", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
        if (error) {
            return;
        }
        initComponents();
        // Utils.productsAll(mainView.getTblProducts(), mainView, modelProduct);
    }

    // eventos de los botones
    private void addEvents() {
        view.getBtnUpdate().addActionListener(this);
    }

    private void setTextFieldsUpdate() {
        codeID = (Long) mainWindow.getTblProducts().getModel().getValueAt(mainWindow.getTblProducts().getSelectedRow(), 0);
        String nameProduct = String.valueOf(mainWindow.getTblProducts().getModel().getValueAt(mainWindow.getTblProducts().getSelectedRow(), 1));
        String priceProduct = String.valueOf(mainWindow.getTblProducts().getModel().getValueAt(mainWindow.getTblProducts().getSelectedRow(), 2));
        String stockProduct = String.valueOf(mainWindow.getTblProducts().getModel().getValueAt(mainWindow.getTblProducts().getSelectedRow(), 3));

        view.getTxtName().setText(nameProduct);
        view.getTxtPrice().setText(priceProduct);
        view.getTxtStock().setText(stockProduct);
    }

    private boolean updatedProduct() {
        String nameProduct = view.getTxtName().getText().trim();
        String priceProduct = view.getTxtPrice().getText().trim();
        String stockProduct = view.getTxtStock().getText().trim();

        boolean result = Utils.validateInputs(nameProduct, priceProduct, stockProduct);
        if (!result) { return false; }

        try {
            // Obtenemos el producto del DB y creamos su instancia
            ResultSet resultSet = modelProduct.getProduct(codeID);
            if (resultSet.next()) {
                product = Product.createProduct(
                    resultSet.getLong("codigo"),
                    resultSet.getString("nombre"),
                    resultSet.getFloat("precio"),
                    resultSet.getInt("inventario")
                );
            }
            // ajustamos los cambios
            product.setName(nameProduct);
            product.setPrice(Float.parseFloat(priceProduct));
            product.setStock(Integer.parseInt(stockProduct));
            // actualizamos en la base de datos
            resultQuery = modelProduct.updateProduct(product);

        } catch (SQLException | ClassNotFoundException e ) {
            // e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Error con el producto", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                ProductDAO.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }

        view.getTxtName().setText("");
        view.getTxtPrice().setText("");
        view.getTxtStock().setText("");

        view.closeWindow();
        return resultQuery >= 1;
    }

    private void initComponents() {
        setTextFieldsUpdate();
        this.view.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getBtnUpdate()) {
            if (updatedProduct()) {
                Utils.printTable(mainWindow.getTblProducts(), mainWindow, modelProduct);
                JOptionPane.showMessageDialog(
                        null,
                        "Producto actualizado correctamente",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
}
