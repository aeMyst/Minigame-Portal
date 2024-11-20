package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import java.util.*;
import java.io.*;

public class Leaderboard implements ILeaderboard {
    private HashMap<String, EloData> players = new HashMap<>();
    private static final String FILE_PATH = "players_data.csv";
    private ArrayList<Player> loadPlayers = new ArrayList<>();

//    @Override
//    public void loadPlayersFromCSV() {
//        // we'll try bufferedReader and parse that values we need from the csv
//        File file = new File(FILE_PATH);
//        Storage storage;
//
//        if (file.exists()) {
//            storage = FileManagement.fileReading(file);
//            if (storage == null) {
//                storage = new Storage();
//                System.out.println("[ERROR] Failed to read file. Starting with an empty player list.");
//            } else {
//                loadPlayers = storage.getPlayers();
//                System.out.println("Loading existing player data from file.");
//            }
//        } else {
//            storage = new Storage();
//            System.out.println("[ERROR] No existing file found. Starting with an empty player list.");
//        }
//
//        for (Player player : loadPlayers) {
//            EloData eloPlayer = new EloData(player.getGameType(), player.getPlayerID(), player.getElo(), player.get)
//        }
//
//    }

    @Override
    public void addPlayer(EloData playerID) {
        players.put(playerID.getPlayerID(), playerID);
    }

    // sorts names with corresponding elo values in order
    @Override
    public List<List<String>> sortLeaderboard(String gameType) {
        List<List<String>> sortedLeaderboard = new ArrayList<>();
        File file = new File(FILE_PATH);
        Storage storage;

        if (file.exists()) {
            storage = FileManagement.fileReading(file);

            List<Player> neededPlayers = new ArrayList<>();


            for (Player player : storage.getPlayers()) {
                String game = player.getGameType();
                if (game.equals(gameType)) {
                    neededPlayers.add(player);
                }
            }

            neededPlayers.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

            for (Player player : neededPlayers) {
                List<String> entry = new ArrayList<>();
                entry.add(player.getPlayerID());
                entry.add(String.valueOf(player.getElo()));
                entry.add(String.valueOf(player.getWins()));
                sortedLeaderboard.add(entry);
            }
        } else {
            System.out.println("[ERROR] File does not exist.");
        }


        return sortedLeaderboard;
    }
}
