package src.ca.ucalgary.seng300.gameApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.Client;
import src.ca.ucalgary.seng300.gamelogic.games.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.games.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.games.Checkers.CheckersGameLogic;


public class EndGameScreen implements IScreen {
    private Scene scene;

    public EndGameScreen(Stage stage, ScreenController controller, Client client, int gameType,
                         BoardManager boardManager, Connect4Logic connect4Logic, CheckersGameLogic checkersGameLogic) {

        Label boardPrint = new Label();
        boardPrint.setFont(new Font("Arial", 24));

        if (gameType == 0) {
            boardPrint.setText(printGameResults(boardManager.getBoard(), null, 0));
        } else if (gameType == 1) {
            boardPrint.setText(printGameResults(null, connect4Logic.getBoard(), 1));
        } else if (gameType == 2) {
            boardPrint.setText(printGameResults(null, checkersGameLogic.getBoard(), 2));
        }

        Label titleLabel = new Label("End of Match");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        Label winnerLabel = new Label("The Winner Was: ");
        winnerLabel.setFont(new Font("Arial", 24));

        Label eloLabel = new Label("Player has gained: ");
        eloLabel.setFont(new Font("Arial", 24));

        Button playAgainButton = new Button("Rematch");
        playAgainButton.setFont(new Font("Arial", 16));
        playAgainButton.setPrefWidth(200);
        playAgainButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        playAgainButton.setOnAction(e -> {
            if (gameType == 0) {
                client.createGameSession();
                controller.showTictactoeGameScreen();
            } else if (gameType == 1) {
                client.createGameSession();
                controller.showConnect4Screen();
            } else if (gameType == 2) {
                client.createGameSession();
                controller.showCheckerScreen();
            }
        });

        Button exitButton = new Button("Exit Game");
        exitButton.setFont(new Font("Arial", 16));
        exitButton.setPrefWidth(200);
        exitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        exitButton.setOnAction(e -> {
            client.disconnectGameSession();
            controller.showGameMenu();
        });

        HBox buttonLayout = new HBox(10, playAgainButton, exitButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, titleLabel, boardPrint, winnerLabel, eloLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");
        scene = new Scene(layout, 800, 600);
    }

    public String printGameResults(char[][] charArray , int[][] intArray, int gameType) {
        String buildString = "";

        if (gameType == 0) {
            for (char[] row : charArray) {
                for (char cell : row) {
                    buildString += cell + " ";
                }
                buildString += "\n";
            }
        } else {
            for (int[] row : intArray) {
                for (int cell : row) {
                    buildString += cell + " ";
                }
                buildString += "\n";
            }
        }
        return buildString;
    }


    @Override
    public Scene getScene() {
        return scene;
    }
}
