package src.ca.ucalgary.seng300.Authentication.models;

public class Profile {

    private String displayName;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int rank;
    private String status;

    public Profile(String displayName) {
        this.displayName = displayName;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.rank = 0;
        this.status = "offline/or let's say invisible";
    }

    public String getDisplayName() {
        return displayName;
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

    public int getRank() {
        return rank;
    }

    public String getStatus() {
        return status;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }

    public void updateRank(int rank) {
        this.rank = rank;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    //Typical toString format for op.gg game profile
    public String toString() {
        return STR."Profile(displayName=\{displayName}, gamesPlayed=\{gamesPlayed}, wins=\{wins}, losses=\{losses}, rank=\{rank}, status=\{status})";
    }
}
