package SeaBattle;

import java.util.Random;

public class GameBot {
    public int hitsnumber = 0;
    private VillainMap villainMap;
    private int[][] probabilityMap = new int[10][10];
    private int remainedShipFields = 20;
    private static Random rnd = new Random();
    private Ship[] positions = new Ship[10];  //<----may be not need
    private HeroMap myMap = new HeroMap();

    public GameBot() {
        villainMap = new VillainMap();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                probabilityMap[i][j] = 1;
            }
        }
        /* initializing probability chessboard */
        for (int i = 0; i < 10; i += 2) {
            for (int j = 0; j < 10; j += 2) {
                probabilityMap[i][j] = 3;
            }
        }
        for (int i = 1; i < 10; i += 2) {
            for (int j = 1; j < 10; j += 2) {
                probabilityMap[i][j] = 3;
            }
        }
    }

    public int[] fire() {
        int maxProbability = 0;
        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (probabilityMap[j][i] != 10) {
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
            probabilityMap[x][y] = 10;
            updateDiagonalProbability(x, y);
            updateOrtogonalProbability(x, y);
        } else if (result.equals("destroyed")) {
            villainMap.fields[x][y] = Map.SHIP;
            probabilityMap[x][y] = 10;
            updateDiagonalProbability(x, y);
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
            destroyShip(x + 1, y);
            destroyShip(x - 1, y);
            destroyShip(x, y + 1);
            destroyShip(x, y - 1);
        }
    }

    private void updateDiagonalProbability(int x, int y) {
        setZeroProbability(x + 1, y + 1);
        setZeroProbability(x - 1, y + 1);
        setZeroProbability(x - 1, y - 1);
        setZeroProbability(x + 1, y - 1);
    }

    /* Only updating Unknown tiles, which has 0.2 probability value */
    private void updateOrtogonalProbability(int x, int y) {
        setHighProbability(x + 1, y);
        setHighProbability(x - 1, y);
        setHighProbability(x, y + 1);
        setHighProbability(x, y - 1);
    }

    private void setZeroProbability(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            return;
        } else {
            probabilityMap[x][y] = 0;
        }
    }

    private void setHighProbability(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            return;
        } else {
            if (probabilityMap[x][y] != 0 && probabilityMap[x][y] != 10) {
                probabilityMap[x][y] = 7;
            }
        }
    }

    public Ship[] initPositions() {
        boolean cluster1Orientation = rnd.nextBoolean();
        int cluster1X = (rnd.nextBoolean() ? 0 : 4);
        int cluster1Y = (rnd.nextBoolean() ? 0 : 5);

        if (!cluster1Orientation) {
            int temp = cluster1X;
            cluster1X = cluster1Y;
            cluster1Y = temp;
        }
        placeCluster1(cluster1X, cluster1Y, cluster1Orientation);
        placeCluster2(!cluster1Orientation);                   // 2 + 1 ships placed near walls
        placeSmallShip(8);                                  // last 2, 8 and 9 ship
        placeSmallShip(9);                                  // placed randomly

        return positions;
    }

    /* In a first cluster there are 4+3+2+2+1+1 size ships, grouped in
    * 4x5 rectangle. X and Y defines a top left corner of the
    * cluster. Boolean horizontal defines orientation (4x5 or 5x4) */
    private void placeCluster1(int x, int y, boolean horizontal) {
        if (horizontal) {
            positions[0] = new Ship(4, x + 2, y, horizontal);
            positions[1] = new Ship(3, x, y + 2, !horizontal);
            positions[2] = new Ship(2, x + 2, y + 2, horizontal);
            positions[3] = new Ship(2, x + 2, y + 4, horizontal);
            positions[4] = new Ship(3, x + 5, y + 2, !horizontal);
            positions[5] = new Ship(1, x, y, horizontal);
            System.out.println("ship # 5 X = " + positions[4].getParts()[0].getX());
            System.out.println("ship # 5 Y = " + positions[4].getParts()[0].getY());
        } else {
            positions[0] = new Ship(4, x, y, horizontal);
            positions[1] = new Ship(3, x + 2, y, !horizontal);
            positions[2] = new Ship(2, x + 2, y + 2, horizontal);
            positions[3] = new Ship(2, x + 4, y + 2, horizontal);
            positions[4] = new Ship(3, x, y + 5, !horizontal);
            positions[5] = new Ship(1, x + 4, y + 5, !horizontal);
            System.out.println("ship # 5 Y = " + positions[5].getParts()[0].getY());
            System.out.println("ship # 6 Y = " + positions[5].getParts()[0].getY());
        }
        for (int i = 0; i < 6; i++) {
            myMap.addShipUnsafe(positions[i]);
        }
    }

    private void placeCluster2(boolean horizontal) {
        boolean placed = false;
        //   int x = (rnd.nextBoolean() ? 0 : 9);
        //   int y = (rnd.nextBoolean() ? 0 : 9);
        int x = 9;
        int y = 9;
        while (!placed) {
            if (horizontal) {
                x = rnd.nextInt(700) % 7;
                if (myMap.couldPlaceShipHere(x, y) && myMap.couldPlaceShipHere(x + 1, y) &&
                        myMap.couldPlaceShipHere(x + 2, y) && myMap.couldPlaceShipHere(x + 3, y)) {
                    positions[6] = new Ship(2, x, y, horizontal);
                    positions[7] = new Ship(1, x + 3, y, horizontal);
                    placed = true;
                }
            } else if (!horizontal) {
                y = rnd.nextInt(700) % 7;
                if (myMap.couldPlaceShipHere(x, y) && myMap.couldPlaceShipHere(x, y + 1) &&
                        myMap.couldPlaceShipHere(x, y + 2) && myMap.couldPlaceShipHere(x, y + 3)) {
                    positions[6] = new Ship(2, x, y, horizontal);
                    positions[7] = new Ship(1, x, y + 3, horizontal);
                    placed = true;
                }
            }
        }
        myMap.addShipUnsafe(positions[6]);
        myMap.addShipUnsafe(positions[7]);
        System.out.println("ship # 6 X = " + positions[6].getParts()[0].getX());
        System.out.println("ship # 6 Y = " + positions[6].getParts()[0].getY());
        System.out.println("ship # 7 X = " + positions[7].getParts()[0].getX());
        System.out.println("ship # 7 Y = " + positions[7].getParts()[0].getY());
    }

    //WORK BAD. perhaps the problem is in COULD PLACE SHIP HERE METHOD (ships either
    //are too close or overlaps oer did not create, donotknow
    private void placeSmallShip(int shipNumber) {
        boolean placed = false;
        while (!placed) {
            int x = rnd.nextInt(500) % 10;
            int y = rnd.nextInt(500) % 10;
            if (myMap.couldPlaceShipHere(x, y)) {
                positions[shipNumber] = new Ship(1, x, y, true);
                myMap.addShipUnsafe(positions[shipNumber]);
                placed = true;
                System.out.println("ship # " + shipNumber + "X = " + x);
                System.out.println("ship # " + shipNumber + "Y = " + y);
            }
        }
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
                System.out.printf(" " + probabilityMap[j][i] + " ");
            }
        }
        System.out.println("");

    }

}
