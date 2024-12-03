package src.ca.ucalgary.seng300.Profile.models;


public class User {

    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static User fromCsv(String csv) {
        String[] data = csv.split(",");
        return new User(data[1], data[2], data[0]);
    }

    public String toCsv() {
        return email + "," + username + "," + password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String toString() {
        return "User(username=" + username + ", email=" + email + ")";
    }
}
