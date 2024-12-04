package test.Profile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.controllers.ProfileController;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;

public class ProfileControllerTest {
    private ProfileController profileController;
    private ProfileService profileService;

    // Set up method to initialize ProfileController and ProfileService before each test
    @Before
    public void setUp() {
        profileService = new ProfileService(null); // Mock or provide necessary dependencies for ProfileService
        profileController = new ProfileController(profileService); // Create a new instance of ProfileController
    }

    // Test for viewing a user's profile
    @Test
    public void testViewProfile() {
        String result = profileController.viewProfile(); // Attempt to view profile
        Assert.assertNotNull("The profile should be returned", result); // Assert that the profile is returned
    }

}
