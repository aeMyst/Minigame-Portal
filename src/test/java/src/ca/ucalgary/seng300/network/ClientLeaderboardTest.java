package src.ca.ucalgary.seng300.network;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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
        mockLeaderboard = Mockito.mock(ILeaderboard.class);
        clientLeaderboard = Mockito.spy(new ClientLeaderboard(mockLeaderboard));
    }

    @Test
    public void testGetConnect4Leaderboard() {
        String[][] expectedLeaderboard = {{"Player1", "1500", "10"}, {"Player2", "1400", "8"}};
        when(mockLeaderboard.getC4Leaderboard()).thenReturn(expectedLeaderboard);
        String[][] actualLeaderboard = clientLeaderboard.getConnect4Leaderboard();
        assertArrayEquals(expectedLeaderboard, actualLeaderboard);
    }

    @Test
    public void testGetC4LeaderboardWithCallback() throws InterruptedException {
        String[][] expectedLeaderboard = {{"Player1", "1500", "10"}, {"Player2", "1400", "8"}};
        when(mockLeaderboard.getC4Leaderboard()).thenReturn(expectedLeaderboard);
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = latch::countDown;
        clientLeaderboard.getC4Leaderboard(callback);
        latch.await(2, TimeUnit.SECONDS);
        verify(mockLeaderboard, times(1)).getC4Leaderboard();
    }

    @Test
    public void testGetTTTLeaderboardWithCallback() throws InterruptedException {
        String[][] expectedLeaderboard = {{"Player1", "1600", "12"}, {"Player2", "1500", "9"}};
        when(mockLeaderboard.getTicTacToeLeaderboard()).thenReturn(expectedLeaderboard);
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = latch::countDown;
        clientLeaderboard.getTTTLeaderboard(callback);
        latch.await(2, TimeUnit.SECONDS);
        verify(mockLeaderboard, times(1)).getTicTacToeLeaderboard();
    }

    @Test
    public void testGetC4LeaderboardInterruptedException() throws InterruptedException {
        String[][] expectedLeaderboard = {{"Player1", "1500", "10"}, {"Player2", "1400", "8"}};
        when(mockLeaderboard.getC4Leaderboard()).thenReturn(expectedLeaderboard);
        doThrow(new InterruptedException()).when(clientLeaderboard).sleep(anyInt());
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = latch::countDown;
        clientLeaderboard.getC4Leaderboard(callback);
        latch.await(2, TimeUnit.SECONDS);
        verify(mockLeaderboard, times(1)).getC4Leaderboard();
    }

    @Test
    public void testGetC4LeaderboardRuntimeException() throws InterruptedException {
        String[][] expectedLeaderboard = {{"Player1", "1500", "10"}, {"Player2", "1400", "8"}};
        when(mockLeaderboard.getC4Leaderboard()).thenReturn(expectedLeaderboard);
        doThrow(new RuntimeException("Simulated Exception")).when(clientLeaderboard).sleep(anyInt());
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = () -> {
            throw new RuntimeException("Callback Exception");
        };
        clientLeaderboard.getC4Leaderboard(callback);
        latch.await(2, TimeUnit.SECONDS);
        verify(mockLeaderboard, times(1)).getC4Leaderboard();

    }

    @Test
    public void testGetCheckersLeaderboard() {
        Runnable callback = () -> System.out.println("Callback executed");
        Client test = new Client();
        ILeaderboard leaderboard = new Leaderboard();
        String[][] Array = leaderboard.getCheckersLeaderboard();

        assertEquals(Array, test.getCheckersLeaderboard(callback));
    }

}