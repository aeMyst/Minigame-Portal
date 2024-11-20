package src.ca.ucalgary.seng300.leaderboard.interfaces;


public interface IMatchmaker {
    void addPlayerToQueue(EloData player);
    void findMatch();
}
