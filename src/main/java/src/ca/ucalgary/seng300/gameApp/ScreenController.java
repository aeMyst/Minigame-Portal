package src.ca.ucalgary.seng300.gameApp;

import javafx.application.Application;
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

    public void showEndGameScreen() {
        EndGameScreen endGame = new EndGameScreen(primaryStage, this);
        primaryStage.setScene(endGame.getScene());

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

    public void showSearchProfileScreen() {
        SearchProfileScreen searchProfileScreen = new SearchProfileScreen(primaryStage, this);
        primaryStage.setScene(searchProfileScreen.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }



}
