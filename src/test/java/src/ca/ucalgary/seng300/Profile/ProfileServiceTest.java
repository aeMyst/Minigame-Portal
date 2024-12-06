package src.ca.ucalgary.seng300.Profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

public class ProfileServiceTest {
    private ProfileService profileService;
    private AuthService authService;
    private String originalProfilePath;
    private String originalUsersPath;

    @Before
    public void setUp() throws Exception {
        authService = new AuthService();
        profileService = new ProfileService(authService);
        // Store original paths
        originalProfilePath = profileService.profilePath;
        originalUsersPath = profileService.usersPath;
    }

    // Test case when no user is logged in
    @Test
    public void testViewProfileWhenUserNotLoggedIn() {
        String result = profileService.viewProfile();
        assertEquals("No user is currently logged in.", result);
    }

    // Test case when a user is logged in and profile needs to be initialized
    @Test
    public void testViewProfileWhenUserLoggedIn() {
        User user = new User("testuser", "password", "test@example.com");
        authService.register(user.getEmail(), user.getUsername(), user.getPassword());
        authService.login(user.getUsername(), user.getPassword());
        String result = profileService.viewProfile();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // Test viewProfile when displayProfile is empty and profile needs to be initialized
    @Test
    public void testViewProfileWhenProfileDoesNotExist() {
        // Ensure user is logged in
        String email = "nonexistentprofile@example.com";
        String username = "noProfileUser";
        String password = "Password123!";

        // Register and login
        authService.register(email, username, password);
        authService.login(username, password);

        // Ensure profiles.csv does not contain this user's profile
        // Remove any existing profile rows for this user
        try {
            removeRowFromFile(profileService.profilePath, username);
        } catch (IOException e) {
            fail("Error removing row from profiles.csv: " + e.getMessage());
        }

        // Capture output to verify initialization message
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call viewProfile
        String result = profileService.viewProfile();

        // Restore System.out
        System.setOut(originalOut);

        // Verify that the profile was initialized
        String expectedOutput = "Default profiles created for user: " + username;
        assertTrue(outContent.toString().trim().contains(expectedOutput));

        // Verify result is not empty
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }


    // Test updating profile with valid data
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

    // Test updating profile when all fields are blank (should fail validation)
    @Test
    public void testUpdateProfileWithAllBlankFields() {
        // Setup
        String email = "blank@example.com";
        String username = "blankuser";
        String password = "Password123!";
        // Register and login
        authService.register(email, username, password);
        authService.login(username, password);
        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        // Call updateProfile with all blank fields
        profileService.updateProfile(authService.isLoggedIn(), "", "", "");
        // Restore System.out
        System.setOut(originalOut);
        // Verify output
        String expectedOutput = "At least one field must be changed.";
        assertEquals(expectedOutput, outContent.toString().trim());
        // Verify that user information remains unchanged
        User userAfterUpdate = authService.isLoggedIn();
        assertEquals(username, userAfterUpdate.getUsername());
        assertEquals(email, userAfterUpdate.getEmail());
    }


    // Test updating profile with invalid username (should fail validation)
    @Test
    public void testUpdateProfileWithInvalidUsername() {
        // Setup
        String email = "invalidusername@example.com";
        String username = "validuser";
        String password = "Password123!";
        // Register and login
        authService.register(email, username, password);
        authService.login(username, password);
        String invalidUsername = "inv@lidUser!"; // Assuming invalid due to special characters
        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        // Call updateProfile with invalid username
        profileService.updateProfile(authService.isLoggedIn(), invalidUsername, "", "");
        // Restore System.out
        System.setOut(originalOut);
        // Verify output
        String expectedOutput = "Invalid username format.";
        assertEquals(expectedOutput, outContent.toString().trim());
        // Verify that user information remains unchanged
        User userAfterUpdate = authService.isLoggedIn();
        assertEquals(username, userAfterUpdate.getUsername());
        assertEquals(email, userAfterUpdate.getEmail());
    }


    // Test updating profile with invalid email (should fail validation)
    @Test
    public void testUpdateProfileWithInvalidEmail() {
        // Setup
        String email = "invalidemailuser@example.com";
        String username = "validemailuser";
        String password = "Password123!";

        // Register and login
        authService.register(email, username, password);
        authService.login(username, password);

        String invalidEmail = "invalidemail"; // invalid email format

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call updateProfile with invalid email
        profileService.updateProfile(authService.isLoggedIn(), "", invalidEmail, "");

        // Restore System.out
        System.setOut(originalOut);

        // Verify output
        String expectedOutput = "Invalid email format.";
        assertEquals(expectedOutput, outContent.toString().trim());

        // Verify that user information remains unchanged
        User userAfterUpdate = authService.isLoggedIn();
        assertEquals(username, userAfterUpdate.getUsername());
        assertEquals(email, userAfterUpdate.getEmail());
    }


    // Test updating profile with invalid password (should fail validation)
    @Test
    public void testUpdateProfileWithInvalidPassword() {
        // Setup
        String email = "invalidpassworduser@example.com";
        String username = "validpassworduser";
        String password = "Password123!";

        // Register and login
        authService.register(email, username, password);
        authService.login(username, password);

        String invalidPassword = "short"; // invalid password format

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call updateProfile with invalid password
        profileService.updateProfile(authService.isLoggedIn(), "", "", invalidPassword);

        // Restore System.out
        System.setOut(originalOut);

        // Verify output
        String expectedOutput = "Invalid password format.";
        assertEquals(expectedOutput, outContent.toString().trim());

        // Verify that user information remains unchanged
        User userAfterUpdate = authService.isLoggedIn();
        assertEquals(username, userAfterUpdate.getUsername());
        assertEquals(email, userAfterUpdate.getEmail());
    }


    // Test updating profile when user is not found in users list
    @Test
    public void testUpdateProfileUserNotFound() throws IOException {
        // Setup
        String email = "usernotfound@example.com";
        String username = "usernotfound";
        String password = "Password123!";
        // Register and login
        authService.register(email, username, password);
        authService.login(username, password);
        // Remove user from users.csv
        removeRowFromFile(profileService.usersPath, username);
        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        // Call updateProfile
        profileService.updateProfile(authService.isLoggedIn(), "newusername", "", "");
        // Restore System.out
        System.setOut(originalOut);
        // Verify output
        String expectedOutput = "User not found.";
        assertEquals(expectedOutput, outContent.toString().trim());
    }


    // Test that updating profile username updates the username in profiles as well
    @Test
    public void testUpdateProfileUpdatesProfileUsername() {
        // Setup
        String email = "updateprofileusername@example.com";
        String oldUsername = "oldusername";
        String newUsername = "newusername";
        String password = "Password123!";
        // Register and login
        authService.register(email, oldUsername, password);
        authService.login(oldUsername, password);
        // Initialize profile for user
        profileService.initializeProfile(oldUsername);
        // Update username
        profileService.updateProfile(authService.isLoggedIn(), newUsername, "", "");
        // Verify that the profile's username is updated
        String profileString = profileService.searchProfile(newUsername);
        assertNotEquals("Profile not found for " + newUsername, profileString);
        // Verify that old username profile is not found
        String oldProfileString = profileService.searchProfile(oldUsername);
        assertEquals("Profile not found for " + oldUsername, oldProfileString);
    }

    // Test initializing profile
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

    // Test searching for an existing profile
    @Test
    public void testSearchProfileFound() {
        String username = "lefaa1";
        // Search Profile
        String result = profileService.searchProfile(username);
        // Verify
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // Test searching for a non-existent profile
    @Test
    public void testSearchProfileNotFound() {
        String result = profileService.searchProfile("nonexistentuser");
        assertEquals("Profile not found for nonexistentuser", result);
    }

    // Test parseInt method with invalid data (should handle NumberFormatException)
    @Test
    public void testParseIntWithInvalidData() throws Exception {
        Method method = ProfileService.class.getDeclaredMethod("parseInt", String.class);
        method.setAccessible(true);
        // Test with valid integer string
        assertEquals(123, method.invoke(profileService, "123"));
        // Test with invalid integer string
        assertEquals(0, method.invoke(profileService, "abc"));
        // Test with empty string
        assertEquals(0, method.invoke(profileService, ""));
        // Test with whitespace
        assertEquals(0, method.invoke(profileService, " "));
        // Test with null string - expect NullPointerException
        try {
            method.invoke(profileService, (String) null);
            fail("Expected NullPointerException");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof NullPointerException);
        }
    }

    // Test buildProfileString when profiles.csv contains invalid numeric data
    @Test
    public void testBuildProfileStringWithInvalidNumericData() throws IOException {
        // Prepare data with invalid numbers
        String invalidProfileLine = "CHECKERS,testuser,abc,def,ghi,jkl";
        appendToFile(profileService.profilePath, invalidProfileLine);
        // Call searchProfile
        String profileString = profileService.searchProfile("testuser");
        // Verify that parseInt handled invalid data by returning 0
        String expectedProfile = "Gametype: CHECKERS\n" +
                "PlayerID: testuser\n" +
                "Elo: 0\n" +
                "Wins: 0\n" +
                "Losses: 0\n" +
                "Draws: 0\n" +
                "Games Played: 0";
        assertTrue(profileString.contains(expectedProfile));
        // Clean up
        removeRowFromFile(profileService.profilePath, "testuser");
    }

    // Helper method to append a line to a file
    private void appendToFile(String filePath, String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    // Test isBlank method with various inputs
    @Test
    public void testIsBlank() throws Exception {
        Method method = ProfileService.class.getDeclaredMethod("isBlank", String.class);
        method.setAccessible(true);
        assertTrue((Boolean) method.invoke(profileService, (String) null));
        assertTrue((Boolean) method.invoke(profileService, ""));
        assertTrue((Boolean) method.invoke(profileService, " "));
        assertFalse((Boolean) method.invoke(profileService, "abc"));
    }

    // Test exception handling in readCsv during searchProfile
    @Test
    public void testReadCsvExceptionHandlingInSearchProfile() {
        // Set profilePath to an invalid path
        String invalidPath = "/invalid/path/profiles.csv";
        profileService.profilePath = invalidPath;

        // Capture error output
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        // Call searchProfile
        String result = profileService.searchProfile("anyuser");

        // Restore System.err
        System.setErr(originalErr);

        // Verify that appropriate error message was printed
        assertTrue(errContent.toString().contains("Error reading from file"));

        // Verify that result is "Profile not found for anyuser"
        assertEquals("Profile not found for anyuser", result);
    }

    // Test exception handling in appendToCsv during initializeProfile
    @Test
    public void testAppendToCsvExceptionHandlingInInitializeProfile() {
        // Set profilePath to an invalid path (e.g., a directory)
        File tempDir = new File("tempDir");
        tempDir.mkdirs(); // Create a directory
        profileService.profilePath = tempDir.getAbsolutePath(); // Use directory path where a file is expected

        // Capture error output
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        // Call initializeProfile
        profileService.initializeProfile("testuser");

        // Restore System.err
        System.setErr(originalErr);

        // Verify that appropriate error message was printed
        assertTrue(errContent.toString().contains("Error appending to file"));

        // Clean up
        tempDir.delete();
    }

    @After
    public void tearDown() throws Exception {
        // Restore original paths
        profileService.profilePath = originalProfilePath;
        profileService.usersPath = originalUsersPath;

        // Remove the additional rows created through the test cases
        removeRowFromFile(profileService.usersPath, "newusername");
        removeRowFromFile(profileService.usersPath, "updateuser");
        removeRowFromFile(profileService.usersPath, "updateprofileusername@example.com");
        removeRowFromFile(profileService.usersPath, "blankuser");
        removeRowFromFile(profileService.usersPath, "validuser");
        removeRowFromFile(profileService.usersPath, "usernotfound");
        removeRowFromFile(profileService.usersPath, "inituser");
        removeRowFromFile(profileService.usersPath, "oldusername");
        removeRowFromFile(profileService.usersPath, "testuser");
        removeRowFromFile(profileService.usersPath, "validemailuser");
        removeRowFromFile(profileService.usersPath, "validpassworduser");
        removeRowFromFile(profileService.usersPath, "writecsvuser");
        removeRowFromFile(profileService.usersPath, "noProfileUser");

        removeRowFromFile(profileService.profilePath, "testuser");
        removeRowFromFile(profileService.profilePath, "inituser");
        removeRowFromFile(profileService.profilePath, "newusername");
        removeRowFromFile(profileService.profilePath, "noProfileUser");
        removeRowFromFile(profileService.profilePath, "writecsvuser");

        // Clean up any directories we may have created
        File tempDir = new File("tempDir");
        if (tempDir.exists()) {
            tempDir.delete();
        }
        File tempDirUsers = new File("tempDirUsers");
        if (tempDirUsers.exists()) {
            tempDirUsers.delete();
        }
    }

    private void removeRowFromFile(String filePath, String username) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        // Read from current file and write to the temporary file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            // Skip rows with the specified username and write other rows to the temp file
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.contains(username)) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        }
        // Replace original file with the temp file
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            throw new IOException("Failed to clean up test data");
        }
    }

    @Test
    public void testUpdateProfileNoChange() throws IOException {
        User user = new User("user1", "password1", "user1@example.com");
        profileService.initializeProfile(user.getUsername());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        profileService.updateProfile(user, "", "", "");

        System.setOut(originalOut);
        assertTrue(outContent.toString().contains("At least one field must be changed."));

        // Cleanup profile and user data
        removeRowFromFile(profileService.profilePath, user.getUsername());
        removeRowFromFile(profileService.usersPath, user.getUsername());
    }

    @Test
    public void testBuildProfileUserFound() throws IOException {
        // Setup
        String email = "user1@example.com";
        String username = "user1";
        String password = "Password123!";

        // Register and login
        authService.register(email, username, password);
        authService.login(username, password);

        // Initialize the profile for the user
        profileService.initializeProfile(username);

        // Call searchProfile
        String profile = profileService.searchProfile(username);

        // Verify that the profile contains the expected details
        assertTrue(profile.contains("Gametype: CHECKERS"));
        assertTrue(profile.contains("PlayerID: user1"));

        // Cleanup profile and user data
        removeRowFromFile(profileService.profilePath, username);
        removeRowFromFile(profileService.usersPath, username);
    }



    @Test
    public void testBuildProfileUserNotFound() {
        String profile = profileService.searchProfile("nonexistentUser");

        assertEquals("Profile not found for nonexistentUser", profile);
    }

    @Test
    public void testReadCsvInvalidDetailsLength() throws IOException {
        // Prepare an invalid row with missing columns and append it to profiles.csv
        String invalidRow = "CHECKERS,user1,1200,10"; // Missing columns
        appendToFile(profileService.profilePath, invalidRow);

        // Capture error output
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        // Call searchProfile to see if it handles the invalid row correctly
        String profile = profileService.searchProfile("user1");

        // Restore System.err
        System.setErr(originalErr);

        // Extract captured error output
        String capturedError = errContent.toString().trim();

        // Verify that an error message was printed for the invalid row
        assertTrue("Expected error message not logged for invalid row", capturedError.contains("Error reading from file"));

        // Verify that the profile was not found due to invalid data
        assertEquals("Profile not found for user1", profile);
    }

    @Test
    public void testUpdateProfileBlankFields() {
        String email = "someblank@example.com";
        String username = "someblankuser";
        String password = "Password123!";
        authService.register(email, username, password);
        authService.login(username, password);

        profileService.updateProfile(authService.isLoggedIn(), "", "newemail@example.com", "");

        User updatedUser = authService.isLoggedIn();

        assertEquals("Expected email to be updated", "newemail@example.com", updatedUser.getEmail());
        assertEquals("Expected username to remain unchanged", username, updatedUser.getUsername());
        assertEquals("Expected password to remain unchanged", password, updatedUser.getPassword());
    }

}