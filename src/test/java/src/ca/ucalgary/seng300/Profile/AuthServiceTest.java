package src.ca.ucalgary.seng300.Profile;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;

import java.io.*;
import java.util.ArrayList;

public class AuthServiceTest {
    private AuthService authService;

    // Set up method to initialize AuthService before each test
    @Before
    public void setUp() {
        authService = new AuthService(); // Create a new instance of AuthService to ensure each test runs independently
    }

    // Test for handling IOException in AuthService constructor (AuthService - catchError)
    @Test
    public void testAuthServiceConstructorIOException() {
        // Set USER_DATA_FILE to an invalid path to cause IOException
        String USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        // Create a new AuthService instance with the invalid path
        AuthService authServiceWithInvalidPath = new AuthService(USER_DATA_FILE);
        // Assert that the users list is empty due to the exception
        Assert.assertTrue("User list should be empty due to IOException in constructor",
                authServiceWithInvalidPath.getSanitizedUsers().isEmpty());
    }

    // Test for successful saveUsers method (saveUsers - success path)
    @Test
    public void testSaveUsersSuccess() {
        boolean registrationResult = authService.register("saveuser@example.com", "SaveUser", "Password1!");
        Assert.assertTrue("Registration should be successful", registrationResult);
        try {
            authService.saveUsers();
            Assert.assertTrue("Users saved successfully", true);
        } catch (Exception e) {
            Assert.fail("An exception occurred during saveUsers: " + e.getMessage());
        }
    }

    // Test for handling IOException in saveUsers method (saveUsers - exception path)
    @Test
    public void testSaveUsersIOException() {
        // Set USER_DATA_FILE to an invalid path to cause IOException
        authService.USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        // Attempt to save users
        authService.saveUsers();
        // Since the exception is caught within saveUsers, the test will pass if no exception is thrown
        Assert.assertTrue("saveUsers executed with IOException", true);
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv"; // Reset USER_DATA_FILE
    }

    // Test for the else branch in register method when storeUser fails (register - else)
    @Test
    public void testRegisterStoreUserIOException() {
        // Set USER_DATA_FILE to an invalid path to cause IOException in storeUser
        authService.USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        // Attempt to register a user
        boolean result = authService.register("testregister@example.com", "TestRegisterUser", "Password1!");
        // The registration should fail because storeUser returns false
        Assert.assertFalse("Registration should fail due to IOException in storeUser", result);
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv"; // Reset USER_DATA_FILE
    }

    // Test for handling IOException in storeUser method (storeUser - catch)
    @Test
    public void testStoreUserIOException() {
        // Set USER_DATA_FILE to an invalid path to cause IOException in storeUser
        authService.USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        // Attempt to store a user directly (private method, so indirectly via register)
        boolean result = authService.register("teststore@example.com", "TestStoreUser", "Password1!");
        // The registration should fail because storeUser returns false due to IOException
        Assert.assertFalse("StoreUser should fail due to IOException", result);
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv"; // Reset USER_DATA_FILE
    }

    // Test for modifying user password when user exists (modifyUserPassword - user found)
    @Test
    public void testModifyUserPasswordUserExists() {
        // Register a user
        boolean registrationResult = authService.register("existing@example.com", "ExistingUser", "Password1!");
        Assert.assertTrue("User should be registered successfully", registrationResult);
        // Modify password
        authService.modifyUserPassword("ExistingUser", "NewPassword1!");
        // Log in with new password to verify it was changed
        boolean loginResult = authService.login("ExistingUser", "NewPassword1!");
        Assert.assertTrue("Should be able to login with new password", loginResult);
    }

    // Test for modifying user password when user does not exist (modifyUserPassword - user not found)
    @Test
    public void testModifyUserPasswordUserDoesNotExist() {
        // Attempt to modify password for a non-existent user
        authService.modifyUserPassword("NonExistentUser", "Password1!");
        // Verify that no changes have been made to users
        boolean userExists = false;
        for (User user : authService.getSanitizedUsers()) {
            if (user.getUsername().equals("NonExistentUser")) {
                userExists = true;
                break;
            }
        }
        Assert.assertFalse("Non-existent user should not be found", userExists);
    }

