package src.ca.ucalgary.seng300.gameApp.loadingScreens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class ServerConnectionScreen implements IScreen {
    private Scene scene;
    private boolean canceled = false;

    public ServerConnectionScreen(Stage stage, ScreenController controller, Client client, boolean disconnectCheck) {
        // Title label
        Label connectingLabel = new Label();
        connectingLabel.getStyleClass().add("title-label");

        if (disconnectCheck) {
            connectingLabel.setText("Disconnecting From Server...");
        } else {
            connectingLabel.setText("Connecting to Server...");
        }

        // Progress indicator (imitates a loading spinner)
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(250, 250);

        // Button to cancel connection
        // created with the help of chatgpt
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("button");
        cancelButton.getStyleClass().add("exit-button");
        cancelButton.setOnAction(e -> {
            canceled = true;
            if (disconnectCheck) {
                controller.showMainMenu();
            } else {
                controller.showSignInScreen();
            }
        });

        // Layout for connecting screen
        VBox layout = new VBox(20, connectingLabel, progressIndicator, cancelButton);
        layout.setAlignment(Pos.CENTER);

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));

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
