package SeaBattle;

public abstract class Map {

    public static final char SHIP = 'x';
    public static final char DESTROYED_SHIP = 'X';
    public static final int WATER = '-';
    public static final int UNKNOWN = 'O';
    protected char[][] fields = new char[10][10];
    protected Ship[] ships = new Ship[10];

    public Map() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                fields[i][j] = UNKNOWN;
        }
    }

    public String checkFieldString(String x, String y) {
        for (Ship S :ships){
            if (!S.checkHit(Integer.parseInt(x), Integer.parseInt(y)).equals("missed"))
                return S.checkHit(Integer.parseInt(x), Integer.parseInt(y));
        }
        return "missed";
    }

    public void printString() {
        System.out.println("********************************");
        System.out.println("*                              *");
        System.out.println("*           TARGET MAP         *");
        System.out.println("*                              *");
        System.out.println("********************************");

        for (int i = 0; i < 10; i++) {
            System.out.println("");
            for (int j = 0; j < 10; j++) {
                System.out.print(fields[i][j] + " ");
            }
        }
    }


}
