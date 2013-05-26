package SeaBattle;

public final class HeroMap extends Map {

    public HeroMap(Ship[] shipsPos) {
        ships = shipsPos;            /*placing the ships*/

        //initializing the map
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fields[i][j] = WATER;
            }
        }
        for (Ship S : ships){
            for (ShipPart SP : S.getParts()){
                fields[SP.getX()][SP.getY()] = SHIP;
            }
        }
    }

    public Ship[] getShips(){
        return ships;
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
