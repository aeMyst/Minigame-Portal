package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

import java.util.ArrayList;

public class ForgotPasswordScreen {
    private Scene scene;

    public ForgotPasswordScreen(Stage stage, ScreenController controller, Client client, AuthService authService) {
        // Title Label
        Label titleLabel = new Label("FORGOT PASSWORD");
        titleLabel.getStyleClass().add("title-label");

        // Username Section
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("search-label");

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("input-field");
        usernameField.setPromptText("Enter your Username");
        usernameField.setMaxWidth(350);

        VBox usernameLayout = new VBox(5, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Email Section
        Label recoveryLabel = new Label("Recovery Email:");
        recoveryLabel.getStyleClass().add("search-label");

        TextField recoveryField = new TextField();
        recoveryField.getStyleClass().add("input-field");
        recoveryField.setPromptText("Enter your email");
        recoveryField.setMaxWidth(350);

        VBox recoveryLayout = new VBox(5, recoveryLabel, recoveryField);
        recoveryLayout.setAlignment(Pos.CENTER);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("button");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = recoveryField.getText();

            ArrayList<User> users = authService.getSanitizedUsers();
            boolean isRecoverySuccessful = false;
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
                    isRecoverySuccessful = true;
                    break;
                }
            }

            if (isRecoverySuccessful) {
                // Simulate successful recovery and show a reset password screen
                controller.showResetPasswordScreen(username, email);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid recovery information.");
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showSignInScreen());

        // Layout
        HBox buttonsLayout = new HBox(15, submitButton, backButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        VBox inputLayout = new VBox(20, titleLabel, usernameLayout, recoveryLayout, buttonsLayout);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);
        rootPane.getStyleClass().add("root-pane");

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

