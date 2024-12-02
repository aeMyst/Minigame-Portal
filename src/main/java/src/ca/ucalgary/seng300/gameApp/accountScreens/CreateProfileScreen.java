package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

/**
 * Represents the Create Profile Screen in the application.
 * Allows users to input their credentials and create a new profile.
 */
public class CreateProfileScreen implements IScreen {
    private Scene scene;

    /**
     * Constructor for CreateProfileScreen.
     *
     * @param stage      the primary stage of the application
     * @param controller the screen controller to manage navigation
     * @param client     the client for handling network communication and registration
     */
    public CreateProfileScreen(Stage stage, ScreenController controller, Client client) {
        // Title label setup
        Label titleLabel = new Label("Create Profile");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Input field for email address
        TextField emailField = new TextField();
        emailField.setPrefWidth(350); // Increase width
        emailField.setPrefHeight(30);
        emailField.setPromptText("Email Address");

        HBox emailLayout = new HBox(10, emailField);
        emailLayout.setAlignment(Pos.CENTER);

        // Input field for username
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(350);
        usernameField.setPrefHeight(30);
        usernameField.setPromptText("Username");

        HBox usernameLayout = new HBox(10, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Input field for password with details
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(350);
        passwordField.setPrefHeight(30);
        passwordField.setPromptText("Password");

        Label passwordDetailsLabel = new Label("Password Requirements: \n - Must include atleast 8 characters\n - Must include atleast 1 Letter \n " +
                "- Must include atleast 1 number\n " + "- must include atleast 1 special character/symbol" );
        passwordDetailsLabel.setFont(new Font("Arial", 10));
        passwordDetailsLabel.setTextFill(Color.GRAY);

        VBox passwordLayout = new VBox(10, passwordField, passwordDetailsLabel);
        passwordLayout.setAlignment(Pos.CENTER);
        HBox passwordHbox = new HBox(10, passwordLayout);
        passwordHbox.setAlignment(Pos.CENTER);

        // Input field for confirming password
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefWidth(350);
        confirmPasswordField.setPrefHeight(30);
        confirmPasswordField.setPromptText("Confirm Password");

        HBox confirmPasswordLayout = new HBox(10, confirmPasswordField);
        confirmPasswordLayout.setAlignment(Pos.CENTER);

        // Back to Sign In button
        Button backToSignInButton = new Button("Back to Sign In");
        backToSignInButton.setFont(new Font("Arial", 16));
        backToSignInButton.setPrefWidth(200);
        backToSignInButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backToSignInButton.setOnAction(e -> {
            // Handle navigation to the sign-in screen
            controller.showSignInScreen();
        });

        // Submit button for profile creation
        Button submitButton = new Button("Create Profile");
        submitButton.setFont(new Font("Arial", 16));
        submitButton.setPrefWidth(200);
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            // Retrieve user input
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Validate input fields and handle registration logic
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            } else if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            } else if (client.registerUser(username, password, email)) {
                // You can add logic to save the profile data
                // TODO: We need to add specifics on why a registration failed
                // Successfully registered
                System.out.println("Profile created for " + username);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Profile created successfully!");

                controller.showSignInScreen();
            } else {
                // Registration failed
                showAlert(Alert.AlertType.ERROR, "Error", "Profile creation failed.");
            }
        });

        // Organize all elements in a vertical box
        VBox layout = new VBox(15, titleLabel, emailLayout, usernameLayout, passwordHbox, confirmPasswordLayout, submitButton, backToSignInButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Create the scene for the profile creation screen
        scene = new Scene(layout, 1280, 900);
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
     * Returns the scene for the create profile screen.
     *
     * @return the Scene object
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
