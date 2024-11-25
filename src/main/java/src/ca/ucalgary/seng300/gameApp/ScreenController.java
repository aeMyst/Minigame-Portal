package src.ca.ucalgary.seng300.gameApp;

import javafx.application.Application;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Client;

public class ScreenController extends Application {
    private Stage primaryStage;
    Client client = new Client();


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showSignInScreen();
        primaryStage.show();

    }

    public void showMainMenu() {
        this.primaryStage = primaryStage;
        MainMenuScreen mainMenu = new MainMenuScreen(primaryStage, this , client);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(mainMenu.getScene());
    }

    public void showGameMenu() {
        GameMenuScreen gameMenu = new GameMenuScreen(primaryStage, this, client);
        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(gameMenu.getScene());
    }

    public void showTictactoeGameScreen() {
        TictactoeGameScreen ticTacToe = new TictactoeGameScreen(primaryStage, this, client);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(ticTacToe.getScene());
    }

    public void showPlayerProfileScreen() {
        PlayerProfileScreen playerProfile = new PlayerProfileScreen(primaryStage, this , client);
        primaryStage.setTitle("View Player Profile");
        primaryStage.setScene(playerProfile.getScene());
    }

    public void showLoadingScreen() {
        LoadingScreen loadingScreen = new  LoadingScreen(primaryStage, this, client);
        primaryStage.setTitle("View Player Profile");
        primaryStage.setScene(loadingScreen.getScene());
    }

    public void showServerConnectionScreen(boolean disconnectCheck) {
        ServerConnectionScreen connection = new ServerConnectionScreen(primaryStage, this, client, disconnectCheck);
        primaryStage.setScene(connection.getScene());
    }

    public void showSignInScreen() {
        SignInScreen signIn = new SignInScreen(primaryStage, this, client);
        primaryStage.setTitle("Sign In");
        primaryStage.setScene(signIn.getScene());
    }

    public void showManageProfileScreen() {
        ManageProfileScreen manageProfile = new ManageProfileScreen(primaryStage, this, client);
        primaryStage.setTitle("Manage Profile");
        primaryStage.setScene(manageProfile.getScene());
    }

    public void showHelpScreen() {
        HelpScreen helpScreen = new HelpScreen(primaryStage, this, client);
        primaryStage.setTitle("Help Screen");
        primaryStage.setScene(helpScreen.getScene());
    }

    // needs an input to distinguish game type, since all games shares the same screen
    public void showQueueScreen(int gameType) {
        QueueScreen queue = new QueueScreen(primaryStage, this, gameType, client);
        primaryStage.setTitle("Queue Screen");
        primaryStage.setScene(queue.getScene());

    }

    public void showEndGameScreen() {
        EndGameScreen endGame = new EndGameScreen(primaryStage, this, client);
        primaryStage.setTitle("Game Over");
        primaryStage.setScene(endGame.getScene());

    }

    public void showC4EndGameScreen() {
        C4EndGameScreen c4EndGame = new C4EndGameScreen(primaryStage, this, client);
        primaryStage.setTitle("Game Over");
        primaryStage.setScene(c4EndGame.getScene());
    }

    public void showCreateProfileScreen() {
        CreateProfileScreen createProfile = new CreateProfileScreen(primaryStage, this, client);
        primaryStage.setTitle("Create Profile");
        primaryStage.setScene(createProfile.getScene());

    }

    public void showUserProfileScreen() {
        UserProfileScreen userProfile = new UserProfileScreen(primaryStage, this, client);
        primaryStage.setTitle("View Profile");
        primaryStage.setScene(userProfile.getScene());

    }

    public void showConnect4Screen() {
        Connect4Screen connect4 = new Connect4Screen(primaryStage, this, client);
        primaryStage.setTitle("Connect 4");
        primaryStage.setScene(connect4.getScene());
    }

    public void showLeaderBoard() {
        LeaderboardController leaderBoard = new LeaderboardController(primaryStage, this, client);
        primaryStage.setTitle("Leaderboard");
        primaryStage.setScene(leaderBoard.getScene());
    }

    public void showCheckerScreen() {
        CheckerScreen checkers = new CheckerScreen(primaryStage, this, client);
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(checkers.getScene());
    }

    public void showMatchmakeChoiceScreen(int gameType) {
        MatchmakeChoiceScreen matchmakeScreen = new MatchmakeChoiceScreen(primaryStage, this, gameType, client);
        primaryStage.setTitle("matchmake Screen");
        primaryStage.setScene(matchmakeScreen.getScene());
    }




    public static void main(String[] args) {
        launch(args);
    }


}
