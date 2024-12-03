package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchmaker;

import java.util.ArrayList;

public class MatchMaker implements IMatchmaker {
    public ArrayList<Player> match = new ArrayList<>();
    public ArrayList<Player> queue = new ArrayList<>();
    private static final int THRESHOLD = 150; // it's good practice to set up the 150 here for future bug fix/code
                                              // design
    private Storage storage;

    public MatchMaker(Storage storage) {
        this.storage = storage;
    }

    public void challengePlayerQueue(String challengeUser, String currentUser, String gameType) {
        for (Player player : storage.getPlayers()) {
            if (player.getGameType().equals(gameType) && player.getPlayerID().equals(challengeUser)) {
                match.add(player);
            } else if (player.getGameType().equals(gameType) && player.getPlayerID().equals(currentUser)) {
                match.add(player);
            }
        }
    }

    @Override
    public void addPlayerToQueue(String user, String gameType) {
        Player currentUserPlayer = Player.defaultPlayer(gameType, user);
        queue.add(currentUserPlayer);
        for (Player player : storage.getPlayers()) {
            if (player.getGameType().equals(gameType)) {
                queue.add(player);
            }
        }

        findMatch(user);
    }

    @Override
    public void findMatch(String user) {
        Player userPlayer = null;

        // Find the player with the matching playerId in the queue
        for (Player player : queue) {
            if (player.getPlayerID().equals(user)) {
                userPlayer = player;
                queue.remove(userPlayer);
                break;
            }
        }

        // If the user is not found, return
        if (userPlayer == null) {
            System.err.println("Player with ID " + user + " not found in the queue.");
            return;
        }

        // Find the closest Elo match for the user
        Player closestMatch = null;

        for (Player player : queue) {
            int eloDifference = Math.abs(userPlayer.getElo() - player.getElo());
            if (eloDifference <= THRESHOLD) {
                closestMatch = player;
            }
        }

        // If no suitable match is found, choose a random player
        if (closestMatch == null) {
            for (Player player : queue) {
                if (!player.getPlayerID().equals(user)) {
                    closestMatch = player;
                    break; // Take the first random player
                }
            }
        }

        // If a match is found
        if (closestMatch != null) {
            match.add(userPlayer);
            match.add(closestMatch);
            queue.remove(userPlayer);
            queue.remove(closestMatch);
        } else {
            System.out.println("No other players available for matching.");
        }
    }

    @Override
    public ArrayList<Player> createMatch() {
        System.out
                .println("Match created between: " + match.get(0).getPlayerID() + " and " + match.get(1).getPlayerID());
        return match;
    }
}