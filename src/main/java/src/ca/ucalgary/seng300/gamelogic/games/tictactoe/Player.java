package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

import java.util.Scanner;

public interface Player {
    char getSymbol();

    int[] getMove(Scanner scanner, Board board);



}

