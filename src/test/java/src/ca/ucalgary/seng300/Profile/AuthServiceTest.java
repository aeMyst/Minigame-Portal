package src.ca.ucalgary.seng300.Profile;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;

import java.io.*;

public class AuthServiceTest {
    private AuthService authService;

    // Set up method to initialize AuthService before each test
    @Before
    public void setUp() {
        authService = new AuthService(); // Create a new instance of AuthService to ensure each test runs independently
    }

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
