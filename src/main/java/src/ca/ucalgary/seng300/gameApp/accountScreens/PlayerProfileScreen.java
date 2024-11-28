package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class PlayerProfileScreen implements IScreen {
    private Scene scene;

    public PlayerProfileScreen(Stage stage, ScreenController controller, Client client) {
        Button gamesButton = new Button("something");
        gamesButton.setOnAction(e -> controller.showGameMenu());
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
