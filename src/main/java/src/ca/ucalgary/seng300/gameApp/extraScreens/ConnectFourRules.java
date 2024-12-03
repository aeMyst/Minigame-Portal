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
/**
 * A class that creates a scene to display the rules for the connect 4 game
 */
public class ConnectFourRules implements IScreen {
    private Scene scene;

    /**
     * Constructes a ConnectFourRules object
     * 
     * @param stage The primary stage for the application
     * @param controller The Screencontroller to manage screen transitions
     * @param client The client to fetch the game rules from
     */
    public ConnectFourRules(Stage stage, ScreenController controller, Client client) {
        //load the riles text from the server
        String filePathFromServer = client.getRulesPath(1);
        String rulesText = RulesUtility.getRules(filePathFromServer);

        //create labels to display
        Label rulesTitle = new Label("CONNECT FOUR RULES:");
        rulesTitle.getStyleClass().add("rules-title");
        Label content = new Label(rulesText);
        content.getStyleClass().add("rules-content");

        //Create the back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("rules-button");
        backButton.getStyleClass().add("rules-button-back");
        backButton.setOnAction(e -> controller.showHelpScreen());

        //set the postions of everything 
        VBox layout = new VBox(15, rulesTitle, content, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("rules-pane");

        //Create a border pane
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(layout);

        // Scene
        scene = new Scene(mainPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("RulesSyles.css").toExternalForm()));
    }

    /**
     * Retrieves the connect 4 rules scene
     * 
     * @return the scene displaying teh connect 4 rules
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
