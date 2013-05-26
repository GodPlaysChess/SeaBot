package Client;

import SeaBattle.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Logic {

    public static void main(String[] args) {

        try (BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {
            ClientConnection client = new ClientConnection("localhost", 12325);
            //ClientConnection client = new ClientConnection("25.181.243.150", 12325);
            Game game = new Game();
            boolean stopRead = false;
            boolean startGame = false;
            String stringIn;

            while (!stopRead) {
                String tempBegLog = client.getData();

                System.out.println("Server: " + tempBegLog);

                if (tempBegLog.equals("game started, your turn")) {
                    startGame = true;
                    game.changeTurn();
                    System.out.println("jump in. Start game = " + startGame);
                }
                if (tempBegLog.equals("game started, wait for opponents turn")) {
                    startGame = true;
                    System.out.println("jump in. Start game = " + startGame);
                }

                while (startGame) {
                    System.out.print("The turn is ");
                    System.out.println(game.isMyTurn() ? "yours" : "opponents");
                    // stringIn = consoleIn.readLine();
                    // String tempInLog = client.getData();

                    if (game.isMyTurn()) {
                        client.sendData(game.botFireToServer());
                        game.handleFireResponse(client.getData());                      //need to encount only opponents data
                        game.changeTurn();
                        System.out.println("I have fired and finished the turn");
                        System.out.println();
                        if (game.getGameState() == Game.GameState.WIN){
                            System.out.println(" I WON ");
                        }
                        if (game.getGameState()!= Game.GameState.UNDEFINED) {
                            startGame = false;
                            game.player.getVillain().printString();
                            game.getHeroMap().printString();
                        }

                    } else if (!game.isMyTurn()) {
                        String sendout = game.handleVillainFire(client.getData());

                        System.out.print("Opponent responded: ");
                        System.out.println(sendout);
                        client.sendData(sendout);
                        game.changeTurn();
                        System.out.println();
                        if (game.getGameState() == Game.GameState.WIN){
                            System.out.println(" I WON ");
                        }
                        if (game.getGameState()!= Game.GameState.UNDEFINED) {
                            startGame = false;
                            game.player.getVillain().printString();
                            game.getHeroMap().printString();
                            game.player.printProbabilityMap();
                        }

                    }


                    /*if (stringIn.equals("quit")) {
                        stopRead = true;
                        startGame = false;
                    }*/
                }
            }
            client.closeConncection();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
