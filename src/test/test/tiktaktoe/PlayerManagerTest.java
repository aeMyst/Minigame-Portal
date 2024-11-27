package test.tiktaktoe;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.PlayerManager;

import static org.junit.Assert.assertEquals;

public class PlayerManagerTest {
    private HumanPlayer player1;
    private HumanPlayer player2;
    private PlayerManager playerManager;


    @Before
    public void setup(){
        player1 = new HumanPlayer('X');
        player2 = new HumanPlayer('O');
        playerManager = new PlayerManager(player1,player2);
    }

    @Test
    public void TestInitialPlayer(){
        // Verify that the initial current player is player1
        assertEquals("Initial player should be player1", player1, playerManager.getCurrentPlayer());

        // Verify the symbol of the initial current player
        assertEquals("Initial player's symbol should be 'X'", 'X', playerManager.getCurrentPlayer().getSymbol());
    }

    @Test
    public void TestSwitchPlayer(){
        playerManager.switchPlayer();
        assertEquals("After first switch, current player should be player2", player2, playerManager.getCurrentPlayer());
        assertEquals("Player2's symbol should be 'O'", 'O', playerManager.getCurrentPlayer().getSymbol());

        // Switch the player again and verify that it's player1's turn
        playerManager.switchPlayer();
        assertEquals("After second switch, current player should be player1", player1, playerManager.getCurrentPlayer());
        assertEquals("Player1's symbol should be 'X'", 'X', playerManager.getCurrentPlayer().getSymbol());
    }
    @Test
    public void TestMultipleSwitches(){
        playerManager.switchPlayer(); // to player2
        playerManager.switchPlayer(); // to player1
        playerManager.switchPlayer(); // to player2

        assertEquals("After three switches, current player should be player2", player2, playerManager.getCurrentPlayer());
        assertEquals("Player2's symbol should be 'O'", 'O', playerManager.getCurrentPlayer().getSymbol());
    }
}


