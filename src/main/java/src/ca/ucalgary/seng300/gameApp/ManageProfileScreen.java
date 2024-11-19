package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ManageProfileScreen implements IScreen {
    private Scene scene;

    public ManageProfileScreen(Stage stage, ScreenController controller) {

        // Label for Manage Profile title
        Label titleLabel = new Label("Manage Profile");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Label and field for changing username
        Label changeUsernameLabel = new Label("Change Username:");
        TextField changeUsernameField = new TextField();

        // Label and field for changing password
        Label changePasswordLabel = new Label("Change Password:");
        PasswordField changePasswordField = new PasswordField();

        // Button for updating new profile changes
        // Button for going back
        // Layout
        // Display Error messages
        // Alert window for view profile/close
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
