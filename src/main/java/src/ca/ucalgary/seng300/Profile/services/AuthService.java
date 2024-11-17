package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.models.User;

public class AuthService implements AuthInterface {
    //Possibly to track the logged in user

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
