package src.ca.ucalgary.seng300.leaderboard.data;

import java.util.ArrayList;

public class HistoryStorage {

    private ArrayList<HistoryPlayer> players;

    public HistoryStorage(ArrayList<HistoryPlayer> players){
        this.players = players;
    }

    public HistoryStorage(){
        ArrayList<HistoryPlayer> emptyPlayers = new ArrayList<HistoryPlayer>();
        this.players = emptyPlayers;
    }

    public void addPlayerHistory(HistoryPlayer player){
        this.players.add(player);
    }

    public ArrayList<HistoryPlayer> getPlayersHistory(){
        return this.players;
    }
}
