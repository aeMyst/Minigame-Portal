package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;

import java.util.ArrayList;

public class DataTest {
    /**
     * Test updating an existing player in the storage.
     */
    @Test
    public void testUpdateExistingPlayer() {
        // Initialize storage with one player
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        Storage storage = new Storage(players);

        // Update the player's information
        Player updatedPlayer1 = new Player("CONNECT4", "Player1", 1100, 15, 5, 0);
        storage.updatePlayer(updatedPlayer1);

        // Verify that the player's information is updated
        Player retrievedPlayer = storage.getPlayers().get(0);
        assertEquals("Player1", retrievedPlayer.getPlayerID());
        assertEquals(1100, retrievedPlayer.getElo());
        assertEquals(15, retrievedPlayer.getWins());
    }

    /**
     * Test adding a new player to the storage.
     */
    @Test
    public void testAddNewPlayer() {
        // Initialize storage with one player
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        Storage storage = new Storage(players);

        // Add a new player
        Player newPlayer = new Player("CONNECT4", "Player2", 1050, 8, 4, 1);
        storage.updatePlayer(newPlayer);

        // Verify that the new player is added
        assertEquals(2, storage.getPlayers().size());
        Player retrievedPlayer = storage.getPlayers().get(1);
        assertEquals("Player2", retrievedPlayer.getPlayerID());
        assertEquals(1050, retrievedPlayer.getElo());
        assertEquals(8, retrievedPlayer.getWins());
    }
}