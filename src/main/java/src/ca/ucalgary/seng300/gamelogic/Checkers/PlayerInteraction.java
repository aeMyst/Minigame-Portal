package src.ca.ucalgary.seng300.gamelogic.Checkers;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

import java.util.Scanner;

public class PlayerInteraction {

    private final CheckersGameLogic gameLogic;
    private final Scanner scanner;

    public PlayerInteraction(CheckersGameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Handles the player's turn based on the current player and returns the coordinates of the moved piece.
     *
     * @param currentPlayer The current player making the move.
     * @return An array containing the row and column of the piece moved, or null if the move was unsuccessful.
     */
    public int[] handlePlayerTurn(Player currentPlayer) {
        int fromRow, fromCol, toRow, toCol;
        System.out.println("Player " + currentPlayer.getPlayerID() + "'s turn.");

        while (true) {
            System.out.print("Enter the row of the piece to move: ");
            fromRow = scanner.nextInt() - 1;
            System.out.print("Enter the column of the piece to move: ");
            fromCol = scanner.nextInt() - 1;

            System.out.print("Enter the row of the destination: ");
            toRow = scanner.nextInt() - 1;
            System.out.print("Enter the column of the destination: ");
            toCol = scanner.nextInt() - 1;

            // Add currentPlayer as the fifth argument
            if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
                if (gameLogic.playerCapturedPiece(fromRow, fromCol, toRow, toCol, currentPlayer)) {
                    System.out.println("Piece captured!");
                    return new int[]{toRow, toCol};
                } else {
                    System.out.println("Invalid capture. Try again.");
                }
            } else {
                if (gameLogic.playerMovedPiece(fromRow, fromCol, toRow, toCol, currentPlayer)) {
                    return new int[]{toRow, toCol};
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }
        }
    }
}