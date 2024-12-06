package src.ca.ucalgary.seng300.Profile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
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
    private String usersCsvPath;
    private String profilesCsvPath;
    private File usersBackupFile;
    private File profilesBackupFile;

    @Before
    public void setUp() {
        authService = new AuthService(); // Initialize AuthService
        profileService = new ProfileService(authService); // Initialize ProfileService with AuthService
        profileController = new ProfileController(profileService); // Initialize ProfileController

        //Paths to the CSV files
        usersCsvPath = profileService.usersPath;
        profilesCsvPath = profileService.profilePath;

        //Backup users.csv
        usersBackupFile = new File(usersCsvPath + ".backup");
        backupFile(usersCsvPath, usersBackupFile);

        //Backup profiles.csv
        profilesBackupFile = new File(profilesCsvPath + ".backup");
        backupFile(profilesCsvPath, profilesBackupFile);
    }

    @After
    public void tearDown() {
        // Restore users.csv
        restoreFile(usersCsvPath, usersBackupFile);
        // Restore profiles.csv
        restoreFile(profilesCsvPath, profilesBackupFile);
    }

    private void backupFile(String filePath, File backupFile) {
        try {
            File originalFile = new File(filePath);
            if (originalFile.exists()) {
                copyFile(originalFile, backupFile);
            }
        } catch (IOException e) {
            Assert.fail("Failed: to backup file: " + filePath + " - " + e.getMessage());
        }
    }

    private void restoreFile(String filePath, File backupFile) {
        try {
            File originalFile = new File(filePath);
            if (backupFile.exists()) {
                copyFile(backupFile, originalFile);
                backupFile.delete(); // Clean up the backup file
            } else {
                // Clear the file if no backup exists
                new PrintWriter(filePath).close();
            }
        } catch (IOException e) {
            Assert.fail("Failed: to restore file: " + filePath + " - " + e.getMessage());
        }
    }

    private void copyFile(File source, File destination) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
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
        User testUser = new User("oldUsername", "Password1!", "oldEmail@example.com");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersCsvPath))) {
            writer.write(testUser.toCsv());
            writer.newLine();
        } catch (IOException e) {
            Assert.fail("Failed: to set up test data: " + e.getMessage());
        }

        profileController.updateProfile(testUser, "newUsername", "newEmail@example.com", "NewPassword1!");

        try (BufferedReader reader = new BufferedReader(new FileReader(usersCsvPath))) {
            String updatedLine = reader.readLine();
            Assert.assertNotNull("Updated line should not be null", updatedLine);

            User updatedUser = User.fromCsv(updatedLine);
            Assert.assertEquals("Username should be updated", "newUsername", updatedUser.getUsername());
            Assert.assertEquals("Email should be updated", "newEmail@example.com", updatedUser.getEmail());
            Assert.assertEquals("Password should be updated", "NewPassword1!", updatedUser.getPassword());
        } catch (IOException e) {
            Assert.fail("Failed: to validate updated data: " + e.getMessage());
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
    @Test
    public void testSearchProfile_UserExists() {
        String username = "testUser";
        String email = "testUser@example.com";
        String password = "Password123!";

        authService.register(email, username, password);
        profileController.initializeProfile(username);
        String result = profileController.searchProfile(username);
        Assert.assertNotNull("Profile should be found", result);
        Assert.assertTrue("Profile should contain username", result.contains(username));
    }

    // Test: Search Profile when user does not exist
    @Test
    public void testSearchProfile_UserDoesNotExist() {
        String username = "nonExistentUser";
        String result = profileController.searchProfile(username);
        Assert.assertEquals("Profile not found for non-existent user", "Profile not found for " + username, result);
    }
}
