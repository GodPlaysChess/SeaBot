package SeaBattle;

public class VillainMap extends Map {

    public VillainMap() {
        super();
    }

    public void openField(String x, String y, String result) {
        openField(Integer.parseInt(x), Integer.parseInt(y), result);
    }

    public void openField(int x, int y, String result) {
        if (result.equals("missed")) {
            fields[x][y] = WATER;
        } else if (result.equals("hit")) {
            fields[x][y] = SHIP;
        } else if (result.equals("destroyed")) {
            fields[x][y] = SHIP;
            destroyShip(x, y);
        }
    }

    private void destroyShip(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9){
            return;
        }
        if (fields[x][y] == SHIP) {
            fields[x][y] = DESTROYED_SHIP;
            destroyShip(x+1, y);
            destroyShip(x-1, y);
            destroyShip(x, y+1);
            destroyShip(x, y-1);
        }
    }

    public boolean finished() {
        return false;
    }

    public void printString() {
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*        OPPONENTS   MAP       *");
        System.out.println("*                              *");
        System.out.println("********************************");

        for (int i = 0; i < 10; i++) {
            System.out.println("");
            for (int j = 0; j < 10; j++) {
                System.out.print(fields[j][i] + " ");
            }
        }
        System.out.println("");
    }

}
