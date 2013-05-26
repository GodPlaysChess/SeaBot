package SeaBattle;

import java.util.ArrayList;

public class GameBot {

    private VillainMap villainMap;
    private float probabilityToHit;
    private float[][] probabilityMap = new float[10][10];
    private int remainedShipFields = 20;
    private int remainedShips = 10;
    private ArrayList<ShipPart> hittedShips = new ArrayList<>(20);
    //  private HeroMap Hero;
    public int hitsnumber = 0;


    public GameBot() {
        villainMap = new VillainMap();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                probabilityMap[i][j] = (float) (remainedShipFields * 1.0 / 100);
            }
        }
    }

    public int[] fire() {
        float maxProbability = 0.1f;
        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (probabilityMap[j][i] != 1) {
                    if (probabilityMap[j][i] > maxProbability) {
                        maxProbability = probabilityMap[j][i];
                        x = j;
                        y = i;
                    }
                }
            }
        }
        System.out.println("hitsnumber: " + hitsnumber++);
        return new int[]{x, y};
    }

    public void openField(String x, String y, String result) {
        openField(Integer.parseInt(x), Integer.parseInt(y), result);
    }

    public void openField(int x, int y, String result) {
        if (result.equals("missed")) {
            villainMap.fields[x][y] = Map.WATER;
            probabilityMap[x][y] = 0;
        } else if (result.equals("hit")) {
            villainMap.fields[x][y] = Map.SHIP;
            probabilityMap[x][y] = 1;
        } else if (result.equals("destroyed")) {
            villainMap.fields[x][y] = Map.SHIP;
            destroyShip(x, y);
        }
    }

    private void destroyShip(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            return;
        }
        if (villainMap.fields[x][y] != Map.SHIP &&
                villainMap.fields[x][y] != Map.DESTROYED_SHIP) {
            probabilityMap[x][y] = 0;
        }
        if (villainMap.fields[x][y] == Map.SHIP) {
            villainMap.fields[x][y] = Map.DESTROYED_SHIP;
            updateProbabilityNearShip(x, y);
            destroyShip(x + 1, y);
            destroyShip(x - 1, y);
            destroyShip(x, y + 1);
            destroyShip(x, y - 1);
        }
    }

    /*ships can not be diagonal*/
    private void updateProbabilityNearShip(int x, int y) {
        probabilityMap[x][y] = 1;
        setZeroProbability(x + 1, y + 1);
        setZeroProbability(x - 1, y + 1);
        setZeroProbability(x - 1, y - 1);
        setZeroProbability(x + 1, y - 1);
    }

    private void setZeroProbability(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            return;
        } else {
            probabilityMap[x][y] = 0;
        }

    }


    public Ship[] initPositions() {
        Ship[] positions = new Ship[10];
        positions[0] = new Ship(4, 0, 0, true);
        positions[1] = new Ship(3, 5, 0, true);
        positions[2] = new Ship(3, 0, 2, true);
        positions[3] = new Ship(2, 4, 2, true);
        positions[4] = new Ship(2, 7, 2, true);
        positions[5] = new Ship(2, 0, 4, true);
        positions[6] = new Ship(1, 3, 4, true);
        positions[7] = new Ship(1, 5, 4, true);
        positions[8] = new Ship(1, 7, 4, true);
        positions[9] = new Ship(1, 9, 4, true);

        return positions;
    }

    public VillainMap getVillain() {
        return villainMap;
    }

    public void printProbabilityMap() {
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*      PROBABILITY   MAP       *");
        System.out.println("*                              *");
        System.out.println("********************************");

        for (int i = 0; i < 10; i++) {
            System.out.println("");
            for (int j = 0; j < 10; j++) {
                System.out.print((int) (probabilityMap[j][i] * 100) + " ");
            }
        }
        System.out.println("");

    }

}
