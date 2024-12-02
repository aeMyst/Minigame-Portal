/**
 * Class to implement methods from ProfileService to handle profile information
 * @author
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
        profileService.updateProfile(user, newUsername, newEmail, newPassword);
    }

    /**
     * Method to initialize profile for new user
     * @param username
     */
    public void initializeProfile(String username) {
        profileService.initializeProfile(username);
    }

    /**
     * Method to search a user's profile
     * @param username
     */
    public void searchProfile(String username) {
        profileService.searchProfile(username);
    }

}