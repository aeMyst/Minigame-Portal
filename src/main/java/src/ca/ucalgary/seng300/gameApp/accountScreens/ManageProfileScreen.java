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

public class ManageProfileScreen implements IScreen {
    private Scene scene;

    public ManageProfileScreen(Stage stage, ScreenController controller, Client client) {
        // Title Label
        Label titleLabel = new Label("MANAGE PROFILE");
        titleLabel.getStyleClass().add("title-label");

        // Change Email Section
        Label changeEmailLabel = new Label("Change Email:");
        changeEmailLabel.getStyleClass().add("search-label");

        TextField changeEmailField = new TextField();
        changeEmailField.getStyleClass().add("input-field");
        changeEmailField.setPromptText("Enter new email");
        changeEmailField.setMaxWidth(350);

        VBox emailLayout = new VBox(5, changeEmailLabel, changeEmailField);
        emailLayout.setAlignment(Pos.CENTER);

        // Change Username Section
        Label changeUsernameLabel = new Label("Change Username:");
        changeUsernameLabel.getStyleClass().add("search-label");

        TextField changeUsernameField = new TextField();
        changeUsernameField.getStyleClass().add("input-field");
        changeUsernameField.setPromptText("Enter new username");
        changeUsernameField.setMaxWidth(350);

        VBox usernameLayout = new VBox(5, changeUsernameLabel, changeUsernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Change Password Section
        Label changePasswordLabel = new Label("Change Password:");
        changePasswordLabel.getStyleClass().add("search-label");

        PasswordField changePasswordField = new PasswordField();
        changePasswordField.getStyleClass().add("input-field");
        changePasswordField.setPromptText("Enter new password");
        changePasswordField.setMaxWidth(350);

        VBox passwordLayout = new VBox(5, changePasswordLabel, changePasswordField);
        passwordLayout.setAlignment(Pos.CENTER);

        // Buttons
        Button updateButton = new Button("Update Profile");
        updateButton.getStyleClass().add("button");
        updateButton.getStyleClass().add("submit-button");
        updateButton.setOnAction(e -> {
            User currentUser = client.loggedIn();

            if (currentUser == null) {
                showErrorMessage("Error", "No user is currently logged in.");
                return;
            }

            // Get input values
            String newEmail = changeEmailField.getText().isEmpty() ? null : changeEmailField.getText();
            String newUsername = changeUsernameField.getText().isEmpty() ? null : changeUsernameField.getText();
            String newPassword = changePasswordField.getText().isEmpty() ? null : changePasswordField.getText();

            // Call the editProfile method
            client.editProfile(currentUser, newUsername, newEmail, newPassword);

            // Provide feedback to the user
            showInfoMessage("Success", "Profile updated successfully!");
            controller.showMainMenu();
        });

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showUserProfileScreen(client.getCurrentUsername()));

        HBox buttonLayout = new HBox(15, updateButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Main Layout
        VBox layout = new VBox(20, titleLabel, emailLayout, usernameLayout, passwordLayout, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");

        // Scene
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

