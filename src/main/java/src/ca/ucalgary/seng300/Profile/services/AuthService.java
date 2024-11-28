package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.utils.ValidationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class AuthService implements AuthInterface {

    private User currentUser = null;
    private ArrayList<User> users = new ArrayList<>();

    private static final String USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/Profile/services/users.csv";

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

//    public String recoverUsername(String email) {
//        for (User user : users) {
//            if (Objects.equals(user.getEmail(), email)) {
//                return user.getUsername();
//            }
//        }
//        return null;
//    }

    public ArrayList<User> getSanitizedUsers() {
        ArrayList<User> sanitizedUsers = new ArrayList<>();
        for (User user : users) {
            sanitizedUsers.add(new User(user.getUsername(), null, user.getEmail()));
        }
        return sanitizedUsers;
    }



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
