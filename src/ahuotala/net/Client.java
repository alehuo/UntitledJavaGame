package ahuotala.net;

import ahuotala.entities.Player;
import ahuotala.game.Tickable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksi Huotala
 */
public class Client extends Thread implements Tickable {

    private DatagramSocket socket;
    private InetAddress host;
    private int port;
    private boolean connected = false;
    private Player player;

    public Client(Player player, String host, int port) {
        try {
            this.player = player;
            this.socket = new DatagramSocket();
            this.host = InetAddress.getByName(host);
            this.port = port;
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        System.out.println("Starting game client...");
        while (true) {
            byte[] dataArray = new byte[1024];
            DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String message = new String(packet.getData());
            if (message.trim().equalsIgnoreCase("CONNECTED")) {
                connected = true;
            }
            if (message.trim().equalsIgnoreCase("POSUPD")) {
            
            }
            System.out.println("SERVER [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] " + message);

        }
    }

    public void disconnect() {
        this.socket.close();
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public void tick() {
        if (connected) {
            send("PLAYERPOS;" + player.getX() + ";" + player.getY() + ";" + player.getDirection());
        }
    }

    /**
     * Send a packet
     *
     * @param dataArray
     * @param ip
     * @param port
     */
    public void send(String cmd) {
        byte[] dataArray = cmd.getBytes();
        DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length, host, port);
        try {
            socket.send(packet);
//            System.out.println("Sent packet to server");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
