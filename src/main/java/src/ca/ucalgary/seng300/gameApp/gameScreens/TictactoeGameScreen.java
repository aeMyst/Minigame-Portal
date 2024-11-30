package src.ca.ucalgary.seng300.gameApp.gameScreens;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
                button.setFont(new Font("Impact", 32));
                button.setPrefSize(100, 100);
                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c, controller, match));
                buttons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }

        // game title
        Label title = new Label("Tic-Tac-Toe Game");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.DARKBLUE);

        // Turn Indicator
        turnIndicator = new Label(String.format("Turn: %s (%s)", playerX.getSymbol(), playerX.getPlayer().getPlayerID()));
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
        sendButton.setPrefWidth(150);
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        sendButton.setOnAction(e -> sendMessage());


        Button emojiButton = new Button("Emoji Menu");
        emojiButton.setFont(new Font("Arial", 16));
        emojiButton.setPrefWidth(150);
        emojiButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        emojiButton.setOnAction(e -> {
            if (!isEmojiOpen) {
                ChatUtility.showEmojiMenu(chatInput, stage, () -> isEmojiOpen = false, client);
                isEmojiOpen = true;
            } else {
                chatArea.appendText("Server: Please select an Emoji or close the menu.\n");
            }
        });

        HBox chatBox = new HBox(10, chatInput, emojiButton, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        VBox chatLayout = new VBox(10, chatArea, chatBox);
        chatLayout.setAlignment(Pos.CENTER);
        chatLayout.setPadding(new Insets(10));

        Button forfeitButton = new Button("Forfeit");
        forfeitButton.setFont(new Font("Arial", 16));
        forfeitButton.setPrefWidth(200);
        forfeitButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: #FFFFFF;");
        forfeitButton.setOnAction(e -> {
            // Create a confirmation dialog
            // chatgpt generated
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setHeaderText("Forfeit Confirmation");
            confirmationDialog.setTitle("Confirm Forfeit");

            Label header = new Label("Are you sure you want to forfeit?");
            header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #d9534f;");

            Label content = new Label("Forfeiting will end the game and declare the opponent as the winner and you will lose elo.");
            content.setStyle("-fx-font-size: 14px; -fx-text-fill: #5a5a5a;");

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
        scene = new Scene(layout, 1280, 900); // Fixed size
    }

    private void handleMove(int row, int col, ScreenController controller, ArrayList<Player> match) {
        if (moveInProgress) {
            return;
        }

        moveInProgress = true;
        disableBoard();

        HumanPlayer currentPlayerObj = playerManager.getCurrentPlayer();
        Player currentPlayerData = currentPlayerObj.getPlayer(); // Get associated Player data

        if (boardManager.isValidMove(row, col)) {
            boardManager.placeSymbol(currentPlayerObj.getSymbol(), row, col);

            client.sendMoveToServer(boardManager, playerManager, status, () -> {
                buttons[row][col].setText(String.valueOf(currentPlayerObj.getSymbol()));

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
            String responseFromServer = client.sendMessageToServer(message, client);
            chatArea.appendText(client.getCurrentUsername() + responseFromServer + "\n");
            chatInput.clear();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Create a custom Label for bold content
        Label content = new Label(message);
        content.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }
}