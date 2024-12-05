package src.ca.ucalgary.seng300.Profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.controllers.ProfileController;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;

import java.io.*;
import org.junit.Assert;

public class ProfileControllerTest {

    private ProfileController profileController;
    private ProfileService profileService;
    private AuthService authService;
    private String csvPath;
    private File backupFile;

    //This is to set up the appropriate environment for the tests to be use and be run
    @Before
    public void setUp() {

        authService = new AuthService();
        profileService = new ProfileService(authService);
        profileController = new ProfileController(profileService);

        //Path to the CSV file
        csvPath = profileService.usersPath;
        // Backup the CSV file before testing
        backupFile = new File(csvPath + ".backup");
        try {
            File originalFile = new File(csvPath);
            if (originalFile.exists()) {
                copyFile(originalFile, backupFile);
            }
        } catch (IOException e) {
            Assert.fail("Failed: Creation of backup of CSV file: " + e.getMessage());
        }
    }

    // This is to restore the csv file based on the back up file.
    @After
    public void tearDown() {
        // Restore the original CSV file from the backup or clear it if backup doesn't exist
        try {
            File originalFile = new File(csvPath);
            if (backupFile.exists()) {
                copyFile(backupFile, originalFile);
                backupFile.delete(); // Clean up the backup file
            } else {
                // Clear the CSV file
                new PrintWriter(csvPath).close();
            }
        } catch (IOException e) {
            Assert.fail("Failed: Tests clean up" + e.getMessage());
        }
    }

    //Responsible for copying the contents of a file
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

    // Test: View Profile when there is no user logged in
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write(testUser.toCsv());
            writer.newLine();
        } catch (IOException e) {
            Assert.fail("Failed: Set up test data" + e.getMessage());
        }

        // Update the user's profile using the controller
        profileController.updateProfile(testUser, "newUsername", "newEmail@example.com", "NewPassword1!");

        // Read back the CSV and confirm the update
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String updatedLine = reader.readLine();
            Assert.assertNotNull("Updated line should not be null", updatedLine);

            User updatedUser = User.fromCsv(updatedLine);
            Assert.assertEquals("Username should be updated", "newUsername", updatedUser.getUsername());
            Assert.assertEquals("Email should be updated", "newEmail@example.com", updatedUser.getEmail());
            Assert.assertEquals("Password should be updated", "NewPassword1!", updatedUser.getPassword());
        } catch (IOException e) {
            Assert.fail("Failed: Update data validation " + e.getMessage());
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
        authService.register(email, username, password); // Register the user
        profileController.initializeProfile(username); // Ensure profile exists
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
