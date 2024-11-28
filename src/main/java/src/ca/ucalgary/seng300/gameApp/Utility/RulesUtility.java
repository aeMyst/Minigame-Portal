package src.ca.ucalgary.seng300.gameApp.Utility;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RulesUtility {

    public static String getRules(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (Exception e) {
            return "Error while reading or finding file";
        }
    }


}
