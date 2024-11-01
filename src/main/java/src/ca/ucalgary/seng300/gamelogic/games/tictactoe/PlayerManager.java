package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

public class PlayerManager {
    private HumanPlayer player1;
    private HumanPlayer player2;
    private HumanPlayer currentPlayer;

    public PlayerManager(HumanPlayer player1, HumanPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public HumanPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        // Check if the currentPlayer is player1
        if (currentPlayer == player1) {
            // If currentPlayer is player1, switch to player2
            currentPlayer = player2;
        } else {
            // Otherwise, switch to player1
            currentPlayer = player1;
        }
    }


}
