package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.logic.EloData;

import java.util.List;

public interface ILeaderboard {
    void loadPlayersFromCSV();
    void addPlayer(EloData playerID);
    List<EloData> sortedLeaderboard();
}
