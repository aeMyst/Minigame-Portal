package src.ca.ucalgary.seng300.gamelogic.cards;

import src.ca.ucalgary.seng300.gamelogic.Location;

public class MoveUnlockedCard extends Card {
    private Location location;

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
