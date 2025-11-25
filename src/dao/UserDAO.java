package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // USER LOGIN: user_id + password
    public boolean validateUser(String userId, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) return false;

            String sql = "SELECT * FROM users WHERE user_id=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();  // Agar user mila
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // REGISTER USER (Advanced table ke liye nahi — ye old wala remove kar do)
    public boolean registerUser(String username, String password) {
        // AB ye method use nahi hoga, kyuki new registration
        // UserDAO_Register class handle karegi.
        return false;
    }
}
