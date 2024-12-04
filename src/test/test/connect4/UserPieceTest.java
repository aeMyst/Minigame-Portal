package connect4;

import src.ca.ucalgary.seng300.gamelogic.Connect4.UserPiece;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test suite for UserPiece class.
 */
public class UserPieceTest {

    private Player playerRed;
    private Player playerBlue;
    private UserPiece redPiece;
    private UserPiece bluePiece;

    @Before
    public void setUp() {
        // Create Player objects with necessary attributes
        playerRed = new Player("Connect4", "RedPlayer", 1200, 10, 5, 2);  // Red player with elo 1200, 10 wins, 5 losses, 2 ties
        playerBlue = new Player("Connect4", "BluePlayer", 1150, 8, 6, 3); // Blue player with elo 1150, 8 wins, 6 losses, 3 ties

        // Create UserPiece objects for testing
        redPiece = new UserPiece(playerRed, 1);  // Red piece (1) for Red player
        bluePiece = new UserPiece(playerBlue, 2); // Blue piece (2) for Blue player
    }

    /**
     * Test the constructor and initial state of the UserPiece.
     */
    @Test
    public void testConstructor() {
        // Verify that the piece and player are set correctly upon creation
        assertEquals("Player should be RedPlayer", playerRed, redPiece.getPlayer());
        assertEquals("Piece for Red player should be 1", 1, redPiece.getPiece());

        assertEquals("Player should be BluePlayer", playerBlue, bluePiece.getPlayer());
        assertEquals("Piece for Blue player should be 2", 2, bluePiece.getPiece());
    }

    /**
     * Test the setPlayer method.
     * Verifies that the piece's owner can be updated.
     */
    @Test
    public void testSetPlayer() {
        // Change the owner of the red piece
        Player newPlayer = new Player("Connect4", "NewPlayer", 1300, 12, 3, 1);  // New player with higher elo
        redPiece.setPlayer(newPlayer);

        // Verify that the owner of the red piece has been updated
        assertEquals("Player should be NewPlayer", newPlayer, redPiece.getPlayer());
    }

    /**
     * Test the getPlayer method.
     * Verifies that we can correctly retrieve the player who owns the piece.
     */
    @Test
    public void testGetPlayer() {
        assertEquals("Player for red piece should be RedPlayer", playerRed, redPiece.getPlayer());
        assertEquals("Player for blue piece should be BluePlayer", playerBlue, bluePiece.getPlayer());
    }

    /**
     * Test the getPiece method.
     * Verifies that we can correctly retrieve the piece associated with the player.
     */
    @Test
    public void testGetPiece() {
        assertEquals("Piece for red player should be 1", 1, redPiece.getPiece());
        assertEquals("Piece for blue player should be 2", 2, bluePiece.getPiece());
    }

    /**
     * Test the toString method.
     * Verifies that the string representation of the UserPiece is correct.
     */
    @Test
    public void testToString() {
        // Test toString method for both pieces
        String expectedRedPieceStr = "GameType: Connect4\tPlayerID: RedPlayer\tElo: 1200\tWins: 10\tLosses: 5\tTies: 2";
        String expectedBluePieceStr = "GameType: Connect4\tPlayerID: BluePlayer\tElo: 1150\tWins: 8\tLosses: 6\tTies: 3";

        assertEquals("toString should return correct representation for red piece", expectedRedPieceStr, redPiece.getPlayer().toString());
        assertEquals("toString should return correct representation for blue piece", expectedBluePieceStr, bluePiece.getPlayer().toString());
    }

    /**
     * Test the toCsv and fromCsv methods.
     * Verifies that the Player object can be serialized to and deserialized from CSV format.
     */
    @Test
    public void testToCsvFromCsv() {
        String csvRed = redPiece.getPlayer().toCsv();
        String csvBlue = bluePiece.getPlayer().toCsv();

        // Convert CSV back to Player objects
        Player playerFromCsvRed = Player.fromCsv(csvRed);
        Player playerFromCsvBlue = Player.fromCsv(csvBlue);

        // Verify that the Player object created from CSV is correct
        assertEquals("Player from CSV should be equal to the original RedPlayer", playerRed.getPlayerID(), playerFromCsvRed.getPlayerID());
        assertEquals("Player from CSV should be equal to the original BluePlayer", playerBlue.getPlayerID(), playerFromCsvBlue.getPlayerID());
    }
}
