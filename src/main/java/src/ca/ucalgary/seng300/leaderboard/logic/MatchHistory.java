package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.HistoryPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchHistory;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MatchHistory implements IMatchHistory {

    private static final String FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/match_history.txt";
    private static final String USERS_PATH = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    private File file = new File(FILE_PATH);
    private int gameCount = 0;

    public void updateMatchHistory(HistoryStorage storage, String player) {

        int max = 0;
        int lineCount = 0;


        try {
            int userCount = FileManagement.countLinesInCSV(new File(USERS_PATH));

            if (userCount > 0) {
                max = userCount * 2;
            }
            lineCount = FileManagement.countLinesInTextFile(file);

            storage = FileManagement.fileReadingHistory(file);

            for (HistoryPlayer hp : storage.getPlayersHistory()) {
                String id = hp.getPlayerIDHistory();
                if (id.equals(player)) {    // checking if players have more than 2 recorded games in history
                    gameCount++;
                }
                System.out.println("game count: " + gameCount);
                if (gameCount >= 2) {
                    FileManagement.clearOtherGameHistory(storage, file, player);
                }
            }

            if (gameCount >= 2) {
                FileManagement.clearOtherGameHistory(storage, file, player);
            }




            FileManagement.fileWritingHistory(file, storage, player);


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
        int counter = 0;
        int size = 0;

        if (file.exists()) {
            storage = FileManagement.fileReadingHistory(file);
            size = storage.getPlayersHistory().size();

            for (HistoryPlayer histPlayer : storage.getPlayersHistory()) {
                String playerID = histPlayer.getPlayerIDHistory();
                if (playerID.equals(player)) {
                    history.add(histPlayer);
                    count++;
                }
            }

//            for (int i = size - 1; i >= 0; i--) {
//                String playerID = storage.getPlayersHistory().get(i).getPlayerIDHistory();
//                if (playerID.equals(player)) {
//                    history.add(storage.getPlayersHistory().get(i));
//                    count++;
//                }
//            }

            if (history.isEmpty()) {
                System.out.println("No match history is available.");
            }

            String[][] historyArr = new String[count][7];

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

            return historyArr;
        } else {
            System.out.println("[ERROR] File does not exist.");
            return new String[0][];
        }
    }
}
