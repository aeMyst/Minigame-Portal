package src.ca.ucalgary.seng300.gameApp.Utility;

import src.ca.ucalgary.seng300.network.Client;

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
    public static List<String> loadTipsFromFile(Client client, int gameType) {
        String fileName = client.getTipsPath(gameType);

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
}
