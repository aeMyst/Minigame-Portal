package src.ca.ucalgary.seng300.gamelogic.Connect4;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

/**
 * An interface used as the skeleton of a player piece
 */
public interface IUserPiece {
    int getPiece();

    void setPlayer(Player player);

    Player getPlayer();
}
