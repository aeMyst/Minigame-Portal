package src.ca.ucalgary.seng300.authentication.models;

public class Profile {
    ;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int rank;

    public Profile() {
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.rank = 0;
    }

    // Getters and Setters

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



    @Override
    public String toString() {
        return String.format("Profile(displayName=%s, gamesPlayed=%d, wins=%d, losses=%d, rank=%d, status=%s)",
                displayName, gamesPlayed, wins, losses, rank);
    }
}