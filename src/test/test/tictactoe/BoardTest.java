package src.ca.ucalgary.seng300.tictactoe;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.Board;

import static org.junit.Assert.*;

/**
 * Test suite for Board class in the Tic-Tac-Toe game.
 */
public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        // Initialize a new Board instance before each test
        board = new Board();
    }

    /**
     * Test the constructor of the Board class to ensure the board is initialized correctly.
     */
    @Test
    public void testBoardInitialization() {
        // Get the board after initialization
        char[][] boardArray = board.getBoard();

        // Verify that the board is 3x3
        assertEquals("Board should have 3 rows", 3, boardArray.length);
        for (int i = 0; i < 3; i++) {
            assertEquals("Each row should have 3 columns", 3, boardArray[i].length);
        }

        // Verify that all positions are initialized with empty spaces
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals("Each position should be empty space", ' ', boardArray[i][j]);
            }
        }
    }

    /**
     * Test the getBoard method to ensure it returns the current state of the board.
     */
    @Test
    public void testGetBoard() {
        // Initially, the board should be empty
        char[][] boardArray = board.getBoard();

        // Verify the entire board is empty (filled with spaces)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals("Board should initially have empty spaces", ' ', boardArray[i][j]);
            }
        }
    }

    /**
     * Test the printBoard method.
     * Note: Since printBoard outputs to the console, we will not directly test the output here.
     * However, we could mock System.out to capture the output if necessary.
     */
    @Test
    public void testPrintBoard() {
        // This test is not checking the output directly, as it's meant for console printing
        // We can manually invoke the method to ensure no exceptions are thrown
        Board.printBoard(board.getBoard());

        // You could also use a mocking framework to capture the output to test it further
        // This part is left as a basic functionality test
    }

    /**
     * Test that the board state changes when a move is made.
     * For simplicity, we simulate placing a piece on the board.
     */
    @Test
    public void testPlaceMove() {
        // Simulate a move at position (1,1) (center of the board)
        char[][] boardArray = board.getBoard();
        boardArray[1][1] = 'X';  // Player X places a piece

        // Verify that the center position is updated correctly
        assertEquals("The center position should be 'X' after the move", 'X', boardArray[1][1]);

        // Simulate another move at position (0,0) (top-left corner)
        boardArray[0][0] = 'O';  // Player O places a piece

        // Verify that the top-left position is updated correctly
        assertEquals("The top-left position should be 'O' after the move", 'O', boardArray[0][0]);
    }

    /**
     * Test that the printBoard method handles a full board correctly.
     */
    @Test
    public void testPrintFullBoard() {
        // Set up a full board (example with X and O moves)
        char[][] boardArray = board.getBoard();
        boardArray[0][0] = 'X';
        boardArray[0][1] = 'O';
        boardArray[0][2] = 'X';
        boardArray[1][0] = 'O';
        boardArray[1][1] = 'X';
        boardArray[1][2] = 'O';
        boardArray[2][0] = 'X';
        boardArray[2][1] = 'O';
        boardArray[2][2] = 'X';

        // Print the board to ensure it doesn't cause any issues with the full state
        Board.printBoard(boardArray);

        // This test doesn't need assertions; it's a check for the proper handling of the board's full state
    }
}
