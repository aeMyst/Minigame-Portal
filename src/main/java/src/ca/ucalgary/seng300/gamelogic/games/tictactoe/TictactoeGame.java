package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

import java.util.Scanner;

public class TictactoeGame extends Game {

    private BoardManager boardManager;
    private PlayerManager playerManager;

    // constructor
    public TictactoeGame() {
        // initialization of board, and players
        boardManager = new BoardManager();
        playerManager = new PlayerManager(new HumanPlayer('X'), new HumanPlayer('O'));
    }

    // method to start game
    @Override
    public void start() {
        while (true) {
            boardManager.printBoard(boardManager.getBoard());

            HumanPlayer currentPlayer = playerManager.getCurrentPlayer();
            System.out.println("Player " + currentPlayer.getSymbol() + ", make your move.");

            int[] move = currentPlayer.getMove(boardManager);

            if (!boardManager.isValidMove(move[0], move[1])) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            boardManager.placeSymbol(currentPlayer.getSymbol(), move[0], move[1]);

            if (boardManager.isWinner(currentPlayer.getSymbol())) {
                boardManager.printBoard(boardManager.getBoard());
                System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                break;
            }

            if (boardManager.isTie()) {
                boardManager.printBoard(boardManager.getBoard());
                System.out.println("It's a draw!");
                break;
            }

            playerManager.switchPlayer();
        }
    }
    // some kind of work is being done here, but making game essentially
}


