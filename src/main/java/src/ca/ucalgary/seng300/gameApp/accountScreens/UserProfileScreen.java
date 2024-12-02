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
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

import java.util.HashMap;
import java.util.Map;

public class UserProfileScreen implements IScreen {
    private Scene scene;
    private VBox userProfileInfoLayout;

    public UserProfileScreen(Stage stage, ScreenController controller, Client client) {
        String currentUsername = client.getCurrentUsername();

        // User Profile Title
        Label userProfileTitle = new Label("USER PROFILE: " + currentUsername);
        userProfileTitle.getStyleClass().add("title-label");

        // The layout for displaying user profile details
        userProfileInfoLayout = new VBox(10);
        userProfileInfoLayout.setAlignment(Pos.CENTER_LEFT);
        userProfileInfoLayout.setPadding(new Insets(20));
        userProfileInfoLayout.getStyleClass().add("user-profile-box");

        // Display user profile details (initially the logged-in user's profile)
        String profileInfo = client.getCurrentUserProfile();
        displayProfile(profileInfo);

        // TextField to search for another profile
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
                    displayProfile(searchResult);
                    userProfileTitle.setText("USER PROFILE: " + profileName);
                }
            } else {
                showErrorMessage("Error", "Please enter a valid profile name.");
            }
        });

        // Button to return to the logged-in user's profile
        Button xButton = new Button("X");
        xButton.getStyleClass().add("button");
        xButton.getStyleClass().add("exit-button");
        xButton.setOnAction(e -> {
            // Reset to the logged-in user's profile
            String updatedProfileInfo = client.getCurrentUserProfile();
            displayProfile(updatedProfileInfo);
            String currentUsernameReset = client.getCurrentUsername();
            userProfileTitle.setText("USER PROFILE: " + currentUsernameReset);
            searchProfileField.clear();
        });

        // Manage Profile Button
        Button manageProfileButton = new Button("Manage Profile");
        manageProfileButton.getStyleClass().add("button");
        manageProfileButton.getStyleClass().add("submit-button");
        manageProfileButton.setOnAction(e -> controller.showManageProfileScreen());

        // Back Button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showMainMenu());

        Button matchHistoryButton = new Button("Match History");
        matchHistoryButton.getStyleClass().add("button");
        matchHistoryButton.getStyleClass().add("submit-button");
        matchHistoryButton.setOnAction(e -> controller.showMatchHistoryScreen());

        // Layout for search
        HBox searchLayout = new HBox(10, searchProfileLabel, searchProfileField, searchButton, xButton);
        searchLayout.setAlignment(Pos.CENTER);

        // Layout for buttons
        HBox buttonsLayout = new HBox(20, matchHistoryButton, manageProfileButton, backButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        // Main layout
        VBox userProfileLayout = new VBox(20, userProfileTitle, userProfileInfoLayout, searchLayout, buttonsLayout);
        userProfileLayout.setAlignment(Pos.CENTER);
        userProfileLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(userProfileLayout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    private void displayProfile(String profileInfo) {
        userProfileInfoLayout.getChildren().clear();

        if (profileInfo.startsWith("Profile not found")) {
            Label notFoundLabel = new Label(profileInfo);
            notFoundLabel.getStyleClass().add("search-label");
            userProfileInfoLayout.getChildren().add(notFoundLabel);
            return;
        }

        String[] profileLists = profileInfo.split("\n\n");
        for (String block : profileLists) {
            String[] lines = block.split("\n");
            Map<String, String> profileData = new HashMap<>();
            for (String line : lines) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    profileData.put(parts[0], parts[1]);
                }
            }

            VBox profileBox = new VBox(5);
            profileBox.setPadding(new Insets(10));
            profileBox.getStyleClass().add("profile-box");

            Label gametypeLabel = new Label("Gametype: " + profileData.getOrDefault("Gametype", ""));
            Label playerIDLabel = new Label("PlayerID: " + profileData.getOrDefault("PlayerID", ""));
            Label eloLabel = new Label("Elo: " + profileData.getOrDefault("Elo", ""));
            Label winsLabel = new Label("Wins: " + profileData.getOrDefault("Wins", ""));
            Label lossesLabel = new Label("Losses: " + profileData.getOrDefault("Losses", ""));
            Label drawsLabel = new Label("Draws: " + profileData.getOrDefault("Draws", ""));
            Label gamesPlayedLabel = new Label("Games Played: " + profileData.getOrDefault("Games Played", ""));

            profileBox.getChildren().addAll(
                    gametypeLabel, playerIDLabel, eloLabel, winsLabel, lossesLabel, drawsLabel, gamesPlayedLabel
            );

            userProfileInfoLayout.getChildren().add(profileBox);
        }
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

