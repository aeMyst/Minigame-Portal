package src.ca.ucalgary.seng300;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import src.ca.ucalgary.seng300.gamelogic.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.PlayerManager;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

/**
 * Test suite for PlayerManager class in the Tic-Tac-Toe game.
 */
public class PlayerManagerTest {

    private Player player1Data;
    private Player player2Data;
    private HumanPlayer player1;
    private HumanPlayer player2;
    private PlayerManager playerManager;

    @Before
    public void setUp() {
        // Initialize Player objects for player1 and player2
        player1Data = new Player("TicTacToe", "Player1", 1000, 10, 5, 3);
        player2Data = new Player("TicTacToe", "Player2", 1200, 12, 4, 2);

        // Create HumanPlayer objects using the Player data
        player1 = new HumanPlayer(player1Data, 'X');
        player2 = new HumanPlayer(player2Data, 'O');

        // Initialize the PlayerManager
        playerManager = new PlayerManager(player1, player2);
    }

    /**
     * Test the constructor and getCurrentPlayer method.
     */
    @Test
    public void testConstructorAndGetCurrentPlayer() {
        // Ensure that the current player is player1 at the start
        assertEquals("The current player should be player1", player1, playerManager.getCurrentPlayer());
    }

    /**
     * Test the switchPlayer method for switching turns.
     */
    @Test
    public void testSwitchPlayer() {
        // Initially, the current player should be player1
        assertEquals("The current player should be player1", player1, playerManager.getCurrentPlayer());

        // Switch player, it should now be player2
        playerManager.switchPlayer();
        assertEquals("After switching, the current player should be player2", player2, playerManager.getCurrentPlayer());

        // Switch again, it should be back to player1
        playerManager.switchPlayer();
        assertEquals("After switching again, the current player should be player1", player1, playerManager.getCurrentPlayer());
    }
}


