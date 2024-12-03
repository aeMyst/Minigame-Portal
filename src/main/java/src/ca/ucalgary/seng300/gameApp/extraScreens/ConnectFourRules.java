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

/**
 * Represents the rule screen for connect 4 in the application.
 * Displays rules of the game for connect 4 fetched from the server for user to view.
 */
public class ConnectFourRules implements IScreen {
    private Scene scene;

    /**
     * Constructs the ConnectFourRules screen and sets up the interface elements.
     *
     * @param stage The primary stage of the application.
     * @param controller The screen controller for navigating between different screens.
     * @param client The client to communicate with the server.
     */
    public ConnectFourRules(Stage stage, ScreenController controller, Client client) {
        // Fetch the rules file path for connect 4 from the server
        String filePathFromServer = client.getRulesPath(1);

        // Load the rules for connect 4 using a utility class
        String rulesText = RulesUtility.getRules(filePathFromServer);

        // Title for connect 4 rules screen
        Label Rules = new Label("Connect Four Rules: ");
        Rules.setFont(new Font("Arial", 24));
        Rules.setTextFill(Color.DARKBLUE);

        // Content label for rules display
        Label content = new Label(rulesText);
        content.setStyle("-fx-font-weight: bold; "
                + "-fx-padding: 10; "
                + "-fx-border-color: black; "
                + "-fx-border-width: 2; "
                + "-fx-border-style: solid; "
                + "-fx-border-radius: 5; "
                + "-fx-background-color: #ffffff;");
        Rules.setFont(new Font("Arial", 24));

        // Button to go back to the help screen
        Button backButton = new Button("back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showHelpScreen());

        // Arrange all the necessary elements into a VBox layout
        VBox layout = new VBox(15, Rules, content, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f8ff;");

        // Use a BorderPane to structure the main layout of the screen
        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(layout);

        // Initialize the scene for the checkers' rules screen
        scene = new Scene(mainMenuPane, 1280, 900);
    }

    /**
     * Returns the constructed scene for the connect 4 rules screen.
     *
     * @return The scene representing the connect 4 rules screen.
     */
    @Override
    public Scene getScene() { return scene; }
}
