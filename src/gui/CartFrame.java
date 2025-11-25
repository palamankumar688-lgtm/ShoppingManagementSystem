package gui;

import model.Cart;
import model.CartItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartFrame extends JFrame {

    private Cart cart;
    private JTable table;
    private DefaultTableModel model;

    public CartFrame(Cart cart) {
        this.cart = cart;

        setTitle("Your Cart");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = {"Product", "Price", "Qty", "Total"};

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        loadCartItems();

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);

        JButton checkoutBtn = new JButton("Proceed to Checkout");
        checkoutBtn.addActionListener(e -> new CheckoutFrame(cart));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(checkoutBtn);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadCartItems() {
        model.setRowCount(0);

        for (CartItem item : cart.getItems()) {
            model.addRow(new Object[]{
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getTotal()
            });
        }
    }
}
