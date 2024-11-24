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
import src.ca.ucalgary.seng300.Client;

public class QueueScreen implements IScreen {
    private Scene scene;
    private boolean canceled = false;

    public QueueScreen(Stage stage, ScreenController controller, int gameType, Client client) {
        // Title label
        Label joiningLabel = new Label("Searching for Player...");
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
        cancelButton.setOnAction(e -> {
            canceled = true;
            client.cancelQueue();
            controller.showGameMenu();
        });

        // Layout
        VBox layout = new VBox(20, joiningLabel, progressIndicator, cancelButton);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 800, 600);

        // Simulate a delay for connecting to another player
        new Thread(() -> {
            try {
                Thread.sleep(5000); // 5-second delay to simulate connecting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!canceled) {
                // Update the label to indicate a player was found
                javafx.application.Platform.runLater(() -> joiningLabel.setText("Player Found!"));

                // Additional delay before setting up the game
                try {
                    Thread.sleep(2000); // 2-second delay to display "Player Found!"
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!canceled) {
                    // Update the label to indicate setting up the game
                    javafx.application.Platform.runLater(() -> joiningLabel.setText("Setting up game..."));

                    // Delay before transitioning to the game screen
                    try {
                        Thread.sleep(2000); // 2-second delay to display "Setting up game..."
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    client.queueGame();

                    if (!canceled) {
                        javafx.application.Platform.runLater(() -> {
                            switch (gameType) {
                                case 0:
                                    controller.showTictactoeGameScreen();
                                    break;
                                case 1:
                                    controller.showConnect4Screen();
                                    break;
                                case 2:
                                    controller.showCheckerScreen();
                                    break;
                            }
                        });
                    }
                }
            }
        }).start();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
