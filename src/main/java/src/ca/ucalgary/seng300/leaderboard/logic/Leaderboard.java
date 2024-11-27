package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import java.util.*;
import java.io.*;

public class Leaderboard implements ILeaderboard {
    private static final String FILE_PATH = "players_data.csv";
    private File file = new File(FILE_PATH);

    // sorts names with corresponding elo values in order

    public String[][] sortLeaderboard(String gameType) {
        Storage storage;

        if (file.exists()) {
            storage = FileManagement.fileReading(file);

            List<Player> neededPlayers = new ArrayList<>();

            int counter = 0;
            int count = 0;


            for (Player player : storage.getPlayers()) {
                String game = player.getGameType();
                if (game.equals(gameType)) {
                    neededPlayers.add(player);
                    counter++;
                }
            }

            neededPlayers.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));
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
            return new String[0][3];
        }
    }

    // all leaderboard will return the top10 for gui readability

    public String[][] getC4Leaderboard() {
        Storage storage;
        int count = 0;
        int sortedCount = 0;
        List<Player> C4Players = new ArrayList<>();
        File C4file = new File(FILE_PATH);

        if (C4file.exists()) {
            storage = FileManagement.fileReading(C4file);

            for (Player player : storage.getPlayers()) {
                String type = player.getGameType();
                if (type.equals("CONNECT4")) {
                    C4Players.add(player);
                    count++;
                } if (count >= 10) {
                    break;
                }
            }

            if (C4Players.isEmpty()) {
                System.out.println("No player data available.");
            }
            // sorting C4 players LB
            C4Players.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

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
            return new String[0][];
        }
    }

    public String[][] getTicTacToeLeaderboard() {
        Storage storage;
        int count = 0;
        int sortedCount = 0;
        List<Player> TicTacToePlayers = new ArrayList<>();

        if (file.exists()) {
            storage = FileManagement.fileReading(file);

            for (Player player : storage.getPlayers()) {
                String type = player.getGameType();
                if (type.equals("TICTACTOE")) {
                    TicTacToePlayers.add(player);
                    count++;
                } if (count >= 10) {
                    break;
                }
            }
            // sorting C4 players LB
            TicTacToePlayers.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

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
            return new String[0][];
        }
    }

    public String[][] getCheckersLeaderboard() {
        Storage storage;
        int count = 0;
        int sortedCount = 0;
        List<Player> CheckersPlayers = new ArrayList<>();

        if (file.exists()) {
            storage = FileManagement.fileReading(file);

            for (Player player : storage.getPlayers()) {
                String type = player.getGameType();
                if (type.equals("CHECKERS")) {
                    CheckersPlayers.add(player);
                    count++;
                } if (count >= 10) {
                    break;
                }
            }
            // sorting C4 players LB
            CheckersPlayers.sort((player1, player2) -> Integer.compare(player2.getElo(), player1.getElo()));

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
            return new String[0][];
        }
    }
}
