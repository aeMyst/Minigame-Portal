package src.ca.ucalgary.seng300.gameApp;

import javafx.application.Application;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Profile.services.AuthService;
import src.ca.ucalgary.seng300.Profile.services.ProfileService;
import src.ca.ucalgary.seng300.gameApp.extraScreens.CheckersRules;
import src.ca.ucalgary.seng300.gameApp.extraScreens.ConnectFourRules;
import src.ca.ucalgary.seng300.gameApp.extraScreens.TTTRules;
import src.ca.ucalgary.seng300.gameApp.leaderboardScreens.LeaderboardController;
import src.ca.ucalgary.seng300.gameApp.loadingScreens.ChallengePlayerScreen;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.accountScreens.*;
import src.ca.ucalgary.seng300.gameApp.gameScreens.EndGameScreen;
import src.ca.ucalgary.seng300.gameApp.extraScreens.HelpScreen;
import src.ca.ucalgary.seng300.gameApp.gameScreens.*;
import src.ca.ucalgary.seng300.gameApp.loadingScreens.QueueScreen;
import src.ca.ucalgary.seng300.gameApp.loadingScreens.ServerConnectionScreen;
import src.ca.ucalgary.seng300.gameApp.menuScreens.GameMenuScreen;
import src.ca.ucalgary.seng300.gameApp.menuScreens.MainMenuScreen;
import src.ca.ucalgary.seng300.gameApp.menuScreens.MatchmakeChoiceScreen;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;

import java.util.ArrayList;

/**
 * ScreenController is responsible for managing the application screens.
 * This class should not be run directly. Use Main.java as the application entry point.
 */
public final class ScreenController extends Application {
    private Stage primaryStage;
    Client client = new Client();

    AuthService authService = new AuthService();
    ProfileService profileService = new ProfileService(authService);

    /**
     * Starts the application and initializes the primary stage.
     *
     * @param primaryStage The main stage of the application.
     * @throws Exception If an error occurs during startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showSignInScreen();
        primaryStage.show();
    }

    /**
     * Disconnects the client and exits the application.
     */
    @Override
    public void stop() {
        System.out.println("Closing Connection to Server...");
        client.disconnect();
        System.exit(0);
    }

    /**
     * Shows the main menu screen.
     */
    public void showMainMenu() {
        this.primaryStage = primaryStage;
        MainMenuScreen mainMenu = new MainMenuScreen(primaryStage, this, client);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(mainMenu.getScene());
    }

    /**
     * Shows the game menu screen.
     */
    public void showGameMenu() {
        GameMenuScreen gameMenu = new GameMenuScreen(primaryStage, this, client);
        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(gameMenu.getScene());
    }

    /**
     * Shows the Tic-Tac-Toe game screen.
     *
     * @param match A list of players participating in the game.
     */
    public void showTictactoeGameScreen(ArrayList<Player> match) {
        TictactoeGameScreen ticTacToe = new TictactoeGameScreen(primaryStage, this, client, match);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(ticTacToe.getScene());
    }

    /**
     * Shows the server connection screen.
     *
     * @param disconnectCheck Whether the user is disconnecting.
     */
    public void showServerConnectionScreen(boolean disconnectCheck) {
        ServerConnectionScreen connection = new ServerConnectionScreen(primaryStage, this, client, disconnectCheck);
        primaryStage.setScene(connection.getScene());
    }

    /**
     * Shows the sign-in screen.
     */
    public void showSignInScreen() {
        SignInScreen signIn = new SignInScreen(primaryStage, this, client);
        primaryStage.setTitle("Sign In");
        primaryStage.setScene(signIn.getScene());
    }

    /**
     * Shows the profile management screen.
     */
    public void showManageProfileScreen() {
        ManageProfileScreen manageProfile = new ManageProfileScreen(primaryStage, this, client);
        primaryStage.setTitle("Manage Profile");
        primaryStage.setScene(manageProfile.getScene());
    }

    /**
     * Shows the help screen.
     */
    public void showHelpScreen() {
        HelpScreen helpScreen = new HelpScreen(primaryStage, this, client);
        primaryStage.setTitle("Help Screen");
        primaryStage.setScene(helpScreen.getScene());
    }

    /**
     * Shows the queue screen for a specific game type.
     *
     * @param gameType The type of game.
     */
    // needs an input to distinguish game type, since all games shares the same screen
    public void showQueueScreen(int gameType) {
        QueueScreen queue = new QueueScreen(primaryStage, this, gameType, client);
        primaryStage.setTitle("Queue Screen");
        primaryStage.setScene(queue.getScene());

    }

    /**
     * Shows the end-game screen.
     *
     * @param gameType          The type of game played.
     * @param boardManager      The board manager for Tic-Tac-Toe.
     * @param connect4Logic     The game logic for Connect4.
     * @param checkersGameLogic The game logic for Checkers.
     * @param match             The players in the match.
     * @param winner            The winner of the game (or null for a draw).
     */
    public void showEndGameScreen(int gameType, BoardManager boardManager, Connect4Logic connect4Logic, CheckersGameLogic checkersGameLogic, ArrayList<Player> match, Player winner) {
        EndGameScreen endGame = new EndGameScreen(primaryStage, this, client, gameType, boardManager, connect4Logic, checkersGameLogic, match, winner);
        primaryStage.setTitle("End Game Screen");
        primaryStage.setScene(endGame.getScene());
    }

