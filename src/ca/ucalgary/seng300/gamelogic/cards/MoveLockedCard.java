package src.ca.ucalgary.seng300.gamelogic.cards;

public class MoveLockedCard extends Card {
    private Location location;
    private Rotation rotation;

    public MoveLockedCard(CardID cardID) {
        super(cardID);
    }

    public Location getLocation() {
        return location;
    }
    public Rotation getRotation() {
        return rotation;
    }


    public

    @Override
    String toString() {
        return "MoveLockedCard(" + super.getCardID() + ", " + location + ")";
    }
}
