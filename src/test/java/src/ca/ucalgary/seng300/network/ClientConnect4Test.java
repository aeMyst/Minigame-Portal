package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import org.junit.Test;
import src.ca.ucalgary.seng300.gamelogic.Connect4.Connect4Logic;
import src.ca.ucalgary.seng300.gamelogic.Connect4.TurnManager;
import src.ca.ucalgary.seng300.gamelogic.Connect4.UserPiece;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import java.util.Arrays;

public class ClientConnect4Test {
    @Test
    public void testNewMoveC4() {
        Connect4Logic dummyLogicManager = new Connect4Logic();
        for (int i = 0; i < dummyLogicManager.getBoard().length; i++) {
            // intellij integrated suggestions used here
            Arrays.fill(dummyLogicManager.getBoard()[i], 1);
        }
        Player player1 = new Player("CONNECT4", "lefaa1", 1500, 0, 0, 0);
        Player player2 = new Player("CONNECT4", "test", 1500, 0, 0, 0);
        UserPiece piece1 = new UserPiece(player1, 1);
        UserPiece piece2 = new UserPiece(player2, 2);
        TurnManager dummyTurnManager = new TurnManager(piece1, piece2);
        String status = "ONGOING";
        Runnable callback = () -> System.out.println("Callback executed");

        ClientConnect4 connect4 = new ClientConnect4();

        connect4.sendC4MoveToServer(dummyLogicManager, dummyTurnManager, status, callback);

    }
}


