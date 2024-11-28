package src.ca.ucalgary.seng300.gamelogic.tictactoe;

public class BoardManager extends Board {

    // check if player has made a valid move
    /**
     * @param row, col the location of the desired move that is being verified valid
     * @return bool value that checks if the move is in bounds and empty
     */
    public boolean isValidMove(int row, int col) {
        boolean isRowInBounds = row >= 0 && row < 3; // checking row is valid
        boolean isColInBounds = col >= 0 && col < 3; // checking column is valid
        boolean isCellEmpty = board[row][col] == ' '; // checking if space is empty

        boolean check = isRowInBounds && isColInBounds && isCellEmpty; // combination

        return check;
    }

    /**
     * @param row, col the location of the desired move that has been verified valid
     * @param  symbol the symbol that is placed in the location X or O
     */

    public void placeSymbol(char symbol, int row, int col) {
        board[row][col] = symbol; // place symbol where user picked if valid
    }

    /**
     * @param symbol of the person that may have won
     * @return bool value that checks if a win has occurred
     * true if symbol is all the way across a row, down a column or diagonal, false if this is not the case
     */
    public boolean isWinner(char symbol) {
        // Check each row and column for a winning condition
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol)
                return true; // Winning row
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)
                return true; // Winning column
        }

        // Check left-to-right diagonal
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
            return true;
        // Check right-to-left diagonal
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)
            return true;

        // if we have reach this point, no winning condition met
        return false;
    }
    /**
     * @return bool value that checks if a win has occurred
     * true if the entire board is full and there is no winner
     */
    public boolean isTie() {
        // check if entire board is full, then there is no winner
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }


}
