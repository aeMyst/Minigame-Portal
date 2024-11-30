package src.ca.ucalgary.seng300.gameApp.gameScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.Utility.ChatUtility;
import src.ca.ucalgary.seng300.gamelogic.Checkers.GameState;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Optional;

/**
 * This code represents the Checkers game screen.
 * Handles the game board creation, game logic integration, player interactions,
 * and chat functionalities/
 */
public class CheckerScreen implements IScreen {
    private Scene scene; // Main scene for the Checkers game
    private Client client; // Client for server interactions
    private final CheckersGameLogic gameLogic; // Game logic for Checkers
    private Label turnIndicator; // Displays the current player's turn
    private TextArea chatArea; // Area for displaying chat messages
    private TextField chatInput; // Input field for sending chat messages
    private Button[][] boardButtons; // Buttons representing the game board
    private final ScreenController controller; // Controller for managing screens
    private int selectedRow = -1;
    private int selectedCol = -1;

    private boolean isEmojiOpen = false; // Indicates if the emoji menu is open
    private ArrayList<Player> match;
    // https://www.tutorialspoint.com/javafx/javafx_images.htm
    private final String WHITE_PIECE_IMAGE_PATH = "src/main/java/src/ca/ucalgary/seng300/images/white_piece.png";
    private final String BLACK_PIECE_IMAGE_PATH = "src/main/java/src/ca/ucalgary/seng300/images/black_piece.png";
    private final String WHITE_KING_IMAGE_PATH = "src/main/java/src/ca/ucalgary/seng300/images/white_king_piece.png";
    private final String BLACK_KING_IMAGE_PATH = "src/main/java/src/ca/ucalgary/seng300/images/black_king_piece.png";

