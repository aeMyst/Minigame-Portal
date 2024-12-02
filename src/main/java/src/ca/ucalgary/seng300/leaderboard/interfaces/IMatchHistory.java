package src.ca.ucalgary.seng300.leaderboard.interfaces;

public interface IMatchHistory {

    void updateMatchHistory(String gameType, String player, String winnerString, String loserString, int eloGained, int eloLost, String date);

    String[][] getMatchHistory(String player);
}
