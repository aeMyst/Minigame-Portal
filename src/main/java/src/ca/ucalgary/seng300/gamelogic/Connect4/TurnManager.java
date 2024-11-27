package src.ca.ucalgary.seng300.gamelogic.Connect4;

public class TurnManager {
    private UserPiece playerRed;
    private UserPiece playerBlue;

    private UserPiece currentPlayer;

    public TurnManager(UserPiece playerRed, UserPiece playerBlue) {
        this.playerRed = playerRed;
        this.playerBlue = playerBlue;
        this.currentPlayer = playerRed;
    }

    public UserPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public void changeTurns() {
        //Checks if player is red and turns it over to blue
        if (currentPlayer == playerRed) {
            currentPlayer = playerBlue;
        } else { //vice versa
            currentPlayer = playerRed;
        }
    }
}
