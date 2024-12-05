package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import java.util.ArrayList;

/**
 * Interface for matchmaking functionality in a leaderboard system.
 */
public interface IMatchmaker {

    /**
     * Adds a player to the matchmaking queue.
     *
     * @param user The username of the player.
     * @param gameType The type of game the player wants to play.
     */
    void addPlayerToQueue(String user, String gameType);

    /**
     * Finds a match for the specified player.
     *
     * @param user The username of the player.
     */
    void findMatch(String user);

    /**
     * Creates a match from the players in the queue.
     *
     * @return An ArrayList of Player objects representing the players in the match.
     */
    ArrayList<Player> createMatch();
}