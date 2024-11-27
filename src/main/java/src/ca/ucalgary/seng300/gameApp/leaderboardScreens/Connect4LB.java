package src.ca.ucalgary.seng300.gameApp.leaderboardScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.IScreen;

public class Connect4LB implements IScreen {
    private Scene scene;

    public Connect4LB(Stage stage, LeaderboardController controller) {
        // Title Label
        Label titleLabel = createLabel("Connect4 Leaderboard", 24, Pos.CENTER);
        titleLabel.setTextFill(Color.DARKBLUE);

        // Leaderboard Entries Section
        VBox leaderboardEntries = createLeaderboardEntries(
                new String[][] {
                        {"1. PlayerX", "1200"},
                        {"2. PlayerY", "1100"},
                        {"3. PlayerZ", "1000"}
                }
        );

        leaderboardEntries.setBorder(new Border(new BorderStroke(
                Color.BLACK, // Border color
                BorderStrokeStyle.SOLID, // Border style
                new CornerRadii(5), // Rounded corners
                new BorderWidths(2) // Border thickness
        )));
        leaderboardEntries.setPadding(new Insets(10)); // Space between border and content

        // Back Button
        Button backButton = createButton("Back to LeaderBoard Menu", 16, 300, "#808080", e -> controller.showLeaderBoardMenu());

        // Main Layout
        VBox layout = new VBox(20, titleLabel, leaderboardEntries, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Scene
        scene = new Scene(layout, 1280, 900);
    }

    private Label createLabel(String text, int fontSize, Pos alignment) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", fontSize));
        label.setAlignment(alignment);
        return label;
    }

    private VBox createLeaderboardEntries(String[][] data) {
        VBox entriesBox = new VBox(10);
        entriesBox.setAlignment(Pos.CENTER_LEFT);
        entriesBox.setPadding(new Insets(20));

        for (String[] entry : data) {
            HBox entryBox = new HBox(15);
            entryBox.setAlignment(Pos.CENTER_LEFT);

            Label playerLabel = new Label(entry[0]);
            playerLabel.setFont(new Font("Arial", 16));

            Label scoreLabel = new Label(entry[1]);
            scoreLabel.setFont(new Font("Arial", 16));

            entryBox.getChildren().addAll(playerLabel, scoreLabel);
            entriesBox.getChildren().add(entryBox);
        }
        return entriesBox;
    }

    private Button createButton(String text, int fontSize, double width, String bgColor, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", fontSize));
        button.setPrefWidth(width);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white;", bgColor));
        button.setOnAction(action);
        return button;
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
