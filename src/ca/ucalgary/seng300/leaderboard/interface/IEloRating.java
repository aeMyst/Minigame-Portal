public interface IEloRating {
    int updateElo(int myElo, int enemyElo, boolean won);
    void updateElo(EloRating winner, EloRating loser);
}