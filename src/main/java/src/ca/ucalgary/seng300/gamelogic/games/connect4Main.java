package src.ca.ucalgary.seng300.gamelogic.games;

import src.ca.ucalgary.seng300.gamelogic.games.Connect4.Connect4Game;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.logic.MatchMaker;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.io.File;

public class connect4Main {
    private static final String FILE_PATH = "players_data.csv";

    public static void main(String[] args) {
        File file = new File(FILE_PATH);
        Storage storage = FileManagement.fileReading(file);

        if (storage == null) {
            storage = new Storage();
            System.out.println("no players");
        }

        MatchMaker matchMaker = new MatchMaker(storage);

        for (Player player : storage.getPlayers()) {
            if (player.getGameType().equals("CONNECT4")) {
                matchMaker.addPlayerToQueue(player);
            }
        }
        matchMaker.findMatch();

        FileManagement.fileWriting(storage, file);
        System.out.println("player data saved");
    }
}
