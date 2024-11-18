package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class QueueScreen implements IScreen {
    private Scene scene;
    private boolean canceled = false;

    public QueueScreen(Stage stage, ScreenController controller, int gameType) {
        // Title label
        Label joiningLabel = new Label("Joining Game With Player...");
        joiningLabel.setFont(new Font("Arial", 24));
        joiningLabel.setTextFill(Color.DARKBLUE);

        // Progress indicator
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100, 100);

        // Button to cancel joining game
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(new Font("Arial", 16));
        cancelButton.setPrefWidth(200);
        cancelButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> { canceled = true;
                                        controller.showGameMenu();});

        // Layout
        VBox layout = new VBox(20, joiningLabel, progressIndicator, cancelButton);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 800, 600);

        // Simulate a delay for connecting to the another player
        new Thread(() -> {
            try {
                Thread.sleep(1000); // 1-second delay to simulate connecting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // After connecting, if not canceled, switch to game screen
            if (!canceled) {
                switch (gameType) {
                    case Integer.MIN_VALUE:
                        javafx.application.Platform.runLater(controller::showTictactoeGameScreen);
                        break;
                    case Integer.MIN_VALUE + 1:
                        javafx.application.Platform.runLater(controller::showConnect4Screen);
                        break;
                    case Integer.MIN_VALUE + 2:
                        javafx.application.Platform.runLater(controller::showCheckerScreen);
                        break;
                }
            }
        }).start();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
