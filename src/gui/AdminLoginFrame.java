package gui;

import javax.swing.*;
import java.awt.*;
import dao.AdminDAO;

public class AdminLoginFrame extends JFrame {

    public AdminLoginFrame() {

        setTitle("Admin Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ======= BACKGROUND IMAGE PANEL =======
        Image bgImage = new ImageIcon(getClass().getResource("admin_bg.png")).getImage();

        JPanel bg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        bg.setLayout(new GridBagLayout());
        setContentPane(bg);

        // ======= WHITE LOGIN BOX =======
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(380, 300));
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        box.setLayout(null);

        // Title
        JLabel title = new JLabel("ADMIN LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(100, 20, 180, 30);
        box.add(title);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(40, 70, 120, 20);
        JTextField userField = new JTextField();
        userField.setBounds(40, 90, 300, 28);
        box.add(userLabel);
        box.add(userField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(40, 130, 120, 20);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(40, 150, 300, 28);
        box.add(passLabel);
        box.add(passField);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(40, 200, 300, 35);
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        box.add(loginBtn);

        bg.add(box);

        // ===== LOGIN ACTION =====
        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            AdminDAO dao = new AdminDAO();
            if (dao.validateAdmin(username, password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new AdminDashboardFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
        });

        setVisible(true);
    }
}
