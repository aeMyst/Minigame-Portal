package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gamelogic.games.Checkers.CheckersGameLogic;

public class CheckerScreen implements IScreen {
    private Scene scene;
    private final CheckersGameLogic gameLogic;
    private Label turnIndicator;
    private TextArea chatArea;
    private TextField chatInput;
    private Button[][] boardButtons;
    private final ScreenController controller;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public CheckerScreen(Stage stage, ScreenController controller, CheckersGameLogic gameLogic) {
        this.controller = controller;
        this.gameLogic = gameLogic;

        Label titleLabel = new Label("Checkers");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        turnIndicator = new Label("Turn: Player " + gameLogic.getCurrentPlayer());
        turnIndicator.setFont(new Font("Arial", 18));
        turnIndicator.setTextFill(Color.DARKGREEN);

        boardButtons = new Button[8][8];
        GridPane gameBoard = createGameBoard();

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(150);
        chatArea.setStyle("-fx-control-inner-background: #f8f8ff; -fx-text-fill: black; -fx-font-size: 14px;");

        chatInput = new TextField();
        chatInput.setPromptText("Type your message...");
        chatInput.setStyle("-fx-background-color: #e0e0e0;");
        chatInput.setOnAction(e -> sendMessage());

        Button sendButton = new Button("Send");
        sendButton.setFont(new Font("Arial", 14));
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        sendButton.setOnAction(e -> sendMessage());

        Button backButton = new Button("Back to Menu");
        backButton.setFont(new Font("Arial", 16));
        backButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        HBox chatBox = new HBox(10, chatInput, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, titleLabel, turnIndicator, gameBoard, chatArea, chatBox, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        scene = new Scene(layout, 800, 600);
    }

    private GridPane createGameBoard() {
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setPadding(new Insets(10));

        int[][] board = gameLogic.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button();
                button.setPrefSize(80, 80);
                if ((row + col) % 2 == 0) {
                    button.setStyle("-fx-background-color: #FFFFFF;");
                } else {
                    button.setStyle("-fx-background-color: #000000;");
                    if (board[row][col] == 1) {
                        button.setText("B");
                        button.setTextFill(Color.BLUE);
                    } else if (board[row][col] == 2) {
                        button.setText("R");
                        button.setTextFill(Color.RED);
                    }
                }
                final int r = row, c = col;
                button.setOnAction(e -> handleSquareClick(r, c));
                boardButtons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }
        return gameBoard;
    }

    private void handleSquareClick(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            if (gameLogic.playerSelectedPiece(row, col, gameLogic.getCurrentPlayer())) {
                selectedRow = row;
                selectedCol = col;
                highlightPossibleMoves(row, col);
            } else {
                chatArea.appendText("Invalid piece selection. Try again.\n");
            }
        } else {
            if (gameLogic.playerMovedPiece(selectedRow, selectedCol, row, col, gameLogic.getCurrentPlayer())) {
                clearHighlights();
                updateBoard();
                gameLogic.switchPlayer();
                turnIndicator.setText("Turn: Player " + gameLogic.getCurrentPlayer());
            } else {
                chatArea.appendText("Invalid move. Try again.\n");
                clearHighlights();
            }
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    private void highlightPossibleMoves(int row, int col) {
        int[][] board = gameLogic.getBoard();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (gameLogic.isValidMove(row, col, r, c, gameLogic.getCurrentPlayer())) {
                    boardButtons[r][c].setStyle("-fx-background-color: #00FF00;");
                }
            }
        }
    }

    private void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 0) {
                    boardButtons[r][c].setStyle("-fx-background-color: #FFFFFF;");
                } else {
                    boardButtons[r][c].setStyle("-fx-background-color: #000000;");
                }
            }
        }
    }

    private void updateBoard() {
        int[][] board = gameLogic.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = boardButtons[row][col];
                if (board[row][col] == 1) {
                    button.setText("B");
                    button.setTextFill(Color.BLUE);
                } else if (board[row][col] == 2) {
                    button.setText("R");
                    button.setTextFill(Color.RED);
                } else {
                    button.setText("");
                }
            }
        }
    }

    private void sendMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty()) {
            chatArea.appendText(gameLogic.getCurrentPlayer() + ": " + message + "\n");
            chatInput.clear();
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}