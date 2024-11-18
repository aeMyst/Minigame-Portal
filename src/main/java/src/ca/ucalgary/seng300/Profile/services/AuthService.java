package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.utils.ValidationUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AuthService implements AuthInterface {
    //Possibly to track the logged in user


    private static final String USER_DATA_FILE = "users.txt";



    // This will register a new user based on the credentials that our users provide.
    // Once registration is completed without an issue, returns true.

    @Override
    public boolean register(String email, String username, String password) {
        if (validateCredentials(username, password, email)) {
            User user = new User(username, password, email);
            return storeUser(user);
        }
        return false;
    }
    // This will Validate the credentials based on formatting rules from ValidationUtils.
    private boolean validateCredentials(String username, String password, String email) {
        // I added isValidUsername here to filter out invalid usernames to prevent SQL injection attack.
        return ValidationUtils.isValidUsername(username) &&
                ValidationUtils.isValidPassword(password) &&
                ValidationUtils.isValidEmail(email);
    }

    // To store user info to a text file, let me know if we need to hash the password later on.
    private boolean storeUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getEmail());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred when storing user: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean login(String username, String password) {
        // Login logic goes here

        return true;
    }

    @Override
    public boolean logout(User currentUser) {
        //logout logic goes here
        currentUser = null;
        return true;
    }

    @Override
    public User isLoggedIn(User currentUser) {
        // returns the user or returns null if the user doesn't exist
        return currentUser;
    }
}
