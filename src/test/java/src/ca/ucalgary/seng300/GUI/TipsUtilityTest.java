package src.ca.ucalgary.seng300.GUI;

import org.junit.Test;
import src.ca.ucalgary.seng300.gameApp.Utility.TipsUtility;
import src.ca.ucalgary.seng300.network.Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TipsUtilityTest {

    @Test
    public void testLoadTipsFromFileValidFile() throws Exception {
        // Arrange: Create a temporary file with tips for a specific game
        File tempFile = File.createTempFile("game_tips", ".txt");
        tempFile.deleteOnExit(); // Ensure the file is deleted after the test

        List<String> expectedTips = new ArrayList<>();
        expectedTips.add("Tip 1: Always think ahead.");
        expectedTips.add("Tip 2: Control the center.");
        expectedTips.add("Tip 3: Watch your opponent's moves.");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String tip : expectedTips) {
                writer.write(tip);
                writer.newLine();
            }
        }

        // Mock the client to return the temporary file's path
        Client mockClient = new Client() {
            @Override
            public String getTipsPath(int gameType) {
                return tempFile.getAbsolutePath();
            }
        };

        // Act: Call loadTipsFromFile with the mocked client
        List<String> actualTips = TipsUtility.loadTipsFromFile(mockClient, 0);

        // Assert: Verify the tips loaded match the expected tips
        assertEquals("Tips loaded from the file should match the expected tips", expectedTips, actualTips);
    }

    @Test
    public void testLoadTipsFromFileInvalidFile() {
        // Arrange: Use an invalid file path
        Client mockClient = new Client() {
            @Override
            public String getTipsPath(int gameType) {
                return "nonexistent_file.txt"; // Invalid file path
            }
        };

        // Act: Call loadTipsFromFile with the mocked client
        List<String> actualTips = TipsUtility.loadTipsFromFile(mockClient, 0);

        // Assert: Verify the error message is added to the tips list
        assertEquals("Error loading tips. Please try again!", actualTips.get(0));
        assertEquals("The list should contain only one element", 1, actualTips.size());
    }
}