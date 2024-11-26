package src.ca.ucalgary.seng300.gamelogic.games.Checkers;

public class CheckersGameLogic implements ICheckers {

    private int[][] board;
    private GameState gameState;
    private PlayerID currentPlayer;
    private final Graphic graphic;

    public CheckersGameLogic(Graphic graphic) {
        this.board = CheckersBoard.createBoard();
        this.graphic = graphic;
        this.gameState = GameState.START;
        this.currentPlayer = PlayerID.PLAYER1;
    }

    @Override
    public Graphic getGraphic() {
        return graphic;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    public PlayerID getCurrentPlayer() {
        return currentPlayer;
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public boolean playerSelectedPiece(int row, int col, PlayerID playerID) {
        if (!isWithinBounds(row, col)) return false;
        int piece = board[row][col];
        return (playerID == PlayerID.PLAYER1 && piece == 1) || (playerID == PlayerID.PLAYER2 && piece == 2);
    }

    @Override
    public boolean playerMovedPiece(int fromRow, int fromCol, int toRow, int toCol, PlayerID playerID) {
        if (!isValidMove(fromRow, fromCol, toRow, toCol, playerID)) {
            return false;
        }
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = 0;
        graphic.update(board);
        return true;
    }

    @Override
    public boolean playerCapturedPiece(int fromRow, int fromCol, int toRow, int toCol, PlayerID playerID) {
        if (!isValidCapture(fromRow, fromCol, toRow, toCol, playerID)) {
            return false;
        }

        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;
        board[midRow][midCol] = 0;
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = 0;

        if (checkKingPromotion(toRow, toCol, playerID)) {
            promoteToKing(toRow, toCol);
        }

        graphic.update(board);
        return true; // Capture was successful
    }

    @Override
    public void promoteToKing(int row, int col, PlayerID playerID) {
        if (!isWithinBounds(row, col)) return;

        int piece = board[row][col];
        if ((playerID == PlayerID.PLAYER1 && row == 7) || (playerID == PlayerID.PLAYER2 && row == 0)) {
            board[row][col] = piece + 2;
            graphic.update(board);
        }
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, PlayerID playerID) {
        if (!isWithinBounds(fromRow, fromCol) || !isWithinBounds(toRow, toCol)) return false;
        if (board[toRow][toCol] != 0) return false;

        int piece = board[fromRow][fromCol];
        int requiredPiece = (playerID == PlayerID.PLAYER1) ? 1 : 2;

        if (piece != requiredPiece && piece != requiredPiece + 2) {
            return false;
        }

        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        return rowDiff == 1 && colDiff == 1;
    }

    public boolean isValidCapture(int fromRow, int fromCol, int toRow, int toCol, PlayerID playerID) {
        if (!isWithinBounds(fromRow, fromCol) || !isWithinBounds(toRow, toCol)) return false;
        if (board[toRow][toCol] != 0) return false;

        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;
        int opponentPiece = (playerID == PlayerID.PLAYER1) ? 2 : 1;

        return board[midRow][midCol] == opponentPiece && Math.abs(toRow - fromRow) == 2;
    }

    boolean checkKingPromotion(int row, int col, PlayerID playerID) {
        return (playerID == PlayerID.PLAYER1 && row == 7) || (playerID == PlayerID.PLAYER2 && row == 0);
    }

    private void promoteToKing(int row, int col) {
        board[row][col] += 2; // Promote piece to king
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Remove switchPlayer from CheckersGameLogic
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PlayerID.PLAYER1) ? PlayerID.PLAYER2 : PlayerID.PLAYER1;
        System.out.println("Switched to " + currentPlayer + "'s turn.");
    }
}