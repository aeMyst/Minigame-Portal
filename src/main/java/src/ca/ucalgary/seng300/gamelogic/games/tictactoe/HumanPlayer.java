package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

import java.io.Console;

public class HumanPlayer implements IPlayer {

    private char symbol;

    /**
     *
     * @param symbol that will be assigned to a player X or O
     */
    public HumanPlayer(char symbol) {
        this.symbol = symbol;
    }

    /**
     *
     * @return symbol that was last assigned
     */

    @Override
    public char getSymbol() {
        return symbol;
    }

    /**
     *
     * @param board the current board before the move is applied
     * @return int [] row, col of where the move occurred
     */

    @Override
    public int[] getMove(Board board) {
        Console console = System.console();

        // get user input
        System.out.println("Enter row (1-3): ");
        int row = Integer.parseInt(console.readLine()) - 1;
        System.out.println("Enter column (1-3): ");
        int col = Integer.parseInt(console.readLine()) - 1;

        // when user finishes their input, this will return [x, y]
        return new int[]{row, col};
    }
}
