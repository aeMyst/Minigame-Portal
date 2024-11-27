package src.ca.ucalgary.seng300.gamelogic.Checkers;

public class GameStateHandler {

    private GameState gameState;

    public GameStateHandler() {
        this.gameState = GameState.START;
    }

    public GameState getState() {
        return gameState;
    }

    public void setState(GameState newState) {
        this.gameState = newState;
    }
}