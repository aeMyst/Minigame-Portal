package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;
import src.ca.ucalgary.seng300.gameApp.Utility.ChatUtility;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.TurnManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.PlayerManager;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

import java.util.Random;
import java.net.InetAddress;

public class Client implements IClient {
    private volatile boolean isQueueCanceled = false;

    AuthInterface auth;
    ProfileInterface profile;
    ClientCheckers clientCheckers;
    ClientConnect4 clientConnect4;
    ClientTicTacToe clientTicTacToe;
    ClientLeaderboard clientLeaderboard;
    ClientAuth clientAuth;

    /**
     * starts server
     */
    public Client() {
        System.out.println("Server Started");
        System.out.println("Waiting for Request...");
        System.out.println("==========================");
        auth = new AuthService();
        profile = new ProfileService((AuthService) auth);
        clientCheckers = new ClientCheckers();
        clientConnect4 = new ClientConnect4();
        clientTicTacToe = new ClientTicTacToe();
        clientLeaderboard = new ClientLeaderboard();
        clientAuth = new ClientAuth(auth);
    }

    public void initializeProfile(String username) {
        profile.initializeProfile(username);
    }

    public void disconnect() {
        System.out.println("Disconnection Successful. Application will now Safely Close.");
    };

    public String sendMessageToServer(String message, Client client) {
        String filteredMessage = ChatUtility.filterMessage(message, client);
        System.out.println("Sending Message to Server: " + message);
        System.out.println("==========================");
        return filteredMessage;
    }

    public boolean logInUser(String username, String password) {
        return clientAuth.logInUser(username, password);
    }

    public void logoutUser() {
        clientAuth.logoutUser();
    }

    public boolean registerUser(String username, String password, String email) {
        return clientAuth.registerUser(username, password, email);
    }

    public boolean validateRecoveryInfo(String username, String recoveryInfo) {
        System.out.println("validateRecoveryInfo");
        return true;
    }

    @Override
    public String getCurrentUsername() {
        User cur_user = auth.isLoggedIn();
        if (cur_user == null) {
            return null;
        }
        return cur_user.getUsername();
    }

    public User loggedIn(){
        return clientAuth.isLoggedIn();
    }
// search for and return a profile
    public String getCurrentUserProfile() {
        return profile.viewProfile();
    }
    public String findProfileInfo(String User) {
        return User;
    }
    public void editProfile(User user, String username, String email, String password){
        profile.updateProfile(user,username,email,password);
    }
    public String searchProfile(String username) {
        return profile.searchProfile(username);
    }

    // ###################################Connect-Disconnect to Server Methods########################################//

    /**
     * connect and initialize server print data for user
     */
    public void connectServer() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println("Request to Connect to Server Initiated");
            Thread.sleep(2000);
            System.out.println(host + " is now Connected to Server");
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * disconnect server print data for user
     */

    public void disconnectServer() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println("Request to Disconnect from Server Initiated");
            Thread.sleep(2000);
            System.out.println(host + " is now disconnected from Server");
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ###################################Connect-Disconnect to Server Methods########################################//

