package src.ca.ucalgary.seng300.authentication.services;

public class AuthService implements AuthInterface {
    @Override
    public boolean register(String username, String password) {
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
        return true;
    }
}
