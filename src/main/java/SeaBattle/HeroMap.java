package SeaBattle;

public final class HeroMap extends Map {

    public HeroMap(){
        super();
    }

    public HeroMap(Ship[] shipsPos) {
        ships = shipsPos;            /*placing the ships*/

        //initializing the map
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fields[i][j] = WATER;
            }
        }
        for (Ship S : ships) {
            for (ShipPart SP : S.getParts()) {
                fields[SP.getX()][SP.getY()] = DESTROYED_SHIP;
            }
        }
    }

    public Ship[] getShips() {
        return ships;
    }

    public boolean couldPlaceShipHere(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (isShipThere(x + i, y + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printString() {
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*           MY     MAP         *");
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
