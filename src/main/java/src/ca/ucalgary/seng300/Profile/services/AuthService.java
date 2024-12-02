package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.utils.ValidationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * AuthService class implementing methods from AuthInterface, methods used by AuthController class to
 * authorize user information
 * @author
 */

public class AuthService implements AuthInterface {

    // Initiate currentUser as null
    private User currentUser = null;
    private ArrayList<User> users = new ArrayList<>();
    // Set pathname for users data
    private static final String USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";

    public AuthService() {
        ArrayList<User> newUsers = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCsv(line);
                newUsers.add(user);
            }
            reader.close();
            this.users = newUsers;
        } catch (IOException e) {
            System.err.println("An error occurred when reading user data: " + e.getMessage());
        }
    }

    public void saveUsers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE));
            for (User user : users) {
                writer.write(user.toCsv());
                writer.newLine();
            }
            writer.close();
            System.out.println("User data successfully saved.");
        } catch (IOException e) {
            System.err.println("An error occurred when saving user data: " + e.getMessage());
        }
    }

    /**
     * Function to register new user
     * @param email
     * @param username
     * @param password
     * @return True if new user is stored without error
     * @return False if error occurs
     */
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

    /**
     * Function to validate entered username, password and email are in acceptable format
     * @param username
     * @param password
     * @param email
     * @return boolean result of ValidationUtils methods called
     */
    public boolean validateCredentials(String username, String password, String email) {
        return ValidationUtils.isValidUsername(username) &&
                ValidationUtils.isValidPassword(password) &&
                ValidationUtils.isValidEmail(email);
    }



    /**
     * Function to store new user information to user txt file
     * @param user
     * @return True if information was added without error
     * @return False if error occurs
     */
    private boolean storeUser(User user) {
        this.users.add(user);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true));
            writer.write(user.toCsv());
            writer.newLine();
            writer.close();
            System.out.println("User successfully registered and stored.");
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred when storing user: " + e.getMessage());
            return false;
        }
    }

    public void modifyUserPassword(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPassword(password);
                saveUsers();
                break;
            }
        }

    }


    public ArrayList<User> getSanitizedUsers() {
        ArrayList<User> sanitizedUsers = new ArrayList<>();
        for (User user : users) {
            sanitizedUsers.add(new User(user.getUsername(), null, user.getEmail()));
        }
        return sanitizedUsers;
    }


    /**
     * Function to login user given username and password to validate in user data file
     * @param username
     * @param password
     * @return True if entered data matches stored user data
     * @return False if user data is not found
     */
    @Override
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful: " + username);
                return true;
            }
        }
        System.out.println("Login failed: Invalid username or password.");
        return false;
    }

    /**
     * Function to logout user and end session
     * @param user
     * @return False if user is invalid
     * @return True if user is valid
     */
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

    // Method to check if a user is logged in, will return null if no user is logged in
    @Override
    public User isLoggedIn() {
        return currentUser;
    }

    public void updateCurrentUser(String newUsername, String newEmail) {
        if (currentUser != null) {
            currentUser.setUsername(newUsername);
            currentUser.setEmail(newEmail);
        }
    }


}
