package test.connect4;

import src.ca.ucalgary.seng300.gamelogic.Connect4.TurnManager;
import src.ca.ucalgary.seng300.gamelogic.Connect4.UserPiece;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test suite for TurnManager class.
 */
public class TurnManagerTest {

    private Player playerRed;
    private Player playerBlue;
    private UserPiece redPiece;
    private UserPiece bluePiece;
    private TurnManager turnManager;

    @Before
    public void setUp() {
        // Create Player objects with necessary attributes
        playerRed = new Player("Connect4", "RedPlayer", 1200, 10, 5, 2);  // Red player with elo 1200, 10 wins, 5 losses, 2 ties
        playerBlue = new Player("Connect4", "BluePlayer", 1150, 8, 6, 3); // Blue player with elo 1150, 8 wins, 6 losses, 3 ties

        // Create UserPiece objects for testing
        redPiece = new UserPiece(playerRed, 1);  // Red piece (1) for Red player
        bluePiece = new UserPiece(playerBlue, 2); // Blue piece (2) for Blue player

        // Create TurnManager with red going first
        turnManager = new TurnManager(redPiece, bluePiece);
    }

    /**
     * Test the initial player at the start of the game.
     */
    @Test
    public void testInitialTurn() {
        // At the start, red should be the current player
        assertEquals("The first player should be RedPlayer", redPiece, turnManager.getCurrentPlayer());
    }

    /**
     * Test that the turn changes after calling changeTurns.
     */
    @Test
    public void testChangeTurns() {
        // Initially, red is the current player
        assertEquals("The first player should be RedPlayer", redPiece, turnManager.getCurrentPlayer());

        // Change turns to blue
        turnManager.changeTurns();
        assertEquals("After one turn, the current player should be BluePlayer", bluePiece, turnManager.getCurrentPlayer());

        // Change turns back to red
        turnManager.changeTurns();
        assertEquals("After two turns, the current player should be RedPlayer", redPiece, turnManager.getCurrentPlayer());
    }

    /**
     * Test the setPlayer method in the context of the TurnManager.
     * Verifies that the current player is correctly swapped when the turn changes.
     */
    @Test
    public void testChangeTurnsWithPlayer() {
        // Initially, red should be the current player
        assertEquals("The first player should be RedPlayer", redPiece, turnManager.getCurrentPlayer());

        // Change turns twice and check that the players alternate correctly
        turnManager.changeTurns();  // Now it should be blue
        assertEquals("After one turn, it should be BluePlayer", bluePiece, turnManager.getCurrentPlayer());

        turnManager.changeTurns();  // Now it should be red again
        assertEquals("After two turns, it should be RedPlayer", redPiece, turnManager.getCurrentPlayer());
    }

    /**
     * Test the behavior of TurnManager when players are swapped out.
     * Verifies that the turn manager can still handle turn changes correctly.
     */
    @Test
    public void testTurnManagerWithSwappedPlayers() {
        // Create new players and pieces
        Player newRedPlayer = new Player("Connect4", "NewRedPlayer", 1300, 15, 4, 3);
        Player newBluePlayer = new Player("Connect4", "NewBluePlayer", 1250, 12, 5, 2);
        UserPiece newRedPiece = new UserPiece(newRedPlayer, 1);
        UserPiece newBluePiece = new UserPiece(newBluePlayer, 2);

        // Create a new TurnManager with the swapped players
        turnManager = new TurnManager(newRedPiece, newBluePiece);

        // Verify that the current player is the new red player
        assertEquals("The first player should be NewRedPlayer", newRedPiece, turnManager.getCurrentPlayer());

        // Swap turns and check the current player
        turnManager.changeTurns();
        assertEquals("After one turn, the current player should be NewBluePlayer", newBluePiece, turnManager.getCurrentPlayer());

        turnManager.changeTurns();
        assertEquals("After two turns, the current player should be NewRedPlayer", newRedPiece, turnManager.getCurrentPlayer());
    }
}
