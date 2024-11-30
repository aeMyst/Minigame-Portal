package src.ca.ucalgary.seng300.leaderboard.data;

public class Player {
    private String gameType;
    private String playerID;
    private int elo;
    private int wins;
    private int losses;
    private int ties;

    public Player(String gameType, String playerID, int elo, int wins, int losses, int ties) {
        this.gameType = gameType;
        this.playerID = playerID;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public static Player defaultPlauer(String gameType, String playerID){
        return new Player(gameType, playerID, 0, 0, 0, 0);
    }

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
