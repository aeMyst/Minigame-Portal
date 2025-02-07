package src.ca.ucalgary.seng300.gameApp.Utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import src.ca.ucalgary.seng300.network.Client;

public class ChatUtility {

    /**
     * returns a path to a file in DB
     *
     * @param client passing the server to access its method
     * @param utilityType the type to get the correct database file
     * @return string that paths to a directory
     */
    public static String getFilePath(Client client, int utilityType) {
        if (utilityType == 0) {
            return client.getChatElements(utilityType);
        } else if (utilityType == 1) {
            return client.getChatElements(utilityType);
        } else {
            return null; // if no path found return null
        }
    }

    /**
     * Reads the contents of a file and returns them as a List of Strings.
     *
     * @param filePath The path to the file.
     * @return A list of lines from the file.
     */
    public static String[] readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
            return lines.toArray(new String[0]); // Convert List to Array
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0]; // Return an empty array if an error occurs
        }
    }


    /**
     * Filters swear words and hateful messages from the input message.
     *
     * @param message The input message to filter.
     * @return A filtered message with banned words replaced by asterisks.
     */
    public static String filterMessage(String message, Client client) {
        String[] bannedWords = readFile(getFilePath(client, 0)); // Use readFile

        for (String word : bannedWords) {
            String replacement = "*".repeat(word.length());
            message = message.replaceAll("(?i)" + word, replacement); // Case-insensitive
        }
        return message;
    }


    /**
     * Shows an emoji menu that allows the user to select emojis to append to the chat input field.
     *
     * @param chatInput  The TextField to which selected emojis will be appended.
     * @param parentStage The parent Stage to which the emoji menu belongs.
     */
    public static Stage showEmojiMenu(TextField chatInput, Stage parentStage, Runnable onClose, Client client) {
        Stage emojiStage = new Stage();
        emojiStage.setTitle("Select an Emoji");

        String[] emojis = readFile(getFilePath(client, 1)); // Use readFile

        GridPane emojiGrid = new GridPane();
        emojiGrid.setAlignment(Pos.CENTER);
        emojiGrid.setHgap(10);
        emojiGrid.setVgap(10);
        emojiGrid.setPadding(new Insets(10));

        for (int i = 0; i < emojis.length; i++) {
            Button emojiButton = new Button(emojis[i]);
            emojiButton.getStyleClass().add("emoji-button");
            emojiButton.setOnAction(e -> {
                chatInput.appendText(emojiButton.getText());
                emojiStage.close(); // Close menu after selection
            });
            //chatgpt generated
            emojiGrid.add(emojiButton, i % 3, i / 3);
        }

        // borderpane to style main screen
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(emojiGrid);
        rootPane.getStyleClass().add("root-pane");

        // set the screen
        Scene emojiScene = new Scene(rootPane, 300, 200);
        emojiScene.getStylesheets().add((ChatUtility.class.getClassLoader().getResource("styles.css").toExternalForm()));
        emojiStage.setScene(emojiScene);
        emojiStage.initOwner(parentStage);

        //
        // ChatGPT generated: learnt about how to run the onClose action when the emojiStage is hidden
        //
        emojiStage.setOnHidden(event -> onClose.run());

        emojiStage.show();

        return emojiStage;
    }
}