    // Test for getSanitizedUsers when users list is populated (getSanitizedUsers - users exist)
    @Test
    public void testGetSanitizedUsersWithUsers() {
        // Register a user
        authService.register("user@example.com", "User1", "Password1!");
        // Get sanitized users
        ArrayList<User> sanitizedUsers = authService.getSanitizedUsers();
        // Check that list is not empty
        Assert.assertFalse("Sanitized users list should not be empty", sanitizedUsers.isEmpty());
        // Check that passwords are null in sanitized users
        for (User user : sanitizedUsers) {
            Assert.assertNull("Password should be null in sanitized user", user.getPassword());
        }
    }

    // Test for getSanitizedUsers when users list is empty (getSanitizedUsers - no users)
    @Test
    public void testGetSanitizedUsersNoUsers() {
        // Set USER_DATA_FILE to invalid path to prevent loading users
        String USER_DATA_FILE = "/invalid/path/nonexistentfile.csv";
        // Create new AuthService instance which will have an empty users list
        AuthService emptyAuthService = new AuthService(USER_DATA_FILE);
        // Get sanitized users
        ArrayList<User> sanitizedUsers = emptyAuthService.getSanitizedUsers();
        // Check that the list is empty
        Assert.assertTrue("Sanitized users list should be empty", sanitizedUsers.isEmpty());
        // Reset USER_DATA_FILE
        authService.USER_DATA_FILE = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    }

    // Test for logging out when user is null (logout - user == null)
    @Test
    public void testLogoutUserNull() {
        // Attempt to logout with null user
        boolean result = authService.logout(null);
        Assert.assertFalse("Logout should fail when user is null", result);
    }

    // Existing tests...

    // Test for registering a valid user
    @Test
    public void testRegisterValidUser() {
        boolean result = authService.register("valid@example.com", "ValidUser", "Password1!"); // Attempt to register a user with valid details
        Assert.assertTrue("We have successfully registered the user! The user is now in our database", result); // Assert that the registration was successful
    }

    // Test for registering a user with an invalid email
    @Test
    public void testRegisterInvalidEmail() {
        boolean result = authService.register("invalid-email", "ValidUser", "Password1!"); // Attempt to register a user with an invalid email
        Assert.assertFalse("Registration failed! The email provided is not in the correct format", result); // Assert that the registration failed due to an invalid email
    }

    // Test for registering a user with an invalid password
    @Test
    public void testRegisterInvalidPassword() {
        boolean result = authService.register("valid@example.com", "ValidUser", "short"); // Attempt to register a user with an invalid password
        Assert.assertFalse("Registration failed! The password does not meet the required criteria", result); // Assert that the registration failed due to an invalid password
    }

    // Test for registering a user with an invalid username
    @Test
    public void testRegisterInvalidUsername() {
        boolean result = authService.register("valid@example.com", "Invalid@User", "Password1!"); // Attempt to register a user with an invalid username
        Assert.assertFalse("Registration failed! The username contains invalid characters", result); // Assert that the registration failed due to an invalid username
    }

    // Test for logging in a valid user
    @Test
    public void testLoginValidUser() {
        authService.register("valid@example.com", "ValidUser", "Password1@a"); // Register a valid user
        boolean result = authService.login("ValidUser", "Password1@a"); // Attempt to log in with correct credentials
        Assert.assertTrue("User logged in successfully! Welcome back to the platform", result); // Assert that the login was successful
    }

    // Test for logging in with an invalid password
    @Test
    public void testLoginInvalidPassword() {
        authService.register("valid@example.com", "ValidUser", "Password1!"); // Register a valid user
        boolean result = authService.login("ValidUser", "WrongPassword1!"); // Attempt to log in with an incorrect password
        Assert.assertFalse("Login failed! The password provided is incorrect", result); // Assert that the login failed due to an incorrect password
    }

