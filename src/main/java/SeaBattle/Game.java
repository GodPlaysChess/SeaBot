package SeaBattle;

import java.util.StringTokenizer;

public class Game {
    public GameBot player;

    private HeroMap heroMap;
   // private VillainMap Villain;
    private boolean myTurn = false;
    private GameState gameState;

    public enum GameState {
        LOST, WIN, UNDEFINED
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void changeTurn() {
        myTurn = !myTurn;
    }

    public Game() {
        player = new GameBot();
        heroMap = new HeroMap(player.initPositions());
//        Villain = new VillainMap();
        gameState = GameState.UNDEFINED;
    }


    //fire to the server, get the response
    public String botFireToServer() {
        return fireToServer(player.fire());
    }

    public String fireToServer(int x, int y) {
        return "shoot " + x + " " + y;
    }

    public String fireToServer(int[] xy) {
        return "shoot " + xy[0] + " " + xy[1];
    }

    //Updating my vision of the enemy map
    public void handleFireResponse(String response) {
        StringTokenizer ST = new StringTokenizer(response);
        if (ST.nextToken().equals("result")) {
            player.getVillain().openField(ST.nextToken(), ST.nextToken(), ST.nextToken());
        }
        if (response.endsWith("I LOST")) {     //<---COULD NOT PARSE
            gameState = GameState.WIN;
        }
    }

    //getting the result of the villain fire
    public String handleVillainFire(String villainFire) {
        //  String result = "wrong input";
        StringTokenizer ST = new StringTokenizer(villainFire);

        if (ST.nextToken().equals("shoot")) {
            String x = ST.nextToken();
            String y = ST.nextToken();

            return "result " + x + " " + y + " " + resultOfVillainFire(x, y);
        } else if (villainFire.endsWith("I LOST")) {
            gameState = GameState.WIN;
            return "I WON";
        }
        return "can not process " + villainFire;
    }

    private String resultOfVillainFire(String x, String y) {
        if (heroMap.checkFieldString(x, y).equals("destroyed")) {
            boolean everythingIsDestroyed = true;
            for (Ship S : heroMap.getShips()) {
                if (!S.isDestroyed())
                    everythingIsDestroyed = false;
            }
            if (everythingIsDestroyed) {
                gameState = GameState.LOST;
                return "destroyed game over, I LOST";
            } else return "destroyed";
        } else return heroMap.checkFieldString(x, y);
    }



    public HeroMap getHeroMap() {
        return heroMap;
    }

    public GameState getGameState() {
        return gameState;
    }


}
