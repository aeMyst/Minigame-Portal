package src.ca.ucalgary.seng300.Authentication.models;

public class User {

    private String username;
    private String passwordHash;
    private String email;
    private Profile profile;

    public User(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.profile = new Profile(username);
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public Profile getProfile() {
        return profile;
    }

    public void updatePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
    }

    public String toString() {
        return STR."User(username=\{username}, email=\{email})";
    }
}
