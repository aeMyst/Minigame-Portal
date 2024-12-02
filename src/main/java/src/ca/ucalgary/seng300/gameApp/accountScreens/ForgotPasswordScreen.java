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
 * Represents the Forgot Password screen in the application.
 * Allows users to recover their account using their username and associated email.
 */
public class ForgotPasswordScreen {
    private Scene scene;

    /**
     * Constructor for ForgotPasswordScreen.
     *
     * @param stage      the primary stage of the application
     * @param controller the screen controller to manage navigation
     * @param client     the client for network communication
     * @param authService the authentication service for validating recovery information
     */
    public ForgotPasswordScreen(Stage stage, ScreenController controller, Client client, AuthService authService) {
        // Title label setup
        Label titleLabel = new Label("Forgot Password");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Input field for username
        Label usernameLabel = new Label("Enter Username: ");
        usernameLabel.setFont(new Font("Arial", 16));

        TextField usernameField = new TextField();
        usernameField.setPrefWidth(350);
        usernameField.setPrefHeight(30);
        usernameField.setPromptText("Enter your Username");

        HBox usernameLayout = new HBox(10, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Input field for recovery email
        Label recoveryLabel = new Label("Enter your email associated with your account:");
        recoveryLabel.setFont(new Font("Arial", 16));

        TextField recoveryField = new TextField();
        recoveryField.setPrefWidth(350);
        recoveryField.setPrefHeight(30);
        recoveryField.setPromptText("Enter your email");

        HBox recoveryHBox = new HBox(10, recoveryLabel, recoveryField);
        recoveryHBox.setAlignment(Pos.CENTER);

        VBox recoveryLayout = new VBox(10, recoveryHBox);
        recoveryLayout.setAlignment(Pos.CENTER);

        // Submit button setup
        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font("Arial", 16));
        submitButton.setPrefWidth(200);
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            // Retrieve input
            String username = usernameField.getText();
            String email = recoveryField.getText();

            // Validate recovery information
            ArrayList<User> users = authService.getSanitizedUsers();
            boolean isRecoverySuccessful = false;
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
                    isRecoverySuccessful = true;
                    break;
                }
            }

            if (isRecoverySuccessful) {
                // Simulate successful recovery and show a reset password screen
                controller.showResetPasswordScreen(username, email);
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
        VBox inputLayout = new VBox(15, titleLabel, usernameLayout, recoveryLayout, submitButton, backButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        // Root layout setup
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);

        // Create scene
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
     * Returns the scene for the Forgot Password screen.
     *
     * @return the Scene object
     */
    public Scene getScene() {
        return scene;
    }
}
