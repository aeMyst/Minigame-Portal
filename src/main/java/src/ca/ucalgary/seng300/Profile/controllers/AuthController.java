package src.ca.ucalgary.seng300.Profile.controllers;

import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
/**
 * Class to implement methods from AuthService to authorize user information
 * @author
 */

public class AuthController {

    // Initiate AuthService field
    private final AuthService authService;

    // Constructor for AuthController object
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Function to register new user
     * @param email
     * @param username
     * @param password
     * @return True if new user is stored without error
     * @return False if error occurs
     */
    public boolean register(String email,String username, String password) {
        // Implement method from AuthService class
        return authService.register(email,username, password);
    }

    /**
     * Function to login user given username and password to validate in user data file
     * @param username
     * @param password
     * @return True if entered data matches stored user data
     * @return False if user data is not found
     */
    public boolean login(String username, String password) {
        // Implement method from AuthService class
        return authService.login(username, password);
    }

    /**
     * Function to logout user and end session
     * @param currentUser
     * @return False if user is invalid
     * @return True if user is valid
     */
    public boolean logout(User currentUser) {
        // Implement method from AuthService class
        return authService.logout(currentUser);
    }

    // Method to check if a user is logged in, will return null if no user is logged in
    public User isLoggedIn(){
        // Implement method from AuthService class
        return authService.isLoggedIn();
    }
}