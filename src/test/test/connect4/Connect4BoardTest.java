package connect4;

import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Board;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

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
    public void TestConstructorInit(){
        Connect4Board board = new Connect4Board();
        int[][] board_array = board.getBoard();

        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                // Assert all cells are initialized to 0
                assertEquals(0, board_array[i][j]);
            }
        }
    }

    /**
     * Test printBoard method to check if the method prints the board in the correct format.
     * This is a more of a manual verification test for the print format.
     * Since printBoard outputs to the console, we won't assert anything in this test.
     */
    @Test
    public void TestPrintBoard(){
        Connect4Board board= new Connect4Board();
        int[][] board_array = board.getBoard();
        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Call printBoard and restore original System.out
        board.printBoard(board_array);
        System.setOut(originalOut);

        // Verify the output matches the expected printed board format
        String expectedOutput =
                "   1   2   3   4   5   6   7   \n" +
                        " -----------------------------   \n" +
                        " | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n" +
                        " -----------------------------   \n" +
                        " | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n" +
                        " -----------------------------   \n" +
                        " | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n" +
                        " -----------------------------   \n" +
                        " | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n" +
                        " -----------------------------   \n" +
                        " | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n" +
                        " -----------------------------   \n" +
                        " | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n" +
                        " -----------------------------   \n";

        assertEquals(expectedOutput.replace("\r\n", "\n"), outputStream.toString().replace("\r\n", "\n"));
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
