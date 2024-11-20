package src.ca.ucalgary.seng300.gamelogic.cards;

import src.ca.ucalgary.seng300.gamelogic.cards.CardID;

public class MoveUnlockedCard extends Card {
    private Location location;
    private Rotation rotation;

    public MoveUnlockedCard(CardID cardID) {
        super(cardID);
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Rotation getRotation() {
        return rotation;
    }
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "MoveUnlockedCard(" + super.getCardID() + ", " + location + ")";
    }
}
