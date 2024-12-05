package src.ca.ucalgary.seng300.network;

import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.models.User;

/**
 * The Authentication Subpart of the Client interface
 */
public class ClientAuth {
    AuthInterface auth;
    public ClientAuth(AuthInterface authInterface) {
        auth = authInterface;
    }

    public boolean logInUser(String username, String password) {
        return auth.login(username, password);
    }

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

    public boolean registerUser(String username, String password, String email) {
        return auth.register(email, username, password);
    }

    public User isLoggedIn() {
        User currentUser = auth.isLoggedIn();
        return currentUser;
    }
}
