package src.ca.ucalgary.seng300.gameApp;

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

public class SignInScreen implements IScreen {
    private Scene scene;

    public SignInScreen(Stage stage, ScreenController controller) {
        Label titleLabel = new Label("SIGN IN");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Username Section
        Label usernameLabel = new Label("Username: ");
        usernameLabel.setFont(new Font("Arial", 16));

        TextField usernameField = new TextField();
        usernameField.setPrefWidth(350); // Increase width
        usernameField.setPrefHeight(30); // Optional: Increase height
        usernameField.setPromptText("Enter your Username");

        HBox usernameLayout = new HBox(10, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Password Section
        Label passwordLabel = new Label("Password: ");
        passwordLabel.setFont(new Font("Arial", 16));

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(350); // Increase width
        passwordField.setPrefHeight(30); // Optional: Increase height
        passwordField.setPromptText("Enter your Password");

        HBox passwordLayout = new HBox(10, passwordLabel, passwordField);
        passwordLayout.setAlignment(Pos.CENTER);

        // Sign In Button
        Button signInButton = new Button("Sign In");
        signInButton.setFont(new Font("Arial", 16));
        signInButton.setPrefWidth(200);
        signInButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        signInButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Verify username and password with authentication logic
            if (!username.isEmpty() && !password.isEmpty()) {
                // Simulate successful login by showing the connecting screen
                controller.showServerConnectionScreen();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
                System.out.println("Username and password cannot be empty.");
            }
        });

        // Create Account Button
        Button createAccountButton = new Button("Create Account");
        createAccountButton.setFont(new Font("Arial", 16));
        createAccountButton.setPrefWidth(200);
        createAccountButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        createAccountButton.setOnAction(e -> controller.showCreateProfileScreen());

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", 16));
        exitButton.setPrefWidth(200);
        exitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        exitButton.setOnAction(e -> System.exit(0));

        // Combine Everything in a VBox
        VBox inputLayout = new VBox(15, titleLabel, usernameLayout, passwordLayout, signInButton, createAccountButton, exitButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        // Main Layout
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);

        scene = new Scene(rootPane, 800, 600);
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
