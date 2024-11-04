package src.ca.ucalgary.seng300.authentication.interfaces;

import src.ca.ucalgary.seng300.authentication.models.User;
import src.ca.ucalgary.seng300.authentication.models.Profile;
import src.ca.ucalgary.seng300.authentication.models.Game;

public interface ProfileInterface {
    void viewProfile(User user);
    void updateProfile(User user, String newUsername, String newEmail, String newPassword);
    void trackGameHistory(User user, Profile profile);
    void updateRanking(User user, int rank);

}