package src.ca.ucalgary.seng300.gameApp.extraScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gameApp.Utility.RulesUtility;
import src.ca.ucalgary.seng300.network.Client;

public class TTTRules implements IScreen {
    private Scene scene;

    public TTTRules(Stage stage, ScreenController controller, Client client) {
        String filePathFromServer = client.getRulesPath(1);
        String rulesText = RulesUtility.getRules(filePathFromServer);

        Label rulesTitle = new Label("TICTACTOE RULES:");
        rulesTitle.getStyleClass().add("rules-title");

        Label content = new Label(rulesText);
        content.getStyleClass().add("rules-content");

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("rules-button");
        backButton.getStyleClass().add("rules-button-back");
        backButton.setOnAction(e -> controller.showHelpScreen());

        VBox layout = new VBox(15, rulesTitle, content, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("rules-pane");

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(layout);

        // Scene
        scene = new Scene(mainPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("RulesSyles.css").toExternalForm()));
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}

