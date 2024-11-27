package test.tiktaktoe;

import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.Board;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void TestConstructorInit(){
        Board board = new Board();
        char[][] board_array = board.getBoard();

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                // Assert all cells are initialized to ' '
                assertEquals(' ', board_array[i][j]);
            }
        }
    }

    @Test
    public void TestPrintBoard(){
        Board board= new Board();
        char[][] board_array = board.getBoard();
        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Call printBoard and restore original System.out
        Board.printBoard(board_array);
        System.setOut(originalOut);

        // Verify the output matches the expected printed board format
        String expectedOutput =
                "      1       2       3   \n" +
                        "    -----------------------\n" +
                        "1  |     |     |     |  \n" +
                        "    -----------------------\n" +
                        "2  |     |     |     |  \n" +
                        "    -----------------------\n" +
                        "3  |     |     |     |  \n" +
                        "    -----------------------\n";

        assertEquals(expectedOutput.replace("\r\n", "\n"), outputStream.toString().replace("\r\n", "\n"));
    }


}
