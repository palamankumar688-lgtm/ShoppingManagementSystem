package gui;

import model.Cart;

import javax.swing.*;
import java.awt.*;

public class CheckoutFrame extends JFrame {

    public CheckoutFrame(Cart cart) {

        setTitle("Checkout");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        JLabel total = new JLabel("Total Amount: ₹" + cart.getTotalAmount(), SwingConstants.CENTER);
        total.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton placeOrder = new JButton("Place Order");

        placeOrder.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Order Placed Successfully!\nThank you for shopping!");

            cart.clear();
            dispose();
        });

        add(new JLabel(""));
        add(total);
        add(new JLabel(""));
        add(placeOrder);

        setVisible(true);
    }
}
