package src.ca.ucalgary.seng300.network;

import org.junit.Before;
import org.junit.Test;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.base.MockitoException;
import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.TurnManager;
import src.ca.ucalgary.seng300.gamelogic.Connect4.UserPiece;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.PlayerManager;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClientTest {

    class MockAuth implements AuthInterface {
        private ArrayList<User> users;
        private User cur_login;

        public MockAuth() {
            // add a test user
            users = new ArrayList<>();
            users.add(new User("test", "test@123", "test@test.com"));
            cur_login = null;
        }

        @Override
        public boolean register(String email, String username, String password) {
            User newUser = new User(username, password, email);
            users.add(newUser);
            return true;
        }

        @Override
        public boolean login(String username, String password) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        cur_login = user;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean logout(User currentUser) {
            if (currentUser.equals(cur_login)) {
                cur_login = null;
            }
            return true;
        }

        @Override
        public User isLoggedIn() {
            if (cur_login == null) {
                return null;
            } else {
                return cur_login;
            }
        }
    }

    class MockProfile implements ProfileInterface {
        private Profile profile = new Profile(new User("test", "test@123", "test@test.ca"));

        @Override
        public String viewProfile() {
            return "Works";
        }

        @Override
        public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {
            profile = new Profile(new User(newUsername, newPassword, newEmail));
        }

        @Override
        public void initializeProfile(String username) {
        }

        @Override
        public String searchProfile(String username) {
            return "What";
        }
    }

    class MockLeaderboard implements ILeaderboard {
        @Override
        public String[][] sortLeaderboard(String gameType) {
            return null;
        }

        @Override
        public String[][] getC4Leaderboard() {
            String s = "Called c4 lb";
            String[][] ret = new String[][]{{s}};
            return ret;
        }

        @Override
        public String[][] getTicTacToeLeaderboard() {
            String s = "Called ttt lb";
            String[][] ret = new String[][]{{s}};
            return ret;
        }

        @Override
        public String[][] getCheckersLeaderboard() {
            String s = "Called checkers lb";
            String[][] ret = new String[][]{{s}};
            return ret;
        }
    }

    Client client;
    MockAuth mockAuth;
    MockProfile mockProfile;
    MockLeaderboard mockLeaderboard;

    @Before
    public void initializeClient() {
        mockAuth = new MockAuth();
        mockProfile = new MockProfile();
        mockLeaderboard = new MockLeaderboard();
        client = Mockito.spy(new Client(mockAuth, mockProfile, mockLeaderboard));
    }

    @Test
    public void checkBaseConstructor() {
        client = new Client();
    }

    @Test
    public void initializeProfile() {
    }

    @Test
    public void disconnect() {
        client.disconnect();
    }

    @Test
    public void sendMessageToServer() {
        String chat_message = "Wow That's Crazy!";
        String server_message = client.sendMessageToServer(chat_message, client);
        assertEquals("Expected no Filtering", chat_message, server_message);
    }

    @Test
    public void sendFilteredMessageToServer() {
        String chat_message = "Wow shut up!";
        String expected_message = "Wow *******!"; // Assuming ChatUtility filters "shut up" to "*******"
        String server_message = client.sendMessageToServer(chat_message, client);
        assertEquals("Expected Filtering", expected_message, server_message);
    }

    @Test
    public void logInUserSuccess() {
        assertTrue("Expected Successful login", client.logInUser("test", "test@123"));
    }

    @Test
    public void logInUserFailure() {
        assertFalse("Expected Failed login", client.logInUser("test", "test@12"));
    }

    @Test
    public void logoutUserNotLoggedIn() {
        assertNull("Expected before logout is null", mockAuth.cur_login);
        client.logoutUser();
        assertNull("Expected after logout is null", mockAuth.cur_login);
    }

    @Test
    public void logoutUserLoggedIn() {
        assertNull("Expected before logout is null", mockAuth.cur_login);
        client.logInUser("test", "test@123");
        assertEquals("Expected to be logged in", mockAuth.cur_login.getUsername(), "test");
        client.logoutUser();
        assertNull("Expected after logout is null", mockAuth.cur_login);
    }

    @Test
    public void registerUser() {
        assertTrue("expected valid register", client.registerUser("test2", "test@321", "test2@test.ca"));
    }

    @Test
    public void validateRecoveryInfo() {
    }

    @Test
    public void getCurrentUsernameLoggedIn() {
        assertTrue("Expected valid Login", client.logInUser("test", "test@123"));
        assertEquals("Expected to get correct username", client.getCurrentUsername(), mockAuth.cur_login.getUsername());
    }

    @Test
    public void getCurrentUsernameNotLoggedIn() {
        assertNull("Since not logged in should return null", client.getCurrentUsername());
    }

    @Test
    public void loggedIn() {
        assertNull("Not logged in = null", client.loggedIn());
    }

    @Test
    public void getCurrentUserProfile() {
        assertEquals("Since the mock just return works", client.getCurrentUserProfile(), "Works");
    }

    @Test
    public void findProfileInfo() {
        assertEquals("Just returns the same string", client.findProfileInfo("test"), "test");
    }

    @Test
    public void editProfile() {
        String init_profile = "Username: test, Email: test@test.ca, Games Played: 0, Wins: 0, Losses: 0, Rank: 0";
        assertEquals("Expected same init profile", mockProfile.profile.getProfileDetails(), init_profile);
        client.editProfile(null, "test2", "test2@test.com", "test@123");
        String after_profile = "Username: test2, Email: test2@test.com, Games Played: 0, Wins: 0, Losses: 0, Rank: 0";
        assertEquals("Expected change profile", mockProfile.profile.getProfileDetails(), after_profile);
    }

    @Test
    public void searchProfile() {
        assertEquals("Calls profile search", client.searchProfile(""), "What");
    }

    @Test
    public void connectServer() {
        // If using a real networking client then check that the
        // correct connection network messages are being sent
        client.connectServer();
    }

    @Test
    public void disconnectServer() {
        // If using a real networking client then check that the
        // correct connection network messages are being sent
        client.disconnectServer();
    }

    @Test
    public void queueGame() {
        // the logic for trying to get the queue canceled is
        // difficult and out of scope for this project
        // esp since threads are involved
        // TODO: actually test cancelling queue
        client.queueGame();
    }

    @Test
    public void queueGameShouldFail() {
        doThrow(new MockitoException("")).when(client).getIsQueueCanceled();
        try {
            client.queueGame();
        } catch (Exception e) {

        }
        client.queueGame();
    }



    @Test
    public void cancelQueue() {
        client.cancelQueue();
    }

    @Test
    public void disconnectGameSession() {
        client.disconnectGameSession();
    }

    Player p1 = new Player("TICTACTOE", "test1", 0, 0, 0, 0);
    Player p2 = new Player("TICTACTOE", "test2", 0, 0, 0, 0);
    BoardManager bm = new BoardManager();
    PlayerManager pm = new PlayerManager(new HumanPlayer(p1, 'x'), new HumanPlayer(p2, 'o'));

    @Test
    public void sendTTTMoveToServerTest() {
        client.sendTTTMoveToServer(bm, pm, "ONGOING", () -> {});
    }

    @Test
    public void sendC4MoveToServer() {
        Connect4Logic logic = new Connect4Logic();
        TurnManager tm = new TurnManager(new UserPiece(p1, 1), new UserPiece(p2, 2));
        client.sendC4MoveToServer(logic, tm, "ONGOING", () -> {});
    }

    @Test
    public void sendC4LeaderboardToServer() {
        String[][] ret = client.getC4Leaderboard(() -> {});
        assertEquals("Expected to return c4 leaderboard", ret[0][0], "Called c4 lb");
    }

    @Test
    public void sendCheckerMoveToServer() {
        CheckersGameLogic gameLogic = new CheckersGameLogic(p1, p2);
        client.sendCheckerMoveToServer(gameLogic, 2, 0, 3, 1, p1, () -> {});
    }

    @Test
    public void sendCheckerKingMoveToServer() {
        CheckersGameLogic game = new CheckersGameLogic(p1, p2);
        // force king promotion on both sides
        game.getBoard()[0][1] = 2;
        game.getBoard()[7][0] = 1;
        game.promoteToKing(0,1, p2);
        game.promoteToKing(7, 0, p1);
        assertEquals(4, game.getBoard()[0][1]);
        client.sendCheckerMoveToServer(game, 6, 1, 4, 3, p2, () -> {});
    }

    @Test
    public void sendCheckersLeaderboardToServer() {
        String[][] ret = client.getCheckersLeaderboard(() -> {});
        assertEquals("Expected to return checkers leaderboard", ret[0][0], "Called checkers lb");
    }

    @Test
    public void sendTTTLeaderboardToServer() {
        String[][] ret = client.getTTTLeaderboard(() -> {});
        assertEquals("Expected to return ttt leaderboard", ret[0][0], "Called ttt lb");
    }

    @Test
    public void getRulesPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/tictactoe_rules.txt", client.getRulesPath(0));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/connect_four_rules.txt", client.getRulesPath(1));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/checkers_rules.txt", client.getRulesPath(2));
        assertEquals("No File Path for GameType: 3", client.getRulesPath(3));
    }

    @Test
    public void getTipsPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/tictactoe_tips.txt", client.getTipsPath(0));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/connect4_tips.txt", client.getTipsPath(1));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/checkers_tips.txt", client.getTipsPath(2));
        assertEquals("Enjoy and Have Fun!", client.getTipsPath(3));
    }

    @Test
    public void getChatElements() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/banned_words.txt", client.getChatElements(0));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/emojis.txt", client.getChatElements(1));
        assertNull("Should return null if not valid request", client.getChatElements(2));
    }

    @Test
    public void getStatPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/profiles.csv", client.getStatPath());
    }

    @Test
    public void getAccountsPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/users.csv", client.getAccountsPath());
    }

    @Test
    public void sendMatchHistoryToServerTest() {
        String[][] history = { { "" } };
        client.sendMatchHistoryToServer(history, () -> {});
    }
    @Test
    public void sendEmptyMatchHistoryToServerTest() {
        String[][] history = {  };
        client.sendMatchHistoryToServer(history, () -> {});
    }


    // Test to cover the catch block in connectServer
    @Test
    public void testConnectServerException() throws InterruptedException {
        doThrow(new RuntimeException("Simulated Exception")).when(client).DUMMY_ERROR_FOR_COV();
        client.connectServer();

    }

    // Test to cover the catch block in disconnectServer
    @Test
    public void testDisconnectServerException() throws InterruptedException {
        doThrow(new RuntimeException("Simulated Exception")).when(client).DUMMY_ERROR_FOR_COV();
        client.disconnectServer();
        // Verify that the exception was caught and handled
    }

    // Test to cover the catch block in queueGame
    @Test
    public void testQueueGameException() throws InterruptedException {
        doThrow(new RuntimeException("Simulated Exception")).when(client).DUMMY_ERROR_FOR_COV();
        client.queueGame();
        // Verify that the exception was caught and handled
    }

    // Test to cover the catch block in cancelQueue
    @Test
    public void testCancelQueueException() throws InterruptedException {
        doThrow(new RuntimeException("Simulated Exception")).when(client).DUMMY_ERROR_FOR_COV();
        client.cancelQueue();
        // Verify that the exception was caught and handled
    }

    // Test to cover the catch block in disconnectGameSession
    @Test
    public void testDisconnectGameSessionException() throws InterruptedException {
        doThrow(new RuntimeException("Simulated Exception")).when(client).DUMMY_ERROR_FOR_COV();
        client.disconnectGameSession();
        // Verify that the exception was caught and handled
    }

    // Test to cover the catch block in sendMatchHistoryToServer
    @Test
    public void testSendMatchHistoryToServerException() throws InterruptedException {
        String[][] history = { { "" } };
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = () -> { throw new RuntimeException("Callback Exception"); };
        client.sendMatchHistoryToServer(history, callback);
        latch.await(2, TimeUnit.SECONDS);
        // Verify that the exception was caught and handled
    }
}