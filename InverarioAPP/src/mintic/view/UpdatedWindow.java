package mintic.view;

import javax.swing.*;

public class UpdatedWindow extends JFrame {
    private JPanel jPanel;
    private JTextField txtName, txtPrice, txtStock;
    private JButton btnUpdate;

    private String nameProduct;
    private String priceProduct;
    private String stockProduct;

    public UpdatedWindow() {
        this.setSize(380, 300);
        this.setTitle("Actualizar Producto");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        initComponents();
        // this.setVisible(true);
    }

    private void addPanel() {
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(10,10,340, 240);
        jPanel.setBorder(BorderFactory.createTitledBorder("Actualizar producto"));

        this.getContentPane().add(jPanel);
    }

    private void addLabels() {
        JLabel lblName = new JLabel("Nombre");
        lblName.setBounds(30, 40, 50, 10);

        JLabel lblPrice = new JLabel("Precio");
        lblPrice.setBounds(30, 80, 50, 10);

        JLabel lblStock = new JLabel("Inventario");
        lblStock.setBounds(30, 120, 70, 10);

        jPanel.add(lblName);
        jPanel.add(lblPrice);
        jPanel.add(lblStock);
    }

    private void addTextFields() {
        txtName = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();

        txtName.setBounds(100, 35, 200, 30);
        txtPrice.setBounds(100, 75, 200, 30);
        txtStock.setBounds(100, 115, 200, 30);

        btnUpdate = new JButton("Actualizar");
        btnUpdate.setBounds(95, 170, 170, 30);
        jPanel.add(txtName);
        jPanel.add(txtPrice);
        jPanel.add(txtStock);
        jPanel.add(btnUpdate);
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JTextField getTxtPrice() {
        return txtPrice;
    }

    public void setTxtPrice(JTextField txtPrice) {
        this.txtPrice = txtPrice;
    }

    public JTextField getTxtStock() {
        return txtStock;
    }

    public void setTxtStock(JTextField txtStock) {
        this.txtStock = txtStock;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public void closeWindow() {
        this.setVisible(false);
    }

    private void initComponents() {
        addPanel();
        addLabels();
        addTextFields();

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
