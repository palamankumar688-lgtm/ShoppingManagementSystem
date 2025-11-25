package dao;

import model.Product;
import java.util.List;

public interface ProductOperations {

    // Product list fetch karna
    List<Product> getAllProducts();

    // Add new product
    boolean addProduct(String name, String category, double price, double discount, int qty, String desc);

    // Update existing product
    boolean updateProduct(Product p);

    // Delete product by ID
    boolean deleteProduct(int id);
}
