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

/**
 * Class that specifies the appearance of match history screen in the GUI
 */
public class MatchHistoryScreen implements IScreen {
    private Scene scene;
    private VBox matchHistoryInfoLayout;
    private String currentUser;

    /**
     * Contains the components (buttons, labels, etc.) and layout used in the match history screen in the GUI
     * @param stage Describes the properties and containers of the screen of the current match history window
     * @param controller Used the manage the switching of screens
     * @param client A class that is used to emulate a server
     * @param initialUser A String that contains the PlayerID of the current player being displayed in the match history screen
     */
    public MatchHistoryScreen(Stage stage, ScreenController controller, Client client, String initialUser) {
        MatchHistory matchHistory = new MatchHistory();
        this.currentUser = initialUser;

        Label historyTitle = new Label("USER MATCH HISTORY: " + initialUser);
        historyTitle.getStyleClass().add("title-label");

        matchHistoryInfoLayout = new VBox(10);
        matchHistoryInfoLayout.setAlignment(Pos.CENTER_LEFT);
        matchHistoryInfoLayout.setPadding(new Insets(20));
        matchHistoryInfoLayout.getStyleClass().add("user-profile-box");

        // display current user logged-in first
        displayHistory(matchHistory.getMatchHistory(initialUser), initialUser);

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
                    this.currentUser = profileName;
                    MatchHistoryServer(client, matchHistory);
                    historyTitle.setText("USER MATCH HISTORY: " + profileName);
                }
            } else {
                showErrorMessage("Error", "Please enter a valid profile name.");
            }
        });

        Button backButton = new Button("back");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            if (searchProfileField.getText().isEmpty()) {
                System.out.println(currentUser);
                controller.showUserProfileScreen(this.currentUser);
            } else {
                controller.showUserProfileScreen(searchProfileField.getText());
            }
        });

        // Button to return to the logged-in user's profile
        Button xButton = new Button("View Your Profile");
        xButton.getStyleClass().add("button");
        xButton.getStyleClass().add("exit-button");
        xButton.setOnAction(e -> {
            // Reset to the logged-in user's profile
            displayHistory(matchHistory.getMatchHistory(client.getCurrentUsername()), client.getCurrentUsername());
            String currentUsernameReset = client.getCurrentUsername();
            this.currentUser = client.getCurrentUsername();
            MatchHistoryServer(client, matchHistory);
            historyTitle.setText("USER PROFILE: " + currentUsernameReset);
            searchProfileField.clear();
        });

        HBox searchLayout = new HBox(10, searchProfileLabel, searchProfileField, searchButton, xButton);
        searchLayout.setAlignment(Pos.CENTER);

        // send request to server
        MatchHistoryServer(client, matchHistory);

        VBox matchHistoryLayout = new VBox(10, historyTitle, matchHistoryInfoLayout, searchLayout, backButton);
        matchHistoryLayout.setAlignment(Pos.CENTER);
        matchHistoryLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(matchHistoryLayout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    /**
     * Method used to get the string of HistoryPlayers and display them in the GUI with its specified designs
     * @param data  A 2D array of HistoryPlayers of String data type
     * @param profile   The playerID of the current player being displayed in the match history screen
     */
    private void displayHistory(String[][] data, String profile) {
        matchHistoryInfoLayout.getChildren().clear();

        VBox display = new VBox(5);
        display.setPadding(new Insets(10));

        if (profile.startsWith("Profile not found")) {
            Label notFoundLabel = new Label(profile);
            notFoundLabel.getStyleClass().add("search-label");
            matchHistoryInfoLayout.getChildren().add(notFoundLabel);
        }

        VBox historyBox = new VBox(5);
        historyBox.setPadding(new Insets(10));

        // go through the 2D array and display the information in its own VBox
        // created with the help of chatgpt
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

    /**
     * Method used to display an error message when an error occurs
     * @param title The title of the error window
     * @param message The String error message displayed in the error window
     */
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method used to display the match history in the console along with the GUI
     * @param client A class that emulates a server
     * @param matchHistory A class that fetches the data in the database used for match history
     */
    private void MatchHistoryServer(Client client, MatchHistory matchHistory) {
        client.sendMatchHistoryToServer(matchHistory.getMatchHistory(currentUser), () -> {
            if (matchHistory.getMatchHistory(currentUser)!=null) {
                System.out.println("\n" + "Player Match History successfully updated");
                System.out.println("==========================");
            } else {
                System.out.println("Player Match History is empty");
                System.out.println("==========================");
            }
        });
    }

    /**
     * A getter method for the scene
     * @return scene
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
