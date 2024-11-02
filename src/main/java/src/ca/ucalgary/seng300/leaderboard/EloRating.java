package src.ca.ucalgary.seng300.leaderboard;

public class EloRating {
    private String playerID;
    private int elo;
    private int gamesPlayed;
    private int wins;
    private int losses;

    public EloRating(String playerID, int elo, int gamesPlayed, int wins, int losses) {
        this.playerID = playerID;
        this.elo = elo;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.losses = losses;
    }

    public String getPlayerID() {
        return playerID;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}

