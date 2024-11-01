package src.ca.ucalgary.seng300.gamelogic.games.tictactoe;

import src.ca.ucalgary.seng300.gamelogic.GameState;
import src.ca.ucalgary.seng300.gamelogic.IGameLogic;
import src.ca.ucalgary.seng300.gamelogic.cards.CardID;
import src.ca.ucalgary.seng300.gamelogic.cards.Location;
import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;
import src.ca.ucalgary.seng300.graphics.Graphic;

import java.util.ArrayList;
import java.util.Optional;

public class TictactoeGame implements IGameLogic {
    Board board;
    Graphic graphic;
    int numPlayers;
    ArrayList<Player> players;
    public TictactoeGame(){
        this.graphic = new Graphic();
        this.numPlayers = 2;
        this.initializeGameState();

    }
    private GameState_tictactoe initializeGameState(){
        Player player1 = new Player('X', board);
        Player player2 = new Player('O', board);
        players.add(0,player1);
        players.add(1,player2);
        GameState_tictactoe gameState = new GameState_tictactoe(players);
        return gameState;
    }
    @Override
    public Graphic getGraphic() {
        return null;
    }

    @Override
    public GameState getGameState() {
        return null;
    }

    @Override
    public int getNumPlayers() {
        return 0;
    }

    @Override
    public Optional<CardID> playerGrabbedCard(Location location, PlayerID playerID) {
        return Optional.empty();
    }

    @Override
    public void playerMovedCard(Location location, CardID cardID, PlayerID playerID) {

    }

    @Override
    public void playerReleasedCard(Location location, CardID cardID, PlayerID playerID) {

    }
}
