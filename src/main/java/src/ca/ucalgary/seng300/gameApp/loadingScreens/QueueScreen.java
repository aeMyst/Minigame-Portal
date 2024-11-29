package src.ca.ucalgary.seng300.gameApp.loadingScreens;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.logic.MatchMaker;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.Utility.TipsUtility;

import java.io.File;
import java.util.List;
import java.util.Random;

public class QueueScreen implements IScreen {
    private Scene scene;
    private boolean canceled = false;

    public QueueScreen(Stage stage, ScreenController controller, int gameType, Client client) {
        System.out.println("Queueing...");

        File players = new File(client.getStatPath());
        String currentUser = client.getCurrentUsername();
        Storage allPlayers = FileManagement.fileReading(players);

        MatchMaker queue = new MatchMaker(allPlayers);

        // Title label
        Label joiningLabel = new Label("Searching for Player...");
        joiningLabel.setFont(new Font("Arial", 24));
        joiningLabel.setTextFill(Color.DARKBLUE);

        // Progress indicator
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100, 100);

        // Tip label
        Label tipLabel = new Label();
        tipLabel.setFont(new Font("Arial", 16));
        tipLabel.setTextFill(Color.DARKGREEN);

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
        cancelButton.setFont(new Font("Arial", 16));
        cancelButton.setPrefWidth(200);
        cancelButton.setStyle("-fx-background-color: #af4c4c; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> {
            canceled = true;
            client.cancelQueue();
            controller.showGameMenu();
        });

        // Layout
        VBox layout = new VBox(20, joiningLabel, progressIndicator, tipLabel, cancelButton);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 1280, 900);

        // Simulate a delay for connecting to another player
        new Thread(() -> {
            try {
                Thread.sleep(5000); // 5-second delay to simulate connecting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!canceled) {

                if (gameType == 0) {
                    queue.addPlayerToQueue(currentUser, "TICTACTOE");
                    queue.createMatch();
                } else if (gameType == 1) {
                    queue.addPlayerToQueue(currentUser, "CONNECT4");
                    queue.createMatch();
                } else if (gameType == 2) {
                    queue.addPlayerToQueue(currentUser, "CHECKERS");
                    queue.createMatch();
                }

                // Update the label to indicate a player was found
                Platform.runLater(() -> joiningLabel.setText("Player Found!"));

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
                                    controller.showConnect4Screen();
                                    break;
                                case 2:
                                    controller.showCheckerScreen();
                                    break;
                            }
                        });
                    }
                }
            }
        }).start();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}


