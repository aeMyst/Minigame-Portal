package src.ca.ucalgary.seng300.gamelogic.Checkers;

public class Graphic {

    /**
     * Updates and displays the current board state to the console.
     *
     * @param board The 2D array representing the current state of the board.
     */
    public void update(int[][] board) {
        System.out.println("Current Board:");
        displayBoard(board);
    }

    /**
     * Displays the board in a readable format with 1-based indexing for rows and columns.
     *
     * @param board The 2D array representing the board state.
     */
    private void displayBoard(int[][] board) {
        // Display column headers starting from 1
        System.out.print("    ");  // Padding for the left corner
        for (int col = 1; col <= board.length; col++) {
            System.out.print(col + " ");
        }
        System.out.println(); // Move to the next line after printing column numbers

        System.out.println("   +-------------------------------+"); // Updated width to fit columns

        for (int row = 0; row < board.length; row++) {
            System.out.print((row + 1) + " | "); // Display row numbers starting from 1
            for (int col = 0; col < board[row].length; col++) {
                switch (board[row][col]) {
                    case 1 -> System.out.print("W ");  // White piece
                    case 2 -> System.out.print("B ");  // Black piece
                    case 3 -> System.out.print("WK"); // White king
                    case 4 -> System.out.print("BK"); // Black king
                    default -> System.out.print(". "); // Empty space
                }
            }
            System.out.println("|"); // End row with a boundary
        }

        System.out.println("   +-------------------------------+"); // Bottom boundary line
    }
}