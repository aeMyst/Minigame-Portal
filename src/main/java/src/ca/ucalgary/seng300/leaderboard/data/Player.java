package src.ca.ucalgary.seng300.leaderboard.data;

public class Player {
    private String playerID;
    private int elo;
    private int wins;
    private int losses;

    public Player(String playerID, int elo, int wins, int losses) {
        this.playerID = playerID;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
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

    public String toString() {
        return playerID + "\t" + elo + "\t" + wins + "\t" + losses;
    }

}
