package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameMenuScreen implements IScreen {
    private Scene scene;

    public GameMenuScreen(Stage stage, ScreenController controller) {
        // Title label for the game menu
        Label titleLabel = new Label("Choose a Game");
        titleLabel.setFont(new Font("Arial", 24));

        // Button to navigate to Tic-Tac-Toe game
        Button ticTacToeButton = new Button("Play Tic-Tac-Toe");
        ticTacToeButton.setOnAction(e -> controller.showTictactoeGameScreen());

        // Button to navigate to Connect 4 game
        Button connectFourButton = new Button("Play Connect 4");
        connectFourButton.setOnAction(e -> controller.showConnect4Screen());

        // Button to navigate to Checkers game
        Button checkersButton = new Button("Play Checkers");
        checkersButton.setOnAction(e -> controller.showCheckerScreen());

        // Layout for game selection buttons
        VBox layout = new VBox(15, titleLabel, ticTacToeButton, connectFourButton, checkersButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f8ff;");

        // Create the scene for the game menu
        scene = new Scene(layout, 800, 600);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
