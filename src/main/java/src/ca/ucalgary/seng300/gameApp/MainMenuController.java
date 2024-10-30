package src.main.java.src.ca.ucalgary.seng300.gameApp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML
    Button TicTacToeButton;
    @FXML
    Button CheckersButton;
    @FXML
    Button ChessButton;
    @FXML
    Button ExitButton;
    private Stage primaryStage;
    private Scene checkersScene;
    private Scene ticTacToeScene;
    @FXML
    private void onExitClick() {
        Platform.exit();
        System.exit(0);
    }
    @FXML
    private void onAboutClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Message");
        alert.setContentText("");
        alert.showAndWait();
    }

    public void initialize(Stage primaryStage, Scene checkersScene, Scene ticTacToeScene) {
        this.primaryStage = primaryStage;
        this.checkersScene = checkersScene;
        this.ticTacToeScene = ticTacToeScene;
    }

    @FXML
    private void goToCheckers() {
        primaryStage.setScene(checkersScene);
        primaryStage.setTitle("Checkers");
    }

    @FXML
    private void goToTicTacToe() {
        primaryStage.setScene(ticTacToeScene);
        primaryStage.setTitle("TicTacToe");
    }
}