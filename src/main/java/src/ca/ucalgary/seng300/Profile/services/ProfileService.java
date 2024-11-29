package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.utils.ValidationUtils;

import java.io.*;
import java.util.*;

public class ProfileService implements ProfileInterface {

    public String profilePath = "src/main/java/src/ca/ucalgary/seng300/Profile/services/profiles.csv";
    public String usersPath = "src/main/java/src/ca/ucalgary/seng300/Profile/services/users.csv";

    private final AuthService authService;

    public ProfileService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String viewProfile() {
        User loggedInUser = authService.isLoggedIn();

        if (loggedInUser == null) {
            return "No user is currently logged in.";
        }

        String username = loggedInUser.getUsername();
        String displayProfile = buildProfileString(username);
        if (displayProfile.isEmpty()) {
            initializeProfile(username);
            displayProfile = buildProfileString(username); // Rebuild after initialization
        }
        return displayProfile;
    }

    @Override
    public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {
        if (!isValidUpdate(newUsername, newEmail, newPassword)) return;

        List<String[]> users = readCsv(usersPath);
        boolean userFound = false;

        for (int i = 0; i < users.size(); i++) {
            String[] details = users.get(i);
            if (details.length == 3 && details[1].trim().equalsIgnoreCase(user.getUsername())) {
                userFound = true;
                users.set(i, new String[]{
                        isBlank(newEmail) ? details[0].trim() : newEmail,
                        isBlank(newUsername) ? details[1].trim() : newUsername,
                        isBlank(newPassword) ? details[2].trim() : newPassword
                });
                break;
            }
        }

        if (!userFound) {
            System.out.println("User not found.");
            return;
        }

        if (!isBlank(newUsername)) updateProfileUsername(user.getUsername(), newUsername);
        writeCsv(usersPath, users);

        if (!isBlank(newUsername)) {
            authService.updateCurrentUser(newUsername, isBlank(newEmail) ? user.getEmail() : newEmail);
        }

        System.out.println("Profile updated successfully.");
    }

    //for new user, instantiate profile with default values
    public void initializeProfile(String username) {
        String[] gameTypes = {"CHECKERS", "CONNECT4", "TicTacToe"};
        for (String gameType : gameTypes) {
            String defaultProfile = String.format("%s,%s,0,0,0,0", gameType, username);
            appendToCsv(profilePath, defaultProfile);
        }
        System.out.println("Default profiles created for user: " + username);
    }


    @Override
    public String searchProfile(String username) {
        String displayProfile = buildProfileString(username);
        if (displayProfile.isEmpty()){
            return "Profile not found for " + username;
        }
        return displayProfile;
    }

    private String buildProfileString(String username) {
        StringBuilder profileBuilder = new StringBuilder();
        List<String[]> profiles = readCsv(profilePath);
        boolean profileFound = false;

        for (String[] details : profiles) {
            if (details.length == 6 && details[1].trim().equalsIgnoreCase(username)) {
                profileFound = true;
                int elo = parseInt(details[2]);
                int wins = parseInt(details[3]);
                int losses = parseInt(details[4]);
                int draws = parseInt(details[5]);
                int gamesPlayed = wins + losses + draws;

                profileBuilder.append(String.format(
                        "Gametype: %s\nPlayerID: %s\nElo: %d\nWins: %d\nLosses: %d\nDraws: %d\nGames Played: %d\n\n",
                        details[0].trim(), details[1].trim(), elo, wins, losses, draws, gamesPlayed
                ));
            }
        }

        if (!profileFound) {
            System.out.println("Profile not found for username: " + username);
        }

        return profileBuilder.toString().trim();
    }

    // Helper methods
    private List<String[]> readCsv(String filePath) {
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return lines;
    }

    private void writeCsv(String filePath, List<String[]> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] line : data) {
                writer.write(String.join(",", line));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private void appendToCsv(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    private void updateProfileUsername(String oldUsername, String newUsername) {
        List<String[]> profiles = readCsv(profilePath);

        for (int i = 0; i < profiles.size(); i++) {
            String[] details = profiles.get(i);
            if (details.length == 6 && details[1].trim().equalsIgnoreCase(oldUsername)) {
                details[1] = newUsername.trim();
                profiles.set(i, details);
            }
        }

        writeCsv(profilePath, profiles);
    }

    //Check to see atleast one field is changed and follows the correct validation format
    private boolean isValidUpdate(String newUsername, String newEmail, String newPassword) {
        if (isBlank(newUsername) && isBlank(newEmail) && isBlank(newPassword)) {
            System.out.println("At least one field must be changed.");
            return false;
        }

        if (!isBlank(newUsername) && !ValidationUtils.isValidUsername(newUsername)) {
            System.out.println("Invalid username format.");
            return false;
        }

        if (!isBlank(newEmail) && !ValidationUtils.isValidEmail(newEmail)) {
            System.out.println("Invalid email format.");
            return false;
        }

        if (!isBlank(newPassword) && !ValidationUtils.isValidPassword(newPassword)) {
            System.out.println("Invalid password format.");
            return false;
        }

        return true;
    }

    private boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    private int parseInt(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
