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
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class ResetPasswordScreen {
    private Scene scene;

    public ResetPasswordScreen(Stage stage, ScreenController controller, Client client, String username) {
        Label titleLabel = new Label("Reset Password");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.DARKBLUE);

        // New Password Section
        Label newPasswordLabel = new Label("New Password: ");
        newPasswordLabel.setFont(new Font("Arial", 16));

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPrefWidth(350);
        newPasswordField.setPrefHeight(30);
        newPasswordField.setPromptText("Enter new password");

        HBox newPasswordLayout = new HBox(10, newPasswordLabel, newPasswordField);
        newPasswordLayout.setAlignment(Pos.CENTER);

        // Confirm Password Section
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPasswordLabel.setFont(new Font("Arial", 16));

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefWidth(350);
        confirmPasswordField.setPrefHeight(30);
        confirmPasswordField.setPromptText("Confirm new password");

        HBox confirmPasswordLayout = new HBox(10, confirmPasswordLabel, confirmPasswordField);
        confirmPasswordLayout.setAlignment(Pos.CENTER);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font("Arial", 16));
        submitButton.setPrefWidth(200);
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (newPassword.equals(confirmPassword)) {
                client.updatePassword(username, newPassword);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password successfully reset!");
                controller.showSignInScreen();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            }
        });

        // Layout
        VBox inputLayout = new VBox(15, titleLabel, newPasswordLayout, confirmPasswordLayout, submitButton);
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

