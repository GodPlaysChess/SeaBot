package SeaBattle;

public class GameBot {

  //  private HeroMap Hero;
    public GameBot() {

    }

    public int[] fire() {
        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);

        return new int[]{x, y};
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

    public int[] findVictim(){

    }

}
