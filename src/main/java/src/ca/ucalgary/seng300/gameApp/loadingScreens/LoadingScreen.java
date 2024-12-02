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

public class LoadingScreen implements IScreen {
    private Scene scene;
    private boolean canceled = false;

    public LoadingScreen(Stage stage, ScreenController controller, Client client) {
        // Title label
        Label connectingLabel = new Label("Finding User...");
        connectingLabel.getStyleClass().add("title-label");

        // Progress indicator (imitates a loading spinner)
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(250, 250);

        // Button to cancel connection
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("button");
        cancelButton.getStyleClass().add("exit-button");
        cancelButton.setOnAction(e -> {
            canceled = true;
            controller.showUserProfileScreen();
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

            // After connecting, if not canceled, switch to the userProfileScreen
            if (!canceled) {
                javafx.application.Platform.runLater(() -> controller.showUserProfileScreen());
            }
        }).start();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
