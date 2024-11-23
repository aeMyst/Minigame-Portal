package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchmaker;

import java.util.ArrayList;
import java.util.List;

public class MatchMaker implements IMatchmaker {
    private ArrayList<Player> queue = new ArrayList<>();
    private static final int THRESHOLD = 150; //it's good practice to set up the 150 here for future bug fix/code design

    //method for adding players to queue after we first parsed all info from csv
    @Override
    public void addPlayerToQueue(Player player) {
        queue.add(player);
        findMatch();
    }

    @Override
    public void findMatch() {
        // some loop that goes through the players in queue, grabs first 2 of similar elos
        for (int p1 = 0; p1 < queue.size(); p1++) {
            for (int p2 = p1 + 1; p2 < queue.size(); p2++) {
                // assuming a threshold of 150
                if (queue.get(p1).getElo() - queue.get(p2).getElo() <= THRESHOLD) {
                    createMatch(queue.get(p1), queue.get(p2));
                    queue.remove(p2);
                    queue.remove(p1);
                    return;
                }
            }
        }
    }

    @Override
    public void createMatch(Player player1, Player player2) {
        System.out.println("Match created between: " + player1.getPlayerID()+ " and " + player2.getPlayerID());
    }
}