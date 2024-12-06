package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import java.util.*;
import java.io.*;

/**
 * This class manages the leaderboard logic for various games, including sorting and retrieving player rankings.
 * It handles loading player data from a file, sorting by Elo rating, and retrieving the top players for different games.
 */
public class Leaderboard implements ILeaderboard {
    private static final String FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/profiles.csv";
    private final File file;

    public Leaderboard() {
        file = new File(FILE_PATH);
    }

    public Leaderboard(String file_path) {
        file = new File(file_path);
    }

    /**
     * Sorts the leaderboard for a specified game type by player Elo rating.
     *
     * @param gameType The game for which the leaderboard is to be sorted (e.g., "CONNECT4").
     * @return A 2D array representing the sorted leaderboard with player ID, Elo, and wins.
     */
    public String[][] sortLeaderboard(String gameType) {
        Storage storage;

        if (file.exists()) {
            // Reading player data from the file
            storage = FileManagement.fileReading(file);

            List<Player> neededPlayers = new ArrayList<>();
            int counter = 0;
            int count = 0;

            // Adding players of the specified game type to the list
            for (Player player : storage.getPlayers()) {
                String game = player.getGameType();
                if (game.equals(gameType)) {
                    neededPlayers.add(player);
                    counter++;
                }
            }

            // Sorting players by Elo rating in descending order
            neededPlayers.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

            // Creating a 2D array for the sorted leaderboard
            String[][] sortedLeaderboard = new String[counter][3];

            for (Player player : neededPlayers) {
                sortedLeaderboard[count][0] = (player.getPlayerID());
                sortedLeaderboard[count][1] = (String.valueOf(player.getElo()));
                sortedLeaderboard[count][2] = (String.valueOf(player.getWins()));
                count++;
            }
            return sortedLeaderboard;
        } else {
            System.err.println("[ERROR] File does not exist.");
            return new String[0][3]; // Return an empty array if the file does not exist
        }
    }

    /**
     * Retrieves the leaderboard for the CONNECT4 game, sorted by Elo rating, and returns the top 10 players.
     *
     * @return A 2D array representing the top 10 players' data for the CONNECT4 game, including player ID, Elo, and wins.
     */
    public String[][] getC4Leaderboard() {
        Storage storage;
        int count = 0;
        int sortedCount = 0;
        List<Player> C4Players = new ArrayList<>();

        if (file.exists()) {
            // Reading player data from the file
            storage = FileManagement.fileReading(file);

            // Adding players of type "CONNECT4" to the list
            for (Player player : storage.getPlayers()) {
                String type = player.getGameType();
                if (type.equals("CONNECT4")) {
                    C4Players.add(player);
                    count++;
                }
                if (count == 10) {
                    break; // Limit to the top 10 players
                }
            }

            // Sorting CONNECT4 players by Elo rating in descending order
            C4Players.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

            // Creating a 2D array for the sorted leaderboard
            String[][] sortedC4LB = new String[count][3];

            for (Player player : C4Players) {
                sortedC4LB[sortedCount][0] = player.getPlayerID();
                sortedC4LB[sortedCount][1] = String.valueOf(player.getElo());
                sortedC4LB[sortedCount][2] = (String.valueOf(player.getWins()));
                sortedCount++;
            }
            return sortedC4LB;
        } else {
            System.out.println("[ERROR] File does not exist.");
            return new String[0][]; // Return an empty array if the file does not exist
        }
    }

    /**
     * Retrieves the leaderboard for the TICTACTOE game, sorted by Elo rating, and returns the top 10 players.
     *
     * @return A 2D array representing the top 10 players' data for the TICTACTOE game, including player ID, Elo, and wins.
     */
    public String[][] getTicTacToeLeaderboard() {
        Storage storage;
        int count = 0;
        int sortedCount = 0;
        List<Player> TicTacToePlayers = new ArrayList<>();

        if (file.exists()) {
            // Reading player data from the file
            storage = FileManagement.fileReading(file);

            // Adding players of type "TICTACTOE" to the list
            for (Player player : storage.getPlayers()) {
                String type = player.getGameType();
                if (type.equals("TICTACTOE")) {
                    TicTacToePlayers.add(player);
                    count++;
                }
                if (count >= 10) {
                    break; // Limit to the top 10 players
                }
            }

            // Sorting TICTACTOE players by Elo rating in descending order
            TicTacToePlayers.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

            // Creating a 2D array for the sorted leaderboard
            String[][] sortedTictacToeLB = new String[count][3];

            for (Player player : TicTacToePlayers) {
                sortedTictacToeLB[sortedCount][0] = player.getPlayerID();
                sortedTictacToeLB[sortedCount][1] = String.valueOf(player.getElo());
                sortedTictacToeLB[sortedCount][2] = (String.valueOf(player.getWins()));
                sortedCount++;
            }
            return sortedTictacToeLB;
        } else {
            System.err.println("[ERROR] File does not exist.");
            return new String[0][]; // Return an empty array if the file does not exist
        }
    }

    /**
     * Retrieves the leaderboard for the CHECKERS game, sorted by Elo rating, and returns the top 10 players.
     *
     * @return A 2D array representing the top 10 players' data for the CHECKERS game, including player ID, Elo, and wins.
     */
    public String[][] getCheckersLeaderboard() {
        Storage storage;
        int count = 0;
        int sortedCount = 0;
        List<Player> CheckersPlayers = new ArrayList<>();

        if (file.exists()) {
            // Reading player data from the file
            storage = FileManagement.fileReading(file);

            // Adding players of type "CHECKERS" to the list
            for (Player player : storage.getPlayers()) {
                String type = player.getGameType();
                if (type.equals("CHECKERS")) {
                    CheckersPlayers.add(player);
                    count++;
                }
                if (count >= 10) {
                    break; // Limit to the top 10 players
                }
            }

            // Sorting CHECKERS players by Elo rating in descending order
            CheckersPlayers.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

            // Creating a 2D array for the sorted leaderboard
            String[][] sortedCheckersLB = new String[count][3];

            for (Player player : CheckersPlayers) {
                sortedCheckersLB[sortedCount][0] = player.getPlayerID();
                sortedCheckersLB[sortedCount][1] = String.valueOf(player.getElo());
                sortedCheckersLB[sortedCount][2] = (String.valueOf(player.getWins()));
                sortedCount++;
            }
            return sortedCheckersLB;
        } else {
            System.err.println("[ERROR] File does not exist.");
            return new String[0][]; // Return an empty array if the file does not exist
        }
    }
}
