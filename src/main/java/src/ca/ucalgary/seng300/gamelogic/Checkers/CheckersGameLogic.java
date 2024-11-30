package src.ca.ucalgary.seng300.gamelogic.Checkers;

import src.ca.ucalgary.seng300.leaderboard.data.Player;

public class CheckersGameLogic implements ICheckers {

    private int[][] board;
    private GameState gameState;
    private Player currentPlayer;
    private final Player player1;
    private final Player player2;;

    public CheckersGameLogic( Player player1, Player player2) {
        this.board = CheckersBoard.createBoard();
        this.gameState = GameState.START;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1; // Start with Player 1

    }


    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public boolean playerSelectedPiece(int row, int col, Player player) {
        if (!isWithinBounds(row, col)) return false;
        int piece = board[row][col];
        int requiredPiece = (player == player1) ? 1 : 2;
        int kingPiece = requiredPiece + 2;

        // Recognize both normal and king pieces
        return piece == requiredPiece || piece == kingPiece;
    }

    @Override
    public boolean playerMovedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (!isValidMove(fromRow, fromCol, toRow, toCol, player)) {
            return false;
        }
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = 0;

        if (checkKingPromotion(toRow, toCol, player)) {
            promoteToKing(toRow, toCol, player);
        }
        return true;
    }

    @Override
    public boolean playerCapturedPiece(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (!isValidCapture(fromRow, fromCol, toRow, toCol, player)) {
            return false;
        }

        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;
        board[midRow][midCol] = 0; // Remove captured piece
        board[toRow][toCol] = board[fromRow][fromCol]; // Move piece
        board[fromRow][fromCol] = 0;

        if (checkKingPromotion(toRow, toCol, player)) {
            promoteToKing(toRow, toCol, player);
        }
        return true;
    }

    @Override
    public void promoteToKing(int row, int col, Player player) {
        if (!isWithinBounds(row, col)) return;

        int piece = board[row][col];
        int requiredPiece = (player == player1) ? 1 : 2;
        if (piece == requiredPiece && checkKingPromotion(row, col, player)) {
            board[row][col] = piece + 2; // Promote to king
        }
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (!isWithinBounds(fromRow, fromCol) || !isWithinBounds(toRow, toCol)) return false;
        if (board[toRow][toCol] != 0) return false;

        int piece = board[fromRow][fromCol];
        int requiredPiece = (player == player1) ? 1 : 2;
        int kingPiece = requiredPiece + 2;

        if (piece != requiredPiece && piece != kingPiece) {
            return false;
        }

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // Ensure the move is diagonal and one step
        if (Math.abs(rowDiff) != 1 || Math.abs(colDiff) != 1) {
            return false;
        }

        // For normal pieces, ensure movement is forward
        if (piece == 1 && rowDiff != 1) return false; // Player 1 moves down
        if (piece == 2 && rowDiff != -1) return false; // Player 2 moves up

        // Kings can move in any direction
        return true;
    }

    public boolean isValidCapture(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (!isWithinBounds(fromRow, fromCol) || !isWithinBounds(toRow, toCol)) return false;
        if (board[toRow][toCol] != 0) return false;

        int piece = board[fromRow][fromCol];
        int opponentPiece = (player == player1) ? 2 : 1;
        int opponentKingPiece = opponentPiece + 2;

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        // Ensure the move is diagonal and two steps
        if (Math.abs(rowDiff) != 2 || Math.abs(colDiff) != 2) {
            return false;
        }

        // For normal pieces, ensure movement is forward
        if (piece == 1 && rowDiff != 2) return false; // Player 1 captures down
        if (piece == 2 && rowDiff != -2) return false; // Player 2 captures up

        // Kings can capture in any direction

        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;

        int midPiece = board[midRow][midCol];

        // Check if the middle piece is an opponent's piece
        if (midPiece != opponentPiece && midPiece != opponentKingPiece) {
            return false;
        }

        return true;
    }

    public boolean hasValidCapture(int row, int col, Player player) {
        int piece = board[row][col];
        if (piece == 0) return false;
        int requiredPiece = (player == player1) ? 1 : 2;
        int kingPiece = requiredPiece + 2;

        if (piece != requiredPiece && piece != kingPiece) {
            return false;
        }

        int[] rowDirs;
        if (piece == requiredPiece) {
            // Normal pieces
            rowDirs = (player == player1) ? new int[]{2} : new int[]{-2};
        } else {
            // Kings
            rowDirs = new int[]{-2, 2};
        }
        int[] colDirs = {-2, 2};

        for (int rd : rowDirs) {
            for (int cd : colDirs) {
                int toRow = row + rd;
                int toCol = col + cd;
                if (isValidCapture(row, col, toRow, toCol, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAnyValidCaptures(Player player) {
        int requiredPiece = (player == player1) ? 1 : 2;
        int kingPiece = requiredPiece + 2;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int piece = board[row][col];
                if (piece == requiredPiece || piece == kingPiece) {
                    if (hasValidCapture(row, col, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean checkKingPromotion(int row, int col, Player player) {
        if (player == player1 && row == 7) {
            return true;
        } else if (player == player2 && row == 0) {
            return true;
        }
        return false;
    }

    public void forfeitGame() {
        if (getCurrentPlayer() == player1) {
            gameState = GameState.PLAYER2_WIN;
        } else {
            gameState = GameState.PLAYER1_WIN;
        }
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("Switched to " + currentPlayer.getPlayerID() + "'s turn.");
    }
}
