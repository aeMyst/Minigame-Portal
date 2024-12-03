package src.ca.ucalgary.seng300.gamelogic.Checkers;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

public interface ICheckers {

    /**
     * Retrieves the current state of the game.
     *
     * @return the current GameState (e.g., START, PLAYER1_WIN, PLAYER2_WIN).
     */
    GameState getGameState();

    /**
     * Returns the number of players in the game.
     *
     * @return the total number of players (typically 2 for Checkers).
     */
    int getNumPlayers();

    /**
     * Checks if the given player has selected a valid piece on the board.
     *
     * @param row    the row index of the selected piece.
     * @param col    the column index of the selected piece.
     * @param player the player attempting to select the piece.
     * @return true if the piece is valid for the player, false otherwise.
     */
    boolean playerSelectedPiece(int row, int col, Player player);

    /**
     * Handles a player's move action on the board.
     *
     * @param fromRow the starting row index of the piece.
     * @param fromCol the starting column index of the piece.
     * @param toRow   the destination row index of the piece.
     * @param toCol   the destination column index of the piece.
     * @param player  the player attempting the move.
     * @return true if the move is valid and successfully performed, false otherwise.
     */
    boolean playerMovedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player);

    /**
     * Handles a player's capture action, removing an opponent's piece.
     *
     * @param fromRow the starting row index of the capturing piece.
     * @param fromCol the starting column index of the capturing piece.
     * @param toRow   the destination row index of the capturing piece.
     * @param toCol   the destination column index of the capturing piece.
     * @param player  the player performing the capture.
     * @return true if the capture is valid and successfully performed, false otherwise.
     */
    boolean playerCapturedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player);

    /**
     * Promotes a piece to a king if it reaches the opposite end of the board.
     *
     * @param row    the row index of the piece to be promoted.
     * @param col    the column index of the piece to be promoted.
     * @param player the player whose piece is being promoted.
     */
    void promoteToKing(int row, int col, Player player);
}