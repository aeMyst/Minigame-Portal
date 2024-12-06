package src.ca.ucalgary.seng300.gamelogic.tictactoe;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

/**
 * human player class that holds the player contents (info + symbol)
 */
public class HumanPlayer implements IPlayer {
    // instance variables
    private Player player;
    private char symbol;

    /**
     * constructor class
     */
    public HumanPlayer(Player player, char symbol) {
        this.player = player;
        this.symbol = symbol;
    }

    /**
     * set the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * get the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * get the associated symbol
     */
    public char getSymbol() {
        return symbol;
    }

}
