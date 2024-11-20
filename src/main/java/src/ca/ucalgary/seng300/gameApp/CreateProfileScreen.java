package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreateProfileScreen implements IScreen {
    private Scene scene;

    public CreateProfileScreen(Stage stage, ScreenController controller) {
        // Title label
        Label titleLabel = new Label("Create Profile");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Fields for user input
        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        // Back to Sign In button
        Button backToSignInButton = new Button("Back to Sign In");
        backToSignInButton.setOnAction(e -> {
            // Handle navigation to the sign-in screen
            System.out.println("Back to Sign In clicked");
            controller.showSignInScreen();
        });

        // Submit button
        Button submitButton = new Button("Create Profile");
        submitButton.setOnAction(e -> {
            // Handle profile creation
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // authentication should happen here

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            } else if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            } else {
                // You can add logic to save the profile data
                System.out.println("Profile created for " + username);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Profile created successfully!");

                controller.showSignInScreen();
            }
        });

        // Layout for profile setup
        VBox layout = new VBox(15, titleLabel, emailField, usernameField, passwordField, confirmPasswordField, submitButton, backToSignInButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Scene for create profile screen
        scene = new Scene(layout, 800, 600);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
