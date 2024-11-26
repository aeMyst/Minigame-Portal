package src.ca.ucalgary.seng300;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.gamelogic.GameState;
import src.ca.ucalgary.seng300.gamelogic.IGameLogic;
import src.ca.ucalgary.seng300.gamelogic.games.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.games.Connect4.TurnManager;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.PlayerManager;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;

import java.util.Random;
import java.net.InetAddress;

public class Client implements IClient {
    private volatile boolean isQueueCanceled = false;

    IGameLogic gameLogic;

    //AuthInterface auth;
    //ProfileInterface profile;

    //IMatchMaker matchMaker;
    //ILeaderboard leaderboard;
    //IElo elo;

    /**
     * starts server
     */
    public Client() {
        System.out.println("Server Started");
        System.out.println("Waiting for Request...");
        System.out.println("==========================");
    }

    /**
     * user login
     * @param username
     * @param password
     */
    public void logInUser(String username, String password) {
    }
// log out user
    public void logoutUser() {
    }
// register user return true if successful
    public boolean registerUser(String username, String password, String email) {
        // TODO: Implement
        return false;
    }
// search for and return a profile
    public String findProfileInfo(String User) {

        return User;
    }
// new move
    @Override
    public void newMove(GameState newState) {

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
// get next move / next person to play
    @Override
    public GameState getNextMove(GameState gamestate) {
        return null;
    }

    /**
     *
     * @param id
     * view current game using the id that it is classified under
     * @return null
     */
    @Override
    public GameState viewGame(int id) {
        return null;
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
    // ###########################################Tic-Tac-Toe Server Methods##########################################//

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
    //.
}