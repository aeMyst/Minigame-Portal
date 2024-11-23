package src.ca.ucalgary.seng300.gameApp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PlayerProfileScreen implements IScreen {
    private Scene scene;

    public PlayerProfileScreen(Stage stage, ScreenController controller) {
        Button gamesButton = new Button("something");
        gamesButton.setOnAction(e -> controller.showGameMenu());
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
