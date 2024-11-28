package src.ca.ucalgary.seng300.Profile.interfaces;


import src.ca.ucalgary.seng300.Profile.models.User;

public interface AuthInterface {
    boolean register(String email,String username, String password);
    boolean login(String username, String password);
    boolean logout(User currentUser);
    User isLoggedIn();
}
