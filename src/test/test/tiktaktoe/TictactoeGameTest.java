package tiktaktoe;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.PlayerManager;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.TictactoeGame;

import static org.junit.Assert.*;

public class TictactoeGameTest {
    private BoardManager boardManager;
    private PlayerManager playerManager;
    private HumanPlayer humanPlayerx;
    private HumanPlayer humanPlayero;
    private TictactoeGame tictactoeGame;

    @Before
    public void setup(){
        tictactoeGame = new TictactoeGame();
    }
    @Test
    public void testPlayerXWins() {
        // Simulate moves leading to a win for player X
        boardManager.placeSymbol('X', 0, 0);
        boardManager.placeSymbol('X', 0, 1);
        boardManager.placeSymbol('X', 0, 2);

        boolean isWinner = boardManager.isWinner('X');
        assertTrue("Player X should be the winner", isWinner);
    }

    @Test
    public void testPlayerOWins() {
        // Simulate moves leading to a win for player O
        boardManager.placeSymbol('O', 0, 0);
        boardManager.placeSymbol('O', 1, 0);
        boardManager.placeSymbol('O', 2, 0);

        boolean isWinner = boardManager.isWinner('O');
        assertTrue("Player O should be the winner", isWinner);
    }

    @Test
    public void testTie() {
        // Simulate a game that ends in a tie
        char[][] tieBoard = {
                {'X', 'O', 'X'},
                {'X', 'X', 'O'},
                {'O', 'X', 'O'}
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardManager.placeSymbol(tieBoard[i][j], i, j);
            }
        }

        boolean isTie = boardManager.isTie();
        assertTrue("The game should end in a tie", isTie);
    }

    @Test
    public void testInvalidMove() {
        // Attempt to make a move in an occupied cell
        boardManager.placeSymbol('X', 0, 0);
        boolean isValidMove = boardManager.isValidMove(0, 0);

        assertFalse("The move should be invalid as the cell is already occupied", isValidMove);
    }

    @Test
    public void testSwitchPlayer() {
        // Ensure players alternate correctly
        assertEquals("Player 1 (X) should start", 'X', playerManager.getCurrentPlayer().getSymbol());

        playerManager.switchPlayer();
        assertEquals("Player 2 (O) should play next", 'O', playerManager.getCurrentPlayer().getSymbol());

        playerManager.switchPlayer();
        assertEquals("Player 1 (X) should play again", 'X', playerManager.getCurrentPlayer().getSymbol());
    }

}
