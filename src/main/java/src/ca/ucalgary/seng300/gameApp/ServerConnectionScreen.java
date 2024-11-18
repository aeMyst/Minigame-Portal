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

public class ServerConnectionScreen implements IScreen {
    private Scene scene;
    private boolean canceled = false;

    public ServerConnectionScreen(Stage stage, ScreenController controller) {
        // Title label
        Label connectingLabel = new Label("Connecting to Server...");
        connectingLabel.setFont(new Font("Arial", 24));
        connectingLabel.setTextFill(Color.DARKBLUE);

        // Progress indicator (imitates a loading spinner)
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100, 100);

        // Button to cancel connection
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(new Font("Arial", 16));
        cancelButton.setPrefWidth(200);
        cancelButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> { canceled = true;
                                        controller.showSignInScreen();});

        // Layout for connecting screen
        VBox layout = new VBox(20, connectingLabel, progressIndicator, cancelButton);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 800, 600);

        // Simulate a delay for connecting to the server (e.g., 3 seconds)
        new Thread(() -> {
            try {
                Thread.sleep(3000); // 3-second delay to simulate connecting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // After connecting, if not canceled, switch to the main menu screen
            if (!canceled) {
                javafx.application.Platform.runLater(() -> controller.showMainMenu());
            }
        }).start();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
