package gui;

import javax.swing.*;
import dao.ProductDAO;

public class AddProductFrame extends JFrame {

    public AddProductFrame() {

        setTitle("Add New Product");
        setSize(400, 450);
        setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameField = new JTextField();

        JLabel catLabel = new JLabel("Category:");
        JTextField catField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JLabel discLabel = new JLabel("Discount (%):");
        JTextField discField = new JTextField();

        JLabel qtyLabel = new JLabel("Quantity:");
        JTextField qtyField = new JTextField();

        JLabel descLabel = new JLabel("Description:");
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);

        JButton addButton = new JButton("Add Product");

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String category = catField.getText();
                double price = Double.parseDouble(priceField.getText());
                double discount = Double.parseDouble(discField.getText());
                int quantity = Integer.parseInt(qtyField.getText());
                String desc = descArea.getText();

                ProductDAO dao = new ProductDAO();
                boolean success = dao.addProduct(name, category, price, discount, quantity, desc);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Product Added Successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding product!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price / Discount / Quantity numeric hona chahiye!");
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(nameLabel); add(nameField);
        add(catLabel); add(catField);
        add(priceLabel); add(priceField);
        add(discLabel); add(discField);
        add(qtyLabel); add(qtyField);
        add(descLabel); add(new JScrollPane(descArea));
        add(addButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
