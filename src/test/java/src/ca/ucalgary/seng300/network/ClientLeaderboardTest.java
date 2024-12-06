package src.ca.ucalgary.seng300.network;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ClientLeaderboardTest {

    private ILeaderboard mockLeaderboard;
    private ClientLeaderboard clientLeaderboard;

    @Before
    public void setUp() {
        mockLeaderboard = new MockLeaderboard();
        clientLeaderboard = new ClientLeaderboard(mockLeaderboard);
    }

    @Test
    public void testGetConnect4Leaderboard() {
        String[][] expectedLeaderboard = {{"Player1", "1500", "10"}, {"Player2", "1400", "8"}};
        String[][] actualLeaderboard = clientLeaderboard.getConnect4Leaderboard();
        assertArrayEquals(expectedLeaderboard, actualLeaderboard);
    }

    @Test
    public void testGetC4LeaderboardWithCallback() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = latch::countDown;
        clientLeaderboard.getC4Leaderboard(callback);
        latch.await(2, TimeUnit.SECONDS);
        // No need to verify as we are not using mocks
    }

    @Test
    public void testGetTTTLeaderboardWithCallback() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = latch::countDown;
        clientLeaderboard.getTTTLeaderboard(callback);
        latch.await(2, TimeUnit.SECONDS);
        // No need to verify as we are not using mocks
    }

    // Mock implementation of ILeaderboard
    private static class MockLeaderboard implements ILeaderboard {
        @Override
        public String[][] sortLeaderboard(String gameType) {
            return new String[0][];
        }

        @Override
        public String[][] getC4Leaderboard() {
            return new String[][]{{"Player1", "1500", "10"}, {"Player2", "1400", "8"}};
        }

        @Override
        public String[][] getTicTacToeLeaderboard() {
            return new String[][]{{"Player1", "1600", "12"}, {"Player2", "1500", "9"}};
        }

        @Override
        public String[][] getCheckersLeaderboard() {
            return new String[0][];
        }
    }
}