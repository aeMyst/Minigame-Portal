package src.ca.ucalgary.seng300.leaderboard.interfaces;

import java.util.List;

public interface ILeaderboard {
    //we will be taking (String "whatever file path")
//    void loadPlayersFromCSV();
    void addPlayer(EloData playerID);
    List<List<String>> sortLeaderboard(String gameType);

}
