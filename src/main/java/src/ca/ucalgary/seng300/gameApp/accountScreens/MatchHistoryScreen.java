package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.network.Client;

public class MatchHistoryScreen implements IScreen {
    private Scene scene;

    public MatchHistoryScreen(Stage stage, ScreenController controller, Client client) {
        Button backButton = new Button("back");
        backButton.setOnAction(e -> controller.showUserProfileScreen());

        String profileInfo = client.getCurrentUserProfile();

        VBox layout = new VBox(20, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    private void displayHistory(String[][] player) {
        VBox historyBox = new VBox(5);
        historyBox.setPadding(new Insets(10));
    }


    @Override
    public Scene getScene() {
        return scene;
    }
}
