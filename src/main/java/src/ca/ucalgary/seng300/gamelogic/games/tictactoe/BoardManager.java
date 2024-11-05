package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

public class BoardManager extends Board {

    // check if player has made a valid move
    public boolean isValidMove(int row, int col) {
        boolean isRowInBounds = row >= 0 && row < 3; // checking row is valid
        boolean isColInBounds = col >= 0 && col < 3; // checking column is valid
        boolean isCellEmpty = board[row][col] == ' '; // checking if space is empty

        boolean check = isRowInBounds && isColInBounds && isCellEmpty; // combination

        return check;
    }

    public void placeSymbol(char symbol, int row, int col) {
        board[row][col] = symbol; // place symbol where user picked if valid
    }

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
