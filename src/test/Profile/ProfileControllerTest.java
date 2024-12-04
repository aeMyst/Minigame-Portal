package Profile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.Profile.controllers.ProfileController;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;

public class ProfileControllerTest {
    private ProfileController profileController;
    private ProfileService profileService;
    private AuthService authService;

    private String username = "newUser";
    private String password = "Password1!";
    private String email = "test@example.ca";

    // Set up method to initialize ProfileController and ProfileService before each test
    @Before
    public void setUp() {
        authService = new AuthService();
        profileService = new ProfileService(authService); // Mock or provide necessary dependencies for ProfileService
        profileController = new ProfileController(profileService); // Create a new instance of ProfileController


    }

    // Test for viewing a user's profile
    @Test
    public void testViewProfile() {
        String result = profileController.viewProfile(); // Attempt to view profile
        Assert.assertNotNull("The profile should be returned", result); // Assert that the profile is returned
        System.out.println(result);
    }

    @Test
    public void testUpdateProfile() {
        boolean registration = authService.register(email, username, password);
        assertTrue("Registration success", registration);
        boolean login = authService.login(username, password);
        assertTrue("Login success", login);

        profileController.updateProfile(authService.isLoggedIn(), "changedUsername", "changedEmail@example.ca", "changedPassword1!");

        User updatedProfile = authService.isLoggedIn();
        assertEquals("changedUsername", updatedProfile.getUsername());
        assertEquals("changedEmail@example.ca", updatedProfile.getEmail());
    }

    @Test
    public void testInitializeProfile(){
        profileController.initializeProfile(username);
        String profile = profileController.searchProfile(username);
        assertNotNull(profile);
        assertFalse(profile.isEmpty());
    }

    @Test
    public void testSearchProfileSuccess(){
        String result = profileController.searchProfile(username);
        assertNotNull(result);System.out.println(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSearchProfileFailure(){
        String result = profileService.searchProfile("nonExistentUser");
        assertEquals("Profile not found for nonExistentUser", result);
    }
}
