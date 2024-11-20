package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

public interface IEloRating {

    int calculateNewElo(int myElo, int enemyElo, boolean won);

    void updateElo(Player winner, Player loser);
}
