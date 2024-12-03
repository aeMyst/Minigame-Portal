package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

/**
 * ResetPasswordScreen represents the screen for user to reset their account password
 * Allows user to enter and confirm a new password to use for signing in purposes
 */
public class ResetPasswordScreen {
    private Scene scene;

    /**
     * Constructs the ResetPasswordScreen with necessary elements
     *
     * @param stage The primary stage for the application
     * @param controller Controller for transitioning between different screens
     * @param client Client for handling network communications
     * @param username The username of the account being reset
     * @param email The email associated with the account
     * @param authService The service for handling user account authentication operations
     */
    public ResetPasswordScreen(Stage stage, ScreenController controller, Client client, String username, String email, AuthService authService) {
        // Title Label
        Label titleLabel = new Label("RESET PASSWORD");
        titleLabel.getStyleClass().add("title-label");

        // New Password Section
        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.getStyleClass().add("search-label");

        // New password field
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.getStyleClass().add("input-field");
        newPasswordField.setPromptText("Enter new password");
        newPasswordField.setMaxWidth(350);

        // Layout for new password
        VBox newPasswordLayout = new VBox(5, newPasswordLabel, newPasswordField);
        newPasswordLayout.setAlignment(Pos.CENTER);

        // Confirm password label
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.getStyleClass().add("search-label");

        // Confirm password field
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("input-field");
        confirmPasswordField.setPromptText("Confirm new password");
        confirmPasswordField.setMaxWidth(350);

        // Layout for confirm password
        VBox confirmPasswordLayout = new VBox(5, confirmPasswordLabel, confirmPasswordField);
        confirmPasswordLayout.setAlignment(Pos.CENTER);

        // Display password requirements
        Label passwordDetailsLabel = new Label("Password Requirements:\n- At least 8 characters\n- Includes 1 letter, 1 number, and 1 special character");
        passwordDetailsLabel.getStyleClass().add("password-details-label");
        passwordDetailsLabel.setWrapText(true);
        passwordDetailsLabel.setMaxWidth(350);

        // Layout for password requirements details
        VBox passwordRequirementsLayout = new VBox(10, passwordDetailsLabel);
        passwordRequirementsLayout.setAlignment(Pos.CENTER);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("button");
        submitButton.getStyleClass().add("submit-button");
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
                // Modify account password and update user on feedback
                authService.modifyUserPassword(username, newPassword);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password successfully reset!");
                controller.showSignInScreen();
            } else {
                // Display error if passwords do not match
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            }
        });

        // Main layout for reset password screen
        VBox inputLayout = new VBox(15, titleLabel, newPasswordLayout, confirmPasswordLayout, passwordRequirementsLayout, submitButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);
        rootPane.getStyleClass().add("root-pane");

        // Set scene for the reset password screen
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    /**
     * Displays an alert with the specified type, title, and message
     *
     * @param alertType Type of alert being displayed
     * @param title Title of the alert dialog
     * @param message Content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the constructed scene for the reset password screen
     *
     * @return Scene representing the reset password screen
     */
    public Scene getScene() {
        return scene;
    }
}

