package src.ca.ucalgary.seng300.leaderboard.utility;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;

import java.io.File;
import java.util.ArrayList;

public class FileTesting {
    public static void main(String[] args) {
        Player player1 = new Player("Lucas", 1200, 33, 11);
        Player player2 = new Player("Vova", 999, 53, 37);
        Player player3 = new Player("Brie", 320, 12, 9);

        Storage storage = new Storage();
        storage.addPlayer(player1);
        storage.addPlayer(player2);
        storage.addPlayer(player3);

        File testFile = new File("players_test.csv");
        FileManagement.fileWriting(storage, testFile);

        Storage loadedStorage = FileManagement.fileReading(testFile);
        ArrayList<Player> loadedPlayers = loadedStorage.getPlayers();

        System.out.println("Existing Players:");
        for (Player player : loadedPlayers) {
            System.out.println(player);
        }
    }
}
