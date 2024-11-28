package src.ca.ucalgary.seng300.Profile.services;

public class UserAuthData {
    private String email;
    private String username;
    private String password;

    public UserAuthData(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    private String getPassword() {
        return password;
    }
}
