package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;

public class TictactoeLB implements IScreen {
    private Scene scene;

    public TictactoeLB(Stage stage, LeaderboardController controller) {
        // Title Label
        Label titleLabel = createLabel("Tic-Tac-Toe Leaderboard", 24, Pos.CENTER);
        Leaderboard leaderboard = new Leaderboard();

        // Leaderboard Entries Section


//        VBox leaderboardEntries = createLeaderboardEntries(
//                new String[][]{
//                        {"1. PlayerX", "1200"},
//                        {"2. PlayerY", "1100"},
//                        {"3. PlayerZ", "1000"}
//                }
//        );

        VBox leaderboardEntries = createLeaderboardEntries(leaderboard.getTicTacToeLeaderboard());


        // Back Button
        Button backButton = createButton("Back to LeaderBoard Menu", 16, 300, "#4CAF50", e -> controller.showLeaderBoardMenu());

        // Main Layout
        VBox layout = new VBox(20, titleLabel, leaderboardEntries, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Scene
        scene = new Scene(layout, 800, 600);
    }

    private Label createLabel(String text, int fontSize, Pos alignment) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", fontSize));
        label.setAlignment(alignment);
        return label;
    }

    private VBox createLeaderboardEntries(String[][] data) {
        int lastEntry = data.length - 1;
        int count = 0;

        VBox entriesBox = new VBox(5);
        entriesBox.setAlignment(Pos.CENTER);
        entriesBox.setPadding(new Insets(10));
        entriesBox.setMaxWidth(420);
        entriesBox.setStyle("-fx-border-color: grey; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");

        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setMaxWidth(400);
        headerBox.setPrefWidth(400);
        headerBox.setStyle("-fx-background-color: grey; -fx-padding: 10; -fx-background-radius: 10 10 0 0;");

        Label nameHeader = new Label("NAME");
        nameHeader.setFont(Font.font("Arial",FontWeight.BOLD,20));
        nameHeader.setPrefWidth(220);
        nameHeader.setAlignment(Pos.CENTER);
        nameHeader.setTextFill(Color.WHITE);

        Label eloHeader = new Label("SCORE");
        eloHeader.setFont(Font.font("Arial",FontWeight.BOLD,20));
        eloHeader.setPrefWidth(90);
        eloHeader.setAlignment(Pos.CENTER);
        eloHeader.setTextFill(Color.WHITE);

        Label winsHeader = new Label("WINS");
        winsHeader.setFont(Font.font("Arial",FontWeight.BOLD,20));
        winsHeader.setPrefWidth(80);
        winsHeader.setAlignment(Pos.CENTER);
        winsHeader.setTextFill(Color.WHITE);

        headerBox.getChildren().addAll(nameHeader,eloHeader,winsHeader);
        entriesBox.getChildren().add(headerBox);

        for (String[] entry : data) {
            HBox entryBox = new HBox(10);
            entryBox.setSpacing(40);
            entryBox.setStyle("-fx-padding: 5; -fx-background-color: lightgrey;");
            entryBox.setPrefWidth(400);
            entryBox.setMinWidth(400);
            entryBox.setMaxWidth(400);
            if (count == lastEntry) {
                entryBox.setStyle("-fx-padding: 5; -fx-background-color: lightgrey; -fx-background-radius: 0 0 10 10;");

            }
//            entryBox.setAlignment(Pos.BASELINE_CENTER);
            entryBox.setAlignment(Pos.BASELINE_LEFT);

            Label playerLabel = new Label(entry[0]);
            playerLabel.setFont(Font.font("Arial", FontWeight.BOLD,16));
            playerLabel.setPrefWidth(160);
            playerLabel.setAlignment(Pos.CENTER_LEFT);

            Label eloLabel = new Label(entry[1]);
            eloLabel.setFont(new Font("Arial", 16));
            eloLabel.setPrefWidth(100);
            eloLabel.setAlignment(Pos.CENTER);

            Label winsLabel = new Label(entry[2]);
            winsLabel.setFont(new Font("Arial", 16));
            eloLabel.setPrefWidth(100);
            eloLabel.setAlignment(Pos.CENTER);


            entryBox.getChildren().addAll(playerLabel, eloLabel, winsLabel);
            entriesBox.getChildren().add(entryBox);

            count++;
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
