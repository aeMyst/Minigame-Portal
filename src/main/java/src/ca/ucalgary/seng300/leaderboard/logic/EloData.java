package src.ca.ucalgary.seng300.leaderboard.logic;


//useless as of now, we will use Player.java class in the data folder
public class EloData {
    private String gameType;
    private String playerID;
    private int elo;
    private int gamesPlayed;
    private int wins;
    private int losses;

    public EloData(String gameType, String playerID, int elo, int gamesPlayed, int wins, int losses) {
        this.gameType = gameType;
        this.playerID = playerID;
        this.elo = elo;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.losses = losses;
    }

    public String getGameType() { return gameType; }

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


