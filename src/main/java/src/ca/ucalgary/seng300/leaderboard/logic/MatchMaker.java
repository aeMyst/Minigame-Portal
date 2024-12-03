package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchmaker;

import java.util.ArrayList;

/**
 * Implements matchmaking logic for pairing players based on game type and Elo ratings.
 * Matches players who meet specified criteria or selects a random player if no suitable
 * match is found.
 */
public class MatchMaker implements IMatchmaker {
    // List of players in a match
    public ArrayList<Player> match = new ArrayList<>();
    // Queue of players waiting for a match
    public ArrayList<Player> queue = new ArrayList<>();
    // Threshold for acceptable Elo difference between players
    private static final int THRESHOLD = 150;

    private Storage storage;

    /**
     * Constructs a MatchMaker instance with the given storage of player data.
     *
     * @param storage The storage instance containing player data.
     */
    public MatchMaker(Storage storage) {
        this.storage = storage;
    }

    /**
     * Adds specific players to a match based on their IDs and game type.
     *
     * @param challengeUser The ID of the user issuing the challenge.
     * @param currentUser   The ID of the current user.
     * @param gameType      The type of game to match players for.
     */
    public void challengePlayerQueue(String challengeUser, String currentUser, String gameType) {
        for (Player player : storage.getPlayers()) {
            // Add both the challenger and the current user to the match if their game types match
            if (player.getGameType().equals(gameType) && player.getPlayerID().equals(challengeUser)) {
                match.add(player);
            } else if (player.getGameType().equals(gameType) && player.getPlayerID().equals(currentUser)) {
                match.add(player);
            }
        }
    }

    /**
     * Adds a player to the matchmaking queue for the specified game type and
     * attempts to find a match.
     *
     * @param user     The ID of the user being added to the queue.
     * @param gameType The type of game the user wants to play.
     */
    @Override
    public void addPlayerToQueue(String user, String gameType) {
        // Create a default player representation for the user
        Player currentUserPlayer = Player.defaultPlayer(gameType, user);
        if (!queue.contains(currentUserPlayer)) {
            queue.add(currentUserPlayer);
        }

        // Add all players of the same game type to the queue
        for (Player player : storage.getPlayers()) {
            if (player.getGameType().equals(gameType) && !queue.contains(player)) {
                queue.add(player);
            }
        }

        // Attempt to find a match for the user
        findMatch(user);
    }

    /**
     * Attempts to find a match for the given user based on Elo ratings and game type.
     *
     * @param user The ID of the user looking for a match.
     */
    @Override
    public void findMatch(String user) {
        Player userPlayer = null;

        // Find the player object corresponding to the user's ID in the queue
        for (Player player : queue) {
            if (player.getPlayerID().equals(user)) {
                userPlayer = player;
                break;
            }
        }

        // If the user is not in the queue, return with an error message
        if (userPlayer == null) {
            System.err.println("Player with ID " + user + " not found in the queue.");
            return;
        }

        Player closestMatch = null;

        // Find the closest match based on Elo ratings within the threshold
        for (Player player : queue) {
            if (!player.getPlayerID().equals(user)) {
                int eloDifference = Math.abs(userPlayer.getElo() - player.getElo());
                if (eloDifference <= THRESHOLD) {
                    closestMatch = player;
                    break;
                }
            }
        }

        if (closestMatch == null && !queue.isEmpty()) {
            closestMatch = queue.get(0);
        }

        // If a match is found, add both players to the match list
        if (closestMatch != null) {
            match.add(userPlayer);
            match.add(closestMatch);
            queue.remove(userPlayer);
            queue.remove(closestMatch);
        } else {
            // No other players available for matching
            System.out.println("No other players available for matching.");
        }
    }

    /**
     * Creates and returns the match list, which contains the two matched players.
     *
     * @return A list of players in the created match.
     */
    @Override
    public ArrayList<Player> createMatch() {
        if (match.size() >= 2) {
            System.out.println("Match created between: " + match.get(0).getPlayerID() +
                    " and " + match.get(1).getPlayerID());
        } else {
            System.err.println("Insufficient players to create a match.");
        }
        return match;
    }
}
