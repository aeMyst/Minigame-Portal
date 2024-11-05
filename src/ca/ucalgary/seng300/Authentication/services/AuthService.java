package src.ca.ucalgary.seng300.authentication.services;

import src.ca.ucalgary.seng300.authentication.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.authentication.models.User;

public class AuthService implements AuthInterface {
    //Possibly to track the logged in user
    private User currentUser;

    @Override
    public boolean register(String email,String username, String password) {
        // Registration logic goes here
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        // Login logic goes here

        return true;
    }

    @Override
    public boolean logout(String username) {
        //logout logic goes here
        currentUser = null;
        return true;
    }

    @Override
    public User isLoggedIn() {
        // returns the user or returns null if the user doesn't exist
        return currentUser;
    }
}
