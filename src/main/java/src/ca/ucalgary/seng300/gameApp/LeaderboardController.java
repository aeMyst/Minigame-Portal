package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeaderboardController implements IScreen {
    private Scene scene;
    private Stage stage;

    public LeaderboardController(Stage stage, ScreenController controller) {
        this.stage = stage;

        // Buttons to navigate to each specific leaderboard screen
        Button ticTacToeButton = new Button("Tic-Tac-Toe Leaderboard");
        ticTacToeButton.setOnAction(e -> showTicTacToeLeaderboard());

        Button checkersButton = new Button("Checkers Leaderboard");
        checkersButton.setOnAction(e -> showCheckersLeaderboard());

        Button connectFourButton = new Button("Connect Four Leaderboard");
        connectFourButton.setOnAction(e -> showConnectFourLeaderboard());

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for the leaderboard menu
        VBox layout = new VBox(20, ticTacToeButton, checkersButton, connectFourButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        scene = new Scene(layout, 800, 600);
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
