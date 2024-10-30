package src.ca.ucalgary.seng300.authentication.interfaces;

import src.ca.ucalgary.seng300.authentication.models.User;
import src.ca.ucalgary.seng300.authentication.models.Profile;
import src.ca.ucalgary.seng300.authentication.models.Game;

public interface ProfileInterface {
    void viewProfile(User user);
    void updateProfile(User user, String displayName, String status);
    void trackGameHistory(User user, Profile profile);
    void updateRanking(User user, int rank);
    void addWin(Profile profile);
    void addLoss(Profile profile);
}