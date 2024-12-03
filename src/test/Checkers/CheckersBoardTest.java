package src.ca.ucalgary.seng300.gamelogic.Checkers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckersBoardTest {

    @Test
    public void testCreateBoard() {
        int[][] board = CheckersBoard.createBoard();
        assertEquals(8, board.length, "Board should have 8 rows.");
        assertEquals(8, board[0].length, "Board should have 8 columns.");

        // Validate pieces
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((row + col) % 2 != 0) {
                    if (row < 3) {
                        assertEquals(1, board[row][col], "Row " + row + " should contain player 1 pieces.");
                    } else if (row > 4) {
                        assertEquals(2, board[row][col], "Row " + row + " should contain player 2 pieces.");
                    } else {
                        assertEquals(0, board[row][col], "Middle rows should be empty.");
                    }
                } else {
                    assertEquals(0, board[row][col], "Light squares should be empty.");
                }
            }
        }
    }
}
