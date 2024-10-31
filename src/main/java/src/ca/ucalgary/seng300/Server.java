package src.ca.ucalgary.seng300;


/// Basic Request/Response server listening
/// for requests corresponding to info that the GUI wants
/// or actions that the User wants to perform such as log in
public class Server {

    /// Starts listening for requests
    /// from clients and then handles the
    /// request and sends the response back to them
    public void serve() {
    }

    /// Server gets a request from a user to log them in
    /// Checks with auth that the user is not currently logged
    /// and then logs them in. Returns the User class datatype
    /// to the client
    private void logInUser(String username, String password) {
        // TODO: Implement
    }

    private void logOutUser() {}


    /// Server gets a request to register a user then asks the auth
    /// service to register the user if the register fails then it
    /// tells the user that the username/email is already used
    private boolean registerUser(String username, String password, String email) {
        // TODO: Implement
        return false;
    }

    /// Asks for profile info for a specific user
    /// if the profile is found an error is called
    /// otherwise we send the profile info to the client
    private void findProfileInfo() {

    }

    /// User sends that they have a new move
    /// Server finds the game that is being
    /// played, checks that it is a valid move
    /// then updates the gameState and sends a confirmation
    /// response that the move has been played
    private void newMove() {
    }

    /// The Client asks if there is new information about
    /// the game currently being played
    /// Most of the time this method will be called when
    /// someone is waiting for a new move to be played
    /// server checks the game, then if there was a change
    /// sends the new info to the client, otherwise sends a
    /// no change method
    private void newGameInfo() {}

    /// The Client wishes to queue for a ranked game,
    /// The server will ask the Matchmaking service
    /// If there is a match they can add,
    /// send a new match made to the client
    /// otherwise let the client know that they are in
    /// queue and need to ping the server if a new match
    /// has been found
    private void queueGame() {}

    /// The Client is pinging to queue if the matchmaker
    /// found a game. If so return the details of the game
    /// otherwise let the client know that they have not found
    /// a game
    private void pingQueue() {}

    /// The Client is letting the server know that
    /// they don't want to find a new game, so
    /// the server lets the matchmaker know that
    /// they should be removed
    private void cancelQueue() {}

    /// A client wants to view a game so
    /// the server sends info about the game to
    /// the client
    private void viewGame() {}

}
