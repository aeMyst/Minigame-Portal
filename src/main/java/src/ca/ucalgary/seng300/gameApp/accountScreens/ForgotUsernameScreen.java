package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class ForgotUsernameScreen {
    private Scene scene;

    public ForgotUsernameScreen(Stage stage, ScreenController controller, Client client, AuthService authService) {
        Label titleLabel = new Label("Forgot Username");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Email or Recovery Info Section
        Label recoveryLabel = new Label("Enter Registered Email or Recovery Information:");
        recoveryLabel.setFont(new Font("Arial", 16));

        TextField recoveryField = new TextField();
        recoveryField.setPrefWidth(350);
        recoveryField.setPrefHeight(30);
        recoveryField.setPromptText("Enter your email or recovery info");

        HBox recoveryHbox = new HBox(recoveryLabel, recoveryField);
        recoveryHbox.setAlignment(Pos.CENTER);

        VBox recoveryLayout = new VBox(10, recoveryHbox);
        recoveryLayout.setAlignment(Pos.CENTER);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font("Arial", 16));
        submitButton.setPrefWidth(200);
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            String recoveryInfo = recoveryField.getText();

            // Validate recovery info using client logic
            String username = client.retrieveUsername(recoveryInfo);
            if (username != null) {
                showAlert(Alert.AlertType.INFORMATION, "Username Retrieved", "Your username is: " + username);
                controller.showSignInScreen();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid recovery information.");
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showSignInScreen());

        // Layout
        VBox inputLayout = new VBox(15, titleLabel, recoveryLayout, submitButton, backButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);

        scene = new Scene(rootPane, 1280, 900);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }
}

