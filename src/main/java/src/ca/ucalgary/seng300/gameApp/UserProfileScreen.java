package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserProfileScreen implements IScreen {
    private Scene scene;

    public UserProfileScreen(Stage stage, ScreenController controller) {

        // User Profile Title
        Label userProfileTitle = new Label("User");
        userProfileTitle.setFont(new Font("Arial", 24));

        // Display user profile details (for now)
        Label rankLabel = new Label("Rank: #7");
        Label statusLabel = new Label("Current Status: Online");
        Label matchHistoryLabel = new Label("Recent Match History: Checkers...");
        Label totalGamesPlayedLabel = new Label("Total Games Played: 21");
        Label totalGamesWonLabel = new Label("Total Games Won: 12");
        Label totalGamesLostLabel = new Label("Total Games Lost: 9");

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

        // Layout
        VBox userProfileInfoLayout = new VBox(20, rankLabel, statusLabel, matchHistoryLabel, totalGamesPlayedLabel, totalGamesWonLabel, totalGamesLostLabel);
        userProfileInfoLayout.setAlignment(Pos.CENTER_LEFT);
        userProfileInfoLayout.setPadding(new Insets(0, 0, 0, 20));

        HBox buttonsLayout = new HBox(20, manageProfileButton, backButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        VBox userProfileLayout = new VBox(20, userProfileTitle, userProfileInfoLayout, buttonsLayout);
        userProfileLayout.setAlignment(Pos.CENTER);
        userProfileLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(userProfileLayout);

        scene = new Scene(rootPane, 800, 600);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}