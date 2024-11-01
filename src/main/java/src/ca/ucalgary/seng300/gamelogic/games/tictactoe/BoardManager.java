package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

public class BoardManager extends Board {

    // check if player has made a valid move
    public boolean isValidMove(int row, int col) {
        // logic here
        return true;
    }

    public void placeSymbol(char symbol, int row, int col) {
        // logic here
        // essentially just place symbol/player in place
    }

    public boolean isWinner(char symbol) {
        //determine winner of game
        // this should be constantly checked
        return true;
    }

    public boolean isTie() {
        // determine if game is tie (in other words, full board no winner)
        return true;
    }



}
