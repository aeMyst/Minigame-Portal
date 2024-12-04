package src.ca.ucalgary.seng300.checkers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.Checkers.GameState;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    @Test
    public void testGameStateEnum() {
        Assertions.assertEquals(GameState.START, GameState.valueOf("START"), "GameState should have START state.");
        assertEquals(GameState.PLAYER1_WIN, GameState.valueOf("PLAYER1_WIN"), "GameState should have PLAYER1_WIN state.");
        assertEquals(GameState.PLAYER2_WIN, GameState.valueOf("PLAYER2_WIN"), "GameState should have PLAYER2_WIN state.");
    }
}
