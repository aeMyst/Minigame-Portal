package src.ca.ucalgary.seng300.authentication.controllers;

import src.ca.ucalgary.seng300.authentication.models.User;
import src.ca.ucalgary.seng300.authentication.models.Profile;
import src.ca.ucalgary.seng300.authentication.services.ProfileService;

public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // View a user's profile
    public void viewProfile(User user) {
        profileService.viewProfile(user);
    }

    public void updateProfile(User user, String displayName, String status) {
        profileService.updateProfile(user, displayName, status);
    }

    // Track game history (increment games played)
    public void trackGameHistory(User user, Profile profile) {
        profileService.trackGameHistory(user, profile);
    }

    public void updateRanking(User user, int rank) {
        profileService.updateRanking(user, rank);
    }

    public void addWin(User user) {
        profileService.addWin(user.getProfile());
    }


    public void addLoss(User user) {
        profileService.addLoss(user.getProfile());
    }
}