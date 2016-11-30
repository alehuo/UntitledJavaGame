package ahuotala.net;

import ahuotala.entities.Player;
import ahuotala.game.Tickable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;
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
    private String uuid;
    private PlayerList playerList;

    public Client(Player player, String host, int port) {
        try {
            this.player = player;
            this.socket = new DatagramSocket();
            this.host = InetAddress.getByName(host);
            this.port = port;
            playerList = new PlayerList();
            uuid = UUID.randomUUID().toString();
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
//        System.out.println("Starting game client...");
        while (true) {
            byte[] dataArray = new byte[4096];
            DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String message = new String(packet.getData());
            if (message.trim().equalsIgnoreCase(ServerStatus.PLAYER_CONNECTED.toString())) {
                connected = true;
            } else {
                ByteArrayInputStream baos = new ByteArrayInputStream(dataArray);
                try {
                    ObjectInputStream oos = new ObjectInputStream(baos);
                    playerList = (PlayerList) oos.readObject();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            System.out.println("SERVER [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] " + message.trim());

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
            //CLIENT_POSUPD;UUID;X;Y;DIRECTION
            send(ClientStatus.CLIENT_POSUPD.toString() + ";" + uuid + ";" + player.getX() + ";" + player.getY() + ";" + player.getDirection());
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

}
