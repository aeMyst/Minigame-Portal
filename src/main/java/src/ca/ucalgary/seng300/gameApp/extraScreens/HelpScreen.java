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
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class HelpScreen implements IScreen {
    private Scene scene;

    public HelpScreen(Stage stage, ScreenController controller, Client client) {

        Label information = new Label("Game Rules:");
        information.setFont(new Font("Arial", 24));
        information.setTextFill(Color.DARKBLUE);

        Button backButton = new Button("back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        Button TTTHelp = new Button("Tic-Tac-Toe Rules");
        TTTHelp.setFont(new Font("Arial", 16));
        TTTHelp.setPrefWidth(200);
        TTTHelp.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        TTTHelp.setOnAction(e -> controller.showTTTRules());

        Button C4Help = new Button("Connect Four Rules");
        C4Help.setFont(new Font("Arial", 16));
        C4Help.setPrefWidth(200);
        C4Help.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        C4Help.setOnAction(e -> controller.showConnectFourRules());

        Button CHHelp = new Button("Checkers' Rules");
        CHHelp.setFont(new Font("Arial", 16));
        CHHelp.setPrefWidth(200);
        CHHelp.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        CHHelp.setOnAction(e -> controller.showCheckersRules());

        VBox helpLayout = new VBox(15, information, TTTHelp, C4Help, CHHelp, backButton);
        helpLayout.setAlignment(Pos.CENTER);
        helpLayout.setPadding(new Insets(20));
        helpLayout.setStyle("-fx-background-color: #f0f8ff;");

        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(helpLayout);

        // Scene for the main menu
        scene = new Scene(mainMenuPane, 1280, 900);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
