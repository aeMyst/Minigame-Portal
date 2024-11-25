package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Client;

public class C4EndGameScreen implements IScreen{
    private Scene scene;

    public C4EndGameScreen(Stage stage, ScreenController controller, Client client) {
        Label titleLabel = new Label("End of Match");
        titleLabel.setFont(new Font("Arial", 24));

        Label resultLabel = new Label("User won with 1 point");
        resultLabel.setFont(new Font("Arial", 16));

        Label promptLabel = new Label("What would you like to do next?");
        promptLabel.setFont(new Font("Arial", 14));

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setFont(new Font("Arial", 14));
        playAgainButton.setOnAction(e -> controller.showConnect4Screen());

        Button returnToMenuButton = new Button("Return to Game Menu");
        returnToMenuButton.setFont(new Font("Arial", 14));
        returnToMenuButton.setOnAction(e -> controller.showGameMenu());

        VBox layout = new VBox(15, titleLabel, resultLabel, promptLabel, playAgainButton, returnToMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");
        scene = new Scene(layout, 400, 300);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

}
