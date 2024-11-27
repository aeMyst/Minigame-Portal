package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.leaderboardScreens.CheckerLB;
import src.ca.ucalgary.seng300.gameApp.leaderboardScreens.Connect4LB;
import src.ca.ucalgary.seng300.gameApp.leaderboardScreens.TictactoeLB;

public class LeaderboardController implements IScreen {
    private Scene scene;
    private Stage stage;

    public LeaderboardController(Stage stage, ScreenController controller, Client client) {
        this.stage = stage;

        // Buttons to navigate to each specific leaderboard screen
        Button ticTacToeButton = new Button("Tic-Tac-Toe Leaderboard");
        ticTacToeButton.setFont(new Font("Arial", 16));
        ticTacToeButton.setPrefWidth(300);
        ticTacToeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        ticTacToeButton.setOnAction(e -> showTicTacToeLeaderboard());

        Button checkersButton = new Button("Checkers Leaderboard");
        checkersButton.setFont(new Font("Arial", 16));
        checkersButton.setPrefWidth(300);
        checkersButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        checkersButton.setOnAction(e -> showCheckersLeaderboard());

        Button connectFourButton = new Button("Connect Four Leaderboard");
        connectFourButton.setFont(new Font("Arial", 16));
        connectFourButton.setPrefWidth(300);
        connectFourButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        connectFourButton.setOnAction(e -> showConnectFourLeaderboard());

        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(300);
        backButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for the leaderboard menu
        VBox layout = new VBox(20, ticTacToeButton, checkersButton, connectFourButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 1280, 900);
    }

    private void showConnectFourLeaderboard() {
        Connect4LB ticTacToeLeaderBoardScreen = new Connect4LB(stage, this);
        stage.setScene(ticTacToeLeaderBoardScreen.getScene());
    }


    // Methods to display each leaderboard screen
    private void showTicTacToeLeaderboard() {
        TictactoeLB ticTacToeLeaderBoardScreen = new TictactoeLB(stage, this);
        stage.setScene(ticTacToeLeaderBoardScreen.getScene());
    }

    private void showCheckersLeaderboard() {
        CheckerLB checkersLeaderBoardScreen = new CheckerLB(stage, this);
        stage.setScene(checkersLeaderBoardScreen.getScene());
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
