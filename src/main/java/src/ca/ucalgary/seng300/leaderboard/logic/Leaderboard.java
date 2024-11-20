package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.GameType;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.util.*;
import java.io.*;

public class Leaderboard implements ILeaderboard {
    private HashMap<String, EloData> players = new HashMap<>();

    @Override
    public void loadPlayersFromCSV(String filename) {
        // we'll try bufferedReader and parse that values we need from the csv

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            ArrayList<String> storeID = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                // ignoring blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] playerArr = line.split(",");

                // ignore lines with disproportionate amount of data
                if (playerArr.length != 6) {
                    continue;
                }

                String gameType = playerArr[0];
                String playerID = playerArr[1];
                storeID.add(playerID);
                int elo = Integer.parseInt(playerArr[2].trim());
                int wins = Integer.parseInt(playerArr[3].trim());
                int losses = Integer.parseInt(playerArr[4].trim());
                int ties = Integer.parseInt(playerArr[5].trim());

                EloData playerData = new EloData(gameType, playerID, elo, wins, losses, ties);
                addPlayer(playerData);

            }

            // checking for duplicate player IDs UNCONFIRMED DRAFT (if anyone asks, lucas said "don't bother")

//            Set<String> checkID = new HashSet<>();
//            for (String id : storeID) {
//                if (!checkID.add(id)) {
//                    throw new IllegalArgumentException("ERROR: Duplicate player ID found: " + id);
//                }
//            }
        } catch (IOException e) {
            System.err.println("ERROR: Failed to read .csv file: " + e.getMessage());
        }
    }

    @Override
    public void addPlayer(EloData playerID) {
        players.put(playerID.getPlayerID(), playerID);
    }

    // sorts names with corresponding elo values in order
    @Override
    public List<List<String>> sortLeaderboard(String gameType) {

        String FILE_PATH = "players_data.csv";
        File file = new File(FILE_PATH);

        Storage storage = FileManagement.fileReading(file);

        List<Player> neededPlayers = new ArrayList<>();

        List<List<String>> sortedLeaderboard = new ArrayList<>();

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

        // separate leaderboards for each game not yet created


        //for (){}
        /*
        List<HashMap.Entry<String, EloData>> sortPlayers = new ArrayList<>(players.entrySet());
        // sorts the players according to descending elo ratings
        sortPlayers.sort((player1, player2) -> Integer.compare(player2.getValue().getElo(), player1.getValue().getElo()));

        // adds each player from the sorted list to another list with the appropriate data type to be returned
        for (HashMap.Entry<String, EloData> entry : sortPlayers) {
            sortedLB.add(entry.getValue());
        }
        */
        return sortedLeaderboard;
    }
}
