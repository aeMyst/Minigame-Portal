package src.ca.ucalgary.seng300.leaderboard.utility;
import src.ca.ucalgary.seng300.leaderboard.data.GameType;
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

public class FileManagement {
    public static Storage fileReading(File file) {
        ArrayList<Player> players = new ArrayList<>();
        Storage storage = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(","); //for reading csv files blah,13,13,11
                if (parts.length == 6) {
                    Player player = new Player(GameType.valueOf(parts[0]), parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
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
                String[] player = {players.get(i).getPlayerID(), String.valueOf(players.get(i).getElo()), String.valueOf(players.get(i).getWins()), String.valueOf(players.get(i).getLosses())};
                writerBuffer.write(String.join(",", player));
                writerBuffer.newLine();
            }

        } catch (IOException error) {
            System.out.println("Error writing file");
            error.printStackTrace();
        }
    }
}
