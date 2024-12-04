package connect4;

import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Board;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class Connect4BoardTest {
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


}
