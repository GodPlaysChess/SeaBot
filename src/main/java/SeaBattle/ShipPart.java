package SeaBattle;

public class ShipPart {
    private int x;
    private int y;
    private boolean hit = false;
    private boolean destroyed = false;


    public ShipPart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ShipPart(int x, int y, boolean hit) {
        this.x = x;
        this.y = y;
        this.hit = hit;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed() {
        destroyed = true;
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
