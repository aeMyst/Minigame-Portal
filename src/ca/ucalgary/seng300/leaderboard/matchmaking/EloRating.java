package src.ca.ucalgary.seng300.leaderboard.matchmaking;

import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;

import java.util.HashMap;

public class EloRating {
    private HashMap<PlayerID, Integer> playerElo;

    public EloRating() {
        this.playerElo = new HashMap<>();
    }

    //method for setting player elo
    public void setPlayerElo(PlayerID playerID, int elo) {
        playerElo.put(playerID, elo);
    }

    //method for getting player elo
    public int getPlayerElo(PlayerID playerID, int elo) {
        return playerElo.getOrDefault(playerID,1);
    }

    //Method to set every player's pre-first game elo
    public void firstGameElo(PlayerID playerID, int elo) {
        playerElo.put(playerID, elo);
    }

    //Method to update a player's elo after win/loss
    public void updatePlayerElo(PlayerID won, PlayerID lost){
        int wonElo = playerElo.get(won);
        int lostElo = playerElo.get(lost);
    }

}
