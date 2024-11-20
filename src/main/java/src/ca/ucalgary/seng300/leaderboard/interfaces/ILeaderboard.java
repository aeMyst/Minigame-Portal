package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

import java.util.List;

public interface ILeaderboard {

    List<List<String>> sortLeaderboard(String gameType);
}
