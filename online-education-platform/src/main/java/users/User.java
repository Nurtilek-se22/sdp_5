package users;

/**
 * Base class for all users in the education platform
 */
public abstract class User {
    protected String name;
    protected String email;
    protected int id;
    protected String role;
    
    public User(String name, String email, int id, String role) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.role = role;
    }
    
    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getId() { return id; }
    public String getRole() { return role; }
    
    // Abstract methods
    public abstract void login();
    public abstract void logout();
    public abstract String getDashboard();
}
