package src.ca.ucalgary.seng300.gamelogic.tictactoe;

public interface IPlayer {
    char getSymbol();

    int[] getMove(Board board);


}

