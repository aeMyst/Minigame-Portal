package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SignInScreen implements IScreen {
    private Scene scene;

    public SignInScreen(Stage stage, ScreenController controller) {
        Label titleLabel = new Label("Sign In");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button signInButton = new Button("Sign In");
        signInButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (!username.isEmpty() && !password.isEmpty()) {
                // Simulate successful login by showing the connecting screen
                controller.showServerConnectionScreen();
            } else {
                System.out.println("Username and password cannot be empty.");
            }
        });

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction(e -> controller.showCreateProfileScreen());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        VBox inputLayout = new VBox(10, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, signInButton, createAccountButton, exitButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(inputLayout);

        scene = new Scene(rootPane, 800, 600);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
