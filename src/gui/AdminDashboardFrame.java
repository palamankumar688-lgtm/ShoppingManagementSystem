package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.DBConnection;

public class AdminDashboardFrame extends JFrame {

    private JLabel totalProductsLabel;
    private JLabel totalUsersLabel;
    private JLabel totalOrdersLabel;

    public AdminDashboardFrame() {

        setTitle("Admin Dashboard - Shopping Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        loadStats();

        setVisible(true);
    }

    // ================== UI BANANA ==================
    private void initUI() {

        // MAIN ROOT PANEL
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(242, 246, 252));
        setContentPane(root);

        // ======= LEFT SIDEBAR =======
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(new Color(25, 32, 52));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel appTitle = new JLabel("Admin Panel");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 10));

        JLabel appSub = new JLabel("Shopping System");
        appSub.setForeground(new Color(170, 190, 230));
        appSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        appSub.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 10));

        sidebar.add(appTitle);
        sidebar.add(appSub);
        sidebar.add(Box.createVerticalStrut(10));

        // Sidebar buttons
        JButton btnDashboard = createSideButton("Dashboard");
        btnDashboard.setEnabled(false); // current screen

        JButton btnProducts = createSideButton("View Products");
        JButton btnAddProduct = createSideButton("Add Product");
        JButton btnUsers = createSideButton("View Users");
        JButton btnOrders = createSideButton("View Orders");
        JButton btnLogout = createSideButton("Logout");

        sidebar.add(btnDashboard);
        sidebar.add(btnProducts);
        sidebar.add(btnAddProduct);
        sidebar.add(btnUsers);
        sidebar.add(btnOrders);

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(20));

        root.add(sidebar, BorderLayout.WEST);

        // ======= TOP HEADER =======
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitle = new JLabel("Overview of your shopping system");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(110, 110, 110));

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(title);
        titleBox.add(subtitle);

        JButton refreshBtn = new JButton("Refresh Stats");
        refreshBtn.addActionListener(e -> loadStats());

        header.add(titleBox, BorderLayout.WEST);
        header.add(refreshBtn, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // ======= CENTER CONTENT (STATS CARDS) =======
        JPanel center = new JPanel();
        center.setBackground(new Color(242, 246, 252));
        center.setLayout(new GridBagLayout());
        root.add(center, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Cards
        JPanel productsCard = createStatCard("Total Products");
        totalProductsLabel = createStatValueLabel();
        productsCard.add(totalProductsLabel, BorderLayout.CENTER);

        JPanel usersCard = createStatCard("Total Users");
        totalUsersLabel = createStatValueLabel();
        usersCard.add(totalUsersLabel, BorderLayout.CENTER);

        JPanel ordersCard = createStatCard("Total Orders");
        totalOrdersLabel = createStatValueLabel();
        totalOrdersLabel.setText("N/A"); // abhi orders table nahi hai
        ordersCard.add(totalOrdersLabel, BorderLayout.CENTER);

        gbc.gridx = 0; gbc.gridy = 0;
        center.add(productsCard, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        center.add(usersCard, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        center.add(ordersCard, gbc);

        // ======= SIDEBAR BUTTON ACTIONS =======
        btnProducts.addActionListener(e -> new ProductListFrame(true));

        btnAddProduct.addActionListener(e -> new AddProductFrame());

        btnUsers.addActionListener(e -> new UserListFrame());

        btnOrders.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Orders module abhi TODO hai.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        btnLogout.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
    }

    // ================== HELPERS ==================

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

    private JPanel createStatCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(220, 130));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        t.setForeground(new Color(90, 90, 90));

        card.add(t, BorderLayout.NORTH);
        return card;
    }

    private JLabel createStatValueLabel() {
        JLabel l = new JLabel("0", SwingConstants.LEFT);
        l.setFont(new Font("Segoe UI", Font.BOLD, 28));
        l.setForeground(new Color(40, 120, 210));
        return l;
    }

    // ================== LOAD STATS FROM DB ==================
    private void loadStats() {
        int productCount = 0;
        int userCount = 0;

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                // Count products
                PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) AS total FROM products");
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    productCount = rs1.getInt("total");
                }

                // Count users
                PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) AS total FROM users");
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    userCount = rs2.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        totalProductsLabel.setText(String.valueOf(productCount));
        totalUsersLabel.setText(String.valueOf(userCount));
        // totalOrdersLabel abhi "N/A" hi rehne do
    }
}
