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
 * Represents the screen displaying the rules for the Checkers game.
 * Implements the IScreen interface to provide the screen's Scene.
 */
public class CheckersRules implements IScreen {
    private Scene scene;

    /**
     * Constructs the CheckersRules screen with the given parameters.
     *
     * @param stage      the main stage of the application.
     * @param controller the screen controller to handle screen transitions.
     * @param client     the client used to retrieve the rules text.
     */
    public CheckersRules(Stage stage, ScreenController controller, Client client) {
        // Retrieve the path to the rules file from the server
        String filePathFromServer = client.getRulesPath(2);

        // Load the rules content using a utility class
        String rulesText = RulesUtility.getRules(filePathFromServer);

        // Create the title label for the rules screen
        Label rulesTitle = new Label("CHECKERS' RULES:");
        rulesTitle.getStyleClass().add("rules-title");

        // Create a label to display the rules content
        Label content = new Label(rulesText);
        content.getStyleClass().add("rules-content");

        // Create a back button to navigate to the help screen
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("rules-button");
        backButton.getStyleClass().add("rules-button-back");
        backButton.setOnAction(e -> controller.showHelpScreen());// Define action for the button

        // Arrange components in a vertical layout
        VBox layout = new VBox(15, rulesTitle, content, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("rules-pane");

        // Create a BorderPane and set the layout to the center
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(layout);

        // Initialize the scene with the main pane and set dimensions
        scene = new Scene(mainPane, 1280, 900);

        // Load and apply the external stylesheet
        scene.getStylesheets().add((getClass().getClassLoader().getResource("RulesSyles.css").toExternalForm()));
    }

    /**
     * Returns the scene associated with this screen.
     *
     * @return the Scene object for the rules screen.
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}

