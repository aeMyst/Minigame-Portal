package src.ca.ucalgary.seng300.Profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;

import java.io.*;
import static org.junit.Assert.*;

public class ProfileServiceTest {
    private ProfileService profileService;
    private AuthService authService;

    @Before
    public void setUp() throws Exception {
        authService = new AuthService();
        profileService = new ProfileService(authService);
    }


    //Test Case of ViewProfile with no user
    @Test
    public void testViewProfileWhenUserNotLoggedIn() {
        String result = profileService.viewProfile();
        assertEquals("No user is currently logged in.", result);
    }

    //TestCase of viewProfile when logged in
    @Test
    public void testViewProfileWhenUserLoggedIn() {
        User user = new User("testuser", "password", "test@example.com");
        authService.register(user.getEmail(), user.getUsername(), user.getPassword());
        authService.login(user.getUsername(), user.getPassword());

        String result = profileService.viewProfile();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    //TestCase of UpdateProfile
    @Test
    public void testUpdateProfileValidData() {
        String email = "update@example.com";
        String username = "updateuser";
        String password = "Password123!";

        // Register
        boolean registrationSuccess = authService.register(email, username, password);
        assertTrue("Registration should succeed", registrationSuccess);
        // Login
        boolean loginSuccess = authService.login(username, password);
        assertTrue("Login should succeed", loginSuccess);

        // Update profile
        profileService.updateProfile(authService.isLoggedIn(), "newusername", "newemail@example.com", "NewPassword123!");

        // Verify updated information
        User updatedUser = authService.isLoggedIn();
        assertNotNull("Updated user should not be null", updatedUser);
        assertEquals("newusername", updatedUser.getUsername());
        assertEquals("newemail@example.com", updatedUser.getEmail());
    }

    //Test Case of initializeProfile
    @Test
    public void testInitializeProfile() {
        String username = "inituser";

        // Initialize profile
        profileService.initializeProfile(username);

        // Search Profile
        String profileString = profileService.searchProfile(username);

        // Verify
        assertNotNull(profileString);
        assertFalse(profileString.isEmpty());
    }

    //TestCase of Search Profile if the user exists
    @Test
    public void testSearchProfileFound() {
        String username = "lefaa1";
        // Search Profile
        String result = profileService.searchProfile(username);

        // Verify
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    //TestCase of Search Profile if the user doesnt exist
    @Test
    public void testSearchProfileNotFound() {
        String result = profileService.searchProfile("nonexistentuser");

        assertEquals("Profile not found for nonexistentuser", result);
    }

    @After
    public void tearDown() throws Exception {
        //Remove the additional rows created through the testcases
        removeRowFromFile(profileService.usersPath, "newusername");
        removeRowFromFile(profileService.usersPath, "updateuser");
        removeRowFromFile(profileService.usersPath, "inituser");
        removeRowFromFile(profileService.profilePath, "inituser");
    }

    private void removeRowFromFile(String filePath, String username) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        //Read from current file and  write to the temporary file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            // Skip rows with the specified username and write other rows to the temp file(which becomes main file later on)
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.contains(username)) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        }
        //Essentially deleting the original file and renaming the temp file to the original file.
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            throw new IOException("Failed to clean up test data");
        }
    }

}
