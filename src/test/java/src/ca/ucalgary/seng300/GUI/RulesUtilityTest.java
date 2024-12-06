package src.ca.ucalgary.seng300.GUI;

import org.junit.Test;
import src.ca.ucalgary.seng300.gameApp.Utility.RulesUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

public class RulesUtilityTest {

    @Test
    public void testGetRulesWithValidFile() throws Exception {
        // Arrange: Create a temporary file and write rules content
        File tempFile = File.createTempFile("rules", ".txt");
        tempFile.deleteOnExit(); // Ensure the file is deleted after the test
        String expectedContent = "These are the game rules.";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(expectedContent);
        }

        // Act: Call getRules with the path of the temporary file
        String actualContent = RulesUtility.getRules(tempFile.getAbsolutePath());

        // Assert: Verify that the content matches
        assertEquals("Content read from the file should match the expected content", expectedContent, actualContent);
    }

    @Test
    public void testGetRulesWithInvalidFilePath() {
        // Arrange: Use an invalid file path
        String invalidFilePath = "nonexistent_file.txt";

        // Act: Call getRules with an invalid file path
        String actualContent = RulesUtility.getRules(invalidFilePath);

        // Assert: Verify that the error message is returned
        assertEquals("Error while reading or finding file", actualContent);
    }

}