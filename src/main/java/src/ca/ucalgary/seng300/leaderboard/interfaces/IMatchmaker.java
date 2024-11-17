package src.ca.ucalgary.seng300.leaderboard.interfaces;

import src.ca.ucalgary.seng300.leaderboard.logic.EloData;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;


public interface IMatchmaker {
    void addPlayerToQueue(EloData player);

    void findMatch();
}
