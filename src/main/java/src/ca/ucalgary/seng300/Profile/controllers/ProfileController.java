package src.ca.ucalgary.seng300.Profile.controllers;

import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;

public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // View a user's profile
    public String viewProfile() {
       return profileService.viewProfile();
    }

    // Update a user's profile (username, email, password)
    public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {
        profileService.updateProfile(user, newUsername, newEmail, newPassword);
    }

    // Track game history (increment games played)
    public void initializeProfile(String username) {
        profileService.initializeProfile(username);
    }

    // Update ranking
    public void searchProfile(String username) {
        profileService.searchProfile(username);
    }


}