package src.ca.ucalgary.seng300.gameApp;

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

public class MainMenuScreen implements IScreen {
    private Scene scene;

    public MainMenuScreen(Stage stage, ScreenController controller) {
        // Title label
        Label titleLabel = new Label("Main Menu");
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
        Button searchProfileButton = new Button("Search Profile");
        searchProfileButton.setFont(new Font("Arial", 16));
        searchProfileButton.setPrefWidth(200);
        searchProfileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        searchProfileButton.setOnAction(e -> controller.showSearchProfileScreen());

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", 16));
        exitButton.setPrefWidth(200);
        exitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        exitButton.setOnAction(e -> System.exit(0));

        // Layout for main menu buttons
        VBox mainMenuLayout = new VBox(15, titleLabel, gamesButton, leaderBoardButton,searchProfileButton, exitButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.setPadding(new Insets(20));
        mainMenuLayout.setStyle("-fx-background-color: #f0f8ff;");

        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(mainMenuLayout);

        // Scene for the main menu
        scene = new Scene(mainMenuPane, 800, 600);
    }

    @Override
    public Scene getScene() {
        return scene;
    }



}
