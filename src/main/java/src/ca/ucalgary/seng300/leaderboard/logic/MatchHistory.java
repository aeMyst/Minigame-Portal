package src.ca.ucalgary.seng300.leaderboard.logic;

import src.ca.ucalgary.seng300.leaderboard.data.HistoryPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;
import src.ca.ucalgary.seng300.leaderboard.interfaces.IMatchHistory;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MatchHistory implements IMatchHistory {

    // File path for match history (converted to file)
    private static final String FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/match_history.txt";
    private File file = new File(FILE_PATH);

    /**
     * Updates the match history by writing the provided storage data to a file.
     *
     * @param storage The storage instance containing match history data.
     * @param player  The ID of the player whose match history is being updated.
     */
    public void updateMatchHistory(HistoryStorage storage, String player) {
        try {
            // Write the match history to the file
            FileManagement.fileWritingHistory(file, storage);
        } catch (Exception e) {
            System.out.println("Error fetching match history");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the match history for a specific player.
     *
     * @param player The ID of the player whose match history is being retrieved.
     * @return A 2D array containing the match history data.
     */
    public String[][] getMatchHistory(String player) {
        HistoryStorage storage;
        List<HistoryPlayer> history = new ArrayList<>();
        int count = 0;
        int size;

        if (file.exists()) {
            // Read the match history from the file
            storage = FileManagement.fileReadingHistory(file);
            size = storage.getPlayersHistory().size();
            ListIterator<HistoryPlayer> itr = storage.getPlayersHistory().listIterator(size);

            // Iterate through the match history in reverse order
            while (itr.hasPrevious()) {
                HistoryPlayer current = itr.previous();
                String currentID = current.getPlayerIDHistory();
                if (currentID.equals(player) && count < 2) {
                    // Add the match to the history list if it belongs to the player and the count is less than 2
                    history.add(current);
                    count++;
                } else if (currentID.equals(player) && count >= 2) {
                    // Remove older matches beyond the most recent two
                    itr.remove();
                }
            }

            if (history.isEmpty()) {
                System.out.println("No match history is available.");
                throw new AssertionError("No match history is available.");
            }

            // Convert the history list to a 2D array
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

            // Write the updated match history back to the file
            FileManagement.fileWritingHistoryNewFile(file, storage);

            return historyArr;
        } else {
            System.out.println("[ERROR] File does not exist.");
            return new String[0][];
        }
    }
    public void clearMatchHistory() {
        try {
            // Clear the contents of the file by writing an empty HistoryStorage object to the file
            HistoryStorage emptyStorage = new HistoryStorage(); // Create an empty HistoryStorage
            FileManagement.fileWritingHistoryNewFile(file, emptyStorage); // Write the empty history to the file
            System.out.println("Match history has been cleared.");
        } catch (Exception e) {
            System.out.println("Error clearing match history");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}