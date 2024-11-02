package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;

import java.util.List;
import java.util.ArrayList;

public class MatchMaker {
    private List<PlayerID> playersInQueue;

    public MatchMaker() {
        this.playersInQueue = new ArrayList<>();
    }

    //Method for adding players into queue.
    public void addPlayerToQueue(PlayerID playerID) {
        playersInQueue.add(playerID);

    }

    public void removePlayerFromQueue(PlayerID playerID) {
        playersInQueue.remove(playerID);
    }

    public List<PlayerID> getPlayersInQueue() {
        return new ArrayList<>(playersInQueue);
    }
}
