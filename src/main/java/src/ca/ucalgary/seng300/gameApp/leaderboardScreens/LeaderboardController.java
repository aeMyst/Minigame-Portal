package src.ca.ucalgary.seng300.gameApp.leaderboardScreens;

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
import src.ca.ucalgary.seng300.network.Client;

public class LeaderboardController implements IScreen {
    private Scene scene;
    private Stage stage;
    private Client client;

    public LeaderboardController(Stage stage, ScreenController controller, Client client) {
        this.stage = stage;
        this.client = client;

        // Title Label
        Label titleLabel = new Label("CHOOSE LEADERBOARDS");
        titleLabel.getStyleClass().add("leaderboard-title");

        // Buttons to navigate to each specific leaderboard screen
        Button ticTacToeButton = new Button("Tic-Tac-Toe Leaderboard");
        ticTacToeButton.getStyleClass().add("leaderboard-button");
        ticTacToeButton.getStyleClass().add("leaderboard-button-primary");
        ticTacToeButton.setOnAction(e -> showTicTacToeLeaderboard());

        Button checkersButton = new Button("Checkers Leaderboard");
        checkersButton.getStyleClass().add("leaderboard-button");
        checkersButton.getStyleClass().add("leaderboard-button-primary");
        checkersButton.setOnAction(e -> showCheckersLeaderboard());

        Button connectFourButton = new Button("Connect Four Leaderboard");
        connectFourButton.getStyleClass().add("leaderboard-button");
        connectFourButton.getStyleClass().add("leaderboard-button-primary");
        connectFourButton.setOnAction(e -> showConnectFourLeaderboard());

        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("leaderboard-button");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for the leaderboard menu
        VBox layout = new VBox(20, titleLabel, ticTacToeButton, checkersButton, connectFourButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("leaderboard-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("LeaderboardStyles.css").toExternalForm()));
    }

    // Methods to display each leaderboard screen
    private void showTicTacToeLeaderboard() {
        TictactoeLB ticTacToeLeaderBoardScreen = new TictactoeLB(stage, this, client);
        stage.setScene(ticTacToeLeaderBoardScreen.getScene());
    }

    private void showCheckersLeaderboard() {
        CheckerLB checkersLeaderBoardScreen = new CheckerLB(stage, this, client);
        stage.setScene(checkersLeaderBoardScreen.getScene());
    }

    private void showConnectFourLeaderboard() {
        Connect4LB connectFourLeaderBoardScreen = new Connect4LB(stage, this, client);
        stage.setScene(connectFourLeaderBoardScreen.getScene());
    }

    // Method to return to the leaderboard menu from any leaderboard screen
    public void showLeaderBoardMenu() {
        stage.setScene(scene);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
