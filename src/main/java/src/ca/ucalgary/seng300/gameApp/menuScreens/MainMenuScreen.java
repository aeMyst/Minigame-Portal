package src.ca.ucalgary.seng300.gameApp.menuScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

/**
 * Represents the main menu screen of the application where users can navigate to different features.
 */
public class MainMenuScreen implements IScreen {
    private Scene scene;
    private String username;

    /**
     * Constructs the MainMenuScreen with the necessary UI components.
     *
     * @param stage      The primary stage of the application.
     * @param controller The controller used to navigate between screens.
     * @param client     The client handling client interactions.
     */
    public MainMenuScreen(Stage stage, ScreenController controller, Client client) {
        // Title label
        username = client.getCurrentUsername();
        Label titleLabel = new Label("Welcome " + username + "!");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Button to navigate to the Game Menu
        Button gamesButton = new Button("Play");
        gamesButton.setFont(new Font("Arial", 16));
        gamesButton.setPrefWidth(200);
        gamesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        gamesButton.setOnAction(e -> controller.showGameMenu());

        // Button to view Leaderboards
        Button leaderBoardButton = new Button("View Leaderboards");
        leaderBoardButton.setFont(new Font("Arial", 16));
        leaderBoardButton.setPrefWidth(200);
        leaderBoardButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        leaderBoardButton.setOnAction(e -> controller.showLeaderBoard());

        // Button to search profile
        Button helpButton = new Button("Help");
        helpButton.setFont(new Font("Arial", 16));
        helpButton.setPrefWidth(200);
        helpButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        helpButton.setOnAction(e -> controller.showHelpScreen());

        // Button to view profile
        Button viewProfileButton = new Button("View Profile");
        viewProfileButton.setFont(new Font("Arial", 16));
        viewProfileButton.setPrefWidth(200);
        viewProfileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        viewProfileButton.setOnAction(e -> controller.showUserProfileScreen());

        // Log out button
        Button logOutButton = new Button("Log Out");
        logOutButton.setFont(new Font("Arial", 16));
        logOutButton.setPrefWidth(200);
        logOutButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: white;");
        logOutButton.setOnAction(e -> controller.showServerConnectionScreen(true));

        // Layout for main menu buttons
        VBox mainMenuLayout = new VBox(15, titleLabel, gamesButton, leaderBoardButton, viewProfileButton, helpButton, logOutButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.setPadding(new Insets(20));
        mainMenuLayout.setStyle("-fx-background-color: #f0f8ff;");

        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(mainMenuLayout);

        // Scene for the main menu
        scene = new Scene(mainMenuPane, 1280, 900);
    }

    @Override
    public Scene getScene() {
        return scene;
    }


}
