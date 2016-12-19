package com.ahuotala.untitledjavagame.net;

import com.ahuotala.untitledjavagame.entities.Player;
import com.ahuotala.untitledjavagame.game.Game;
import com.ahuotala.untitledjavagame.game.Tickable;
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
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksi Huotala
 */
public final class Client extends Thread implements Tickable {

    private DatagramSocket socket;
    private final InetAddress host;
    private final int port;
    private boolean connected = false;
    private final Player player;
    private final String uuid;
    private PlayerList playerList;
    private static final Logger LOG = Logger.getLogger(Client.class.getName());
    private final Game game;

    public Client(Game game, Player player, String host, int port) throws SocketException, UnknownHostException {

        this.player = player;
        this.socket = new DatagramSocket();
        this.host = InetAddress.getByName(host);
        this.port = port;
        this.game = game;
        playerList = new PlayerList();
        uuid = UUID.randomUUID().toString();
        LOG.log(Level.INFO, "Connecting to " + host + ":" + port + " with player UUID " + uuid);
    }

    @Override
    public void run() {

        new Thread(new TcpClient(game, host, port)).start();

        while (TcpClient.connected) {
            byte[] dataArray = new byte[4096];
            DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(game, "Network error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                System.exit(0);
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
                    JOptionPane.showMessageDialog(game, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(0);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(game, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(0);
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
            JOptionPane.showMessageDialog(game, "Network error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public String getUuid() {
        return uuid;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

}
