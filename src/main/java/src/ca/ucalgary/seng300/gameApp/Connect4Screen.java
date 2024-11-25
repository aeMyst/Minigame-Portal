package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Client;
import src.ca.ucalgary.seng300.gamelogic.games.Connect4.*;

public class Connect4Screen implements IScreen {
    private Scene scene;

    //placeholder users
    private UserPiece user1 = new UserPiece(1);
    private UserPiece user2 = new UserPiece(2);
    private UserPiece currentPlayer = user1;
    private Connect4Logic logicManager = new Connect4Logic();
    private TurnManager turnManager;
    private Client client;
    private String status;
    private Stage stage;
    private Button[][] gameButtons = new Button[6][7];
    private Label turnIndicator;
    private TextArea chatArea;
    private TextField chatInput;

    public Connect4Screen(Stage stage, ScreenController controller, Client client) {

        this.stage = stage;
        this.client = client;

        logicManager = new Connect4Logic();
        turnManager = new TurnManager(user1, user2);

        Label title = new Label("Connect4");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.DARKBLUE);

        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setPadding(new Insets(10));

        // 2d array of buttons corresponding to game board
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Button button = new Button();
                button.setPrefSize(40, 40);
                int finalCol = col;
                button.setOnAction(e -> gameButtonClicked(finalCol, controller));
                gameButtons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }

        turnIndicator = new Label("Player turn: Player " + currentPlayer.getPiece() + " (RED)");
        turnIndicator.setFont(new Font("Arial", 18));
        turnIndicator.setTextFill(Color.DARKGREEN);

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(150);
        chatArea.setStyle("-fx-control-inner-background: #f8f8ff; -fx-text-fill: black; -fx-font-size: 14px;");

        chatInput = new TextField();
        chatInput.setPromptText("Type your message...");
        chatInput.setPrefWidth(250);
        chatInput.setStyle("-fx-background-color: #e0e0e0;");
        chatInput.setOnAction(e -> sendMessage());

        Button sendButton = new Button("Send");
        sendButton.setFont(new Font("Arial", 14));
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        sendButton.setOnAction(e -> sendMessage());

        Button backToMenuButton = new Button("Back to Menu");
        backToMenuButton.setFont(new Font("Arial", 16));
        backToMenuButton.setPrefWidth(200);
        backToMenuButton.setOnAction(e -> controller.showMainMenu());

        HBox chatBox = new HBox(5, chatInput, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, title, turnIndicator, gameBoard, chatArea, chatBox, backToMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        scene = new Scene(layout, 800, 600);
    }

    private boolean moveInProgress = false;

    // handle move attempted by player when button on game board is clicked
    private void gameButtonClicked(int column, ScreenController controller) {
        if (moveInProgress) {
            return;
        }

        moveInProgress = true;
        disableBoard();

        //check for and make valid move
        if (logicManager.placePiece(logicManager.getBoard(), column, currentPlayer.getPiece())) {
            // find piece just placed to change corresponding button (i.e., place piece in GUI board)
            for (int rowCheck = 0; rowCheck < logicManager.getBoard().length; rowCheck++) {
                if (logicManager.getBoard()[rowCheck][column] == currentPlayer.getPiece()) {
                    if (currentPlayer == user1)
                        gameButtons[rowCheck][column].setStyle("-fx-background-color: #e74c3c");
                    else
                        gameButtons[rowCheck][column].setStyle("-fx-background-color: #3498db");
                    break;
                }
            }
            client.sendC4MoveToServer(logicManager, turnManager, status, () -> {
                if (logicManager.horizontalWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                    controller.showMainMenu();
                } else if (logicManager.verticalWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                    controller.showEndGameScreen();
                } else if (logicManager.forwardslashWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                    controller.showEndGameScreen();
                } else if (logicManager.backslashWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                    controller.showEndGameScreen();
                } else if (logicManager.boardFull(logicManager.getBoard())) {
                    // tie if board is full
                    status = "DONE";
                    controller.showEndGameScreen();
                }
                if (currentPlayer == user1) {
                    currentPlayer = user2;
                    turnIndicator.setText("Player turn: Player " + currentPlayer.getPiece() + " (BLUE)");
                } else {
                    currentPlayer = user1;
                    turnIndicator.setText("Player turn: Player " + currentPlayer.getPiece() + " (RED)");
                }
                enableBoard();
                moveInProgress = false;
            });
        } else {
            chatArea.appendText("Please make a valid move.\n");
            enableBoard();
            moveInProgress = false;
        }
    }

    private void disableBoard() {
        for (int i = 0; i < gameButtons.length; i++) {
            for (int j = 0; j < gameButtons[i].length; j++) {
                gameButtons[i][j].setDisable(true);
            }
        }
    }

    private void enableBoard() {
        for (int i = 0; i < gameButtons.length; i++) {
            for (int j = 0; j < gameButtons[i].length; j++) {
                gameButtons[i][j].setDisable(false);
            }
        }
    }

    private void sendMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty()) {
            chatArea.appendText("Player: " + message + "\n");
            chatInput.clear();
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
