package src.ca.ucalgary.seng300.leaderboard.utility;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// Example main to demonstrate the functionality of file reading, writing, storage, and basic Player operations for future use cases
public class ExampleMain {
    // Path to the CSV file containing player profiles
    private static final String FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/profiles.csv";

    /**
     * Main method to demonstrate file reading, writing, and player data management.
     * Provides an interactive console-based menu for users to interact with the system.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        File file = new File(FILE_PATH); // File object for the CSV file
        Storage storage;

        // Check if the file exists
        if (file.exists()) {
            // Read existing data from the file
            storage = FileManagement.fileReading(file);
            if (storage == null) {
                // If file reading fails, initialize with an empty storage
                storage = new Storage();
                System.out.println("Error reading existing data. Starting with an empty player list.");
            } else {
                System.out.println("Loaded existing player data from file.");
            }
        } else {
            // If no file exists, initialize with an empty storage
            storage = new Storage();
            System.out.println("No existing file found. Starting with an empty player list.");
        }

        Scanner scanner = new Scanner(System.in); // Scanner for user input
        boolean continueRunning = true;
        Leaderboard leaderboard = new Leaderboard(); // Create an instance of the Leaderboard class

        // Main program loop
        while (continueRunning) {
            // Display options to the user
            System.out.println("\nSelect one of the following options below:");
            System.out.println("1. Show existing player data");
            System.out.println("2. Add a new player");
            System.out.println("3. Display sorted leaderboard by game type");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1, 2, 3, or 4): ");
            String choice = scanner.nextLine().trim(); // Read and trim user input

            // Handle user choice
            switch (choice) {
                case "1":
                    // Option 1: Display current player data
                    System.out.println("\nCurrent Player Data:");
                    displayPlayerData(storage);
                    break;

                case "2":
                    // Option 2: Add a new player
                    Player newPlayer = getPlayerInput(scanner);
                    storage.addPlayer(newPlayer); // Add player to storage

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
                    // Option 4: Exit the program
                    continueRunning = false;
                    System.out.println("Exiting program.");
                    break;

                default:
                    // Handle invalid input
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }

        scanner.close(); // Close the scanner
    }

    /**
     * Displays all player data stored in the system.
     *
     * @param storage The Storage object containing the list of players.
     */
    private static void displayPlayerData(Storage storage) {
        ArrayList<Player> players = storage.getPlayers(); // Retrieve player list
        if (players.isEmpty()) {
            System.out.println("No player data available.");
        } else {
            for (Player player : players) {
                System.out.println(player); // Display each player's details
            }
        }
    }

    /**
     * Gathers input from the user to create a new Player object.
     * Validates the game type and retrieves other player details.
     *
     * @param scanner The Scanner object for user input.
     * @return A Player object initialized with the provided input.
     */
    private static Player getPlayerInput(Scanner scanner) {
        String gameType;
        while (true) {
            // Prompt the user to enter a valid game type
            System.out.print("Enter Type of Game (CONNECT4, TICTACTOE, CHECKERS): ");
            gameType = scanner.nextLine().toUpperCase();
            if (gameType.equals("CONNECT4") || gameType.equals("TICTACTOE") || gameType.equals("CHECKERS")) {
                break; // Exit loop if input is valid
            } else {
                System.out.println("Invalid game type. Please enter CONNECT4, TICTACTOE, or CHECKERS.");
            }
        }

        // Gather additional player details
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

        // Create and return a new Player object
        return new Player(gameType, playerID, elo, wins, losses, draws);
    }

    /**
     * Displays a sorted leaderboard for a specific game type.
     * Retrieves and prints the leaderboard details based on the game type.
     *
     * @param leaderboard The Leaderboard object containing the sorted player data.
     * @param gameType The type of game (e.g., CONNECT4, TICTACTOE, CHECKERS).
     */
    private static void displaySortedLeaderboard(Leaderboard leaderboard, String gameType) {
        // String[][] sortedLeaderboard = leaderboard.sortLeaderboard(gameType);
        // if (sortedLeaderboard.length == 0) {
        // System.out.println("No players available for the specified game type.");
        // } else {
        // System.out.println("\nSorted Leaderboard for " + gameType + ":");
        // System.out.printf("%-15s %-10s %-10s%n", "Player ID", "Elo", "Wins");
        // for (String[] playerData : sortedLeaderboard) {
        // System.out.printf("%-15s %-10s %-10s%n", playerData[0], playerData[1],
        // playerData[2]);
        // }
        // }
        // Retrieve the leaderboard based on the game type and display it
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
    // will have matchmaker example method implementation later on
}
