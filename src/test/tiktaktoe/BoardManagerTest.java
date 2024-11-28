package tiktaktoe;

import org.junit.jupiter.api.Test;
import org.junit.*;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.Board;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardManagerTest {
    BoardManager boardManager;
    private char[][] board;

    @Before
    public void setup() {
        Board board1 = new Board();
        board = board1.getBoard();
        boardManager = new BoardManager();

    }

    @Test
    public void isValidMoveTest() {
        assertTrue(boardManager.isValidMove(2, 2));
        assertFalse(boardManager.isValidMove(-1, 4));
        boardManager.placeSymbol('X', 1, 2);
        assertFalse(boardManager.isValidMove(1, 2));


    }
    @Test
    public void testIsWinner_ColumnWin() {
        boardManager.placeSymbol('O', 0, 1);
        boardManager.placeSymbol('O', 1, 1);
        boardManager.placeSymbol('O', 2, 1);
        assertTrue(boardManager.isWinner('O'));
    }

    @Test
    public void testIsWinner_DiagonalWinLeftToRight() {
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 1, 1);
        boardManager.placeSymbol('X', 2, 2);

    private void isWinnerTest() {
        boardManager.placeSymbol('X', 1, 1);
        boardManager.placeSymbol('X', 1, 3);
        assertTrue(boardManager.isWinner('X'));
    }

    @Test
    public void testIsWinner_DiagonalWinRightToLeft() {
        boardManager.placeSymbol('O', 0, 2);
        boardManager.placeSymbol('O', 1, 1);
        boardManager.placeSymbol('O', 2, 0);
        assertTrue(boardManager.isWinner('O'));
    }

    @Test
    public void testIsWinner_NoWin() {
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 0, 1);
        boardManager.placeSymbol('O', 0, 2);
        assertFalse(boardManager.isWinner('X'));
        assertFalse(boardManager.isWinner('O'));
    }

    @Test
    public void testIsTie_NotTie() {
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 0, 1);
        boardManager.placeSymbol('O', 0, 2);
        assertFalse(boardManager.isTie());
    }

    @Test
    public void testIsTie_TrueTie() {
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('O', 0, 1);
        boardManager.placeSymbol('X', 0, 2);
        boardManager.placeSymbol('X', 1, 0);
        boardManager.placeSymbol('O', 1, 1);
        boardManager.placeSymbol('X', 1, 2);
        boardManager.placeSymbol('O', 2, 0);
        boardManager.placeSymbol('X', 2, 1);
        boardManager.placeSymbol('O', 2, 2);
        assertTrue(boardManager.isTie());

    private void isTieTest() {

    }
}
