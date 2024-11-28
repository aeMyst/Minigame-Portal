package src.ca.ucalgary.seng300.leaderboard.interfaces;


import src.ca.ucalgary.seng300.leaderboard.data.Player;

public interface IMatchmaker {

    void addPlayerToQueue(Player player);

    void findMatch();

    void createMatch(Player player1, Player player2);
}