    // ############################################Queue Server Methods###############################################//
    //set up next game
    public void queueGame() {
        try {
            System.out.println("Queueing...");

            // Check if the queue was canceled during the first delay
            Thread.sleep(1000);
            if (isQueueCanceled) {
                return;
            }

            Thread.sleep(2000); // Continue the remaining delay
            if (isQueueCanceled) {
                return;
            }

            createGameSession(); // creating fake game session being created

            // Check again before announcing connection
            Thread.sleep(2000);
            if (isQueueCanceled) {
                return;
            }

            System.out.println("Success! Connecting to game session...");
            System.out.println("==========================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * start new game session
     */
    public void createGameSession() {
        System.out.println("Game Session Created");
    }

    /**
     * if someone starts a game and changes their mind
     */
    public void cancelQueue() {
        try {
            isQueueCanceled = true;
            System.out.println("Canceling Queue Now...");
            Thread.sleep(1000);
            System.out.println("Queue Canceled");
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * disconnect and end game session
     */

    public void disconnectGameSession() {
        try {
            System.out.println("Game Session Disconnecting...");
            Thread.sleep(2000);
            System.out.println("Disconnected from Game Session, returning to home screen...");
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // ############################################Queue Server Methods###############################################//

    // ###########################################Tic-Tac-Toe Server Methods##########################################//

    /**
     * once move has occurred send to server to update stats/leaderboard and the gamestate
     * @param boardManager the current board
     * @param playerManager the players and the current player
     * @param status of the game, the current gamestate
     * @param callback call after server response
     */
    public void sendTTTMoveToServer(BoardManager boardManager, PlayerManager playerManager, String status, Runnable callback) {
        clientTicTacToe.sendMoveToServer(boardManager, playerManager, status, callback);
    }
    // ###########################################Connect 4 Server Methods############################################//

    public void sendC4MoveToServer(Connect4Logic logicManager, TurnManager turnManager, String status, Runnable callback) {
        clientConnect4.sendC4MoveToServer(logicManager, turnManager, status, callback);
    }

    public String[][] getC4Leaderboard(Runnable callback) {
        return clientLeaderboard.getC4Leaderboard(callback);
    }
    // ###########################################Connect 4 Server Methods############################################//

    // ###########################################Checkers Server Methods#############################################//

    public void sendCheckerMoveToServer(CheckersGameLogic gameLogic, int fromRow, int fromCol, int toRow, int toCol, Player player, Runnable callback) {
        clientCheckers.sendCheckerMoveToServer(gameLogic, fromRow, fromCol, toRow, toCol, player, callback);
    }



    public String[][] getCheckersLeaderboard(Runnable callback) {
        return clientLeaderboard.getCheckersLeaderboard(callback);
    }


    public String[][] getTTTLeaderboard(Runnable callback) {
        return clientLeaderboard.getTTTLeaderboard(callback);
    }

    // ###########################################Checkers Server Methods#############################################//

    public String getRulesPath(int gameType) {
        System.out.println("Server Request Started for fetching Rules");
        // tictactoe rules
        if (gameType == 0) {
            System.out.println("returning file path in database for rules");
            System.out.println("==========================");
            return "src/main/java/src/ca/ucalgary/seng300/database/tictactoe_rules.txt";
            // connect four rules
        } else if (gameType == 1) {
            System.out.println("returning file path in database for rules");
            System.out.println("==========================");
            return "src/main/java/src/ca/ucalgary/seng300/database/connect_four_rules.txt";
            // checkers rules
        } else if (gameType == 2) {
            System.out.println("returning file path in database for rules");
            System.out.println("==========================");
            return "src/main/java/src/ca/ucalgary/seng300/database/checkers_rules.txt";
        } else {
            System.out.println("No file path found...");
            System.out.println("==========================");
            return "No File Path for GameType: " + gameType;
        }
    }

    public String getTipsPath(int gameType) {
        System.out.println("Server Request Started for fetching Tips");
        // tictactoe tips
        if (gameType == 0) {
            System.out.println("returning file path in database for tips");
            return "src/main/java/src/ca/ucalgary/seng300/database/tictactoe_tips.txt";
            // connect four tips
        } else if (gameType == 1) {
            System.out.println("returning file path in database for tips");
            return "src/main/java/src/ca/ucalgary/seng300/database/connect4_tips.txt";
            // checkers tips
        } else if (gameType == 2) {
            System.out.println("returning file path in database for tips");
            return "src/main/java/src/ca/ucalgary/seng300/database/checkers_tips.txt";
        } else {
            // default
            System.out.println("No file path found... using default...");
            return "Enjoy and Have Fun!";
        }
    }

    public String getChatElements(int utilityType) {
        System.out.println("Server Request Started for fetching Chat Elements");
        if (utilityType == 0) {
            System.out.println("returning file path in database for filtered words");
            System.out.println("==========================");
            return "src/main/java/src/ca/ucalgary/seng300/database/banned_words.txt";
        } else if (utilityType == 1) {
            System.out.println("returning file path in database for Emojis");
            System.out.println("==========================");
            return "src/main/java/src/ca/ucalgary/seng300/database/emojis.txt";
        } else {
            System.out.println("No file Path Found");
            return null; // no path, return null
        }
    }

    public String getStatPath() {
        System.out.println("Server Request Started for fetching Stats");
        return "src/main/java/src/ca/ucalgary/seng300/database/profiles.csv";
    }

    public String getAccountsPath() {
        System.out.println("Server Request started for fetching Accounts in System");
        return "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    }

    // TODO: fix method call in matchHistoryScreen
    public void sendMatchHistoryToServer(String[][] matchHistory, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(500) + 500; // Simulate server delay between 500ms and 1000ms

        new Thread(() -> {
            try {
                Thread.sleep(time);
                System.out.println("Server Communication for Player Match History...");
                System.out.println("User Match History being updated...\n");

                System.out.println("Sorted Leaderboard for Checkers:\n");
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-10s %-15s%n",
                        "Game Type", "Player ID", "Winner ID", "Loser ID", "Elo Gained", "Elo Lost", "Date");
                for (String[] entry : matchHistory) {
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-10s %-15s%n", entry[0], entry[1], entry[2], entry[3], entry[4], entry[5], entry[6]);
                }

                // Update the GUI on the JavaFX thread
                Platform.runLater(callback);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
