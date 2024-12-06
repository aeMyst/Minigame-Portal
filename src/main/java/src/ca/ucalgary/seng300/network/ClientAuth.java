package src.ca.ucalgary.seng300.network;

import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.models.User;

/**
 * The Authentication Subpart of the Client interface
 */
public class ClientAuth {
    AuthInterface auth;

    // constructor method
    public ClientAuth(AuthInterface authInterface) {
        auth = authInterface;
    }

    // login user @ server
    public boolean logInUser(String username, String password) {
        return auth.login(username, password);
    }

    // logout user @ server
    public void logoutUser() {
        // first check that the user is currently logged in
        User cur_user = auth.isLoggedIn();
        if (cur_user == null) {
            // if not just return as the user is logged out
            return;
        }
        // logout user
        auth.logout(cur_user);
    }

    // register user @ server
    public boolean registerUser(String username, String password, String email) {
        return auth.register(email, username, password);
    }

    // verify user is logged in @ server
    public User isLoggedIn() {
        User currentUser = auth.isLoggedIn();
        return currentUser;
    }
}
