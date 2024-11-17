package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TictactoeGameScreen implements IScreen {
    private Scene scene;
    private String currentPlayer = "X";
    private Button[][] buttons = new Button[3][3];
    private Label turnIndicator;
    private TextArea chatArea;
    private TextField chatInput;
    private Label player1ScoreLabel, player2ScoreLabel;
    private int player1Score = 0, player2Score = 0;
    private ScreenController controller;

    public TictactoeGameScreen(Stage stage, ScreenController controller) {
        this.controller = controller;

        // Initialize game board UI
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setPadding(new Insets(10));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button("-");
                button.setFont(new Font("Arial", 24));
                button.setPrefSize(100, 100);
                button.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: black;");
                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c));
                buttons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }

        player1ScoreLabel = new Label("Player X Score: " + player1Score);
        player2ScoreLabel = new Label("Player O Score: " + player2Score);
        player1ScoreLabel.setFont(new Font("Arial", 16));
        player2ScoreLabel.setFont(new Font("Arial", 16));

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

        // Set up the overall layout
        Button backToMenuButton = new Button("Back to Menu");
        backToMenuButton.setFont(new Font("Arial", 16));
        backToMenuButton.setPrefWidth(200);
        backToMenuButton.setOnAction(e -> controller.showMainMenu());

        HBox chatBox = new HBox(5, chatInput, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        turnIndicator = new Label("Turn: Player " + currentPlayer);
        turnIndicator.setFont(new Font("Arial", 18));
        turnIndicator.setTextFill(Color.DARKGREEN);

        VBox layout = new VBox(15, turnIndicator, gameBoard, player1ScoreLabel, player2ScoreLabel, chatArea, chatBox, backToMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        scene = new Scene(layout, 600, 800);
    }

    private void handleMove(int row, int col) {
        Button button = buttons[row][col];
        if (!button.getText().equals("-")) return;

        button.setText(currentPlayer);
        button.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;");
        if (isWin()) {
            showWinAlert(currentPlayer);
            updateScore();
        } else if (isDraw()) {
            turnIndicator.setText("Draw!");
            controller.showEndGameScreen("Draw", player1Score, player2Score);
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            turnIndicator.setText("Turn: Player " + currentPlayer);
        }
    }

    private void showWinAlert(String winner) {
        Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
        winAlert.setTitle("Game Over");

        Label headerLabel = new Label(" Congratulations! ");
        headerLabel.setFont(new Font("Arial", 20));
        headerLabel.setTextFill(Color.DARKGREEN);
        winAlert.setHeaderText(null);
        winAlert.getDialogPane().setHeader(headerLabel);

        Label contentLabel = new Label("Player " + winner + " wins!");
        contentLabel.setFont(new Font("Arial", 16));
        contentLabel.setTextFill(Color.DARKBLUE);
        winAlert.getDialogPane().setContent(contentLabel);

        ButtonType okButton = new ButtonType("OK");
        winAlert.getButtonTypes().setAll(okButton);
        winAlert.showAndWait();

        // Show end game screen with the final results
        controller.showEndGameScreen(winner, player1Score, player2Score);
    }
    private void sendMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty()) {
            chatArea.appendText("Player: " + message + "\n");
            chatInput.clear();
        }
    }
    private boolean isWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(currentPlayer) && buttons[i][1].getText().equals(currentPlayer)
                    && buttons[i][2].getText().equals(currentPlayer)) return true;
            if (buttons[0][i].getText().equals(currentPlayer) && buttons[1][i].getText().equals(currentPlayer)
                    && buttons[2][i].getText().equals(currentPlayer)) return true;
        }
        return buttons[0][0].getText().equals(currentPlayer) && buttons[1][1].getText().equals(currentPlayer)
                && buttons[2][2].getText().equals(currentPlayer)
                || buttons[0][2].getText().equals(currentPlayer) && buttons[1][1].getText().equals(currentPlayer)
                && buttons[2][0].getText().equals(currentPlayer);
    }

    private boolean isDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("-")) return false;
            }
        }
        return true;
    }

    private void updateScore() {
        if (currentPlayer.equals("X")) {
            player1Score++;
            player1ScoreLabel.setText("Player X Score: " + player1Score);
        } else {
            player2Score++;
            player2ScoreLabel.setText("Player O Score: " + player2Score);
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
