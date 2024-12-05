package src.ca.ucalgary.seng300.gamelogic.Checkers;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

/**
 * This class implements the logic for a Checkers game. It provides methods
 * for managing the game state, validating moves, capturing pieces, promoting pieces to kings,
 * and handling player turns.
 */
public class CheckersGameLogic implements ICheckers {

    private int[][] board;// The Checkers board represented as a 2D array.
    private GameState gameState;// The current state of the game.
    private Player currentPlayer;// The player whose turn it is
    private final Player player1;// Player 1 instance
    private final Player player2;;// Player 2 instance

    /**
     * Constructor for CheckersGameLogic. Initializes the board, game state, and players.
     *
     * @param player1 the first player.
     * @param player2 the second player.
     */
    public CheckersGameLogic( Player player1, Player player2) {
        this.board = CheckersBoard.createBoard();// Initialize the board
        this.gameState = GameState.START;// Set the initial game state
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1; // Start with Player 1

    }

    /**
     * @return the current state of the game.
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @return the number of players in the game (always 2 for Checkers).
     */
    @Override
    public int getNumPlayers() {
        return 2;
    }

    /**
     * @return the player whose turn it is.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return the current Checkers board as a 2D array.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Validates whether the given player has selected a piece at the specified position.
     *
     * @param row    the row index of the board.
     * @param col    the column index of the board.
     * @param player the player attempting to select the piece.
     * @return true if the player has selected a valid piece; false otherwise.
     */
    @Override
    public boolean playerSelectedPiece(int row, int col, Player player) {
        if (!isWithinBounds(row, col)) return false;    // ensure the position is valid
        int piece = board[row][col];
        int requiredPiece = (player == player1) ? 1 : 2;
        int kingPiece = requiredPiece + 2;

        // recognize both normal and king pieces
        return piece == requiredPiece || piece == kingPiece;
    }

    /**
     * Attempts to move a piece for the specified player.
     *
     * @param fromRow the starting row index.
     * @param fromCol the starting column index.
     * @param toRow   the target row index.
     * @param toCol   the target column index.
     * @param player  the player making the move.
     * @return true if the move is valid and completed; false otherwise.
     */
    @Override
    public boolean playerMovedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (!isValidMove(fromRow, fromCol, toRow, toCol, player)) {
            return false;
        }

        // Perform the move
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = 0;

