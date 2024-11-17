package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EndGameScreen implements IScreen {
    private Scene scene;

    public EndGameScreen(Stage stage, ScreenController controller) {
        // Title for End of Match
        Label titleLabel = new Label("End of Match");
        titleLabel.setFont(new Font("Arial", 24));

        // Result of the game (e.g., "User won with 1 point")
        Label resultLabel = new Label("User won with 1 point");
        resultLabel.setFont(new Font("Arial", 16));

        // Prompt asking what the user wants to do next
        Label promptLabel = new Label("What would you like to do next?");
        promptLabel.setFont(new Font("Arial", 14));

        // Button to play again
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setFont(new Font("Arial", 14));
        playAgainButton.setOnAction(e -> controller.showTictactoeGameScreen());  // Restart the Tic-Tac-Toe game

        // Button to return to game menu
        Button returnToMenuButton = new Button("Return to Game Menu");
        returnToMenuButton.setFont(new Font("Arial", 14));
        returnToMenuButton.setOnAction(e -> controller.showGameMenu());

        // Layout setup
        VBox layout = new VBox(15, titleLabel, resultLabel, promptLabel, playAgainButton, returnToMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        // Create the scene with the layout
        scene = new Scene(layout, 400, 300);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
