package src.ca.ucalgary.seng300;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.gamelogic.GameState;
import src.ca.ucalgary.seng300.gamelogic.IGameLogic;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.PlayerManager;
import java.util.Random;

public class Client implements IClient {


    IGameLogic gameLogic;

    //AuthInterface auth;
    //ProfileInterface profile;

    //IMatchMaker matchMaker;
    //ILeaderboard leaderboard;
    //IElo elo;


    public void logInUser(String username, String password) {
    }

    public void logoutUser() {}



    public boolean registerUser(String username, String password, String email) {
        // TODO: Implement
        return false;
    }

    public String findProfileInfo(String User) {

        return User;
    }

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

    public void newGameInfo() {}


    public GameState queueGame(String gameKind) {
        return null;
    }

    @Override
    public GameState getNextMove(GameState gamestate) {
        return null;
    }


    public void pingQueue() {}


    public void cancelQueue() {}


    public GameState viewGame(int id) {
        return null;
    }
}
