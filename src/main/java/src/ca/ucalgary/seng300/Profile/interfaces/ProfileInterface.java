package src.ca.ucalgary.seng300.authentication.interfaces;

import src.ca.ucalgary.seng300.authentication.models.User;
import src.ca.ucalgary.seng300.authentication.models.Profile;


public interface ProfileInterface {
    String viewProfile(Profile profile);
    void updateProfile(User user, String newUsername, String newEmail, String newPassword);
    void trackGameHistory(User user, Profile profile);
    void updateRanking(User user, int rank);

}