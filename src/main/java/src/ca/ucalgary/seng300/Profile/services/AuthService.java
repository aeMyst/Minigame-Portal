package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.utils.ValidationUtils;

import java.io.*;

public class AuthService implements AuthInterface {

    private User currentUser = null;

    private static final String USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/Profile/services/users.txt";

    // This will register a new user based on the credentials that our users provide.
    // Once registration is completed without an issue, returns true.
    @Override
    public boolean register(String email, String username, String password) {
        if (validateCredentials(username, password, email)) {
            User user = new User(username, password, email);
            boolean isStored = storeUser(user);
            if (isStored) {
                System.out.println("Registration successful for user: " + username);
                return true;
            } else {
                System.out.println("Failed to store user information.");
            }
        } else {
            System.out.println("Invalid credentials provided. Registration failed.");
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
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true));
            writer.write(user.getEmail() + "," + user.getUsername() + "," + user.getPassword());
            writer.newLine();
            writer.close(); // Close the writer to save changes
            System.out.println("User successfully registered and stored.");
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred when storing user: " + e.getMessage());
            return false;
        }
    }



    @Override
    public boolean login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 3) {
                    String storedUsername = userDetails[1];
                    String storedPassword = userDetails[2];
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        currentUser = new User(storedUsername, storedPassword, null);
                        System.out.println("Login successful : " + username);
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean logout(User user) {
        if (user == null || !user.equals(currentUser)) {
            System.out.println("User Invalid.");
            return false;
        }

        currentUser = null;
        System.out.println("User successfully logged out.");
        return true;
    }

    @Override
    public User isLoggedIn() {
        return currentUser;
    }


}
