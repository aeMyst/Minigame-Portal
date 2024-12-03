package src.ca.ucalgary.seng300.gameApp.menuScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
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
        // Title label
        Label titleLabel = new Label("CHOOSE A GAME");
        titleLabel.getStyleClass().add("title-label");

        // Buttons
        Button ticTacToeButton = new Button("Play Tic-Tac-Toe");
        ticTacToeButton.getStyleClass().add("button");
        ticTacToeButton.getStyleClass().add("submit-button");
        ticTacToeButton.setOnAction(e -> controller.showMatchmakeChoiceScreen(0));

        Button connectFourButton = new Button("Play Connect 4");
        connectFourButton.getStyleClass().add("button");
        connectFourButton.getStyleClass().add("submit-button");
        connectFourButton.setOnAction(e -> controller.showMatchmakeChoiceScreen(1));

        Button checkersButton = new Button("Play Checkers");
        checkersButton.getStyleClass().add("button");
        checkersButton.getStyleClass().add("submit-button");
        checkersButton.setOnAction(e -> controller.showMatchmakeChoiceScreen(2));

        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("exit-button");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout
        VBox layout = new VBox(15, titleLabel, ticTacToeButton, connectFourButton, checkersButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
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

