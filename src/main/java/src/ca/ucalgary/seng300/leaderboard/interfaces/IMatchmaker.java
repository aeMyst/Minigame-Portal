package src.ca.ucalgary.seng300.leaderboard.interfaces;


public interface IMatchmaker {
    void addPlayerToQueue(Player player);
    void findMatch();
}
