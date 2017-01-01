
package com.ahuotala.untitledjavagame.game;

import com.ahuotala.untitledjavagame.entities.Player;
import com.ahuotala.untitledjavagame.net.Client;
import com.ahuotala.untitledjavagame.net.ClientStatus;
import com.ahuotala.untitledjavagame.net.TcpClient;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Multiplayer class
 *
 * @author alehuo
 */
public class Multiplayer {
    private static final Logger LOG = Logger.getLogger(Multiplayer.class.getName());

    /**
     * UdpClient handles data connection
     */
    private Client udpClient;
    private Thread udpClientThread;

    /**
     * TcpClient makes sure that the server is connected
     */
    private TcpClient tcpClient;
    private Thread tcpClientThread;
    /**
     * Game entity
     */
    private Game game;

    /**
     * Player entity
     */
    private Player player;

    /**
     * Host
     */
    private InetAddress host;

    /**
     * Port
     */
    private int port;

    /**
     *
     * @param g
     * @param p
     */
    public Multiplayer(Game g, Player p) {
        this.game = g;
        this.player = p;
    }

    /**
     *
     * @param host
     * @param port
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
    public boolean connect(InetAddress host, int port) throws SocketException, UnknownHostException {

        LOG.log(Level.INFO, "Player wants to connect to {0}:{1}", new Object[]{host.getHostAddress(), port});

        this.host = host;
        this.port = port;

        //Initialize TcpClient
        tcpClient = new TcpClient(host, port);

        //Initialize TcpClient thread
        tcpClientThread = new Thread(tcpClient);
        tcpClientThread.setDaemon(true);

        //Start the thread
        LOG.info("Starting TCP Client thread");
        tcpClientThread.start();

        //Initialize UdpClient if we can connect
        if (isConnected()) {
            udpClient = new Client(this);
            udpClientThread = new Thread(udpClient);
            udpClientThread.setDaemon(true);
            //Start the thread
            LOG.info("Starting UDP Client thread");
            udpClientThread.start();
            udpClient.send(ClientStatus.CLIENT_CONNECTED.toString() + ";" + udpClient.getUuid());
            return true;
        } else {
            LOG.log(Level.SEVERE, "Error connecting to {0}:{1}. Server didn't respond to TCP Client's request.", new Object[]{host.getHostAddress(), port});
            return false;
        }
    }

    /**
     * Disconnect from server
     */
    public void disconnect() {
        //If TCP and UDP clients have a connection
        if (isConnected() && udpClient.isConnected()) {
            udpClient.send(ClientStatus.CLIENT_DISCONNECTED.toString() + ";" + udpClient.getUuid());
            udpClient.disconnect();
            tcpClient.disconnect();
        } else {
            LOG.warning("Can't disconnect: Already disconnected");
        }
    }

    /**
     * Returns the connection state
     *
     * @return
     */
    public boolean isConnected() {
        return (tcpClient != null) ? tcpClient.isConnected() : false;
    }

    /**
     * Send packets in an own Thread Receive packets in an own Thread
     *
     * @throws java.lang.InterruptedException
     */
    public void update() throws InterruptedException {
        while (isConnected()) {
            //Sleep for 32 milliseconds (We update 30 times a second)
            Thread.sleep(32);
            //1. receive data
            //2. send data
        }
    }

    /**
     *
     * @return
     */
    public Client getUdpClient() {
        return udpClient;
    }

    /**
     *
     * @param udpClient
     */
    public void setUdpClient(Client udpClient) {
        this.udpClient = udpClient;
    }

    /**
     *
     * @return
     */
    public TcpClient getTcpClient() {
        return tcpClient;
    }

    /**
     *
     * @param tcpClient
     */
    public void setTcpClient(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    /**
     *
     * @return
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     *
     * @return
     */
    public InetAddress getHost() {
        return host;
    }

    /**
     *
     * @param host
     */
    public void setHost(InetAddress host) {
        this.host = host;
    }

    /**
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

}
