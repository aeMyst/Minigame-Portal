package src.ca.ucalgary.seng300.leaderboard.data;

public class HistoryPlayer {

    private String gameType;
    private String playerID;
    private String winnerString;
    private String loserString;
    private int eloGained;
    private int eloLost;
    private String date;

    public HistoryPlayer(String gameType, String playerID, String winnerString, String loserString, int eloGained, int eloLost, String date) {
        this.gameType = gameType;
        this.playerID = playerID;
        this.winnerString = winnerString;
        this.loserString = loserString;
        this.eloGained = eloGained;
        this.eloLost = eloLost;
        this.date = date;
    }

    public void setGameTypeHistory(String gameType) { this.gameType = gameType; }

    public void setPlayerIDHistory(String playerID) { this.playerID = playerID; }

    public void setWinnerString(String winnerString) { this.winnerString = winnerString; }

    public void setLoserString(String loserString) { this.loserString = loserString; }

    public void setEloGained(int eloGained) { this.eloGained = eloGained; }

    public void setEloLost(int eloLost) { this.eloLost = eloLost; }

    public void setDate(String date) { this.date = date; }

    public String getGameTypeHistory() { return gameType; }

    public String getPlayerIDHistory() { return playerID; }

    public String getWinnerString() { return winnerString; }

    public String getLoserString() { return loserString; }

    public int getEloGained() { return eloGained; }

    public int getEloLost() { return eloLost; }

    public String getDate() { return date; }


}
