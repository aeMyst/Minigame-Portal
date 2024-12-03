package src.ca.ucalgary.seng300.leaderboardmatchmaking;

import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;
import src.ca.ucalgary.seng300.leaderboard.data.Player;

import static org.junit.Assert.*;

public class EloRatingTest {

    private EloRating eloRating;

    @Before
    public void setUp() {
        eloRating = new EloRating();
    }

    @Test
    public void testCalculateNewElo_PlayerWinsAgainstEqualOpponent() {
        int newElo = eloRating.calculateNewElo(1500, 1500, true);
        assertEquals(1524, newElo);
    }

    @Test
    public void testCalculateNewElo_PlayerLosesAgainstEqualOpponent() {
        int newElo = eloRating.calculateNewElo(1500, 1500, false);
        assertEquals(1476, newElo);
    }

    @Test
    public void testCalculateNewElo_PlayerWinsAgainstHigherOpponent() {
        int newElo = eloRating.calculateNewElo(1400, 1600, true);
        assertEquals(1431, newElo);
    }

    @Test
    public void testCalculateNewElo_PlayerLosesAgainstHigherOpponent() {
        int newElo = eloRating.calculateNewElo(1400, 1600, false);
        assertEquals(1383, newElo);
    }

    @Test
    public void testCalculateNewElo_PlayerWinsAgainstLowerOpponent() {
        int newElo = eloRating.calculateNewElo(1600, 1400, true);
        assertEquals(1615, newElo);
    }

    @Test
    public void testCalculateNewElo_PlayerLosesAgainstLowerOpponent() {
        int newElo = eloRating.calculateNewElo(1600, 1400, false);
        assertEquals(1576, newElo);
    }

    @Test
    public void testCalculateNewElo_PlayerCannotHaveNegativeElo() {
        int newElo = eloRating.calculateNewElo(0, 1500, false);
        assertEquals(0, newElo);
    }

    @Test
    public void testUpdateElo_WinnerAndLoserRatingsUpdateCorrectly() {
        Player winner = new Player("Winner", "1500", 1500, 0, 0, 0);
        Player loser = new Player("Loser", "1501", 1500, 0, 0, 0);

        eloRating.updateElo(winner, loser);

        assertEquals(1524, winner.getElo());
        assertEquals(1476, loser.getElo());
    }

    @Test
    public void testUpdateElo_WinnerWithHigherRatingAndLoserWithLowerRating() {
        Player winner = new Player("Winner", "1502", 1600, 0, 0, 0);
        Player loser = new Player("Loser", "1503", 1400, 0, 0, 0);

        eloRating.updateElo(winner, loser);

        assertEquals(1615, winner.getElo());
        assertEquals(1376, loser.getElo());
    }

    @Test
    public void testUpdateElo_LoserWithZeroRatingDoesNotGoNegative() {
        Player winner = new Player("Winner", "1504", 1500, 0, 0, 0);
        Player loser = new Player("Loser", "1505", 0, 0, 0, 0);

        eloRating.updateElo(winner, loser);

        assertEquals(1524, winner.getElo());
        assertEquals(0, loser.getElo());
    }
}
