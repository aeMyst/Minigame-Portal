package src.ca.ucalgary.seng300.leaderboard.controllers;

import src.ca.ucalgary.seng300.leaderboard.logic.EloData;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;

public class EloRatingController {
    private EloRating eloRating;

    public EloRatingController() {
        this.eloRating = new EloRating();
    }

    public void updatePlayerElo(EloData winner, EloData loser) {
        // Call the updateElo method from EloRating
        eloRating.updateElo(winner, loser);
    }

    public int getPlayerElo(EloData player) {
        // Return the current ELO of the player
        return player.getElo();
    }

    public void displayPlayerStats(EloData player) {
        System.out.println("Player ID: " + player.getPlayerID());
        System.out.println("ELO: " + player.getElo());
        System.out.println("Games Played: " + player.getGamesPlayed());
        System.out.println("Wins: " + player.getWins());
        System.out.println("Losses: " + player.getLosses());
    }

    //testing
}
