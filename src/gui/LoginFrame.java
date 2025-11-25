package gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("Choose Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);   // FULL SCREEN
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ========= BACKGROUND PANEL =========
        JPanel bg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 90, 150),
                        0, getHeight(), new Color(0, 180, 219)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bg.setLayout(new GridBagLayout()); 
        add(bg, BorderLayout.CENTER);

        // ========= WHITE BOX =========
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(420, 350));
        box.setBackground(Color.WHITE);
        box.setLayout(null);
        box.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // CENTER BOX TO SCREEN
        bg.add(box, new GridBagConstraints());

        // ========= TITLE =========
        JLabel title = new JLabel("Select Login Type", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(90, 20, 250, 35);
        box.add(title);

        // ========= Admin Login Button =========
        JButton adminBtn = new JButton("Admin Login");
        adminBtn.setBounds(85, 80, 250, 40);
        adminBtn.setBackground(new Color(0, 120, 215));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        adminBtn.addActionListener(e -> {
            new AdminLoginFrame();
            dispose();
        });
        box.add(adminBtn);

        // ========= User Login Button =========
        JButton userBtn = new JButton("User Login");
        userBtn.setBounds(85, 140, 250, 40);
        userBtn.setBackground(new Color(40, 167, 69));
        userBtn.setForeground(Color.WHITE);
        userBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        userBtn.addActionListener(e -> {
            new UserLoginFrame();
            dispose();
        });
        box.add(userBtn);

        // ========= Register Button =========
        JButton regBtn = new JButton("Register");
        regBtn.setBounds(85, 200, 250, 40);
        regBtn.setBackground(new Color(255, 193, 7));
        regBtn.setForeground(Color.BLACK);
        regBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        regBtn.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });
        box.add(regBtn);

        setVisible(true);
    }
}
