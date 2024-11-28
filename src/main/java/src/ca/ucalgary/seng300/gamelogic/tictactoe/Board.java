package src.ca.ucalgary.seng300.gamelogic.tictactoe;

public class Board {
    //instance variables
    protected char[][] board;

    // constructor method that initializes the board
    public Board() {
        board = new char[3][3]; // Create 2d array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' '; // each arrow will be filled with a placeholder with empty space
            }
        }
    }

    /**
     *
     * @return board a 2d array representation of the chars in the board created by the loop in Board()
     */

    public char[][] getBoard() {
        return board;
    }

    public static void printBoard(char[][] board) {
        System.out.println("      1       2       3   ");  // Column labels
        System.out.println("    -----------------------");

        for (int i = 0; i < 3; i++) {
            System.out.print((i + 1) + "  |  ");  // Row label
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j] + "  |  ");
            }
            System.out.println();
            System.out.println("    -----------------------");
        }
    }
}
