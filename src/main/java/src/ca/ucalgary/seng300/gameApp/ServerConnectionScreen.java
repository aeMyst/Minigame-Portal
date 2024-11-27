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

public class ServerConnectionScreen implements IScreen {
    private Scene scene;
    private boolean canceled = false;

    public ServerConnectionScreen(Stage stage, ScreenController controller, Client client, boolean disconnectCheck) {
        // Title label
        Label connectingLabel = new Label();
        connectingLabel.setFont(new Font("Arial", 24));
        connectingLabel.setTextFill(Color.DARKBLUE);

        if (disconnectCheck) {
            connectingLabel.setText("Disconnecting From Server...");
        } else {
            connectingLabel.setText("Connecting to Server...");
        }

        // Progress indicator (imitates a loading spinner)
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100, 100);

        // Button to cancel connection
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(new Font("Arial", 16));
        cancelButton.setPrefWidth(200);
        cancelButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> { canceled = true;
            if (disconnectCheck) {
                controller.showMainMenu();
            } else {
                controller.showSignInScreen();
            }
        });

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
            if (!canceled && !disconnectCheck) {
                client.connectServer();
                javafx.application.Platform.runLater(() -> controller.showMainMenu());
            } else if (!canceled && disconnectCheck) {
                // logout user if logged in then disconnect
                client.logoutUser();
                client.disconnectServer();
                javafx.application.Platform.runLater(() -> controller.showSignInScreen());
            }
        }).start();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
