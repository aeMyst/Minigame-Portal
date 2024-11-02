package src.ca.ucalgary.seng300.leaderboard.interfaces;
import src.ca.ucalgary.seng300.leaderboard.EloRating;
public interface IEloRating {

    int updateElo(int myElo, int enemyElo, boolean won);
    void updateElo(EloRating winner, EloRating loser);

}
