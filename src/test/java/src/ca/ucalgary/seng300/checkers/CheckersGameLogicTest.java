package src.ca.ucalgary.seng300.checkers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import src.ca.ucalgary.seng300.leaderboard.data.Player;
import src.ca.ucalgary.seng300.gamelogic.Checkers.CheckersGameLogic;
import src.ca.ucalgary.seng300.gamelogic.Checkers.GameState;

public class CheckersGameLogicTest {

    private CheckersGameLogic game;
    private Player player1;
    private Player player2;

    @Before
    public void setup() {
        // Initialize the players with dummy data for testing, using the Player constructor
        player1 = new Player("Checkers", "p1", 1500, 0, 0, 0);
        player2 = new Player("Checkers", "p2", 1500, 0, 0, 0);
        game = new CheckersGameLogic(player1, player2);
    }

    @Test
    public void testInitialGameState() {

        // test gameState
        assertEquals(GameState.START, game.getGameState());

        // test getNumPlayers
        assertEquals(2, game.getNumPlayers());

        // test getCurrentPlayer
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    public void testGetBoard() {
        // initialize a board first
        int[][] testBoard = new int[8][8];
        int size = testBoard.length;
        // iterate through each row and column of the board.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // check if the square is a light square ((row + col) % 2 != 0).
                // white pieces (player 1) are on rows 0 to 2 on light squares
                if (row < 3 && (row + col) % 2 != 0) {
                    testBoard[row][col] = 1;// White piece (player 1).
                }
                // Black pieces (player 2) are on rows 5 to 7 on light squares
                else if (row > 4 && (row + col) % 2 != 0) {
                    testBoard[row][col] = 2;// Black piece (player 2).
                }
                // Empty squares are set to 0
                else {
                    testBoard[row][col] = 0;//if neither a white nor black piece is set, set to 0
                }
            }
        }
        // board should be initialized correctly
        assertEquals(testBoard, game.getBoard());
    }


    @Test
    public void testPlayerSelectedPiece() {

        // choose piece in row out of bound
        assertFalse(game.playerSelectedPiece(-1, 1, player1)); // Below 0
        assertFalse(game.playerSelectedPiece(8, 1, player1));  // Above 7

        // choose piece in column out of bound
        assertFalse(game.playerSelectedPiece(2, -1, player1)); // Below 0
        assertFalse(game.playerSelectedPiece(2, 8, player1));  // Above 7

        setup();
        // select valid piece for Player 1
        assertTrue(game.playerSelectedPiece(0, 1, player1)); // Row 1, Col 1
        // select valid piece for Player 2
        assertTrue(game.playerSelectedPiece(7, 0, player2));

        game.getBoard()[3][3] = 3; // initialize piece to be king (player one) at 3, 3
        assertTrue(game.playerSelectedPiece(3, 3, player1));

        game.getBoard()[7][0] = 4; // initialize piece to be king (player two) at 7, 0
        assertTrue(game.playerSelectedPiece(7, 0, player2));

        // gpt generated - asked it to figure out the missing test case
        // Initialize a square with a piece that doesn't belong to the player
        game.getBoard()[4][4] = 2; // Player 2's piece
        assertFalse(game.playerSelectedPiece(4, 4, player1)); // Player 1 tries to select it

    }

    @Test
    public void testPlayerMovedPiece() {
        // Test moving a piece
        assertTrue(game.playerMovedPiece(2, 1, 3, 0, player1)); // valid move for Player 1
        assertFalse(game.playerMovedPiece(5, 0, 3, 1, player1)); // Invalid move (should be forward)

        // check king promotion
        game.getBoard()[0][3] = 0;
        game.getBoard()[1][4] = 2;
        assertTrue(game.playerMovedPiece(1,4,0,3, player2));

    }

    @Test
    public void testPlayerCapturedPiece() {
        // test player capture and immediate king promotion
        game.getBoard()[2][5] = 2;
        game.getBoard()[0][3] = 0;
        assertTrue(game.playerCapturedPiece(2,5,0,3,player2));

        setup();
        assertFalse(game.playerCapturedPiece(2,1,3,2,player1));
    }

    @Test
    public void testKingPromotion() {
        // test king proper king promotion
        game.getBoard()[0][1] = 2;
        game.promoteToKing(0,1, player2);
        assertEquals(4, game.getBoard()[0][1]);

        // cannot promote a piece out of bounds
        game.promoteToKing(8,3, player1);

        game.getBoard()[4][3] = 2;
        game.promoteToKing(4, 3, player2);
        assertEquals(2, game.getBoard()[4][3]);
    }

    @Test
    public void testIsValidMove() {
        // test within boundary
        game.isValidMove(8,3,10,0, player1);

        game.getBoard()[5][2] = 4;
        assertTrue(game.isValidMove(5,2,4,1, player2));

        //check to see if move is diagonal, if not return false
        setup();
        assertFalse(game.isValidMove(5,2,4,2, player2));
        assertFalse(game.isValidMove(5,2,5,1, player2));

        //check to see if move is forward
        game.getBoard()[6][3] = 0;
        assertFalse(game.isValidMove(5,2,6,3, player2));
        game.getBoard()[1][2] = 0;
        assertFalse(game.isValidMove(2,1,1,2, player1));
    }

    @Test
    public void testForfeitGame() {
        // Test forfeiting the game
        game.forfeitGame();
        assertEquals(GameState.PLAYER2_WIN, game.getGameState()); // Player 1 forfeits, Player 2 wins

        setup();
        game.switchPlayer();
        game.forfeitGame();
        assertEquals(GameState.PLAYER1_WIN, game.getGameState());
    }

    @Test
    public void testCheckKingPromotion() {
        game.getBoard()[7][1] = 1;
        assertTrue(game.checkKingPromotion(7, 1, player1));
    }

    @Test
    public void testHasAnyValidCapture() {
        //player two valid capture
        game.getBoard()[4][3] = 1;
        assertTrue(game.hasAnyValidCaptures(player2));

        // player two king piece valid capture
        game.getBoard()[5][2] = 4;
        assertTrue(game.hasAnyValidCaptures(player2));

        // player 1 valid capture
        game.getBoard()[3][2] = 2;
        assertTrue(game.hasAnyValidCaptures(player1));

        // player 1 has no valid captures
        setup();
        assertFalse(game.hasAnyValidCaptures(player1));
    }

    @Test
    public void testHasValidCaptures() {
        // non existent piece
        assertFalse(game.hasValidCapture(4,3, player1));

        game.getBoard()[2][1] = 3;
        assertFalse(game.hasValidCapture(2,1, player2));

    }


    @Test
    public void testInvalidMoveOutOfBounds() {
        // Test invalid moves outside of the board
        assertFalse(game.playerMovedPiece(0, 0, -1, -1, player1)); // Move out of bounds
        assertFalse(game.playerMovedPiece(7, 7, 8, 8, player2)); // Move out of bounds
    }

    @Test
    public void testValidCaptureWithKing() {
        // Test a valid capture using a king piece
        game.playerMovedPiece(6, 1, 5, 2, player1); // Move Player 1's piece
        game.playerMovedPiece(1, 0, 2, 1, player2); // Move Player 2's piece
        game.playerMovedPiece(5, 2, 4, 3, player1); // Move Player 1's piece
        game.playerMovedPiece(2, 1, 3, 2, player2); // Move Player 2's piece
        game.playerMovedPiece(4, 3, 3, 4, player1); // Move Player 1's piece
        game.promoteToKing(3, 4, player1); // Promote to king
        assertTrue(game.playerCapturedPiece(6, 1, 4, 3, player1)); // Valid capture by king
    }

    @Test
    public void testIsValidCapture() {
        assertFalse(game.isValidCapture(8,1,1,1, player1));

        assertFalse(game.isValidCapture(4,2,6,2, player2));

        game.getBoard()[3][2] = 2;
        game.getBoard()[5][0] = 0;
        assertFalse(game.isValidCapture(3,2,5,0, player2));

        setup();
        game.getBoard()[4][2] = 1;
        assertFalse(game.isValidCapture(4,2,2,0, player1));

        setup();
        game.getBoard()[4][1] = 3;
        assertTrue(game.isValidCapture(5,2,3,0, player2));


        //assertFalse(game.isValidCapture(1,2,0,2, player1));

    }

    @Test
    public void testSwitchPlayer() {
        // Test switching players after a move
        assertEquals(player1, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(player2, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(player1, game.getCurrentPlayer());
    }

}

