package src.ca.ucalgary.seng300.gamelogic.Connect4;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

import java.io.Console;

/**
 * A class representing a connect 4 game piece, that tracks the piece and it's owner
 */
public class UserPiece implements IUserPiece {
    private int piece;
    private Player player;

    /**
     * A consturctor to create a game piece 
     * 
     * @param piece The piece to be tracked
     * @param player The owner of the piece
     */
    public UserPiece(Player player, int piece) {
        this.player = player;
        this.piece = piece;
    }

    /**
     * A method to update the owner of a piece
     * 
     * @param player The new owner
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Find the owner of a piece
     * 
     * @return The owner of the piece
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Find the piece that's being tracked
     * 
     * @return The piece belonging to the player
     */
    public int getPiece() {
        return piece;
    }
}
