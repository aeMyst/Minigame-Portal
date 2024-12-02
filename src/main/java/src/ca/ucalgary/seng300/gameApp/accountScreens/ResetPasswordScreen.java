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

public class ResetPasswordScreen {
    private Scene scene;

    public ResetPasswordScreen(Stage stage, ScreenController controller, Client client, String username, String email, AuthService authService) {
        // Title Label
        Label titleLabel = new Label("RESET PASSWORD");
        titleLabel.getStyleClass().add("title-label");

        // New Password Section
        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.getStyleClass().add("search-label");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.getStyleClass().add("input-field");
        newPasswordField.setPromptText("Enter new password");
        newPasswordField.setMaxWidth(350);

        VBox newPasswordLayout = new VBox(5, newPasswordLabel, newPasswordField);
        newPasswordLayout.setAlignment(Pos.CENTER);

        // Confirm Password Section
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.getStyleClass().add("search-label");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("input-field");
        confirmPasswordField.setPromptText("Confirm new password");
        confirmPasswordField.setMaxWidth(350);

        VBox confirmPasswordLayout = new VBox(5, confirmPasswordLabel, confirmPasswordField);
        confirmPasswordLayout.setAlignment(Pos.CENTER);

        // Password Requirements
        Label passwordDetailsLabel = new Label("Password Requirements:\n- At least 8 characters\n- Includes 1 letter, 1 number, and 1 special character");
        passwordDetailsLabel.getStyleClass().add("password-details-label");
        passwordDetailsLabel.setWrapText(true);
        passwordDetailsLabel.setMaxWidth(350);

        VBox passwordRequirementsLayout = new VBox(10, passwordDetailsLabel);
        passwordRequirementsLayout.setAlignment(Pos.CENTER);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("button");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(e -> {
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (newPassword.equals(confirmPassword)) {
                if (!authService.validateCredentials(username, email, newPassword)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Password does not meet requirements!");
                    return;
                }

                authService.modifyUserPassword(username, newPassword);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password successfully reset!");
                controller.showSignInScreen();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            }
        });

        // Layout
        VBox inputLayout = new VBox(15, titleLabel, newPasswordLayout, confirmPasswordLayout, passwordRequirementsLayout, submitButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);
        rootPane.getStyleClass().add("root-pane");

        // Scene
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
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


