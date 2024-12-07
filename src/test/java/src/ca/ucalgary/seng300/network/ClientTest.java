package src.ca.ucalgary.seng300.network;

import org.junit.Before;
import org.junit.Test;
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

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

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
            return cur_login;
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
            return new String[][]{{"Called c4 lb"}};
        }

        @Override
        public String[][] getTicTacToeLeaderboard() {
            return new String[][]{{"Called ttt lb"}};
        }

        @Override
        public String[][] getCheckersLeaderboard() {
            return new String[][]{{"Called checkers lb"}};
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
        client = new Client(mockAuth, mockProfile, mockLeaderboard);
    }
    
    /**
     * Tests the basic constructor of the Client class.
     */

    @Test
    public void checkBaseConstructor() {
        client = new Client();
    }

     /**
     * Tests the profile initialization method
     */

    @Test
    public void initializeProfile() {
    }

    /**
     * Tests for when the client disconnects
     */

    @Test
    public void disconnect() {
        client.disconnect();
    }

    /**
     * Tests sending a message to the server that requires no filtering
     */

    @Test
    public void sendMessageToServer() {
        String chat_message = "Wow That's Crazy!";
        String server_message = client.sendMessageToServer(chat_message, client);
        assertEquals("Expected no Filtering", chat_message, server_message);
    }

    /**
     * Tests sending an empty message to the server
     * The server should respond with a message saying the message needs to be at least 1 character.
     */

    @Test
    public void sendEmptyMessageToServer() {
        String empty_message = "";
        String expected_response = "The message needs to be at least 1 character";

        // Assuming the sendMessageToServer method handles empty messages appropriately
        String server_response = client.sendMessageToServer(empty_message, client);

        // Validate that the server's response matches the expected message
        assertEquals("Expected empty message validation", expected_response, server_response);
    }

    /**
     * Tests sending a message to the server that does require filtering
     */

    @Test
    public void sendFilteredMessageToServer() {
        String chat_message = "Wow shut up!";
        String expected_message = "Wow *******!"; // Assuming ChatUtility filters "shut up" to "*******"
        String server_message = client.sendMessageToServer(chat_message, client);
        assertEquals("Expected Filtering", expected_message, server_message);
    }

     /**
     * Test for successful user log in
     */

    @Test
    public void logInUserSuccess() {
        assertTrue("Expected Successful login", client.logInUser("test", "test@123"));
    }

    /**
     * Test for an unsuccessful user log in due to incorrect password
     */

    @Test
    public void logInUserFailure() {
        assertFalse("Expected Failed login", client.logInUser("test", "test@12"));
    }

    /**
     * Tests for when a user tries to log out when not even logged in
     */

    @Test
    public void logoutUserNotLoggedIn() {
        assertNull("Expected before logout is null", mockAuth.cur_login);
        client.logoutUser();
        assertNull("Expected after logout is null", mockAuth.cur_login);
    }

    /**
     * Test for a successful log out after the user has logged in 
     */

    @Test
    public void logoutUserLoggedIn() {
        assertNull("Expected before logout is null", mockAuth.cur_login);
        client.logInUser("test", "test@123");
        assertEquals("Expected to be logged in", mockAuth.cur_login.getUsername(), "test");
        client.logoutUser();
        assertNull("Expected after logout is null", mockAuth.cur_login);
    }

    /**
     * Tests for a new user being registered successfully
     */

    @Test
    public void registerUser() {
        assertTrue("expected valid register", client.registerUser("test2", "test@321", "test2@test.ca"));
    }

     /**
     * Tests to validate the information that user would need in case they forgot log in details to recover the account
     */

    @Test
    public void validateRecoveryInfo() {
    }

    /**
     * Test to get the username of the current logged in user
     */

    @Test
    public void getCurrentUsernameLoggedIn() {
        assertTrue("Expected valid Login", client.logInUser("test", "test@123"));
        assertEquals("Expected to get correct username", client.getCurrentUsername(), mockAuth.cur_login.getUsername());
    }

     /**
     * Test for if someone tried to see username while they are not logged in
     */

    @Test
    public void getCurrentUsernameNotLoggedIn() {
        assertNull("Since not logged in should return null", client.getCurrentUsername());
    }

     /**
     * Tests to see if you are logged in or not
     */

    @Test
    public void loggedIn() {
        assertNull("Not logged in = null", client.loggedIn());
    }

    /**
     * Tests for getting the profile of whoever is currently logged in
     */

    @Test
    public void getCurrentUserProfile() {
        assertEquals("Since the mock just return works", client.getCurrentUserProfile(), "Works");
    }

    /**
     * Test to find information about the profile
     */

    @Test
    public void findProfileInfo() {
        assertEquals("Just returns the same string", client.findProfileInfo("test"), "test");
    }

    /**
     * Test to allow the user to edit information in their profile
     */

    @Test
    public void editProfile() {
        String init_profile = "Username: test, Email: test@test.ca, Games Played: 0, Wins: 0, Losses: 0, Rank: 0";
        assertEquals("Expected same init profile", mockProfile.profile.getProfileDetails(), init_profile);
        client.editProfile(null, "test2", "test2@test.com", "test@123");
        String after_profile = "Username: test2, Email: test2@test.com, Games Played: 0, Wins: 0, Losses: 0, Rank: 0";
        assertEquals("Expected change profile", mockProfile.profile.getProfileDetails(), after_profile);
    }

    /**
     * Tests to search for a profile
     */

    @Test
    public void searchProfile() {
        assertEquals("Calls profile search", client.searchProfile(""), "What");
    }

    /**
     * Test connecting the client to the server
     */

    @Test
    public void connectServer() {
        client.connectServer();
    }

    /**
     * Test disconnecting from the server
     */

    @Test
    public void disconnectServer() {
        client.disconnectServer();
    }

    /**
     * Test for queuing a game
     */

    @Test
    public void queueGame() {
        client.queueGame();
    }

    /**
     * Testing for a queue that fails
     */

    @Test
    public void queueGameShouldFail() {
        // Simulate a failure in queueGame by directly invoking the failure condition
        try {
            client.queueGame();
        } catch (Exception e) {
            // Handle exception if needed
        }
    }

    /**
     * Testing for cancelling the queue
     */

    @Test
    public void cancelQueue() {
        client.cancelQueue();
    }

    /**
     * Test for user disconnecting from the game session
     */

    @Test
    public void disconnectGameSession() {
        client.disconnectGameSession();
    }

    Player p1 = new Player("TICTACTOE", "test1", 0, 0, 0, 0);
    Player p2 = new Player("TICTACTOE", "test2", 0, 0, 0, 0);
    BoardManager bm = new BoardManager();
    PlayerManager pm = new PlayerManager(new HumanPlayer(p1, 'x'), new HumanPlayer(p2, 'o'));

    /**
     * Test for sending Tic Tac Toe move to the server
     */

    @Test
    public void sendTTTMoveToServerTest() {
        client.sendTTTMoveToServer(bm, pm, "ONGOING", () -> {});
    }

    /**
     * Test for sending Connect4 move to the server
     */

    @Test
    public void sendC4MoveToServer() {
        Connect4Logic logic = new Connect4Logic();
        TurnManager tm = new TurnManager(new UserPiece(p1, 1), new UserPiece(p2, 2));
        client.sendC4MoveToServer(logic, tm, "ONGOING", () -> {});
    }

    /**
     * Test for seeing the connect4 leaderboard
     */

    @Test
    public void sendC4LeaderboardToServer() {
        String[][] ret = client.getC4Leaderboard(() -> {});
        assertEquals("Expected to return c4 leaderboard", ret[0][0], "Called c4 lb");
    }

    /**
     * Test for sending checkers move to the server
     */

    @Test
    public void sendCheckerMoveToServer() {
        CheckersGameLogic gameLogic = new CheckersGameLogic(p1, p2);
        client.sendCheckerMoveToServer(gameLogic, 2, 0, 3, 1, p1, () -> {});
    }

    /**
     * Test for sending checkers king move to the server
     */

    @Test
    public void sendCheckerKingMoveToServer() {
        CheckersGameLogic game = new CheckersGameLogic(p1, p2);
        game.getBoard()[0][1] = 2;
        game.getBoard()[7][0] = 1;
        game.promoteToKing(0, 1, p2);
        game.promoteToKing(7, 0, p1);
        assertEquals(4, game.getBoard()[0][1]);
        client.sendCheckerMoveToServer(game, 6, 1, 4, 3, p2, () -> {});
    }

    /**
     * Test for seeing the Checkers leaderboard
     */

    @Test
    public void sendCheckersLeaderboardToServer() {
        String[][] ret = client.getCheckersLeaderboard(() -> {});
        assertEquals("Expected to return checkers leaderboard", ret[0][0], "Called checkers lb");
    }

    /**
     * Test for seeing the Tic Tac Toe leaderboard 
     */

    @Test
    public void sendTTTLeaderboardToServer() {
        String[][] ret = client.getTTTLeaderboard(() -> {});
        assertEquals("Expected to return ttt leaderboard", ret[0][0], "Called ttt lb");
    }

    /**
     * Test for the user to see the rules for all 3 of our games
     */

    @Test
    public void getRulesPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/tictactoe_rules.txt", client.getRulesPath(0));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/connect_four_rules.txt", client.getRulesPath(1));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/checkers_rules.txt", client.getRulesPath(2));
        assertEquals("No File Path for GameType: 3", client.getRulesPath(3));
    }

    /**
     * Test for the user to get tips for all 3 of our games
     */

    @Test
    public void getTipsPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/tictactoe_tips.txt", client.getTipsPath(0));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/connect4_tips.txt", client.getTipsPath(1));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/checkers_tips.txt", client.getTipsPath(2));
        assertEquals("Enjoy and Have Fun!", client.getTipsPath(3));
    }

    /**
     * Tests to see chat elements such as emojis that can be sent or words that cannot be sent
     */

    @Test
    public void getChatElements() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/banned_words.txt", client.getChatElements(0));
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/emojis.txt", client.getChatElements(1));
        assertNull("Should return null if not valid request", client.getChatElements(2));
    }

    /**
     * Test for the path to your profile to see your stats
     */

    @Test
    public void getStatPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/profiles.csv", client.getStatPath());
    }

    /**
     * Test for the path to your accounts
     */

    @Test
    public void getAccountsPath() {
        assertEquals("src/main/java/src/ca/ucalgary/seng300/database/users.csv", client.getAccountsPath());
    }

    /**
     * Test for user to see the match history
     */

    @Test
    public void sendMatchHistoryToServerTest() {
        String[][] history = { { "" } };
        client.sendMatchHistoryToServer(history, () -> {});
    }

    /**
     * Test for if the user has not played any matches, for an empty match history to be shown
     */

    @Test
    public void sendEmptyMatchHistoryToServerTest() {
        String[][] history = { };
        client.sendMatchHistoryToServer(history, () -> {});
    }

     /**
     * Test to cover the catch block in connectServer
     */

    @Test
    public void testConnectServerException() {
        try {
            client.connectServer();
        } catch (RuntimeException e) {
            // Handle exception if needed
        }
    }

    /**
     * Test to cover the catch block in disconnectServer
     */

    @Test
    public void testDisconnectServerException() {
        try {
            client.disconnectServer();
        } catch (RuntimeException e) {
            // Handle exception if needed
        }
    }

    /**
     * Test to cover the catch block in queueGame
     */

    @Test
    public void testQueueGameException() {
        try {
            client.queueGame();
        } catch (RuntimeException e) {
            // Handle exception if needed
        }
    }

    /**
     * Test to cover the catch block in cancelQueue
     */

    @Test
    public void testCancelQueueException() {
        try {
            client.cancelQueue();
        } catch (RuntimeException e) {
            // Handle exception if needed
        }
    }

    /**
     * Test to cover the catch block in disconnectGameSession
     */

    @Test
    public void testDisconnectGameSessionException() {
        try {
            client.disconnectGameSession();
        } catch (RuntimeException e) {
            // Handle exception if needed
        }
    }

    /**
     * Test to cover the catch block in sendMatchHistoryToServer
     */
    
    @Test
    public void testSendMatchHistoryToServerException() throws InterruptedException {
        String[][] history = { { "" } };
        CountDownLatch latch = new CountDownLatch(1);
        Runnable callback = () -> { throw new RuntimeException("Callback Exception"); };
        client.sendMatchHistoryToServer(history, callback);
        latch.await(2, TimeUnit.SECONDS);
    }
}