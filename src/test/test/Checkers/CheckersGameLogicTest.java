package Checkers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.Checkers.GameState;

public class CheckersGameLogicTest {

    private CheckersGameLogic game;
    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        // Initialize the players with dummy data for testing, using the Player constructor
        player1 = new Player("Checkers", "p1", 1500, 0, 0, 0);
        player2 = new Player("Checkers", "p2", 1500, 0, 0, 0);
        game = new CheckersGameLogic(player1, player2);
    }

    @Test
    public void testInitialGameState() {
        // Test the initial game state
        assertEquals(GameState.START, game.getGameState());
        assertEquals(2, game.getNumPlayers());
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    public void testPlayerSelectedPiece() {
        // Test that a player can select a valid piece
        assertTrue(game.playerSelectedPiece(5, 0, player1)); // Player 1 selects a piece
        assertFalse(game.playerSelectedPiece(5, 0, player2)); // Player 2 cannot select Player 1's piece
        assertFalse(game.playerSelectedPiece(3, 0, player1)); // Invalid position (empty)
    }

    @Test
    public void testPlayerMovedPiece() {
        // Test moving a piece
        assertTrue(game.playerMovedPiece(5, 0, 4, 1, player1)); // Valid move for Player 1
        assertFalse(game.playerMovedPiece(5, 0, 3, 1, player1)); // Invalid move (should be forward)
    }

    @Test
    public void testPlayerCapturedPiece() {
        // Test capturing an opponent's piece
        game.playerMovedPiece(5, 0, 4, 1, player1); // Move Player 1's piece
        game.playerMovedPiece(2, 1, 3, 2, player2); // Move Player 2's piece
        assertTrue(game.playerCapturedPiece(5, 0, 3, 2, player1)); // Valid capture for Player 1
        assertFalse(game.playerCapturedPiece(5, 0, 4, 2, player1)); // Invalid capture (no opponent piece)
    }

    @Test
    public void testKingPromotion() {
        // Test king promotion when reaching the opponent's back row
        game.playerMovedPiece(6, 1, 5, 2, player1); // Move Player 1's piece
        game.playerMovedPiece(1, 0, 2, 1, player2); // Move Player 2's piece
        game.playerMovedPiece(5, 2, 4, 3, player1); // Move Player 1's piece
        game.playerMovedPiece(2, 1, 3, 2, player2); // Move Player 2's piece
        game.playerMovedPiece(4, 3, 3, 4, player1); // Player 1 moves to the last row
        assertTrue(game.playerMovedPiece(3, 4, 2, 5, player1)); // Move to the last row for promotion
    }

    @Test
    public void testSwitchPlayer() {
        // Test switching players after a move
        assertEquals(player1, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(player2, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    public void testHasValidCapture() {
        // Test if a piece has valid captures
        game.playerMovedPiece(5, 0, 4, 1, player1);
        game.playerMovedPiece(2, 1, 3, 2, player2);
        assertTrue(game.hasValidCapture(5, 0, player1)); // Player 1 has a valid capture
        assertFalse(game.hasValidCapture(5, 1, player2)); // Player 2 has no valid capture
    }

    @Test
    public void testHasAnyValidCaptures() {
        // Test if any piece of a player has valid captures
        game.playerMovedPiece(5, 0, 4, 1, player1);
        game.playerMovedPiece(2, 1, 3, 2, player2);
        game.playerMovedPiece(3, 2, 4, 3, player1);
        assertTrue(game.hasAnyValidCaptures(player1)); // Player 1 has a valid capture
        assertFalse(game.hasAnyValidCaptures(player2)); // Player 2 has no valid captures
    }

    @Test
    public void testForfeitGame() {
        // Test forfeiting the game
        game.forfeitGame();
        assertEquals(GameState.PLAYER2_WIN, game.getGameState()); // Player 1 forfeits, Player 2 wins
    }

    @Test
    public void testInvalidMoveOutOfBounds() {
        // Test invalid moves outside of the board
        assertFalse(game.playerMovedPiece(0, 0, -1, -1, player1)); // Move out of bounds
        assertFalse(game.playerMovedPiece(7, 7, 8, 8, player2)); // Move out of bounds
    }

    @Test
    public void testValidCaptureWithKing() {
        // Test a valid capture using a king piece
        game.playerMovedPiece(6, 1, 5, 2, player1); // Move Player 1's piece
        game.playerMovedPiece(1, 0, 2, 1, player2); // Move Player 2's piece
        game.playerMovedPiece(5, 2, 4, 3, player1); // Move Player 1's piece
        game.playerMovedPiece(2, 1, 3, 2, player2); // Move Player 2's piece
        game.playerMovedPiece(4, 3, 3, 4, player1); // Move Player 1's piece
        game.promoteToKing(3, 4, player1); // Promote to king
        assertTrue(game.playerCapturedPiece(6, 1, 4, 3, player1)); // Valid capture by king
    }

    @Test
    public void testPlayerToCsv() {
        // Test converting player to CSV format
        String expectedCsv = "Checkers,p1,1500,0,0,0";
        assertEquals(expectedCsv, player1.toCsv());
    }

    @Test
    public void testPlayerFromCsv() {
        // Test creating a player from CSV string
        String csv = "Checkers,p2,1500,5,3,2";
        Player playerFromCsv = Player.fromCsv(csv);
        assertEquals("Checkers", playerFromCsv.getGameType());
        assertEquals("p2", playerFromCsv.getPlayerID());
        assertEquals(1500, playerFromCsv.getElo());
        assertEquals(5, playerFromCsv.getWins());
        assertEquals(3, playerFromCsv.getLosses());
        assertEquals(2, playerFromCsv.getTies());
    }

}
