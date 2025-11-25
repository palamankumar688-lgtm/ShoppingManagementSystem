package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL  = "jdbc:mysql://localhost:3306/shopping_db";
    private static final String USER = "aman";
    private static final String PASS = "Aman@123";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
            return null;
        }
    }
}
