package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import dao.UserDAO;

public class UserLoginFrame extends JFrame {

    public UserLoginFrame() {

        setTitle("User Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ======== BACKGROUND IMAGE PANEL ========
        JPanel bg = new JPanel() {

            Image bgImg = new ImageIcon(
                getClass().getResource("mntdataUGC_JFPCXQsAHvpu.png")
            ).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), null);
            }
        };

        bg.setBounds(0, 0, 1920, 1080);
        bg.setLayout(null);

        // ======== LOGIN BOX ========
        JPanel panel = new JPanel();
        panel.setSize(350, 300);
        panel.setBackground(new Color(255, 255, 255, 220)); // slight transparency
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

        JLabel title = new JLabel("USER LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(90, 15, 200, 30);

        JLabel uidLabel = new JLabel("User ID:");
        uidLabel.setBounds(40, 70, 200, 20);

        JTextField uidField = new JTextField();
        uidField.setBounds(40, 95, 260, 28);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(40, 135, 200, 20);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(40, 160, 260, 28);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(40, 210, 260, 35);
        loginBtn.setBackground(new Color(58, 123, 213));
        loginBtn.setForeground(Color.white);

        loginBtn.addActionListener(e -> {
            String uid = uidField.getText();
            String pass = new String(passField.getPassword());

            UserDAO dao = new UserDAO();

            if (dao.validateUser(uid, pass)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new ProductListFrame(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID or Password!");
            }
        });

        panel.add(title);
        panel.add(uidLabel);
        panel.add(uidField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);

        bg.add(panel);
        add(bg);

        // ======== AUTO-CENTER THE LOGIN BOX ========
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.setLocation(
                        (getWidth() - panel.getWidth()) / 2,
                        (getHeight() - panel.getHeight()) / 2
                );
            }
        });

        setVisible(true);
    }
}
