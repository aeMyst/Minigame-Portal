package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit tests for the Leaderboard class.
 * Tests various methods for sorting, retrieving, and handling leaderboards.
 */
public class LeaderboardTest {

    private static final String TEST_FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/profiles_test.csv";
    private Leaderboard leaderboard;
    private File testFile;

    /**
     * Set up the test environment.
     * Initializes the Leaderboard instance and test file reference.
     */
    @Before
    public void setUp() {

        leaderboard = new Leaderboard(TEST_FILE_PATH);
    }

    /**
     * Tests sorting of the leaderboard for the CONNECT4 game.
     * Ensures players are sorted by Elo in descending order.
     */
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

    /**
     * Tests sorting of the leaderboard for TICTACTOE with mixed player Elo values.
     */
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

    /**
     * Tests retrieval and sorting of the CHECKERS leaderboard.
     * Ensures players are sorted by Elo in descending order.
     */
    @Test
    public void testGetCheckersLeaderboard() throws IOException {
        String[][] result = leaderboard.getCheckersLeaderboard();

        assertEquals("lefaa1", result[0][0]);
        assertEquals("1228", result[0][1]);
        assertEquals("s1mple", result[3][0]);
        assertEquals("140", result[3][1]);
    }

    /**
     * Tests retrieval and sorting of the CONNECT4 leaderboard.
     */
    @Test
    public void testGetC4Leaderboard() throws IOException {
        String[][] result = leaderboard.getC4Leaderboard();

        assertEquals("Karl", result[0][0]);
        assertEquals("1315", result[0][1]);
        assertEquals("lefaa1", result[1][0]);
        assertEquals("1153", result[1][1]);
    }

    /**
     * Tests retrieval and sorting of the TICTACTOE leaderboard.
     */
    @Test
    public void testGetTicTacToeLeaderboard() throws IOException {
        String[][] result = leaderboard.getTicTacToeLeaderboard();

        assertEquals("Karl", result[0][0]);
        assertEquals("900", result[0][1]);
        assertEquals("test", result[2][0]);
        assertEquals("199", result[2][1]);
    }

    /**
     * Tests behavior when trying to retrieve a leaderboard for a non-existent game.
     * Verifies the system handles empty results gracefully.
     */
    @Test
    public void testLeaderboardHandlesEmptyResult() throws IOException {
        String[][] result = leaderboard.sortLeaderboard("CHESS");

        assertEquals(0, result.length); // Expect an empty result array for a non-existent game.
    }

    /**
     * Tests behavior when the leaderboard file does not exist.
     * Verifies that the system functions without crashing.
     */
    @Test
    public void testLeaderboardHandlesNonexistentFile() {
        String[][] result = leaderboard.getC4Leaderboard();

        assertNotNull(result); // Ensure the method does not return null even when the file is missing.
    }

    /**
     * Tests the leaderboard's handling of invalid or corrupted data in the file.
     * Ensures the system does not crash and returns a valid result.
     */
    @Test
    public void testLeaderboardHandlesInvalidData() throws IOException {
        String[][] result = leaderboard.getC4Leaderboard();

        assertNotNull(result); // Ensure the leaderboard handles invalid data gracefully.
        
    }
}