    // Test for logging in with an invalid username
    @Test
    public void testLoginInvalidUsername() {
        authService.register("valid@example.com", "ValidUser", "Password1!"); // Register a valid user
        boolean result = authService.login("WrongUser", "Password1!"); // Attempt to log in with an incorrect username
        Assert.assertFalse("Login failed! The username provided does not exist", result); // Assert that the login failed due to an incorrect username
    }

    // Test for logging out a valid user
    @Test
    public void testLogoutValidUser() {
        // Register a valid user with correct credentials
        boolean registrationResult = authService.register("valid@example.com", "ValidUser", "Password1@");
        Assert.assertTrue("Registration should be successful", registrationResult); // Ensure registration was successful

        // Log in the registered user
        boolean loginResult = authService.login("ValidUser", "Password1@");
        Assert.assertTrue("Login should be successful", loginResult); // Ensure login was successful

        // Get the logged-in user and log out
        User user = authService.isLoggedIn();
        Assert.assertNotNull("A user should be logged in before logging out", user); // Ensure the user is logged in
        boolean logoutResult = authService.logout(user);
        Assert.assertTrue("User logged out successfully! Goodbye for now", logoutResult); // Assert that the user was successfully logged out
    }



    // Test for logging out an invalid user
    @Test
    public void testLogoutInvalidUser() {
        User invalidUser = new User("InvalidUser", "InvalidPassword", "invalid@example.com"); // Create an invalid user instance
        boolean result = authService.logout(invalidUser); // Attempt to log out the invalid user
        Assert.assertFalse("Logout failed! The user is not recognized by the system", result); // Assert that the logout failed because the user is not valid
    }

    // Test to check if a user is logged in
    @Test
    public void testIsLoggedIn() {
        boolean registrationResult = authService.register("valid@example.com", "ValidUser", "Password1@a"); // Register a valid user
        if (!registrationResult) {
            Assert.fail("Registration failed, cannot proceed with login test");
        }
        boolean loginResult = authService.login("ValidUser", "Password1@a"); // Log in the user
        Assert.assertTrue("Login should be successful", loginResult); // Ensure login was successful
        User loggedInUser = authService.isLoggedIn(); // Get the logged-in user

        Assert.assertNotNull("User login confirmed! The user is now active in the system", loggedInUser); // Assert that a user is logged in
        Assert.assertEquals("The logged-in user matches the expected username! Welcome ValidUser", "ValidUser", loggedInUser.getUsername()); // Assert that the logged-in user is the expected one
    }

    // Test to check if no user is logged in after logout
    @Test
    public void testIsLoggedOut() {
        authService.register("valid@example.com", "ValidUser", "Password1!"); // Register a valid user
        authService.login("ValidUser", "Password1!"); // Log in the user
        User user = authService.isLoggedIn(); // Get the logged-in user
        authService.logout(user); // Log out the user
        Assert.assertNull("We have successfully logged out! No user is currently active in the system", authService.isLoggedIn()); // Assert that no user is logged in after logout
    }

    @After
    public void tearDown() throws Exception {
        // Remove the additional rows created through the test cases
        removeRowFromFile(authService.USER_DATA_FILE, "ValidUser");
        removeRowFromFile(authService.USER_DATA_FILE, "Invalid@User");
        removeRowFromFile(authService.USER_DATA_FILE, "WrongUser");
        removeRowFromFile(authService.USER_DATA_FILE, "ExistingUser");
        removeRowFromFile(authService.USER_DATA_FILE, "User1");
        removeRowFromFile(authService.USER_DATA_FILE, "SaveUser");
        removeRowFromFile(authService.USER_DATA_FILE, "TestRegisterUser");
        removeRowFromFile(authService.USER_DATA_FILE, "TestStoreUser");
        removeRowFromFile(authService.USER_DATA_FILE, "NonExistentUser");
        removeRowFromFile(authService.USER_DATA_FILE, "TestUser");
    }

    private void removeRowFromFile(String filePath, String username) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        // Read from current file and write to the temporary file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            // Skip rows with the specified username and write other rows to the temp file (which becomes the main file later on)
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.contains(username)) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        }
        // Essentially deleting the original file and renaming the temp file to the original file.
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            throw new IOException("Failed to clean up test data");
        }
    }





}
