package src.ca.ucalgary.seng300.leaderboard.utility;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.data.GameType;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


//Example main to show file reading, writing, and storage and Player are all functional for future use cases
public class ExampleMain {

    private static final String FILE_PATH = "players_data.csv";

    public static void main(String[] args) {
        File file = new File(FILE_PATH);
        Storage storage;

        // checks if file is even there
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

        while (continueRunning) {
            // shows you your options
            System.out.println("\nSelect one of the following options below:");
            System.out.println("1. Show existing player data");
            System.out.println("2. Add a new player");
            System.out.println("3. Exit");

            System.out.print("Enter your choice (1, 2, or 3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // first option: display current data
                    System.out.println("\nCurrent Player Data:");
                    displayPlayerData(storage);
                    break;

                case "2":
                    // second option: get new player details and add to storage
                    Player newPlayer = getPlayerInput(scanner);
                    storage.addPlayer(newPlayer);

                    // save updated data back to the file
                    FileManagement.fileWriting(storage, file);
                    System.out.println("Player added and data saved successfully.");
                    break;

                case "3":
                    // final option: exit program
                    continueRunning = false;
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    // shows player data
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

    // gets player input
    private static Player getPlayerInput(Scanner scanner) {
        String gameType;
        while (true) {
            System.out.println("Enter Type of Game(CONNECT4, TICTACTOE, CHECKERS): ");
            gameType = scanner.nextLine().toUpperCase();
            if (gameType.equals("CONNECT4")) {
                break;
            } else if (gameType.equals("TICTACTOE")) {
                break;
            } else if (gameType.equals("CHECKERS")) {
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

        System.out.println("Enter Draws: ");
        int draws = Integer.parseInt(scanner.nextLine());

        return new Player(gameType, playerID, elo, wins, losses, draws);
    }
}