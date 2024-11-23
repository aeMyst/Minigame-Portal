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
    private Label gameOverMessage; // Label for game over message
    private HBox endGameControls; // Buttons for replay and exit
    private TextArea chatArea; // Chat display area
    private TextField chatInput; // Chat input field
    private BoardManager boardManager;
    private PlayerManager playerManager;
    private Client client;
    private Stage stage;

    public TictactoeGameScreen(Stage stage, ScreenController controller) {
        this.stage = stage;
        boardManager = new BoardManager();
        playerManager = new PlayerManager(new HumanPlayer('X'), new HumanPlayer('O'));
        client = new Client();

        // Game Board
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button(" ");
                button.setFont(new Font("Impact", 32));
                button.setPrefSize(100, 100);
                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c));
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

        // Game Over Message
        gameOverMessage = new Label();
        gameOverMessage.setFont(new Font("Arial", 20));
        gameOverMessage.setTextFill(Color.DARKRED);
        gameOverMessage.setVisible(false);

        // End Game Controls
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setFont(new Font("Arial", 16));
        playAgainButton.setPrefWidth(200);
        playAgainButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        playAgainButton.setOnAction(e -> resetGame());

        Button exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", 16));
        exitButton.setPrefWidth(200);
        exitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        exitButton.setOnAction(e -> controller.showMainMenu());

        endGameControls = new HBox(10, playAgainButton, exitButton);
        endGameControls.setAlignment(Pos.CENTER);
        endGameControls.setVisible(false);

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

        // Main Layout
        VBox layout = new VBox(15, title, turnIndicator, gameBoard, gameOverMessage, endGameControls, chatLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        scene = new Scene(layout, 800, 600); // Fixed size
    }

    private void handleMove(int row, int col) {
        if (!boardManager.isValidMove(row, col)) return;

        HumanPlayer currentPlayerObj = playerManager.getCurrentPlayer();

        client.sendMoveToServer(boardManager, playerManager, "ONGOING", () -> {
            // Update GUI after "server acknowledgment"
            buttons[row][col].setText(String.valueOf(currentPlayerObj.getSymbol()));
            buttons[row][col].setDisable(true);

            boardManager.placeSymbol(currentPlayerObj.getSymbol(), row, col);

            // Check for game outcomes
            if (boardManager.isWinner(currentPlayerObj.getSymbol())) {
                showEndGameMessage("Player " + currentPlayerObj.getSymbol() + " wins!");
            } else if (boardManager.isTie()) {
                showEndGameMessage("It's a tie!");
            } else {
                playerManager.switchPlayer();
                currentPlayer = String.valueOf(playerManager.getCurrentPlayer().getSymbol());
                turnIndicator.setText("Turn: Player " + currentPlayer);
            }
        });
    }

    private void showEndGameMessage(String message) {
        gameOverMessage.setText(message);
        gameOverMessage.setVisible(true);
        endGameControls.setVisible(true);
        turnIndicator.setVisible(false);

        // Disable all buttons on the board
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    private void resetGame() {
        // Reset game state
        boardManager = new BoardManager();
        playerManager = new PlayerManager(new HumanPlayer('X'), new HumanPlayer('O'));
        this.currentPlayer = "X";
        turnIndicator.setText("Turn: Player " + this.currentPlayer);
        turnIndicator.setVisible(true);

        // Reset GUI board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setDisable(false);
            }
        }
        // Hide game over message and controls
        gameOverMessage.setVisible(false);
        endGameControls.setVisible(false);
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
