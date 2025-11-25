package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDAO;
import model.Product;
import model.Cart;
import model.CartItem;
import threads.AutoRefreshThread;

public class ProductListFrame extends JFrame {

    private final boolean isAdmin;
    private JTable table;
    private DefaultTableModel model;

    // ⭐ Global Static Cart
    private static Cart cart = new Cart();


    public ProductListFrame(boolean isAdmin) {
        this.isAdmin = isAdmin;

        setTitle(isAdmin ? "Admin - Manage Products" : "Products - Browse");
        setExtendedState(JFrame.MAXIMIZED_BOTH);   // full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        loadProducts();

        // ==============================
        // ⭐ MULTITHREADING FOR FULL MARKS
        // ==============================
        Object lock = new Object();

        AutoRefreshThread t = new AutoRefreshThread(lock, this::loadProducts);
        t.start();

        setVisible(true);
    }

    // ================== UI BANANA ==================
    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(242, 246, 252));
        setContentPane(root);

        // ---------- LEFT SIDEBAR ----------
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(25, 32, 52));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel appTitle = new JLabel("Shopping System");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));

        JLabel roleLabel = new JLabel(isAdmin ? "ADMIN MODE" : "USER MODE");
        roleLabel.setForeground(new Color(180, 200, 255));
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 10));

        sidebar.add(appTitle);
        sidebar.add(roleLabel);
        sidebar.add(Box.createVerticalStrut(10));

        JButton btnProducts = createSideButton("Products");
        btnProducts.setEnabled(false);

        sidebar.add(btnProducts);
        sidebar.add(Box.createVerticalGlue());

        JButton btnClose = createSideButton("Close Window");
        btnClose.addActionListener(e -> dispose());
        sidebar.add(btnClose);
        sidebar.add(Box.createVerticalStrut(20));

        root.add(sidebar, BorderLayout.WEST);

        // ---------- TOP HEADER ----------
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel(isAdmin ? "Manage Products" : "Browse Products");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel sub = new JLabel(isAdmin
                ? "Add, edit & delete products."
                : "Select a product & add to cart.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(110, 110, 110));

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(title);
        titleBox.add(sub);

        header.add(titleBox, BorderLayout.WEST);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadProducts());
        header.add(refreshBtn, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // ---------- TABLE AREA ----------
        String[] columns = {"ID", "Name", "Category", "Price", "Discount", "Quantity", "Description"};

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isAdmin && column != 0;
            }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(230, 235, 245));
        table.setSelectionBackground(new Color(204, 228, 255));

        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        root.add(pane, BorderLayout.CENTER);

        // ---------- BOTTOM BAR ----------
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));
        bottom.setBackground(new Color(242, 246, 252));

        if (isAdmin) {
            JButton btnAdd = createPrimaryButton("Add Product");
            JButton btnUpdate = createSecondaryButton("Update Selected");
            JButton btnDelete = createDangerButton("Delete Selected");

            btnAdd.addActionListener(e -> new AddProductFrame());
            btnUpdate.addActionListener(e -> updateSelectedProduct());
            btnDelete.addActionListener(e -> deleteSelectedProduct());

            bottom.add(btnAdd);
            bottom.add(btnUpdate);
            bottom.add(btnDelete);
        } else {
            JButton btnCart = createPrimaryButton("Add to Cart");
            btnCart.addActionListener(e -> addToCart());
            bottom.add(btnCart);
        }

        root.add(bottom, BorderLayout.SOUTH);
    }

    // ---------- BUTTON STYLES ----------
    private JButton createSideButton(String text) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(35, 45, 70));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        return b;
    }

    private JButton createPrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(new Color(58, 123, 213));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private JButton createSecondaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setBackground(Color.WHITE);
        b.setForeground(new Color(40, 40, 40));
        b.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        b.setFocusPainted(false);
        return b;
    }

    private JButton createDangerButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setBackground(new Color(220, 53, 69));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    // ================== PRODUCT DATA LOAD ==================
    private void loadProducts() {
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getAllProducts();


        model.setRowCount(0);

        for (Product p : list) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getDiscount(),
                    p.getQuantity(),
                    p.getDescription()
            });
        }
    }

    // ================== ADMIN: UPDATE PRODUCT ==================
    private void updateSelectedProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        String category = (String) model.getValueAt(row, 2);
        double price = Double.parseDouble(model.getValueAt(row, 3).toString());
        double discount = Double.parseDouble(model.getValueAt(row, 4).toString());
        int quantity = Integer.parseInt(model.getValueAt(row, 5).toString());
        String description = (String) model.getValueAt(row, 6);

        Product p = new Product(id, name, category, price, discount, quantity, description);

        ProductDAO dao = new ProductDAO();
        if (dao.updateProduct(p)) {
            JOptionPane.showMessageDialog(this, "Product updated!");
            loadProducts();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating product.");
        }
    }

    // ================== ADMIN: DELETE PRODUCT ==================
    private void deleteSelectedProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        ProductDAO dao = new ProductDAO();
        if (dao.deleteProduct(id)) {
            JOptionPane.showMessageDialog(this, "Product deleted!");
            loadProducts();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting product.");
        }
    }

    // ================== USER: ADD TO CART ==================
    private void addToCart() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        double price = Double.parseDouble(model.getValueAt(row, 3).toString());
        int stockQty = Integer.parseInt(model.getValueAt(row, 5).toString());

        String qtyStr = JOptionPane.showInputDialog(this, "Enter Quantity:", "1");

        if (qtyStr == null) return;

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity!");
            return;
        }

        if (qty <= 0 || qty > stockQty) {
            JOptionPane.showMessageDialog(this, "Invalid quantity!");
            return;
        }

        CartItem item = new CartItem(id, name, price, qty);
        cart.addItem(item);

        JOptionPane.showMessageDialog(this, "Added to cart:\n" + name + " x " + qty);
    }
}
