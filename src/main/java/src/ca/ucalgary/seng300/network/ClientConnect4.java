package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.TurnManager;
import java.util.Random;

/**
 * The Connect 4 component of the Client class
 */
public class ClientConnect4 {
    /**
     * Sends a connect 4 move over to the server
     * @param logicManager The Connect4 Logic system
     * @param turnManager The Turn manager
     * @param status What the status of the game is, either "ONGOING" or "DONE"
     * @param callback A function that executes after the server has done processing
     *                  the move, generally will just update the GUI
     */
    public void sendC4MoveToServer(Connect4Logic logicManager, TurnManager turnManager, String status, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000); // delay
        //
        // ChatGPT Generated: taught me how to use Thread.sleep and to pause before running next line
        // this is to simulate a delay in placing a piece in GUI
        //
        new Thread(() -> {
            try {
                Thread.sleep(time); // simulate server delay
                System.out.println("Server Communication now...");
                System.out.println("Move acknowledged by server: " + status);
                newMoveC4(logicManager, turnManager, status); // updates the board and game state
                Platform.runLater(callback); // calls the callback after the fake server responds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Shows the current board state to the server
     * @param logicManager The logic manager for the current game
     * @param turnManager the turn manager for keeping track of who's turn it is
     * @param status The status of the game, either "ONGOING" or "DONE"
     */
    private void newMoveC4(Connect4Logic logicManager, TurnManager turnManager, String status) {
        System.out.println("Game Status: " + status);
        System.out.println("Current Player: " + turnManager.getCurrentPlayer().getPiece());

        int[][] board = logicManager.getBoard();
        System.out.println("   1   2   3   4   5   6   7");
        System.out.println("   --------------------------");

        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == 0 ? " " : cell) + " | ");
            }
            System.out.println();
            System.out.println("   -----------------------------");
        }
    }
    // Was written with help of AI(better formatting for output)
}
