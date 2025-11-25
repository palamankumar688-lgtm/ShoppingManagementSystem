package model;

public class Admin extends User {

    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String toString() {
        return "Admin: " + name + " (" + email + ")";
    }
}
