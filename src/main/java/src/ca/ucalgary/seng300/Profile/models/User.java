package src.ca.ucalgary.seng300.Profile.models;

/**
 * Class for User object and methods
 */
public class User {

    // Fields of User object
    private String username;
    private String password;
    private String email;

    // Constructor for User object
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getter and setter methods for username
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    // Getter and setter methods for password
    public String getPassword() {
        return password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // Getter and setter methods for email
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    // Method to view user information in string format
    public String toString() {
        return "User(username=" + username + ", email=" + email + ")";
    }
}
