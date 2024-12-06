package src.ca.ucalgary.seng300.Profile;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;

import static org.junit.Assert.assertEquals;

public class ProfileTest {
    private Profile profile;
    private User user;

    @Before
    public void setUp() {
        // Create a real User object
        user = new User("testUser", "passowrd1!", "testUser@example.com");

        // Initialize the Profile object with the real User
        profile = new Profile(user);
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
}