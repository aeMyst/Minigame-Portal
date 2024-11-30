package src.ca.ucalgary.seng300.gameApp.gameScreens;

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
import src.ca.ucalgary.seng300.gamelogic.Checkers.GameState;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;

import java.util.ArrayList;


public class EndGameScreen implements IScreen {
    private Scene scene;

    public EndGameScreen(Stage stage, ScreenController controller, Client client, int gameType,
                         BoardManager boardManager, Connect4Logic connect4Logic, CheckersGameLogic checkersGameLogic,
    ArrayList<Player> match, Player winner) {
        // elo object
        EloRating eloRating = new EloRating();

        Label boardPrint = new Label();
        boardPrint.setFont(new Font("Courier New", 24));

        // string initialization
        int currentWinnerElo;
        int currentLoserElo;
        String winnerString = "";
        String loserString = "";
        String eloGain = "";
        String eloLoss = "";

        // loser Player object
        Player loser;

        if (winner != null) {
            loser = (match.get(0).equals(winner)) ? match.get(1) : match.get(0);
            winnerString = winner.getPlayerID();
            loserString = loser.getPlayerID();
            currentWinnerElo = winner.getElo();
            currentLoserElo = loser.getElo();
            // adjust elo
            eloRating.updateElo(winner, loser);
            winner.setWins(winner.getWins() + 1);
            loser.setLosses(loser.getLosses() + 1);
            eloGain = String.valueOf(winner.getElo() - currentWinnerElo);
            eloLoss = String.valueOf(currentLoserElo - loser.getElo());

            for (Player player : match) {
                if (player.getPlayerID().equals(winner.getPlayerID())) {
                    player = winner;
                } else {
                    player = loser;
                }
            }

        } else {
            for (Player player : match) {
                player.setTies(player.getTies() + 1);
            }
        }

        // update stats db after match has finished
        FileManagement.updateProfilesInCsv(client.getStatPath(), match);

        if (gameType == 0) {
            boardPrint.setText(printGameResults(boardManager.getBoard(), null, 0));
        } else if (gameType == 1) {
            boardPrint.setText(printGameResults(null, connect4Logic.getBoard(), 1));
        } else if (gameType == 2) {
            boardPrint.setText(printGameResults(null, checkersGameLogic.getBoard(), 2));
        }


        Label titleLabel = new Label("End of Match, Here are the Game Results:");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setStyle("-fx-font-weight: bold");

        Label winnerLabel = new Label();
        winnerLabel.setFont(new Font("Arial", 24));
        winnerLabel.setStyle("-fx-font-weight: bold");

        Label loserLabel = new Label();
        loserLabel.setFont(new Font("Arial", 24));
        loserLabel.setStyle("-fx-font-weight: bold");

        if (winner != null) {
            winnerLabel.setText("The Winner Was " + winnerString + " And Has Gained: +" + eloGain + " ELO");
            loserLabel.setText("The Loser Was " + loserString + " And Has Loss: -" + eloLoss + " ELO");
        } else {
            winnerLabel.setText("It was a Draw! Play again?");
        }

        Button playAgainButton = new Button("Rematch");
        playAgainButton.setFont(new Font("Arial", 16));
        playAgainButton.setPrefWidth(200);
        playAgainButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        playAgainButton.setOnAction(e -> {
            if (gameType == 0) {
                client.createGameSession();
                controller.showTictactoeGameScreen(match);
            } else if (gameType == 1) {
                client.createGameSession();
                controller.showConnect4Screen(match);
            } else if (gameType == 2) {
                client.createGameSession();
                controller.showCheckerScreen(match);
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

        VBox layout = new VBox(15, titleLabel, boardPrint, winnerLabel, loserLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");
        scene = new Scene(layout, 1280, 900);
    }

    public String printGameResults(char[][] charArray , int[][] intArray, int gameType) {
        String buildString = "";

        if (gameType == 0) {
            for (char[] row : charArray) {
                for (char cell : row) {
                    // chat-gpt generated to print out nicely if a cell is empty
                    buildString += String.format("%-3s", (cell == '\0' || cell == ' ') ? "~" : cell);
                }
                buildString += "\n\n";
            }
        } else {
            for (int[] row : intArray) {
                for (int cell : row) {
                    // Replace 0 with '~', otherwise print the number
                    buildString += String.format("%-3s", (cell == 0) ? "~" : cell);
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
