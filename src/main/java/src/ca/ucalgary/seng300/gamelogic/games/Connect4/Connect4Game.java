package src.ca.ucalgary.seng300.gamelogic.games.Connect4;

public class Connect4Game extends StartGame{
    private Connect4Logic logicManager;
    private TurnManager turnManager;

    public Connect4Game() {
        logicManager = new Connect4Logic();
        turnManager = new TurnManager(new UserPiece(1), new UserPiece(2));
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
}
