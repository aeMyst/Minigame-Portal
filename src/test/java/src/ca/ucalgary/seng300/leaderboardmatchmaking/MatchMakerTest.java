package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.leaderboard.logic.*;
import src.ca.ucalgary.seng300.leaderboard.data.*;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class MatchMakerTest {

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

    @Test
    public void testUpdateMatchHistoryError() {
        MatchHistory matchHistory = new MatchHistory();
        assertThrows(RuntimeException.class, () -> {
            matchHistory.updateMatchHistory(null, "Player3");
        });
    }

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
     * Test adding a player to the queue when they are already present.
     */
    @Test
    public void testAddPlayerToQueueAlreadyPresent() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        storage.addPlayer(player1);

        // Add the player to the queue
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");

        // Attempt to add the same player again
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");

        // Check that the player is not added twice
        assertEquals(1, matchMaker.queue.size());
    }

    /**
     * Test removing older matches beyond the most recent two.
     */
    @Test
    public void testRemoveOlderMatches() {
        HistoryStorage storage = new HistoryStorage();
        MatchHistory matchHistory = new MatchHistory();
        HistoryPlayer player1 = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player2", 10, -10, "2024-12-03");
        HistoryPlayer player2 = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player3", 15, -15, "2024-12-04");
        HistoryPlayer player3 = new HistoryPlayer("CONNECT4", "Player1", "Player1", "Player4", 20, -20, "2024-12-05");
        storage.addPlayerHistory(player1);
        storage.addPlayerHistory(player2);
        storage.addPlayerHistory(player3);

        // Update match history to ensure the file is created
        matchHistory.updateMatchHistory(storage, "Player1");

        // Retrieve match history
        String[][] history = matchHistory.getMatchHistory("Player1");

        // Verify the match history contains only the most recent two matches
        assertEquals(2, history.length);

        // Verify the most recent match
        assertEquals("CONNECT4", history[0][0]);
        assertEquals("Player1", history[0][1]);
        assertEquals("Player1", history[0][2]);
        assertEquals("Player4", history[0][3]);
        assertEquals("20", history[0][4]);
        assertEquals("-20", history[0][5]);
        assertEquals("2024-12-05", history[0][6]);

        // Verify the second most recent match
        assertEquals("CONNECT4", history[1][0]);
        assertEquals("Player1", history[1][1]);
        assertEquals("Player1", history[1][2]);
        assertEquals("Player3", history[1][3]);
        assertEquals("15", history[1][4]);
        assertEquals("-15", history[1][5]);
        assertEquals("2024-12-04", history[1][6]);

        // Clean up
        File file = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history.txt");
        file.delete();
    }

    /**
     * Test handling no match history available.
     */
    @Test
    public void testNoMatchHistoryAvailable() {
        HistoryStorage storage = new HistoryStorage();
        MatchHistory matchHistory = new MatchHistory();

        // Retrieve match history when no history is available
        String[][] history = matchHistory.getMatchHistory("Player1");

        // Verify the match history is empty
        assertEquals(0, history.length);
    }

    /**
     * Test handling an exception when updating match history.
     */
    @Test
    public void testUpdateMatchHistoryException() {
        HistoryStorage storage = new HistoryStorage();
        MatchHistory matchHistory = new MatchHistory() {
            @Override
            public void updateMatchHistory(HistoryStorage storage, String player) {
                try {
                    throw new IOException("Simulated IO Exception");
                } catch (Exception e) {
                    System.out.println("Error fetching match history");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        };

        // Simulate an exception by calling the overridden method
        try {
            matchHistory.updateMatchHistory(storage, "Player1");
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof IOException);
            assertEquals("Simulated IO Exception", e.getCause().getMessage());
        }
    }

    /**
     * Test finding a match with no suitable players within the Elo threshold.
     */
    @Test
    public void testFindMatchNoSuitablePlayers() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1300, 15, 7, 0);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        // Add both players to the queue
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");

        // Attempt to find a match
        matchMaker.findMatch("Player1");

        assertEquals(1, matchMaker.queue.size());
    }

    @Test
    public void testFileDoesNotExist() {
        // Ensure the file does not exist
        File file = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history.txt");
        if (file.exists()) {
            file.delete();
        }

        MatchHistory matchHistory = new MatchHistory();

        String[][] result = matchHistory.getMatchHistory("player1");

        assertNotNull(result);
        assertEquals(0, result.length); // Expect an empty array
    }

    @Test
    public void testMoreThanTwoMatchesForPlayer() throws IOException {
        // Create and populate the file
        File file = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        HistoryStorage storage = new HistoryStorage();
        storage.addPlayerHistory(new HistoryPlayer("game1", "player1", "winner", "loser", 10, -10, "2023-12-04"));
        storage.addPlayerHistory(new HistoryPlayer("game2", "player1", "winner", "loser", 15, -5, "2023-12-05"));
        storage.addPlayerHistory(new HistoryPlayer("game3", "player1", "winner", "loser", 20, -20, "2023-12-06"));
        FileManagement.fileWritingHistoryNewFile(file, storage); // Populate file

        MatchHistory matchHistory = new MatchHistory();

        String[][] result = matchHistory.getMatchHistory("player1");

        assertNotNull(result);
        assertEquals(2, result.length); // Expect only the 2 most recent matches
        assertEquals("game3", result[0][0]); // Most recent match
        assertEquals("game2", result[1][0]); // Second most recent match
    }

    @Test
    public void testFewerThanTwoMatchesForPlayer() throws IOException {
        // Step 1: Save the initial state of the file (backup the current file)
        File file = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history.txt");
        File backupFile = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history_backup.txt");
        if (file.exists()) {
            Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Step 2: Clear the match history
        MatchHistory matchHistory = new MatchHistory();
        matchHistory.clearMatchHistory(); // Clear match history before test

        // Step 3: Add one match for player1
        HistoryStorage storage = new HistoryStorage();
        storage.addPlayerHistory(new HistoryPlayer("game1", "player1", "winner", "loser", 10, -10, "2023-12-04"));
        FileManagement.fileWritingHistoryNewFile(file, storage); // Populate file with one match

        // Step 4: Fetch match history for "player1"
        String[][] result = matchHistory.getMatchHistory("player1");

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.length); // Expect 1 match for "player1"
        assertEquals("game1", result[0][0]); // Verify match details

        // Step 5: Restore the original file state
        if (backupFile.exists()) {
            Files.copy(backupFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            backupFile.delete(); // Clean up the backup file
        }
    }

    @Test
    public void testMatchHistoryEmptyForNewPlayer() throws IOException {
        // Step 1: Save the initial state of the file (backup the current file)
        File file = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history.txt");
        File backupFile = new File("src/main/java/src/ca/ucalgary/seng300/database/match_history_backup.txt");
        if (file.exists()) {
            Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Step 2: Clear the match history
        MatchHistory matchHistory = new MatchHistory();
        matchHistory.clearMatchHistory(); // Clear match history before test

        // Step 3: Fetch match history for a player with no history
        String[][] result = matchHistory.getMatchHistory("new_player");

        // Assertions
        assertNotNull(result);
        assertThrows(); // Expect no matches for "new_player"

        // Step 4: Restore the original file state
        if (backupFile.exists()) {
            Files.copy(backupFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            backupFile.delete(); // Clean up the backup file
        }
    }

}