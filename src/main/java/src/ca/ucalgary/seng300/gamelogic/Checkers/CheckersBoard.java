package src.ca.ucalgary.seng300.gamelogic.Checkers;

/**
 * This class represents a Checkers board. It provides methods to create and initialize
 * a standard 8x8 checkers board with pieces positioned for the start of a game.
 */
public class CheckersBoard {

    /**
     * Creates a new Checkers board and initializes it with the standard starting positions.
     *
     * @return a 2D integer array representing the Checkers board.
     *         - 0 represents an empty square.
     *         - 1 represents a square occupied by a white piece (player 1).
     *         - 2 represents a square occupied by a black piece (player 2).
     */
    public static int[][] createBoard() {
        int size = 8; // Standard checkers board size is 8x8
        int[][] board = new int[size][size];// Create a 2D array to represent the board.
        initializeBoard(board);// Initialize the board with the starting positions.
        return board; // Return initialized board
    }

    /**
     * Initializes a Checkers board with pieces in their standard starting positions.
     * White pieces (player 1) are placed on rows 0-2 on light squares.
     * Black pieces (player 2) are placed on rows 5-7 on light squares.
     * All other squares are empty.
     *
     * @param board the 2D integer array representing the Checkers board.
     */
    public static void initializeBoard(int[][] board) {
        int size = board.length; // Determine the board size.

        // Iterate through each row and column of the board.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Check if the square is a light square ((row + col) % 2 != 0).
                // White pieces (player 1) are on rows 0 to 2 on light squares
                if (row < 3 && (row + col) % 2 != 0) {
                    board[row][col] = 1;// White piece (player 1).
                }
                // Black pieces (player 2) are on rows 5 to 7 on light squares
                else if (row > 4 && (row + col) % 2 != 0) {
                    board[row][col] = 2;// Black piece (player 2).
                }
                // Empty squares are set to 0
                else {
                    board[row][col] = 0;//if neither a white nor black piece is set, set to 0
                }
            }
        }
    }
}

