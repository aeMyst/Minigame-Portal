/**
 * Class to implement methods from ProfileInterface and implemented by
 */

package src.ca.ucalgary.seng300.Profile.controllers;

import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;

public class ProfileController {

    // Initialize ProfileService field
    private final ProfileService profileService;

    // Constructor for ProfileController object
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // View a user's profile
    public String viewProfile() {
        // Implement method from ProfileService
        return profileService.viewProfile();
    }

    /**
     * Method to update profile information
     * @param user
     * @param newUsername
     * @param newEmail
     * @param newPassword
     */
    public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {
        // Implement method from profileService class
        profileService.updateProfile(user, newUsername, newEmail, newPassword);
    }

    /**
     * Method to track player game history, increment games played
     * @param user
     * @param profile
     */
    public void trackGameHistory(User user, Profile profile) {
        // Implement method from profileService class
        profileService.trackGameHistory(user, profile);
    }

    /**
     * Method to updating player ranking after game finishes
     * @param user
     * @param rank
     */
    public void updateRanking(User user, int rank) {
        // Implement method from profileService class
        profileService.updateRanking(user, rank);
    }


}