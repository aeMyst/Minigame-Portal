package src.ca.ucalgary.seng300.gameApp.menuScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

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
            if (!challengeName.isEmpty()) {
                controller.showQueueScreen(gameChoice);
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

    @Override
    public Scene getScene() {
        return scene;
    }
}
