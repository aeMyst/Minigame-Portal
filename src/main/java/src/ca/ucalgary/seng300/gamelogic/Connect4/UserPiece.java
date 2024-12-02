package src.ca.ucalgary.seng300.gamelogic.Connect4;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

import java.io.Console;

public class UserPiece implements IUserPiece {
    private int piece;
    private Player player;

    public UserPiece(Player player, int piece) {
        this.player = player;
        this.piece = piece;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPiece() {
        return piece;
    }
}
