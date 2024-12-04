package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

/**
 * Interface for Elo rating functionality in a leaderboard system.
 */
public interface IEloRating {

    /**
     * Calculates the new Elo rating after a match.
     *
     * @param myElo The current Elo rating of the player.
     * @param enemyElo The current Elo rating of the opponent.
     * @param won A boolean indicating if the player won the match.
     * @return The new Elo rating of the player.
     */
    int calculateNewElo(int myElo, int enemyElo, boolean won);

    /**
     * Updates the Elo ratings of the winner and loser after a match.
     *
     * @param winner The player who won the match.
     * @param loser The player who lost the match.
     */
    void updateElo(Player winner, Player loser);
}