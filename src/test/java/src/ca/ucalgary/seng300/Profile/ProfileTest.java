package src.ca.ucalgary.seng300.Profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProfileTest {

    private Profile profile;
    private User testUser;
    private AuthService authService;
    private ProfileService profileService;

    private File usersBackupFile;
    private File profilesBackupFile;

    private String usersCsvPath;
    private String profilesCsvPath;

    @Before
    public void setUp() throws IOException {
        authService = new AuthService(); // Initialize AuthService
        profileService = new ProfileService(authService); // Initialize ProfileService

        // Paths to CSV files
        usersCsvPath = profileService.usersPath;
        profilesCsvPath = profileService.profilePath;
        // Backup users.csv
        usersBackupFile = new File(usersCsvPath + ".backup");
        backupFile(usersCsvPath, usersBackupFile);
        // Backup profiles.csv
        profilesBackupFile = new File(profilesCsvPath + ".backup");
        backupFile(profilesCsvPath, profilesBackupFile);

        // Create a real User object for testing
        testUser = new User("testUser", "password123", "testUser@example.com");
        // Initialize the Profile object with the test User
        profile = new Profile(testUser);
    }
    // Utility methods for backup and restore
    private void backupFile(String filePath, File backupFile) {
        try {
            File originalFile = new File(filePath);
            if (originalFile.exists()) {
                copyFile(originalFile, backupFile);
            }
        } catch (IOException e) {
            fail("Failed to backup file: " + filePath + " - " + e.getMessage());
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
            fail("Failed to restore file: " + filePath + " - " + e.getMessage());
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

    @After
    public void tearDown() {
        // Restore users.csv
        restoreFile(usersCsvPath, usersBackupFile);
        // Restore profiles.csv
        restoreFile(profilesCsvPath, profilesBackupFile);
    }

    @Test
    public void testConstructorInitializesFieldsCorrectly() {
        assertEquals(0, profile.getGamesPlayed());
        assertEquals(0, profile.getWins());
        assertEquals(0, profile.getLosses());
        assertEquals(0, profile.getRank());
    }

    @Test
    public void testGetGamesPlayed() {
        assertEquals(0, profile.getGamesPlayed());
    }

    @Test
    public void testGetWins() {
        assertEquals(0, profile.getWins());
    }

    @Test
    public void testGetLosses() {
        assertEquals(0, profile.getLosses());
    }

    @Test
    public void testGetRank() {
        assertEquals(0, profile.getRank());
    }

    @Test
    public void testGetProfileDetails() {
        String expectedDetails = "Username: testUser, Email: testUser@example.com, Games Played: 0, Wins: 0, Losses: 0, Rank: 0";
        assertEquals(expectedDetails, profile.getProfileDetails());
    }

    @Test
    public void testToString() {
        String username = "testuser";
        String password = "securepassword";
        String email = "testuser@example.com";
        User user = new User(username, password, email);

        String actualToString = user.toString();
        String expectedToString = "User(username=" + username + ", email=" + email + ")";
        assertEquals("The toString method should return the correct string representation.", expectedToString, actualToString);
    }
}
