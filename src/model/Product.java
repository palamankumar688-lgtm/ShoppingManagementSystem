package model;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private double discount;
    private int quantity;
    private String description;

    public Product(int id, String name, String category, double price, double discount, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
    public int getQuantity() { return quantity; }
    public String getDescription() { return description; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return name + " | " + category + " | Rs." + price + " | Disc: " + discount + "% | Qty: " + quantity;
    }
}
