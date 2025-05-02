package registration;

public class User {
    private int id;
    private String username;
    private String passwordHash; // SHA-256 hashed password
    private String role; // "admin", "staff", or "customer"
    private String name;

    public User(int id, String username, String passwordHash, String role, String name) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.name = name;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public String getName() { return name; }

    // Security check methods
    public boolean isAdmin() { return "admin".equalsIgnoreCase(role); }
    public boolean isStaff() { return "staff".equalsIgnoreCase(role); }
    public boolean isCustomer() { return "customer".equalsIgnoreCase(role); }
}