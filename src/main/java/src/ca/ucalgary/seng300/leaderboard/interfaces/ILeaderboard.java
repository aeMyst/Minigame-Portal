package src.ca.ucalgary.seng300.leaderboard.interfaces;

/**
 * Interface for leaderboard functionality in a leaderboard system.
 */
public interface ILeaderboard {

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
