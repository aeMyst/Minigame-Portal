package src.ca.ucalgary.seng300.leaderboard.controllers;

import src.ca.ucalgary.seng300.leaderboard.logic.EloData;
import src.ca.ucalgary.seng300.leaderboard.logic.MatchMaker;

import java.util.ArrayList;
import java.util.List;

public class MatchmakerController {
    private MatchMaker matchMaker;

    public MatchmakerController() {
        this.matchMaker = new MatchMaker();
    }

    public void addPlayer(EloData player) {
        matchMaker.addPlayerToQueue(player);
    }

    public List<EloData> getQueue() {
        // Assuming MatchMaker has a method to return the queue
//        return matchMaker.getQueue();
        return new ArrayList<>();
    }

    public void findAndCreateMatch() {
        matchMaker.findMatch();
    }

    public void displayQueue() {
        List<EloData> queue = getQueue();
        for (EloData player : queue) {
            System.out.println("Player ID: " + player.getPlayerID());
            System.out.println("ELO: " + player.getElo());
            System.out.println("Games Played: " + player.getGamesPlayed());
            System.out.println("Wins: " + player.getWins());
            System.out.println("Losses: " + player.getLosses());
        }
    }
}