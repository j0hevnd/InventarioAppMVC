package mintic.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel jPanel, jPanelTbl, jPanelBtn;
    private final String lblUpdate = "Ingrese los nuevos valores";
    private JTextField txtName, txtPrice, txtStock;
    private JButton btnAdd, btnDelete, btnShowFrameUpdate, btnUpdate, btnReport;
    private DefaultTableModel tblModel = new DefaultTableModel(new String[] {"#", "Nombre", "Precio", "Inventario"}, 0);
    private JTable tblProducts = new JTable(tblModel);
    private JScrollPane scrollBar = new JScrollPane(tblProducts);

    public MainWindow() {
        int width = 800;
        int height = 670;
        this.setSize(width, height);
        this.setTitle("Reto 5: Inventario Productos");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        initComponents();
        this.setVisible(true);
    }

    // Metodos para la creación de los diseños
    private void addLabelMain() {
        JLabel lblWelcome = new JLabel("¡Bienvenido a la App de inventario!");
        lblWelcome.setBounds(10, 10, 800, 20);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("helvetica", Font.PLAIN, 20));
        this.add(lblWelcome);
    }

    private void addPanels() {
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(40, 80, 350, 200);
        btnAdd = new JButton("Agregar");

        int panelX = (getWidth() - jPanel.getWidth() - getInsets().left - getInsets().right) / 2; // centrar en eje x
        jPanel.setLocation(panelX, 70);
        String lblLegend = "Agregar un nuevo producto";
        jPanel.setBorder(BorderFactory.createTitledBorder(lblLegend));

        // panel para la tabla
        jPanelTbl = new JPanel();
        jPanelTbl.setLayout(null);
        jPanelTbl.setBounds(40,300,500, 200);
        int panelX2 = (getWidth() - jPanelTbl.getWidth() - getInsets().left - getInsets().right) / 2;
        jPanelTbl.setLocation(panelX2, 300);

        // panel para los botones
        jPanelBtn = new JPanel();
        jPanelBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 0));
        jPanelBtn.setBounds(40, 500, 500, 40);
        int panelX3 = (getWidth() - jPanelBtn.getWidth() - getInsets().left - getInsets().right) / 2;
        jPanelBtn.setLocation(panelX3, 520);

        // Añadimos los panales
        this.getContentPane().add(jPanel);
        this.getContentPane().add(jPanelTbl);
        this.getContentPane().add(jPanelBtn);
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

    private void addTextField() {
        txtName = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();

        txtName.setBounds(100, 35, 200, 30);
        txtPrice.setBounds(100, 75, 200, 30);
        txtStock.setBounds(100, 115, 200, 30);
        btnAdd.setBounds(110, 160, 150, 30);

        jPanel.add(txtName);
        jPanel.add(txtPrice);
        jPanel.add(txtStock);
        jPanel.add(btnAdd);
    }

    private void tblProducts() {
        // mostramos la tabla
        scrollBar.setBounds(0, 0, jPanelTbl.getWidth(), jPanelTbl.getHeight());
        scrollBar.setVisible(true);
        jPanelTbl.add(scrollBar);
    }

    private void btnCRUD() {
        btnDelete = new JButton("Borrar Producto");
        btnShowFrameUpdate= new JButton("Actualizar Producto");
        btnReport = new JButton("Informes");

        jPanelBtn.add(btnDelete);
        jPanelBtn.add(btnShowFrameUpdate);
        jPanelBtn.add(btnReport);
    }

    // Métodos get y set para las vistas
    public JTextField getTxtName() { return txtName; }

    public void setTxtName(JTextField txtName) { this.txtName = txtName; }

    public JTextField getTxtPrice() { return txtPrice; }

    public void setTxtPrice(JTextField txtPrice) { this.txtPrice = txtPrice; }

    public JTextField getTxtStock() { return txtStock; }

    public void setTxtStock(JTextField txtStock) { this.txtStock = txtStock; }

    public JButton getBtnAdd() { return btnAdd; }

    public void setBtnAdd(JButton btnAdd) { this.btnAdd = btnAdd; }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JButton getBtnShowFrameUpdate() {
        return btnShowFrameUpdate;
    }

    public void setBtnShowFrameUpdate(JButton btnShowFrameUpdate) {
        this.btnShowFrameUpdate = btnShowFrameUpdate;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JButton getBtnReport() {
        return btnReport;
    }

    public void setBtnReport(JButton btnReport) {
        this.btnReport = btnReport;
    }

    public DefaultTableModel getTblModel() {
        return tblModel;
    }

    public void setTblModel(DefaultTableModel tblModel) {
        this.tblModel = tblModel;
    }

    public JTable getTblProducts() {
        return tblProducts;
    }

    public void setTblProducts(JTable tblProducts) {
        this.tblProducts = tblProducts;
    }

    public JScrollPane getScrollBar() {
        return scrollBar;
    }

    public void setScrollBar(JScrollPane scrollBar) {
        this.scrollBar = scrollBar;
    }

    private void initComponents() {
        addLabelMain();
        addPanels();
        addLabels();
        addTextField();
        tblProducts();
        btnCRUD();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
