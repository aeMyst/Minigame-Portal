package src.ca.ucalgary.seng300;

import src.ca.ucalgary.seng300.gamelogic.games.ExampleGame;

public class Main {
    public static void main(String[] args) {
        int seed = 42;
        ExampleGame game = new ExampleGame(seed);
        System.out.println(game);
        game.drawCard();
        System.out.println(game);


    }
}
