package src.ca.ucalgary.seng300.gamelogic.games;

import src.ca.ucalgary.seng300.gamelogic.GameState;
import src.ca.ucalgary.seng300.gamelogic.IGameLogic;
import src.ca.ucalgary.seng300.gamelogic.cards.*;
import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;
import src.ca.ucalgary.seng300.graphics.Graphic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

public class ExampleGame implements IGameLogic {

    /**
     * @param numPlayers
     * @param seed
     */
    ArrayList<CardID> deck;
    ArrayList<CardID> hand;

    public ExampleGame(int seed) {
        this.graphic = new Graphic();
        this.numPlayers = 1;
        this.initializeGameState(seed);
    }

    /**
     * @param seed
     * @return new GameState Object
     */
    private GameState initializeGameState(int seed) {
        int numCards = 4;
        HashMap<CardID, Card> cards_map = new HashMap<>();
        for (int i = 0; i < numCards; i++) {
            CardID cardID = new CardID(i);
            Card card = new HiddenCard(cardID);
            cards_map.put(cardID, card);
        }
        Cards cards = new Cards(cards_map);
        GameState gameState = new GameState(cards);

        this.deck = new ArrayList<CardID>();
        this.hand = new ArrayList<CardID>();
        for (int i = 0; i < numCards; i++) {
            this.deck.add(new CardID(i));
        }
        Collections.shuffle(this.deck);
        return gameState;
    }

    public boolean drawCard() {
        if (!this.deck.isEmpty()) {
            this.hand.add(this.deck.removeFirst());
            return true;
        }
        return false;
    }


    // Fields and Methods for Interface
    Graphic graphic;
    GameState gameState;
    int numPlayers;

    /**
     * @return
     */
    @Override
    public Graphic getGraphic() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public GameState getGameState() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int getNumPlayers() {
        return 0;
    }

    /**
     * @param location
     * @param playerID
     * @return
     */
    @Override
    public Optional<CardID> playerGrabbedCard(Location location, PlayerID playerID) {
        return Optional.empty();
    }

    /**
     * @param location
     * @param cardID
     * @param playerID
     */
    @Override
    public void playerMovedCard(Location location, CardID cardID, PlayerID playerID) {

    }

    /**
     * @param location
     * @param cardID
     * @param playerID
     */
    @Override
    public void playerReleasedCard(Location location, CardID cardID, PlayerID playerID) {

    }

    public String toString() {
        return "ExampleGame(Deck(" + this.deck + "), Hand(" + this.hand + "))";
    }
}
