package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchmaker;

import java.util.List;
import java.util.ArrayList;

public class MatchMaker implements IMatchmaker {
    private ArrayList<EloData> queue = new ArrayList<>();

    //method for adding players to queue after we first parsed all info from csv
    public void addPlayerToQueue(EloData player) {
        queue.add(player);
        findMatch();
    }

    public void findMatch() {
        // some loop that goes through the players in queue, grabs first 2 of similar elos
        for (int p1 = 0; p1 < queue.size(); p1++) {
            for (int p2 = p1 + 1; p2 < queue.size(); p2++) {
                // assuming a threshold of 150
                if (queue.get(p1).getElo() - queue.get(p2).getElo() <= 150) {
                    createMatch(queue.get(p1), queue.get(p2));
                    queue.remove(p2);
                    queue.remove(p1);
                    return;
                }
            }
        }
    }

    private void createMatch(EloData player1, EloData player2) {
        System.out.println("Match created between: " + player1.getPlayerID()+ " and " + player2.getPlayerID());
    }
}