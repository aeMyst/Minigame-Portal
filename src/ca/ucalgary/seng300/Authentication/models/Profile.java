package src.ca.ucalgary.seng300.authentication.models;

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

    // Getters and Setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Profile(displayName=%s, gamesPlayed=%d, wins=%d, losses=%d, rank=%d, status=%s)",
                displayName, gamesPlayed, wins, losses, rank, status);
    }
}