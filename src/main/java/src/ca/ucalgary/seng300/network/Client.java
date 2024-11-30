package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.Profile.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;
import src.ca.ucalgary.seng300.gameApp.Utility.ChatUtility;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.Checkers.PlayerID;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.TurnManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.PlayerManager;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;

import java.util.Random;
import java.net.InetAddress;

public class Client implements IClient {
    private volatile boolean isQueueCanceled = false;

    AuthInterface auth;
    ProfileInterface profile;

    /**
     * starts server
     */
    public Client() {
        System.out.println("Server Started");
        System.out.println("Waiting for Request...");
        System.out.println("==========================");
        auth = new AuthService();
        profile = new ProfileService((AuthService) auth);
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
        return auth.login(username, password);
    }

    public void logoutUser() {
        // first check that the user is currently logged in
        User cur_user = auth.isLoggedIn();
        if (cur_user == null) {
            // if not just return as the user is logged out
            return;
        }
        // logout user
        auth.logout(cur_user);
    }

    public boolean registerUser(String username, String password, String email) {
        return auth.register(email, username, password);
    }

    public boolean validateRecoveryInfo(String username, String recoveryInfo) {
        System.out.println("validateRecoveryInfo");
        return true;
    }

    public void updatePassword(String username, String newPassword) {
        System.out.println("updatePassword");
    }

    public String retrieveUsername(String recoveryInfo) {
        return "some string";
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
        User currentUser = auth.isLoggedIn();
        return currentUser;
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
     * get leaderboard
     * @return null
     */
    @Override
    public ILeaderboard getLeaderBoard() {
        return null;
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
     *
     * @param boardManager
     * @param playerManager
     * @param status
     * initialize a move print the current board and current player whose turn it is
     */
    public void newMoveTTT(BoardManager boardManager, PlayerManager playerManager, String status) {
        System.out.println("Game Status: " + status);
        System.out.println("Current Player: " + playerManager.getCurrentPlayer().getSymbol());
        for (char[] row : boardManager.getBoard()) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    /**
     * once move has occurred send to server to update stats/leaderboard and the gamestate
     * @param boardManager the current board
     * @param playerManager the players and the current player
     * @param status of the game, the current gamestate
     * @param callback call after server response
     */

    public void sendMoveToServer(BoardManager boardManager, PlayerManager playerManager, String status, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000);
        new Thread(() -> {
            try {
                Thread.sleep(time); // Simulate 1-second delay
                System.out.println("Server Communication now...");
                System.out.println("Move acknowledged by server: " + status);
                newMoveTTT(boardManager, playerManager, status);
                Platform.runLater(callback);// Call the callback after the "server" responds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    // ###########################################Connect 4 Server Methods############################################//

    /**
     * connect 4 move setup
     * @param logicManager
     * @param turnManager
     * @param status
     */

    public void newMoveC4(Connect4Logic logicManager, TurnManager turnManager, String status) {
        System.out.println("Game Status: " + status);
        System.out.println("Current Player: " + turnManager.getCurrentPlayer().getPiece());

        int[][] board = logicManager.getBoard();
        System.out.println("   1   2   3   4   5   6   7");
        System.out.println("   --------------------------");

        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == 0 ? " " : cell) + " | ");
            }
            System.out.println();
            System.out.println("   -----------------------------");
        }
    }

    public void sendC4MoveToServer(Connect4Logic logicManager, TurnManager turnManager, String status, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000); // delay
        new Thread(() -> {
            try {
                Thread.sleep(time); // simulate server delay
                System.out.println("Server Communication now...");
                System.out.println("Move acknowledged by server: " + status);
                newMoveC4(logicManager, turnManager, status); // updates the board and game state
                Platform.runLater(callback); // calls the callback after the fake server responds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendC4LeaderboardToServer(String[][] leaderboard, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000); // simulate server delay

        new Thread(() -> {
            try {
                Thread.sleep(time);
                System.out.println("Server Communication for Leaderboard...");
                System.out.println("Leaderboard for Connect 4 being updated..." + "\n");

                int count = 1;

                System.out.println("Sorted Leaderboard for Connect 4:\n");
                System.out.printf("%-10s %-16s %-10s %-10s%n", "Rank", "Player ID", "Rating", "Wins");
                for (String[] entry : leaderboard) {
                    System.out.printf("%-10d %-16s %-10s %-10s%n", count, entry[0], entry[1], entry[2]);

                    count++;
                }

                // update the GUI
                Platform.runLater(callback);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    // ###########################################Connect 4 Server Methods############################################//

    // ###########################################Checkers Server Methods#############################################//
    public void sendCheckerMoveToServer(CheckersGameLogic gameLogic, int fromRow, int fromCol, int toRow, int toCol,PlayerID playerID, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(10); // Simulate random delay between 500ms and 1500ms

        new Thread(() -> {
            try {
                Thread.sleep(time); // Simulate server processing time
                System.out.println("Server Communication: Processing move...");
                System.out.printf("Move acknowledged by server: [%d, %d] -> [%d, %d]\n", fromRow, fromCol, toRow, toCol);
                newMoveCheckers(gameLogic,playerID);
                Platform.runLater(callback); // Execute the callback on the JavaFX thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void newMoveCheckers(CheckersGameLogic logicManager, PlayerID currentPlayer) {
        System.out.println("Current Player: " + (currentPlayer == PlayerID.PLAYER1 ? "Player 1 (White)" : "Player 2 (Black)"));

        int[][] board = logicManager.getBoard();
        System.out.println("     1  2  3  4  5  6  7  8");
        System.out.println("   +------------------------+");
        for (int row = 0; row < board.length; row++) {
            System.out.print((row + 1) + " | ");
            for (int col = 0; col < board[row].length; col++) {
                switch (board[row][col]) {
                    case 1 -> System.out.print("W  "); // White piece
                    case 2 -> System.out.print("B  "); // Black piece
                    case 3 -> System.out.print("WK "); // White king
                    case 4 -> System.out.print("BK "); // Black king
                    default -> System.out.print(".  "); // Empty square
                }
            }
            System.out.println("|");
        }
        System.out.println("   +------------------------+");
    }

    public void sendCheckersLeaderboardToServer(String [][] leaderboard, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000); // simulate server delay

        new Thread(() -> {
            try {
                Thread.sleep(time);
                System.out.println("Server Communication for Checkers...");
                System.out.println("Leaderboard for Checkers being updated...\n");

                int count = 1;

                System.out.println("Sorted Leaderboard for Checkers:\n");
                System.out.printf("%-10s %-16s %-10s %-10s%n", "Rank", "Player ID", "Rating", "Wins");
                for (String[] entry : leaderboard) {
                    System.out.printf("%-10d %-16s %-10s %-10s%n", count, entry[0], entry[1], entry[2]);

                    count++;
                }

                // update the GUI
                Platform.runLater(callback);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendTTTLeaderboardToServer(String[][] leaderboard, Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000); // simulate server delay

        new Thread(() -> {
            try {
                Thread.sleep(time);
                System.out.println("Server Communication for Leaderboard...");
                System.out.println("Leaderboard for Tic-Tac-Toe being updated..." + "\n");

                int count = 1;

                System.out.println("Sorted Leaderboard for Tic-Tac-Toe:\n");
                System.out.printf("%-10s %-16s %-10s %-10s%n", "Rank", "Player ID", "Rating", "Wins");
                for (String[] entry : leaderboard) {
                    System.out.printf("%-10d %-16s %-10s %-10s%n", count, entry[0], entry[1], entry[2]);

                    count++;
                }

                // update the GUI
                Platform.runLater(callback);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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

}
