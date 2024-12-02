package src.ca.ucalgary.seng300.gameApp.accountScreens;

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
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.leaderboard.logic.MatchHistory;
import src.ca.ucalgary.seng300.network.Client;

import java.util.Arrays;

public class MatchHistoryScreen implements IScreen {
    private Scene scene;
    private VBox matchHistoryInfoLayout;

    public MatchHistoryScreen(Stage stage, ScreenController controller, Client client) {
        MatchHistory matchHistory = new MatchHistory();

        Label historyTitle = new Label("USER MATCH HISTORY: " + client.getCurrentUsername());
        historyTitle.getStyleClass().add("title-label");

        matchHistoryInfoLayout = new VBox(10);
        matchHistoryInfoLayout.setAlignment(Pos.CENTER_LEFT);
        matchHistoryInfoLayout.setPadding(new Insets(20));
        matchHistoryInfoLayout.getStyleClass().add("user-profile-box");

        // display current user logged-in first
        String profileInfo = client.getCurrentUsername();
        displayHistory(matchHistory.getMatchHistory(profileInfo), profileInfo);

        Label searchProfileLabel = new Label("Search Profile:");
        searchProfileLabel.getStyleClass().add("search-label");

        TextField searchProfileField = new TextField();
        searchProfileField.getStyleClass().add("input-field");
        searchProfileField.setPromptText("Enter profile name");

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("button");
        searchButton.getStyleClass().add("submit-button");
        searchButton.setOnAction(e -> {
            String profileName = searchProfileField.getText();
            if (!profileName.isEmpty()) {
                String searchResult = client.searchProfile(profileName);
                if (searchResult.startsWith("Profile not found")) {
                    showErrorMessage("Error", "Profile not found for username: " + profileName);
                } else {
                    displayHistory(matchHistory.getMatchHistory(profileName), profileName);
                    historyTitle.setText("USER MATCH HISTORY: " + profileName);
                }
            } else {
                showErrorMessage("Error", "Please enter a valid profile name.");
            }
        });

        Button backButton = new Button("back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showUserProfileScreen());

        // Button to return to the logged-in user's profile
        Button xButton = new Button("X");
        xButton.getStyleClass().add("button");
        xButton.getStyleClass().add("exit-button");
        xButton.setOnAction(e -> {
            // Reset to the logged-in user's profile
            displayHistory(matchHistory.getMatchHistory(client.getCurrentUsername()), client.getCurrentUsername());
            String currentUsernameReset = client.getCurrentUsername();
            historyTitle.setText("USER PROFILE: " + currentUsernameReset);
            searchProfileField.clear();
        });

        String currentPlayer = client.getCurrentUsername();

        HBox searchLayout = new HBox(10, searchProfileLabel, searchProfileField, searchButton, xButton);
        searchLayout.setAlignment(Pos.CENTER);

//        client.sendMatchHistoryToServer(matchHistory.getMatchHistory(currentPlayer), () -> {
//            if (matchHistory.getMatchHistory(currentPlayer)!=null) {
//                System.out.println("\n" + "Player Match History successfully updated");
//                System.out.println("==========================");
//            } else {
//                System.out.println("Player Match History is empty");
//                System.out.println("==========================");
//            }
//        });

        VBox matchHistoryLayout = new VBox(10, historyTitle, matchHistoryInfoLayout, searchLayout, backButton);
        matchHistoryLayout.setAlignment(Pos.CENTER);
        matchHistoryLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(matchHistoryLayout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    private void displayHistory(String[][] data, String profile) {
        matchHistoryInfoLayout.getChildren().clear();

        System.out.println(Arrays.deepToString(data));

        VBox display = new VBox(5);
        display.setPadding(new Insets(10));

        if (profile.startsWith("Profile not found")) {
            Label notFoundLabel = new Label(profile);
            notFoundLabel.getStyleClass().add("search-label");
            matchHistoryInfoLayout.getChildren().add(notFoundLabel);
        }

        VBox historyBox = new VBox(5);
        historyBox.setPadding(new Insets(10));

        for (String[] entry : data) {
            if (!(entry[1] == null)) {
                VBox entryBox = new VBox(5);
                entryBox.setPadding(new Insets(10));
                entryBox.getStyleClass().add("profile-box");

                entryBox.getChildren().clear();

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
        }

        matchHistoryInfoLayout.getChildren().add(historyBox);
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
