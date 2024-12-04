package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

import java.util.Random;

public class ClientCheckers {

    public void sendCheckerMoveToServer(CheckersGameLogic gameLogic, int fromRow, int fromCol, int toRow, int toCol, Player player, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(10);

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
