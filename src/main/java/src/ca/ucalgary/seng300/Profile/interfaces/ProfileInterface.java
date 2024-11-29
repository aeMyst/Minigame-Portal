package src.ca.ucalgary.seng300.Profile.interfaces;

import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.models.Profile;

/**
 * Interface to define methods implemented in ProfileService and ProfileController
 * @author
 */
public interface ProfileInterface {
    // Initializing string field
    String viewProfile();

    // Method to update profile information
    void updateProfile(User user, String newUsername, String newEmail, String newPassword);

    // Method to track game history
    void trackGameHistory(User user, Profile profile);

    // Method to update ranking
    void updateRanking(User user, int rank);

}