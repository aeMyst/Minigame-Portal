package src.ca.ucalgary.seng300.gameApp.leaderboardScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;
import src.ca.ucalgary.seng300.gameApp.IScreen;

public class TictactoeLB implements IScreen {
    private Scene scene;

    public TictactoeLB(Stage stage, LeaderboardController controller, Client client) {
        // Title Label
        Label titleLabel = new Label("TIC-TAC-TOE LEADERBOARD");
        titleLabel.getStyleClass().add("leaderboard-title");
        // Leaderboard Entries Section

        String[][] leaderboard = client.getTTTLeaderboard(() -> {
                System.out.println("\n" + "Tic-Tac-Toe Leaderboard GET call succeeded");
        });

        VBox leaderboardEntries = createLeaderboardEntries(leaderboard);

        // Back Button
        Button backButton = new Button("Back to LeaderBoard Menu");
        backButton.getStyleClass().add("leaderboard-button");
        backButton.getStyleClass().add("leaderboard-button-primary");
        backButton.setOnAction(e -> controller.showLeaderBoardMenu());

        // Main Layout
        VBox layout = new VBox(20, titleLabel, leaderboardEntries, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("leaderboard-pane");

        // Scene
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("LeaderboardStyles.css").toExternalForm()));
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

        Label nameHeader = new Label("PLAYERID");
        nameHeader.setFont(Font.font("Arial",FontWeight.BOLD,20));
        nameHeader.setPrefWidth(220);
        nameHeader.setAlignment(Pos.CENTER);
        nameHeader.setTextFill(Color.WHITE);

        Label eloHeader = new Label("RATING");
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

            entryBox.setAlignment(Pos.BASELINE_LEFT);

            Label playerLabel = new Label(entry[0]);
            playerLabel.setFont(Font.font("Arial", FontWeight.BOLD,16));
            playerLabel.setPrefWidth(160);
            playerLabel.setAlignment(Pos.CENTER);

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


