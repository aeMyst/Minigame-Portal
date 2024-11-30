package src.ca.ucalgary.seng300.gamelogic.Connect4;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

public interface IUserPiece {
    int getPiece();

    void setPlayer(Player player);

    Player getPlayer();
}
