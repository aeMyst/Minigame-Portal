package src.ca.ucalgary.seng300.leaderboard.utility;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// Example main to show file reading, writing, and storage and Player are all functional for future use cases
public class ExampleMain {

    private static final String FILE_PATH = "players_data.csv";

    public static void main(String[] args) {
        File file = new File(FILE_PATH);
        Storage storage;

        // Check if the file exists
        if (file.exists()) {
            storage = FileManagement.fileReading(file);
            if (storage == null) {
                storage = new Storage();
                System.out.println("Error reading existing data. Starting with an empty player list.");
            } else {
                System.out.println("Loaded existing player data from file.");
            }
        } else {
            storage = new Storage();
            System.out.println("No existing file found. Starting with an empty player list.");
        }

        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;
        Leaderboard leaderboard = new Leaderboard(); // Create an instance of the Leaderboard class

        while (continueRunning) {
            // Show options
            System.out.println("\nSelect one of the following options below:");
            System.out.println("1. Show existing player data");
            System.out.println("2. Add a new player");
            System.out.println("3. Display sorted leaderboard by game type");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1, 2, 3, or 4): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // Option 1: Display current data
                    System.out.println("\nCurrent Player Data:");
                    displayPlayerData(storage);
                    break;

                case "2":
                    // Option 2: Get new player details and add to storage
                    Player newPlayer = getPlayerInput(scanner);
                    storage.addPlayer(newPlayer);

                    // Save updated data back to the file
                    FileManagement.fileWriting(storage, file);
                    System.out.println("Player added and data saved successfully.");
                    break;

                case "3":
                    // Option 3: Display sorted leaderboard by game type
                    System.out.print("Enter the game type (CONNECT4, TICTACTOE, CHECKERS): ");
                    String gameType = scanner.nextLine().toUpperCase();
                    displaySortedLeaderboard(leaderboard, gameType);
                    break;

                case "4":
                    // Option 4: Exit program
                    continueRunning = false;
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }

    // Display player data
    private static void displayPlayerData(Storage storage) {
        ArrayList<Player> players = storage.getPlayers();
        if (players.isEmpty()) {
            System.out.println("No player data available.");
        } else {
            for (Player player : players) {
                System.out.println(player);
            }
        }
    }

    // Get player input
    private static Player getPlayerInput(Scanner scanner) {
        String gameType;
        while (true) {
            System.out.print("Enter Type of Game (CONNECT4, TICTACTOE, CHECKERS): ");
            gameType = scanner.nextLine().toUpperCase();
            if (gameType.equals("CONNECT4") || gameType.equals("TICTACTOE") || gameType.equals("CHECKERS")) {
                break;
            } else {
                System.out.println("Invalid game type. Please enter CONNECT4, TICTACTOE, or CHECKERS.");
            }
        }

        System.out.print("Enter Player ID: ");
        String playerID = scanner.nextLine();

        System.out.print("Enter Elo rating: ");
        int elo = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Wins: ");
        int wins = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Losses: ");
        int losses = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Draws: ");
        int draws = Integer.parseInt(scanner.nextLine());

        return new Player(gameType, playerID, elo, wins, losses, draws);
    }

    // Display sorted leaderboard
    private static void displaySortedLeaderboard(Leaderboard leaderboard, String gameType) {
//        String[][] sortedLeaderboard = leaderboard.sortLeaderboard(gameType);
//        if (sortedLeaderboard.length == 0) {
//            System.out.println("No players available for the specified game type.");
//        } else {
//            System.out.println("\nSorted Leaderboard for " + gameType + ":");
//            System.out.printf("%-15s %-10s %-10s%n", "Player ID", "Elo", "Wins");
//            for (String[] playerData : sortedLeaderboard) {
//                System.out.printf("%-15s %-10s %-10s%n", playerData[0], playerData[1], playerData[2]);
//            }
//        }
        if (gameType.equals("CONNECT4")) {
            String[][] sortedLeaderboard = leaderboard.getC4Leaderboard();
            System.out.println("\nSorted Leaderboard for " + gameType + ":");
            System.out.printf("%-15s %-10s %-10s%n", "Player ID", "Elo", "Wins");
            for (String[] playerData : sortedLeaderboard) {
                System.out.printf("%-15s %-10s %-10s%n", playerData[0], playerData[1], playerData[2]);
            }
        } else if (gameType.equals("CHECKERS")) {
            String[][] sortedLeaderboard = leaderboard.getCheckersLeaderboard();
            System.out.println("\nSorted Leaderboard for " + gameType + ":");
            System.out.printf("%-15s %-10s %-10s%n", "Player ID", "Elo", "Wins");
            for (String[] playerData : sortedLeaderboard) {
                System.out.printf("%-15s %-10s %-10s%n", playerData[0], playerData[1], playerData[2]);
            }
        } else if (gameType.equals("TICTACTOE")) {
            String[][] sortedLeaderboard = leaderboard.getTicTacToeLeaderboard();
            System.out.println("\nSorted Leaderboard for " + gameType + ":");
            System.out.printf("%-15s %-10s %-10s%n", "Player ID", "Elo", "Wins");
            for (String[] playerData : sortedLeaderboard) {
                System.out.printf("%-15s %-10s %-10s%n", playerData[0], playerData[1], playerData[2]);
            }
        }
    }
    //will have matchmaker example method implementation later on
}
