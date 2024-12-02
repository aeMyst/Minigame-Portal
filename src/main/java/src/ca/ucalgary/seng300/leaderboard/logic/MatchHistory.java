package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.HistoryPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchHistory;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MatchHistory implements IMatchHistory {

    private static final String FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/match_history.txt";
    private static final String USERS_PATH = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    private File file = new File(FILE_PATH);

    public void updateMatchHistory(HistoryStorage storage, String player) {

        int max = 0;
        int lineCount = 0;
        int gameCount = 0;

        try {
            FileManagement.fileWritingHistory(file, storage);


        } catch (Exception e) {
            System.out.println("Error fetching match history");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String[][] getMatchHistory(String player) {
        HistoryStorage storage;
        List<HistoryPlayer> history = new ArrayList<>();
        int count = 0;
        int size;

        if (file.exists()) {
            storage = FileManagement.fileReadingHistory(file);
            size = storage.getPlayersHistory().size();


            ListIterator<HistoryPlayer> itr = storage.getPlayersHistory().listIterator(size);
            while (itr.hasPrevious()) {
                HistoryPlayer current = itr.previous();
                String currentID = current.getPlayerIDHistory();
                if (currentID.equals(player) && count < 2) {
                    history.add(current);
                    count++;
                } else if (currentID.equals(player) && count >= 2) {
                    itr.remove();
                }
            }

            if (history.isEmpty()) {
                System.out.println("No match history is available.");
            }

            String[][] historyArr = new String[2][7];
            int counter = 0;

            for (HistoryPlayer hp : history) {
                historyArr[counter][0] = hp.getGameTypeHistory();
                historyArr[counter][1] = hp.getPlayerIDHistory();
                historyArr[counter][2] = hp.getWinnerString();
                historyArr[counter][3] = hp.getLoserString();
                historyArr[counter][4] = String.valueOf(hp.getEloGained());
                historyArr[counter][5] = String.valueOf(hp.getEloLost());
                historyArr[counter][6] = hp.getDate();

                counter++;

            }

            FileManagement.fileWritingHistoryNewFile(file, storage);


            return historyArr;
        } else {
            System.out.println("[ERROR] File does not exist.");
            return new String[0][];
        }
    }
}
