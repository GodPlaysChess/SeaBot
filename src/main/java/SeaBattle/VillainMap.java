package SeaBattle;

public class VillainMap extends Map {

    public VillainMap(){
        super();
    }

    public void openField(String x, String y, String result){
        openField(Integer.parseInt(x), Integer.parseInt(y), result);
    }

    public void openField(int x, int y, String result){
        fields[x][y] = result.equals("missed") ? WATER : SHIP;                   //add destroyed later
    }

    public boolean finished(){
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
