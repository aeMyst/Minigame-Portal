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

/**
 * Represents the help screen in the application.
 * Allows user to view different rules for checkers, connect 4, and tic-tac-toe.
 */
public class HelpScreen implements IScreen {
    private Scene scene;

    /**
     * Constructs the HelpScreen and sets up the interface elements.
     *
     * @param stage The primary stage of the application.
     * @param controller The screen controller for navigating between different screens.
     * @param client The client to communicate with the server.
     */
    public HelpScreen(Stage stage, ScreenController controller, Client client) {
        // Title for help screen
        Label information = new Label("Game Rules:");
        information.setFont(new Font("Arial", 24));
        information.setTextFill(Color.DARKBLUE);

        // Button to go back to the main menu
        Button backButton = new Button("back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Button to view rules for tic-tac-toe
        Button TTTHelp = new Button("Tic-Tac-Toe Rules");
        TTTHelp.setFont(new Font("Arial", 16));
        TTTHelp.setPrefWidth(200);
        TTTHelp.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        TTTHelp.setOnAction(e -> controller.showTTTRules());

        // Button to view rules for connect 4
        Button C4Help = new Button("Connect Four Rules");
        C4Help.setFont(new Font("Arial", 16));
        C4Help.setPrefWidth(200);
        C4Help.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        C4Help.setOnAction(e -> controller.showConnectFourRules());

        // Button to view rules for checkers
        Button CHHelp = new Button("Checkers' Rules");
        CHHelp.setFont(new Font("Arial", 16));
        CHHelp.setPrefWidth(200);
        CHHelp.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        CHHelp.setOnAction(e -> controller.showCheckersRules());

        // Arrange all elements into a VBox layout
        VBox helpLayout = new VBox(15, information, TTTHelp, C4Help, CHHelp, backButton);
        helpLayout.setAlignment(Pos.CENTER);
        helpLayout.setPadding(new Insets(20));
        helpLayout.setStyle("-fx-background-color: #f0f8ff;");

        // Use a BorderPane to structure the main layout of the screen
        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(helpLayout);

        // Initialize the scene for the help screen
        scene = new Scene(mainMenuPane, 1280, 900);
    }

    /**
     * Returns the constructed scene for the help screen.
     *
     * @return The scene representing the help screen.
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
