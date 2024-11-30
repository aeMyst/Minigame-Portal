package src.ca.ucalgary.seng300.Profile.models;

public class Profile {
    private User user;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int rank;

    public Profile(User user) {
        this.user = user;
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



    public String getProfileDetails() {
        return String.format("Username: %s, Email: %s, Games Played: %d, Wins: %d, Losses: %d, Rank: %d",
                user.getUsername(), user.getEmail(), gamesPlayed, wins, losses, rank);
    }
}