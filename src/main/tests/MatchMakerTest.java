import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.leaderboard.logic.*;
import src.ca.ucalgary.seng300.leaderboard.data.*;

public class MatchMakerTest {

    @Test
    public void testBasicMatchCreation() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");

        assertEquals(0, matchMaker.queue.size());
        assertEquals(2, matchMaker.match.size());
    }

    @Test
    public void testThresholdBoundary() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1150, 15, 2, 1);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");

        assertEquals(0, matchMaker.queue.size());
        assertEquals(2, matchMaker.match.size());
    }

    @Test
    public void testQueueHandling() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);
        Player player3 = new Player("CONNECT4", "Player3", 1200, 20, 1, 2);
        Player player4 = new Player("CONNECT4", "Player4", 1250, 25, 0, 3);
        storage.addPlayer(player1);
        storage.addPlayer(player2);
        storage.addPlayer(player3);
        storage.addPlayer(player4);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");
        matchMaker.addPlayerToQueue("Player3", "CONNECT4");
        matchMaker.addPlayerToQueue("Player4", "CONNECT4");

        assertEquals(0, matchMaker.queue.size());
        assertEquals(4, matchMaker.match.size());
    }

    @Test
    public void testNoMatchFound() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1300, 5, 10, 0);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");

        assertEquals(2, matchMaker.queue.size());
        assertEquals(0, matchMaker.match.size());
    }

    @Test
    public void testCorrectMatchRemoval() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");

        assertEquals(0, matchMaker.queue.size());
        assertEquals(2, matchMaker.match.size());
    }

    @Test
    public void testAddPlayerMultipleTimes() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        storage.addPlayer(player1);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");

        assertEquals(2, matchMaker.queue.size());
    }

    @Test
    public void testMaxPlayersInQueue() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        int maxPlayers = 100;
        for (int i = 0; i < maxPlayers; i++) {
            Player player = new Player("CONNECT4", "Player" + i, 1000 + i, i, 100 - i, 0);
            storage.addPlayer(player);
            matchMaker.addPlayerToQueue("Player" + i, "CONNECT4");
        }

        assertEquals(0, matchMaker.queue.size());
        assertEquals(maxPlayers, matchMaker.match.size());
    }

    @Test
    public void testEmptyQueue() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        matchMaker.findMatch("Player1");
        assertEquals(0, matchMaker.queue.size());
        assertEquals(0, matchMaker.match.size());
    }

    @Test
    public void testDuplicatePlayerIDs() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player1", 1100, 15, 2, 1);
        storage.addPlayer(player1);
        storage.addPlayer(player2);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player1", "CONNECT4");

        assertEquals(0, matchMaker.queue.size());
        assertEquals(2, matchMaker.match.size());
    }

    @Test
    public void testEloSorting() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1200, 20, 1, 2);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);
        Player player3 = new Player("CONNECT4", "Player3", 1000, 10, 5, 0);
        storage.addPlayer(player1);
        storage.addPlayer(player2);
        storage.addPlayer(player3);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");
        matchMaker.addPlayerToQueue("Player3", "CONNECT4");

        assertEquals(0, matchMaker.queue.size());
        assertEquals(3, matchMaker.match.size());
    }

    @Test
    public void testSameEloRatings() {
        Storage storage = new Storage();
        MatchMaker matchMaker = new MatchMaker(storage);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1000, 15, 2, 1);
        Player player3 = new Player("CONNECT4", "Player3", 1000, 20, 1, 2);
        Player player4 = new Player("CONNECT4", "Player4", 1000, 25, 0, 3);
        storage.addPlayer(player1);
        storage.addPlayer(player2);
        storage.addPlayer(player3);
        storage.addPlayer(player4);

        matchMaker.addPlayerToQueue("Player1", "CONNECT4");
        matchMaker.addPlayerToQueue("Player2", "CONNECT4");
        matchMaker.addPlayerToQueue("Player3", "CONNECT4");
        matchMaker.addPlayerToQueue("Player4", "CONNECT4");

        assertEquals(0, matchMaker.queue.size());
        assertEquals(4, matchMaker.match.size());
    }
}