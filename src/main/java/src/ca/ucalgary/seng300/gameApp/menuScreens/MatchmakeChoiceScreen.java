package src.ca.ucalgary.seng300.gameApp.menuScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

import java.io.File;

/**
 * Represents the Matchmaking Choice screen in the application.
 * Provides options for the user to either queue for a game or challenge another player.
 */
public class MatchmakeChoiceScreen implements IScreen {
    private Scene scene;

    /**
     * Constructs the MatchmakeChoiceScreen.
     *
     * @param stage      the primary stage of the application
     * @param controller the screen controller to manage navigation between screens
     * @param gameChoice the chosen game type for matchmaking
     * @param client     the client for handling user and network interactions
     */
    public MatchmakeChoiceScreen(Stage stage, ScreenController controller, int gameChoice, Client client) {
        // Title label
        Label matchmakeLabel = new Label("MATCHMAKING OPTIONS");
        matchmakeLabel.getStyleClass().add("title-label");

        // Buttons and Fields
        Button queueButton = new Button("Queue Game");
        queueButton.getStyleClass().add("button");
        queueButton.getStyleClass().add("submit-button");
        queueButton.setOnAction(e -> controller.showQueueScreen(gameChoice));

        // challenge label
        Label challengeLabel = new Label("Challenge Player:");
        challengeLabel.getStyleClass().add("search-label");

        // Text field for entering the name of the player to challenge
        TextField challengeField = new TextField();
        challengeField.getStyleClass().add("input-field");
        challengeField.setPromptText("Enter player's name...");

        // Button to send a challenge to the specified player
        Button challengeButton = new Button("Send Challenge");
        challengeButton.getStyleClass().add("button");
        challengeButton.getStyleClass().add("submit-button");
        challengeButton.setOnAction(e -> {
            String challengeName = challengeField.getText();

            boolean playerFound = true; // Flag to check if the player exists
            boolean enterOwnUser = false; // Flag to check if the user is challenging themselves

            // Load all player data from the storage file
            File accounts = new File(client.getStatPath());
            Storage allPlayers = FileManagement.fileReading(accounts);

            // Validate the input and check for the player
            if (!challengeName.isEmpty()) {
                for (Player player : allPlayers.getPlayers()) {
                    if (player.getPlayerID().equals(challengeName) && !player.getPlayerID().equals(client.getCurrentUsername())) {
                        playerFound = true;
                        break;
                    } else if (player.getPlayerID().equals(challengeName)) {
                        enterOwnUser = true;
                    } else {
                        playerFound = false;
                    }
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill out the field."); // Show error if the field is empty
            }
            // Handle invalid cases for challenges
            if (enterOwnUser) {
                showAlert(Alert.AlertType.ERROR, "Error", "You cannot challenge yourself. Try again.");
            } else if (!playerFound) {
                showAlert(Alert.AlertType.ERROR, "Error", "There is no user with that username. Try again.");
            } else {
                controller.showChallengePlayerScreen(challengeName, gameChoice); // Navigate to the challenge screen
            }
        });


        // Layout for the challenge player section
        HBox challengeLayout = new HBox(10, challengeLabel, challengeField, challengeButton);
        challengeLayout.setAlignment(Pos.CENTER);

        // Button to navigate back to the main game menu
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("exit-button");
        backButton.setOnAction(e -> controller.showGameMenu());
        
        // Layout for organizing all elements vertically
        VBox layout = new VBox(15, matchmakeLabel, queueButton, challengeLayout, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Root pane for the scene
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");
        
        // Create the scene for the matchmaking choice screen
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    /**
     * Displays an alert dialog with the given type, title, and message.
     *
     * @param alertType the type of alert (e.g., ERROR, INFORMATION)
     * @param title     the title of the alert
     * @param message   the message content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the scene for the Matchmaking Choice screen.
     *
     * @return the Scene object
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
