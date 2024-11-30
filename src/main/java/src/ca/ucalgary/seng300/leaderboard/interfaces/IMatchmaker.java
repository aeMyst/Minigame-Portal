package src.ca.ucalgary.seng300.leaderboard.interfaces;


import src.ca.ucalgary.seng300.leaderboard.data.Player;
<<<<<<< HEAD

public interface IMatchmaker {

    void addPlayerToQueue(Player player);

    void findMatch();

    void createMatch(Player player1, Player player2);
=======
import java.util.ArrayList;

public interface IMatchmaker {

    void addPlayerToQueue(String user, String gameType);

    void findMatch(String user);

    ArrayList<Player> createMatch();
>>>>>>> main
}
