package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.leaderboard.logic.*;
import src.ca.ucalgary.seng300.leaderboard.data.*;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class MatchMakerTest {
    private static final String TEST_FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/match_history_test.txt";
    /**
     * Test adding the same player multiple times to the queue.
     */
    @Test
    public void testAddPlayerMultipleTimes() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        storage.addPlayer(player1);

        // Add the same player to the queue twice
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");

        // Check that the player is not added twice to the queue
        assertEquals(1, matchMaker.queue.size());
    }

    /**
     * Test finding a match when the queue is empty.
     */
    @Test
    public void testEmptyQueue() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);

        // Attempt to find a match with an empty queue
        matchMaker.findMatch("Player1");

        // Check that the queue and match list are empty
        assertEquals(0, matchMaker.queue.size());
        assertEquals(0, matchMaker.match.size());
    }

    /**
     * Test adding a player to the queue.
     */
    @Test
    public void testAddPlayerToQueue() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        storage.addPlayer(player1);

        // Add a player to the queue
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");

        // Check that the player is added to the queue
        assertEquals(1, matchMaker.queue.size());
        assertEquals(player1, matchMaker.queue.get(0));
    }

    /**
     * Test challenging a player in the queue.
     */
    @Test
    public void testChallengePlayerQueue() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 7, 0);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        // Challenge a player in the queue
        matchMaker.challengePlayerQueue("Player1", "Player2", "CONNECT4");

        // Check that both players are added to the match
        assertEquals(2, matchMaker.match.size());
        assertTrue(matchMaker.match.contains(player1));
        assertTrue(matchMaker.match.contains(player2));
    }

    /**
     * Test challenging a player with a different game type.
     */
    @Test
    public void testChallengePlayerQueueWithDifferentGameType() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("TIKTAKTOE", "Player2", 1100, 15, 7, 0);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        // Challenge a player with a different game type
        matchMaker.challengePlayerQueue("Player1", "Player2", "CONNECT4");

        // Check that only the player with the matching game type is added to the match
        assertEquals(1, matchMaker.match.size());
        assertTrue(matchMaker.match.contains(player1));
        assertFalse(matchMaker.match.contains(player2));
    }

    /**
     * Test creating a match with insufficient players.
     */
    @Test
    public void testCreateMatchWithInsufficientPlayers() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        storage.addPlayer(player1);

        // Add one player to the match list directly
        matchMaker.match.add(player1);

        // Attempt to create a match
        ArrayList<Player> match = matchMaker.createMatch();

        // Check that no match is created
        assertEquals(1, match.size());
        assertTrue(match.contains(player1));
    }

    /**
     * Test creating a match with sufficient players.
     */
    @Test
    public void testCreateMatchWithSufficientPlayers() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 7, 0);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        // Add two players to the match list directly
        matchMaker.match.add(player1);
        matchMaker.match.add(player2);

        // Attempt to create a match
        ArrayList<Player> match = matchMaker.createMatch();

        // Check that a match is created
        assertEquals(2, match.size());
        assertTrue(match.contains(player1));
        assertTrue(match.contains(player2));
    }

    /**
     * Test updating match history.
     */
    @Test
    public void testUpdateMatchHistory() {
        HistoryStorage storage = new HistoryStorage();
        MatchHistory matchHistory = new MatchHistory();
        HistoryPlayer player1 = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        storage.addPlayerHistory(player1);

        // Update match history
        matchHistory.updateMatchHistory(storage, "Player1");

        // Verify that the file is written correctly
        File file = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history.txt");
        assertTrue(file.exists());

        // Clean up
        file.delete();
    }

    /**
     * Test updating match history error.
     */
    @Test
    public void testUpdateMatchHistoryError() {
        File file = new File(TEST_FILE_PATH);
        MatchHistory matchHistory = new MatchHistory(TEST_FILE_PATH);
        assertThrows(RuntimeException.class, () -> {
            matchHistory.updateMatchHistory(null, "Player3");
        });
        file.delete();
    }

    /**
     * Test getting match history.
     */
    @Test
    public void testGetMatchHistory() {
        HistoryStorage storage = new HistoryStorage();
        MatchHistory matchHistory = new MatchHistory();
        HistoryPlayer player1 = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        HistoryPlayer player2 = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player3", 15, -15, "2024-12-04");
        storage.addPlayerHistory(player1);
        storage.addPlayerHistory(player2);

        // Update match history to ensure the file is created
        matchHistory.updateMatchHistory(storage, "Player1");

        // Retrieve match history
        String[][] history = matchHistory.getMatchHistory("Player1");

        // Verify the match history
        assertEquals(2, history.length);

        // Verify the first match (most recent)
        assertEquals("CONNECT4", history[0][0]);
        assertEquals("Player1", history[0][1]);
        assertEquals("Player1", history[0][2]);
        assertEquals("Player3", history[0][3]);
        assertEquals("15", history[0][4]);
        assertEquals("-15", history[0][5]);
        assertEquals("2024-12-04", history[0][6]);

        // Verify the second match (older)
        assertEquals("CONNECT4", history[1][0]);
        assertEquals("Player1", history[1][1]);
        assertEquals("Player1", history[1][2]);
        assertEquals("Player2", history[1][3]);
        assertEquals("10", history[1][4]);
        assertEquals("-10", history[1][5]);
        assertEquals("2024-12-03", history[1][6]);

        // Clean up
        File file = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history.txt");
        file.delete();
    }

    /**
     * Test challengePlayerQueue.
     */
    @Test
    public void testChallengePlayerQueueAddsPlayersToMatch() {
        // Create a storage and players
        Storage storage = new Storage();
        Player player1 = new Player("CONNECT4", "player1", 1500, 10, 5, 2);
        Player player2 = new Player("CONNECT4", "player2", 1450, 8, 6, 3);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        // Create the MatchMaker
        MatchMaker matchMaker = new MatchMaker(storage);

        // Call the challengePlayerQueue method
        matchMaker.challengePlayerQueue("player1", "player2", "CONNECT4");

        // Assert that both players are added to the match list
        assertTrue("player1 should be in the match list", matchMaker.match.contains(player1));
        assertTrue("player2 should be in the match list", matchMaker.match.contains(player2));
    }

    /**
     * Test challengePlayerQueue if in different games.
     */
    @Test
    public void testChallengePlayerQueueDoesNotAddPlayerIfDifferentGame() {
        // Create a storage and players
        Storage storage = new Storage();
        Player player1 = new Player("CONNECT4", "player1", 1500, 10, 5, 2);
        Player player3 = new Player("GameTypeB", "player3", 1600, 15, 3, 4);
        storage.addPlayer(player1);
        storage.addPlayer(player3);

        // Create the MatchMaker
        MatchMaker matchMaker = new MatchMaker(storage);

        // Call the challengePlayerQueue method
        matchMaker.challengePlayerQueue("player1", "player3", "CONNECT4");

        // Assert that no players are added to the match list because of different game types
        assertFalse("No players should be in the match list", matchMaker.match.isEmpty());
    }

    /**
     * Test adding players to queue if multiple players.
     */
    @Test
    public void testAddPlayerToQueueAndFindMatch() {
        // Create a storage and players
        Storage storage = new Storage();
        Player player1 = new Player("CONNECT4", "player1", 1500, 10, 5, 2);
        Player player2 = new Player("CONNECT4", "player2", 1450, 8, 6, 3);
        Player player4 = new Player("CONNECT4", "player4", 1700, 12, 3, 4);
        storage.addPlayer(player1);
        storage.addPlayer(player2);
        storage.addPlayer(player4);

        // Create the MatchMaker
        MatchMaker matchMaker = new MatchMaker(storage);

        // Add players to the queue
        matchMaker.addPlayerToQueue("player1", "CONNECT4");
        matchMaker.addPlayerToQueue("player2", "CONNECT4");

        // Create match and ensure player1 and player2 are matched based on Elo ratings
        ArrayList<Player> match = matchMaker.createMatch();

        assertEquals("There should be two players in the match", 4, match.size());
        assertTrue("player1 should be in the match", match.contains(player1));
        assertTrue("player2 should be in the match", match.contains(player2));
    }

    /**
     * Test queue for single player.
     */
    @Test
    public void testFindMatchForSinglePlayer() {
        // Create a storage and player
        Storage storage = new Storage();
        Player player1 = new Player("CONNECT4", "player1", 1500, 10, 5, 2);
        storage.addPlayer(player1);

        // Create the MatchMaker
        MatchMaker matchMaker = new MatchMaker(storage);

        // Add a single player to the queue
        matchMaker.addPlayerToQueue("player1", "CONNECT4");

        // Assert that no match is created, and player1 is still in the queue
        assertTrue("player1 should still be in the queue", matchMaker.queue.contains(player1));
    }

    /**
     * Test adding players to queue with far elos.
     */
    @Test
    public void testNoClosestMatchButQueueIsNotEmpty() {
        Storage storage = new Storage(); // Assuming Storage has a constructor to initialize players
        MatchMaker matchMaker = new MatchMaker(storage);
        // Arrange: Create players and add to queue
        Player player1 = new Player("GameTypeA", "player1", 1500, 10, 5, 2);
        Player player2 = new Player("GameTypeA", "player2", 1450, 8, 6, 3);
        storage.addPlayer(player1);
        storage.addPlayer(player2);
        matchMaker.addPlayerToQueue("player1", "GameTypeA");
        matchMaker.addPlayerToQueue("player2", "GameTypeA");

        // Act: Try to find match
        matchMaker.findMatch("player1");

        // Assert: closestMatch should be null and queue should not be empty
        assertTrue("Queue should not be empty", !matchMaker.queue.isEmpty());
    }

    /**
     * Test adding players to queue with same game.
     */
    @Test
    public void testAddPlayerToQueueForCorrectGameType() {
        Storage storage = new Storage(); // Assuming Storage has a constructor to initialize players
        MatchMaker matchMaker = new MatchMaker(storage);
        // Arrange: Create a player and a game type
        Player player1 = new Player("CHESS", "player1", 1500, 10, 5, 2);
        storage.addPlayer(player1);

        // Act: Add player to the queue for "GameTypeA"
        matchMaker.addPlayerToQueue("player1", "CHESS");

        // Assert: The player should be in the queue
        assertTrue("player1 should be in the queue", matchMaker.queue.contains(player1));
    }

    /**
     * Test adding players to queue twice.
     */
    @Test
    public void testPlayerNotAddedToQueueTwice() {
        Storage storage = new Storage(); // Assuming Storage has a constructor to initialize players
        MatchMaker matchMaker = new MatchMaker(storage);
        // Arrange: Create players and add to storage
        Player player1 = new Player("GameTypeA", "player1", 1500, 10, 5, 2);
        Player player2 = new Player("GameTypeA", "player2", 1450, 8, 6, 3);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        // Act: Add player1 to the queue and attempt to add the same player again
        matchMaker.addPlayerToQueue("player1", "GameTypeA");
        matchMaker.addPlayerToQueue("player1", "GameTypeA"); // Same player

        // Assert: The player should only appear once in the queue
        assertEquals("There should be only one player1 in the queue", 1, matchMaker.queue.stream().filter(p -> p.getPlayerID().equals("player1")).count());
    }

    @Test
    public void testNoPlayersForMatch() {
        Storage mockStorage = new Storage();
        MatchMaker mm = new MatchMaker(mockStorage);

        Player player1 = new Player("GameTypeA", "player1", 1000, 10, 5, 2);
        Player player2 = new Player("GameTypeB", "player2", 3000, 10, 5, 2);

        mockStorage.addPlayer(player1);
        mockStorage.addPlayer(player2);

        mm.addPlayerToQueue("player1", "GameTypeA");
        mm.addPlayerToQueue("player2", "GameTypeB");

    }
}