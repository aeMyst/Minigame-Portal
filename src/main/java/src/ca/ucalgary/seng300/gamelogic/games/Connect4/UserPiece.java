package src.ca.ucalgary.seng300.gamelogic.games.Connect4;

import java.io.Console;

public class UserPiece implements IUserPiece {
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
        System.out.println("Enter column (1-7): ");
        int col = Integer.parseInt(console.readLine()) - 1;

        // when user finishes their input, this will return [x, y]
        return new int[]{1, col};
    }
}
