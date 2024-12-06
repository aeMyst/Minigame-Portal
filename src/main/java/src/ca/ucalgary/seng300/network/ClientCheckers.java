package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

import java.util.Random;

/**
 * The Checkers part of the Client class
 */
public class ClientCheckers {
    /**
     * Sends a new checkers move to the network
     * @param gameLogic The checkers game logic unit
     * @param fromRow what row is the source piece from [0-7]
     * @param fromCol what col is the source piece from [0-7]
     * @param toRow what row is the piece being moved to from [0-7]
     * @param toCol what col is the piece being moved to from [0-7]
     * @param player The player that is making the move
     * @param callback A function to be called after the network has resolved the move
     */
    public void sendCheckerMoveToServer(CheckersGameLogic gameLogic, int fromRow, int fromCol, int toRow, int toCol, Player player, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(10);
        //
        // ChatGPT Generated: taught me how to use Thread.sleep and to pause before running next line
        // this is to simulate a delay in placing a piece in GUI
        //
        new Thread(() -> {
            try {
                Thread.sleep(time); // Simulate server processing time
                System.out.println("Server Communication: Processing move...");
                System.out.printf("Move acknowledged by server: [%d, %d] -> [%d, %d]\n", fromRow, fromCol, toRow, toCol);

                // Log the board state after the move
                newMoveCheckers(gameLogic, player);

                Platform.runLater(callback); // Execute the callback on the JavaFX thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Shows to console what the current game state is
     * @param logicManager logic manager for the current game
     * @param currentPlayer the player that needs to make a move
     */
    private void newMoveCheckers(CheckersGameLogic logicManager, Player currentPlayer) {
        System.out.println("Current Player: " + currentPlayer.getPlayerID());

        int[][] board = logicManager.getBoard();
        System.out.println("     1  2  3  4  5  6  7  8");
        System.out.println("   +------------------------+");
        for (int row = 0; row < board.length; row++) {
            System.out.print((row + 1) + " | ");
            for (int col = 0; col < board[row].length; col++) {
                switch (board[row][col]) {
                    case 1 -> System.out.print("W  "); // White piece
                    case 2 -> System.out.print("B  "); // Black piece
                    case 3 -> System.out.print("WK "); // White king
                    case 4 -> System.out.print("BK "); // Black king
                    default -> System.out.print(".  "); // Empty square
                }
            }
            System.out.println("|");
        }
        System.out.println("   +------------------------+");
    }
}
