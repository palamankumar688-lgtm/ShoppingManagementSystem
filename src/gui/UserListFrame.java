package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.UserDAO_Register;
import java.sql.*;
import dao.DBConnection;

public class UserListFrame extends JFrame {

    public UserListFrame() {

        setTitle("All Registered Users");
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columns = {"User ID", "Full Name", "Phone", "Email", "DOB"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, full_name, phone, email, dob FROM users";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("user_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("dob")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        add(pane);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
