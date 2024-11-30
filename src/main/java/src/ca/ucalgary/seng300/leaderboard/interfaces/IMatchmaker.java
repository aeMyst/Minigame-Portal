package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import java.util.ArrayList;

public interface IMatchmaker {

    void addPlayerToQueue(String user, String gameType);

    void findMatch(String user);

    ArrayList<Player> createMatch();
}
