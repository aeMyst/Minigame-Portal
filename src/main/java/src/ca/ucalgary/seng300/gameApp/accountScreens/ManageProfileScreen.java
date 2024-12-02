package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

/**
 * Represents the Manage Profile screen in the application.
 * Allows users to update their email, username, or password and navigate back to the main menu.
 */

public class ManageProfileScreen implements IScreen {
    private Scene scene;

    /**
     * Constructor for ManageProfileScreen.
     *
     * @param stage      the primary stage of the application
     * @param controller the screen controller for navigation
     * @param client     the client for handling user authentication and profile updates
     */
    public ManageProfileScreen(Stage stage, ScreenController controller, Client client) {
        // Title for the Manage Profile screen
        Label titleLabel = new Label("Manage Profile");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Input field for updating email
        Label changeEmailLabel = new Label("Change Email:");
        TextField changeEmailField = new TextField();
        changeEmailField.setPromptText("Enter new Email");

        HBox emailLayout = new HBox(10, changeEmailLabel, changeEmailField);
        emailLayout.setAlignment(Pos.CENTER);

        // Input field for updating username
        Label changeUsernameLabel = new Label("Change Username:");
        TextField changeUsernameField = new TextField();
        changeUsernameField.setPromptText("Enter new username");

        HBox usernameLayout = new HBox(10, changeUsernameLabel, changeUsernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Input field for updating password
        Label changePasswordLabel = new Label("Change Password:");
        PasswordField changePasswordField = new PasswordField();
        changePasswordField.setPromptText("Enter new password");

        HBox passwordLayout = new HBox(10, changePasswordLabel, changePasswordField);
        passwordLayout.setAlignment(Pos.CENTER);

        // Button for updating new profile changes
        Button updateButton = new Button("Update Profile");
        updateButton.setFont(new Font("Arial", 16));
        updateButton.setPrefWidth(200);
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        updateButton.setOnAction(e -> {

            // Retrieve current user
            User currentUser = client.loggedIn(); //

            if (currentUser == null) {
                showErrorMessage("Error", "No user is currently logged in.");
                return;
            }

            // Get input values for changes
            String newEmail = changeEmailField.getText().isEmpty() ? null : changeEmailField.getText();
            String newUsername = changeUsernameField.getText().isEmpty() ? null : changeUsernameField.getText();
            String newPassword = changePasswordField.getText().isEmpty() ? null : changePasswordField.getText();

            // Update profile using client logic
            client.editProfile(currentUser, newUsername, newEmail, newPassword);

            // Provide feedback and navigate back to the main menu
            showInfoMessage("Success", "Profile updated successfully!");
            controller.showMainMenu();
        });

        // Button to go back to the main menu
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for buttons
        HBox buttonLayout = new HBox(20, updateButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Main layout containing all elements
        VBox layout = new VBox(20, titleLabel, emailLayout, usernameLayout, passwordLayout, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Create the scene
        scene = new Scene(layout, 1280, 900);
    }

    /**
     * Displays an error message alert.
     *
     * @param title   the title of the alert
     * @param message the error message to display
     */
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an informational message alert.
     *
     * @param title   the title of the alert
     * @param message the informational message to display
     */
    private void showInfoMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the scene for the Manage Profile screen.
     *
     * @return the Scene object
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
