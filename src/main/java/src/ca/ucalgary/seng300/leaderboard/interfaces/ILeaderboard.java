package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.logic.EloData;

import java.util.List;

public interface ILeaderboard {
    //we will be taking (String "whatever file path")
    void loadPlayersFromCSV(String filename);
    void addPlayer(EloData playerID);
    List<EloData> sortedLeaderboard();
}
