package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.leaderboard.data.*;

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

    /**
     * Test setting the game type history.
     */
    @Test
    public void testSetGameTypeHistory() {
        HistoryPlayer player = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        player.setGameTypeHistory("CHECKERS");
        assertEquals("CHECKERS", player.getGameTypeHistory());
    }

    /**
     * Test setting the player ID history.
     */
    @Test
    public void testSetPlayerIDHistory() {
        HistoryPlayer player = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        player.setPlayerIDHistory("Player2");
        assertEquals("Player2", player.getPlayerIDHistory());
    }

    /**
     * Test setting the winner string.
     */
    @Test
    public void testSetWinnerString() {
        HistoryPlayer player = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        player.setWinnerString("Player3");
        assertEquals("Player3", player.getWinnerString());
    }

    /**
     * Test setting the loser string.
     */
    @Test
    public void testSetLoserString() {
        HistoryPlayer player = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        player.setLoserString("Player4");
        assertEquals("Player4", player.getLoserString());
    }

    /**
     * Test setting the Elo gained.
     */
    @Test
    public void testSetEloGained() {
        HistoryPlayer player = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        player.setEloGained(20);
        assertEquals(20, player.getEloGained());
    }

    /**
     * Test setting the Elo lost.
     */
    @Test
    public void testSetEloLost() {
        HistoryPlayer player = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        player.setEloLost(-20);
        assertEquals(-20, player.getEloLost());
    }

    /**
     * Test setting the date.
     */
    @Test
    public void testSetDate() {
        HistoryPlayer player = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        player.setDate("2024-12-04");
        assertEquals("2024-12-04", player.getDate());
    }

    @Test
    public void testConstructorInitializesCorrectly() {
        Player player = new Player("CHECKERS", "123", 1500, 10, 5, 2);
        assertEquals("CHECKERS", player.getGameType());
        assertEquals("123", player.getPlayerID());
        assertEquals(1500, player.getElo());
        assertEquals(10, player.getWins());
        assertEquals(5, player.getLosses());
        assertEquals(2, player.getTies());
    }

    @Test
    public void testDefaultPlayerMethodCreatesPlayerWithZeroedStats() {
        Player player = Player.defaultPlayer("CHECKERS", "456");
        assertEquals("CHECKERS", player.getGameType());
        assertEquals("456", player.getPlayerID());
        assertEquals(0, player.getElo());
        assertEquals(0, player.getWins());
        assertEquals(0, player.getLosses());
        assertEquals(0, player.getTies());
    }

    @Test
    public void testSetAndGetGameType() {
        Player player = new Player("CONNECT4", "123", 1500, 10, 5, 2);
        player.setGameType("CHECKERS");
        assertEquals("CHECKERS", player.getGameType());
    }

    @Test
    public void testSetAndGetPlayerID() {
        Player player = new Player("TICTACTOE", "123", 1500, 10, 5, 2);
        player.setPlayerID("789");
        assertEquals("789", player.getPlayerID());
    }

    @Test
    public void testSetAndGetElo() {
        Player player = new Player("TICTACTOE", "123", 1500, 10, 5, 2);
        player.setElo(1600);
        assertEquals(1600, player.getElo());
    }

    @Test
    public void testSetAndGetWins() {
        Player player = new Player("CONNECT4", "123", 1500, 10, 5, 2);
        player.setWins(20);
        assertEquals(20, player.getWins());
    }

    @Test
    public void testSetAndGetLosses() {
        Player player = new Player("CHECKERS", "123", 1500, 10, 5, 2);
        player.setLosses(15);
        assertEquals(15, player.getLosses());
    }

    @Test
    public void testSetAndGetTies() {
        Player player = new Player("TICTACTOE", "123", 1500, 10, 5, 2);
        player.setTies(7);
        assertEquals(7, player.getTies());
    }

    @Test
    public void testToCsvConvertsCorrectly() {
        Player player = new Player("CHECKERS", "123", 1500, 10, 5, 2);
        String csv = player.toCsv();
        assertEquals("CHECKERS,123,1500,10,5,2", csv);
    }

    @Test
    public void testFromCsvParsesCorrectly() {
        String csv = "TICTACTOE,456,1200,15,10,5";
        Player player = Player.fromCsv(csv);
        assertEquals("TICTACTOE", player.getGameType());
        assertEquals("456", player.getPlayerID());
        assertEquals(1200, player.getElo());
        assertEquals(15, player.getWins());
        assertEquals(10, player.getLosses());
        assertEquals(5, player.getTies());
    }
}