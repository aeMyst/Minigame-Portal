package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IEloRating;

public class EloRating implements IEloRating {

    //we're going to calculate new elo ratings from your current elo and the enemy's elo
    @Override
    public int calculateNewElo(int myElo, int enemyElo, boolean won) {
<<<<<<< HEAD
        int K = 48;  // Constant K-factor used in calculation
=======
        int K = 200;  // Constant K-factor used in calculation
>>>>>>> main
        double expectedScore = 1.0 / (1 + Math.pow(10, (enemyElo - myElo) / 400.0));

        int actualScore;
        if (won) {
            actualScore = 1;
        } else {
            actualScore = 0;
        }

        // Calculates the new rating
        int newElo = myElo + (int)(K * (actualScore - expectedScore));

        // Ensures the rating does not go below 0
        if (newElo < 0) {
            newElo = 0;
        }

        return newElo;
    }

    //this will be updated after every match
    @Override
    public void updateElo(Player winner, Player loser) {
        int myNewElo = calculateNewElo(winner.getElo(), loser.getElo(), true);
        int enemyNewElo = calculateNewElo(loser.getElo(), winner.getElo(), false);

        winner.setElo(myNewElo);
        loser.setElo(enemyNewElo);
    }
}
