package src.ca.ucalgary.seng300.leaderboard.data;

import java.util.ArrayList;
public class Storage {
    private ArrayList<Player> players;

    public Storage(ArrayList<Player> players){
        this.players = players;
    }

    public Storage(){
        ArrayList<Player> emptyPlayers = new ArrayList<Player>();
        this.players = emptyPlayers;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public void updatePlayer(Player updatedPlayer) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getPlayerID().equals(updatedPlayer.getPlayerID())) {
                System.out.println("updating player: " + updatedPlayer.getPlayerID());
                this.players.set(i, updatedPlayer);
                return;
            }
        }
        System.out.println("adding new player: " + updatedPlayer.getPlayerID());
        players.add(updatedPlayer);
    }
}
