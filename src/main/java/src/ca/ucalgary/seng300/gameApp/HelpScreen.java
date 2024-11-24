package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Client;

public class HelpScreen implements IScreen {
    private Scene scene;

    public HelpScreen(Stage stage, ScreenController controller, Client client) {

        Label information = new Label("Some kind of Information Here");
        information.setFont(new Font("Arial", 24));
        information.setTextFill(Color.DARKBLUE);

        Button gamesButton = new Button("back");
        gamesButton.setFont(new Font("Arial", 16));
        gamesButton.setPrefWidth(200);
        gamesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        gamesButton.setOnAction(e -> controller.showMainMenu());

        VBox helpLayout = new VBox(15, information, gamesButton);
        helpLayout.setAlignment(Pos.CENTER);
        helpLayout.setPadding(new Insets(20));
        helpLayout.setStyle("-fx-background-color: #f0f8ff;");


        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(helpLayout);

        // Scene for the main menu
        scene = new Scene(mainMenuPane, 800, 600);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
