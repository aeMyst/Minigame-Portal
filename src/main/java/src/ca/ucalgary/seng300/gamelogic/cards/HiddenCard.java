package src.ca.ucalgary.seng300.gamelogic.cards;


public class HiddenCard extends Card {
    public HiddenCard(CardID cardID) {
        super(cardID);
    }

    @Override
    public String toString() {
        return "HiddenCard(" + super.getCardID() + ")";
    }
}
