package src.ca.ucalgary.seng300.gameApp.extraScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

public class HelpScreen implements IScreen {
    private Scene scene;

    public HelpScreen(Stage stage, ScreenController controller, Client client) {

        // Title Label
        Label information = new Label("GAME RULES:");
        information.getStyleClass().add("rules-title");

        // Back Button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("rules-button");
        backButton.getStyleClass().add("rules-button-back");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Tic-Tac-Toe Rules Button
        Button TTTHelp = new Button("Tic-Tac-Toe Rules");
        TTTHelp.getStyleClass().add("rules-button");
        TTTHelp.getStyleClass().add("rules-button-primary");
        TTTHelp.setOnAction(e -> controller.showTTTRules());

        // Connect Four Rules Button
        Button C4Help = new Button("Connect Four Rules");
        C4Help.getStyleClass().add("rules-button");
        C4Help.getStyleClass().add("rules-button-primary");
        C4Help.setOnAction(e -> controller.showConnectFourRules());

        // Checkers' Rules Button
        Button CHHelp = new Button("Checkers' Rules");
        CHHelp.getStyleClass().add("rules-button");
        CHHelp.getStyleClass().add("rules-button-primary");
        CHHelp.setOnAction(e -> controller.showCheckersRules());

        // Layout
        VBox helpLayout = new VBox(15, information, TTTHelp, C4Help, CHHelp, backButton);
        helpLayout.setAlignment(Pos.CENTER);
        helpLayout.setPadding(new Insets(20));
        helpLayout.getStyleClass().add("rules-pane");

        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(helpLayout);

        // Scene
        scene = new Scene(mainMenuPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("RulesSyles.css").toExternalForm()));
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}

