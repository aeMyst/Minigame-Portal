package src.ca.ucalgary.seng300.gamelogic.games.Checkers;

public interface ICheckers {
    Graphic getGraphic();

    GameState getGameState();

    int getNumPlayers();

    boolean playerSelectedPiece(int row, int col, PlayerID playerID);

    boolean playerMovedPiece(int fromRow, int fromCol, int toRow, int toCol, PlayerID playerID);

    boolean playerCapturedPiece(int fromRow, int fromCol, int toRow, int toCol, PlayerID playerID);

    void promoteToKing(int row, int col, PlayerID playerID);
}