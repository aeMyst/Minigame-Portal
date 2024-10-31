package src.ca.ucalgary.seng300.gamelogic.games.Connect4;

public class Board {

    //method for creating the initial board
    public static int[][] createBoard(int rows, int columns) {

        return new int[rows][columns];
    }

    //method for taking 2D array from createBoard and counting number of rows
    public static int rowSetUp(int[][] board) {
        return board.length;
    }

    //method for taking 2D array from createBoard and counting number of columns
    public static int columnSetUp(int[][] board) {
        return board[0].length;
    }

    //method for checking if any positions are empty in the column
    public static boolean columnFull() {
        return false;
    }

    //method for checking if any positions are empty in the board
    public static boolean boardFull() {
        return false;
    }

    //main method for placing pieces, looping until board is full or win condition is achieved
    public static int placePiece() {
        return 1;
    }

    //method for undoing last placed piece
    public static int undoPiece() {
        return 1;
    }

    //method for checking horizontal win condition is achieved
    public static boolean horizontalWin() {
        return false;
    }

    //method for checking vertical win condition is achieved
    public static boolean verticalWin() {
        return false;
    }

    //method for checking backslash win condition is achieved
    public static boolean backslashWin() {
        return false;
    }

    //method for checking forwardslash win condition is achieved
    public static boolean forwardslashWin() {
        return false;
    }
}
