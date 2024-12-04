package src.ca.ucalgary.seng300.gamelogic.Connect4;

/**
 * A class that implements all the logic needed to play a game of connect 4 using the Connect4Board class
 */
public class Connect4Logic extends Connect4Board {

    /**
     * This Method checks if a position exists in a given board
     * 
     * @param board The board that we are checking
     * @param row The row of the position we want to check
     * @param col The collumn of the position we want to check
     * @return True if the position is valid, false otherwise
     */
    public boolean valid(int[][] board, int row, int col) {
        //the series of conditionals tests whether inputs are within board dimensions.
        if (row < 0 || row >= board.length) {
            return false;
        }
        if (col < 0 || col >= board[0].length) {
            return false;
        }
        return true;
    }


    /**
     * method for checking if any positions are empty in a board
     * 
     * @param board The board we want to check
     * @return False if there is an open spot in the board, true otherwise
     */
    public boolean boardFull(int[][] board) {
        //Loop through each item in the 2d array and check if any are empty (== 0)
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * main method for placing pieces, looping until board is full or win condition is achieved
     * 
     * @param board The board that we are checking
     * @param piece The kind of piece that is being placed
     * @param col The collumn of the position we want to check
     * @return True if the piece is properly placed, false otherwise
     */
    public boolean placePiece(int[][] board, int col, int piece) {
        //checks for full column
        if (board[0][col] != 0) {
            return false; 
        }
        //find and place the piece in the first available row in the selected collumn
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = piece;
                return true;
            }
        }
        return false;
    }


    /**
     * method for checking horizontal win condition is achieved
     * 
     * @param board The board we are checking 
     * @param piece The piece we are checking the win-state of
     * @return True if a horizontal win exits for the piece, false otherwise
     */
    public boolean horizontalWin(int[][] board, int piece) {
        //Loop through each piece for each row
        for (int row = 0; row < board.length; row++) {
            int counter = 0;
            for (int col = 0; col < board[row].length; col++) {
                //count consective horizontal pieces, if the count ever reaches 4 a win condition exits
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

    /**
     * method for checking vertical win condition is achieved
     * 
     * @param board The board we are checking 
     * @param piece The piece we are checking the win-state of
     * @return True if a vertical win exits for the piece, false otherwise
     */
    public boolean verticalWin(int[][] board, int piece) {
        //Loop through each piece for each collumn
        for (int col = 0; col < board[0].length; col++) {
            int counter = 0;
            for (int row = 0; row < board.length; row++) {
                //count consective horizontal pieces, if the count ever reaches 4 a win condition exits
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

    /**
     * method for checking if a backslash win condition is achieved
     * 
     * @param board The board we are checking 
     * @param piece The piece we are checking the win-state of
     * @return True if a backslash win exits for the piece, false otherwise
     */
    public boolean backslashWin(int[][] board, int piece) {
        //we loop only the first 2 rows as only they are capable of fitting a chain
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 0; col <= board[0].length - 4; col++) {
                //for each copy of the piece, check if the 3 positions Diagonally up and right also contain the piece, if so a win condition is found
                if (board[row][col] == piece) {
                    if (board[row + 1][col + 1] == piece && board[row + 2][col + 2] == piece && board[row + 3][col + 3] == piece) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * method for checking if a fowardslash win condition is achieved
     * 
     * @param board The board we are checking 
     * @param piece The piece we are checking the win-state of
     * @return True if a fowardslash win exits for the piece, false otherwise
     */
    public boolean forwardslashWin(int[][] board, int piece) {
        //We only look at the first 4 rows and ignore the first 2 collumns so we only check valid spots
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 3; col < board[0].length; col++) {
                //for each copy of the piece, check if the 3 positions Diagonally down adn right also contain the piece, if so a win condition is found
                if (board[row][col] == piece) {
                    if (board[row + 1][col - 1] == piece && board[row + 2][col - 2] == piece && board[row + 3][col - 3] == piece) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
