package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class SignInScreen implements IScreen {
    private Scene scene;

    public SignInScreen(Stage stage, ScreenController controller, Client client) {
        // Title Label
        Label titleLabel = new Label("SIGN IN");
        titleLabel.getStyleClass().add("title-label");

        // Username Section
        Label usernameLabel = new Label("Username: ");
        usernameLabel.getStyleClass().add("search-label");

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("input-field");
        usernameField.setPromptText("Enter your Username");

        HBox usernameLayout = new HBox(10, usernameLabel, usernameField);
        usernameLayout.setAlignment(Pos.CENTER);

        // Password Section
        Label passwordLabel = new Label("Password: ");
        passwordLabel.getStyleClass().add("search-label");

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("input-field");
        passwordField.setPromptText("Enter your Password");

        HBox passwordLayout = new HBox(10, passwordLabel, passwordField);
        passwordLayout.setAlignment(Pos.CENTER);

        // Buttons Section
        Button signInButton = new Button("Sign In");
        signInButton.getStyleClass().add("button");
        signInButton.getStyleClass().add("submit-button");
        signInButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            boolean isLoggedIn = client.logInUser(username, password);
            if (isLoggedIn) {
                controller.showServerConnectionScreen(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Login");
            }
        });

        Button createAccountButton = new Button("Create Account");
        createAccountButton.getStyleClass().add("button");
        createAccountButton.getStyleClass().add("create-account-button");
        createAccountButton.setOnAction(e -> controller.showCreateProfileScreen());

        VBox signInCreateLayout = new VBox(10, signInButton, createAccountButton);
        signInCreateLayout.setAlignment(Pos.CENTER);

        // Forgot Password and Username Section
        Button forgotPasswordButton = new Button("Forgot Password?");
        forgotPasswordButton.getStyleClass().add("button");
        forgotPasswordButton.getStyleClass().add("forgot-button");
        forgotPasswordButton.setOnAction(e -> controller.showForgotPasswordScreen());

        Button forgotUsernameButton = new Button("Forgot Username?");
        forgotUsernameButton.getStyleClass().add("button");
        forgotUsernameButton.getStyleClass().add("forgot-button");
        forgotUsernameButton.setOnAction(e -> controller.showForgotUsernameScreen());

        HBox forgotLayout = new HBox(15, forgotPasswordButton, forgotUsernameButton);
        forgotLayout.setAlignment(Pos.CENTER);

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("button");
        exitButton.getStyleClass().add("exit-button");
        exitButton.setOnAction(e -> controller.stop());

        VBox mainLayout = new VBox(20, titleLabel, usernameLayout, passwordLayout, signInCreateLayout, forgotLayout, exitButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(mainLayout);
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

    @Override
    public Scene getScene() {
        return scene;
    }
}


