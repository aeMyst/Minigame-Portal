package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

import java.util.Scanner;

public interface IPlayer {
    char getSymbol();

    int[] getMove(Board board);



}

