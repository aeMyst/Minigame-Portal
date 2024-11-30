package src.ca.ucalgary.seng300.gameApp.gameScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.TurnManager;
import src.ca.ucalgary.seng300.gamelogic.Connect4.UserPiece;

import java.util.ArrayList;
import java.util.Optional;

public class Connect4Screen implements IScreen {
    private Scene scene;

    // Players and game logic
    private UserPiece currentPlayer;
    private UserPiece playerRed;
    private UserPiece playerBlue;
    private Connect4Logic logicManager;
    private TurnManager turnManager;
    private Client client;
    private String status = "ONGOING";
    private Stage stage;
    private Button[][] gameButtons = new Button[6][7];
    private Label turnIndicator;
    private TextArea chatArea;
    private TextField chatInput;
    private boolean isEmojiOpen = false;
    private ArrayList<Player> match;

    public Connect4Screen(Stage stage, ScreenController controller, Client client, ArrayList<Player> match) {
        this.stage = stage;
        this.client = client;
        this.match = match;

        // Initialize players dynamically from match
        Player playerOne = match.get(0);
        Player playerTwo = match.get(1);

        playerRed = new UserPiece(playerOne, 1); // Assign piece 1 to playerOne (red)
        playerBlue = new UserPiece(playerTwo, 2); // Assign piece 2 to playerTwo (blue)
        currentPlayer = playerRed; // Red starts first

        logicManager = new Connect4Logic();
        turnManager = new TurnManager(playerRed, playerBlue);

        // UI setup
        Label title = new Label("Connect4");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.DARKBLUE);

        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setPadding(new Insets(10));

        // Initialize game buttons
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

        turnIndicator = new Label("Player turn: " + currentPlayer.getPlayer().getPlayerID() + " (RED)");
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
                isEmojiOpen = true;
                ChatUtility.showEmojiMenu(chatInput, stage, () -> isEmojiOpen = false, client);
            } else {
                chatArea.appendText("Server: Please select an Emoji or close the menu.\n");
            }
        });

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

        HBox chatBox = new HBox(5, chatInput, emojiButton, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, title, turnIndicator, gameBoard, chatArea, chatBox, forfeitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        scene = new Scene(layout, 1280, 900);
    }

    private boolean moveInProgress = false;

    // handle move attempted by player when button on game board is clicked
    private void gameButtonClicked(int column, ScreenController controller) {
        if (moveInProgress) {
            return;
        }

        moveInProgress = true;
        disableBoard();

        if (logicManager.placePiece(logicManager.getBoard(), column, currentPlayer.getPiece())) {
            // Update the UI board
            for (int row = 0; row < logicManager.getBoard().length; row++) {
                if (logicManager.getBoard()[row][column] == currentPlayer.getPiece()) {
                    gameButtons[row][column].setStyle(currentPlayer == playerRed
                            ? "-fx-background-color: #e74c3c"
                            : "-fx-background-color: #3498db");
                    break;
                }
            }

            client.sendC4MoveToServer(logicManager, turnManager, status, () -> {
                // Check win conditions
                if (logicManager.horizontalWin(logicManager.getBoard(), currentPlayer.getPiece())
                        || logicManager.verticalWin(logicManager.getBoard(), currentPlayer.getPiece())
                        || logicManager.backslashWin(logicManager.getBoard(), currentPlayer.getPiece())
                        || logicManager.forwardslashWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                    status = "DONE";
                    controller.showEndGameScreen(1, null, logicManager, null, match, currentPlayer.getPlayer());
                } else if (logicManager.boardFull(logicManager.getBoard())) {
                    status = "DONE";
                    controller.showEndGameScreen(1, null, logicManager, null, match, null);
                } else {
                    switchTurns();
                }
                enableBoard();
                moveInProgress = false;
            });
        } else {
            chatArea.appendText("Server: Please make a valid move.\n");
            enableBoard();
            moveInProgress = false;
        }
    }

    private void switchTurns() {
        currentPlayer = (currentPlayer == playerRed) ? playerBlue : playerRed;
        turnIndicator.setText("Player turn: " + currentPlayer.getPlayer().getPlayerID()
                + (currentPlayer == playerRed ? " (RED)" : " (BLUE)"));
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
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            chatArea.appendText("Player: " + client.sendMessageToServer(message, client) + "\n");
            chatInput.clear();
        }
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
