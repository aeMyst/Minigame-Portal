package src.ca.ucalgary.seng300.gameApp;
/*
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuApplication extends Application {
    private Scene checkersScene;
    private Scene ticTacToeScene;
    private Scene mainMenuScene;
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("MainMenuScene.fxml"));
        mainMenuScene = new Scene(mainMenuLoader.load());
        MainMenuController mainMenuController = mainMenuLoader.getController();


        FXMLLoader ticTacToeLoader = new FXMLLoader(getClass().getResource("TicTacToeScene.fxml"));
        ticTacToeScene = new Scene(ticTacToeLoader.load());
        TicTacToeController ticTacToeController = ticTacToeLoader.getController();

        // Initialize controllers with necessary references
        mainMenuController.initialize(primaryStage, checkersScene, ticTacToeScene);
        ticTacToeController.initialize(primaryStage, mainMenuScene);

        // Set the main menu scene and show the stage
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuApplication extends Application {

    private String currentPlayer = "X";
    private Button[][] buttons = new Button[3][3];
    private Label turnIndicator;
    private TextArea chatArea;
    private TextField chatInput;
    private Label player1ScoreLabel, player2ScoreLabel;
    private int player1Score = 0, player2Score = 0;
    private Label signLabel;
    private Label signInStatusLabel;

    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    private void showMainMenu(Stage stage) {
        Label titleLabel = new Label("Main Menu");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        signInStatusLabel = new Label("Guest");

        signLabel = new Label("Status: " + signInStatusLabel.getText());
        signLabel.setFont(new Font("Arial", 14));
        signLabel.setTextFill(Color.DARKGRAY);
        signLabel.setPadding(new Insets(5, 0, 10, 0));

        Button gamesButton = new Button("Choose a Game");
        gamesButton.setFont(new Font("Arial", 16));
        gamesButton.setPrefWidth(200);
        gamesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        gamesButton.setOnAction(e -> {showGameMenu(stage);});

        Button playButton = new Button("Play Tic-Tac-Toe");
        playButton.setFont(new Font("Arial", 16));
        playButton.setPrefWidth(200);
        playButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        playButton.setOnAction(e -> showTicTacToeGame(stage));

        Button settingsButton = new Button("Settings");
        settingsButton.setFont(new Font("Arial", 16));
        settingsButton.setPrefWidth(200);
        settingsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button leaderBoardButton = new Button("LeaderBoard");
        leaderBoardButton.setFont(new Font("Arial", 16));
        leaderBoardButton.setPrefWidth(200);
        leaderBoardButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button helpButton = new Button("Help");
        helpButton.setFont(new Font("Arial", 16));
        helpButton.setPrefWidth(200);
        helpButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", 16));
        exitButton.setPrefWidth(200);
        exitButton.setStyle("-fx-background-color: #4CAF50;");
        exitButton.setOnAction(e -> System.exit(0));

        Button logInButton = new Button("Sign in");
        logInButton.setOnAction(e -> showLogInMenu(stage));

        VBox mainMenuLayout = new VBox(15, signLabel, logInButton, titleLabel, gamesButton, settingsButton, helpButton, leaderBoardButton, exitButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.setPadding(new Insets(20));
        mainMenuLayout.setStyle("-fx-background-color: #f0f8ff;");

        Scene mainMenuScene = new Scene(mainMenuLayout, 800, 600);
        stage.setScene(mainMenuScene);
        stage.setTitle("Main Menu");
        stage.show();
    }
    private void showGameMenu(Stage stage) {
        Label gameMenuTitle = new Label("Game Menu");
        gameMenuTitle.setFont(new Font("Arial", 24));
        gameMenuTitle.setTextFill(Color.DARKBLUE);
        gameMenuTitle.setPadding(new Insets(10, 0, 20, 0));

        Button startGameButton_TTT = new Button("Play TicTacToe");
        startGameButton_TTT.setFont(new Font("Arial", 16));
        startGameButton_TTT.setPrefWidth(200);
        startGameButton_TTT.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        startGameButton_TTT.setOnAction(e -> showTicTacToeGame(stage));

        Button startGameButton_connect4 = new Button("Play Connect4");
        startGameButton_connect4.setFont(new Font("Arial", 16));
        startGameButton_connect4.setPrefWidth(200);
        startGameButton_connect4.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #ff6347; -fx-text-fill: white;");
        backButton.setOnAction(e -> showMainMenu(stage));

        VBox gameMenuLayout = new VBox(15, gameMenuTitle, startGameButton_TTT, startGameButton_connect4, backButton);
        gameMenuLayout.setAlignment(Pos.CENTER);
        gameMenuLayout.setPadding(new Insets(20));
        gameMenuLayout.setStyle("-fx-background-color: #f0f8ff;");

        Scene gameMenuScene = new Scene(gameMenuLayout, 800, 600);
        stage.setScene(gameMenuScene);
        stage.setTitle("Game Menu");
        stage.show();
    }
    private void showTicTacToeGame(Stage stage) {
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

        Button sendButton = new Button("Send");
        sendButton.setFont(new Font("Arial", 14));
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        sendButton.setOnAction(e -> sendMessage());

        Button backToMenuButton = new Button("Back to Menu");
        backToMenuButton.setFont(new Font("Arial", 16));
        backToMenuButton.setPrefWidth(200);
        backToMenuButton.setOnAction(e -> showMainMenu(stage));

        HBox chatBox = new HBox(5, chatInput, sendButton);
        chatBox.setAlignment(Pos.CENTER);

        turnIndicator = new Label("Turn: Player " + currentPlayer);
        turnIndicator.setFont(new Font("Arial", 18));
        turnIndicator.setTextFill(Color.DARKGREEN);

        VBox layout = new VBox(15, turnIndicator, gameBoard, player1ScoreLabel, player2ScoreLabel, chatArea, chatBox, backToMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        Scene gameScene = new Scene(layout, 600, 800);
        stage.setScene(gameScene);
        stage.setTitle("Tic-Tac-Toe");
    }
    private void showLogInMenu(Stage stage) {
        Label signInTitle = new Label("Sign In");
        signInTitle.setFont(new Font("Arial", 24));
        signInTitle.setTextFill(Color.DARKBLUE);
        signInTitle.setPadding(new Insets(10, 0, 20, 0));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFont(new Font("Arial", 14));
        usernameField.setPrefWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFont(new Font("Arial", 14));
        passwordField.setPrefWidth(250);

        Button signInButton = new Button("Sign In");
        signInButton.setFont(new Font("Arial", 16));
        signInButton.setPrefWidth(200);
        signInButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        signInButton.setOnAction(e -> handleSignIn(usernameField.getText(), passwordField.getText()));

        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(new Font("Arial", 16));
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-background-color: #ff6347; -fx-text-fill: white;");
        backButton.setOnAction(e -> showMainMenu(stage));

        VBox signInLayout = new VBox(15, signInTitle, usernameField, passwordField, signInButton, backButton);
        signInLayout.setAlignment(Pos.CENTER);
        signInLayout.setPadding(new Insets(20));
        signInLayout.setStyle("-fx-background-color: #f0f8ff;");

        Scene signInScene = new Scene(signInLayout, 800, 600);
        stage.setScene(signInScene);
        stage.setTitle("Sign In");
        stage.show();
    }
    private void handleMove(int row, int col) {
        Button button = buttons[row][col];
        if (!button.getText().equals("-")) {
            return;
        }
        button.setText(currentPlayer);
        button.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;");
        if (isWin()) {
            showWinAlert(currentPlayer);
            updateScore();
            resetBoard();
        } else if (isDraw()) {
            turnIndicator.setText("Draw!");
            resetBoard();
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            turnIndicator.setText("Turn: Player " + currentPlayer);
        }
    }
    private boolean isWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(currentPlayer) && buttons[i][1].getText().equals(currentPlayer)
                    && buttons[i][2].getText().equals(currentPlayer)) {
                return true;
            }
            if (buttons[0][i].getText().equals(currentPlayer) && buttons[1][i].getText().equals(currentPlayer)
                    && buttons[2][i].getText().equals(currentPlayer)) {
                return true;
            }
        }
        return buttons[0][0].getText().equals(currentPlayer) && buttons[1][1].getText().equals(currentPlayer)
                && buttons[2][2].getText().equals(currentPlayer) ||
                buttons[0][2].getText().equals(currentPlayer) && buttons[1][1].getText().equals(currentPlayer)
                        && buttons[2][0].getText().equals(currentPlayer);
    }
    private boolean isDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }
    private void handleSignIn(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password cannot be empty.");
        }
        else  {
            signInStatusLabel.setText("Signed in as " + username);
            showAlert("Success", "Welcome, " + username + "!");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("-");
                buttons[row][col].setStyle("-fx-background-color: #87CEFA; -fx-text-fill: black;");
            }
        }
        currentPlayer = "X";
        turnIndicator.setText("Turn: Player " + currentPlayer);
    }
    private void sendMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty()) {
            chatArea.appendText("Player: " + message + "\n");
            chatInput.clear();
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

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType exit = new ButtonType("Exit");
        winAlert.getButtonTypes().setAll(playAgain, exit);
        winAlert.showAndWait();
        if (winAlert.getResult() == playAgain) {
            resetBoard();
        }
        else {
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
