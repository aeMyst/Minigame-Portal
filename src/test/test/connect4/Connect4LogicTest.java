package test.connect4;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;

import static org.junit.Assert.*;

/**
 * Test suite for Connect4Logic class.
 */
public class Connect4LogicTest {

    private Connect4Logic gameLogic;
    private int[][] board;

    @Before
    public void setUp() {
        gameLogic = new Connect4Logic();
        board = gameLogic.getBoard(); // Initialize the board from Connect4Board class
    }

    /**
     * Test the valid method.
     * Verifies that valid positions are within the bounds of the board.
     */
    @Test
    public void testValidPosition() {
        assertTrue(gameLogic.valid(board, 0, 0)); // top-left corner (valid)
        assertTrue(gameLogic.valid(board, 5, 6)); // bottom-right corner (valid)
        assertFalse(gameLogic.valid(board, -1, 0)); // Invalid row
        assertFalse(gameLogic.valid(board, 0, 7)); // Invalid column
        assertFalse(gameLogic.valid(board, 6, 6)); // Invalid row (out of bounds)
    }

    /**
     * Test the boardFull method.
     * Verifies that the method returns the correct result when the board is full or has empty spaces.
     */
    @Test
    public void testBoardFull() {
        // Make the board full by setting all positions to non-zero
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 1; // Fill with player 1's pieces
            }
        }

        assertTrue(gameLogic.boardFull(board)); // Board should be full now
        board[0][0] = 0; // Empty one spot
        assertFalse(gameLogic.boardFull(board)); // Now, the board should have empty spots
    }

    /**
     * Test the placePiece method.
     * Verifies that pieces are placed correctly in the first available row of a column.
     */
    @Test
    public void testPlacePiece() {
        assertTrue(gameLogic.placePiece(board, 0, 1)); // Place piece in column 0
        assertEquals(1, board[5][0]); // The piece should be placed in the bottom-most row

        assertTrue(gameLogic.placePiece(board, 1, 2)); // Place piece in column 1
        assertEquals(2, board[5][1]); // The piece should be placed in the bottom-most row

        // Test column full
        for (int i = 0; i < 6; i++) {
            gameLogic.placePiece(board, 2, 1); // Fill column 2
        }
        assertFalse(gameLogic.placePiece(board, 2, 1)); // Column 2 should now be full
    }

    /**
     * Test the horizontalWin method.
     * Verifies that the horizontal win condition is detected correctly.
     */
    @Test
    public void testHorizontalWin() {
        // Horizontal win in the second row
        board[5][0] = 1;
        board[5][1] = 1;
        board[5][2] = 1;
        board[5][3] = 1;

        assertTrue(gameLogic.horizontalWin(board, 1)); // Player 1 should win horizontally

        // Reset board and test for no horizontal win
        for (int i = 0; i < 4; i++) {
            board[5][i] = 0; // Reset the row
        }
        assertFalse(gameLogic.horizontalWin(board, 1)); // No win condition now
    }

    /**
     * Test the verticalWin method.
     * Verifies that the vertical win condition is detected correctly.
     */
    @Test
    public void testVerticalWin() {
        // Vertical win in the first column
        board[0][0] = 1;
        board[1][0] = 1;
        board[2][0] = 1;
        board[3][0] = 1;

        assertTrue(gameLogic.verticalWin(board, 1)); // Player 1 should win vertically

        // Reset board and test for no vertical win
        for (int i = 0; i < 4; i++) {
            board[i][0] = 0; // Reset the column
        }
        assertFalse(gameLogic.verticalWin(board, 1)); // No win condition now
    }

    /**
     * Test the backslashWin method.
     * Verifies that the backslash diagonal win condition is detected correctly.
     */
    @Test
    public void testBackslashWin() {
        // Backslash diagonal win (from top-left to bottom-right)
        board[0][0] = 1;
        board[1][1] = 1;
        board[2][2] = 1;
        board[3][3] = 1;

        assertTrue(gameLogic.backslashWin(board, 1)); // Player 1 should win diagonally

        // Reset board and test for no backslash win
        for (int i = 0; i < 4; i++) {
            board[i][i] = 0; // Reset the diagonal
        }
        assertFalse(gameLogic.backslashWin(board, 1)); // No win condition now
    }

    /**
     * Test the forwardslashWin method.
     * Verifies that the forwardslash diagonal win condition is detected correctly.
     */
    @Test
    public void testForwardslashWin() {
        // Forwardslash diagonal win (from top-right to bottom-left)
        board[0][3] = 1;
        board[1][2] = 1;
        board[2][1] = 1;
        board[3][0] = 1;

        assertTrue(gameLogic.forwardslashWin(board, 1)); // Player 1 should win diagonally

        // Reset board and test for no forwardslash win
        for (int i = 0; i < 4; i++) {
            board[i][3 - i] = 0; // Reset the diagonal
        }
        assertFalse(gameLogic.forwardslashWin(board, 1)); // No win condition now
    }
}
