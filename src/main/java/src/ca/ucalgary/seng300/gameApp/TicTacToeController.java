package src.main.java.src.ca.ucalgary.seng300.gameApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
public class TicTacToeController {
    @FXML
    Label Player1_Label;
    @FXML
    Label Player2_Label;
    @FXML
    Label Round_Label;
    @FXML
    Label Score1_Label;
    @FXML
    Label Score2_Label;
    @FXML
    Label GameState_Label;
    private Stage primaryStage;
    private Scene mainMenuScene;
    private TTTGameState gameState = TTTGameState.ONGOING;
    private TTTPLayerState currentPlayer = TTTPLayerState.X;

    public void initialize(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }
    @FXML
    private void goBackToMenu() {
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Main Menu");
    }
    @FXML
    private ListView<String> chatDisplay;
    @FXML
    private TextField chatInput;
    @FXML
    private Button TTTBoardButton1;
    @FXML
    private Button TTTBoardButton2;
    @FXML
    private Button TTTBoardButton3;
    @FXML
    private Button TTTBoardButton4;
    @FXML
    private Button TTTBoardButton5;
    @FXML
    private Button TTTBoardButton6;
    @FXML
    private Button TTTBoardButton7;
    @FXML
    private Button TTTBoardButton8;
    @FXML
    private Button TTTBoardButton9;
    @FXML
    private void handleSendMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty()) {
            chatDisplay.getItems().add("Player: " + message); // Adds the message to the chat display
            chatInput.clear(); // Clears the input field
        }
    }
    @FXML
    private void handleTTTBoardButtonPress(ActionEvent event) {
        if (gameState != TTTGameState.ONGOING) {
            return; // Ignore clicks if the game is over
        }
        Button clickedButton = (Button) event.getSource();
        clickedButton.setText(currentPlayer.toString());
        GameState_Label.setText("Game = " + gameState.toString());
        clickedButton.setDisable(true);
        currentPlayer = (currentPlayer == TTTPLayerState.X) ? TTTPLayerState.O : TTTPLayerState.X;
    }
}