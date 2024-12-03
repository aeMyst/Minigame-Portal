package src.ca.ucalgary.seng300.leaderboard.interfaces;

/**
 * Interface for leaderboard functionality in a leaderboard system.
 */
public interface ILeaderboard {

    /**
     * Sorts the leaderboard for a specific game type.
     *
     * @param gameType The type of game for which the leaderboard should be sorted.
     * @return A 2D array of Strings representing the sorted leaderboard.
     */
    String[][] sortLeaderboard(String gameType);

    /**
     * Retrieves the leaderboard for Connect Four.
     *
     * @return A 2D array of Strings representing the Connect Four leaderboard.
     */
    String[][] getC4Leaderboard();

    /**
     * Retrieves the leaderboard for Tic Tac Toe.
     *
     * @return A 2D array of Strings representing the Tic Tac Toe leaderboard.
     */
    String[][] getTicTacToeLeaderboard();

    /**
     * Retrieves the leaderboard for Checkers.
     *
     * @return A 2D array of Strings representing the Checkers leaderboard.
     */
    String[][] getCheckersLeaderboard();
}