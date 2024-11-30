package src.ca.ucalgary.seng300.leaderboard.utility;
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
            reader.close();
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

    public static void updateProfilesInCsv(String filePath, ArrayList<Player> matchPlayers) {
        List<Player> allPlayers = new ArrayList<>();

        // Step 1: Read existing CSV rows
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allPlayers.add(Player.fromCsv(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading profiles.csv: " + e.getMessage());
            return;
        }

        // Step 2: Update matching rows
        for (Player matchPlayer : matchPlayers) {
            // if the id of matchPlayer & gametype of matchPlayer is not in allPlayers, add it
            boolean found = false;
            for (Player player : allPlayers) {
                if (matchPlayer.getGameType().equals(player.getGameType()) && matchPlayer.getPlayerID().equals(player.getPlayerID())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                allPlayers.add(matchPlayer);
            }
            for (Player player : allPlayers) {
                if (matchPlayer.getGameType().equals(player.getGameType()) && matchPlayer.getPlayerID().equals(player.getPlayerID())) {
                    player.setElo(player.getElo());
                    player.setWins(player.getWins());
                    player.setLosses(player.getLosses());
                    player.setTies(player.getTies());
                    break; // Move to the next player
                }
            }
        }


        // Step 3: Write updated rows back to the CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Player player : allPlayers) {
                writer.write(player.toCsv());
                writer.newLine();
            }
            System.out.println("Profiles successfully updated in CSV.");
        } catch (IOException e) {
            System.err.println("Error writing to profiles.csv: " + e.getMessage());
        }
    }

}
