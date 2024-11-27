package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CheckerLB implements IScreen {
    private Scene scene;

    public CheckerLB(Stage stage, LeaderboardController controller) {
        Label titleLabel = createLabel("Checkers Leaderboard", 24, Pos.CENTER);

        VBox leaderboardEntries = createLeaderboardEntries(
                new String[][]{
                        {"1. PlayerBlue", "1500"},
                        {"2. PlayerRed", "1400"},
                        {"3. PlayerGreen", "1300"}
                }
        );

        Button backButton = createButton("Back to LeaderBoard Menu", 16, 300, "#4CAF50", e -> controller.showLeaderBoardMenu());

        VBox layout = new VBox(20, titleLabel, leaderboardEntries, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 800, 600);
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
