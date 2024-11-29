package src.ca.ucalgary.seng300.Profile.interfaces;

import src.ca.ucalgary.seng300.Profile.models.User;

/**
 * Interface to define methods implemented in AuthService and AuthController
 * @author
 */
public interface AuthInterface {
    // Method to register new user
    boolean register(String email,String username, String password);

    // Method to login user
    boolean login(String username, String password);

    //Method to logout user
    boolean logout(User currentUser);

    // Initialize user field
    User isLoggedIn();
}
