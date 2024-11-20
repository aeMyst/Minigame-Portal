package src.ca.ucalgary.seng300.leaderboard.interfaces;

public interface IEloRating {

    int calculateNewElo(int myElo, int enemyElo, boolean won);
    void updateElo(EloData winner, EloData loser);

}
