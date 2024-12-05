package Profile;

import org.junit.After;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.Profile.controllers.AuthController;
import src.ca.ucalgary.seng300.Profile.controllers.ProfileController;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;
import src.ca.ucalgary.seng300.Profile.services.AuthService;

import java.io.*;

/**
 * Test Class for AuthController Class methods
 */
public class AuthControllerTest {
    // Fields used in setup and test cases
    private AuthController authController;
    private AuthService authService;
    private ProfileService profileService;
    private String username = "testUser";
    private String email = "testUser@example.ca";
    private String password = "Password1!";

    // Method to instantiate AuthService and AuthController
    @Before
    public void setup(){
        authService = new AuthService();
        authController = new AuthController(authService);
        profileService = new ProfileService(authService);
    }

    // Test: Register a valid user
    @Test
    public void testValidRegister(){
        boolean result = authController.register(email, username, password);
        assertTrue("User has been sucessfully registered", result);
    }

    // Test: Register a user with an invalid username
    @Test
    public void testInvalidUsernameRegister(){
        boolean result = authController.register(email, "Invalid!User", password);
        assertFalse("User cannot be registered, invalid username", result);
    }

    // Test: Register a user with an invalid email
    @Test
    public void testInvalidEmailRegister(){
        boolean result = authController.register("invalid email", username, password);
        assertFalse("User cannot be registered, invalid email", result);
    }

    // Test: Register a user with an invalid password
    @Test
    public void testInvalidPasswordRegister(){
        boolean result = authController.register(email, username, "short");
        assertFalse("User cannot be registered, invalid username", result);
    }

    // Test: Login a valid user
    @Test
    public void testValidUserLogin(){
        boolean registerSuccess = authController.register(email, username, password);
        // Assert registration was successful before testing login
        assertTrue(registerSuccess);
        boolean result = authController.login(username, password);
        assertTrue("User was logged in successfully",result);
    }

    // Test: Login a user with an invalid username
    @Test
    public void testInvalidUsernameLogin(){
        boolean registerSuccess = authController.register(email, username, password);
        // Assert registration was successful before testing login
        assertTrue(registerSuccess);
        boolean result = authController.login("WrongUser", password);
        assertFalse("Username is not correct. User not logged in", result);
    }

    // Test: Login a user with an invalid password
    @Test
    public void testInvalidPasswordLogin(){
        boolean registerSuccess = authController.register(email, username, password);
        // Assert registration was successful before testing login
        assertTrue(registerSuccess);
        boolean result = authController.login(username, "WrongPassword1!");
        assertFalse("User password is not correct. User not logged in", result);
    }

    // Test: Logout a valid user
    @Test
    public void testValidUserLogout(){
        boolean registerSuccess = authController.register(email, username, password);
        boolean loginSuccess = authController.login(username, password);
        // Assert registration and login were successful before testing login
        assertTrue(registerSuccess);
        assertTrue(loginSuccess);
        boolean result = authController.logout(authService.isLoggedIn());
        assertTrue("User was logged out successfully",result);
    }

    // Test: Logout an invalid user
    @Test
    public void testInvalidUserLogout(){
        User invalidUser = new User("InvalidUser", "Invalidpswd1!", "invalid@example.ca");
        boolean result = authController.logout(invalidUser);
        assertFalse("User not found, logout unsuccessful", result);
    }

    // Test: Check if a user is correctly logged in
    @Test
    public void testValidIsLoggedIn(){
        boolean registerSuccess = authController.register(email, username, password);
        boolean loginSuccess = authController.login(username, password);
        // Assert registration and login were successful before testing login
        assertTrue(registerSuccess);
        assertTrue(loginSuccess);
        User resultUser = authController.isLoggedIn();
        assertNotNull("Correct User is logged in", resultUser);
    }

    // Test: Check if no user is logged in after being logged out
    @Test
    public void testInvalidIsLoggedIn(){
        boolean registerSuccess = authController.register(email, username, password);
        boolean loginSuccess = authController.login(username, password);
        boolean logoutSuccess = authController.logout(authController.isLoggedIn());
        // Assert registration, login and logout were successful before testing login
        assertTrue(registerSuccess);
        assertTrue(loginSuccess);
        assertTrue(logoutSuccess);
        User resultUser = authController.isLoggedIn();
        assertNull("No User is logged in", resultUser);
    }

    @After
    public void clearCSV() throws IOException {
        removeTestUser(profileService.usersPath, username);
    }

    public void removeTestUser(String filepath, String username) throws IOException{
        File inputFile = new File(filepath);
        File tempFile = new File(filepath + ".tmp");
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
