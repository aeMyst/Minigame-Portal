package src.ca.ucalgary.seng300.checkers;

import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersBoard;

import static org.junit.Assert.*;

/**
 * Test suite for the CheckersBoard class.
 */
public class CheckersBoardTest {

    @Test
    public void testCreateBoard() {
        int[][] board = CheckersBoard.createBoard();
        assertNotNull("The board should not be null", board);
        assertEquals("The board should have 8 rows", 8, board.length);
        for (int[] row : board) {
            assertEquals("Each row should have 8 columns", 8, row.length);
        }
    }

    @Test
    public void testInitializeBoardWhitePieces() {
        int[][] board = CheckersBoard.createBoard();

        // Check rows 0-2 for white pieces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    assertEquals("White pieces should occupy light squares in rows 0-2", 1, board[row][col]);
                } else {
                    assertEquals("Dark squares should be empty in rows 0-2", 0, board[row][col]);
                }
            }
        }
    }

    @Test
    public void testInitializeBoardBlackPieces() {
        int[][] board = CheckersBoard.createBoard();

        // Check rows 5-7 for black pieces
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    assertEquals("Black pieces should occupy light squares in rows 5-7", 2, board[row][col]);
                } else {
                    assertEquals("Dark squares should be empty in rows 5-7", 0, board[row][col]);
                }
            }
        }
    }

    @Test
    public void testInitializeBoardEmptySquares() {
        int[][] board = CheckersBoard.createBoard();

        // Check rows 3 and 4 for empty squares
        for (int row = 3; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                assertEquals("Middle rows should be empty", 0, board[row][col]);
            }
        }
    }
}

