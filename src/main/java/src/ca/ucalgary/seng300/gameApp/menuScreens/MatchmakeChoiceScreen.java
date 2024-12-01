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
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class MatchmakeChoiceScreen implements IScreen {
    private Scene scene;

    public MatchmakeChoiceScreen(Stage stage, ScreenController controller, int gameChoice, Client client) {
        // Title label
        Label matchmakeLabel = new Label("MATCHMAKING OPTIONS");
        matchmakeLabel.getStyleClass().add("title-label");

        // Buttons and Fields
        Button queueButton = new Button("Queue Game");
        queueButton.getStyleClass().add("button");
        queueButton.getStyleClass().add("submit-button");
        queueButton.setOnAction(e -> controller.showQueueScreen(gameChoice));

        Label challengeLabel = new Label("Challenge Player:");
        challengeLabel.getStyleClass().add("search-label");

        TextField challengeField = new TextField();
        challengeField.getStyleClass().add("input-field");
        challengeField.setPromptText("Enter player's name...");

        Button challengeButton = new Button("Send Challenge");
        challengeButton.getStyleClass().add("button");
        challengeButton.getStyleClass().add("submit-button");
        challengeButton.setOnAction(e -> {
            String challengeName = challengeField.getText();
            if (challengeName.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill out the field.");
                return;
            }
            // Further challenge logic...
        });

        HBox challengeLayout = new HBox(10, challengeLabel, challengeField, challengeButton);
        challengeLayout.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("exit-button");
        backButton.setOnAction(e -> controller.showGameMenu());

        // Layout
        VBox layout = new VBox(15, matchmakeLabel, queueButton, challengeLayout, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");

        // Scene
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
