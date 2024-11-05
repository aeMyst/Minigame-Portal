package src.ca.ucalgary.seng300.gamelogic.games.Checkers;

public class CheckersBoard {

    public static int[][] createBoard() {
        int size = 8; // Standard checkers board size is 8x8
        int[][] board = new int[size][size];
        initializeBoard(board);
        return board; // Return initialized board
    }

    // Initialize the checkers board with pieces
    public static void initializeBoard(int[][] board) {
        int size = board.length;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // White pieces (player 1) are on rows 0 to 2 on dark squares
                if (row < 3 && (row + col) % 2 != 0) {
                    board[row][col] = 1;
                }
                // Black pieces (player 2) are on rows 5 to 7 on dark squares
                else if (row > 4 && (row + col) % 2 != 0) {
                    board[row][col] = 2;
                }
                // Empty squares are set to 0
                else {
                    board[row][col] = 0;
                }
            }
        }
    }
}

