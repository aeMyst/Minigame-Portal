package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import java.util.*;
import java.io.*;

public class Leaderboard implements ILeaderboard {
    private HashMap<String, EloData> players = new HashMap<>();

    @Override
    public void loadPlayersFromCSV(String filename) {
        //we'll try bufferedReader and parse that values we need from the csv


    }

    @Override
    public void addPlayer(EloData playerID) {
        //future logic implemented here

    }

    //sorts names with corresponding elo values in order
    @Override
    public List<EloData> sortedLeaderboard() {
        List<EloData> sortedPlayers = new ArrayList<>(players.values());
        return sortedPlayers;
    }
}
