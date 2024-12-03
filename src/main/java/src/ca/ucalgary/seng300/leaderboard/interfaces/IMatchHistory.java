package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;

public interface IMatchHistory {

    void updateMatchHistory(HistoryStorage storage, String player);

    String[][] getMatchHistory(String player);
}
