package src.ca.ucalgary.seng300.leaderboard.controllers;

import src.ca.ucalgary.seng300.leaderboard.data.GameType;
import src.ca.ucalgary.seng300.leaderboard.logic.*;
import java.util.*;


public class LeaderboardController {
    private Leaderboard leaderboard;
    private EloRating elo;
    private MatchMaker matchmaker;

    // constructor

    public LeaderboardController() {
        this.leaderboard = new Leaderboard();
        this.elo = new EloRating();
        this.matchmaker = new MatchMaker();
    }

    // parsing csv file
    public void loadPlayers(String filename) {
        leaderboard.loadPlayersFromCSV(filename); // depends on the loadPlayersFromCSV method
        //displaySortedLeaderboard();
    }
    public void displaySortedLeaderboard(String gameType) {
        List<List<String>> sorted = leaderboard.sortLeaderboard(gameType);
        for (List<String> player : sorted) {
            System.out.println("PlayerID: " + player.get(0) + ", Elo: " + player.get(1));
        }
    }

    // using UI to add players
    public void addPlayer(EloData player) {
        // update later if needed
        matchmaker.addPlayerToQueue(player); // queue for match
        leaderboard.addPlayer(player);  // add to leaderboard
    }

    // use UI to find match & create match
    public void findMatch() {
        matchmaker.findMatch();
    }

    public void updateEloAfterMatch(EloData winner, EloData loser) {
        elo.updateElo(winner, loser);
    }

}
