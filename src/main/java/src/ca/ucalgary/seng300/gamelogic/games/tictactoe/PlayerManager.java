package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

public class PlayerManager {
    private HumanPlayer player1;
    private HumanPlayer player2;
    private HumanPlayer currentPlayer;

    public PlayerManager(HumanPlayer play1, HumanPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        // logic for switching turns
    }


}
