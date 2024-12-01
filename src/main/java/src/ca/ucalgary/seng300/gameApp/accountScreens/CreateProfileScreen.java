package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class CreateProfileScreen implements IScreen {
    private Scene scene;

    public CreateProfileScreen(Stage stage, ScreenController controller, Client client) {
        // Title Label
        Label titleLabel = new Label("CREATE PROFILE");
        titleLabel.getStyleClass().add("title-label");

        // Email Section
        Label emailLabel = new Label("Email Address:");
        emailLabel.getStyleClass().add("search-label");

        TextField emailField = new TextField();
        emailField.getStyleClass().add("input-field");
        emailField.setPromptText("Enter your email address");
        emailField.setMaxWidth(400);

        VBox emailLayout = new VBox(5, emailLabel, emailField);
        emailLayout.setAlignment(Pos.CENTER);

        // Username Section
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("search-label");

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("input-field");
        usernameField.setPromptText("Enter your username");
        usernameField.setMaxWidth(400);

        VBox usernameLayout = new VBox(5, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Password Section
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("search-label");

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("input-field");
        passwordField.setPromptText("Enter your password");
        passwordField.setMaxWidth(400);

        // Password Requirements
        Label passwordRequirementTitle = new Label("Password Requirements:");
        passwordRequirementTitle.getStyleClass().add("password-details-label");
        passwordRequirementTitle.setAlignment(Pos.CENTER);

        Label passwordRequirementLabel = new Label("- At least 8 characters\n- Includes 1 letter, 1 number, and 1 special character");
        passwordRequirementLabel.getStyleClass().add("password-details-label");
        passwordRequirementLabel.setWrapText(true);
        passwordRequirementLabel.setMaxWidth(400);
        passwordRequirementLabel.setAlignment(Pos.CENTER);

        VBox passwordRequirementLayout = new VBox(3, passwordRequirementTitle, passwordRequirementLabel);
        passwordRequirementLayout.setAlignment(Pos.CENTER);

        VBox passwordLayout = new VBox(5, passwordLabel, passwordField, passwordRequirementLayout);
        passwordLayout.setAlignment(Pos.CENTER);

        // Confirm Password Section
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.getStyleClass().add("search-label");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("input-field");
        confirmPasswordField.setPromptText("Re-enter your password");
        confirmPasswordField.setMaxWidth(400);

        VBox confirmPasswordLayout = new VBox(5, confirmPasswordLabel, confirmPasswordField);
        confirmPasswordLayout.setAlignment(Pos.CENTER);

        // Buttons Section
        Button submitButton = new Button("Create Profile");
        submitButton.getStyleClass().add("button");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(e -> {
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            } else if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            } else if (client.registerUser(username, password, email)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Profile created successfully!");
                controller.showSignInScreen();
            } else {
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

        // Scene for create profile screen
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

    @Override
    public Scene getScene() {
        return scene;
    }
}








