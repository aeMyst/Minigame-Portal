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
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;
import java.util.Random;
import java.net.InetAddress;

/**
 * The Main client for connecting to the Network
 * this class is the connecting bridge from the frontend
 * to the backend. Since our system is only hosted on one
 * machine there are no network calls implemented in these network
 * stubs. The infrastructure in this code provides a scaffold for
 * network implementation for different types of networks.
 */
public class Client implements IClient {
    private volatile boolean isQueueCanceled = false;
    protected boolean getIsQueueCanceled() {
        return isQueueCanceled;
    }

    AuthInterface auth;
    ProfileInterface profile;
    ClientCheckers clientCheckers;
    ClientConnect4 clientConnect4;
    ClientTicTacToe clientTicTacToe;
    ClientLeaderboard clientLeaderboard;
    ClientAuth clientAuth;


    /**
     * The Main Production Constructor for Client
     * sets up using the main implementors of the interface
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
        clientLeaderboard = new ClientLeaderboard(new Leaderboard());
        clientAuth = new ClientAuth(auth);
    }

    /**
     * @param authInterface authentication object that satisfies the Authentication Interface
     * @param profileInterface profile object that satisfies the Profile Interface
     * @param leaderboard leaderboard object that satisfies the Leaderboard Interface
     */
    public Client(AuthInterface authInterface, ProfileInterface profileInterface, ILeaderboard leaderboard) {
        System.out.println("Server Started");
        System.out.println("Waiting for Request...");
        System.out.println("==========================");
        auth = authInterface;
        profile = profileInterface;
        clientCheckers = new ClientCheckers();
        clientConnect4 = new ClientConnect4();
        clientTicTacToe = new ClientTicTacToe();
        clientLeaderboard = new ClientLeaderboard(leaderboard);
        clientAuth = new ClientAuth(auth);
    }



    /**
     * Disconnect the client from the network
     */
    public void disconnect() {
        System.out.println("Disconnection Successful. Application will now Safely Close.");
    };

    /**
     * Send a message to the server who will filter it out and then return it
     * @param message The chat message that the user wants to send
     * @param client The client that sent it
     * @return The filtered Message
     */
    public String sendMessageToServer(String message, Client client) {
        String filteredMessage = ChatUtility.filterMessage(message, client);
        System.out.println("Sending Message to Server: " + message);
        System.out.println("==========================");
        return filteredMessage;
    }

    /**
     * @param username Username that the user provides
     * @param password password that the user provided
     * @return true if authentication logs in the user false otherwise
     */
    public boolean logInUser(String username, String password) {
        return clientAuth.logInUser(username, password);
    }

    /**
     * Logs out the user, see ClientAuth.logoutUser()
     * for implementation and specifics
     */
    public void logoutUser() {
        clientAuth.logoutUser();
    }

    /**
     * @param username New username
     * @param password Password for the new account
     * @param email email for the new account
     * @return true if the account was registered in the system false otherwise
     */
    public boolean registerUser(String username, String password, String email) {
        return clientAuth.registerUser(username, password, email);
    }



    /**
     * Gets the username of the user
     * @return The username of the current user is null if the user is not logged in
     */
    @Override
    public String getCurrentUsername() {
        User cur_user = auth.isLoggedIn();
        if (cur_user == null) {
            return null;
        }
        return cur_user.getUsername();
    }

    /**
     * Checks if the user is logged in and returns the User if so
     * @return The User information of the current logged in user is null if the user is not logged in
     */
    public User loggedIn(){
        return clientAuth.isLoggedIn();
    }

    /**
     * Gets the current user profile string
     * @return The profile string of the current user
     */
// search for and return a profile
    public String getCurrentUserProfile() {
        return profile.viewProfile();
    }

    /**
     * @param User The user for which we want the profile info for
     * @return The Profile string information
     */
    public String findProfileInfo(String User) {
        return User;
    }

    /**
     * Edit the profile of the user
     * @param user The user we are attempting to edit
     * @param username The new username
     * @param email The new email
     * @param password The new password
     */
    public void editProfile(User user, String username, String email, String password){
        profile.updateProfile(user,username,email,password);
    }

    /**
     * Search the profile of the username
     * @param username The username of the profile we are trying to search for
     * @return The profile string of the username we tried to find if not found returns an error string
     */
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


