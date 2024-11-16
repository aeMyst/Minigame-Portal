package src.ca.ucalgary.seng300.gamelogic.cards;

import src.ca.ucalgary.seng300.gamelogic.cards.CardID;

import java.util.HashMap;

public class Cards {
    HashMap<CardID, Card> cards;

    public Cards(HashMap<CardID, Card> cards) {
        this.cards = cards;
    }
}
