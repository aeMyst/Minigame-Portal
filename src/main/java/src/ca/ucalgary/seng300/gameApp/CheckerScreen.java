package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Client;
import src.ca.ucalgary.seng300.gamelogic.games.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.games.Checkers.PlayerID;

public class CheckerScreen implements IScreen {
    private Scene scene;
    private Client client;
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

                // Set button background color
                if ((row + col) % 2 == 0) {
                    button.setStyle("-fx-background-color: #FFFFFF;");
                } else {
                    button.setStyle("-fx-background-color: #000000;");
                    int piece = board[row][col];
                    if (piece == 1) {
                        button.setText("B");
                        button.setTextFill(Color.BLUE);
                    } else if (piece == 2) {
                        button.setText("R");
                        button.setTextFill(Color.RED);
                    } else if (piece == 3) { // Player 1 King
                        button.setText("BK");
                        button.setTextFill(Color.BLUE);
                    } else if (piece == 4) { // Player 2 King
                        button.setText("RK");
                        button.setTextFill(Color.RED);
                    }
                }

                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c));
                boardButtons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }
        return gameBoard;
    }

    private void handleMove(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            // No piece is currently selected
            if (gameLogic.playerSelectedPiece(row, col, gameLogic.getCurrentPlayer())) {
                selectedRow = row;
                selectedCol = col;
                highlightPossibleMoves(row, col);
            } else {
                chatArea.appendText("Invalid piece selection. Try again.\n");
            }
        } else {
            // A piece is already selected
            if (selectedRow == row && selectedCol == col) {
                // Deselect the piece if the player clicks on it again
                selectedRow = -1;
                selectedCol = -1;
                clearHighlights();
            } else if (gameLogic.playerSelectedPiece(row, col, gameLogic.getCurrentPlayer())) {
                // Select a different piece
                selectedRow = row;
                selectedCol = col;
                clearHighlights();
                highlightPossibleMoves(row, col);
            } else {
                // Attempt to move to the selected square
                int fromRow = selectedRow;
                int fromCol = selectedCol;
                int toRow = row;
                int toCol = col;
                PlayerID currentPlayer = gameLogic.getCurrentPlayer();
                if (gameLogic.isValidCapture(fromRow, fromCol, toRow, toCol, currentPlayer)) {
                    gameLogic.playerCapturedPiece(fromRow, fromCol, toRow, toCol, currentPlayer);
                    updateBoard();

                    // Check for additional captures
                    if (gameLogic.hasValidCapture(toRow, toCol, currentPlayer)) {
                        selectedRow = toRow;
                        selectedCol = toCol;
                        clearHighlights();
                        highlightPossibleMoves(toRow, toCol);
                    } else {
                        if (checkGameState()) {
                            return;
                        }
                        selectedRow = -1;
                        selectedCol = -1;
                        clearHighlights();
                        gameLogic.switchPlayer();
                        turnIndicator.setText("Turn: Player " + gameLogic.getCurrentPlayer());
                    }
                } else if (gameLogic.isValidMove(fromRow, fromCol, toRow, toCol, currentPlayer)) {
                    if (gameLogic.hasAnyValidCaptures(currentPlayer)) {
                        chatArea.appendText("You must capture if possible.\n");
                        clearHighlights();
                        selectedRow = -1;
                        selectedCol = -1;
                    } else {
                        gameLogic.playerMovedPiece(fromRow, fromCol, toRow, toCol, currentPlayer);
                        updateBoard();
                        selectedRow = -1;
                        selectedCol = -1;
                        clearHighlights();
                        gameLogic.switchPlayer();
                        turnIndicator.setText("Turn: Player " + gameLogic.getCurrentPlayer());
                    }
                } else {
                    chatArea.appendText("Invalid move. Try again.\n");
                    // Keep the selected piece highlighted
                }
            }
        }
    }



    private void highlightPossibleMoves(int row, int col) {
        PlayerID currentPlayer = gameLogic.getCurrentPlayer();
        boolean mustCapture = gameLogic.hasAnyValidCaptures(currentPlayer);
        boolean pieceHasCapture = gameLogic.hasValidCapture(row, col, currentPlayer);

        clearHighlights(); // Clear previous highlights

        boolean hasValidMove = false;

        if (mustCapture && !pieceHasCapture) {
            chatArea.appendText("You must capture with a piece that can capture.\n");
            selectedRow = -1;
            selectedCol = -1;
            return;
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (pieceHasCapture && gameLogic.isValidCapture(row, col, r, c, currentPlayer)) {
                    boardButtons[r][c].setStyle("-fx-background-color: #FF0000;"); // Red for captures
                    hasValidMove = true;
                } else if (!mustCapture && gameLogic.isValidMove(row, col, r, c, currentPlayer)) {
                    boardButtons[r][c].setStyle("-fx-background-color: #00FF00;"); // Green for moves
                    hasValidMove = true;
                }
            }
        }

        if (!hasValidMove) {
            chatArea.appendText("No valid moves available for the selected piece.\n");
            selectedRow = -1;
            selectedCol = -1;
            clearHighlights();
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
                int piece = board[row][col];
                if (piece == 1) {
                    button.setText("B");
                    button.setTextFill(Color.BLUE);
                } else if (piece == 2) {
                    button.setText("R");
                    button.setTextFill(Color.RED);
                } else if (piece == 3) { // Player 1 King
                    button.setText("BK");
                    button.setTextFill(Color.BLUE);
                } else if (piece == 4) { // Player 2 King
                    button.setText("RK");
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
    private boolean checkGameState() {
        int whiteCount = 0;
        int blackCount = 0;

        int[][] board = gameLogic.getBoard();
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 1 || cell == 3) {
                    whiteCount++;
                } else if (cell == 2 || cell == 4) {
                    blackCount++;
                }
            }
        }

        if (whiteCount == 0) {
            controller.showEndGameScreen(2, null, null, gameLogic); // Adjust game type accordingly
            return true;
        } else if (blackCount == 0) {
            controller.showEndGameScreen(2, null, null, gameLogic); // Adjust game type accordingly
            return true;
        }

        return false;
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
