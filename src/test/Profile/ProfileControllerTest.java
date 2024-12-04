package Profile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.controllers.ProfileController;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;
import src.ca.ucalgary.seng300.Profile.services.AuthService;

import java.io.*;

public class ProfileControllerTest {

    private ProfileController profileController;
    private ProfileService profileService;
    private AuthService authService;

    @Before
    public void setUp() {
        authService = new AuthService(); // Initialize AuthService
        profileService = new ProfileService(authService); // Initialize ProfileService with AuthService
        profileController = new ProfileController(profileService); // Initialize ProfileController
    }

    // Test: View Profile when user is logged in
    @Test
    public void testViewProfile_UserLoggedIn() {
        String testUsername = "testUser";
        String testEmail = "testUser@example.com";
        String testPassword = "Password123!";

        authService.register(testEmail, testUsername, testPassword); // Register user
        authService.login(testUsername, testPassword); // Log in the user

        String result = profileController.viewProfile();

        Assert.assertNotNull("Profile should be returned for logged-in user", result);
        Assert.assertTrue("Profile should contain username", result.contains(testUsername));
    }

    // Test: View Profile when no user is logged in
    @Test
    public void testViewProfile_NoUserLoggedIn() {
        String result = profileController.viewProfile();
        Assert.assertEquals("No user is currently logged in.", result);
    }

    // Test: Update Profile
    @Test
    public void testUpdateProfile() {
        // Initialize a user and simulate saving them to the CSV file
        User testUser = new User("oldUsername", "Password1!", "oldEmail@example.com");
        String csvPath = profileService.usersPath;

        // Write the test user to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write(testUser.toCsv());
            writer.newLine();
        } catch (IOException e) {
            Assert.fail("Failed to set up test data: " + e.getMessage());
        }

        // Update the user's profile using the controller
        profileController.updateProfile(testUser, "newUsername", "newEmail@example.com", "NewPassword1!");

        // Read back the CSV and confirm the update
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String updatedLine = reader.readLine();
            Assert.assertNotNull("Updated line should not be null", updatedLine);

            // Convert the CSV line to a User object and validate
            User updatedUser = User.fromCsv(updatedLine);
            Assert.assertEquals("Username should be updated", "newUsername", updatedUser.getUsername());
            Assert.assertEquals("Email should be updated", "newEmail@example.com", updatedUser.getEmail());
            Assert.assertEquals("Password should be updated", "NewPassword1!", updatedUser.getPassword());
        } catch (IOException e) {
            Assert.fail("Failed to validate updated data: " + e.getMessage());
        }
    }

    // Test: Initialize Profile
    @Test
    public void testInitializeProfile() {
        String username = "newUser";
        profileController.initializeProfile(username);

        String profileData = profileService.searchProfile(username);

        Assert.assertNotNull("Profile should be created for the new user", profileData);
        Assert.assertTrue("Profile data should contain username", profileData.contains(username));
    }

    // Test: Search Profile when user exists
//    @Test
//    public void testSearchProfile_UserExists() {
//        String username = "testUser";
//        String email = "testUser@example.com";
//        String password = "Password123!";
//
//        authService.register(email, username, password); // Register the user
//
//        // Now, search for the profile using the controller
//        profileController.initializeProfile(username); // Initialize profile for the user (to ensure it exists)
//
//        String result = profileController.searchProfile(username); // Search for profile
//
//        Assert.assertNotNull("Profile should be found", result);
//        Assert.assertTrue("Profile should contain username", result.contains(username));
//    }
//
//    // Test: Search Profile when user does not exist
//    @Test
//    public void testSearchProfile_UserDoesNotExist() {
//        String username = "nonExistentUser";
//
//        // Attempt to search for a non-existent user profile
//        String result = profileController.searchProfile(username); // Search for non-existent user
//
//        Assert.assertEquals("Profile not found for non-existent user", "Profile not found for " + username, result);
//    }
}
