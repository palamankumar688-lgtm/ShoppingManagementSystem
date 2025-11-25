package gui;

import javax.swing.*;
import java.awt.*;
import dao.UserDAO_Register;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {

        setTitle("Create New Account");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= IMAGE BACKGROUND ===================
        ImageIcon bgImage = new ImageIcon(getClass().getResource("reg_bg.png"));
        Image image = bgImage.getImage();

        JPanel bg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        bg.setLayout(new GridBagLayout());
        add(bg);

        // ================= WHITE BOX ===================
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(420, 580));
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        box.setLayout(null);

        bg.add(box, new GridBagConstraints());

        JLabel title = new JLabel("Create New Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(80, 20, 260, 30);
        box.add(title);

        int y = 70;

        // Full Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(40, y, 350, 20);
        JTextField nameField = new JTextField();
        nameField.setBounds(40, y + 25, 350, 30);
        box.add(nameLabel);
        box.add(nameField);
        y += 70;

        // Phone
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(40, y, 350, 20);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(40, y + 25, 350, 30);
        box.add(phoneLabel);
        box.add(phoneField);
        y += 70;

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40, y, 350, 20);
        JTextField emailField = new JTextField();
        emailField.setBounds(40, y + 25, 350, 30);
        box.add(emailLabel);
        box.add(emailField);
        y += 70;

        // DOB
        JLabel dobLabel = new JLabel("DOB (YYYY-MM-DD):");
        dobLabel.setBounds(40, y, 350, 20);
        JTextField dobField = new JTextField();
        dobField.setBounds(40, y + 25, 350, 30);
        box.add(dobLabel);
        box.add(dobField);
        y += 70;

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(40, y, 350, 20);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(40, y + 25, 350, 30);
        box.add(passLabel);
        box.add(passField);

        // Register Button
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(40, 480, 350, 40);
        registerBtn.setBackground(new Color(0, 120, 215));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        box.add(registerBtn);

        // Back Button
        JButton backBtn = new JButton("Back to Login");
        backBtn.setBounds(40, 530, 350, 40);
        box.add(backBtn);

        // Register Action
        registerBtn.addActionListener(e -> {

            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String dob = dobField.getText();
            String password = new String(passField.getPassword());

            UserDAO_Register dao = new UserDAO_Register();
            String uid = dao.registerUser(name, phone, email, dob, password);

            if (uid != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Account Created Successfully!\nYour User ID: " + uid,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                new LoginFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }
}
