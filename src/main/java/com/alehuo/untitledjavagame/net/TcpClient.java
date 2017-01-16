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

import com.alehuo.untitledjavagame.Game;
import com.alehuo.untitledjavagame.net.packets.ConnectionTestPacket;
import com.alehuo.untitledjavagame.net.packets.PingPacket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alehuo
 */
public class TcpClient implements Runnable {

    /**
     *
     */
    public boolean connected = false;

    private boolean running = true;

    private Socket clientSocket;
    private Game g;
    private final InetAddress host;
    private final int port;

    //Interval of the ping packet
    private final int rate = 5;

    /**
     *
     * @param g
     * @param host
     * @param port
     */
    public TcpClient(Game g, InetAddress host, int port) {
        this.g = g;
        this.host = host;
        this.port = port;

        try {
            clientSocket = new Socket(host.getHostAddress(), port);
            clientSocket.close();
            connected = true;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(g, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param host
     * @param port
     */
    public TcpClient(InetAddress host, int port) {
        this.host = host;
        this.port = port;

        try {
            clientSocket = new Socket(host.getHostAddress(), port);
            ConnectionTestPacket conTestPacket = new ConnectionTestPacket();
            clientSocket.getOutputStream().write(ObjectHandler.prepare(conTestPacket));
            clientSocket.close();
            connected = true;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(g, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void run() {
        while (running && connected) {
            try {
                //Sleep
                Thread.sleep(rate * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Logger.getLogger(TcpClient.class.getName()).log(Level.FINEST, "Sending a ping packet to server");
                clientSocket = new Socket(host.getHostAddress(), port);
                PingPacket pingPacket = new PingPacket();
                clientSocket.getOutputStream().write(ObjectHandler.prepare(pingPacket));
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, "Could not send a ping packet to server");
//                JOptionPane.showMessageDialog(g, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                connected = false;
            }
        }
    }

    /**
     * Return the connection state
     *
     * @return Connection state
     */
    public synchronized boolean isConnected() {
        return connected;
    }

    /**
     *
     */
    public void disconnect() {
        running = false;
        connected = false;
    }

}
