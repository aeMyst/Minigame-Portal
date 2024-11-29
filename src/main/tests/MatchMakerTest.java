import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.leaderboard.logic.*;
import src.ca.ucalgary.seng300.leaderboard.data.*;

public class MatchMakerTest {

    @Test
    public void testBasicMatchCreation() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);
        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);

        assertEquals(0, matchMaker.queue.size());
    }

    @Test
    public void testThresholdBoundary() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1150, 15, 2, 1);
        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);

        assertEquals(0, matchMaker.queue.size());
    }

    @Test
    public void testQueueHandling() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);
        Player player3 = new Player("CONNECT4", "Player3", 1200, 20, 1, 2);
        Player player4 = new Player("CONNECT4", "Player4", 1250, 25, 0, 3);

        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);
        matchMaker.addPlayerToQueue(player3);
        matchMaker.addPlayerToQueue(player4);

        assertEquals(0, matchMaker.queue.size());
    }

    @Test
    public void testNoMatchFound() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1300, 5, 10, 0);
        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);

        assertEquals(2, matchMaker.queue.size());
    }

    @Test
    public void testCorrectMatchRemoval() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);

        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);

        assertEquals(0, matchMaker.queue.size());
    }

    @Test
    public void testAddPlayerMultipleTimes() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player1);

        assertEquals(2, matchMaker.queue.size());
    }

    @Test
    public void testMaxPlayersInQueue() {
        MatchMaker matchMaker = new MatchMaker(null);
        int maxPlayers = 100;
        for (int i = 0; i < maxPlayers; i++) {
            matchMaker.addPlayerToQueue(new Player("CONNECT4", "Player" + i, 1000 + i, i, 100 - i, 0));
        }

        assertEquals(maxPlayers, matchMaker.queue.size());
    }

    @Test
    public void testEmptyQueue() {
        MatchMaker matchMaker = new MatchMaker(null);
        matchMaker.findMatch();
    }

    @Test
    public void testDuplicatePlayerIDs() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player1", 1100, 15, 2, 1);

        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);

        assertEquals(0, matchMaker.queue.size());
    }

    @Test
    public void testEloSorting() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1200, 20, 1, 2);
        Player player2 = new Player("CONNECT4", "Player2", 1100, 15, 2, 1);
        Player player3 = new Player("CONNECT4", "Player3", 1000, 10, 5, 0);

        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);
        matchMaker.addPlayerToQueue(player3);

        assertEquals(0, matchMaker.queue.size());
    }

    @Test
    public void testSameEloRatings() {
        MatchMaker matchMaker = new MatchMaker(null);
        Player player1 = new Player("CONNECT4", "Player1", 1000, 10, 5, 0);
        Player player2 = new Player("CONNECT4", "Player2", 1000, 15, 2, 1);
        Player player3 = new Player("CONNECT4", "Player3", 1000, 20, 1, 2);
        Player player4 = new Player("CONNECT4", "Player4", 1000, 25, 0, 3);

        matchMaker.addPlayerToQueue(player1);
        matchMaker.addPlayerToQueue(player2);
        matchMaker.addPlayerToQueue(player3);
        matchMaker.addPlayerToQueue(player4);

        assertEquals(0, matchMaker.queue.size());
    }
}
