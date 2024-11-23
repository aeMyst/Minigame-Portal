package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.interfaces.IEloRating;

public class EloRating implements IEloRating {

    //we're going to calculate new elo ratings from your current elo and the enemy's elo
    @Override
    public int calculateNewElo(int myElo, int enemyElo, boolean won) {
        //we'll figure out some logic for the formula later

        return myElo;
    }

    //this will be updated after every match
    @Override
    public void updateElo(EloData winner, EloData loser) {
        int myNewElo = calculateNewElo(winner.getElo(), loser.getElo(), true);
        int enemyNewElo = calculateNewElo(loser.getElo(), winner.getElo(), false);

        winner.setElo(myNewElo);
        loser.setElo(enemyNewElo);
    }
}
