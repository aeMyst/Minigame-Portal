package src.ca.ucalgary.seng300.gameApp.gameScreens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryPlayer;
import src.ca.ucalgary.seng300.leaderboard.data.HistoryStorage;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EndGameScreen implements IScreen {
    private Scene scene;
    private static final String FILE_PATH = "src/main/java/src/ca/ucalgary/seng300/database/match_history.txt";
    private static final String USERS_PATH = "src/main/java/src/ca/ucalgary/seng300/database/users.csv";
    private File file = new File(FILE_PATH);

    public EndGameScreen(Stage stage, ScreenController controller, Client client, int gameType,
                         BoardManager boardManager, Connect4Logic connect4Logic, CheckersGameLogic checkersGameLogic,
                         ArrayList<Player> match, Player winner) {
        // ELO Object
        EloRating eloRating = new EloRating();
        Label boardPrint = new Label();
        boardPrint.getStyleClass().add("board-print");

        // String initialization
        int currentWinnerElo;
        int currentLoserElo;
        String winnerString = "";
        String loserString = "";
        String eloGain = "";
        String eloLoss = "";

        // Loser Player object
        Player loser;

        if (winner != null) {
            loser = (match.get(0).equals(winner)) ? match.get(1) : match.get(0);
            winnerString = winner.getPlayerID();
            loserString = loser.getPlayerID();
            currentWinnerElo = winner.getElo();
            currentLoserElo = loser.getElo();
            // Adjust ELO
            eloRating.updateElo(winner, loser);
            winner.setWins(winner.getWins() + 1);
            loser.setLosses(loser.getLosses() + 1);

            eloGain = String.valueOf(winner.getElo() - currentWinnerElo);
            eloLoss = String.valueOf(currentLoserElo - loser.getElo());

            for (Player player : match) {
                updateMatchHistory(player.getGameType(), player.getPlayerID(),winnerString, loserString, Integer.parseInt(eloGain), Integer.parseInt(eloLoss),LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));
            }

        } else {
            for (Player player : match) {
                player.setTies(player.getTies() + 1);
            }
        }

        // Update stats db
        FileManagement.updateProfilesInCsv(client.getStatPath(), match);

        if (gameType == 0) {
            boardPrint.setText(printGameResults(boardManager.getBoard(), null, 0));
        } else if (gameType == 1) {
            boardPrint.setText(printGameResults(null, connect4Logic.getBoard(), 1));
        } else if (gameType == 2) {
            boardPrint.setText(printGameResults(null, checkersGameLogic.getBoard(), 2));
        }

        // Title label
        Label titleLabel = new Label("End of Match, Here are the Game Results:");
        titleLabel.getStyleClass().add("title-label");

        Label winnerLabel = new Label();
        winnerLabel.getStyleClass().add("result-label");

        Label loserLabel = new Label();
        loserLabel.getStyleClass().add("result-label");

        if (winner != null) {
            winnerLabel.setText("The Winner Was " + winnerString + " And Has Gained: +" + eloGain + " ELO");
            loserLabel.setText("The Loser Was " + loserString + " And Has Lost: -" + eloLoss + " ELO");
        } else {
            winnerLabel.setText("It was a Draw! Play again?");
        }

        Button playAgainButton = new Button("Rematch");
        playAgainButton.getStyleClass().add("button");
        playAgainButton.getStyleClass().add("submit-button");
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
        exitButton.getStyleClass().add("button");
        exitButton.getStyleClass().add("exit-button");
        exitButton.setOnAction(e -> {
            client.disconnectGameSession();
            controller.showGameMenu();
        });

        HBox buttonLayout = new HBox(10, playAgainButton, exitButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, titleLabel, boardPrint, winnerLabel, loserLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("root-pane");

        scene = new Scene(layout, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("GamesStyles.css").toExternalForm()));
    }

    public String printGameResults(char[][] charArray, int[][] intArray, int gameType) {
        StringBuilder buildString = new StringBuilder();

        if (gameType == 0) {
            for (char[] row : charArray) {
                for (char cell : row) {
                    buildString.append(String.format("%-3s", (cell == '\0' || cell == ' ') ? "~" : cell));
                }
                buildString.append("\n\n");
            }
        } else {
            for (int[] row : intArray) {
                for (int cell : row) {
                    buildString.append(String.format("%-3s", (cell == 0) ? "~" : cell));
                }
                buildString.append("\n");
            }
        }
        return buildString.toString();
    }

    public void updateMatchHistory(String gameType, String player, String winnerString, String loserString, int eloGained, int eloLost, String date) {

        HistoryStorage storage;

        int max = 0;
        int lineCount = 0;
        int gameCount = 0;

        try {
            int userCount = FileManagement.countLinesInCSV(new File(USERS_PATH));
            storage = FileManagement.fileReadingHistory(file);
            if (userCount > 0) {
                max = userCount * 2;
            }
            lineCount = FileManagement.countLinesInTextFile(file);

            storage = FileManagement.fileReadingHistory(file);

            for (HistoryPlayer hp : storage.getPlayersHistory()) {
                String id = hp.getPlayerIDHistory();
                if (id.equals(player)) {    // checking if players has more than 2 recorded games in history
                    gameCount++;
                }
            }

            if (gameCount > 2) {
                FileManagement.clearOtherGameHistory(storage, file, player);
            }

            FileManagement.fileWritingHistory(file, storage, gameType, player, winnerString, loserString, eloGained, eloLost, date);


        } catch (Exception e) {
            System.out.println("Error fetching match history");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String[][] getMatchHistory(String player) {
        HistoryStorage storage;
        List<HistoryPlayer> history = new ArrayList<>();
        int count = 0;
        int counter = 0;

        if (file.exists()) {
            storage = FileManagement.fileReadingHistory(file);

            for (HistoryPlayer histPlayer : storage.getPlayersHistory()) {
                String playerID = histPlayer.getPlayerIDHistory();
                if (playerID.equals(player)) {
                    history.add(histPlayer);
                    count++;
                }
            }

            if (history.isEmpty()) {
                System.out.println("No match history is available.");
            }

            String[][] historyArr = new String[count][7];

            for (HistoryPlayer hp : history) {
                historyArr[counter][0] = hp.getGameTypeHistory();
                historyArr[counter][1] = hp.getPlayerIDHistory();
                historyArr[counter][2] = hp.getWinnerString();
                historyArr[counter][3] = hp.getLoserString();
                historyArr[counter][4] = String.valueOf(hp.getEloGained());
                historyArr[counter][5] = String.valueOf(hp.getEloLost());
                historyArr[counter][6] = hp.getDate();
                counter++;
            }

            return historyArr;
        } else {
            System.out.println("[ERROR] File does not exist.");
            return new String[0][];
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}

