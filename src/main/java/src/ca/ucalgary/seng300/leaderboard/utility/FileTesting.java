package src.ca.ucalgary.seng300.leaderboard.utility;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;

import java.io.File;
import java.util.ArrayList;

//For now, useless, please refer to ExampleMain where I show how functionality works.

public class FileTesting {
    public static void main(String[] args) {
        Player player1 = new Player("CONNECT4","Lucas", 1200, 33, 11, 2);
        Player player2 = new Player("CONNECT4","Vova", 999, 53, 37, 11);
        Player player3 = new Player("TICTACTOE","Brie", 320, 12, 9, 4);

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
