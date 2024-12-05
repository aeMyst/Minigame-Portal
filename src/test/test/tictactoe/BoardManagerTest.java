package tiktaktoe;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;

import static org.junit.Assert.*;

public class BoardManagerTest {

    private BoardManager boardManager;

    @Before
    public void setUp() {
        // Initialize a new BoardManager before each test
        boardManager = new BoardManager();
    }

    /**
     * Test the isValidMove method to ensure it correctly validates moves.
     */
    @Test
    public void testIsValidMove() {
        // Initially, all cells are empty, so any move should be valid
        assertTrue("Move (0,0) should be valid", boardManager.isValidMove(0, 0));
        assertTrue("Move (2,2) should be valid", boardManager.isValidMove(2, 2));

        // Try moving to a filled position (after placing a symbol)
        boardManager.placeSymbol('X', 1, 1);
        assertFalse("Move (1,1) should be invalid as it's already occupied", boardManager.isValidMove(1, 1));

        // Try moving out of bounds
        //assertFalse("Move (-1, 0) should be invalid as it's out of bounds", boardManager.isValidMove(-1, 0));
        //assertFalse("Move (0, 3) should be invalid as it's out of bounds", boardManager.isValidMove(0, 3));
    }

    /**
     * Test the placeSymbol method to ensure it places symbols correctly.
     */
    @Test
    public void testPlaceSymbol() {
        // Place a symbol at (0, 0) and check if it's placed
        boardManager.placeSymbol('X', 0, 0);
        assertEquals("The symbol at position (0,0) should be 'X'", 'X', boardManager.getBoard()[0][0]);

        // Place a symbol at (1, 1) and check if it's placed
        boardManager.placeSymbol('O', 1, 1);
        assertEquals("The symbol at position (1,1) should be 'O'", 'O', boardManager.getBoard()[1][1]);
    }

    /**
     * Test the isWinner method to ensure it correctly detects a winner.
     */
    @Test
    public void testIsWinner() {
        // Place 'X' in a winning row
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 0, 1);
        boardManager.placeSymbol('X', 0, 2);
        assertTrue("X should be the winner in row 0", boardManager.isWinner('X'));

        // Place 'O' in a winning column
        boardManager.placeSymbol('O', 0, 0);
        boardManager.placeSymbol('O', 1, 0);
        boardManager.placeSymbol('O', 2, 0);
        assertTrue("O should be the winner in column 0", boardManager.isWinner('O'));

        // Place 'X' in a winning diagonal
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 1, 1);
        boardManager.placeSymbol('X', 2, 2);
        assertTrue("X should be the winner in the diagonal", boardManager.isWinner('X'));

        // Place 'O' in the other diagonal
        boardManager.placeSymbol('O', 0, 2);
        boardManager.placeSymbol('O', 1, 1);
        boardManager.placeSymbol('O', 2, 0);
        assertTrue("O should be the winner in the reverse diagonal", boardManager.isWinner('O'));
    }

    /**
     * Test the isTie method to ensure it correctly detects a tie condition.
     */
    @Test
    public void testIsTie() {
        // Fill the board without a winner
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('O', 0, 1);
        boardManager.placeSymbol('X', 0, 2);
        boardManager.placeSymbol('X', 1, 0);
        boardManager.placeSymbol('O', 1, 1);
        boardManager.placeSymbol('X', 1, 2);
        boardManager.placeSymbol('O', 2, 0);
        boardManager.placeSymbol('X', 2, 1);
        boardManager.placeSymbol('O', 2, 2);

        assertTrue("The game should be a tie", boardManager.isTie());
    }

    /**
     * Test the isTie method to ensure it returns false if there's still an empty space.
     */
    @Test
    public void testIsNotTie() {
        // Fill the board partially and ensure it's not a tie
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('O', 0, 1);
        boardManager.placeSymbol('X', 0, 2);
        boardManager.placeSymbol('X', 1, 0);
        boardManager.placeSymbol('O', 1, 1);

        assertFalse("The game should not be a tie yet", boardManager.isTie());
    }
}
