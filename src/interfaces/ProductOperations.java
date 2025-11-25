package interfaces;

import model.Product;

public interface ProductOperations {
    void addProduct(Product p);
    void updateProduct(Product p);
    void deleteProduct(int id);
}
