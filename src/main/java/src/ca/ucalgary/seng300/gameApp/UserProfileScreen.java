package src.ca.ucalgary.seng300.gameApp;

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
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserProfileScreen implements IScreen {
    private Scene scene;

    public UserProfileScreen(Stage stage, ScreenController controller) {
        // User Profile Title
        Label userProfileTitle = new Label("USER PROFILE");
        userProfileTitle.setFont(new Font("Arial", 32));

        // Display user profile details (initially the logged-in user's profile)
        Label rankLabel = new Label("Rank: #7");
        rankLabel.setFont(new Font("Arial", 16));
        Label statusLabel = new Label("Current Status: Online");
        statusLabel.setFont(new Font("Arial", 16));
        Label matchHistoryLabel = new Label("Recent Match History: Checkers...");
        matchHistoryLabel.setFont(new Font("Arial", 16));
        Label totalGamesPlayedLabel = new Label("Total Games Played: 21");
        totalGamesPlayedLabel.setFont(new Font("Arial", 16));
        Label totalGamesWonLabel = new Label("Total Games Won: 12");
        totalGamesWonLabel.setFont(new Font("Arial", 16));
        Label totalGamesLostLabel = new Label("Total Games Lost: 9");
        totalGamesLostLabel.setFont(new Font("Arial", 16));

        // TextField to search for another profile
        Label searchProfileLabel = new Label("Search Profile:");
        searchProfileLabel.setFont(new Font("Arial", 16));
        TextField searchProfileField = new TextField();
        searchProfileField.setPromptText("Enter profile name");

        Button searchButton = new Button("Search");
        searchButton.setFont(new Font("Arial", 16));
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF;");
        searchButton.setOnAction(e -> {
            String profileName = searchProfileField.getText();
            if (!profileName.isEmpty()) {
                controller.showLoadingScreen();
            } else {
                showErrorMessage("Error", "Please enter a valid profile name.");
            }
        });

        // Button to return to the logged-in user's profile
        Button xButton = new Button("X");
        xButton.setFont(new Font("Arial", 16));
        xButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
        xButton.setOnAction(e -> {
            // Reset to the logged-in user's profile
            rankLabel.setText("Rank: #7");
            statusLabel.setText("Current Status: Online");
            matchHistoryLabel.setText("Recent Match History: Checkers...");
            totalGamesPlayedLabel.setText("Total Games Played: 21");
            totalGamesWonLabel.setText("Total Games Won: 12");
            totalGamesLostLabel.setText("Total Games Lost: 9");
            userProfileTitle.setText("USER PROFILE");
            searchProfileField.clear();
        });

        // Manage Profile Button
        Button manageProfileButton = new Button("Manage Profile");
        manageProfileButton.setFont(new Font("Arial", 18));
        manageProfileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF;");
        manageProfileButton.setOnAction(e -> controller.showManageProfileScreen());

        // Back Button
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 18));
        backButton.setStyle("-fx-background-color: #AAAAAA; -fx-text-fill: #FFFFFF;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for profile info
        VBox userProfileInfoLayout = new VBox(10, rankLabel, statusLabel, matchHistoryLabel, totalGamesPlayedLabel, totalGamesWonLabel, totalGamesLostLabel);
        userProfileInfoLayout.setAlignment(Pos.CENTER);
        userProfileInfoLayout.setPadding(new Insets(0, 0, 0, 20));

        // Layout for search
        HBox searchLayout = new HBox(10, searchProfileLabel, searchProfileField, searchButton, xButton);
        searchLayout.setAlignment(Pos.CENTER);

        // Layout for buttons
        HBox buttonsLayout = new HBox(20, manageProfileButton, backButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        // Main layout
        VBox userProfileLayout = new VBox(20, userProfileTitle, userProfileInfoLayout, searchLayout, buttonsLayout);
        userProfileLayout.setAlignment(Pos.CENTER);
        userProfileLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(userProfileLayout);

        scene = new Scene(rootPane, 800, 600);
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