        // Check for king promotion
        if (checkKingPromotion(toRow, toCol, player)) {
            promoteToKing(toRow, toCol, player);
        }
        return true;
    }

    /**
     * Attempts to capture an opponent's piece for the specified player.
     *
     * @param fromRow the starting row index.
     * @param fromCol the starting column index.
     * @param toRow   the target row index.
     * @param toCol   the target column index.
     * @param player  the player making the capture.
     * @return true if the capture is valid and completed; false otherwise.
     */
    @Override
    public boolean playerCapturedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (!isValidCapture(fromRow, fromCol, toRow, toCol, player)) {
            return false;
        }

        int midRow = (fromRow + toRow) / 2;// Find the row of the captured piece.
        int midCol = (fromCol + toCol) / 2;// Find the column of the captured piece.

        board[midRow][midCol] = 0; // Remove captured piece
        board[toRow][toCol] = board[fromRow][fromCol]; // Move piece
        board[fromRow][fromCol] = 0;

        // Check for king promotion
        if (checkKingPromotion(toRow, toCol, player)) {
            promoteToKing(toRow, toCol, player);
        }
        return true;
    }

    /**
     * Promotes a piece to a king if it reaches the opponent's back row.
     *
     * @param row    the row index of the piece.
     * @param col    the column index of the piece.
     * @param player the player who owns the piece.
     */
    @Override
    public void promoteToKing(int row, int col, Player player) {
        if (!isWithinBounds(row, col)) {
            return;
        }

        int piece = board[row][col];
        int requiredPiece = (player == player1) ? 1 : 2;

        if (piece == requiredPiece && checkKingPromotion(row, col, player)) {
            board[row][col] = piece + 2; // Promote to king
        }
    }

    /**
     * Checks if a move is valid for the specified player.
     *
     * @param fromRow the starting row.
     * @param fromCol the starting column.
     * @param toRow   the target row.
     * @param toCol   the target column.
     * @param player  the player attempting the move.
     * @return true if the move is valid; false otherwise.
     */
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        // Ensure both the starting and target positions are within board boundaries
        if (!isWithinBounds(fromRow, fromCol) || !isWithinBounds(toRow, toCol)) return false;

        // The target position must be empty
        if (board[toRow][toCol] != 0) return false;

        int piece = board[fromRow][fromCol];
        int requiredPiece = (player == player1) ? 1 : 2;// Identify the player's piece
        int kingPiece = requiredPiece + 2;// The player's king piece

        // Verify that the piece belongs to the player (normal or king)
        if (piece != requiredPiece && piece != kingPiece) {
            return false;
        }

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // Ensure the move is diagonal and one step
        if (Math.abs(rowDiff) != 1 || Math.abs(colDiff) != 1) {
            return false;
        }

        // For normal pieces, ensure movement is forward
        if (piece == 1 && rowDiff != 1) return false; // Player 1 moves down
        if (piece == 2 && rowDiff != -1) return false; // Player 2 moves up

        // Kings can move in any direction
        return true;
    }

    /**
     * Validates whether a capture is valid for the given player.
     *
     * @param fromRow the starting row index.
     * @param fromCol the starting column index.
     * @param toRow   the target row index.
     * @param toCol   the target column index.
     * @param player  the player attempting the capture.
     * @return true if the capture is valid; false otherwise.
     */
    public boolean isValidCapture(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        // Ensure both the starting and target positions are within board boundaries
        if (!isWithinBounds(fromRow, fromCol) || !isWithinBounds(toRow, toCol)) return false;

        // The target position must be empty
        if (board[toRow][toCol] != 0) return false;

        int piece = board[fromRow][fromCol];
        int opponentPiece = (player == player1) ? 2 : 1;// Opponent's normal piece
        int opponentKingPiece = opponentPiece + 2;// Opponent's king piece

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // Ensure the move is diagonal and two steps
        if (Math.abs(rowDiff) != 2 || Math.abs(colDiff) != 2) {
            return false;
        }

        // For normal pieces, ensure movement is forward
        if (piece == 1 && rowDiff != 2) return false; // Player 1 captures down
        if (piece == 2 && rowDiff != -2) return false; // Player 2 captures up

        // Determine the position of the piece being captured.
        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;

        int midPiece = board[midRow][midCol];

        // Check if the middle piece is an opponent's piece
        if (midPiece != opponentPiece && midPiece != opponentKingPiece) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a specific piece for the player has any valid capture moves.
     *
     * @param row    the row index of the piece.
     * @param col    the column index of the piece.
     * @param player the player owning the piece.
     * @return true if there is a valid capture; false otherwise.
     */
    public boolean hasValidCapture(int row, int col, Player player) {
        int piece = board[row][col];
        if (piece == 0) return false;// Ensure the position is not empty.

        int requiredPiece = (player == player1) ? 1 : 2;
        int kingPiece = requiredPiece + 2;

        // Check that the piece belongs to the player
        if (piece != requiredPiece && piece != kingPiece) {
            return false;
        }

        // Define directions for valid captures based on piece type (normal or king)
        int[] rowDirs;
        if (piece == requiredPiece) {
            // Normal pieces
            rowDirs = (player == player1) ? new int[]{2} : new int[]{-2};
        } else {
            // Kings
            rowDirs = new int[]{-2, 2};
        }
        int[] colDirs = {-2, 2};// Captures always involve diagonal moves

        // Check each potential capture direction
        for (int rd : rowDirs) {
            for (int cd : colDirs) {
                int toRow = row + rd;
                int toCol = col + cd;
                if (isValidCapture(row, col, toRow, toCol, player)) {
                    return true;// A valid capture exists
                }
            }
        }
        return false;// No valid captures found
    }

    /**
     * Checks if any pieces of the given player have valid capture moves.
     *
     * @param player the player to check for valid captures.
     * @return true if the player has any valid captures; false otherwise.
     */
    public boolean hasAnyValidCaptures(Player player) {
        int requiredPiece = (player == player1) ? 1 : 2;
        int kingPiece = requiredPiece + 2;

        // Iterate over all board positions to find player's pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == requiredPiece || piece == kingPiece) {
                    if (hasValidCapture(row, col, player)) {
                        return true;// Found at least one valid capture
                    }
                }
            }
        }
        return false;// No valid captures
    }

    /**
     * Determines if a piece should be promoted to a king.
     *
     * @param row    the row index of the piece.
     * @param col    the column index of the piece.
     * @param player the player owning the piece.
     * @return true if the piece qualifies for king promotion; false otherwise.
     */
    public boolean checkKingPromotion(int row, int col, Player player) {
        // Player 1 promotes upon reaching the last row (index 7)
        if (player == player1 && row == 7) {
            return true;
        } else if (player == player2 && row == 0) {// Player 2 promotes upon reaching the first row (index 0)
            return true;
        }
        return false;
    }

    /**
     * Forfeits the game and declares the opponent as the winner.
     */
    public void forfeitGame() {
        if (getCurrentPlayer() == player1) {
            gameState = GameState.PLAYER2_WIN;
        } else {
            gameState = GameState.PLAYER1_WIN;
        }
    }

    /**
     * Checks if a given position is within the bounds of the Checkers board.
     *
     * @param row the row index.
     * @param col the column index.
     * @return true if the position is within bounds; false otherwise.
     */
    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Switches the turn to the other player.
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("Switched to " + currentPlayer.getPlayerID() + "'s turn.");
    }
}
