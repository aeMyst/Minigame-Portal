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

public class ManageProfileScreen implements IScreen {
    private Scene scene;

    public ManageProfileScreen(Stage stage, ScreenController controller, Client client) {
        // Label for Manage Profile title
        Label titleLabel = new Label("Manage Profile");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Label and field for changing email
        Label changeEmailLabel = new Label("Change Email:");
        TextField changeEmailField = new TextField();
        changeEmailField.setPromptText("Enter new Email");

        HBox emailLayout = new HBox(10, changeEmailLabel, changeEmailField);
        emailLayout.setAlignment(Pos.CENTER);

        // Label and field for changing username
        Label changeUsernameLabel = new Label("Change Username:");
        TextField changeUsernameField = new TextField();
        changeUsernameField.setPromptText("Enter new username");

        HBox usernameLayout = new HBox(10, changeUsernameLabel, changeUsernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Label and field for changing password
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

            // if email field is filled, change the email
            User currentUser = client.loggedIn(); //

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

        // Button for going back
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for buttons
        HBox buttonLayout = new HBox(20, updateButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Main Layout
        VBox layout = new VBox(20, titleLabel, emailLayout, usernameLayout, passwordLayout, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 1280, 900);
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
