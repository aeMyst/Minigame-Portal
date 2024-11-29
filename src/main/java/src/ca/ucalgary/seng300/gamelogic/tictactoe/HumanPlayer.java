package src.ca.ucalgary.seng300.gamelogic.tictactoe;

import src.ca.ucalgary.seng300.leaderboard.data.Player;


public class HumanPlayer implements IPlayer {
    private Player player;
    private char symbol;

    public HumanPlayer(Player player, char symbol) {
        this.player = player;
        this.symbol = symbol;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public char getSymbol() {
        return symbol;
    }

}
