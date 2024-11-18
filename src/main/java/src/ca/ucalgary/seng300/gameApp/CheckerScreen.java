package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CheckerScreen implements IScreen {
    private Scene scene;
    private String currentPlayer = "Blue";
    private Button[][] buttons = new Button[8][8];
    private Label turnIndicator;
    private TextArea chatArea;
    private TextField chatInput;
    private Label blueScoreLabel, redScoreLabel;
    private int blueScore = 0, redScore = 0;
    private ScreenController controller;

    public CheckerScreen(Stage stage, ScreenController controller) {
        this.controller = controller;
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setPadding(new Insets(10));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button();
                button.setPrefSize(80, 80);
                if ((row + col) % 2 == 0) {
                    button.setStyle("-fx-background-color: #FFFFFF;");
                } else {
                    button.setStyle("-fx-background-color: #000000;");
                }
                if (row < 3 && (row + col) % 2 != 0) {
                    button.setText("B");
                    button.setTextFill(Color.BLUE);
                } else if (row > 4 && (row + col) % 2 != 0) {
                    button.setText("R");
                    button.setTextFill(Color.RED);
                }
                final int r = row, c = col;
                button.setOnAction(e -> handleMove(r, c));
                buttons[row][col] = button;
                gameBoard.add(button, col, row);
            }
        }
        blueScoreLabel = new Label("Blue Score: " + blueScore);
        redScoreLabel = new Label("Red Score: " + redScore);
        blueScoreLabel.setFont(new Font("Arial", 16));
        redScoreLabel.setFont(new Font("Arial", 16));

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

        turnIndicator = new Label("Turn: " + currentPlayer);
        turnIndicator.setFont(new Font("Arial", 18));
        turnIndicator.setTextFill(Color.DARKGREEN);

        VBox layout = new VBox(15, turnIndicator, gameBoard, blueScoreLabel, redScoreLabel, chatArea, chatBox, backToMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        scene = new Scene(layout, 800, 800);
    }

    private void handleMove(int row, int col) {
        Button button = buttons[row][col];
        if (!button.getText().isEmpty() && !button.getText().equals(currentPlayer.charAt(0) + "")) {
            return;
        }
        //move logic
        currentPlayer = currentPlayer.equals("Blue") ? "Red" : "Blue";
        turnIndicator.setText("Turn: " + currentPlayer);
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
