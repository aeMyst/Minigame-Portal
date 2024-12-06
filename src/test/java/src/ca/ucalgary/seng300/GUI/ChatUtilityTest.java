package src.ca.ucalgary.seng300.GUI;

import javafx.application.Platform;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import src.ca.ucalgary.seng300.gameApp.Utility.ChatUtility;
import src.ca.ucalgary.seng300.network.Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ChatUtilityTest {

    @Test
    public void testGetFilePathWithValidUtilityType0() {
        // Arrange: Create a mock Client
        Client mockClient = new Client() {
            @Override
            public String getChatElements(int utilityType) {
                if (utilityType == 0) {
                    return "path/to/bannedWords.txt";
                }
                return null;
            }
        };

        // Act: Call getFilePath with utilityType 0
        String filePath = ChatUtility.getFilePath(mockClient, 0);

        // Assert: Verify the correct path is returned
        assertEquals("path/to/bannedWords.txt", filePath);
    }

    @Test
    public void testGetFilePathWithValidUtilityType1() {
        // Arrange: Create a mock Client
        Client mockClient = new Client() {
            @Override
            public String getChatElements(int utilityType) {
                if (utilityType == 1) {
                    return "path/to/emojis.txt";
                }
                return null;
            }
        };

        // Act: Call getFilePath with utilityType 1
        String filePath = ChatUtility.getFilePath(mockClient, 1);

        // Assert: Verify the correct path is returned
        assertEquals("path/to/emojis.txt", filePath);
    }

    @Test
    public void testGetFilePathWithInvalidUtilityType() {
        // Arrange: Create a mock Client
        Client mockClient = new Client() {
            @Override
            public String getChatElements(int utilityType) {
                return null; // Invalid utility type returns null
            }
        };

        // Act: Call getFilePath with an invalid utilityType (e.g., 99)
        String filePath = ChatUtility.getFilePath(mockClient, 99);

        // Assert: Verify null is returned for an invalid utilityType
        assertEquals(null, filePath);
    }

    @Test
    public void testReadFileValidFile() throws Exception {
        // Arrange: Create a temporary file with some lines
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit();
        String[] expectedLines = {"hello", "world", "test"};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String line : expectedLines) {
                writer.write(line);
                writer.newLine();
            }
        }

        // Act: Read the file using ChatUtility.readFile
        String[] actualLines = ChatUtility.readFile(tempFile.getAbsolutePath());

        // Assert: Verify the file contents match
        assertArrayEquals("The lines read from the file should match the expected lines", expectedLines, actualLines);
    }

    @Test
    public void testReadFileInvalidFile() {
        // Arrange: Use a nonexistent file path
        String invalidFilePath = "nonexistent_file.txt";

        // Act: Call ChatUtility.readFile with an invalid path
        String[] actualLines = ChatUtility.readFile(invalidFilePath);

        // Assert: Verify that an empty array is returned
        assertArrayEquals("An empty array should be returned for an invalid file path", new String[0], actualLines);
    }

    @Test
    public void testFilterMessageWithBannedWords() throws Exception {
        // Arrange: Create a temporary file with banned words
        File tempFile = File.createTempFile("bannedWords", ".txt");
        tempFile.deleteOnExit();
        String[] bannedWords = {"bad", "ugly"};
        String inputMessage = "This is a bad and ugly world.";
        String expectedMessage = "This is a *** and **** world.";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String word : bannedWords) {
                writer.write(word);
                writer.newLine();
            }
        }

        // Mock the Client to return the banned words file
        Client mockClient = new Client() {
            @Override
            public String getChatElements(int utilityType) {
                if (utilityType == 0) {
                    return tempFile.getAbsolutePath();
                }
                return null;
            }
        };

        // Act: Filter the message using ChatUtility.filterMessage
        String actualMessage = ChatUtility.filterMessage(inputMessage, mockClient);

        // Assert: Verify the filtered message matches the expected output
        assertEquals("The filtered message should replace banned words with asterisks", expectedMessage, actualMessage);
    }
}
