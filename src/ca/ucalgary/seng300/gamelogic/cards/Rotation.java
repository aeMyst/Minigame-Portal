package src.ca.ucalgary.seng300.gamelogic.cards;

public class Rotation {
    private int rotation;

    public Rotation(int rotation) {
        this.rotation = rotation;
    }

    // as fractions of 2^32
    public int getRotationAsInt() {
        return rotation;
    }
    // as fractions of 2^32
    public int setRotationAsInt(int rotation) {
        return rotation;
    }
}
