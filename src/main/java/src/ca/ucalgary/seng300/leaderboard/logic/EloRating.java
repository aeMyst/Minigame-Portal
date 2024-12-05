package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IEloRating;

/**
 * This class implements the Elo rating system used to calculate and update player ratings.
 * The Elo system adjusts player ratings based on match outcomes, factoring in the strength
 * of opponents and ensuring no rating falls below zero.
 */
public class EloRating implements IEloRating {

    /**
     * Calculates the new Elo rating for a player based on their current rating, the opponent's
     * rating, and the match outcome.
     *
     * @param myElo    The Elo rating of the player.
     * @param enemyElo The Elo rating of the opponent.
     * @param won      Whether the player won the match (true if they won, false otherwise).
     * @return The updated Elo rating for the player.
     */
    @Override
    public int calculateNewElo(int myElo, int enemyElo, boolean won) {
        int K = 200; // The K-factor determines the sensitivity of the rating changes.

        // Calculate the expected score based on Elo rating difference.
        double expectedScore = 1.0 / (1 + Math.pow(10, (enemyElo - myElo) / 400.0));

        // Determine the actual score: 1 for a win, 0 for a loss.
        int actualScore = won ? 1 : 0;

        // Calculate the new Elo rating using the formula.
        int newElo = myElo + (int)(K * (actualScore - expectedScore));

        // Ensure the Elo rating does not go below zero.
        if (newElo < 0) {
            newElo = 0;
        }

        return newElo;
    }

    /**
     * Updates the Elo ratings for both the winner and loser of a match.
     *
     * @param winner The player who won the match.
     * @param loser  The player who lost the match.
     */
    @Override
    public void updateElo(Player winner, Player loser) {
        // Calculate the winner's new Elo rating based on their victory.
        int myNewElo = calculateNewElo(winner.getElo(), loser.getElo(), true);

        // Calculate the loser's new Elo rating based on their defeat.
        int enemyNewElo = calculateNewElo(loser.getElo(), winner.getElo(), false);

        // Update the players' Elo ratings.
        winner.setElo(myNewElo);
        loser.setElo(enemyNewElo);
    }
}
