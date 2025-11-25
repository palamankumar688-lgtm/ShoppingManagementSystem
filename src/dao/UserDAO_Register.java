package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO_Register {

    public String registerUser(String name, String phone, String email, String dob, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) return null;

            // AUTO GENERATE USER ID
            String userId = generateUserId(conn);

            String sql = "INSERT INTO users(user_id, full_name, phone, email, dob, password) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, userId);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, dob);
            ps.setString(6, password);

            ps.executeUpdate();
            return userId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateUserId(Connection conn) throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM users";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        rs.next();
        int count = rs.getInt("total") + 1;
        return "U" + String.format("%03d", count);  // Example: U001, U002, U010
    }
}
