package src.ca.ucalgary.seng300.Profile.interfaces;

import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.models.Profile;

/**
 * Interface to define methods implemented in ProfileService and ProfileController to handle profile information
 * @author
 */
public interface ProfileInterface {
    // Initializing string field
    String viewProfile();
    // Method to update profile information
    void updateProfile(User user, String newUsername, String newEmail, String newPassword);
    //Method to initialize profile for new user
    void initializeProfile(String username);
    //Method to search user's profile
    String searchProfile(String username);

}