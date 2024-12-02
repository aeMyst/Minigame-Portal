package src.ca.ucalgary.seng300.gamelogic.Connect4;

/**
 * A class that represents a connect4 board as a 2d array
 * 
 * 
 */
public class Connect4Board {
    protected int[][] board;

    /**
     * Constructor for creating a new connect 4 board with dimensions 6 * 7
     */
    public Connect4Board() {
        board = new int[6][7]; //creates 2D array with standard dimensions of connect 4 board
    }

    /**
     *@return the 2d array of the connect 4 board in its current state
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * prints out current board state to the console labels
     * 
     * @param the board to be printed
     */
    public static void printBoard(int[][] board) {
        //Print out the collumn labels
        System.out.println("   1   2   3   4   5   6   7   ");
        System.out.println(" -----------------------------   ");

        //Print out the board by looping through the 2d array
        for (int i = 0; i < board.length; i++) {
            System.out.print(" | ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println(" -----------------------------   ");
        }
    }
}
