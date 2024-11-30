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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

import java.io.File;

public class MatchmakeChoiceScreen implements IScreen {
    private Scene scene;
    private int gameChoice;

    public MatchmakeChoiceScreen(Stage stage, ScreenController controller, int gameChoice, Client client) {
        this.gameChoice = gameChoice;

        Label matchmakeLabel = new Label("Matchmaking Choice");
        matchmakeLabel.setFont(new Font("Arial", 24));
        matchmakeLabel.setTextFill(Color.DARKBLUE);

        Button queueButton = new Button("Queue Game");
        queueButton.setFont(new Font("Arial", 16));
        queueButton.setPrefWidth(200);
        queueButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        queueButton.setOnAction(e -> controller.showQueueScreen(gameChoice));

        Label challengeLabel = new Label("Challenge Player");
        challengeLabel.setFont(new Font("Arial", 16));

        TextField challengeField = new TextField();
        challengeField.setPrefWidth(350); // Increase width
        challengeField.setPromptText("Enter player's name...");

        Button challengeButton = new Button("Send Challenge");
        challengeButton.setFont(new Font("Arial", 16));
        challengeButton.setPrefWidth(200);
        challengeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        challengeButton.setOnAction(e -> {
            String challengeName = challengeField.getText();

            boolean playerFound = true;
            boolean enterOwnUser = false;

            File accounts = new File(client.getStatPath());
            Storage allPlayers = FileManagement.fileReading(accounts);

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
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill out the field.");
            }

            if (enterOwnUser) {
                showAlert(Alert.AlertType.ERROR, "Error", "You can not challenge yourself. Try again.");
            } else if (!playerFound) {
                showAlert(Alert.AlertType.ERROR, "Error", "There is no user with that username. Try again.");
            } else {
                controller.showChallengePlayerScreen(challengeName, gameChoice);
            }
                });

        HBox challengeLayout = new HBox(10, challengeLabel, challengeField, challengeButton);
        challengeLayout.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showGameMenu());

        VBox layout = new VBox(15, matchmakeLabel, queueButton, challengeLayout, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);

        scene = new Scene(rootPane, 1280, 900);
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
