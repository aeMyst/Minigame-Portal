package src.ca.ucalgary.seng300.gamelogic.games.Connect4;

public class Board {
    protected int [][] board;

    public Board() {
        board = new int[6][7]; //creates 2D array with standard dimensions of connect 4 board
    }

    public int[][] getBoard() {
        return board;
    }

    public static void printBoard(int[][] board) {
        System.out.println("   1   2   3   4   5   6   7   "); //column labels, will figure out spacing later
        System.out.println("   --------------------------   ");

        for (int i = 0; i < board.length; i++) {
            System.out.println(" | ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.println(board[i][j] + " | ");
            }
        }
    }




}
