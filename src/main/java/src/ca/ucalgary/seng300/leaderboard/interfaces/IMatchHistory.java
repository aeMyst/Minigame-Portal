package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;

/**
 * Interface for managing match history in the leaderboard system.
 */
public interface IMatchHistory {

    /**
     * Updates the match history by writing the provided storage data to a file.
     *
     * @param storage The storage instance containing match history data.
     * @param player  The ID of the player whose match history is being updated.
     */
    void updateMatchHistory(HistoryStorage storage, String player);

    /**
     * Retrieves the match history for a specific player.
     *
     * @param player The ID of the player whose match history is being retrieved.
     * @return A 2D array containing the match history data.
     */
    String[][] getMatchHistory(String player);
}
