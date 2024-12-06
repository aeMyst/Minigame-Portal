package src.ca.ucalgary.seng300.gameApp.loadingScreens;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gameApp.Utility.TipsUtility;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.logic.MatchMaker;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;

import java.io.File;
import java.util.List;
import java.util.Random;
/**
 * screen challengePlayer class
 */
public class ChallengePlayerScreen implements IScreen {
    private Scene scene;

    private boolean canceled = false;
    /**
     * challenger player screen constructor
     */
    public ChallengePlayerScreen(Stage stage, ScreenController controller, Client client, String challengeUser, int gameType) {
        System.out.println("Finding Correct User... Challenge Request sent...");

        File players = new File(client.getStatPath());
        String currentUser = client.getCurrentUsername();
        Storage allPlayers = FileManagement.fileReading(players);

        MatchMaker queue = new MatchMaker(allPlayers);

        // Title label
        Label joiningLabel = new Label("Challenge Request Sent To: " + challengeUser);
        joiningLabel.getStyleClass().add("title-label");

        // Progress indicator
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(250, 250);

        // Tip label
        Label tipLabel = new Label();
        joiningLabel.getStyleClass().add("tip-label");

        // Load game-specific tips using TipsUtility
        List<String> tips = TipsUtility.loadTipsFromFile(client, gameType);

        // Start thread to update the tip label
        new Thread(() -> {
            Random random = new Random();
            while (!canceled) {
                // Select a random tip
                String randomTip = tips.get(random.nextInt(tips.size()));

                // Update the tip label on the JavaFX thread
                Platform.runLater(() -> tipLabel.setText("Tip: " + randomTip));

                // Wait for 5 seconds before updating again
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Button to cancel joining game
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("button");
        cancelButton.getStyleClass().add("exit-button");
        cancelButton.setOnAction(e -> {
            canceled = true;
            client.cancelQueue();
            controller.showGameMenu();
        });

        // Layout
        VBox layout = new VBox(20, joiningLabel, progressIndicator, tipLabel, cancelButton);
        layout.setAlignment(Pos.CENTER);

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("root-pane");

        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));

        // Simulate a delay for connecting to another player
        new Thread(() -> {
            try {
                Thread.sleep(5000); // 5-second delay to simulate connecting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!canceled) {

                if (gameType == 0) {
                    queue.challengePlayerQueue(challengeUser, currentUser, "TICTACTOE");
                } else if (gameType == 1) {
                    queue.challengePlayerQueue(challengeUser, currentUser, "CONNECT4");
                } else if (gameType == 2) {
                    queue.challengePlayerQueue(challengeUser, currentUser, "CHECKERS");
                }

                // Update the label to indicate a player was found
                //
                // ChatGPT Generated: how to get dynamic labels using a runLater
                //
                Platform.runLater(() -> joiningLabel.setText("Player Accepted Challenge!"));

                // Additional delay before setting up the game
                try {
                    Thread.sleep(2000); // 2-second delay to display "Player Found!"
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!canceled) {
                    // Update the label to indicate setting up the game
                    Platform.runLater(() -> joiningLabel.setText("Setting up game..."));

                    // Delay before transitioning to the game screen
                    try {
                        Thread.sleep(2000); // 2-second delay to display "Setting up game..."
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    client.queueGame();

                    if (!canceled) {
                        Platform.runLater(() -> {
                            switch (gameType) {
                                case 0:
                                    controller.showTictactoeGameScreen(queue.createMatch());
                                    break;
                                case 1:
                                    controller.showConnect4Screen(queue.createMatch());
                                    break;
                                case 2:
                                    controller.showCheckerScreen(queue.createMatch());
                                    break;
                            }
                        });
                    }
                }
            }
        }).start();
    }

    /**
     * method for returning the screen
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
