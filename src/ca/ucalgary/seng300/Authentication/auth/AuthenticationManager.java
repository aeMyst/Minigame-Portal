package src.ca.ucalgary.seng300.Authentication.auth;

import src.ca.ucalgary.seng300.Authentication.models.User;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
// The userDatabase is subject to change. We need to ask our TA/prof to see if we can import one.
    public AuthenticationManager() {
        Map<String, User> userDatabase = new HashMap<>();
    }
    public boolean registerUser(String username, String password, String email) {
        // For now, we'll assume registration always succeeds, will adjust it later.
        return true;
    }
    public User login(String username, String password) {

        return null;
    }

    public void logout(User user) {

    }
    public boolean isAuthenticated(User user) {
        return true;
    }
}
