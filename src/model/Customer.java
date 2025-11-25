package model;

public class Customer extends User {

    public Customer(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String toString() {
        return "Customer: " + name + " (" + email + ")";
    }
}
