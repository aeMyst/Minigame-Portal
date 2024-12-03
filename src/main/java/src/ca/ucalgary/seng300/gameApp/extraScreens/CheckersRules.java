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
 * Represents the rules screen for checkers in the application.
 * Displays rules of the game for checkers fetched from the server for user to view.
 */
public class CheckersRules implements IScreen {
    private Scene scene;

    /**
     * Constructs the CheckersRules screen and sets up the interface elements.
     *
     * @param stage The primary stage of the application.
     * @param controller The screen controller for navigating between different screens.
     * @param client The client to communicate with the server.
     */
    public CheckersRules(Stage stage, ScreenController controller, Client client) {
        // Fetch the rules file path for checkers from the server
        String filePathFromServer = client.getRulesPath(2);

        // Load the rules for checkers using a utility class
        String rulesText = RulesUtility.getRules(filePathFromServer);

        Label rulesTitle = new Label("CHECKERS' RULES:");
        rulesTitle.getStyleClass().add("rules-title");

        // Content label for rules display
        Label content = new Label(rulesText);
        content.getStyleClass().add("rules-content");

        // Button to go back to the help screen
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("rules-button");
        backButton.getStyleClass().add("rules-button-back");
        backButton.setOnAction(e -> controller.showHelpScreen());

        // Arrange all the necessary elements into a VBox layout
        VBox layout = new VBox(15, rulesTitle, content, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("rules-pane");

        // Use a BorderPane to structure the main layout of the screen
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(layout);

        // Initialize the scene for the checkers' rules screen
        scene = new Scene(mainPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("RulesSyles.css").toExternalForm()));
    }

    /**
     * Returns the constructed scene for the checkers' rules screen.
     *
     * @return The scene representing the checkers' rules screen.
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
