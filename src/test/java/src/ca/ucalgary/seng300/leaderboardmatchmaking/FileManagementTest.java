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
    private static final String INVALID_FILE = "invalid_file.csv";

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

    @Test
    public void testFileReadingWithFileNotFound() {
        File file = new File(INVALID_FILE); // Non-existent file
        Storage storage = FileManagement.fileReading(file);

        // Verify that an error message is printed, and storage is null
        assertNull("Storage should be null due to file not found", storage);
    }

    @Test
    public void testFileReadingHistoryWithFileNotFound() {
        File file = new File(INVALID_FILE); // Non-existent file
        HistoryStorage historyStorage = FileManagement.fileReadingHistory(file);

        // Verify that an error message is printed, and historyStorage is null
        assertNull("HistoryStorage should be null due to file not found", historyStorage);
    }

    @Test
    public void testFileWritingHistoryNewFile() throws IOException {
        File file = new File("new_output_history.csv");

        ArrayList<HistoryPlayer> historyPlayers = new ArrayList<>();
        historyPlayers.add(new HistoryPlayer("gameType2", "player3", "player3", "player4", 30, -30, "2024-01-02"));
        HistoryStorage historyStorage = new HistoryStorage(historyPlayers);

        FileManagement.fileWritingHistoryNewFile(file, historyStorage);

        // Verify the contents of the written file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertEquals("gameType2,player3,player3,player4,30,-30,2024-01-02", line);
        }

        file.delete(); // Clean up
    }

    @Test
    public void testUpdateProfilesInCsvWithFileNotFound() {
        ArrayList<Player> updatedPlayers = new ArrayList<>();
        updatedPlayers.add(new Player("gameType1", "player1", 1550, 15, 6, 3));

        FileManagement.updateProfilesInCsv(INVALID_FILE, updatedPlayers);

        // Since the file doesn't exist, the method should handle the error gracefully.
    }

    @Test
    public void testUpdateProfilesInCsvWithValidData() throws IOException {
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

    @Test
    public void testFileReading_FileNotFoundMessage() {
        File file = new File("non_existent_file.csv"); // Non-existent file

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the method
        Storage storage = FileManagement.fileReading(file);

        // Verify the output message
        assertTrue(outContent.toString().contains("File not found"));

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    public void testFileWriting_ErrorWritingFileMessage() {
        File file = new File("read_only_file.csv");

        // Make the file read-only
        try {
            file.createNewFile();
            file.setReadOnly();

            // Capture System.out
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Create sample data
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player("gameType1", "player1", 1500, 10, 5, 2));
            Storage storage = new Storage(players);

            // Call the method
            FileManagement.fileWriting(storage, file);

            // Verify the output message
            assertTrue(outContent.toString().contains("Error writing file"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.setWritable(true);
            file.delete();
        }

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    public void testFileReadingHistory_FileNotFoundMessage() {
        File file = new File("non_existent_history_file.csv");

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the method
        HistoryStorage historyStorage = FileManagement.fileReadingHistory(file);

        // Verify the output message
        assertTrue(outContent.toString().contains("File not found"));

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    public void testFileWritingHistory_ErrorWritingFileMessage() {
        File file = new File("read_only_history_file.csv");

        // Make the file read-only
        try {
            file.createNewFile();
            file.setReadOnly();

            // Capture System.out
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Create sample history data
            ArrayList<HistoryPlayer> historyPlayers = new ArrayList<>();
            historyPlayers.add(new HistoryPlayer("gameType1", "player1", "player1", "player2", 50, -50, "2024-01-01"));
            HistoryStorage historyStorage = new HistoryStorage(historyPlayers);

            // Call the method
            FileManagement.fileWritingHistory(file, historyStorage);

            // Verify the output message
            assertTrue(outContent.toString().contains("Error writing file"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.setWritable(true);
            file.delete();
        }

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    public void testUpdateProfilesInCsv_ErrorReadingFileMessage() {
        File file = new File("non_existent_profiles.csv");

        // Capture System.err
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        // Call the method
        FileManagement.updateProfilesInCsv(file.getPath(), new ArrayList<>());

        // Verify the output message
        assertTrue(errContent.toString().contains("Error reading profiles.csv"));

        // Restore System.err
        System.setErr(System.err);
    }

    @Test
    public void testUpdateProfilesInCsv_ErrorWritingFileMessage() {
        File file = new File("read_only_profiles.csv");

        try {
            file.createNewFile();
            file.setReadOnly();

            // Capture System.err
            ByteArrayOutputStream errContent = new ByteArrayOutputStream();
            System.setErr(new PrintStream(errContent));

            // Create sample data
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player("gameType1", "player1", 1500, 10, 5, 2));

            // Call the method
            FileManagement.updateProfilesInCsv(file.getPath(), players);

            // Verify the output message
            assertTrue(errContent.toString().contains("Error writing to profiles.csv"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.setWritable(true);
            file.delete();
        }

        // Restore System.err
        System.setErr(System.err);
    }

    @Test
    public void testFileReadingInvalidFormat() throws IOException {
        File invalidFile = new File("invalid_players.csv");
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("type1,ID1,1000,10,5\n"); // Invalid format
        }

        assertThrows(IllegalArgumentException.class, () -> {
            FileManagement.fileReading(invalidFile);
        });
    }

    @Test
    public void testFileReadingHistoryInvalidFormat() throws IOException {
        File invalidFile = new File("invalid_history.csv");
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("type1,ID1,winner1,loser1,20,10\n"); // Invalid format
        }

        assertThrows(IllegalArgumentException.class, () -> {
            FileManagement.fileReadingHistory(invalidFile);
        });
    }

    @Test
    public void testFileNotFound() {
        File nonExistentFile = new File("non_existent.csv");
        Storage storage = FileManagement.fileReading(nonExistentFile);
        assertNull(storage);

        HistoryStorage historyStorage = FileManagement.fileReadingHistory(nonExistentFile);
        assertNull(historyStorage);
    }

    @Test
    public void testFileNotFoundException() {
        File nonExistentFile = new File("non_existent.csv");
        Storage storage = FileManagement.fileReading(nonExistentFile);
        assertNull(storage);
    }

    @Test
    public void testUpdateProfilesInCsvMatch() throws IOException {
        // Initialize the test file with sample data
        File testFile = new File("test_players.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("type1,ID1,1000,10,5,2\n");
            writer.write("type2,ID2,1500,15,3,1\n");
        }

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("type1", "ID1", 1100, 11, 6, 3));

        FileManagement.updateProfilesInCsv(testFile.getPath(), players);

        Storage newStorage = FileManagement.fileReading(testFile);
        assertEquals(2, newStorage.getPlayers().size());
        assertEquals(1100, newStorage.getPlayers().get(0).getElo());
        assertEquals(11, newStorage.getPlayers().get(0).getWins());
        assertEquals(6, newStorage.getPlayers().get(0).getLosses());
        assertEquals(3, newStorage.getPlayers().get(0).getTies());
    }


}
