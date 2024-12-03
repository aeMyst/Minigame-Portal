package src.ca.ucalgary.seng300.gamelogic.Checkers;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

public interface ICheckers {

    GameState getGameState();

    int getNumPlayers();

    boolean playerSelectedPiece(int row, int col, Player player);

    boolean playerMovedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player);

    boolean playerCapturedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player);

    void promoteToKing(int row, int col, Player player);
}