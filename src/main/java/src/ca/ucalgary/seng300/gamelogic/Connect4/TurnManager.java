package src.ca.ucalgary.seng300.gamelogic.Connect4;

/**
 * A class to track who's turn it is in a game of connect 4
 */
public class TurnManager {
    private UserPiece playerRed;
    private UserPiece playerBlue;

    private UserPiece currentPlayer;

    /**
     * Constructor class that creates User pieces for red and blue players, and sets the red player to go first
     */
    public TurnManager(UserPiece playerRed, UserPiece playerBlue) {
        this.playerRed = playerRed;
        this.playerBlue = playerBlue;
        this.currentPlayer = playerRed;
    }

    /**
     * Get the player whos turn it is
     * 
     * @return The player who moves next
     */
    public UserPiece getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Swaps which player's turn it is based on the current player
     */
    public void changeTurns() {
        //Checks if player is red and turns it over to blue
        if (currentPlayer == playerRed) {
            currentPlayer = playerBlue;
        } else { //vice versa
            currentPlayer = playerRed;
        }
    }
}
