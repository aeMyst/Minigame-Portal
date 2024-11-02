package src.ca.ucalgary.seng300.gamelogic.games.Connect4;

import java.io.Console;

public class UserPiece {
    private int piece;

    public UserPiece(int piece) {
        this.piece = piece;
    }

    public int getPiece() {
        return piece;
    }

    public int[] userMove(Connect4Board board) {
        Console console = System.console();

        // get user input
        System.out.println("Enter row (1-6): ");
        int row = Integer.parseInt(console.readLine()) - 1;
        System.out.println("Enter column (1-7): ");
        int col = Integer.parseInt(console.readLine()) - 1;

        // when user finishes their input, this will return [x, y]
        return new int[]{row, col};
    }
}
