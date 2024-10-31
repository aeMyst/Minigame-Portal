package src.ca.ucalgary.seng300.gamelogic.cards;

public class CardID {
    private int CardID;

    public CardID(int CardID) {
        this.CardID = CardID;
    }

    public int getCardID() {
        return CardID;
    }

    public String toString() {
        return String.format("CardID(%d)", CardID);
    }
}
