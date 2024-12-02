package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryPlayer;
import src.ca.ucalgary.seng300.leaderboard.logic.MatchHistory;
import src.ca.ucalgary.seng300.network.Client;

public class MatchHistoryScreen implements IScreen {
    private Scene scene;
    private VBox matchHistoryLayout;

    public MatchHistoryScreen(Stage stage, ScreenController controller, Client client) {
        Label historyTitle = new Label("USER MATCH HISTORY: " + client.getCurrentUsername());
        historyTitle.getStyleClass().add("title-label");


        Button backButton = new Button("back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showUserProfileScreen());
        MatchHistory matchHistory = new MatchHistory();

        String currentPlayer = client.getCurrentUsername();



        VBox historyEntries = displayHistory(matchHistory.getMatchHistory(currentPlayer), client.getCurrentUserProfile());
        client.sendMatchHistoryToServer(matchHistory.getMatchHistory(currentPlayer), () -> {
            if (matchHistory.getMatchHistory(currentPlayer)!=null) {
                System.out.println("\n" + "Player Match History successfully updated");
                System.out.println("==========================");
            } else {
                System.out.println("Player Match History is empty");
                System.out.println("==========================");
            }
        });

        VBox layout = new VBox(20, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));


        matchHistoryLayout = new VBox(10, historyTitle, historyEntries, backButton);
        matchHistoryLayout.setAlignment(Pos.CENTER);
        matchHistoryLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(matchHistoryLayout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    private VBox displayHistory(String[][] data, String profile) {

        VBox display = new VBox(5);
        display.setPadding(new Insets(10));

        if (profile.startsWith("Profile not found")) {
            Label notFoundLabel = new Label(profile);
            notFoundLabel.getStyleClass().add("search-label");
            matchHistoryLayout.getChildren().add(notFoundLabel);
        }

        VBox historyBox = new VBox(5);
        historyBox.setPadding(new Insets(10));

        for (String[] entry : data) {
            VBox entryBox = new VBox(5);
            entryBox.setPadding(new Insets(10));
            entryBox.getStyleClass().add("profile-box");
            Label gameType = new Label("Game Type: " + entry[0]);
            Label playerID = new Label("Player ID: " + entry[1]);
            Label winner = new Label("Winner: " + entry[2]);
            Label loser = new Label("Loser: " + entry[3]);
            Label eloGained = new Label("Elo Gained: " + entry[4]);
            Label eloLost = new Label("Elo Lost: " + entry[5]);
            Label date = new Label("Date of Match: " + entry[6]);

            entryBox.getChildren().addAll(gameType, playerID, winner, loser, eloGained, eloLost, date);
            historyBox.getChildren().add(entryBox);

        }

        display.getChildren().add(historyBox);
        return display;
    }


    @Override
    public Scene getScene() {
        return scene;
    }
}
