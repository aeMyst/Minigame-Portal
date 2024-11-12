package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchmaker;

import java.util.ArrayList;
import java.util.List;

public class MatchMaker implements IMatchmaker {
    private ArrayList<EloData> queue = new ArrayList<>();

    //method for adding players to queue after we first parsed all info from csv
    public void addPlayerToQueue(EloData player) {
        queue.add(player);
        findMatch();
    }

    public void findMatch() {
        //some loop that goes through the players in queue, grabs first 2 of similar elos

    }

    private void createMatch(EloData player1, EloData player2) {
        //two players gets added into match
    }

    public List<EloData> getQueue() {
        return null;
    }
}

