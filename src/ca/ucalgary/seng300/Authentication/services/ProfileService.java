package src.ca.ucalgary.seng300.authentication.services;

import src.ca.ucalgary.seng300.authentication.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.authentication.models.Profile;
import src.ca.ucalgary.seng300.authentication.models.User;

public class ProfileService implements ProfileInterface {

    @Override
    public void viewProfile(User user) {
        // Logic to view a user's profile
        
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