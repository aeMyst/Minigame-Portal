package src.ca.ucalgary.seng300.gamelogic.Checkers;

public class GameController {
    private CheckersGameLogic gameLogic;      // The main game logic
    private GameStateHandler gameStateHandler; // Handles the game state
    private Graphic graphic;                  // For displaying the board

    public GameController() {
        this.graphic = new Graphic();             // Initialize Graphic instance
        this.gameLogic = new CheckersGameLogic(graphic); // Pass Graphic to game logic
        this.gameStateHandler = new GameStateHandler();
    }

    public void startGame() {
        gameStateHandler.setState(GameState.START);
        System.out.println("Game started! White moves first.");
        gameStateHandler.setState(GameState.PLAYER_TURN);
        playGame();
    }

    private void playGame() {
        // Initial display of the board
        graphic.update(gameLogic.getBoard());

        // Create a single PlayerInteraction instance for the entire game
        PlayerInteraction playerInteraction = new PlayerInteraction(gameLogic);

        while (gameStateHandler.getState() != GameState.PLAYER1_WIN && gameStateHandler.getState() != GameState.PLAYER2_WIN) {
            if (gameStateHandler.getState() == GameState.PLAYER_TURN) {
                System.out.println("Player " + gameLogic.getCurrentPlayer() + "'s turn.");

                // Handle player's turn and capture the row and col of the piece moved
                int[] movedPieceCoordinates = playerInteraction.handlePlayerTurn(gameLogic.getCurrentPlayer());
                graphic.update(gameLogic.getBoard()); // Update board display after each turn

                if (movedPieceCoordinates != null && gameLogic.checkKingPromotion(movedPieceCoordinates[0], movedPieceCoordinates[1], gameLogic.getCurrentPlayer())) {
                    gameStateHandler.setState(GameState.KING_PROMOTION);
                    gameLogic.promoteToKing(movedPieceCoordinates[0], movedPieceCoordinates[1], gameLogic.getCurrentPlayer());
                    System.out.println("Piece promoted to King.");
                }

                // Check for a winner
                PlayerID winner = checkForWinner();
                if (winner != null) {
                    if (winner == PlayerID.PLAYER1) gameStateHandler.setState(GameState.PLAYER1_WIN);
                    else gameStateHandler.setState(GameState.PLAYER2_WIN);
                    String winnerColor = (winner == PlayerID.PLAYER1) ? "White" : "Black";
                    System.out.println("Game Over! Player " + winnerColor + " wins!");
                } else {
                    // Only switch players if the game is not over
                    gameLogic.switchPlayer();
                    System.out.println("Switched to " + gameLogic.getCurrentPlayer() + "'s turn.");

                    // Ensure PLAYER_TURN is set after switching
                    gameStateHandler.setState(GameState.PLAYER_TURN);
                }
            }
        }
    }

    public void forfeitGame() {
        if (gameLogic.getCurrentPlayer() == PlayerID.PLAYER1) {
            gameStateHandler.setState(GameState.PLAYER2_WIN);
        } else {
            gameStateHandler.setState(GameState.PLAYER1_WIN);
        }
    }

    private PlayerID checkForWinner() {
        int whiteCount = 0;
        int blackCount = 0;

        for (int[] row : gameLogic.getBoard()) {
            for (int cell : row) {
                if (cell == 1 || cell == 3) whiteCount++;
                else if (cell == 2 || cell == 4) blackCount++;
            }
        }

        if (whiteCount == 0) {
            return PlayerID.PLAYER2; // Black wins
        } else if (blackCount == 0) {
            return PlayerID.PLAYER1; // White wins
        } else {
            return null; // No winner yet
        }
    }
}
