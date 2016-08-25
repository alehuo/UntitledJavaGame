package ahuotala.net;

import ahuotala.game.Tickable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author Aleksi Huotala
 */
public class Client extends Thread implements Tickable {

    private DatagramSocket socket;
    private int port;

    public Client(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        System.out.println("Starting game server...");
        while (true) {
            byte[] dataArray = new byte[1024];
            DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String message = new String(packet.getData());

            System.out.println("SERVER [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] " + message);

        }
    }

    public void connect() {

    }

    public void disconnect() {

    }

    @Override
    public void tick() {

    }

    public void send() {

    }

}
