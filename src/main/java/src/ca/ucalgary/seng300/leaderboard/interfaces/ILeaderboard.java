package src.ca.ucalgary.seng300.leaderboard.interfaces;

public interface ILeaderboard {

    String[][] sortLeaderboard(String gameType);

    String[][] getC4Leaderboard();

    String[][] getTicTacToeLeaderboard();

    String[][] getCheckersLeaderboard();
}
