package Checkers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.Checkers.GameState;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

public class CheckersGameLogicTest {

    private CheckersGameLogic gameLogic;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setup() {
        player1 = new Player( "Player 1");
        player2 = new Player( "Player 2");
        gameLogic = new CheckersGameLogic(player1, player2);
    }

    @Test
    public void testPlayerSelectedPiece() {
        assertTrue(gameLogic.playerSelectedPiece(2, 1, player1), "Player 1 should be able to select their piece.");
        assertFalse(gameLogic.playerSelectedPiece(5, 0, player1), "Player 1 should not be able to select Player 2's piece.");
    }

    @Test
    public void testPlayerMovedPiece() {
        gameLogic.playerMovedPiece(2, 1, 3, 2, player1);
        int[][] board = gameLogic.getBoard();
        assertEquals(1, board[3][2], "Player 1's piece should be moved.");
        assertEquals(0, board[2][1], "Original position should be empty.");
    }

    @Test
    public void testPlayerCapturedPiece() {
        gameLogic.playerMovedPiece(5, 2, 4, 3, player2); // Move Player 2 piece into capture position
        assertTrue(gameLogic.playerCapturedPiece(2, 1, 4, 3, player1), "Player 1 should capture Player 2's piece.");
    }

    @Test
    public void testPromoteToKing() {
        gameLogic.playerMovedPiece(2, 1, 7, 2, player1);
        int[][] board = gameLogic.getBoard();
        assertEquals(3, board[7][2], "Player 1's piece should be promoted to king.");
    }

    @Test
    public void testForfeitGame() {
        gameLogic.forfeitGame();
        Assertions.assertEquals(GameState.PLAYER2_WIN, gameLogic.getGameState(), "Player 2 should win after Player 1 forfeits.");
    }
}
