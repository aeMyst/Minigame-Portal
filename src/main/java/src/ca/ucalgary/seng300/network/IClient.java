package src.ca.ucalgary.seng300.network;

public interface IClient {


    /// Using the username and password attempt to log in the user
    boolean logInUser(String username, String password);

    /// Log out the user if they are currently logged in
    void logoutUser();

    /// Attempt to register a new user with a new username, password and
    /// email returning false if there was an error
    boolean registerUser(String username, String password, String email);

    /// Gets the username of the current user if logged in
    /// not logged in returns null
    String getCurrentUsername();

    /// Find the profile info of a given user
    String findProfileInfo(String user);


    /// queue for a specific game and return a starting game
    void  queueGame();

}
