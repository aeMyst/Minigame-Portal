package src.ca.ucalgary.seng300.gamelogic.cards;

import src.ca.ucalgary.seng300.graphics.Graphic;

public abstract class Card {
    Graphic graphic;
    // as fractions of 65535
    int rotationAngle;
    CardID cardID;

    public Card(CardID cardID) {
        this.graphic = new Graphic();
        this.cardID = cardID;
    }
}
