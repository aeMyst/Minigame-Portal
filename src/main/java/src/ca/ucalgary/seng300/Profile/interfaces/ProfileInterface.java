package src.ca.ucalgary.seng300.Profile.interfaces;

import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.models.Profile;


public interface ProfileInterface {
    String viewProfile();
    void updateProfile(User user, String newUsername, String newEmail, String newPassword);
    void initializeProfile(String username);
    String searchProfile(String username);

}