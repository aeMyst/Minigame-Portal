package test.tictactoe;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardManagerTest {

    private BoardManager boardManager;

    @Before
    public void setup() {
        // a new BoardManager before each test
        boardManager = new BoardManager();
    }

    /**
     * Test the isValidMove method to ensure it correctly validates moves.
     */
    @Test
    public void testIsValidMove() {
        // Valid moves
        assertTrue("Move (0,0) should be valid", boardManager.isValidMove(0, 0));
        assertTrue("Move (2,2) should be valid", boardManager.isValidMove(2, 2));

        // Out-of-bounds moves
        assertFalse("Move (-1, 0) should be invalid as it's out of bounds", boardManager.isValidMove(-1, 0));
        assertFalse("Move (0, -1) should be invalid as it's out of bounds", boardManager.isValidMove(0, -1)); // col < 0
        assertFalse("Move (0, 3) should be invalid as it's out of bounds", boardManager.isValidMove(0, 3));
        assertFalse("Move (3, 0) should be invalid as it's out of bounds", boardManager.isValidMove(3, 0));

        // Occupied cell
        boardManager.placeSymbol('X', 1, 1);
        assertFalse("Move (1,1) should be invalid as it's already occupied", boardManager.isValidMove(1, 1));
    }

    /**
     * test whether it return correct boolean for no winner if board is full
     */
    @Test
    public void testNoWinner() {
        // Fill the board without any winning condition
        boardManager.placeSymbol('X', 0, 0);        //
        boardManager.placeSymbol('O', 0, 1);        // How the Board looks:
        boardManager.placeSymbol('X', 0, 2);        //  X   |   O   |   X
        boardManager.placeSymbol('O', 1, 0);        // -------------------
        boardManager.placeSymbol('X', 1, 1);        //  O   |   X   |   X
        boardManager.placeSymbol('X', 1, 2);        // -------------------
        boardManager.placeSymbol('O', 2, 0);        //  O   |   X   |   O
        boardManager.placeSymbol('X', 2, 1);        //
        boardManager.placeSymbol('O', 2, 2);        // Clearly no winner, as per test results

        assertFalse("No winner should be detected", boardManager.isWinner('X'));
        assertFalse("No winner should be detected", boardManager.isWinner('O'));
    }

    /**
     * test to make sure no win if no pieces on board
     */
    @Test
    public void testIsNotTieEmptyBoard() {
        // there shouldn't be a tie if the board is empty
        assertFalse("An empty board should not be a tie", boardManager.isTie());
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
        setup();
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 0, 1);
        boardManager.placeSymbol('X', 0, 2);
        assertTrue("X should be the winner in row 0", boardManager.isWinner('X'));

        // Place 'O' in a winning column
        setup();
        boardManager.placeSymbol('O', 0, 0);
        boardManager.placeSymbol('O', 1, 0);
        boardManager.placeSymbol('O', 2, 0);
        assertTrue("O should be the winner in column 0", boardManager.isWinner('O'));

        setup();
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 0, 1);
        boardManager.placeSymbol('X', 1, 2);
        assertFalse("No winner should be found", boardManager.isWinner('X'));

        // Place 'X' in a winning left-right diagonal
        setup();
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 1, 1);
        boardManager.placeSymbol('X', 2, 2);
        assertTrue("X should be the winner in the diagonal", boardManager.isWinner('X'));

        setup();
        boardManager.placeSymbol('O', 2, 0);
        boardManager.placeSymbol('O', 1, 1);
        boardManager.placeSymbol('O', 0, 2);
        assertTrue("O should be the winner in the diagonal", boardManager.isWinner('O'));

        // Place 'O' in the right-left diagonal
        setup();
        boardManager.placeSymbol('O', 0, 2);
        boardManager.placeSymbol('O', 1, 1);
        boardManager.placeSymbol('O', 2, 0);
        assertTrue("O should be the winner in the reverse diagonal", boardManager.isWinner('O'));

        setup();
        boardManager.placeSymbol('X', 2, 2);
        boardManager.placeSymbol('X', 1, 1);
        boardManager.placeSymbol('X', 0, 0);
        assertTrue("O should be the winner in the reverse diagonal", boardManager.isWinner('X'));

        // BoardManager testDiagonal = new BoardManager();
        setup();
        boardManager.placeSymbol('O', 0, 0);
        boardManager.placeSymbol('O', 0, 2);
        boardManager.placeSymbol('O', 2, 2);
        assertFalse("No winner should be found", boardManager.isWinner('O'));

        // BoardManager testDiagonal1 = new BoardManager();
        setup();
        boardManager.placeSymbol('X', 0, 2);
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 2, 0);
        assertFalse("No winner should be found", boardManager.isWinner('X'));
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
