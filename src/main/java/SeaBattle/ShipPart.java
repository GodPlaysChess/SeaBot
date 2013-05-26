package SeaBattle;

public class ShipPart {
    private int x;
    private int y;
    boolean hit = false;

    public ShipPart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHit() {
        return hit;
    }

    public void toHit() {
        hit = true;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
