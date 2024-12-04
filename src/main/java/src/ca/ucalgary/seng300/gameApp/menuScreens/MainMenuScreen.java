package src.ca.ucalgary.seng300.gameApp.menuScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

/**
 * Represents the main menu screen of the application where users can navigate to different features.
 */
public class MainMenuScreen implements IScreen {
    private Scene scene;

    /**
     * Constructs the MainMenuScreen with the necessary UI components.
     *
     * @param stage      The primary stage of the application.
     * @param controller The controller used to navigate between screens.
     * @param client     The client handling client interactions.
     */
    public MainMenuScreen(Stage stage, ScreenController controller, Client client) {
        // Title label
        String username = client.getCurrentUsername();
        Label titleLabel = new Label("Welcome " + username + "!");
        titleLabel.getStyleClass().add("title-label");

        // Buttons
        Button gamesButton = new Button("Play");
        gamesButton.getStyleClass().add("button");
        gamesButton.getStyleClass().add("submit-button");
        gamesButton.setOnAction(e -> controller.showGameMenu());

        Button leaderBoardButton = new Button("View Leaderboards");
        leaderBoardButton.getStyleClass().add("button");
        leaderBoardButton.getStyleClass().add("submit-button");
        leaderBoardButton.setOnAction(e -> controller.showLeaderBoard());

        Button helpButton = new Button("Help");
        helpButton.getStyleClass().add("button");
        helpButton.getStyleClass().add("submit-button");
        helpButton.setOnAction(e -> controller.showHelpScreen());

        Button viewProfileButton = new Button("View Profile");
        viewProfileButton.getStyleClass().add("button");
        viewProfileButton.getStyleClass().add("submit-button");
        viewProfileButton.setOnAction(e -> controller.showUserProfileScreen(client.getCurrentUsername()));

        Button logOutButton = new Button("Log Out");
        logOutButton.getStyleClass().add("button");
        logOutButton.getStyleClass().add("exit-button");
        logOutButton.setOnAction(e -> controller.showServerConnectionScreen(true));

        // Layout
        VBox mainMenuLayout = new VBox(15, titleLabel, gamesButton, leaderBoardButton, viewProfileButton, helpButton, logOutButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(mainMenuLayout);
        rootPane.getStyleClass().add("root-pane");

        // Scene
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
