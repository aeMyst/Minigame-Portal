package src.ca.ucalgary.seng300.gamelogic.games.Connect4;

public interface IUserPiece {
    int getPiece();

    int[] userMove(Connect4Board board);
}
