package src.ca.ucalgary.seng300.gamelogic;

import src.ca.ucalgary.seng300.gamelogic.cards.Cards;

public class GameState {
    private Cards cards;

    public GameState(Cards cards) {
        this.cards = cards;
    }

    public Cards getCards() {
        return cards;
    }
}
