package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;

import java.io.*;
import java.util.*;

public class ProfileService implements ProfileInterface {
    // Initialize authService
    private final AuthService authService;

    // Constructor for ProfileService object
    public ProfileService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Method to view profile information from profiles.csv file
     * Error message printed if method:
     *  1. Fails to read file
     *  2. Fails to parse numeric values
     *  3. User is not found
     * @return String
     */
    @Override
    public String viewProfile() {
        // Retrieve the currently logged-in user from AuthService
        User loggedInUser = authService.isLoggedIn();
        // Check if user is logged in
        if (loggedInUser == null) {
            return "No user is currently logged in.";
        }
        // Retrieve username
        String username = loggedInUser.getUsername();
        // Set filepath
        String filePath = "src/main/java/src/ca/ucalgary/seng300/Profile/services/profiles.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] details = line.split(",");
                if (details.length == 6) {
                    String gameType = details[0].trim();
                    String fileUsername = details[1].trim();
                    int elo = Integer.parseInt(details[2].trim());
                    int wins = Integer.parseInt(details[3].trim());
                    int losses = Integer.parseInt(details[4].trim());
                    int draws = Integer.parseInt(details[5].trim());

                    if (fileUsername.equalsIgnoreCase(username)) {
                        int gamesPlayed = wins + losses + draws;

                        return String.format(
                                "Gametype: %s\nPlayerID: %s\nElo: %d\nWins: %d\nLosses: %d\nDraws: %d\nGames Played: %d",
                                gameType, fileUsername, elo, wins, losses, draws, gamesPlayed
                        );
                    }
                }
            }
        } catch (IOException e) {
            return "Error reading profile file: " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Error parsing numeric values: " + e.getMessage();
        }
        return "Profile not found for username: " + username;
    }
    @Override
    public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {
        if (newUsername != null && !newUsername.isEmpty()) {
            user.setUsername(newUsername);
        }

        if (newEmail != null && !newEmail.isEmpty()) {
            user.setEmail(newEmail);
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }
    }

    @Override
    public void trackGameHistory(User user, Profile profile) {
        //TrackGameHistory
    }

    @Override
    public void updateRanking(User user, int rank) {
        //update ranking
    }
}