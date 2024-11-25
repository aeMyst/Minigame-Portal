package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Client;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.*;

public class TictactoeGameScreen {
    private Scene scene;
    private String currentPlayer = "X";
    private Button[][] buttons = new Button[3][3];
    private Label turnIndicator;
    private TextArea chatArea; // Chat display area
    private TextField chatInput; // Chat input field
    private BoardManager boardManager;
    private PlayerManager playerManager;
    private Client client;
    private Stage stage;
    private String status = "ONGOING";

    public TictactoeGameScreen(Stage stage, ScreenController controller, Client client) {
        this.stage = stage;
        this.client = client;

        boardManager = new BoardManager();
        playerManager = new PlayerManager(new HumanPlayer('X'), new HumanPlayer('O'));

        // Game Board
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);

        // create interactables for game
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button(" ");
                button.setFont(new Font("Impact", 32));
                button.setPrefSize(100, 100);
                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c, controller));
                buttons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }

        // game title
        Label title = new Label("Tic-Tac-Toe Game");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.DARKBLUE);

        // Turn Indicator
        turnIndicator = new Label("Turn: Player " + currentPlayer);
        turnIndicator.setFont(new Font("Arial", 18));
        turnIndicator.setTextFill(Color.DARKGREEN);

        // Chatroom
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(150);
        chatArea.setWrapText(true);
        chatArea.setStyle("-fx-control-inner-background: #f8f8ff; -fx-text-fill: black; -fx-font-size: 14px;");

        chatInput = new TextField();
        chatInput.setPromptText("Type your message...");
        chatInput.setOnAction(e -> sendMessage());

        Button sendButton = new Button("Send");
        sendButton.setFont(new Font("Arial", 16));
        sendButton.setPrefWidth(100);
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        sendButton.setOnAction(e -> sendMessage());

        HBox chatBox = new HBox(10, chatInput, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        VBox chatLayout = new VBox(10, chatArea, chatBox);
        chatLayout.setAlignment(Pos.CENTER);
        chatLayout.setPadding(new Insets(10));

        Button forfeitButton = new Button("Forfeit");
        forfeitButton.setFont(new Font("Arial", 16));
        forfeitButton.setPrefWidth(200);
        forfeitButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
        forfeitButton.setOnAction(e -> controller.showMainMenu());

        // Main Layout
        VBox layout = new VBox(15, title, turnIndicator, gameBoard, chatLayout, forfeitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        scene = new Scene(layout, 800, 600); // Fixed size
    }

    private boolean moveInProgress = false;

    private void handleMove(int row, int col, ScreenController controller) {
        if (moveInProgress) {
            return;
        }

        moveInProgress = true;
        disableBoard();

        HumanPlayer currentPlayerObj = playerManager.getCurrentPlayer();

        if (boardManager.isValidMove(row, col)) {
            boardManager.placeSymbol(currentPlayerObj.getSymbol(), row, col);

            client.sendMoveToServer(boardManager, playerManager, status, () -> {
                // Update GUI after "server acknowledgment"
                buttons[row][col].setText(String.valueOf(currentPlayerObj.getSymbol()));

                // Check for game outcomes
                if (boardManager.isWinner(currentPlayerObj.getSymbol())) {
                    this.status = "DONE";
                    System.out.println("Winner Found, Game Status: " + status);
                    System.out.println("==========================");
                    controller.showEndGameScreen(0, boardManager, null, null);

                } else if (boardManager.isTie()) {
                    this.status = "DONE";
                    System.out.println("Tie Found, Game Status: " + status);
                    System.out.println("==========================");
                    controller.showEndGameScreen(0, boardManager, null, null);

                } else {
                    playerManager.switchPlayer();
                    currentPlayer = String.valueOf(playerManager.getCurrentPlayer().getSymbol());
                    turnIndicator.setText("Turn: Player " + currentPlayer);
                }

                moveInProgress = false;
                enableBoard();
            });
        } else {
            chatArea.appendText("Server: Please make a valid move.\n");
            moveInProgress = false;
            enableBoard();
        }
    }

    private void disableBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }

    private void enableBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setDisable(false);
            }
        }
    }

    private void sendMessage() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            chatArea.appendText("Player: " + message + "\n");
            chatInput.clear();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
