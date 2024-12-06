package src.ca.ucalgary.seng300.Profile.models;

import java.util.Objects;

/**

 Class for User object and methods*/
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

    // get information from csv file
    public static User fromCsv(String csv) {
        String[] data = csv.split(",");
        return new User(data[1], data[2], data[0]);
    }

    // preparing to write to csv by getting User information
    public String toCsv() {
        return email + "," + username + "," + password;
    }

    // Getter and setter methods for username
    public void setUsername(String username) {
        this.username = username;
    }

    // return the username from User
    public String getUsername() {
        return username;
    }

    // Getter and setter methods for password
    public String getPassword() {
        return password;
    }

    // Getter and setter methods for email
    public void setEmail(String email) {
        this.email = email;
    }

    // return the email of User
    public String getEmail() {
        return email;
    }

    // set the password of user
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // converting User to String
    public String toString() {
        return "User(username=" + username + ", email=" + email + ")";
    }
}
