package network;

import org.junit.jupiter.api.Test;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.BoardManager;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.HumanPlayer;
import src.ca.ucalgary.seng300.gamelogic.tictactoe.PlayerManager;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.network.Client;

public class ClientTest {
//Testing connection with server for TTT
    private Player player1;
    private Player player2;
        @Test
        public void testNewMoveTTT() {
            // Setup actual BoardManager
            char[][] board = {
                    {'X', 'O', ' '},
                    {' ', 'X', 'O'},
                    {'O', ' ', 'X'}
            };
            BoardManager boardManager = new BoardManager(); // Assuming a constructor for initializing board

            // Setup actual PlayerManager
            HumanPlayer playerX = new HumanPlayer(player1,'X');
            HumanPlayer playerO = new HumanPlayer(player2,'O');
            PlayerManager playerManager = new PlayerManager(playerX, playerO); // Assuming a constructor for players
            if (playerManager.getCurrentPlayer() !=playerX) {
                playerManager.switchPlayer();
            }; // Set current player

            // Execute method
            Client server = new Client();
            server.newMoveTTT(boardManager, playerManager, "Game in Progress");

            // No assertions here since this method prints output. You can visually verify or capture and assert console logs if required.
        }

        @Test
        public void testSendMoveToServer() throws InterruptedException {
            // Setup actual BoardManager
            char[][] board = {
                    {'X', 'O', ' '},
                    {' ', 'X', 'O'},
                    {'O', ' ', 'X'}
            };
            BoardManager boardManager = new BoardManager();

            // Setup actual PlayerManager

            HumanPlayer playerX = new HumanPlayer(player1, 'X');
            HumanPlayer playerO= new HumanPlayer(player2,'O');
            PlayerManager playerManager = new PlayerManager(playerX, playerO);
            if (playerManager.getCurrentPlayer() !=playerX) {
                playerManager.switchPlayer();
            }

            // Setup callback
            Runnable callback = () -> System.out.println("Callback executed!");

            // Execute method
            Client server = new Client();
            server.sendMoveToServer(boardManager, playerManager, "Game in Progress", callback);

            // Wait for the thread to complete
            Thread.sleep(1500); // Adjust based on delay randomness

            // You can visually verify the console output or extend functionality for assertions if needed.
        }
    }
