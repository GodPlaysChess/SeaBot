package Client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientConnection {

    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut = null;

    ClientConnection(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendData(String msg) throws IOException {
        socketOut.println(msg);
    }

    public String getData() {
        try {
            return socketIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "something went wrong";
    }

    public void closeConncection() {
        try {
            socketOut.close();
            socketIn.close();
            clientSocket.close();
            System.out.println("Connection is closed");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
