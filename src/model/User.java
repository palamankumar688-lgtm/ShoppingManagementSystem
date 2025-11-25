package model;

public class User extends Person {
    
    private String password;

    public User() {
        super("", ""); // default
    }

    public User(String username, String email, String password) {
        super(username, email); // Person class ka constructor call
        this.password = password;
    }

    @Override
    public void showDetails() {
        System.out.println("User Details: " + name + " | Email: " + email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
