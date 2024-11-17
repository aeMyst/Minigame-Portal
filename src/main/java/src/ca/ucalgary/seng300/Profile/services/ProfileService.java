package src.ca.ucalgary.seng300.Profile.services;

import src.ca.ucalgary.seng300.Profile.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;

public class ProfileService implements ProfileInterface {

    @Override
    public String viewProfile(Profile profile) {
        // Logic to view a user's profile
        return profile.getProfileDetails();
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