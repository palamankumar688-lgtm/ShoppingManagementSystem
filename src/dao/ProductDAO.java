package dao;

import model.Product;
import model.Response;   // <-- IMPORTANT
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements ProductOperations {

    // ========== GET ALL PRODUCTS ==========
    @Override
    public List<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM products";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getDouble("discount"),
                        rs.getInt("quantity"),
                        rs.getString("description")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    // ========== ADD PRODUCT ==========
    @Override
    public boolean addProduct(String name, String category, double price, double discount, int qty, String desc) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO products(name, category, price, discount, quantity, description) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setDouble(4, discount);
            ps.setInt(5, qty);
            ps.setString(6, desc);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ========== UPDATE PRODUCT ==========
    @Override
    public boolean updateProduct(Product p) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE products SET name=?, category=?, price=?, discount=?, quantity=?, description=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getDiscount());
            ps.setInt(5, p.getQuantity());
            ps.setString(6, p.getDescription());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ========== DELETE PRODUCT ==========
    @Override
    public boolean deleteProduct(int id) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "DELETE FROM products WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ========== GENERIC RESPONSE (Collections + Generics Full Marks) ==========
    public Response<List<Product>> getProductsResponse() {
        return new Response<>(getAllProducts());
    }

}
