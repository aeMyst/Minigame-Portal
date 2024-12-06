package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
        titleLabel.getStyleClass().add("title-label");

        // Input field for email address
        Label emailLabel = new Label("Email Address:");
        emailLabel.getStyleClass().add("search-label");

        TextField emailField = new TextField();
        emailField.getStyleClass().add("input-field");
        emailField.setPromptText("Enter your email address");
        emailField.setMaxWidth(350);

        VBox emailLayout = new VBox(5, emailLabel, emailField);
        emailLayout.setAlignment(Pos.CENTER);

        // Input field for username
        // Username Section
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("search-label");

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("input-field");
        usernameField.setPromptText("Enter your username");
        usernameField.setMaxWidth(350);

        VBox usernameLayout = new VBox(5, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Input field for password with details
        // Password Section
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("search-label");

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("input-field");
        passwordField.setPromptText("Enter your password");
        passwordField.setMaxWidth(350);

        // Password Requirements
        Label passwordRequirementTitle = new Label("Password Requirements:");
        passwordRequirementTitle.getStyleClass().add("password-details-label");

        VBox passwordLayoutOne = new VBox(5, passwordRequirementTitle);
        passwordLayoutOne.setAlignment(Pos.CENTER);

        Label passwordRequirementLabel = new Label("- At least 8 characters\n- Includes 1 letter, 1 number, and 1 special character");
        passwordRequirementLabel.getStyleClass().add("password-details-label");
        passwordRequirementLabel.setWrapText(true);
        passwordRequirementLabel.setMaxWidth(350);

        VBox passwordRequirementLayout = new VBox(5, passwordLayoutOne, passwordRequirementLabel);
        passwordRequirementLayout.setAlignment(Pos.CENTER);

        VBox passwordLayout = new VBox(5, passwordLabel, passwordField, passwordRequirementLayout);
        passwordLayout.setAlignment(Pos.CENTER);

        // Confirm Password Section
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.getStyleClass().add("search-label");

        // Input field for confirming password
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("input-field");
        confirmPasswordField.setPromptText("Re-enter your password");
        confirmPasswordField.setMaxWidth(350);

        VBox confirmPasswordLayout = new VBox(5, confirmPasswordLabel, confirmPasswordField);
        confirmPasswordLayout.setAlignment(Pos.CENTER);

        // Buttons Section
        Button submitButton = new Button("Create Profile");
        submitButton.getStyleClass().add("button");
        submitButton.getStyleClass().add("submit-button");
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
                showAlert(Alert.AlertType.INFORMATION, "Success", "Profile created successfully!");
                controller.showSignInScreen();
            } else {
                // Registration failed
                showAlert(Alert.AlertType.ERROR, "Error", "Profile creation failed.");
            }
        });

        Button backToSignInButton = new Button("Back to Sign In");
        backToSignInButton.getStyleClass().add("button");
        backToSignInButton.getStyleClass().add("back-button");
        backToSignInButton.setOnAction(e -> controller.showSignInScreen());

        HBox buttonsLayout = new HBox(15, submitButton, backToSignInButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        // Main Layout
        VBox mainLayout = new VBox(20, titleLabel, emailLayout, usernameLayout, passwordLayout, confirmPasswordLayout, buttonsLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
        mainLayout.setMaxWidth(600);

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(mainLayout);
        rootPane.getStyleClass().add("root-pane");

        // Create the scene for the profile creation screen
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    /**
     * Displays an alert dialog with the given type, title, and message.
     *
     * @param alertType the type of alert (e.g., ERROR, INFORMATION)
     * @param title     the title of the alert
     * @param message   the message content of the alert
     *
     * ChatGPT Generated: taught me how to pop up a new error menu using Alert.AlertType making sure to display
     *                  the correct error/alert
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

