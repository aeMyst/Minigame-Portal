package test.tiktaktoe;

import org.junit.jupiter.api.Test;
import org.junit.*;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.Board;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.Game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardManagerTest {
    BoardManager boardManager;
    private char[][] board;
    @Before
    public void setup(){
        Board board1=new Board();
        board= board1.getBoard();
        boardManager = new BoardManager();

    }
    @Test
    public void isValidMoveTest(){
        assertTrue(boardManager.isValidMove(2,2));
        assertFalse(boardManager.isValidMove(-1,4));
        boardManager.placeSymbol('X',1,2);
        assertFalse(boardManager.isValidMove(1,2));



    }
    private void isWinnerTest(){
        boardManager.placeSymbol('X',1,1);
        boardManager.placeSymbol('X', 1,3);
        assertTrue(boardManager.isWinner('X'));
    }
    private void isTieTest(){
        
    }
}
