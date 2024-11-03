package src.ca.ucalgary.seng300.authentication.interfaces;


public interface AuthInterface {
    boolean register(String username, String password);
    boolean login(String username, String password);
    boolean logout(String username);
}
