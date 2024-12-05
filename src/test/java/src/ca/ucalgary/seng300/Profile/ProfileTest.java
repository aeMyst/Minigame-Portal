package src.ca.ucalgary.seng300.Profile;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileTest {

    private Profile profile;
    private User mockUser;

    @Before
    public void setUp() {
        // Create a mock User object
        mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getEmail()).thenReturn("testUser@example.com");

        // Initialize the Profile object with the mock User
        profile = new Profile(mockUser);
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