package src.ca.ucalgary.seng300.gameApp.extraScreens;

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
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gameApp.Utility.RulesUtility;
import src.ca.ucalgary.seng300.network.Client;

public class ConnectFourRules implements IScreen {
    private Scene scene;

    public ConnectFourRules(Stage stage, ScreenController controller, Client client) {
        String filePath = "src/main/java/src/ca/ucalgary/seng300/database/connect_four_rules.txt";
        String rulesText = RulesUtility.getRules(filePath);

        Label Rules = new Label("Connect Four Rules: ");
        Rules.setFont(new Font("Arial", 24));
        Rules.setTextFill(Color.DARKBLUE);

        Label content = new Label(rulesText);
        content.setStyle("-fx-font-weight: bold");
        Rules.setFont(new Font("Arial", 24));

        Button backButton = new Button("back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showHelpScreen());

        VBox layout = new VBox(15, Rules, content, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f8ff;");

        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(layout);

        // Scene for the main menu
        scene = new Scene(mainMenuPane, 1280, 900);
    }

    @Override
    public Scene getScene() { return scene; }
}
