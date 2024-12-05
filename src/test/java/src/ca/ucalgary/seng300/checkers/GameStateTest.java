package src.ca.ucalgary.seng300.checkers;

import org.junit.Assert;
import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.Checkers.GameState;

public class GameStateTest {

    @Test
    public void testGameStateEnum() {
        Assert.assertEquals("GameState should have START state.", GameState.START, GameState.valueOf("START"));
        Assert.assertEquals("GameState should have PLAYER1_WIN state.", GameState.PLAYER1_WIN, GameState.valueOf("PLAYER1_WIN"));
        Assert.assertEquals("GameState should have PLAYER2_WIN state.", GameState.PLAYER2_WIN, GameState.valueOf("PLAYER2_WIN"));
    }
}
