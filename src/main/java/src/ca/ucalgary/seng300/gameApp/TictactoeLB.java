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

public class TictactoeLB implements IScreen {
    private Scene scene;

    public TictactoeLB(Stage stage, LeaderboardController controller) {
        Label titleLabel = new Label("Tic-Tac-Toe Leaderboard");
        titleLabel.setFont(new Font("Arial", 24));

        VBox leaderboardEntries = new VBox(10);
        leaderboardEntries.setAlignment(Pos.CENTER_LEFT);
        leaderboardEntries.setPadding(new Insets(20));

        String[][] data = {
                {"1. PlayerX", "1200"},
                {"2. PlayerY", "1100"},
                {"3. PlayerZ", "1000"}
        };

        for (String[] entry : data) {
            HBox entryBox = new HBox(15);
            Label playerLabel = new Label(entry[0]);
            Label scoreLabel = new Label(entry[1]);
            entryBox.getChildren().addAll(playerLabel, scoreLabel);
            leaderboardEntries.getChildren().add(entryBox);
        }

        Button backButton = new Button("Back to LeaderBoard Menu");
        backButton.setOnAction(e -> controller.showLeaderBoardMenu());

        VBox layout = new VBox(20, titleLabel, leaderboardEntries, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        scene = new Scene(layout, 800, 600);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
