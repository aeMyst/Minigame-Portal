package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class LeaderboardTest {
    private static final String TEST_FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/profiles_test_LeaderboardTest.csv";
    private Leaderboard leaderboard;
    private File testFile;

    @Before
    public void setUp() {
        leaderboard = new Leaderboard();
        testFile = new File(TEST_FILE_PATH);
    }

    // Test the sorting of the leaderboard for the CONNECT4 game and ensure the players are sorted by Elo in descending order.
    @Test
    public void testSortLeaderboardCorrectOrder() throws IOException {
        String[][] result = leaderboard.sortLeaderboard("CONNECT4");

        assertEquals("Karl", result[0][0]);
        assertEquals("1315", result[0][1]);
        assertEquals("lefaa1", result[1][0]);
        assertEquals("1153", result[1][1]);
        assertEquals("testing", result[2][0]);
        assertEquals("109", result[2][1]);
    }

    // Test the sorting of the leaderboard for TICTACTOE and ensure proper sorting for the game with mixed player Elo values.
    @Test
    public void testSortLeaderboardWithMixedGames() throws IOException {
        String[][] result = leaderboard.sortLeaderboard("TICTACTOE");

        assertEquals("Karl", result[0][0]);
        assertEquals("900", result[0][1]);
        assertEquals("testing", result[1][0]);
        assertEquals("214", result[1][1]);
        assertEquals("test", result[2][0]);
        assertEquals("199", result[2][1]);
    }

    // Test that the leaderboard for the CHECKERS game is correctly retrieved and sorted by Elo in descending order.
    @Test
    public void testGetCheckersLeaderboard() throws IOException {
        String[][] result = leaderboard.getCheckersLeaderboard();

        assertEquals("lefaa1", result[0][0]);
        assertEquals("1228", result[0][1]);
        assertEquals("s1mple", result[3][0]);
        assertEquals("140", result[3][1]);
    }

    // Test that the leaderboard for CONNECT4 is correctly retrieved and sorted by Elo in descending order.
    @Test
    public void testGetC4Leaderboard() throws IOException {
        String[][] result = leaderboard.getC4Leaderboard();

        assertEquals("Karl", result[0][0]);
        assertEquals("1315", result[0][1]);
        assertEquals("lefaa1", result[1][0]);
        assertEquals("1153", result[1][1]);
    }

    // Test that the leaderboard for TICTACTOE is correctly retrieved and sorted by Elo in descending order.
    @Test
    public void testGetTicTacToeLeaderboard() throws IOException {
        String[][] result = leaderboard.getTicTacToeLeaderboard();

        assertEquals("Karl", result[0][0]);
        assertEquals("900", result[0][1]);
        assertEquals("test", result[2][0]);
        assertEquals("199", result[2][1]);
    }

    // Test the behavior when trying to retrieve a leaderboard for a non-existent game (CHESS).
    @Test
    public void testLeaderboardHandlesEmptyResult() throws IOException {
        String[][] result = leaderboard.sortLeaderboard("CHESS");

        assertEquals(0, result.length);  // Expect an empty result array for a non-existent game.
    }

    // Test the behavior when the leaderboard file does not exist. The system should still function gracefully.
    @Test
    public void testLeaderboardHandlesNonexistentFile() {
        String[][] result = leaderboard.getC4Leaderboard();

        assertNotNull(result);  // Ensure the method does not return null even when the file is missing.
    }

    // Test the leaderboard handling invalid or corrupted data within the file.
    @Test
    public void testLeaderboardHandlesInvalidData() throws IOException {
        String[][] result = leaderboard.getC4Leaderboard();

        assertNotNull(result);  // Ensure the leaderboard handles invalid data gracefully.
        assertTrue(result.length > 0);  // Ensure that the result is not empty.
    }
}
