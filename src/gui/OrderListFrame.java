package gui;

import javax.swing.*;

public class OrderListFrame extends JFrame {

    public OrderListFrame() {
        setTitle("Orders");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("No orders available yet!", JLabel.CENTER);
        add(label);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
