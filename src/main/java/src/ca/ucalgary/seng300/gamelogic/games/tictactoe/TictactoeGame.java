package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

import java.util.Scanner;

public class TictactoeGame extends Game {

    private BoardManager boardManager;
    private PlayerManager playerManager;
    private Scanner scanner;

    // constructor
    public TictactoeGame() {
        boardManager = new BoardManager();
        playerManager = new PlayerManager(new HumanPlayer('X'), new HumanPlayer('O'));
        scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        while (true) {
            boardManager.printBoard();
            // some kind of work is being done here, but making game essentially
        }
    }
}
