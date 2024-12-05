package src.ca.ucalgary.seng300.tictactoe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import src.ca.ucalgary.seng300.gamelogic.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

/**
 * Test suite for HumanPlayer class in the Tic-Tac-Toe game.
 */
public class HumanPlayerTest {

    private Player player;
    private HumanPlayer humanPlayer;

    @Before
    public void setUp() {
        // Initialize the Player and HumanPlayer objects before each test
        player = new Player("TicTacToe", "Player1", 1000, 10, 5, 3);
        humanPlayer = new HumanPlayer(player, 'X');
    }

    /**
     * Test the constructor and getter methods for the player and symbol fields.
     */
    @Test
    public void testConstructorAndGetters() {
        // Verify the player is correctly set
        assertEquals("Player ID should be 'Player1'", "Player1", humanPlayer.getPlayer().getPlayerID());
        assertEquals("Player's game type should be 'TicTacToe'", "TicTacToe", humanPlayer.getPlayer().getGameType());

        // Verify the symbol is correctly set
        assertEquals("The symbol should be 'X'", 'X', humanPlayer.getSymbol());
    }

    /**
     * Test the setter and getter methods for the player.
     */
    @Test
    public void testSetPlayer() {
        // Create a new player and set it
        Player newPlayer = new Player("TicTacToe", "Player2", 1200, 12, 4, 2);
        humanPlayer.setPlayer(newPlayer);

        // Verify the player has been updated
        assertEquals("Player ID should be 'Player2' after setting a new player", "Player2", humanPlayer.getPlayer().getPlayerID());
        assertEquals("Player's game type should still be 'TicTacToe'", "TicTacToe", humanPlayer.getPlayer().getGameType());
    }

    /**
     * Test the getter for the symbol.
     */
    @Test
    public void testGetSymbol() {
        // Verify the symbol is 'X'
        assertEquals("The symbol of the player should be 'X'", 'X', humanPlayer.getSymbol());
    }
}