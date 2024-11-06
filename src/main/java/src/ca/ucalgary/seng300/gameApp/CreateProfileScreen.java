package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreateProfileScreen implements IScreen {
    private Scene scene;

    public CreateProfileScreen(Stage stage, ScreenController controller) {
        // Title label
        Label titleLabel = new Label("Create Profile");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Back to Sign In button
        Button backToSignInButton = new Button("Back to Sign In");
        backToSignInButton.setOnAction(e -> controller.showSignInScreen());

        // Layout for profile setup
        VBox layout = new VBox(15, titleLabel, backToSignInButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Scene for create profile screen
        scene = new Scene(layout, 800, 600);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
