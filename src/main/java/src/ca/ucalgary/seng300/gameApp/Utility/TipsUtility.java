package src.ca.ucalgary.seng300.gameApp.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TipsUtility {

    /**
     * Loads game-specific tips from a file based on the game type.
     *
     * @param gameType The game type (0: Tic-Tac-Toe, 1: Connect4, 2: Checkers).
     * @return A list of tips for the selected game.
     */
    public static List<String> loadTipsFromFile(int gameType) {
        String fileName = getFileName(gameType);

        List<String> tips = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tips.add(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
            tips.add("Error loading tips. Please try again!");
        }
        return tips;
    }

    /**
     * Gets the file name for the tips file based on the game type.
     *
     * @param gameType The game type (0: Tic-Tac-Toe, 1: Connect4, 2: Checkers).
     * @return The file path to the tips file.
     */
    private static String getFileName(int gameType) {
        switch (gameType) {
            case 0:
                return "src/main/java/src/ca/ucalgary/seng300/database/tictactoe_tips.txt";
            case 1:
                return "src/main/java/src/ca/ucalgary/seng300/database/connect4_tips.txt";
            case 2:
                return "src/main/java/src/ca/ucalgary/seng300/database/checkers_tips.txt";
            default:
                return "Have fun and play your best!"; // Default tips file
        }
    }
}