    /**
     * Shows the profile creation screen.
     */
    public void showCreateProfileScreen() {
        CreateProfileScreen createProfile = new CreateProfileScreen(primaryStage, this, client);
        primaryStage.setTitle("Create Profile");
        primaryStage.setScene(createProfile.getScene());

    }
    /**
     * Shows the user profile screen.
     */
    public void showUserProfileScreen(String initialUser) {
        UserProfileScreen userProfile = new UserProfileScreen(primaryStage, this, client, initialUser);
        primaryStage.setTitle("View Profile");
        primaryStage.setScene(userProfile.getScene());

    }

    /**
     * Shows the Connect4 game screen.
     *
     * @param match A list of players participating in the game.
     */
    public void showConnect4Screen(ArrayList<Player> match) {
        Connect4Screen connect4 = new Connect4Screen(primaryStage, this, client, match);
        primaryStage.setTitle("Connect 4");
        primaryStage.setScene(connect4.getScene());
    }

    /**
     * Shows the leaderboard screen.
     */
    public void showLeaderBoard() {
        LeaderboardController leaderBoard = new LeaderboardController(primaryStage, this, client);
        primaryStage.setTitle("Leaderboard");
        primaryStage.setScene(leaderBoard.getScene());
    }

    /**
     * Shows the Checker game screen.
     *
     * @param match A list of players participating in the game.
     */
    public void showCheckerScreen(ArrayList<Player> match) {
        // Create Player instances for player1 and player2
        Player player1 = new Player("Checkers", "Player1", 1200, 0, 0, 0);
        Player player2 = new Player("Checkers", "Player2", 1200, 0, 0, 0);

        // Create CheckersGameLogic with player1 and player2
        CheckersGameLogic gameLogic = new CheckersGameLogic( player1, player2);

        // Pass CheckersGameLogic and client into CheckerScreen
        CheckerScreen checkers = new CheckerScreen(primaryStage, this, client,match);

        // Set up the stage
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(checkers.getScene());
    }

    public void showMatchmakeChoiceScreen(int gameType) {
        MatchmakeChoiceScreen matchmakeScreen = new MatchmakeChoiceScreen(primaryStage, this, gameType, client);
        primaryStage.setTitle("matchmake Screen");
        primaryStage.setScene(matchmakeScreen.getScene());
    }

    /**
     * Shows the "forgot password" screen.
     */
    public void showForgotPasswordScreen() {
        ForgotPasswordScreen forgotPassword = new ForgotPasswordScreen(primaryStage, this, client, authService);
        primaryStage.setTitle("Forgot Password");
        primaryStage.setScene(forgotPassword.getScene());
    }

    /**
     * Shows the password restoration screen.
     */
    public void showResetPasswordScreen(String username, String email) {
        ResetPasswordScreen resetPassword = new ResetPasswordScreen(primaryStage, this, client, username, email, authService);
        primaryStage.setTitle("Reset Password");
        primaryStage.setScene(resetPassword.getScene());
    }

    /**
     * Shows the "forgot username" screen.
     */
    public void showForgotUsernameScreen() {
        ForgotUsernameScreen showUsername = new ForgotUsernameScreen(primaryStage, this, client, authService);
        primaryStage.setTitle("Find Username");
        primaryStage.setScene(showUsername.getScene());

    }

    /**
     * Shows the Connect4 game-rules screen.
     */
    public void showConnectFourRules() {
        ConnectFourRules c4Rules = new ConnectFourRules(primaryStage, this, client);
        primaryStage.setTitle("Connect Four Rules");
        primaryStage.setScene(c4Rules.getScene());
    }

    /**
     * Shows the TicTacToe game-rules screen.
     */
    public void showTTTRules() {
        TTTRules TTTrules = new TTTRules(primaryStage, this, client);
        primaryStage.setTitle("Tic-Tac-Toe Rules");
        primaryStage.setScene(TTTrules.getScene());
    }

    /**
     * Shows the Checkers game-rules screen.
     */
    public void showCheckersRules() {
        CheckersRules CHRules = new CheckersRules(primaryStage, this, client);
        primaryStage.setTitle("Checkers' Rules");
        primaryStage.setScene(CHRules.getScene());
    }

    /**
     * Shows the challenge player screen.
     */
    public void showChallengePlayerScreen(String challengeUser, int gameChoice) {
        ChallengePlayerScreen challengeScreen = new ChallengePlayerScreen(primaryStage, this, client, challengeUser, gameChoice);
        primaryStage.setTitle("Challenge Player Screen");
        primaryStage.setScene(challengeScreen.getScene());
    }

    public void showMatchHistoryScreen(String userDisplay) {
        MatchHistoryScreen matchHistoryScreen = new MatchHistoryScreen(primaryStage, this, client, userDisplay);
        primaryStage.setTitle("Match History");
        primaryStage.setScene(matchHistoryScreen.getScene());
    }
}
