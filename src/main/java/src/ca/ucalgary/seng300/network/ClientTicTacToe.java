package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.PlayerManager;

import java.util.Random;

/**
 * The Tic-Tac-Toe component of the Client class
 */
public class ClientTicTacToe {
    /**
     * @param boardManager the current board
     * @param playerManager the players and the current player
     * @param status of the game, the current gamestate either "ONGOING" or "DONE"
     */
    private void newMoveTTT(BoardManager boardManager, PlayerManager playerManager, String status) {
        System.out.println("Game Status: " + status);
        System.out.println("Current Player: " + playerManager.getCurrentPlayer().getSymbol());
        for (char[] row : boardManager.getBoard()) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    /**
     * once move has occurred send to server to update stats/leaderboard and the gamestate
     * @param boardManager the current board
     * @param playerManager the players and the current player
     * @param status of the game, the current gamestate either "ONGOING" or "DONE"
     * @param callback call after server response
     */
    public void sendMoveToServer(BoardManager boardManager, PlayerManager playerManager, String status, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000);
        //
        // ChatGPT Generated: taught me how to use Thread.sleep and to pause before running next line
        // this is to simulate a delay in placing a piece in GUI
        //
        new Thread(() -> {
            try {
                Thread.sleep(time); // Simulate 1-second delay
                System.out.println("Server Communication now...");
                System.out.println("Move acknowledged by server: " + status);
                newMoveTTT(boardManager, playerManager, status);
                Platform.runLater(callback);// Call the callback after the "server" responds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
