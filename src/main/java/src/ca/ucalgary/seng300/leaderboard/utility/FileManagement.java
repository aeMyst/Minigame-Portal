package src.ca.ucalgary.seng300.leaderboard.utility;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;


public class FileManagement {
    /**
     * Reads player data from a specified CSV file and converts it into a Storage object.
     * Each line in the file is parsed into a Player object if the format is valid.
     *
     * @param file The file object representing the CSV file to be read.
     * @return A Storage object containing the list of players, or null if an error occurs.
     */
    public static Storage fileReading(File file) {
        ArrayList<Player> players = new ArrayList<>();
        Storage storage = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(","); // Parse CSV format (e.g., "type,ID,elo,wins,losses,ties")
                if (parts.length == 6) {
                    Player player = new Player(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    players.add(player); // Add player to the list
                } else {
                    throw new IllegalArgumentException("Invalid file format");
                }
                line = reader.readLine(); // Read the next line
            }
            Storage newStorage = new Storage(players); // Create a Storage object with the players
            storage = newStorage;
            reader.close();
        } catch (IOException error) {
            if (error instanceof FileNotFoundException) {
                System.out.println("File not found"); // Handle missing file
            } else {
                System.out.println("Error reading file"); // Handle other IO errors
            }
        }
        return storage;
    }

    /**
     * Writes player data from a Storage object to a specified CSV file.
     * Each player's attributes are serialized into a CSV format and saved to the file.
     *
     * @param storage The Storage object containing the list of players to be written.
     * @param file The file object representing the destination CSV file.
     */
    public static void fileWriting(Storage storage, File file) {
        ArrayList<Player> players = storage.getPlayers();

        try (BufferedWriter writerBuffer = new BufferedWriter(new FileWriter(file))) {
            // Convert Player attributes into CSV format
            for (int i = 0; i < players.size(); i++) {
                String[] player = {players.get(i).getGameType(), players.get(i).getPlayerID(), String.valueOf(players.get(i).getElo()), String.valueOf(players.get(i).getWins()), String.valueOf(players.get(i).getLosses()), String.valueOf(players.get(i).getTies())};
                writerBuffer.write(String.join(",", player)); // Write the player's data to the file
                writerBuffer.newLine(); // Add a new line
            }

        } catch (IOException error) {
            System.out.println("Error writing file"); // Handle errors during file writing
            error.printStackTrace();
        }
    }

    public static HistoryStorage fileReadingHistory(File file) {
        ArrayList<HistoryPlayer> players = new ArrayList<>();
        HistoryStorage storage = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    HistoryPlayer player = new HistoryPlayer(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[6]);
                    players.add(player);
                } else {
                    throw new IllegalArgumentException("Invalid file format");
                }
                line = reader.readLine();
            }
            HistoryStorage newStorage = new HistoryStorage(players);
            storage = newStorage;
        } catch (IOException error) {
            if (error instanceof FileNotFoundException) {
                System.out.println("File not found");
            } else {
                System.out.println("Error reading file");
            }
        }
        return storage;
    }

    public static void fileWritingHistory(File file, HistoryStorage storage) {

        try (BufferedWriter writerBuffer = new BufferedWriter(new FileWriter(file,true))) {
            //write info to the file in the format of "gametype, player_id, winner, loser, eloGained, eloLost"
            for (HistoryPlayer hp : storage.getPlayersHistory()) {
                String[] player = {hp.getGameTypeHistory(), hp.getPlayerIDHistory(), hp.getWinnerString(), hp.getLoserString(), String.valueOf(hp.getEloGained()), String.valueOf(hp.getEloLost()), hp.getDate()};
                writerBuffer.write(String.join(",", player));
                writerBuffer.newLine();
            }

        } catch (IOException error) {
            System.out.println("Error writing file");
            error.printStackTrace();
        }
    }

    public static void fileWritingHistoryNewFile(File file, HistoryStorage storage) {
        try (BufferedWriter writerBuffer = new BufferedWriter(new FileWriter(file))) {
            //write info to the file in the format of "gametype, player_id, winner, loser, eloGained, eloLost"
            for (HistoryPlayer hp : storage.getPlayersHistory()) {
                String[] player = {hp.getGameTypeHistory(), hp.getPlayerIDHistory(), hp.getWinnerString(), hp.getLoserString(), String.valueOf(hp.getEloGained()), String.valueOf(hp.getEloLost()), hp.getDate()};
                writerBuffer.write(String.join(",", player));
                writerBuffer.newLine();
            }

        } catch (IOException error) {
            System.out.println("Error writing file");
            error.printStackTrace();
        }
    }

    public static int countLinesInCSV(File file) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.matches("^,+$")) {
                    count++;
                }
            }
            return count;
        } catch (IOException error) {
            System.out.println("Error reading file");
            error.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates player profiles in a specified CSV file based on a list of players.
     * Matches existing rows in the CSV with the players in the list and updates their attributes.
     * If a player's attributes match a row, the row is updated with the new data.
     *
     * @param filePath The file path of the CSV file to be updated.
     * @param players An ArrayList of Player objects containing the updated data.
     */
    public static void updateProfilesInCsv(String filePath, ArrayList<Player> players) {
        List<String[]> allRows = new ArrayList<>();

        // Step 1: Read existing CSV rows
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allRows.add(line.split(",")); // Split CSV rows into columns
            }
        } catch (IOException e) {
            System.err.println("Error reading profiles.csv: " + e.getMessage());
            return;
        }

        // Step 2: Update matching rows
        for (Player player : players) {
            for (String[] row : allRows) {
                if (row[0].equals(player.getGameType()) && row[1].equals(player.getPlayerID())) {
                    // Updates row with the player's new data
                    row[2] = String.valueOf(player.getElo());
                    row[3] = String.valueOf(player.getWins());
                    row[4] = String.valueOf(player.getLosses());
                    row[5] = String.valueOf(player.getTies());
                    break; // Move to the next player
                }
            }
        }

        // Step 3: Write updated rows back to the CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : allRows) {
                writer.write(String.join(",", row)); // Write each row back to the file
                writer.newLine(); // Add a new line
            }
            System.out.println("Profiles successfully updated in CSV."); // Confirm success
        } catch (IOException e) {
            System.err.println("Error writing to profiles.csv: " + e.getMessage()); // Handle writing errors
        }
    }

}
