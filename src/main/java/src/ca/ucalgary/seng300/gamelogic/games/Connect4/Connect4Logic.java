package src.ca.ucalgary.seng300.gamelogic.games.Connect4;

public class Connect4Logic {

    public static boolean valid(int[][] board, int row, int col) {
        //the series of conditionals tests whether inputs are within board dimensions.
        if (row < 0 || row >= board.length) {
            return false;
        }
        if (col < 0 || col >= board[0].length) {
            return false;
        }
        return true;
    }


    //method for checking if any positions are empty in the board
    public static boolean boardFull(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    return  false;
                }
            }
        }
        return true;
    }

    //main method for placing pieces, looping until board is full or win condition is achieved
    public static boolean placePiece(int[][] board, int col, int piece) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = piece;
                return true;
            }
        }
        return false;
    }


    //method for checking horizontal win condition is achieved
    public static boolean horizontalWin(int[][] board, int piece) {
        for (int row = 0; row < board.length; row++) {
            int counter = 0;
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == piece) {
                    counter = counter + 1;
                    if (counter == 4) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        return false;
    }

    //method for checking vertical win condition is achieved
    public static boolean verticalWin(int[][]board, int piece) {
        for (int col = 0; col < board[0].length; col++) {
            int counter = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[row][col] == piece) {
                    counter = counter + 1;
                    if (counter == 4) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        return false;
    }

    //method for checking backslash win condition is achieved
    //I'll figure out the logic for this later
    public static boolean backslashWin(int[][] board, int piece) {
        //we loop only the first 2 rows as only they are capable of fitting a chain
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 0; col <= board[0].length - 4; col++) {
            }

        }

        return false;
    }

    //method for checking forwardslash win condition is achieved
    //I'll figure out the logic for this later
    public static boolean forwardslashWin(int[][] board, int piece) {
        return false;
    }
}
