package src.ca.ucalgary.seng300.gameApp.Utility;

import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * rules Utility class
 */
public class RulesUtility {

    /**
     * Shows an emoji menu that allows the user to select emojis to append to the chat input field.
     *
     * @param filePath takes in a filePath for the file
     * @return a string (the rules for game)
     */
    public static String getRules(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (Exception e) {
            return "Error while reading or finding file";
        }
    }
}
