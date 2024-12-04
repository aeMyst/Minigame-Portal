package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.utils.ValidationUtils;

import java.io.*;
import java.util.*;

/**
 * ProfileService class containing methods from ProfileInterface, methods used by ProfileController class to
 * handle profile information
 * @author
 */
public class ProfileService implements ProfileInterface {

    //Path storing users data and their profile data
    public String profilePath = "src/main/java/src/ca/ucalgary/seng300/database/profiles.csv";
    public String usersPath = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";

    //Initialize authservice
    private final AuthService authService;

    //Constructor for ProfileService
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
        // Instantiate user as current logged in user
        User loggedInUser = authService.isLoggedIn();

        // Check if no user is logged in
        if (loggedInUser == null) {
            return "No user is currently logged in.";
        }

        // Get username and display string versionof profile
        String username = loggedInUser.getUsername();
        String displayProfile = buildProfileString(username);
        // Check if profile exists
        if (displayProfile.isEmpty()) {
            initializeProfile(username);
            // Rebuild after initialization
            displayProfile = buildProfileString(username);
        }
        return displayProfile;
    }

    /**
     * Function to update profile information
     * @param user
     * @param newUsername
     * @param newEmail
     * @param newPassword
     */
    @Override
    public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {
        // Check if at least one field is updated
        if (!isValidUpdate(newUsername, newEmail, newPassword)) return;

        // Retrieve information from stored users
        List<String[]> users = readCsv(usersPath);
        boolean userFound = false;
        // Find user in List
        for (int i = 0; i < users.size(); i++) {
            String[] details = users.get(i);
            // Find user in csv file
            if (details.length == 3 && details[1].trim().equalsIgnoreCase(user.getUsername())) {
                userFound = true;
                // Update changed information
                users.set(i, new String[]{
                        isBlank(newEmail) ? details[0].trim() : newEmail,
                        isBlank(newUsername) ? details[1].trim() : newUsername,
                        isBlank(newPassword) ? details[2].trim() : newPassword
                });
                break;
            }
        }
        // Return error if user not found
        if (!userFound) {
            System.out.println("User not found.");
            return;
        }
        // Update username if desired
        if (!isBlank(newUsername)) updateProfileUsername(user.getUsername(), newUsername);
        writeCsv(usersPath, users);

        if (!isBlank(newUsername)) {
            authService.updateCurrentUser(newUsername, isBlank(newEmail) ? user.getEmail() : newEmail);
        }

        System.out.println("Profile updated successfully.");
    }

    /**
     * Function to instantiate new profile with default values
     * @param username
     */
    public void initializeProfile(String username) {
        // For each game type, set each value to zero and append to csv file
        String[] gameTypes = {"CHECKERS", "CONNECT4", "TICTACTOE"};
        for (String gameType : gameTypes) {
            String defaultProfile = String.format("%s,%s,0,0,0,0", gameType, username);
            appendToCsv(profilePath, defaultProfile);
        }
        System.out.println("Default profiles created for user: " + username);
    }

    /**
     * Function to search profile and display profile information
     * @param username
     * @return Profile information if profile exists
     * @return Profile not found if profile does not exist
     */
    @Override
    public String searchProfile(String username) {
        // Call buildProfileString()
        String displayProfile = buildProfileString(username);
        // Check if profile exists
        if (displayProfile.isEmpty()){
            return "Profile not found for " + username;
        }
        return displayProfile;
    }

    /**
     * Function to convert profile information to string
     * @param username
     * @return Profile information
     * @return Profile not found if error occurs
     */
    private String buildProfileString(String username) {
        // Initialize new StringBuilder
        StringBuilder profileBuilder = new StringBuilder();
        // Load profile information from csv
        List<String[]> profiles = readCsv(profilePath);
        boolean profileFound = false;
        // Parse each profile detail from string to integer
        for (String[] details : profiles) {
            if (details.length == 6 && details[1].trim().equalsIgnoreCase(username)) {
                profileFound = true;
                int elo = parseInt(details[2]);
                int wins = parseInt(details[3]);
                int losses = parseInt(details[4]);
                int draws = parseInt(details[5]);
                int gamesPlayed = wins + losses + draws;

                // Append to Stringbuilder
                profileBuilder.append(String.format(
                        "Gametype: %s\nPlayerID: %s\nElo: %d\nWins: %d\nLosses: %d\nDraws: %d\nGames Played: %d\n\n",
                        details[0].trim(), details[1].trim(), elo, wins, losses, draws, gamesPlayed
                ));
            }
        }
        // Return error message if profile not found
        if (!profileFound) {
            System.out.println("Profile not found for username: " + username);
        }
        // Return profile string
        return profileBuilder.toString().trim();
    }

    /**
     * Helper function to read from csv file
     * @param filePath
     * @return Lines from csv
     */
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

    /**
     * Helper method to write to csv file
     * @param filePath
     * @param data
     */
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

    /**
     * Helper method to append to csv file
     * @param filePath
     * @param line
     */
    private void appendToCsv(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    /**
     * Function to update profile username
     * @param oldUsername
     * @param newUsername
     */
    private void updateProfileUsername(String oldUsername, String newUsername) {
        // Read data from csv
        List<String[]> profiles = readCsv(profilePath);
        // Find profile in list
        for (int i = 0; i < profiles.size(); i++) {
            String[] details = profiles.get(i);
            // If profile found, set new username
            if (details.length == 6 && details[1].trim().equalsIgnoreCase(oldUsername)) {
                details[1] = newUsername.trim();
                profiles.set(i, details);
            }
        }
        // Add new data to csv
        writeCsv(profilePath, profiles);
    }

    /**
     *  Function to check to see at least one field is changed and follows the correct validation format
     */
    private boolean isValidUpdate(String newUsername, String newEmail, String newPassword) {
        // Checl if fields are blank
        if (isBlank(newUsername) && isBlank(newEmail) && isBlank(newPassword)) {
            System.out.println("At least one field must be changed.");
            return false;
        }
        // Check if fields follow correct format
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

    /**
     * Function to check if string value is blank
     * @param str
     * @return boolean
     */
    private boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    /**
     * Function to parse string value to integer
     * @param str
     * @return
     */
    private int parseInt(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
