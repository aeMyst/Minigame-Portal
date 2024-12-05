package src.ca.ucalgary.seng300.checkers;

import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersBoard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckersBoardTest {

    @Test
    public void testCreateBoard() {
        int[][] board = CheckersBoard.createBoard();
        assertEquals("Board should have 8 rows.", 8, board.length);
        assertEquals("Board should have 8 columns.", 8, board[0].length);

        // Validate pieces
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((row + col) % 2 != 0) {
                    if (row < 3) {
                        assertEquals("Row " + row + " should contain player 1 pieces.", 1, board[row][col]);
                    } else if (row > 4) {
                        assertEquals("Row " + row + " should contain player 2 pieces.", 2, board[row][col]);
                    } else {
                        assertEquals("Middle rows should be empty.", 0, board[row][col]);
                    }
                } else {
                    assertEquals("Light squares should be empty.", 0, board[row][col]);
                }
            }
        }
    }
}
