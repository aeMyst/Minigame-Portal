package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;

import java.io.*;
import java.util.*;

public class ProfileService implements ProfileInterface {

    @Override
    public String viewProfile(Profile profile) {
        // Logic to view a user's profile
        return profile.getProfileDetails();
    }

    public String displayProfileFromFile(String username) {
        String filePath = "Usersprofiles.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equalsIgnoreCase(username)) {
                    return String.format("Username: %s, Email: %s, Games Played: %s, Wins: %s, Losses: %s, Rank: %s",
                            details[0], details[1], details[2], details[3], details[4], details[5]);
                }
            }
        } catch (IOException e) {
            return "Error reading profile file: " + e.getMessage();
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