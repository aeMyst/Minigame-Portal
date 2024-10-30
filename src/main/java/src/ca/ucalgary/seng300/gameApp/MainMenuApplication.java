package src.ca.ucalgary.seng300.gameApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuApplication extends Application {
    private Scene checkersScene;
    private Scene ticTacToeScene;
    private Scene mainMenuScene;
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("MainMenuScene.fxml"));
        mainMenuScene = new Scene(mainMenuLoader.load());
        MainMenuController mainMenuController = mainMenuLoader.getController();


        FXMLLoader ticTacToeLoader = new FXMLLoader(getClass().getResource("TicTacToeScene.fxml"));
        ticTacToeScene = new Scene(ticTacToeLoader.load());
        TicTacToeController ticTacToeController = ticTacToeLoader.getController();

        // Initialize controllers with necessary references
        mainMenuController.initialize(primaryStage, checkersScene, ticTacToeScene);
        ticTacToeController.initialize(primaryStage, mainMenuScene);

        // Set the main menu scene and show the stage
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}