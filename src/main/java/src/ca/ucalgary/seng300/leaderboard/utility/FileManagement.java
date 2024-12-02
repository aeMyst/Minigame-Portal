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
    public static Storage fileReading(File file) {
        ArrayList<Player> players = new ArrayList<>();
        Storage storage = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(","); //for reading csv files blah,13,13,11
                if (parts.length == 6) {
                    Player player = new Player(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    players.add(player);
                } else {
                    throw new IllegalArgumentException("Invalid file format");
                }
                line = reader.readLine();
            }
            Storage newStorage = new Storage(players);
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

    public static void fileWriting(Storage storage, File file) {
        ArrayList<Player> players = storage.getPlayers();

        try (BufferedWriter writerBuffer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < players.size(); i++) { //write info to the file in the format of "type of game, playerID, elo, wins, losses, ties"
                String[] player = {players.get(i).getGameType(), players.get(i).getPlayerID(), String.valueOf(players.get(i).getElo()), String.valueOf(players.get(i).getWins()), String.valueOf(players.get(i).getLosses()), String.valueOf(players.get(i).getTies())};
                writerBuffer.write(String.join(",", player));
                writerBuffer.newLine();
            }

        } catch (IOException error) {
            System.out.println("Error writing file");
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

    public static void fileWritingHistory(File file, HistoryStorage storage, String player) {

        try (BufferedWriter writerBuffer = new BufferedWriter(new FileWriter(file, true))) {
            int count = 0;
            for (HistoryPlayer hp : storage.getPlayersHistory()) {
                String id = hp.getPlayerIDHistory();
                if (id.equals(player)) {    // checking if players have more than 2 recorded games in history
                    count++;
                }
            }
            if (count >= 2) {
                clearOtherGameHistory(storage, file, player);
            }
            //write info to the file in the format of "gametype, player_id, winner, loser, eloGained, eloLost"
            for (HistoryPlayer hp : storage.getPlayersHistory()) {
                String[] addPlayer = {hp.getGameTypeHistory(), hp.getPlayerIDHistory(), hp.getWinnerString(), hp.getLoserString(), String.valueOf(hp.getEloGained()), String.valueOf(hp.getEloLost()), hp.getDate()};
                writerBuffer.write(String.join(",", addPlayer));
                writerBuffer.newLine();
            }

        } catch (IOException error) {
            System.out.println("Error writing file");
            error.printStackTrace();
        }
    }

    public static void clearOtherGameHistory(HistoryStorage storage, File file, String player) {
        List<String> keepLines = new ArrayList<>();
        String recentGame = null;
        ArrayList<HistoryPlayer> arrList = storage.getPlayersHistory();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (HistoryPlayer hp : storage.getPlayersHistory()) {
                    System.out.println("type: " + hp.getGameTypeHistory() + ", id: " + hp.getPlayerIDHistory());
                }
                if (line.contains("," + player + ",")) {
                    recentGame = line;

                } else {
                    keepLines.add(line);
                }
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                System.out.println("File not found");
            } else {
                System.out.println("Error reading file");
            }
        }

        if (recentGame != null) {
            keepLines.add(recentGame);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String l : keepLines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file");
            e.printStackTrace();
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

    public static int countLinesInTextFile(File file) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
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

    public static void updateProfilesInCsv(String filePath, ArrayList<Player> players) {
        List<String[]> allRows = new ArrayList<>();

        // Step 1: Read existing CSV rows
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allRows.add(line.split(",")); // Split CSV into columns
            }
        } catch (IOException e) {
            System.err.println("Error reading profiles.csv: " + e.getMessage());
            return;
        }

        // Step 2: Update matching rows
        for (Player player : players) {
            for (String[] row : allRows) {
                if (row[0].equals(player.getGameType()) && row[1].equals(player.getPlayerID())) {
                    // Replace row with updated player data
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
                writer.write(String.join(",", row));
                writer.newLine();
            }
            System.out.println("Profiles successfully updated in CSV.");
        } catch (IOException e) {
            System.err.println("Error writing to profiles.csv: " + e.getMessage());
        }
    }

}
