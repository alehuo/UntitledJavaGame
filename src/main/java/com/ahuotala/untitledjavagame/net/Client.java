package com.ahuotala.untitledjavagame.net;

import com.ahuotala.untitledjavagame.entities.Player;
import com.ahuotala.untitledjavagame.game.Game;
import com.ahuotala.untitledjavagame.game.Multiplayer;
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
public class Client extends Thread implements Tickable {
    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    private DatagramSocket socket;
    private final InetAddress host;
    private final int port;
    private boolean connected = false;
    private final Player player;
    private final String uuid;
    private PlayerList playerList;
    private final Game game;
    private final TcpClient tcpClient;

    /**
     *
     * @param m Multiplayer object
     * @throws SocketException
     * @throws UnknownHostException
     */
    public Client(Multiplayer m) throws SocketException, UnknownHostException {

        this.player = m.getPlayer();
        this.socket = new DatagramSocket();
        this.host = InetAddress.getByName(m.getHost().toString());
        this.port = m.getPort();
        this.game = m.getGame();
        this.tcpClient = m.getTcpClient();

        playerList = new PlayerList();
        uuid = UUID.randomUUID().toString();
        LOG.log(Level.INFO, "Connecting to {0}:{1} with player UUID {2}", new Object[]{host, port, uuid});
    }

    @Override
    public void run() {

        new Thread(new TcpClient(game, host, port)).start();

        /**
         * While we are connected
         */
        while (tcpClient.isConnected()) {
            byte[] dataArray = new byte[4096];
            DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
//                JOptionPane.showMessageDialog(game, "Network error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
                } catch (IOException | ClassNotFoundException ex) {
//                    JOptionPane.showMessageDialog(game, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(0);
                }
//                    JOptionPane.showMessageDialog(game, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);

            }
//            System.out.println("SERVER [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] " + message.trim());

        }
    }

    /**
     *
     */
    public void disconnect() {
        this.socket.close();
    }

    /**
     *
     * @return
     */
    public synchronized boolean isConnected() {
        return connected;
    }

    /**
     * Tick method
     */
    @Override
    public void tick() {
        if (tcpClient.isConnected() && isConnected()) {
            //Update player position every time
            //CLIENT_POSUPD;UUID;X;Y;DIRECTION
            send(ClientStatus.CLIENT_POSUPD.toString() + ";" + uuid + ";" + player.getX() + ";" + player.getY() + ";" + player.getDirection());
        }
    }

    /**
     * Send a packet
     *
     * @param cmd
     */
    public synchronized void send(String cmd) {
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

    /**
     *
     * @return
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @return
     */
    public PlayerList getPlayerList() {
        return playerList;
    }

}
