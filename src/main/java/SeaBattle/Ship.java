package SeaBattle;

public class Ship {

    private ShipPart[] parts;
    //private boolean destroyed = false;

    public Ship(int size, int xHead, int yHead, boolean horizontal) {
        parts = new ShipPart[size];
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                parts[i] = new ShipPart(xHead + i, yHead);
            } else {
                parts[i] = new ShipPart(xHead, yHead + i);
            }
        }
    }

    public String checkHit(int x, int y) {
        for (ShipPart SP : parts) {
            if (SP.getX() == x && SP.getY() == y) {
                SP.toHit();
                if (this.isDestroyed()) {
                    return "destroyed";
                } else return "hit";
            }
        }
        return "missed";
    }

    public boolean isDestroyed() {
        boolean destroyed = true;

        for (ShipPart S : parts) {
            if (!S.isHit()) {
                destroyed = false;
            }
        }
        return destroyed;
    }

    public ShipPart[] getParts(){
        return parts;
    }
}
