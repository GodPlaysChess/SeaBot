package SeaBattle;

import java.util.StringTokenizer;

public class Game {
    private HeroMap Hero;
    private VillainMap Villain;
    private GameBot Player;
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
        Player = new GameBot();
        Hero = new HeroMap(Player.initPositions());
        Villain = new VillainMap();
        gameState = GameState.UNDEFINED;
    }


    //fire to the server, get the response
    public String botFireToServer() {
        return fireToServer(Player.fire());
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
        if (response.endsWith("I LOST")) {     //<---COULD NOT PARSE
            gameState = GameState.WIN;
            return;
        } else if (ST.nextToken().equals("result")) {
            Villain.openField(ST.nextToken(), ST.nextToken(), ST.nextToken());
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
            return "I WIN";
        }
        return "can not process " + villainFire;
    }

    private String resultOfVillainFire(String x, String y) {
        if (Hero.checkFieldString(x, y).equals("destroyed")) {
            boolean everythingIsDestroyed = true;
            for (Ship S : Hero.getShips()) {
                if (!S.isDestroyed())
                    everythingIsDestroyed = false;
            }
            if (everythingIsDestroyed) {
                gameState = GameState.LOST;
                return " game over, I LOST";
            } else return "destroyed";
        } else return Hero.checkFieldString(x, y);
    }

    public VillainMap getVillain() {
        return Villain;
    }

    public HeroMap getHero() {
        return Hero;
    }

    public GameState getGameState() {
        return gameState;
    }


}
