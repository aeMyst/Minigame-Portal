package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameMenuScreen implements IScreen {
    private Scene scene;
    // define game type as int
    private int ticTacToe = Integer.MIN_VALUE;
    private int connect4 = Integer.MIN_VALUE + 1;
    private int checkers = Integer.MIN_VALUE + 2;

    public GameMenuScreen(Stage stage, ScreenController controller) {
        // Title label for the game menu
        Label titleLabel = new Label("Choose a Game");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Button to navigate to Tic-Tac-Toe game
        Button ticTacToeButton = new Button("Play Tic-Tac-Toe");
        ticTacToeButton.setFont(new Font("Arial", 16));
        ticTacToeButton.setPrefWidth(200);
        ticTacToeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        ticTacToeButton.setOnAction(e -> controller.showQueueScreen(ticTacToe));

        // Button to navigate to Connect 4 game
        Button connectFourButton = new Button("Play Connect 4");
        connectFourButton.setFont(new Font("Arial", 16));
        connectFourButton.setPrefWidth(200);
        connectFourButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        connectFourButton.setOnAction(e -> controller.showQueueScreen(connect4));

        // Button to navigate to Checkers game
        Button checkersButton = new Button("Play Checkers");
        checkersButton.setFont(new Font("Arial", 16));
        checkersButton.setPrefWidth(200);
        checkersButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        checkersButton.setOnAction(e -> controller.showQueueScreen(checkers));

        // Button to go back to main menu
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for game selection buttons
        VBox layout = new VBox(15, titleLabel, ticTacToeButton, connectFourButton, checkersButton, backButton);
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
