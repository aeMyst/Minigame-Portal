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

/**
 * Represents the screen for user to reset their account password.
 * Allows user to enter and confirm a new password to use for signing in purposes.
 */
public class ResetPasswordScreen {
    private Scene scene;

    /**
     * Constructs the ResetPasswordScreen with necessary elements.
     *
     * @param stage The primary stage for the application.
     * @param controller Controller for transitioning between different screens.
     * @param client Client for handling network communications.
     * @param username The username of the account being reset.
     * @param email The email associated with the account.
     * @param authService The service for handling user account authentication operations.
     */
    public ResetPasswordScreen(Stage stage, ScreenController controller, Client client, String username, String email,  AuthService authService) {
        // Reset password screen title
        Label titleLabel = new Label("Reset Password");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.DARKBLUE);

        // New password label
        Label newPasswordLabel = new Label("New Password: ");
        newPasswordLabel.setFont(new Font("Arial", 16));

        // New password field
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPrefWidth(350);
        newPasswordField.setPrefHeight(30);
        newPasswordField.setPromptText("Enter new password");

        // Layout for new password
        HBox newPasswordLayout = new HBox(10, newPasswordLabel, newPasswordField);
        newPasswordLayout.setAlignment(Pos.CENTER);

        // Confirm password label
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPasswordLabel.setFont(new Font("Arial", 16));

        // Confirm password field
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefWidth(350);
        confirmPasswordField.setPrefHeight(30);
        confirmPasswordField.setPromptText("Confirm new password");

        // Layout for confirm password
        HBox confirmPasswordLayout = new HBox(10, confirmPasswordLabel, confirmPasswordField);
        confirmPasswordLayout.setAlignment(Pos.CENTER);

        // Display password requirements
        Label passwordDetailsLabel = new Label("Password Requirements: \n - Must include atleast 8 characters\n - Must include atleast 1 Letter \n " +
                "- Must include atleast 1 number\n " + "- must include atleast 1 special character/symbol" );
        passwordDetailsLabel.setFont(new Font("Arial", 10));
        passwordDetailsLabel.setTextFill(Color.GRAY);

        // Layout for password requirements details
        VBox passwordLayout = new VBox(10, passwordDetailsLabel);
        passwordLayout.setAlignment(Pos.CENTER);
        HBox passwordHbox = new HBox(10, passwordLayout);
        passwordHbox.setAlignment(Pos.CENTER);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font("Arial", 16));
        submitButton.setPrefWidth(200);
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Check if the entered password matches confirmation
            if (newPassword.equals(confirmPassword)) {
                // Validate the password format, displaying error if format is invalid
                if (!authService.validateCredentials(username, email, newPassword)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Password does not meet requirements!");
                    return;
                }
                // Modify account password and update user on success feedback
                authService.modifyUserPassword(username, newPassword);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password successfully reset!");
                controller.showSignInScreen();
            } else {
                // Display error if passwords do not match
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            }
        });

        // Main layout for reset password screen
        VBox inputLayout = new VBox(15, titleLabel, newPasswordLayout, confirmPasswordLayout, passwordLayout, submitButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);

        // Set scene for the reset password screen
        scene = new Scene(rootPane, 1280, 900);
    }

    /**
     * Displays an alert with the specified type, title, and message.
     *
     * @param alertType Type of alert being displayed.
     * @param title Title of the alert dialog.
     * @param message Content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        // Create alert with the appropriate type, title, and message
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the constructed scene for the reset password screen.
     *
     * @return The scene representing the reset password screen.
     */
    public Scene getScene() {
        return scene;
    }
}

