package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
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
        Label titleLabel = new Label("MANAGE PROFILE");
        titleLabel.getStyleClass().add("title-label");

        // Input field for updating email
        Label changeEmailLabel = new Label("Change Email:");
        changeEmailLabel.getStyleClass().add("search-label");

        TextField changeEmailField = new TextField();
        changeEmailField.getStyleClass().add("input-field");
        changeEmailField.setPromptText("Enter new email");
        changeEmailField.setMaxWidth(350);

        VBox emailLayout = new VBox(5, changeEmailLabel, changeEmailField);
        emailLayout.setAlignment(Pos.CENTER);

        // Input field for updating username
        Label changeUsernameLabel = new Label("Change Username:");
        changeUsernameLabel.getStyleClass().add("search-label");

        TextField changeUsernameField = new TextField();
        changeUsernameField.getStyleClass().add("input-field");
        changeUsernameField.setPromptText("Enter new username");
        changeUsernameField.setMaxWidth(350);

        VBox usernameLayout = new VBox(5, changeUsernameLabel, changeUsernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Input field for updating password
        Label changePasswordLabel = new Label("Change Password:");
        changePasswordLabel.getStyleClass().add("search-label");

        PasswordField changePasswordField = new PasswordField();
        changePasswordField.getStyleClass().add("input-field");
        changePasswordField.setPromptText("Enter new password");
        changePasswordField.setMaxWidth(350);

        VBox passwordLayout = new VBox(5, changePasswordLabel, changePasswordField);
        passwordLayout.setAlignment(Pos.CENTER);

        // Button for updating new profile changes
        Button updateButton = new Button("Update Profile");
        updateButton.getStyleClass().add("button");
        updateButton.getStyleClass().add("submit-button");
        updateButton.setOnAction(e -> {
            // Retrieve current user
            User currentUser = client.loggedIn();

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
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showUserProfileScreen(client.getCurrentUsername()));

        HBox buttonLayout = new HBox(15, updateButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Main layout containing all elements
        VBox layout = new VBox(20, titleLabel, emailLayout, usernameLayout, passwordLayout, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");

        // Create the scene
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
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
     *
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.AlertType.html
     *                The following resource was used to find out the type of AlertTypes that exist
     *                the JavaFX library
     *
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

