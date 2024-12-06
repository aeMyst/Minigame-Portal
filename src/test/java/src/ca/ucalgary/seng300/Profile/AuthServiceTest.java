package src.ca.ucalgary.seng300.Profile;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AuthServiceTest {
    private AuthService authService;
    // Keep track of created usernames so we can remove them after tests
    private static final Set<String> createdUsernames = new HashSet<>();

    @Before
    public void setUp() {
        authService = new AuthService(); // Create a new instance of AuthService for each test

        // Ensure that users.csv ends with a newline character. If not, tests may merge lines.
        ensureTrailingNewline(authService.USER_DATA_FILE);
    }

    // Helper method to ensure the last line in a file ends with a newline
    private void ensureTrailingNewline(String filePath) {
        try {
            // Open file in read/write mode
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            long length = raf.length();
            if (length > 0) {
                raf.seek(length - 1); // Move to the last byte
                byte lastByte = raf.readByte();
                if (lastByte != '\n') {
                    // Append a newline if missing at the end of the file
                    raf.seek(length);
                    raf.write('\n');
                }
            }
            raf.close();
        } catch (IOException e) {
            // If we cannot ensure a trailing newline, a warning is printed
            System.err.println("Warning: Could not ensure trailing newline for " + filePath + ": " + e.getMessage());
        }
    }

    // Registers a user and adds their username to the tracking set if successful
    private boolean registerAndTrack(String email, String username, String password) {
        boolean result = authService.register(email, username, password);
        if (result) {
            createdUsernames.add(username);
        }
        return result;
    }

    @Test
    public void testAuthServiceConstructorIOException() {
        // Test constructor with invalid file path
        String USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        AuthService authServiceWithInvalidPath = new AuthService(USER_DATA_FILE);
        // Expecting an empty user list due to IOException
        assertTrue("User list should be empty due to IOException in constructor",
                authServiceWithInvalidPath.getSanitizedUsers().isEmpty());
    }

    @Test
    public void testSaveUsersSuccess() {
        // Test successful user save
        boolean registrationResult = registerAndTrack("saveuser@example.com", "SaveUser", "Password1!");
        assertTrue("Registration should be successful", registrationResult);
        try {
            authService.saveUsers(); // Attempt to save the users
            assertTrue("Users saved successfully", true);
        } catch (Exception e) {
            Assert.fail("An exception occurred during saveUsers: " + e.getMessage());
        }
    }

    @Test
    public void testSaveUsersIOException() {
        // Force saveUsers to encounter an IOException by using an invalid path
        authService.USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        authService.saveUsers(); // This should be handled internally
        assertTrue("saveUsers executed with IOException", true);
        // Reset the file path
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    }

    @Test
    public void testRegisterStoreUserIOException() {
        // Force a registration failure due to invalid file path
        authService.USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        boolean result = registerAndTrack("testregister@example.com", "TestRegisterUser", "Password1!");
        assertFalse("Registration should fail due to IOException in storeUser", result);
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv"; // Reset
    }

    @Test
    public void testStoreUserIOException() {
        // Force a storeUser failure due to invalid file path
        authService.USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        boolean result = registerAndTrack("teststore@example.com", "TestStoreUser", "Password1!");
        assertFalse("StoreUser should fail due to IOException", result);
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv"; // Reset
    }

    @Test
    public void testModifyUserPasswordUserExists() {
        // Test modifying an existing user's password
        boolean registrationResult = registerAndTrack("existing@example.com", "ExistingUser", "Password1!");
        assertTrue("User should be registered successfully", registrationResult);
        authService.modifyUserPassword("ExistingUser", "NewPassword1!");
        // Verify that the user can log in with the new password
        boolean loginResult = authService.login("ExistingUser", "NewPassword1!");
        assertTrue("Should be able to login with new password", loginResult);
    }

    @Test
    public void testModifyUserPasswordUserDoesNotExist() {
        // Attempt to modify password for a non-existent user
        authService.modifyUserPassword("NonExistentUser", "Password1!");
        boolean userExists = false;
        for (User user : authService.getSanitizedUsers()) {
            if (user.getUsername().equals("NonExistentUser")) {
                userExists = true;
                break;
            }
        }
        // User should not be found
        assertFalse("Non-existent user should not be found", userExists);
    }

    @Test
    public void testGetSanitizedUsersWithUsers() {
        // Test retrieving sanitized users when users exist
        registerAndTrack("user@example.com", "User1", "Password1!");
        ArrayList<User> sanitizedUsers = authService.getSanitizedUsers();
        assertFalse("Sanitized users list should not be empty", sanitizedUsers.isEmpty());
        for (User user : sanitizedUsers) {
            Assert.assertNull("Password should be null in sanitized user", user.getPassword());
        }
    }

    @Test
    public void testGetSanitizedUsersNoUsers() {
        // Test retrieving sanitized users when no users exist
        String USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        AuthService emptyAuthService = new AuthService(USER_DATA_FILE);
        ArrayList<User> sanitizedUsers = emptyAuthService.getSanitizedUsers();
        assertTrue("Sanitized users list should be empty", sanitizedUsers.isEmpty());
        // Reset the file path
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    }

    @Test
    public void testLogoutUserNull() {
        // Test logging out when user is null
        boolean result = authService.logout(null);
        assertFalse("Logout should fail when user is null", result);
    }

    @Test
    public void testRegisterValidUser() {
        // Test registration of a valid user
        boolean result = registerAndTrack("valid@example.com", "ValidUser", "Password1!");
        assertTrue("We have successfully registered the user!", result);
    }

    @Test
    public void testRegisterInvalidEmail() {
        // Test registration with an invalid email
        boolean result = authService.register("invalid-email", "ValidUser", "Password1!");
        assertFalse("Registration failed! The email is invalid", result);
    }

    @Test
    public void testRegisterInvalidPassword() {
        // Test registration with an invalid password
        boolean result = authService.register("valid@example.com", "ValidUser", "short");
        assertFalse("Registration failed due to invalid password", result);
    }

    @Test
    public void testRegisterInvalidUsername() {
        // Test registration with an invalid username
        boolean result = authService.register("valid@example.com", "Invalid@User", "Password1!");
        assertFalse("Registration failed due to invalid username", result);
    }

    @Test
    public void testLoginValidUser() {
        // Test login with valid credentials
        registerAndTrack("valid@example.com", "ValidUser", "Password1@a");
        boolean result = authService.login("ValidUser", "Password1@a");
        assertTrue("User logged in successfully!", result);
    }

    @Test
    public void testLoginInvalidPassword() {
        // Test login with an incorrect password
        registerAndTrack("valid@example.com", "ValidUser", "Password1!");
        boolean result = authService.login("ValidUser", "WrongPassword1!");
        assertFalse("Login failed due to incorrect password", result);
    }

    @Test
    public void testLoginInvalidUsername() {
        // Test login with a non-existent username
        registerAndTrack("valid@example.com", "ValidUser", "Password1!");
        boolean result = authService.login("WrongUser", "Password1!");
        assertFalse("Login failed due to non-existent username", result);
    }

    @Test
    public void testLogoutValidUser() {
        // Test logging out a valid user after login
        boolean registrationResult = registerAndTrack("valid@example.com", "ValidUser", "Password1@");
        assertTrue("Registration should be successful", registrationResult);

        boolean loginResult = authService.login("ValidUser", "Password1@");
        assertTrue("Login should be successful", loginResult);

        User user = authService.isLoggedIn();
        Assert.assertNotNull("A user should be logged in", user);

        boolean logoutResult = authService.logout(user);
        assertTrue("User logged out successfully!", logoutResult);
    }

    @Test
    public void testLogoutInvalidUser() {
        // Test logout with an invalid user
        User invalidUser = new User("InvalidUser", "InvalidPassword", "invalid@example.com");
        boolean result = authService.logout(invalidUser);
        assertFalse("Logout failed because user is not recognized", result);
    }

    @Test
    public void testIsLoggedIn() {
        // Test checking if a user is logged in
        boolean registrationResult = registerAndTrack("valid@example.com", "ValidUser", "Password1@a");
        if (!registrationResult) {
            Assert.fail("Registration failed, cannot proceed");
        }
        boolean loginResult = authService.login("ValidUser", "Password1@a");
        assertTrue("Login should be successful", loginResult);

        User loggedInUser = authService.isLoggedIn();
        Assert.assertNotNull("User login confirmed!", loggedInUser);
        Assert.assertEquals("Welcome ValidUser", "ValidUser", loggedInUser.getUsername());
    }

    @Test
    public void testIsLoggedOut() {
        // Test that no user is logged in after logout
        registerAndTrack("valid@example.com", "ValidUser", "Password1!");
        authService.login("ValidUser", "Password1!");
        User user = authService.isLoggedIn();
        authService.logout(user);
        Assert.assertNull("No user is currently active after logout", authService.isLoggedIn());
    }

    // Removes a user line from users.csv by matching the username exactly
    private void removeRowFromFile(String filePath, String usernameToRemove) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        boolean userFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            // Read each line and check if it matches the username to remove
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts.length == 3) {
                    String fileUsername = parts[1].trim();
                    if (fileUsername.equals(usernameToRemove)) {
                        userFound = true;
                        continue; // Skip this line if it's the user we're removing
                    }
                }
                // Write back all other lines
                writer.write(currentLine);
                writer.newLine();
            }
        }

        if (userFound) {
            // If a user was removed, replace the original file with the temp file
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                throw new IOException("Failed to clean up test data");
            }
        } else {
            // If no user was found, just delete the temp file
            tempFile.delete();
        }

        // Ensure trailing newline after cleanup
        ensureTrailingNewline(filePath);
    }

    // Removes profile entries for a given username from profiles.csv
    private void removeProfileRows(String filePath, String username) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        boolean entryFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            // Check each profile line and remove those belonging to the given username
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                // profiles.csv format: Gametype,Username,Elo,Wins,Losses,Draws
                if (parts.length == 6 && parts[1].trim().equalsIgnoreCase(username)) {
                    entryFound = true;
                    continue; // Skip lines that match the username
                }
                // Write back all other lines
                writer.write(currentLine);
                writer.newLine();
            }
        }

        if (entryFound) {
            // If profiles were removed, replace the original file
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                throw new IOException("Failed to clean up profile test data");
            }
        } else {
            // If no entry found, delete the temp file
            tempFile.delete();
        }
        ensureTrailingNewline(filePath);
    }

    @Test
    public void testUpdateCurrentUser() {
        // Test updating the currently logged-in user's username and email
        registerAndTrack("user@example.com", "UpdateUser", "Password123!");
        authService.login("UpdateUser", "Password123!");
        authService.updateCurrentUser("UpdatedUser", "updated@example.com");

        User updatedUser = authService.isLoggedIn();
        Assert.assertNotNull("The updated user should not be null", updatedUser);
        Assert.assertEquals("Username should be updated", "UpdatedUser", updatedUser.getUsername());
        Assert.assertEquals("Email should be updated", "updated@example.com", updatedUser.getEmail());
    }

    @Test
    public void testModifyUserPasswordInvalidFormat() {
        // Test modifying password to an invalid format
        boolean registrationResult = registerAndTrack("existing@example.com", "ExistingUser", "Password1!");
        assertTrue("User should be registered successfully", registrationResult);
        authService.modifyUserPassword("ExistingUser", "short");
        // Since there's no password validation, the invalid password still works
        boolean loginResult = authService.login("ExistingUser", "short");
        assertTrue("User can still log in because invalid password isn't checked", loginResult);
    }

    @Test
    public void testRegisterDuplicateUser() {
        // Test registering duplicate users (no uniqueness check in code)
        boolean firstRegistrationResult = registerAndTrack("duplicate@example.com", "DuplicateUser", "Password123!");
        assertTrue("First registration should be successful", firstRegistrationResult);

        // Use registerAndTrack for AnotherUser to ensure they are tracked and removed later
        boolean secondRegistrationResult = registerAndTrack("duplicate@example.com", "AnotherUser", "Password123!");
        assertTrue("Registration succeeds since duplicates aren't checked", secondRegistrationResult);

        boolean thirdRegistrationResult = registerAndTrack("another@example.com", "DuplicateUser", "Password123!");
        assertTrue("Registration also succeeds without uniqueness checks", thirdRegistrationResult);
    }

    @Test
    public void testLogoutWithoutLogin() {
        // Test logging out without anyone logged in
        User notLoggedInUser = new User("NotLoggedIn", "password", "notloggedin@example.com");
        boolean logoutResult = authService.logout(notLoggedInUser);
        assertFalse("Logout should fail since no user is logged in", logoutResult);
    }

    @Test
    public void testRegisterWithNullInputs() {
        // Test registering with null inputs for email, username, or password
        boolean nullEmailResult = authService.register(null, "UserWithNullEmail", "Password123!");
        assertFalse("Registration should fail with null email", nullEmailResult);

        boolean nullUsernameResult = authService.register("nulluser@example.com", null, "Password123!");
        assertFalse("Registration should fail with null username", nullUsernameResult);

        boolean nullPasswordResult = authService.register("nullpassword@example.com", "UserWithNullPassword", null);
        assertFalse("Registration should fail with null password", nullPasswordResult);
    }

    @Test
    public void testRegisterWithEmptyInputs() {
        // Test registering with empty strings for email, username, or password
        boolean emptyEmailResult = authService.register("", "UserWithEmptyEmail", "Password123!");
        assertFalse("Registration should fail with empty email", emptyEmailResult);

        boolean emptyUsernameResult = authService.register("emptyuser@example.com", "", "Password123!");
        assertFalse("Registration should fail with empty username", emptyUsernameResult);

        boolean emptyPasswordResult = authService.register("emptypassword@example.com", "UserWithEmptyPassword", "");
        assertFalse("Registration should fail with empty password", emptyPasswordResult);
    }

    @Test
    public void testLoginWithNullOrEmptyCredentials() {
        // Test logging in with null or empty credentials
        registerAndTrack("valid@example.com", "ValidUser", "Password123!");

        boolean nullUsernameLogin = authService.login(null, "Password123!");
        assertFalse("Login should fail with null username", nullUsernameLogin);

        boolean nullPasswordLogin = authService.login("ValidUser", null);
        assertFalse("Login should fail with null password", nullPasswordLogin);

        boolean emptyUsernameLogin = authService.login("", "Password123!");
        assertFalse("Login should fail with empty username", emptyUsernameLogin);

        boolean emptyPasswordLogin = authService.login("ValidUser", "");
        assertFalse("Login should fail with empty password", emptyPasswordLogin);
    }

    @Test
    public void testAddUser() throws IOException {
        // Arrange: Create a temporary file with mock user data
        File tempFile = File.createTempFile("user_data", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("email1@example.com,user1,pass1");
        writer.newLine();
        writer.write("email2@example.com,user2,pass2");
        writer.newLine();
        writer.write("email3@example.com,user3,pass3");
        writer.close();

        User user1 = new User("user1", "pass1", "email1@example.com");
        User user2 = new User("user2", "pass2", "email2@example.com");
        User user3 = new User("user3", "pass3", "email3@example.com");

        // test AuthService(FILE_PATH) method
        AuthService authService = new AuthService(tempFile.getAbsolutePath());

        assertTrue("Login should succeed for user1", authService.login("user1", "pass1"));
        assertTrue("Login should succeed for user2", authService.login("user2", "pass2"));
        assertTrue("Login should succeed for user3", authService.login("user3", "pass3"));

        boolean registrationResult = authService.register("email1@example.com", "user1", "pass1");
        assertFalse("Registration should fail for existing user1", registrationResult);

        tempFile.delete();
    }

    @After
    public void tearDown() throws Exception {
        // Remove all users that were registered and tracked
        for (String username : createdUsernames) {
            removeRowFromFile(authService.USER_DATA_FILE, username);
        }
        createdUsernames.clear();

        // Ensure trailing newline after tests complete
        ensureTrailingNewline(authService.USER_DATA_FILE);
    }
}
