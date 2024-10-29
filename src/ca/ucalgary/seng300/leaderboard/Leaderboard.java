package src.ca.ucalgary.seng300.leaderboard;

import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;

import java.util.HashMap;

public class Leaderboard {
    private HashMap<PlayerID, Integer> rankedPlayers;

    //Sets up hashmap to store ranked players on leaderboard
    public Leaderboard() {
        this.rankedPlayers = new HashMap<>();
    }

    //method for updating the leaderboard with new Elo ratings. This will be called after each match.
    public void updateLeaderboard(PlayerID playerID, int newElo) {
        rankedPlayers.put(playerID, newElo);
    }

    //method for getting a player's rank.
    public int getPlayerRank(PlayerID playerID) {
        return rankedPlayers.getOrDefault(playerID, 1);
    }
}
