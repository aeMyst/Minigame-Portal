package src.ca.ucalgary.seng300.Profile.models;

/**
 * Class for Profile object and methods
 */
public class Profile {

    // Fields of profile object
    private User user;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int rank;

    // Constructor for Profile object
    public Profile(User user) {
        this.user = user;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.rank = 0;
    }

    // Getter method for games played
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    // Getter method for games won
    public int getWins() {
        return wins;
    }

    // Getter method for games lost
    public int getLosses() {
        return losses;
    }

    // Getter method for player rank
    public int getRank() {
        return rank;
    }


    /**
     * Function to return Profile details in string profile
     * @return String Profile details
     */
    public String getProfileDetails() {
        return String.format("Username: %s, Email: %s, Games Played: %d, Wins: %d, Losses: %d, Rank: %d",
                user.getUsername(), user.getEmail(), gamesPlayed, wins, losses, rank);
    }
}