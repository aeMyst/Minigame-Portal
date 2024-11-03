package src.ca.ucalgary.seng300;

import src.ca.ucalgary.seng300.gamelogic.GameState;
import src.ca.ucalgary.seng300.gamelogic.IGameLogic;

public class Client implements IClient {

    IGameLogic gameLogic;

    AuthInterface auth;
    ProfileInterface profile;

    IMatchMaker matchMaker;
    ILeaderboard leaderboard;
    IElo elo;


    public void logInUser(String username, String password) {
    }

    public void logOutUser() {}



    public boolean registerUser(String username, String password, String email) {
        // TODO: Implement
        return false;
    }

    public void findProfileInfo(String User) {

    }


    public void newMove(GameState newState) {
    }


    public void newGameInfo() {}


    public void queueGame(String gameKind) {}


    public void pingQueue() {}


    public void cancelQueue() {}


    public void viewGame(int id) {}
}
