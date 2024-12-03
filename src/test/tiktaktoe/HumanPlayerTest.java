package tiktaktoe;

import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

import static org.junit.Assert.assertEquals;

public class HumanPlayerTest {
    private Player player1;
    private Player player2;

        @Test
        public void testGetSymbol() {
            // Test with 'X'
            HumanPlayer playerX = new HumanPlayer(player1,'X');
            assertEquals('X', playerX.getSymbol());

            // Test with 'O'
            HumanPlayer playerO = new HumanPlayer(player2,'O');
            assertEquals('O', playerO.getSymbol());
        }

        // The test for getMove is skipped due to reliance on console input.
    }
