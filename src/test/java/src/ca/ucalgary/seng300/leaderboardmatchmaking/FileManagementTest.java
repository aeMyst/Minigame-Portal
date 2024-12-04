package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FileManagementTest {

    private static final String TEST_FILE = "test_players.csv";
    private static final String TEST_HISTORY_FILE = "test_history.csv";

    @Before
    public void setUp() throws IOException {
        // Create a temporary test file with sample player data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE))) {
            writer.write("gameType1,player1,1500,10,5,2\n");
            writer.write("gameType1,player2,1600,20,3,1\n");
        }

        // Create a temporary test history file with sample data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_HISTORY_FILE))) {
            writer.write("gameType1,player1,player1,player2,50,-50,2024-01-01\n");
        }
    }

    @After
    public void tearDown() {
        // Delete test files after each test
        new File(TEST_FILE).delete();
        new File(TEST_HISTORY_FILE).delete();
    }

    @Test
    public void testFileReading() {
        File file = new File(TEST_FILE);
        Storage storage = FileManagement.fileReading(file);

        // Verify that the players were correctly read
        assertNotNull("Storage object should not be null", storage);
        List<Player> players = storage.getPlayers();
        assertEquals("Should read 2 players", 2, players.size());

        // Check the details of the first player
        Player player1 = players.get(0);
        assertEquals("gameType1", player1.getGameType());
        assertEquals("player1", player1.getPlayerID());
        assertEquals(1500, player1.getElo());
        assertEquals(10, player1.getWins());
        assertEquals(5, player1.getLosses());
        assertEquals(2, player1.getTies());
    }

    @Test
    public void testFileWriting() throws IOException {
        File file = new File("output_players.csv");

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("gameType1", "player3", 1400, 5, 3, 1));
        Storage storage = new Storage(players);

        FileManagement.fileWriting(storage, file);

        // Verify the contents of the written file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertEquals("gameType1,player3,1400,5,3,1", line);
        }

        file.delete(); // Clean up the output file
    }

    @Test
    public void testFileReadingHistory() {
        File file = new File(TEST_HISTORY_FILE);
        HistoryStorage historyStorage = FileManagement.fileReadingHistory(file);

        // Verify the history was correctly read
        assertNotNull("HistoryStorage object should not be null", historyStorage);
        List<HistoryPlayer> players = historyStorage.getPlayersHistory();
        assertEquals("Should read 1 history entry", 1, players.size());

        // Check the details of the history player
        HistoryPlayer historyPlayer = players.get(0);
        assertEquals("gameType1", historyPlayer.getGameTypeHistory());
        assertEquals("player1", historyPlayer.getPlayerIDHistory());
        assertEquals("player1", historyPlayer.getWinnerString());
        assertEquals("player2", historyPlayer.getLoserString());
        assertEquals(50, historyPlayer.getEloGained());
        assertEquals(-50, historyPlayer.getEloLost());
        assertEquals("2024-01-01", historyPlayer.getDate());
    }

    @Test
    public void testFileWritingHistory() throws IOException {
        File file = new File("output_history.csv");

        ArrayList<HistoryPlayer> historyPlayers = new ArrayList<>();
        historyPlayers.add(new HistoryPlayer("gameType2", "player3", "player3", "player4", 30, -30, "2024-01-02"));
        HistoryStorage historyStorage = new HistoryStorage(historyPlayers);

        FileManagement.fileWritingHistory(file, historyStorage);

        // Verify the contents of the written file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertEquals("gameType2,player3,player3,player4,30,-30,2024-01-02", line);
        }

        file.delete(); // Clean up the output file
    }

    @Test
    public void testUpdateProfilesInCsv() throws IOException {
        ArrayList<Player> updatedPlayers = new ArrayList<>();
        updatedPlayers.add(new Player("gameType1", "player1", 1550, 15, 6, 3));

        FileManagement.updateProfilesInCsv(TEST_FILE, updatedPlayers);

        // Verify the updated file
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line = reader.readLine();
            assertEquals("gameType1,player1,1550,15,6,3", line);
            line = reader.readLine();
            assertEquals("gameType1,player2,1600,20,3,1", line);
        }
    }
}