    /**
     * Constructor for CheckerScreen.
     *
     * @param stage      The primary stage of the application.
     * @param controller The screen controller for navigation between screens.
     * @param client     The client for server communication.
     * @param match      The list of players in the current match.
     */
    public CheckerScreen(Stage stage, ScreenController controller, Client client, ArrayList<Player> match) {
        this.controller = controller;
        this.client = client;
        this.match = match;

        Player playerOne = match.get(0);
        Player playerTwo = match.get(1);

        // Correctly assign to the class-level gameLogic field
        this.gameLogic = new CheckersGameLogic(playerOne, playerTwo);

        Label titleLabel = new Label("Checkers");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        turnIndicator = new Label("Turn: " + gameLogic.getCurrentPlayer().getPlayerID());
        turnIndicator.setFont(new Font("Arial", 18));
        turnIndicator.setTextFill(Color.DARKGREEN);

        // Game board creation
        boardButtons = new Button[8][8];
        GridPane gameBoard = createGameBoard();

        // Chat area for displaying messages
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(150);
        chatArea.setStyle("-fx-control-inner-background: #f8f8ff; -fx-text-fill: black; -fx-font-size: 14px;");

        // Chat input field
        chatInput = new TextField();
        chatInput.setPromptText("Type your message...");
        chatInput.setStyle("-fx-background-color: #e0e0e0;");
        chatInput.setOnAction(e -> sendMessage());

        // Send button
        Button sendButton = new Button("Send");
        sendButton.setFont(new Font("Arial", 16));
        sendButton.setPrefWidth(150);
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        sendButton.setOnAction(e -> sendMessage());

        // Emoji menu button
        Button emojiButton = new Button("Emoji Menu");
        emojiButton.setFont(new Font("Arial", 16));
        emojiButton.setPrefWidth(150);
        emojiButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        emojiButton.setOnAction(e -> {
            if (!isEmojiOpen) {
                isEmojiOpen = true;
                ChatUtility.showEmojiMenu(chatInput, stage, () -> isEmojiOpen = false, client);
            } else {
                chatArea.appendText("Server: Please select an Emoji or close the menu.\n");
            }
        });

        // Back to the Main Menu button
        Button backButton = new Button("Forfeit");
        backButton.setFont(new Font("Arial", 16));
        backButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Chat layout
        HBox chatBox = new HBox(10, chatInput, emojiButton, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        // Main layout for the Checkers game screen
        VBox layout = new VBox(15, titleLabel, turnIndicator, gameBoard, chatArea, chatBox, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        scene = new Scene(layout, 1280, 900);
    }

    /**
     * Creates the game board for Checkers.
     *
     * @return Checkers game board.
     */
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
                    button.setStyle("-fx-background-color: #654321;");
                } else {
                    button.setStyle("-fx-background-color: #FFFDD0;");
                }

                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c)); // Handle on click event
                boardButtons[row][col] = button; // Store button in the button array
                gameBoard.add(button, col, row); // Add button to the grid
            }
        }
        updateBoard();
        return gameBoard;
    }

    /**
     * Updates the game board with the current state from the game logic.
     */
    private void updateBoard() {
        int[][] board = gameLogic.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = boardButtons[row][col];
                int piece = board[row][col];

                // Set images based on the piece type
                try {
                    if (piece == 1) { // White piece
                        ImageView whitePieceImage = new ImageView(
                                new Image(new FileInputStream(WHITE_PIECE_IMAGE_PATH)));
                        whitePieceImage.setFitWidth(60);
                        whitePieceImage.setFitHeight(60);
                        whitePieceImage.setPreserveRatio(true);
                        button.setGraphic(whitePieceImage);
                    } else if (piece == 2) { // Black piece
                        ImageView blackPieceImage = new ImageView(
                                new Image(new FileInputStream(BLACK_PIECE_IMAGE_PATH)));
                        blackPieceImage.setFitWidth(60);
                        blackPieceImage.setFitHeight(60);
                        blackPieceImage.setPreserveRatio(true);
                        button.setGraphic(blackPieceImage);
                    } else if (piece == 3) { // White king
                        ImageView whiteKingImage = new ImageView(new Image(new FileInputStream(WHITE_KING_IMAGE_PATH)));
                        whiteKingImage.setFitWidth(60);
                        whiteKingImage.setFitHeight(60);
                        whiteKingImage.setPreserveRatio(true);
                        button.setGraphic(whiteKingImage);
                    } else if (piece == 4) { // Black king
                        ImageView blackKingImage = new ImageView(new Image(new FileInputStream(BLACK_KING_IMAGE_PATH)));
                        blackKingImage.setFitWidth(60);
                        blackKingImage.setFitHeight(60);
                        blackKingImage.setPreserveRatio(true);
                        button.setGraphic(blackKingImage);
                    } else {
                        button.setGraphic(null); // Empty square
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    button.setGraphic(null);
                }
            }
        }
    }

    /**
     * Handles a player's move.
     *
     * @param row The row of the clicked square.
     * @param col The column of the clicked square.
     */
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
        } else if (selectedRow == row && selectedCol == col) {
            // Unselect the currently selected piece if clicked again
            clearHighlights();
            selectedRow = -1;
            selectedCol = -1;
            chatArea.appendText("Piece unselected.\n");
        } else if (gameLogic.playerSelectedPiece(row, col, gameLogic.getCurrentPlayer())) {
            // If another piece is selected, switch selection to the new piece
            clearHighlights();
            selectedRow = row;
            selectedCol = col;
            highlightPossibleMoves(row, col);
            chatArea.appendText("Piece switched to a new selection.\n");
        } else {
            // Attempt to move the selected piece
            int fromRow = selectedRow;
            int fromCol = selectedCol;
            int toRow = row;
            int toCol = col;
            Player currentPlayer = gameLogic.getCurrentPlayer();

            if (gameLogic.isValidCapture(fromRow, fromCol, toRow, toCol, currentPlayer)) {
                gameLogic.playerCapturedPiece(fromRow, fromCol, toRow, toCol, currentPlayer);
                updateBoard();
                clearHighlights(); // Clear highlights after capture

                client.sendCheckerMoveToServer(gameLogic, fromRow, fromCol, toRow, toCol, currentPlayer, () -> {
                    if (gameLogic.hasValidCapture(toRow, toCol, currentPlayer)) {
                        selectedRow = toRow;
                        selectedCol = toCol;
                        highlightPossibleMoves(toRow, toCol);
                    } else {
                        if (checkGameState()) {
                            return;
                        }
                        selectedRow = -1;
                        selectedCol = -1;
                        clearHighlights();
                        gameLogic.switchPlayer();
                        turnIndicator.setText("Turn: " + gameLogic.getCurrentPlayer().getPlayerID());
                    }
                });

            } else if (gameLogic.isValidMove(fromRow, fromCol, toRow, toCol, currentPlayer)) {
                if (gameLogic.hasAnyValidCaptures(currentPlayer)) {
                    chatArea.appendText("You must capture if possible.\n");
                    clearHighlights();
                    selectedRow = -1;
                    selectedCol = -1;
                } else {
                    gameLogic.playerMovedPiece(fromRow, fromCol, toRow, toCol, currentPlayer);
                    updateBoard();
                    clearHighlights(); // Clear highlights after a valid move

                    client.sendCheckerMoveToServer(gameLogic, fromRow, fromCol, toRow, toCol, currentPlayer, () -> {
                        selectedRow = -1;
                        selectedCol = -1;
                        clearHighlights();
                        gameLogic.switchPlayer();
                        turnIndicator.setText("Turn: " + gameLogic.getCurrentPlayer().getPlayerID());
                    });
                }
            } else {
                chatArea.appendText("Invalid move. Try again.\n");
            }
        }
    }

    /**
     * Highlights player's possible moves.
     *
     * @param row The row of the clicked square.
     * @param col The column of the clicked square.
     */

    private void highlightPossibleMoves(int row, int col) {
        Player currentPlayer = gameLogic.getCurrentPlayer();
        boolean mustCapture = gameLogic.hasAnyValidCaptures(currentPlayer);
        boolean pieceHasCapture = gameLogic.hasValidCapture(row, col, currentPlayer);

        clearHighlights();

        if (mustCapture && !pieceHasCapture) {
            chatArea.appendText("Server: You must capture with a piece that can capture.\n");
            selectedRow = -1;
            selectedCol = -1;
            return;
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (pieceHasCapture && gameLogic.isValidCapture(row, col, r, c, currentPlayer)) {
                    boardButtons[r][c].setStyle("-fx-background-color: #FF0000;");
                } else if (!mustCapture && gameLogic.isValidMove(row, col, r, c, currentPlayer)) {
                    boardButtons[r][c].setStyle("-fx-background-color: #00FF00;");
                }
            }
        }
    }

    private void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 0) {
                    boardButtons[r][c].setStyle("-fx-background-color: #654321;");
                } else {
                    boardButtons[r][c].setStyle("-fx-background-color: #FFFDD0;");
                }
            }
        }
    }

    /**
     * Sends a chat message to the server and displays it in the chat area.
     */
    private void sendMessage() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            String responseFromServer = client.sendMessageToServer(message, client);
            chatArea.appendText(client.getCurrentUsername() + ": " + responseFromServer + "\n");
            chatInput.clear();
        }
    }

    /**
     * Checks the game state to determine if the game has ended.
     *
     * @return True if the game has ended, false otherwise.
     */
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
        Player currentPlayerData = gameLogic.getCurrentPlayer();

        // Determine the winner based on the current game state or piece count
        if (whiteCount == 0 || gameLogic.getGameState() == GameState.PLAYER2_WIN) {
            // Player 2 (Black) wins
            controller.showEndGameScreen(2, null, null, gameLogic, match, currentPlayerData);
            return true;
        } else if (blackCount == 0 || gameLogic.getGameState() == GameState.PLAYER1_WIN) {
            // Player 1 (White) wins
            controller.showEndGameScreen(2, null, null, gameLogic, match, currentPlayerData);
            return true;
        }
        return false; // No winner yet
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);

        Label content = new Label(message);
        content.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
