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
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(mainMenu.getScene());
    }

    public void showGameMenu() {
        GameMenuScreen gameMenu = new GameMenuScreen(primaryStage, this);
        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(gameMenu.getScene());
    }

    public void showTictactoeGameScreen() {
        TictactoeGameScreen ticTacToe = new TictactoeGameScreen(primaryStage, this);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(ticTacToe.getScene());
    }

    public void showPlayerProfileScreen() {
        PlayerProfileScreen playerProfile = new PlayerProfileScreen(primaryStage, this);
        primaryStage.setTitle("View Player Profile");
        primaryStage.setScene(playerProfile.getScene());
    }

    public void showLoadingScreen() {
        LoadingScreen loadingScreen = new  LoadingScreen(primaryStage, this);
        primaryStage.setTitle("View Player Profile");
        primaryStage.setScene(loadingScreen.getScene());
    }

    public void showServerConnectionScreen() {
        ServerConnectionScreen connection = new ServerConnectionScreen(primaryStage, this);
        primaryStage.setScene(connection.getScene());
    }

    public void showSignInScreen() {
        SignInScreen signIn = new SignInScreen(primaryStage, this);
        primaryStage.setTitle("Sign In");
        primaryStage.setScene(signIn.getScene());
    }

    public void showManageProfileScreen() {
        ManageProfileScreen manageProfile = new ManageProfileScreen(primaryStage, this);
        primaryStage.setTitle("Manage Profile");
        primaryStage.setScene(manageProfile.getScene());
    }

    public void showHelpScreen() {
        HelpScreen helpScreen = new HelpScreen(primaryStage, this);
        primaryStage.setTitle("Help Screen");
        primaryStage.setScene(helpScreen.getScene());
    }

    // needs an input to distinguish game type, since all games shares the same screen
    public void showQueueScreen(int gameType) {
        QueueScreen queue = new QueueScreen(primaryStage, this, gameType);
        primaryStage.setTitle("Queue");
        primaryStage.setScene(queue.getScene());

    }

    public void showEndGameScreen() {
        EndGameScreen endGame = new EndGameScreen(primaryStage, this);
        primaryStage.setTitle("Game Over");
        primaryStage.setScene(endGame.getScene());

    }

    public void showCreateProfileScreen() {
        CreateProfileScreen createProfile = new CreateProfileScreen(primaryStage, this);
        primaryStage.setTitle("Create Profile");
        primaryStage.setScene(createProfile.getScene());

    }

    public void showUserProfileScreen() {
        UserProfileScreen userProfile = new UserProfileScreen(primaryStage, this);
        primaryStage.setTitle("View Profile");
        primaryStage.setScene(userProfile.getScene());

    }

    public void showConnect4Screen() {
        Connect4Screen connect4 = new Connect4Screen(primaryStage, this);
        primaryStage.setTitle("Connect 4");
        primaryStage.setScene(connect4.getScene());
    }

    public void showLeaderBoard() {
        LeaderboardController leaderBoard = new LeaderboardController(primaryStage, this);
        primaryStage.setTitle("Leaderboard");
        primaryStage.setScene(leaderBoard.getScene());
    }

    public void showCheckerScreen() {
        CheckerScreen checkers = new CheckerScreen(primaryStage, this);
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(checkers.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }


}
