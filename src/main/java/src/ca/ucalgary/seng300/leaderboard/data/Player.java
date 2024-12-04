package src.ca.ucalgary.seng300.leaderboard.data;

/**
 * Class representing a player in the leaderboard system.
 */
public class Player {
    private String gameType;
    private String playerID;
    private int elo;
    private int wins;
    private int losses;
    private int ties;

    /**
     * Constructor to initialize a Player object with specified attributes.
     *
     * @param gameType The type of game the player is playing.
     * @param playerID The unique identifier for the player.
     * @param elo The Elo rating of the player.
     * @param wins The number of wins the player has.
     * @param losses The number of losses the player has.
     * @param ties The number of ties the player has.
     */
    public Player(String gameType, String playerID, int elo, int wins, int losses, int ties) {
        this.gameType = gameType;
        this.playerID = playerID;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    /**
     * Static method to create a default Player object with zeroed statistics.
     *
     * @param gameType The type of game the player is playing.
     * @param playerID The unique identifier for the player.
     * @return A new Player object with default values.
     */
    public static Player defaultPlayer(String gameType, String playerID) {
        return new Player(gameType, playerID, 0, 0, 0, 0);
    }

    // Getter and setter methods for the player's attributes

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getElo() {
        return elo;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWins() {
        return wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getLosses() {
        return losses;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public int getTies() {
        return ties;
    }

    public static Player fromCsv(String csv) {
        String[] parts = csv.split(",");
        // game, playerID, elo, wins, losses, ties
        return new Player(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
    }

    public String toCsv() {
        return this.getGameType() + "," + this.getPlayerID() + "," + this.getElo() + "," + this.getWins() + "," + this.getLosses() + "," + this.getTies();
    }


    /**
     * Returns a string representation of the player's attributes.
     *
     * @return A string containing the player's game type, ID, Elo rating, wins, losses, and ties.
     */
    @Override
    public String toString() {
        return "GameType: " + gameType +
                "\tPlayerID: " + playerID +
                "\tElo: " + elo +
                "\tWins: " + wins +
                "\tLosses: " + losses +
                "\tTies: " + ties;
    }

}
