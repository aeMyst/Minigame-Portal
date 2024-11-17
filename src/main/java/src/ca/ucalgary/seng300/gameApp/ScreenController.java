package src.ca.ucalgary.seng300.gameApp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScreenController extends Application {
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showSignInScreen();
        primaryStage.show();

    }

    public void showMainMenu() {
        this.primaryStage = primaryStage;
        MainMenuScreen mainMenu = new MainMenuScreen(primaryStage, this);
        System.out.println(primaryStage.getTitle());
        primaryStage.setScene(mainMenu.getScene());
    }

    public void showGameMenu() {
        GameMenuScreen gameMenu = new GameMenuScreen(primaryStage, this);
        primaryStage.setScene(gameMenu.getScene());
    }

    public void showTictactoeGameScreen() {
        TictactoeGameScreen ticTacToe = new TictactoeGameScreen(primaryStage, this);
        primaryStage.setScene(ticTacToe.getScene());
    }

    public void showProfileScreen() {
        ProfileScreen profile = new ProfileScreen(primaryStage, this);
        primaryStage.setScene(profile.getScene());
    }

    public void showServerConnectionScreen() {
        ServerConnectionScreen connection = new ServerConnectionScreen(primaryStage, this);
        primaryStage.setScene(connection.getScene());
    }

    public void showSignInScreen() {
        SignInScreen signIn = new SignInScreen(primaryStage, this);
        primaryStage.setScene(signIn.getScene());
    }

    public void showManageProfileScreen() {
        ManageProfileScreen manageProfile = new ManageProfileScreen(primaryStage, this);
        primaryStage.setScene(manageProfile.getScene());

    }
    public void showEndGameScreen(String result, int player1Score, int player2Score) {
        Label endGameLabel = new Label("Game Over");
        endGameLabel.setFont(new Font("Arial", 24));
        endGameLabel.setTextFill(Color.DARKBLUE);

        Label resultLabel = new Label(result.equals("Draw") ? "It's a Draw!" : "Player " + result + " wins!");
        resultLabel.setFont(new Font("Arial", 18));
        resultLabel.setTextFill(Color.DARKGREEN);

        Label scoreLabel = new Label("Final Scores:\nPlayer X: " + player1Score + "\nPlayer O: " + player2Score);
        scoreLabel.setFont(new Font("Arial", 16));

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setFont(new Font("Arial", 16));
        playAgainButton.setOnAction(e -> showTictactoeGameScreen());

        Button backToMainMenuButton = new Button("Back to Main Menu");
        backToMainMenuButton.setFont(new Font("Arial", 16));
        backToMainMenuButton.setOnAction(e -> showMainMenu());

        VBox layout = new VBox(20, endGameLabel, resultLabel, scoreLabel, playAgainButton, backToMainMenuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        Scene endGameScene = new Scene(layout, 400, 300);
        primaryStage.setScene(endGameScene);
    }

    public void showCreateProfileScreen() {
        CreateProfileScreen createProfile = new CreateProfileScreen(primaryStage, this);
        primaryStage.setScene(createProfile.getScene());

    }

    public void showConnect4Screen() {
        Connect4Screen connect4 = new Connect4Screen (primaryStage, this);
        primaryStage.setScene(connect4.getScene());

    }

    public void showLeaderBoard() {
        LeaderboardController leaderBoard = new LeaderboardController(primaryStage, this);
        primaryStage.setScene(leaderBoard.getScene());
    }

    public void showCheckerScreen() {
        CheckerScreen checkers = new CheckerScreen(primaryStage, this);
        primaryStage.setScene(checkers.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }


}
