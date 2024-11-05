package src.ca.ucalgary.seng300.authentication.controllers;

import src.ca.ucalgary.seng300.authentication.models.Profile;
import src.ca.ucalgary.seng300.authentication.models.User;
import src.ca.ucalgary.seng300.authentication.services.ProfileService;

public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // View a user's profile
    public String viewProfile(Profile profile) {
       return profileService.viewProfile(profile);
    }

    // Update a user's profile (username, email, password)
    public void updateProfile(User user, String newUsername, String newEmail, String newPassword) {
        profileService.updateProfile(user, newUsername, newEmail, newPassword);
    }

    // Track game history (increment games played)
    public void trackGameHistory(User user, Profile profile) {
        profileService.trackGameHistory(user, profile);
    }

    // Update ranking
    public void updateRanking(User user, int rank) {
        profileService.updateRanking(user, rank);
    }
}