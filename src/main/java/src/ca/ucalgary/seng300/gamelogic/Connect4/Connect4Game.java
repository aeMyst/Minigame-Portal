package src.ca.ucalgary.seng300.gamelogic.Connect4;

import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.leaderboard.data.Storage;
import src.ca.ucalgary.seng300.leaderboard.logic.EloRating;
import src.ca.ucalgary.seng300.leaderboard.utility.FileManagement;


import java.io.File;

public class Connect4Game extends StartGame {
    private Connect4Logic logicManager;
    private TurnManager turnManager;
    private Player playerRed;
    private Player playerBlue;
    private EloRating eloRating;
    private Storage storage;

    public Connect4Game(Player playerRed, Player playerBlue, Storage storage) {
        this.logicManager = new Connect4Logic();
        this.turnManager = new TurnManager(new UserPiece(1), new UserPiece(2));
        this.playerRed = playerRed;
        this.playerBlue = playerBlue;
        this.eloRating = new EloRating();
        this.storage = storage;
    }

    @Override
    public void startGame() {
        while (true) {
            logicManager.printBoard(logicManager.getBoard());
            UserPiece currentPlayer = turnManager.getCurrentPlayer();

            System.out.println("Player " + currentPlayer.getPiece() + ", make your move.");

            int[] move = currentPlayer.userMove(logicManager);
            int col = move[1];

            if (!logicManager.valid(logicManager.getBoard(), 0, col)) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            if (!logicManager.placePiece(logicManager.getBoard(), col, currentPlayer.getPiece())) {
                System.out.println("The column is full.");
                continue;
            }

            if (logicManager.horizontalWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                logicManager.printBoard(logicManager.getBoard());
                System.out.println("Player " + currentPlayer.getPiece() + " wins!");
                break;
            } else if (logicManager.verticalWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                logicManager.printBoard(logicManager.getBoard());
                System.out.println("Player " + currentPlayer.getPiece() + " wins!");
                break;
            } else if (logicManager.forwardslashWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                logicManager.printBoard(logicManager.getBoard());
                System.out.println("Player " + currentPlayer.getPiece() + " wins!");
                break;
            } else if (logicManager.backslashWin(logicManager.getBoard(), currentPlayer.getPiece())) {
                logicManager.printBoard(logicManager.getBoard());
                System.out.println("Player " + currentPlayer.getPiece() + " wins!");
                break;
            } else if (logicManager.boardFull(logicManager.getBoard())) {
                logicManager.printBoard(logicManager.getBoard());
                System.out.println("Board is full. Tie game!");
                break;
            }
            turnManager.changeTurns();
        }
    }

    private void updateElo(UserPiece winner) {
        Player winnerPlayer = (winner.getPiece() == 1) ? playerRed : playerBlue;
        Player loserPlayer = (winner.getPiece() == 1) ? playerBlue : playerRed;

        eloRating.updateElo(winnerPlayer, loserPlayer);

        storage.updatePlayer(winnerPlayer);
        storage.updatePlayer(loserPlayer);

        System.out.println("New elo ratings:");
        System.out.println(winnerPlayer);
        System.out.println(loserPlayer);

        File file = new File("players_data.csv");
        FileManagement.fileWriting(storage, file);
        System.out.println("Player data saved.");
    }
}
