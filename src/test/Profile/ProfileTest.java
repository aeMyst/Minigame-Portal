package Profile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import src.ca.ucalgary.seng300.Profile.controllers.ProfileController;
import src.ca.ucalgary.seng300.Profile.models.Profile;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;


public class ProfileTest {
    private String username = "username";
    private String email = "test@example.ca";
    private String password = "Password1!";
    private AuthService authService;

    @Before
    public void setup(){
        authService = new AuthService();
        boolean register = authService.register(email, username, password);
        boolean login = authService.login(username, password);

    }

    @Test
    public void testGetGamesPlayed(){
        Profile profile = new Profile(authService.isLoggedIn());
        int games = profile.getGamesPlayed();
        assertNotNull(games);
    }

    @Test
    public void testGetWins(){
        Profile profile = new Profile(authService.isLoggedIn());
        int wins = profile.getWins();
        assertNotNull(wins);
    }
    @Test
    public void testGetLosses(){
        Profile profile = new Profile(authService.isLoggedIn());
        int losses = profile.getLosses();
        assertNotNull(losses);
    }

    @Test
    public void testGetRank(){
        Profile profile = new Profile(authService.isLoggedIn());
        int rank = profile.getRank();
        assertNotNull(rank);
    }

    @Test
    public void testGetProfileDetails(){
        Profile profile = new Profile(authService.isLoggedIn());
        String details = profile.getProfileDetails();
        assertNotNull("Profile details should be printed", details);
        assertEquals("Username: username, Email: test@example.ca, Games Played: 0, Wins: 0, Losses: 0, Rank: 0", details);
    }
}
