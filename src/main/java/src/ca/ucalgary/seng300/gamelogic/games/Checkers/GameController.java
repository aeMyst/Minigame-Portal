package src.ca.ucalgary.seng300.gamelogic.games.Checkers;

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

        while (gameStateHandler.getState() != GameState.GAME_OVER) {
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

                if (isGameOver()) {
                    gameStateHandler.setState(GameState.GAME_OVER);
                    System.out.println("Game Over! Player " + (gameLogic.getCurrentPlayer() == PlayerID.PLAYER1 ? "Black" : "White") + " wins!");
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

    /**
     * Handles a single player's turn and returns the coordinates of the piece moved.
     *
     * @param currentPlayer The current player making the move.
     * @return An array containing the row and column of the piece moved, or null if the move was unsuccessful.
     */
    private int[] handlePlayerTurn(PlayerID currentPlayer) {
        // Logic to handle a single turn
        PlayerInteraction playerInteraction = new PlayerInteraction(gameLogic);

        // Returns the coordinates of the moved piece if successful, or null if no move was made
        return playerInteraction.handlePlayerTurn(currentPlayer);
    }

    private boolean isGameOver() {
        int whiteCount = 0;
        int blackCount = 0;

        for (int[] row : gameLogic.getBoard()) {
            for (int cell : row) {
                if (cell == 1 || cell == 3) whiteCount++;
                else if (cell == 2 || cell == 4) blackCount++;
            }
        }
        return whiteCount == 0 || blackCount == 0;
    }
}