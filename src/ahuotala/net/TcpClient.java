/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.net;

import ahuotala.game.Game;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alehuo
 */
public class TcpClient implements Runnable {
    
    public static boolean connected = false;
    
    private Socket clientSocket;
    private final Game g;
    private final InetAddress host;
    private final int port;

    //Interval of heartbeat
    private final int rate = 5;
    
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
            JOptionPane.showMessageDialog(g, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                //Sleep
                Thread.sleep(rate * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Logger.getLogger(TcpClient.class.getName()).log(Level.INFO, "Sending heartbeat packet to server");
                clientSocket = new Socket(host.getHostAddress(), port);
                clientSocket.getOutputStream().write("PING".getBytes());
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(g, "Error: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                connected = false;
                System.exit(1);
            }
        }
    }
    
    public boolean isConnected() {
        return connected;
    }
    
}
