package src.ca.ucalgary.seng300;

import src.ca.ucalgary.seng300.gamelogic.GameState;

public interface IClient {


    /// Using the username and password attempt to log in the user
    boolean logInUser(String username, String password);

    /// Log out the user if they are currently logged in
    void logoutUser();

    /// Attempt to register a new user with a new username, password and
    /// email returning false if there was an error
    boolean registerUser(String username, String password, String email);

    /// Find the profile info of a given user
    String findProfileInfo(String user);

    /// queue for a specific game and return a starting game
    void queueGame();

    /// If we are waiting for the next move ask for the next move in a game
    GameState getNextMove(GameState gamestate);
    /// cancel the request to queue up
    /// Spectate a game with a specific id
    GameState viewGame(int id);

    /// Get the current leaderboard
    //ILeaderBoard getLeaderBoard();
  
}
