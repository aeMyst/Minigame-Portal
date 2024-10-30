package src.ca.ucalgary.seng300.authentication.services;

import src.ca.ucalgary.seng300.authentication.interfaces.ProfileInterface;
import src.ca.ucalgary.seng300.authentication.models.User;
import src.ca.ucalgary.seng300.authentication.models.Profile;

public class ProfileService implements ProfileInterface {

    @Override
    public void viewProfile(User user) {
        // Logic to view a user's profile
        System.out.println(user.getProfile().toString());
    }

    @Override
    public void updateProfile(User user, String displayName, String status) {
        user.getProfile().setDisplayName(displayName);
        user.getProfile().setStatus(status);
    }

    @Override
    public void trackGameHistory(User user, Profile profile) {
        profile.setGamesPlayed(profile.getGamesPlayed() + 1);

    }

    @Override
    public void updateRanking(User user, int rank) {
        user.getProfile().setRank(rank);
    }

    // wins/losses
    public void addWin(Profile profile) {
        profile.setWins(profile.getWins() + 1);
    }

    public void addLoss(Profile profile) {
        profile.setLosses(profile.getLosses() + 1);
    }
}