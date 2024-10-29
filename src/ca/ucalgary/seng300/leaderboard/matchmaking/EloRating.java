package src.ca.ucalgary.seng300.leaderboard.matchmaking;

import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;

import java.util.HashMap;

public class EloRating {
    private HashMap<PlayerID, Integer> playerElo;

    public EloRating() {
        this.playerElo = new HashMap<>();
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

    //We'll figure out some elo calculation formula for this method later.
//    public int calculateNewElo(int currentelo, int enemyelo, boolean winner) {
//        we do the math later.
//        will return the new elo replacing current.
//    }

    //method for getting player elo.
    public int getPlayerElo(PlayerID playerID) {
        return playerElo.getOrDefault(playerID,1);
    }
}
