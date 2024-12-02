package src.ca.ucalgary.seng300.gameApp.menuScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

/**
 * Represents the game menu screen where the user can choose a game to play.
 * Provides options for Tic-Tac-Toe, Connect 4, Checkers, or returning to the main menu.
 */
public class GameMenuScreen implements IScreen {
    private Scene scene;

    /**
     * Constructor for the GameMenuScreen class.
     *
     * @param stage      The main application stage.
     * @param controller The controller to manage screen transitions.
     * @param client     The client for handling network interactions.
     */
    public GameMenuScreen(Stage stage, ScreenController controller, Client client) {
        // Title label for the game menu
        Label titleLabel = new Label("Choose a Game");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Button to navigate to Tic-Tac-Toe game
        Button ticTacToeButton = new Button("Play Tic-Tac-Toe");
        ticTacToeButton.setFont(new Font("Arial", 16));
        ticTacToeButton.setPrefWidth(200);
        ticTacToeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        ticTacToeButton.setOnAction(e -> controller.showMatchmakeChoiceScreen(0));

        // Button to navigate to Connect 4 game
        Button connectFourButton = new Button("Play Connect 4");
        connectFourButton.setFont(new Font("Arial", 16));
        connectFourButton.setPrefWidth(200);
        connectFourButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        connectFourButton.setOnAction(e -> controller.showMatchmakeChoiceScreen(1));

        // Button to navigate to Checkers game
        Button checkersButton = new Button("Play Checkers");
        checkersButton.setFont(new Font("Arial", 16));
        checkersButton.setPrefWidth(200);
        checkersButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        checkersButton.setOnAction(e -> controller.showMatchmakeChoiceScreen(2));

        // Button to go back to main menu
        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for game selection buttons
        VBox layout = new VBox(15, titleLabel, ticTacToeButton, connectFourButton, checkersButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f8ff;");

        // Create the scene for the game menu
        scene = new Scene(layout, 1280, 900);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
