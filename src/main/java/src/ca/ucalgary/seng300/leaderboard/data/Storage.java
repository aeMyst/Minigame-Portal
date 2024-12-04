package src.ca.ucalgary.seng300.leaderboard.data;

import java.util.ArrayList;

/**
 * Class representing the storage for players in the leaderboard system.
 */
public class Storage {
    private ArrayList<Player> players;

    /**
     * Constructor to initialize the Storage object with a list of players.
     *
     * @param players The list of players to be stored.
     */
    public Storage(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Default constructor to initialize the Storage object with an empty list of players.
     */
    public Storage() {
        ArrayList<Player> emptyPlayers = new ArrayList<Player>();
        this.players = emptyPlayers;
    }

    /**
     * Adds a player to the storage.
     *
     * @param player The player to be added.
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Retrieves the list of players from the storage.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Updates the information of an existing player in the storage.
     * If the player does not exist, adds the player to the storage.
     *
     * @param updatedPlayer The player with updated information.
     */
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
