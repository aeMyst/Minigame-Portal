package src.ca.ucalgary.seng300.gamelogic.cards;

import src.ca.ucalgary.seng300.graphics.Graphic;
import src.ca.ucalgary.seng300.gamelogic.cards.CardID;

public abstract class Card {
    private Graphic graphic;
    private CardID cardID;

    public Card(CardID cardID) {
        this.graphic = new Graphic();
        this.cardID = cardID;
    }

    public CardID getCardID() {
        return cardID;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    public String toString() {
        return "Card(" + cardID.getCardID() + ")";
    }
}
