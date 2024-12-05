package src.ca.ucalgary.seng300.connect4;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Board;

import static org.junit.Assert.*;

/**
 * Test suite for Connect4Board class.
 */
public class Connect4BoardTest {

    private Connect4Board board;

    @Before
    public void setUp() {
        // Initialize a new Connect4Board before each test
        board = new Connect4Board();
    }

    /**
     * Test the constructor of Connect4Board.
     * Verifies that a new board is created with the correct dimensions (6 rows and 7 columns).
     */
    @Test
    public void testConstructor() {
        int[][] boardState = board.getBoard();

        // Ensure the board is 6 rows by 7 columns
        assertEquals(6, boardState.length);
        for (int i = 0; i < boardState.length; i++) {
            assertEquals(7, boardState[i].length);
        }
    }

    /**
     * Test the getBoard method.
     * Verifies that the getBoard method returns the correct 2D array representing the board state.
     */
    @Test
    public void testGetBoard() {
        int[][] boardState = board.getBoard();

        // Ensure the board is initialized to 0 (empty spaces)
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                assertEquals(0, boardState[i][j]);
            }
        }
    }

    /**
     * Test printBoard method to check if the method prints the board in the correct format.
     * This is a more of a manual verification test for the print format.
     * Since printBoard outputs to the console, we won't assert anything in this test.
     */
    @Test
    public void testPrintBoard() {
        // Here, we can't assert the printed output directly.
        // However, you can manually verify the output after running the tests.
        Connect4Board.printBoard(board.getBoard());
    }

    /**
     * Test the printBoard method with a custom board setup.
     * This verifies if the printBoard method correctly prints a non-empty board.
     */
    @Test
    public void testPrintCustomBoard() {
        int[][] customBoard = {
                {1, 2, 1, 2, 1, 2, 1},
                {2, 1, 2, 1, 2, 1, 2},
                {1, 2, 1, 2, 1, 2, 1},
                {2, 1, 2, 1, 2, 1, 2},
                {1, 2, 1, 2, 1, 2, 1},
                {2, 1, 2, 1, 2, 1, 2}
        };

        // Use the printBoard method to display the custom board
        Connect4Board.printBoard(customBoard);
    }

    /**
     * Test if the board initializes with the correct empty state.
     * Verifies that all positions are set to 0 initially.
     */
    @Test
    public void testInitialBoardState() {
        int[][] boardState = board.getBoard();

        // Ensure that the entire board is initialized with 0s (empty spaces)
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                assertEquals(0, boardState[i][j]);
            }
        }
    }
}
