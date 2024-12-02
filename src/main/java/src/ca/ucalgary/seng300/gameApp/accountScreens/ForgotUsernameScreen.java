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
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

import java.util.ArrayList;

/**
 * Represents the Forgot Username screen in the application.
 * Allows users to recover their username using registered email or recovery information.
 */
public class ForgotUsernameScreen {
    private Scene scene;

    /**
     * Constructor for ForgotUsernameScreen.
     *
     * @param stage      the primary stage of the application
     * @param controller the screen controller to manage navigation
     * @param client     the client for network communication
     * @param authService the authentication service for validating recovery information
     */
    public ForgotUsernameScreen(Stage stage, ScreenController controller, Client client, AuthService authService) {
        // Title label setup
        Label titleLabel = new Label("Forgot Username");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Input field for recovery email or information
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

        // Submit button setup
        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font("Arial", 16));
        submitButton.setPrefWidth(200);
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            // Retrieve recovery input
            String recoveryInfo = recoveryField.getText();

            // Validate recovery info using client logic
            ArrayList<User> sanitizedUsers = authService.getSanitizedUsers();
            String username = null;
            for (User user : sanitizedUsers) {
                if (user.getEmail().equals(recoveryInfo)) {
                    username = user.getUsername();
                    break;
                }
            }
            if (username != null) {
                // Show success alert with username
                showAlert(Alert.AlertType.INFORMATION, "Username Retrieved", "Your username is: " + username);
                controller.showSignInScreen();
            } else {
                // Show error alert if recovery fails
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid recovery information.");
            }
        });

        // Back button setup
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showSignInScreen());

        // Layout for inputs and buttons
        VBox inputLayout = new VBox(15, titleLabel, recoveryLayout, submitButton, backButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        // Root layout setup
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);

        // Create the scene
        scene = new Scene(rootPane, 1280, 900);
    }

    /**
     * Displays an alert dialog with the given type, title, and message.
     *
     * @param alertType the type of alert (e.g., ERROR, INFORMATION)
     * @param title     the title of the alert
     * @param message   the message content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the scene for the Forgot Username screen.
     *
     * @return the Scene object
     */
    public Scene getScene() {
        return scene;
    }
}

