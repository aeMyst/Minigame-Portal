package src.ca.ucalgary.seng300.gameApp.gameScreens;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.Utility.ChatUtility;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.PlayerManager;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This code represents a Tic-Tac-Toe game screen.
 * Handles game board creation, game logic integration, player interactions, and chat functionality.
 */
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
    private boolean isEmojiOpen = false;
    private boolean moveInProgress = false;

    /**
     * Constructor for TictactoeGameScreen.
     *
     * @param stage      The primary stage for the application.
     * @param controller The screen controller for navigation between screens.
     * @param client     The network client for server communication.
     */
    public TictactoeGameScreen(Stage stage, ScreenController controller, Client client, ArrayList<Player> match) {
        this.stage = stage;
        this.client = client;

        Player humanPlayerX = match.get(0);
        Player humanPlayerO = match.get(1);

        HumanPlayer playerX = new HumanPlayer(humanPlayerX, 'X');
        HumanPlayer playerO = new HumanPlayer(humanPlayerO, 'O');

        boardManager = new BoardManager();
        playerManager = new PlayerManager(playerX, playerO);

        // Game Board
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);

        // create interactables for game
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button(" ");
                button.getStyleClass().add("tictactoe-button");
                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c, controller, match));
                buttons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }

        // game title
        Label title = new Label("TIC-TAC-TOE GAME");
        title.getStyleClass().add("title-label");

        // Turn Indicator
        turnIndicator = new Label(String.format("Turn: %s (%s)", playerX.getPlayer().getPlayerID(), playerX.getSymbol()));
        turnIndicator.getStyleClass().add("label-turn-indicator");

        // Chatroom
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setPrefHeight(150);
        chatArea.getStyleClass().add("text-area-chat");


        chatInput = new TextField();
        chatInput.getStyleClass().add("input-field");
        chatInput.setPromptText("Type your message...");
        chatInput.setOnAction(e -> sendMessage());

        // Send button
        Button sendButton = new Button("Send");
        sendButton.getStyleClass().add("button-send");
        sendButton.setOnAction(e -> sendMessage());

        // Emoji button to show emoji menu
        Button emojiButton = new Button("Emoji Menu");
        emojiButton.getStyleClass().add("button-emoji");
        emojiButton.setOnAction(e -> {
            if (!isEmojiOpen) {
                ChatUtility.showEmojiMenu(chatInput, stage, () -> isEmojiOpen = false, client);
                isEmojiOpen = true;
            } else {
                chatArea.appendText("Server: Please select an Emoji or close the menu.\n");
            }
        });

        // Chat layout
        HBox chatBox = new HBox(10, chatInput, emojiButton, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        VBox chatLayout = new VBox(10, chatArea, chatBox);
        chatLayout.setAlignment(Pos.CENTER);
        chatLayout.setPadding(new Insets(10));

        // Forfeit button to exit the game
        Button forfeitButton = new Button("Forfeit");
        forfeitButton.getStyleClass().add("button");
        forfeitButton.getStyleClass().add("button-forfeit");
        forfeitButton.setOnAction(e -> {
            // Create a confirmation dialog
            // chatgpt generated
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setHeaderText("Forfeit Confirmation");
            confirmationDialog.setTitle("Confirm Forfeit");

            Label header = new Label("Are you sure you want to forfeit?");
            header.getStyleClass().add("dialog-header");

            Label content = new Label("Forfeiting will end the game and declare the opponent as the winner and you will lose elo.");
            content.getStyleClass().add("dialog-content");

            VBox dialogContent = new VBox(10, header, content);
            dialogContent.setAlignment(Pos.CENTER_LEFT);
            confirmationDialog.getDialogPane().setContent(dialogContent);

            ButtonType forfeitButtonType = new ButtonType("Confirm Forfeit", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel Forfeit", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationDialog.getButtonTypes().setAll(forfeitButtonType, cancelButtonType);

            // Show the dialog and wait for a response
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // Handle user response
            if (result.isPresent() && result.get() == forfeitButtonType) {
                // User confirmed forfeiting
                System.out.println(client.getCurrentUsername() + " has forfeited the game.");

                Player winner = null;
                Player loser = null;

                EloRating eloRating = new EloRating();
                String eloLoss;
                int currentLoserElo = 0;

                // loop to find winner and loser
                for (Player player : match) {
                    if (!player.getPlayerID().equals(client.getCurrentUsername())) {
                        winner = player;
                    } else {
                        loser = player;
                        currentLoserElo = loser.getElo();
                    }
                }

                eloRating.updateElo(winner, loser);
                winner.setWins(winner.getWins() + 1);
                loser.setLosses(loser.getLosses() + 1);
                eloLoss = String.valueOf(currentLoserElo - loser.getElo());

                // loop to edit player profiles in match
                for (Player player : match) {
                    if (player.getPlayerID().equals(winner.getPlayerID())) {
                        player = winner;
                    } else {
                        player = loser;
                    }
                }

                FileManagement.updateProfilesInCsv(client.getStatPath(), match);

                controller.showMainMenu(); // Navigate back to the main menu
                https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.AlertType.html
                showAlert(Alert.AlertType.INFORMATION, "The game was Forfeited", "You have Loss -" + eloLoss +" Elo" );
            } else {
                // User canceled forfeiting
                System.out.println(client.getCurrentUsername() + " has canceled forfeiting.");
            }
                });

        // Main Layout
        VBox layout = new VBox(15, title, turnIndicator, gameBoard, chatLayout, forfeitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900); // Fixed size
        scene.getStylesheets().add((getClass().getClassLoader().getResource("GamesStyles.css").toExternalForm()));
    }

    /**
     * Handles a move made by the player.
     *
     * @param row        The row of the move.
     * @param col        The column of the move.
     * @param controller The screen controller for navigating screens.
     */
    private void handleMove(int row, int col, ScreenController controller, ArrayList<Player> match) {
        if (moveInProgress) {
            return;     // Ignore input if a move is already in progress
        }

        moveInProgress = true;
        disableBoard();

        HumanPlayer currentPlayerObj = playerManager.getCurrentPlayer();
        Player currentPlayerData = currentPlayerObj.getPlayer(); // Get associated Player data

        if (boardManager.isValidMove(row, col)) { // Check if move is valid
            boardManager.placeSymbol(currentPlayerObj.getSymbol(), row, col);

            client.sendTTTMoveToServer(boardManager, playerManager, status, () -> {
                // Update GUI after "server acknowledgment"
                buttons[row][col].setText(String.valueOf(currentPlayerObj.getSymbol()));

                // Check for game outcomes
                if (boardManager.isWinner(currentPlayerObj.getSymbol())) {
                    this.status = "DONE";
                    System.out.println("Winner: " + currentPlayerData.getPlayerID());
                    controller.showEndGameScreen(0, boardManager, null, null, match, currentPlayerData);

                } else if (boardManager.isTie()) {
                    this.status = "DONE";
                    System.out.println("Game Tied.");
                    controller.showEndGameScreen(0, boardManager, null, null, match, null);

                } else {
                    playerManager.switchPlayer();
                    currentPlayer = String.valueOf(playerManager.getCurrentPlayer().getPlayer().getPlayerID());
                    turnIndicator.setText(String.format("Turn: %s (%s)", playerManager.getCurrentPlayer().getSymbol(), currentPlayer));
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

    /**
     * Disables all buttons on the game board.
     */
    private void disableBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }

    /**
     * Enables all buttons on the game board.
     */
    private void enableBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setDisable(false);
            }
        }
    }

    /**
     * Sends a message typed in the chat input field to the server.
     */
    private void sendMessage() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            String responseFromServer = client.sendMessageToServer(message, client);
            chatArea.appendText(client.getCurrentUsername() + ": " + responseFromServer + "\n");
            chatInput.clear();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Create a custom Label for bold content
        Label content = new Label(message);
        content.getStyleClass().add("dialog-content");

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    /**
     * Returns the scene for the Tic-Tac-Toe game screen.
     *
     * @return The game screen.
     */
    public Scene getScene() {
        return scene;
    }
}
