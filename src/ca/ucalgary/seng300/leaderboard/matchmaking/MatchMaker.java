package src.ca.ucalgary.seng300.leaderboard.matchmaking;

import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;

import java.util.List;

public class MatchMaker {
    private List<PlayerID> playersInQueue;

    //Method for finding suitable match for players in queue.
    public void findMatch() {
        //We'll loop through the players in order and figure out some algorithm for grouping similar ratings together.
        PlayerID player1;
        PlayerID player2;

        findMatch();
        return;
    }

    //Method for adding players into queue.
    public void addPlayerToQueue(PlayerID playerID) {
        playersInQueue.add(playerID);
        findMatch();
    }

    public void createMatch(PlayerID player1, PlayerID player2) {
        //we will grab a boolean value on whether player1 wins and .updatePlayerElo
    }


}