    /**
     * Queues up a game
     */
    public void queueGame() {
        //
        // ChatGPT Generated: taught me how to use Thread.sleep and to pause before running next line
        // this is to simulate a queue happening in GUI
        //
        try {
            System.out.println("Queueing...");

            // Check if the queue was canceled during the first delay
            Thread.sleep(1000);
            if (getIsQueueCanceled()) {
                return;
            }

            Thread.sleep(2000); // Continue the remaining delay
            if (getIsQueueCanceled()) {
                return;
            }

            createGameSession(); // creating fake game session being created

            // Check again before announcing connection
            Thread.sleep(2000);
            if (getIsQueueCanceled()) {
                return;
            }

            System.out.println("Success! Connecting to game session...");
            System.out.println("==========================");
        } catch (Exception e) {
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

    /**
     * @param logicManager The Connect4 Logic system
     * @param turnManager The Turn manager
     * @param status What the status of the game is, either "ONGOING" or "DONE"
     * @param callback A function that executes after the server has done processing
     *                  the move, generally will just update the GUI
     */
    public void sendC4MoveToServer(Connect4Logic logicManager, TurnManager turnManager, String status, Runnable callback) {
        clientConnect4.sendC4MoveToServer(logicManager, turnManager, status, callback);
    }

    /**
     * Gets the connect 4 leaderboard
     * @param callback A function that executes after the leaderboard has been received
     * @return The Leaderboard String
     */
    public String[][] getC4Leaderboard(Runnable callback) {
        return clientLeaderboard.getC4Leaderboard(callback);
    }
    // ###########################################Connect 4 Server Methods############################################//

    // ###########################################Checkers Server Methods#############################################//

    /**
     * Sends a new checkers move to the network
     * @param gameLogic The checkers game logic unit
     * @param fromRow what row is the source piece from [0-7]
     * @param fromCol what col is the source piece from [0-7]
     * @param toRow what row is the piece being moved to from [0-7]
     * @param toCol what col is the piece being moved to from [0-7]
     * @param player The player that is making the move
     * @param callback A function to be called after the network has resolved the move
     */
    public void sendCheckerMoveToServer(CheckersGameLogic gameLogic, int fromRow, int fromCol, int toRow, int toCol, Player player, Runnable callback) {
        clientCheckers.sendCheckerMoveToServer(gameLogic, fromRow, fromCol, toRow, toCol, player, callback);
    }


    /**
     * Gets the checkers leaderboard
     * @param callback A function to be called after the server has recieved the checkers leaderboard
     * @return The Leaderboard String
     */
    public String[][] getCheckersLeaderboard(Runnable callback) {
        return clientLeaderboard.getCheckersLeaderboard(callback);
    }


    /**
     * Gets the Tic-Tac-Toe leaderboard
     * @param callback A function to be called after the server has received the tic-tac-toe leaderboard
     * @return The Leaderboard String
     */
    public String[][] getTTTLeaderboard(Runnable callback) {
        return clientLeaderboard.getTTTLeaderboard(callback);
    }

    // ###########################################Checkers Server Methods#############################################//

    /**
     * Gets the correct file path for a corresponding games rules
     * @param gameType what kind of game are we getting rules for
     *                 0 => tic-tac-toe
     *                 1 => connect-four
     *                 2 => checkers
     * @return A file path string for where to get the rules file, returns no file path found if invalid gameType
     */
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
    /**
     * Gets the correct file path for a corresponding games tips
     * @param gameType what kind of game are we getting rules for
     *                 0 => tic-tac-toe
     *                 1 => connect-four
     *                 2 => checkers
     * @return A file path string for where to get the tips file, returns "Enjoy and Have Fun!" if invalid game found
     */
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

    /**
     * Get file paths for chat utility data
     * @param utilityType What kind of util file path we want
     *                    0 => banned word list
     *                    1 => emoji list
     * @return The file path of the resource
     */
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

    /**
     * @return The file path for profiles database
     */
    public String getStatPath() {
        System.out.println("Server Request Started for fetching Stats");
        return "src/main/java/src/ca/ucalgary/seng300/database/profiles.csv";
    }

    /**
     * @return The file path for the users database
     */
    public String getAccountsPath() {
        System.out.println("Server Request started for fetching Accounts in System");
        return "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    }

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    // Was written with help of AI(better formatting for output)

}

