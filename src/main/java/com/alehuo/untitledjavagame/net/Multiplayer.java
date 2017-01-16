/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame.net;

import com.alehuo.untitledjavagame.entities.Player;
import com.alehuo.untitledjavagame.Game;
import com.alehuo.untitledjavagame.net.packets.ConnectPacket;
import com.alehuo.untitledjavagame.net.packets.DisconnectPacket;
import com.alehuo.untitledjavagame.net.packets.UpdatePositionPacket;
import java.net.InetAddress;
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

    public Multiplayer() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
    }

    /**
     *
     * @param host
     * @param port
     * @return
     */
    public boolean connect(InetAddress host, int port) {

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
            if (udpClient.isReady()) {
                udpClientThread = new Thread(udpClient);
                udpClientThread.setDaemon(true);
                //Start the thread
                LOG.info("Starting UDP Client thread");
                udpClientThread.start();
                ConnectPacket connectPacket = new ConnectPacket();
                connectPacket.setUuid(udpClient.getUuid());
                udpClient.send(connectPacket);
                return true;
            } else {
                LOG.log(Level.SEVERE, "Error initializing UDP client.");
                return false;
            }

        } else {
//            udpClientThread.interrupt();
//            tcpClientThread.interrupt();
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
            DisconnectPacket disconnectPacket = new DisconnectPacket();
            disconnectPacket.setUuid(udpClient.getUuid());
            udpClient.send(disconnectPacket);
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
        return (tcpClient != null) ? (tcpClient.isConnected()) : false;
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
            UpdatePositionPacket obj = new UpdatePositionPacket();
            obj.setX(255);
            obj.setY(255);
            obj.setUuid(udpClient.getUuid());
            udpClient.send(obj);
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
