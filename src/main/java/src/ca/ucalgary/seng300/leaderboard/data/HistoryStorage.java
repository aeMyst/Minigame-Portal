package src.ca.ucalgary.seng300.leaderboard.data;

import java.util.ArrayList;

/**
 *  Class representing the storage for HistoryPlayers used for match histories
 */
public class HistoryStorage {

    private ArrayList<HistoryPlayer> players;

    /**
     * Constructor to initialize the HistoryStorage object with a list of HistoryPlayer used in match histories
     * @param players
     */
    public HistoryStorage(ArrayList<HistoryPlayer> players){
        this.players = players;
    }

    /**
     * Default constructor to initialize a HistoryStorage object with an empty list
     */
    public HistoryStorage(){
        ArrayList<HistoryPlayer> emptyPlayers = new ArrayList<HistoryPlayer>();
        this.players = emptyPlayers;
    }

    /**
     * Adds a HistoryPlayer to HistoryStorage
     * @param player The HistoryPlayer to be added to the storage
     */
    public void addPlayerHistory(HistoryPlayer player){
        this.players.add(player);
    }

    /**
     * Fetches the list of HistoryPlayer objects from HistoryStorage
     * @return The list of HistoryPlayer objects
     */
    public ArrayList<HistoryPlayer> getPlayersHistory(){
        return this.players;
    }
}
