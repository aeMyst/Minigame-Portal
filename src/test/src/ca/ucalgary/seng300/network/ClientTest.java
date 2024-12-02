package src.ca.ucalgary.seng300.network;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClientTest {

    class MockAuth implements AuthInterface {
        private ArrayList<User> users;
        private User cur_login;
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
                    }
                    else {
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
        private ArrayList<Profile> profiles;
        @Override
        public String viewProfile() {
            return "";
        }

        @Override
        public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {

        }

        @Override
        public void initializeProfile(String username) {

        }

        @Override
        public String searchProfile(String username) {
            return "";
        }
    }
    Client client;
    @Before
    public void initializeClient() {
        // setup new client
        client = new Client(new MockAuth(), new MockProfile());
    }

    @Test
    public void initializeProfile() {
    }

    @Test
    public void disconnect() {
    }

    @Test
    public void sendMessageToServer() {
    }

    @Test
    public void logInUser() {
    }

    @Test
    public void logoutUser() {
        assertEquals("This should fail", 2, 3);
    }

    @Test
    public void registerUser() {
    }

    @Test
    public void validateRecoveryInfo() {
    }

    @Test
    public void updatePassword() {
    }

    @Test
    public void retrieveUsername() {
    }

    @Test
    public void getCurrentUsername() {
    }

    @Test
    public void loggedIn() {
    }

    @Test
    public void getCurrentUserProfile() {
    }

    @Test
    public void findProfileInfo() {
    }

    @Test
    public void editProfile() {
    }

    @Test
    public void searchProfile() {
    }

    @Test
    public void connectServer() {
    }

    @Test
    public void disconnectServer() {
    }

    @Test
    public void queueGame() {
    }

    @Test
    public void getLeaderBoard() {
    }

    @Test
    public void createGameSession() {
    }

    @Test
    public void cancelQueue() {
    }

    @Test
    public void disconnectGameSession() {
    }

    @Test
    public void newMoveTTT() {
    }

    @Test
    public void sendMoveToServer() {
    }

    @Test
    public void newMoveC4() {
    }

    @Test
    public void sendC4MoveToServer() {
    }

    @Test
    public void sendC4LeaderboardToServer() {
    }

    @Test
    public void sendCheckerMoveToServer() {
    }

    @Test
    public void newMoveCheckers() {
    }

    @Test
    public void sendCheckersLeaderboardToServer() {
    }

    @Test
    public void sendTTTLeaderboardToServer() {
    }

    @Test
    public void getRulesPath() {
    }

    @Test
    public void getTipsPath() {
    }

    @Test
    public void getChatElements() {
    }

    @Test
    public void getStatPath() {
    }

    @Test
    public void getAccountsPath() {
    }
}