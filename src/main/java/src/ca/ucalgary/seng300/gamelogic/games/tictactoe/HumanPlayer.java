package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

import java.util.Scanner;

public class HumanPlayer implements Player {

    private char symbol;

    public HumanPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public int[] getMove(Scanner scanner, Board board) {
        // some row some column
        return new int[0];
    }
}
