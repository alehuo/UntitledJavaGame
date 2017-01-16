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
import com.alehuo.untitledjavagame.Tickable;
import com.alehuo.untitledjavagame.net.packets.PlayerConnectedPacket;
import com.alehuo.untitledjavagame.net.packets.PlayerListPacket;
import com.alehuo.untitledjavagame.net.packets.UpdatePositionPacket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alehuo
 */
public class Client extends Thread implements Tickable {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    /**
     * Socket
     */
    private DatagramSocket socket;
    /**
     * Host
     */
    private InetAddress host;
    /**
     * Port
     */
    private int port;
    /**
     * Connected
     */
    private boolean connected = false;
    /**
     * Ready
     */
    private boolean ready = false;
    /**
     * Player
     */
    private Player player;
    /**
     * Uuid
     */
    private String uuid;
    /**
     * Player list
     */
    private PlayerList playerList;
    /**
     * Game
     */
    private Game game;
    /**
     * TCP client
     */
    private TcpClient tcpClient;

    /**
     *
     * @param m Multiplayer object
     */
    public Client(Multiplayer m) {

        try {
            this.player = m.getPlayer();
            this.socket = new DatagramSocket();
            this.host = m.getHost();
            this.port = m.getPort();
            this.game = m.getGame();
            this.tcpClient = m.getTcpClient();

            playerList = new PlayerList();
            uuid = UUID.randomUUID().toString();
            LOG.log(Level.INFO, "Connecting to {0}:{1} with player UUID {2}", new Object[]{host, port, uuid});

            ready = true;
        } catch (SocketException ex) {
            LOG.log(Level.INFO, "Failed to connect to {0}:{1}", new Object[]{host, port});
        }
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public void run() {

        new Thread(new TcpClient(game, host, port)).start();

        /**
         * While we are connected, receive packets
         */
        while (tcpClient.isConnected()) {
            byte[] dataArray = new byte[8192];
            DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length);
//            if (socket.isConnected()) {
            try {
                socket.receive(packet);
                Object messageObj = ObjectHandler.receive(packet);
                try {

                    //Confirmation of player successfully connected
                    if (messageObj.getClass().equals(PlayerConnectedPacket.class)) {
                        connected = true;
                    } else if (messageObj.getClass().equals(PlayerListPacket.class)) {
                        //Player list coming from the server
                        PlayerListPacket plrList = (PlayerListPacket) messageObj;
                        if (plrList != null) {
                            playerList.setPlayers(plrList.getMpPlayers());
                        } else {
                            LOG.log(Level.WARNING, "Player list is null");
                        }

                    }

                    LOG.log(Level.INFO, "SERVER [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] " + messageObj.getClass().toString());
                } catch (NullPointerException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(game, "Network error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
//                    System.exit(0);
                LOG.log(Level.SEVERE, null, ex);
            }
//            } else {
//                LOG.log(Level.SEVERE, "No connection to the server");
//            }

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
            UpdatePositionPacket updatePos = new UpdatePositionPacket();
            updatePos.setUuid(uuid);
            updatePos.setX(player.getX());
            updatePos.setY(player.getY());
            updatePos.setDirection(player.getDirection());
            send(updatePos);
//            send(ClientStatus.CLIENT_POSUPD.toString() + ";" + uuid + ";" + player.getX() + ";" + player.getY() + ";" + player.getDirection());
        }
    }

    /**
     * Send a packet
     *
     * @param obj
     */
    public synchronized void send(Object obj) {
        byte[] dataArray = new byte[8192];
        dataArray = ObjectHandler.prepare(obj);
        DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length, host, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(game, "Network error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
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
