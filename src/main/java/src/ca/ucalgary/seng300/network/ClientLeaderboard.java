package src.ca.ucalgary.seng300.network;

import javafx.application.Platform;
import src.ca.ucalgary.seng300.leaderboard.interfaces.ILeaderboard;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;

import java.util.Random;

/**
 * The Leaderboard component of the Client class
 */
public class ClientLeaderboard {
    ILeaderboard leaderboard = new Leaderboard();

    public ClientLeaderboard(ILeaderboard lb) {
        leaderboard = lb;
    }

    public String[][] getConnect4Leaderboard() {
        return leaderboard.getC4Leaderboard();
    }
    public String[][] getC4Leaderboard(Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000); // simulate server delay
        String[][] connect4LB = leaderboard.getC4Leaderboard();

        new Thread(() -> {
            try {
                Thread.sleep(time);
                System.out.println("Server Communication for Leaderboard...");
                System.out.println("Getting Leaderboard for Connect 4..." + "\n");

                int count = 1;

                System.out.println("Sorted Leaderboard for Connect 4:\n");
                System.out.printf("%-10s %-16s %-10s %-10s%n", "Rank", "Player ID", "Rating", "Wins");
                for (String[] entry : connect4LB) {
                    System.out.printf("%-10d %-16s %-10s %-10s%n", count, entry[0], entry[1], entry[2]);

                    count++;
                }

                // update the GUI
                Platform.runLater(callback);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return connect4LB;
    }

    public String[][] getCheckersLeaderboard(Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(500) + 500; // Simulate server delay between 500ms and 1000ms

        String[][] checkersLeaderboard = leaderboard.getCheckersLeaderboard();

        new Thread(() -> {
            try {
                Thread.sleep(time);
                System.out.println("Server Communication for Checkers...");
                System.out.println("Leaderboard for Checkers being updated...\n");

                int count = 1;
                System.out.println("Sorted Leaderboard for Checkers:\n");
                System.out.printf("%-10s %-16s %-10s %-10s%n", "Rank", "Player ID", "Rating", "Wins");
                for (String[] entry : checkersLeaderboard) {
                    System.out.printf("%-10d %-16s %-10s %-10s%n", count, entry[0], entry[1], entry[2]);
                    count++;
                }

                // Update the GUI on the JavaFX thread
                Platform.runLater(callback);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return checkersLeaderboard;
    }


    public String[][] getTTTLeaderboard(Runnable callback) {
        Random rand = new Random();
        int time = rand.nextInt(1000); // simulate server delay
        String[][] tttLeaderboard = leaderboard.getTicTacToeLeaderboard();
        new Thread(() -> {
            try {
                Thread.sleep(time);
                System.out.println("Server Communication for Leaderboard...");
                System.out.println("Leaderboard for Tic-Tac-Toe being updated..." + "\n");

                int count = 1;

                System.out.println("Sorted Leaderboard for Tic-Tac-Toe:\n");
                System.out.printf("%-10s %-16s %-10s %-10s%n", "Rank", "Player ID", "Rating", "Wins");
                for (String[] entry : tttLeaderboard) {
                    System.out.printf("%-10d %-16s %-10s %-10s%n", count, entry[0], entry[1], entry[2]);
                    count++;
                }

                // update the GUI
                Platform.runLater(callback);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return tttLeaderboard;
    }


}
